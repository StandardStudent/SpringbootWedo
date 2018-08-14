package com.weido.work.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.weido.work.pojo.*;

import com.weido.work.repository.*;
import com.weido.work.service.RoomService;
import com.weido.work.service.SceneDefaultService;
import com.weido.work.service.SceneMineService;
import com.weido.work.service.SensorService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;



@RestController
//@RequestMapping(value = "/was1/wdhome/zhinengjia/")
public class RoomController {
    @PersistenceContext
    EntityManager em;
    @Autowired
    RoomService roomService;
    @Autowired
    SensorService sensorService;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    SceneDefaultService sceneDefaultService;
    @Autowired
    SceneMineService sceneMineService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HomeRepository homeRepository;
    //用于储存返回信息
    LinkedHashMap map = new LinkedHashMap();



    @PostMapping(value = "/was1/wdhome/zhinengjia/addRoom")
    public JSONObject addRoom(@RequestBody JSONObject jsonObject) {
        String roomName = jsonObject.get("roomname").toString();
        int homeId = Integer.parseInt(jsonObject.get("homeid").toString());
        int enable = 0;
        int uid = Integer.parseInt(jsonObject.get("uid").toString());
        UserHome userHome = new UserHome();
        userHome.setHomeid(homeId);
        UserRoom userRoom = new UserRoom(roomName, enable,userHome);
        userRoom.setUserHome(userHome);
        roomService.addRoom(userRoom);
        //User user = roomService.findUser(uid);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("roomid",userRoom.getRoomid());
        map.put("type", 1);
        map.put("msg","成功");
        map.put("data", jsonObject2);
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }



    @PostMapping(value = "/was1/wdhome/zhinengjia/findRoom")
    public JSONObject findRoom(@RequestBody JSONObject jsonObject) {
        int roomid = Integer.parseInt(jsonObject.get("roomid").toString());
        List<Sensor> sensors = sensorRepository.findAllByRoomidInt(roomid);
        String json = JSON.toJSONString(sensors, SerializerFeature.WriteNullStringAsEmpty);
        JSONArray jsonArray = JSONArray.fromObject(json);
        map.put("type",1);
        map.put("msg","成功");
        map.put("data",jsonArray);
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }



    /**
     * 查询某一个房中所有设备
     *
     * @param homeid 房间id
     * @return
     */
    @PostMapping(value = "/findAllDev")
    public JSONObject findAllSensor(@RequestParam("homeid") int homeid) {
        List<Sensor> sensorList = sensorService.findAllByHomeId(homeid);
        map.put("type", "success");
        map.put("data", sensorList.toString());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }

    /**
     * 查询所有模式
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/findAllModel")
    public JSONObject showAllModel(@RequestBody JSONObject jsonObject) {
        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
        List<SceneDefault> sceneDefaultList = sceneDefaultService
                .findSceneDefaultByHomeId(homeid);
        List<SceneMine> sceneMineList = sceneMineService
                .findSceneMineByHomeId(homeid);
        map.put("type", "success");
        map.put("sd", sceneDefaultList.toString());
        map.put("sm", sceneMineList.toString());
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }

//    /**
//     * 离/回家
//     */
//    @PostMapping(value = "/was1/wdhome/zhinengjia/leaveHome")
//    public JSONObject leavebackHome(@RequestBody JSONObject jsonObject) {
//        int uid = Integer.parseInt(jsonObject.get("uid").toString());
//        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
//        UserHome userHome = new UserHome();
//        userHome.setHomeid(homeid);
//        UserRoom userRoom = new UserRoom(userHome);
//        //离家
//        if(){
//            Sensor sensor = new Sensor("打开",userRoom);
//        }
//        //回家
//        else {
//            Sensor sensor = new Sensor("关闭",userRoom);
//        }
//        sensorRepository.save(sensor);
//        map.put("type", "1");
//        map.put("msg","成功");
//        JSONObject jsonObject1 = JSONObject.fromObject(map);
//        return jsonObject1;
//    }


