package com.weido.engineer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.weido.engineer.pojo.*;
import com.weido.engineer.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class OrderController {

    @Autowired
    CommOrderRepository commOrderRepository;
    @Autowired
    EngineersRepository engineersRepository;
    @Autowired
    SeperateNumRepository seperateNumRepository;
    @Autowired
    OrderTypeRepository orderTypeRepository;
    @Autowired
    SeperateRepository seperateRepository;
    @Autowired
    OrderStepRepository orderStepRepository;
    @Autowired
    finishCountRepository finishCountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserHomeRepository userHomeRepository;
    @Autowired
    UserRoomRepository userRoomRepository;
    @Autowired
    ScenceModelRepository scenceModelRepository;
    LinkedHashMap map = new LinkedHashMap();
    LinkedHashMap datemap = new LinkedHashMap();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/homePage")
    public JSONObject findOderByTime(@RequestBody JSONObject jsonObject) {
        Date Mondaydate,Fridaydate,firstdate,lastdate;
        int eid;
        List<CommOrders> week,month;
        JSONObject jsonObject1;
        String orderSize,giveSize,daySize;
        try {
            eid = Integer.parseInt(jsonObject.get("eid").toString());
            Date date = sdf.parse(jsonObject.get("order_time").toString());
            getWeekByDate(date);
            List<CommOrders> day = commOrderRepository.findAllByEidAndCurrentTime(eid);
            Mondaydate = sdf.parse(datemap.get("Monday").toString());
            Fridaydate = sdf.parse(datemap.get("Friday").toString());
            firstdate = sdf.parse(datemap.get("first").toString());
            lastdate = sdf.parse(datemap.get("last").toString());
            week = commOrderRepository.findWMByEidAndCurrent(eid, Mondaydate, Fridaydate);
            month = commOrderRepository.findWMByEidAndCurrent(eid, firstdate, lastdate);
            jsonObject1 = new JSONObject();
            jsonObject1.put("day", JSON.toJSONString(day));
            jsonObject1.put("week", JSON.toJSONString(week));
            jsonObject1.put("month", JSON.toJSONString(month));
            finishCount finishCount = finishCountRepository.findCountByEid(eid, 0);
            finishCount finishCount2 = finishCountRepository.findCountByEidAndType(eid,0);
            orderSize = JSON.toJSONString(finishCount, SerializerFeature.WriteNullNumberAsZero);
            giveSize = JSON.toJSONString(finishCount2,SerializerFeature.WriteNullNumberAsZero);
            finishCount finishCount1 = finishCountRepository.findCountByEid(eid, 1);
            daySize = JSON.toJSONString(finishCount1, SerializerFeature.WriteNullNumberAsZero);
            jsonObject1.put("orderSize", Integer.parseInt(JSONObject.fromObject(orderSize).get("count").toString()));
            jsonObject1.put("giveSize", Integer.parseInt(JSONObject.fromObject(giveSize).get("count").toString()));
            jsonObject1.put("daySize", Integer.parseInt(JSONObject.fromObject(daySize).get("count").toString()));
            map.put("type", 1);
            map.put("msg", "成功");
            map.put("data", jsonObject1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(map);
    }

    private JSONObject getWeekByDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 2);
        String imptimeMi = sdf.format(cal.getTime());
        System.out.println("所在周星期三的日期：" + imptimeMi);
        cal.add(Calendar.DATE, 2);
        String imptimeEnd = sdf.format(cal.getTime());
        System.out.println("所在周星期五的日期：" + imptimeEnd);
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = sdf.format(c.getTime());
        System.out.println("===============first:" + first);
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sdf.format(ca.getTime());
        System.out.println("===============last:" + last);
        datemap.put("Monday", imptimeBegin);
        datemap.put("Friday", imptimeEnd);
        datemap.put("first", first);
        datemap.put("last", last);
        datemap.put("day", ca.get(Calendar.DATE));
        return JSONObject.fromObject(map);
    }

//    /***
//     * 按状态/角色查询所有工单(分报警和维修)
//     * @param jsonObject
//     * @return
//     */
//    @PostMapping("/findAllCommByType")
//    public JSONObject findAllCommBytype(@RequestBody JSONObject jsonObject) {
//        int eid = Integer.parseInt(jsonObject.get("eid").toString());
//        int gid = Integer.parseInt(jsonObject.get("gid").toString());
//        int finished = Integer.parseInt(jsonObject.get("status_id").toString());
//        int order_type = Integer.parseInt(jsonObject.get("order_type").toString());
//        int page = Integer.parseInt(jsonObject.get("page_no").toString());
//        int rid = Integer.parseInt(jsonObject.get("rid").toString());
//        //Pageable pageable =new PageRequest(page,1);
//        int type = Integer.parseInt(jsonObject.get("type").toString());
//        page = (page - 1) * 20;
//        System.out.println(page);
//        List<CommOrders> commOrders = commOrderRepository.findAllByEidAndGidByType(eid, gid, finished, order_type, page);//finished 0未接单 1已接单 2已完成
//        //type:1分配 0转单
//        if (type == 1) {
//            rid--;
//        }
//        JSONObject steps2 = new JSONObject();
//        List<Engineers> engineers = engineersRepository.findAllByRidAndGid(rid, gid, eid);
//        String comm = JSON.toJSONString(commOrders);
//        System.out.println(comm);
//        String en = JSON.toJSONString(engineers);
//        JSONArray jsonArray = JSONArray.fromObject(comm);
//        JSONArray jsonArray1 = JSONArray.fromObject(en);
//        System.out.println(jsonArray1);
//        int count = commOrderRepository.count(eid, gid, 1, 1);
//        finishCount finishCounts = finishCountRepository.findCountByEidAndGid(eid, gid);
//        int todayCount = commOrderRepository.findCountByEidAndFinished(eid, 1);
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("emergency", count);
//        jsonObject2.put("workingCount", finishCounts.getCount());
//        jsonObject2.put("todayCount", todayCount);
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONArray steps = JSONArray.fromObject(JSON.toJSONString(
//                    orderStepRepository.findAllByOidAndEid(commOrders.get(i).getOid(), eid)));
//            if (steps.size() != 0) {
//                steps2 = (JSONObject) steps.getJSONObject(0).get("orderStatus");
//            }
//            jsonArray.getJSONObject(i).put("address", commOrders.get(i).getUserHome().getAddress());
//            jsonArray.getJSONObject(i).put("order_type", commOrders.get(i).getOrderType().getOrder_type());
//            jsonArray.getJSONObject(i).put("step", steps2.get("status_id"));
//            if (commOrders.get(i).getEngineers() == null) {
//                jsonArray.getJSONObject(i).put("engineer_name", "");
//            } else {
//                jsonArray.getJSONObject(i).put("engineer_name", commOrders.get(i).getEngineers().getName());
//            }
//            jsonArray.getJSONObject(i).put("user_phone", commOrders.get(i).getUser().getMobile());
//            jsonArray.getJSONObject(i).put("other_engineers", JSONArray.fromObject(JSON.toJSONString(jsonArray1)));
//        }
//        map.put("type", 1);
//        map.put("msg", "成功");
//        JSONObject jsonObject3 = new JSONObject();
//        jsonObject3.put("order", jsonArray);
//        jsonObject3.put("count", jsonObject2);
//        map.put("data", jsonObject3);
//        return JSONObject.fromObject(map);
//    }

    /***
     * 按状态/角色查询所有工单(不分报警和维修)
     * @param jsonObject
     * @return
     */
    @PostMapping("/findAllComm")
    public JSONObject findAllComm(@RequestBody JSONObject jsonObject) {
        int eid,order_type,gid,finished,page,rid,type;
        Date Mondaydate,Fridaydate,date1;
        String Monday,Friday;
        JSONArray jsonArray;
        JSONObject jsonObject2 = new JSONObject();
        eid = Integer.parseInt(jsonObject.get("eid").toString());
        gid = Integer.parseInt(jsonObject.get("gid").toString());
        finished = Integer.parseInt(jsonObject.get("status_id").toString());
        page = Integer.parseInt(jsonObject.get("page_no").toString());
        rid = Integer.parseInt(jsonObject.get("rid").toString());
        type = Integer.parseInt(jsonObject.get("type").toString());
        List<CommOrders> commOrders = new ArrayList<>();
        page = (page - 1) * 20;
        if (jsonObject.containsKey("order_type")&&!jsonObject.get("order_type").toString().equals("")){
            order_type = Integer.parseInt(jsonObject.get("order_type").toString());
            commOrders = commOrderRepository.findAllByEidAndGidByType(eid, gid, finished, order_type, page);//finished 0未接单 1已接单 2已完成
        }
        if (!jsonObject.get("today").toString().equals("")) {
            commOrders = commOrderRepository.findAllByEidAndFinished(eid, finished, page);//finished 0未接单 1已接单 2已完成
            jsonObject2.put("weekDelay", 0);
        } else if (!jsonObject.get("late").toString().equals("")) {
            date1 = new Date();
            getWeekByDate(date1);
            Monday = datemap.get("Monday").toString();
            System.out.println(Monday);
            Friday = datemap.get("Friday").toString();
            System.out.println(Friday);
            try {
                Mondaydate = sdf.parse(Monday);
                Fridaydate = sdf.parse(Friday);
                commOrders = commOrderRepository.findAllBylate(Integer.parseInt(jsonObject.get("late").toString())
                        , Mondaydate, Fridaydate);
                jsonObject2.put("weekDelay", commOrders.size());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            jsonObject2.put("weekDelay", 0);
            if (finished == 2) {
                commOrders = commOrderRepository.findRangByEidAndGid(eid, gid, finished, page);
            }else if (finished==0){
                commOrders = commOrderRepository.findAllByEidAndEidAndNoEid(eid,gid,finished,page);
            }
            else {
                commOrders = commOrderRepository.findAllByEidAndGid(eid, gid, finished, page);//finished 0未接单 1已接单 2已完成
            }
        }
        //type:1分配 0其他情况
        if (type == 1) {
            rid--;
        }
        String comm = JSON.toJSONString(commOrders);
        jsonArray = JSONArray.fromObject(comm);
        List<Engineers> engineers = engineersRepository.findAllByRidAndGid(rid, gid, eid);
        System.out.println(engineers);
        finishCount finishCounts = finishCountRepository.findCountByEidAndGid(eid, gid);
        int count = commOrderRepository.count(eid, gid, 1, 1);
        int todayCount = commOrderRepository.findCountByEidAndFinished(eid, 1);
        jsonObject2.put("emergency", count);
        jsonObject2.put("workingCount", finishCounts.getCount());
        jsonObject2.put("todayCount", todayCount);
        JSONObject steps2 = new JSONObject();
        JSONObject acttime = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray steps = JSONArray.fromObject(JSON.toJSONString(
                    orderStepRepository.findAllByOidAndEid(commOrders.get(i).getOid(), eid)));
            if (steps.size() != 0) {
                steps2 = (JSONObject) steps.getJSONObject(0).get("orderStatus");
                acttime = steps.getJSONObject(0);
            }
            jsonArray.getJSONObject(i).put("address", commOrders.get(i).getUserHome().getAddress());
            jsonArray.getJSONObject(i).put("order_type", commOrders.get(i).getOrderType().getOrder_type());
            jsonArray.getJSONObject(i).put("user_name", commOrders.get(i).getUser().getUname());
            jsonArray.getJSONObject(i).put("user_phone", commOrders.get(i).getUser().getMobile());
            jsonArray.getJSONObject(i).put("step", steps2.get("status_id"));
            if (acttime.containsKey("acttime")) {
                jsonArray.getJSONObject(i).put("acttime", acttime.get("acttime"));
            } else {
                jsonArray.getJSONObject(i).put("acttime", 0);
            }
            if (commOrders.get(i).getEngineers() == null) {
                jsonArray.getJSONObject(i).put("engineer_name", "");
            } else {
                jsonArray.getJSONObject(i).put("engineer_name", commOrders.get(i).getEngineers().getName());
            }
            jsonArray.getJSONObject(i).put("other_engineers", JSONArray.fromObject(JSON.toJSONString(engineers)));
        }
        map.put("type", 1);
        map.put("msg", "成功");
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("order", jsonArray);
        jsonObject3.put("count", jsonObject2);
        //map.put("data", jsonObject3.put(jsonArray));
        //jsonObject3.put("data",jsonArray.get(0));
        map.put("data", jsonObject3);
        return JSONObject.fromObject(map);
    }

//    /***
//     * 更改接单类型
//     * @param jsonObject
//     * @return
//     */
//    @PostMapping("/changeType")
//    public void changeType(@RequestBody JSONObject jsonObject){
//        int oid = Integer.parseInt(jsonObject.get("oid").toString());
//        int finished = Integer.parseInt(jsonObject.get("finished").toString());
//        commOrderRepository.changeFinished(oid,finished);//finished 0未接单 1已接单 2已完成非评分 3已完成
//        map.put("type", 1);
//        map.put("msg", "成功");
//    }
//@Query(value = "update comm_orders c,order_step o " +
//            "set c.order_step_step_id=?2 and c.finished=?5 and o.acttime=?3" +
//            " and o.engineers_eid=?4 and o.order_status_status_id=?6  where oid=?1",nativeQuery = true)

    //备注：后期要考虑finished和status_id字段重复
    @PostMapping("/changeStep")
    public JSONObject changeStep(@RequestBody JSONObject jsonObject) {
        int oid = Integer.parseInt(jsonObject.get("oid").toString());
        int finished = Integer.parseInt(jsonObject.get("finished").toString());
        int status_id = Integer.parseInt(jsonObject.get("step").toString());
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        Date acttime = new Date();
        Engineers engineers = new Engineers();
        engineers.setEid(eid);
        CommOrders commOrders = new CommOrders();
        commOrders.setOid(oid);
        OrderStatus orderStatus1 = new OrderStatus();
        orderStatus1.setStatus_id(status_id);
        if (status_id == 1) {
            commOrderRepository.changeStep(oid, finished, eid);//finished 0未接单 1已接单 2已完成非评分 3已完成
            OrderStep orderStep = new OrderStep(acttime, "工单已转接", orderStatus1, commOrders, engineers);
            orderStepRepository.save(orderStep);
        } else {
            if (jsonObject.get("description").toString().equals("")) {
                OrderStep orderStep = new OrderStep(acttime, "", orderStatus1, commOrders, engineers);
                orderStepRepository.save(orderStep);
                commOrderRepository.changeFinished(oid, finished);
            } else {
                OrderStep orderStep = new OrderStep(acttime,
                        jsonObject.get("description").toString(), orderStatus1, commOrders, engineers);
                orderStepRepository.save(orderStep);
                commOrderRepository.changeFinished(oid, finished);
            }
        }
        map.put("type", 1);
        map.put("msg", "更改步骤成功");
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping("/getScore")
    public JSONObject getScornByEid(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        int role = Integer.parseInt(jsonObject.get("role").toString());
        JSONObject mapObject = new JSONObject();
        boolean flag = false;
        //成绩
        //够且当前工程是没有在前十
        Seperate mycommOrders = seperateRepository.findScoreByEid(eid, 2);
        List<Seperate> commOrders = seperateRepository.findScoreByGid(gid, 2, role);
        JSONObject myjsonstars = JSONObject.fromObject(JSON.toJSONString(mycommOrders));
        JSONArray allStarArray = JSONArray.fromObject(JSON.toJSONString(commOrders));
        SeperateNum seperateNums = seperateNumRepository.findNumberByEid(eid, 2);
        JSONObject myjsonnumber = JSONObject.fromObject(JSON.toJSONString(seperateNums));
        List<SeperateNum> seperateNums1 = seperateNumRepository.findNumberByGid(gid, 2, role);
        JSONArray allNumberArray = JSONArray.fromObject(JSON.toJSONString(seperateNums1));
        sort(allNumberArray, "number", false);
        if (allStarArray.size() < 10) {
            for (int i = 0; i < allStarArray.size(); i++) {
                int score = Integer.parseInt(allStarArray.getJSONObject(i).get("appraise_score").toString());
                allStarArray.getJSONObject(i).put("appraise_score", (score / 3) + "");
            }
            mapObject.put("stars", allStarArray);
            mapObject.put("number", seperateNums1);
        } else {
            for (int i = 0; i < 10; i++) {
                int score=Integer.parseInt(allStarArray.getJSONObject(i).get("appraise_score").toString());
                allStarArray.getJSONObject(i).put("appraise_score",score/3+"");
                if (Integer.parseInt(allStarArray.getJSONObject(i).get("eid").toString()) == eid) {
                    flag=true;
                } else {
                    flag=false;
                }
            }
            if (flag==false){
                allStarArray.set(9, myjsonstars);
                allNumberArray.set(9, myjsonnumber);
            }
            mapObject.put("stars", allStarArray);
            mapObject.put("number", allNumberArray);
        }
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", mapObject);
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/transfer")
    public JSONObject transfer(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int oid = Integer.parseInt(jsonObject.get("oid").toString());
        commOrderRepository.transfer(eid, oid);
        map.put("type", 1);
        map.put("msg", "分配成功");
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/saveLocation")
    public JSONObject saveLocation(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        Double locationLon = (Double) jsonObject.get("lon");
        Double locationLat = (Double) jsonObject.get("lat");
        Date locationTime = new Date();
        engineersRepository.updateLocation(locationLon, locationLat, locationTime, eid);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/was1/wdhome/zhinengjia/addhome")
    public JSONObject addHome(@RequestBody JSONObject jsonObject) {
        String mobile = jsonObject.get("mobile").toString();
        int uid = userRepository.selectById(mobile);
        String homename = jsonObject.get("homename").toString();
        String address = jsonObject.get("address").toString();
        User user = new User();
        user.setUid(uid);
        UserHome userHome = new UserHome();
        userHome.setHomename(homename);
        userHome.setAddress(address);
        userHome.setUsers(user);
        userHome.setDevs(new Devs("", ""));
        userHomeRepository.save(userHome);
        List<UserHome> userHomes = userHomeRepository.findAllByhomeId(uid);
        System.err.println(JSON.toJSONString(userHomes));
        userRoomRepository.addRoom("默认房间", 0, "", userHomes.get(userHomes.size() - 1).getHomeid());
        UserHome userHome1 = new UserHome();
        userHome1.setHomeid(userHomes.get(userHomes.size() - 1).getHomeid());
        ScenceModel scenceModel = new ScenceModel();
        scenceModel.setUserHome(userHome1);
        scenceModel.setScenceName("默认模式");
        scenceModel.setMsgStatus(0);
        scenceModel.setAction("");
        scenceModelRepository.save(scenceModel);
        List<ScenceModel> scenceModels = scenceModelRepository.findAll();
        List<UserRoom> userRooms = userRoomRepository.findAll();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("homeid", userHomes.get(userHomes.size() - 1).getHomeid());
        jsonObject2.put("roomid", userRooms.get(userRooms.size() - 1).getRoomid());
        jsonObject2.put("smid", scenceModels.get(scenceModels.size() - 1).getSmid());
        LinkedHashMap map = new LinkedHashMap();
        map.put("type", 1);
        map.put("msg", "正常");
        map.put("data", jsonObject2);
        JSONObject jsonObject1 = JSONObject.fromObject(map);
        return jsonObject1;
    }


//    @PostMapping("/todayMission")
//    public JSONObject findAllCommByEidAndGid(@RequestBody JSONObject jsonObject) {
//        try {
//            int eid = Integer.parseInt(jsonObject.get("eid").toString());
//            int finished = Integer.parseInt(jsonObject.get("status_id").toString());
//            Date date = sdf.parse(jsonObject.get("order_time").toString());
//            Date date1 = new Date();
//            System.out.println(date1);
//            List<CommOrders> commOrders = commOrderRepository.findAllByEidAndFinished(eid, finished);//finished 0未接单 1已接单 2已完成
//            String commString = JSON.toJSONString(commOrders);
//            JSONArray jsonArray = JSONArray.fromObject(commString);
//            map.put("type", 1);
//            map.put("msg", "成功");
//            map.put("data", jsonArray);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return JSONObject.fromObject(map);
//    }

    private static void sort(JSONArray ja, final String field, boolean isAsc) {
        Collections.sort(ja, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                Object f1 = o1.get(field);
                Object f2 = o2.get(field);
                if (f1 instanceof Number && f2 instanceof Number) {
                    return ((Number) f1).intValue() - ((Number) f2).intValue();
                } else {
                    return f1.toString().compareTo(f2.toString());
                }
            }
        });
        if (!isAsc) {
            Collections.reverse(ja);
        }
    }

}
