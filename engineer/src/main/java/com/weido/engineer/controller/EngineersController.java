package com.weido.engineer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.weido.engineer.pojo.*;
import com.weido.engineer.repository.EngineersRepository;
import com.weido.engineer.repository.UserRepository;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class EngineersController {
    @Autowired
    EngineersRepository engineersRepository;
    @Autowired
    UserRepository userRepository;
    LinkedHashMap map = new LinkedHashMap();

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
            if (map.containsKey("data")){
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
            jsonObject1.put("role",role);
            jsonObject1.put("serviceShop",serviceShop);
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
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            System.err.println(fileName);
            List<Engineers> engineers = engineersRepository.findAllByEid(eid);
            String destFileName = request.getServletContext().getRealPath("")
                    + engineers.get(0).getMobile() + File.separator + fileName;
            System.err.println(destFileName);
            File destFile = new File(destFileName);
            System.err.println(destFile);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdir();
            }
            file.transferTo(destFile);
            engineersRepository.updatePng(destFileName, eid);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("png",destFileName);
            map.put("type", 1);
            map.put("msg", "成功");
            map.put("data", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(map);
    }

    /***
     * 我的
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/my")
    public JSONObject findUser(@RequestBody JSONObject jsonObject){
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        List<Engineers> engineers = engineersRepository.findAllByEid(eid);

        String json=JSON.toJSONString(engineers.get(0),SerializerFeature.WriteDateUseDateFormat);
        System.err.println(engineers);
        JSONObject jsonObject1=JSONObject.fromObject(json);
        map.put("type",1);
        map.put("msg","成功");
        map.put("data",jsonObject1);
        return JSONObject.fromObject(map);
    }

    /***
     * 开通用户
     * int pid, String mobile, String uname, boolean sex, String password, Date registTime, int vipStatus, Date vipExpiration, UserHome userHome
     */
    @PostMapping(value = "/addUser")
    public JSONObject addUser(@RequestBody JSONObject jsonObject){
        String userName=jsonObject.get("user_name").toString();
        Boolean sex = Boolean.valueOf(jsonObject.get("sex").toString());
        int pid=Integer.parseInt(jsonObject.get("pid").toString());
        String address=jsonObject.get("address").toString();
        String phone=jsonObject.get("phone").toString();
        int year = Integer.parseInt(jsonObject.get("vipExpiration").toString());
        int cid = Integer.parseInt(jsonObject.get("cid").toString());
        String password = "123456";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);//增加一年
        Communities communities = new Communities();
        communities.setCid(cid);
        UserHome userHome = new UserHome("我的家",address,communities);
        userHome.setDevs(new Devs("",""));
        User user=new User(pid,phone,userName,sex,password,date,1,cal.getTime(), (List<UserHome>) userHome);
        userRepository.save(user);
        map.put("type",1);
        map.put("msg","开通成功");
        return JSONObject.fromObject(map);
    }

    @PostMapping("/findPersonByGid")
    public JSONObject findPersonByGid(@RequestBody JSONObject jsonObject){
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        List<Engineers> engineers = engineersRepository.findAllByGid(gid);
        String engineersString = JSON.toJSONString(engineers);
        map.put("type",1);
        map.put("msg","成功");
        map.put("data",engineersString);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/getMsg")
    public JSONObject sendMsg(@RequestBody JSONObject jsonObject){
        String mobile = jsonObject.get("mobile").toString();
        String msg = jsonObject.get("msg").toString();
        String code=HttpRequest.sendGet("http://www.ithaas.com/SMSDemo/interface/setTopBox/SMSACJson", "tel="+mobile);
        System.out.println(code);
        JSONObject jsonObject1 = JSONObject.fromObject(code);
        if (msg.equals(jsonObject1.get("code"))){
            map.put("type",1);
            map.put("msg",jsonObject1.get("code"));
            map.put("data","验证码验证通过");
        }
        else {
            if(Integer.parseInt(jsonObject1.get("code").toString())==0){
                map.put("type",0);
                map.put("msg","验证失败，请稍后再试");
            }
            else {
                map.put("type",0);
                map.put("msg",jsonObject1.get("message"));
                map.put("data","验证码验证未通过");
            }
        }
        return JSONObject.fromObject(map);
    }





}