    /**
     * 查询特定房的所有房间
     *
     * @param homeid 房id
     * @param uid    用户id(可不传)
     * @return
     */
    @PostMapping(value = "/defaultRoom")
    public JSONObject findAllRoom(@RequestParam("homeid") int homeid,
                                  @RequestParam("uid") int uid) {
        List<UserRoom> userRoomList = roomService.findAllRoomByHomeId(homeid);
        map.put("type", "success");
        map.put("data", userRoomList.toString());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }

    /**
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/common")
    public JSONObject findSensorBySID(@RequestBody JSONObject jsonObject) {
        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        String sid = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            if (sid.equals("")) {
                sid = sid + jsonObject1.get("sid");
            } else {
                sid = sid + "," + jsonObject1.get("sid");
            }
        }
        List<Sensor> sensorList = sensorService.findAllBySID(sid, homeid);
        map.put("type", "success");
        map.put("data", sensorList.toString());
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }

    /**
     * 切换家庭
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/was1/wdhome/zhinengjia/changeHome")
    public JSONObject changeHome(@RequestBody JSONObject jsonObject) {
        //int uid = Integer.parseInt(jsonObject.get("uid").toString());
        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
        List<UserHome> homes = homeRepository.findAllByhomeId(homeid);
        String json = JSON.toJSONString(homes);
        JSONObject jsonObject2 = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(json);
        for (int i=0;i<jsonArray.size();i++){
            jsonObject2 = jsonArray.getJSONObject(i);
        }
        map.put("type", 1);
        map.put("msg","成功");
        map.put("data", jsonObject2);
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }

    /**
     * 初始页面
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/was1/wdhome/zhinengjia/index")
    public JSONObject FirstPage(@RequestBody JSONObject jsonObject) {
        int uid = Integer.parseInt(jsonObject.get("uid").toString());
        List<UserHome> userHomes = homeRepository.findAllByUid(uid);
        String json = JSON.toJSONString(userHomes, SerializerFeature.WriteNullStringAsEmpty);
        JSONArray jsonArray = JSONArray.fromObject(json);
        map.put("type", 1);
        map.put("msg", "正常");
        map.put("data",jsonArray);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/was1/wdhome/zhinengjia/addSensor")
    public JSONObject addSensor(@RequestBody JSONObject jsonObject){
        int roomid = Integer.parseInt(jsonObject.get("roomid").toString());
        System.out.println("++++++++++"+roomid);
        String sensorName = jsonObject.get("sname").toString();
        String sn = jsonObject.get("sn").toString();

//        int typeId = Integer.parseInt(jsonObject.get("typeid").toString());
        UserRoom userRoom = new UserRoom();
        userRoom.setRoomid(roomid);
//        SensorType sensorType = new SensorType();
//        sensorType.setTypeid(typeId);
        Sensor sensor = new Sensor(sn,sensorName,userRoom);
        sensorRepository.save(sensor);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("sid",sensor.getSid());
        map.put("type",1);
        map.put("msg","成功");
        map.put("data",jsonObject1);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/was1/wdhome/zhinengjia/findAllSensor")
    public JSONObject findAllSensor(@RequestBody JSONObject jsonObject){
        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
        List<Sensor> sensors= sensorRepository.findAllByHomeId(homeid);
        String json = JSON.toJSONString(sensors);
        JSONArray jsonArray = JSONArray.fromObject(json);
        map.put("type",1);
        map.put("msg","成功");
        map.put("data",jsonArray);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/was1/wdhome/zhinengjia/updateGateway")
    public JSONObject updateGateway(@RequestBody JSONObject jsonObject){
        int homeid = Integer.parseInt(jsonObject.get("homeid").toString());
        String gatewayname = jsonObject.get("gatewayname").toString();
        String gatewaypwd = jsonObject.get("gatewaypwd").toString();
        homeRepository.updateGateway(homeid,gatewayname,gatewaypwd);
        map.put("type","1");
        map.put("msg","成功");
        return JSONObject.fromObject(map);
    }

//    @PostMapping(value = "/was1/wdhome/zhinengjia/collectSensor")
//    public JSONObject collect(@RequestBody JSONObject jsonObject){
//        int sid = Integer.parseInt(jsonObject.get("sid").toString());
//        int collectstatus = Integer.parseInt(jsonObject.get("collect").toString());
//
//    }

}
