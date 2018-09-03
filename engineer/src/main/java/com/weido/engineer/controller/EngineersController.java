package com.weido.engineer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.weido.engineer.pojo.*;
import com.weido.engineer.repository.*;
import com.weido.engineer.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class EngineersController {
    @Autowired
    EngineersRepository engineersRepository;
    @Autowired
    UserRepository userRepository;
    LinkedHashMap map = new LinkedHashMap();
    String code = null;
    @Autowired
    UserHomeRepository userHomeRepository;
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    /***
     * 登陆
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody JSONObject jsonObject) {
        String mobile = jsonObject.get("mobile").toString();
        String pwd = jsonObject.get("password").toString();
        Engineers engineers = engineersRepository.findAllByMobileAndPwd(mobile, pwd);
        if (engineers == null) {
            map.put("type", 0);
            map.put("msg", "用户名或密码错误");
            if (map.containsKey("data")) {
                map.remove("data");
            }
        } else {
            String json = JSON.toJSONString(engineers);
            System.out.println(json);
            JSONObject jsonObject1 = JSONObject.fromObject(json);
            Long time = (Long) jsonObject1.get("birthday");
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String role = JSON.toJSONString(engineers.getRoles().getRid());
            String serviceShop = JSON.toJSONString(engineers.getServiceShop().getGid());
            jsonObject1.put("birthday", simpleDateFormat.format(date));
            jsonObject1.put("role", role);
            jsonObject1.put("serviceShop", serviceShop);
            map.put("type", 1);
            map.put("msg", "成功");
            map.put("data", jsonObject1);
        }
        return JSONObject.fromObject(map);
    }

    /***
     * 上传头像
     * @param eid
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/updatePng")
    public JSONObject updatePng(@RequestParam("eid") int eid,
                                @RequestParam("headImg") MultipartFile file,
                                HttpServletRequest request
    ) {
        String sonPath = "/usr/local/tomcat8/webapps/engineer/img/";
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String filePath = sonPath;
        File dest = new File(filePath + fileName);
        String imgPath = (sonPath + fileName);
        String imPath1 = ("/engineer/img/" + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            engineersRepository.updatePng(imPath1, eid);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("png", imPath1);
            map.put("type", 1);
            map.put("data", jsonObject1);
            return JSONObject.fromObject(map);
        } catch (Exception e) {
            map.put("type", 1);
            map.put("msg", "上传失败");
            return JSONObject.fromObject(map);
        }

    }

    /***
     * 我的
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/my")
    public JSONObject findUser(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        List<Engineers> engineers = engineersRepository.findAllByEid(eid);
        String json = JSON.toJSONString(engineers.get(0), SerializerFeature.WriteDateUseDateFormat);
        System.err.println(engineers);
        JSONObject jsonObject1 = JSONObject.fromObject(json);
        jsonObject1.put("serviceShopName",engineers.get(0).getServiceShop().getGname());
        jsonObject1.put("appraise","优秀");
        //jsonObject1.put("fault",engineers.get(0).);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonObject1);
        return JSONObject.fromObject(map);
    }

    /***
     * 开通用户
     * int pid, String mobile, String uname, boolean sex, String password, Date registTime, int vipStatus, Date vipExpiration, UserHome userHome
     */
    @PostMapping(value = "/addUser")
    public JSONObject addUser(@RequestBody JSONObject jsonObject) {
        String userName = jsonObject.get("user_name").toString();
        Boolean sex = Boolean.valueOf(jsonObject.get("sex").toString());
        String pid = jsonObject.get("pid").toString();
        String address = jsonObject.get("address").toString();
        String phone = jsonObject.get("phone").toString();
        int year = Integer.parseInt(jsonObject.get("vipExpiration").toString());
        int cid = Integer.parseInt(jsonObject.get("cid").toString());
        String msg = jsonObject.get("msgCode").toString();
        String password = "123456";
        String codeStr = JSONObject.fromObject(code).get("code").toString();
        UserHome userHome;
        if (msg.equals(codeStr)) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, year);//增加一年
            Communities communities = new Communities();
            communities.setCid(cid);
            User user = new User(pid, phone, userName, sex, password);
            List<User> users = userRepository.findByMobile(phone);
            userHome = new UserHome("我的家", address, communities,date, 1, cal.getTime(),1);
            if (users.size() != 0) {
                User user1 = new User();
                user1.setUid(users.get(0).getUid());
                userHome.setUsers(user1);
            } else {
                userRepository.save(user);
                userHome = new UserHome("我的家", address, communities,date, 1, cal.getTime(),1);
                userHome.setUsers(user);
            }
            userHome.setDevs(new Devs("", ""));
            userHomeRepository.save(userHome);
            userRoomRepository.addRoom("默认房间",0,"",userHome.getHomeid());
            map.put("type", 1);
            map.put("msg", "开通成功");
        } else {
            map.put("type", 0);
            map.put("msg", "验证码错误");
        }
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping("/findPersonByGid")
    public JSONObject findPersonByGid(@RequestBody JSONObject jsonObject) {
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        List<Engineers> engineers = engineersRepository.findAllByGid(gid);
        String engineersString = JSON.toJSONString(engineers);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", engineersString);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/getMsg")
    public JSONObject sendMsg(@RequestBody JSONObject jsonObject) {
        String mobile = jsonObject.get("mobile").toString();
//        String msg = jsonObject.get("msg").toString();
        code = HttpRequest.sendGet("http://www.ithaas.com/SMSDemo/interface/setTopBox/SMSACJson", "tel=" + mobile);
        JSONObject jsonObject1 = JSONObject.fromObject(code);
        System.out.println(code);
//        if(!code.equals(null)) {
        if (Integer.parseInt(jsonObject1.get("code").toString()) == 0) {
            map.put("type", jsonObject1.get("code"));
            map.put("msg", "发送频繁，请稍后再试");
        } else {
            map.put("type", 1);
            map.put("msg", jsonObject1.get("message").toString());
        }
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/findCommunity")
    public JSONObject findCommunity(@RequestBody JSONObject jsonObject) {
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        List<Communities> communities = communityRepository.findAllByGid(gid);
        String json = JSON.toJSONString(communities);
        JSONArray jsonArray = JSONArray.fromObject(json);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/changePwd")
    public JSONObject changePwd(@RequestBody JSONObject jsonObject) {
        String mobile = jsonObject.get("mobile").toString();
        List<Engineers> engineers = engineersRepository.findAllByMobile(mobile);
        System.out.println(engineers);
        if (engineers.size() != 0) {
            String msgCode = jsonObject.get("msgCode").toString();
            String pwd = jsonObject.get("password").toString();
            System.out.println(pwd);
            String codeStr = JSONObject.fromObject(code).get("code").toString();
            if (msgCode.equals(codeStr)) {
                engineersRepository.updatePassword(mobile, pwd);
                map.put("type", 1);
                map.put("msg", "修改成功");
            } else {
                map.put("type", 0);
                map.put("msg", "验证码错误");
            }
            map.put("data", "");
        } else {
            map.put("type",0);
            map.put("msg","当前用户未注册");
            map.put("data","");
        }
        return JSONObject.fromObject(map);
    }




}
