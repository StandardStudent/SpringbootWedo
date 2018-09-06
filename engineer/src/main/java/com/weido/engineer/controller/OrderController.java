package com.weido.engineer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.weido.engineer.pojo.*;
import com.weido.engineer.repository.*;
import com.weido.engineer.util.JsonUtils;
import com.weido.engineer.util.dataUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.weido.engineer.util.dataUtils.isInTime;

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
    @Autowired
    CountFaultRepository countFaultRepository;
    @Autowired
    FaultRepository faultRepository;

    LinkedHashMap map = new LinkedHashMap();
    LinkedHashMap datemap = new LinkedHashMap();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    private static WebSocketClient client;// 连接客户端
    private static DraftInfo selectDraft;// 连接协议
    int type1 = 0, type2 = 0, type3 = 0, type4 = 0, type5 = 0, type6 = 0;

    @PostMapping("/homePage")
    public JSONObject findOderByTime(@RequestBody JSONObject jsonObject) {
        int eid;
        List<CommOrders> week, month;
        JSONObject jsonObject1;
        String orderSize, giveSize, daySize;
        JsonConfig jsonConfig = new JsonConfig();
        try {
            eid = Integer.parseInt(jsonObject.get("eid").toString());
            List<CommOrders> day = commOrderRepository.findAllByEidAndCurrentTime(eid);
            week = commOrderRepository.findWMByEidAndCurrent(eid, getWeekMonday(), getWeekFriday());
            month = commOrderRepository.findWMByEidAndCurrent(eid, getMonthFirstday(), getMonthLast());
            jsonObject1 = new JSONObject();
            jsonObject1.put("day", JSON.toJSONString(day));
            jsonObject1.put("week", JSON.toJSONString(week));
            jsonObject1.put("month", JSON.toJSONString(month));
            finishCount finishCount = finishCountRepository.findCountByEid(eid, 0);
            finishCount finishCount2 = finishCountRepository.findCountByEidAndType(eid, 0);
            orderSize = JSON.toJSONString(finishCount, SerializerFeature.WriteNullNumberAsZero);
            giveSize = JSON.toJSONString(finishCount2, SerializerFeature.WriteNullNumberAsZero);
            finishCount finishCount1 = finishCountRepository.findCountByEid(eid, 1);
            daySize = JSON.toJSONString(finishCount1, SerializerFeature.WriteNullNumberAsZero);
            int gid = engineersRepository.findAllByEid(eid).get(0).getServiceShop().getGid();
            int elseNum = finishCountRepository.findCountByNull(0, 1, gid).getCount();
            System.out.println(elseNum);
            jsonObject1.put("orderSize", Integer.parseInt(JSONObject.fromObject(orderSize).get("count").toString())
                    + elseNum);
            jsonObject1.put("giveSize", Integer.parseInt(JSONObject.fromObject(giveSize).get("count").toString()));
            jsonObject1.put("daySize", Integer.parseInt(JSONObject.fromObject(daySize).get("count").toString()));
            jsonObject1.put("todayWorking", commOrderRepository.findtodayWorking(eid));
            map.put("type", 1);
            map.put("msg", "成功");
            map.put("data", JSONObject.fromObject(JSON.toJSONString(jsonObject1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(map);
    }

    /***
     * 按状态/角色查询所有工单(分报警和维修)
     * @param jsonObject
     * @return
     */
    @PostMapping("/findAllCommByType")
    public JSONObject findAllCommBytype(@RequestBody JSONObject jsonObject) {
        String comm;
        JSONArray jsonArray;
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        int finished = Integer.parseInt(jsonObject.get("status_id").toString());
        int order_type = Integer.parseInt(jsonObject.get("order_type").toString());//1 报警 2 维修 3 预约维修 4 已抢报警单
        int page = Integer.parseInt(jsonObject.get("page_no").toString());
        int rid = Integer.parseInt(jsonObject.get("rid").toString());
        int type = Integer.parseInt(jsonObject.get("type").toString());
        page = (page - 1) * 20;
        System.out.println(page);
        List<CommOrders> commOrders;
        if (order_type == 4) {
            order_type = 1;
            commOrders = commOrderRepository.findOrderTypeNotNull(eid, gid, finished, order_type, page);//finished 0未接单 1已接单 2已完成
        } else {
            commOrders = commOrderRepository.findOderTypeByEidAndGidByType(eid, gid, finished, order_type, page);//finished 0未接单 1已接单 2已完成
        }
        comm = JSON.toJSONString(commOrders);
        jsonArray = JSONArray.fromObject(comm);
        int strStartIndex, strEndIndex;
        //type:1分配 0转单
        if (rid > 1) {
            rid--;
        }
        JSONObject steps2 = new JSONObject();
        List<Engineers> engineers = engineersRepository.findAllByRidAndGid(rid, gid, eid);
        String en = JSON.toJSONString(engineers);
        JSONArray jsonArray1 = JSONArray.fromObject(en);
        System.out.println(jsonArray1);
        int count = commOrderRepository.count(eid, gid, 1, 1);
        finishCount finishCounts = finishCountRepository.findCountByEidAndGid(eid, gid);
        int todayCount = commOrderRepository.findCountByEidAndFinished(eid, 1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("emergency", count);
        jsonObject2.put("workingCount", finishCounts.getCount());
        jsonObject2.put("todayCount", todayCount);
        for (int i = 0; i < jsonArray.size(); i++) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(OrderStep.class, "step_id", "acttime", "description"
                    , "order_status_status_id", "comm_order_oid", "late");
            JSONArray steps = JSONArray.fromObject(JSON.toJSONString(orderStepRepository.findAllByOidAndEid
                    ((Integer) jsonArray.getJSONObject(i).get("oid")), filter));
            if (steps.size() != 0) {
                steps2 = (JSONObject) steps.getJSONObject(0).get("orderStatus");
            }
            jsonArray.getJSONObject(i).put("community", commOrders.get(i).getUserHome().getCommunities().getName());
            jsonArray.getJSONObject(i).put("address", commOrders.get(i).getUserHome().getCommunities().getLocation()
                    + commOrders.get(i).getUserHome().getAddress());
            strEndIndex = commOrders.get(i).getUserHome().getCommunities().getLocation().indexOf("市");
            if (commOrders.get(i).getUserHome().getCommunities().getLocation().contains("省")) {
                strStartIndex = commOrders.get(i).getUserHome().getCommunities().getLocation().indexOf("省");
                jsonArray.getJSONObject(i).put("city", commOrders.get(i).getUserHome().getCommunities()
                        .getName().substring(strStartIndex, strEndIndex));
            } else {
                jsonArray.getJSONObject(i).put("city", commOrders.get(i).getUserHome().getCommunities()
                        .getName().substring(0, strEndIndex));
            }
            jsonArray.getJSONObject(i).put("order_type", commOrders.get(i).getOrderType().getOrder_type());
            jsonArray.getJSONObject(i).put("step", steps2);
            if (commOrders.get(i).getEngineers() == null) {
                jsonArray.getJSONObject(i).put("engineer_name", "");
            } else {
                jsonArray.getJSONObject(i).put("engineer_name", commOrders.get(i).getEngineers().getName());
            }
            jsonArray.getJSONObject(i).put("matter", commOrders.get(i).getFault().getFaultname());
            jsonArray.getJSONObject(i).put("user_phone", commOrders.get(i).getUser().getMobile());
//            jsonArray.getJSONObject(i).put("history", JSONArray.fromObject(JSON.toJSONString(orderStepRepository.
//                    findAllStepByOid(commOrders.get(i).getOid()))));
            jsonArray.getJSONObject(i).put("other_engineers", JSONArray.fromObject(JSON.toJSONString(jsonArray1)));
        }
        map.put("type", 1);
        map.put("msg", "成功");
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("order", jsonArray);
        jsonObject3.put("count", jsonObject2);
        map.put("data", jsonObject3);
        return JSONObject.fromObject(map);
    }

    /***
     * 按状态/角色查询所有工单(不分报警和维修)
     * @param jsonObject
     * @return
     */
    @PostMapping("/findAllComm")
    public JSONObject findAllComm(@RequestBody JSONObject jsonObject) {
        int eid, order_type, gid, finished, page, rid, type, strStartIndex, strEndIndex;
        JSONArray jsonArray;
        JSONObject jsonObject2 = new JSONObject();
        eid = Integer.parseInt(jsonObject.get("eid").toString());
        gid = Integer.parseInt(jsonObject.get("gid").toString());
        finished = Integer.parseInt(jsonObject.get("status_id").toString());
        page = Integer.parseInt(jsonObject.get("page_no").toString());
        rid = Integer.parseInt(jsonObject.get("rid").toString());
        type = Integer.parseInt(jsonObject.get("type").toString());
        List<CommOrders> commOrders = new ArrayList<CommOrders>();
        page = (page - 1) * 20;
        if (jsonObject.containsKey("order_type") && !jsonObject.get("order_type").toString().equals("")) {
            order_type = Integer.parseInt(jsonObject.get("order_type").toString());
            commOrders = commOrderRepository.findAllByEidAndGidByType(eid, gid, finished, order_type, page);//finished 0未接单 1已接单 2已完成
        }
        if (jsonObject.containsKey("today") && !jsonObject.get("today").toString().equals("")) {
            commOrders = commOrderRepository.findAllByEidAndFinished(eid, finished, page);//finished 0未接单 1已接单 2已完成
            jsonObject2.put("weekDelay", 0);
        } else if (jsonObject.containsKey("late") && !jsonObject.get("late").toString().equals("")) {
            try {
                commOrders = commOrderRepository.findAllBylate(Integer.parseInt(jsonObject.get("late").toString())
                        , getWeekMonday(), getWeekFriday(), eid, page);
                jsonObject2.put("weekDelay", commOrders.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            jsonObject2.put("weekDelay", 0);
            if (finished == 2) {
                commOrders = commOrderRepository.findRangByEidAndGid(eid, gid, finished, page);
            } else {
                commOrders = commOrderRepository.findAllByEidAndGid(eid, gid, finished, page);//finished 0未接单 1已接单 2已完成
            }
        }
        //type:1分配 0其他情况
        if (rid > 1) {
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
                    orderStepRepository.findAllByOidAndEid(commOrders.get(i).getOid())));
            if (steps.size() != 0) {
                steps2 = (JSONObject) steps.getJSONObject(0).get("orderStatus");
                acttime = steps.getJSONObject(0);
            }
            jsonArray.getJSONObject(i).put("community", commOrders.get(i).getUserHome().getCommunities().getName());
            jsonArray.getJSONObject(i).put("address", commOrders.get(i).getUserHome().getCommunities().getLocation()
                    + commOrders.get(i).getUserHome().getAddress());
            strEndIndex = commOrders.get(i).getUserHome().getCommunities().getLocation().indexOf("市");
            if (commOrders.get(i).getUserHome().getCommunities().getLocation().contains("省")) {
                strStartIndex = commOrders.get(i).getUserHome().getCommunities().getLocation().indexOf("省");
                jsonArray.getJSONObject(i).put("city", commOrders.get(i).getUserHome().getCommunities()
                        .getName().substring(strStartIndex, strEndIndex) + "市");
            } else {
                jsonArray.getJSONObject(i).put("city", commOrders.get(i).getUserHome().getCommunities()
                        .getName().substring(0, strEndIndex) + "市");
            }
            jsonArray.getJSONObject(i).put("order_type", commOrders.get(i).getOrderType().getOrder_type());
            jsonArray.getJSONObject(i).put("user_name", commOrders.get(i).getUser().getUname());
            jsonArray.getJSONObject(i).put("user_phone", commOrders.get(i).getUser().getMobile());
            jsonArray.getJSONObject(i).put("step", steps2.get("status_id"));
            jsonArray.getJSONObject(i).put("matter", commOrders.get(i).getFault().getFaultname());
            jsonArray.getJSONObject(i).put("history", JSONArray.fromObject(JSON.toJSONString(orderStepRepository.
                    findAllStepByOid(commOrders.get(i).getOid()), SerializerFeature.DisableCircularReferenceDetect)));
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
        map.put("data", jsonObject3);
        return JSONObject.fromObject(map);
    }

    //备注：后期要考虑finished和status_id字段重复
    @PostMapping("/changeStep")
    public JSONObject changeStep(@RequestBody JSONObject jsonObject) throws Exception {
        OrderStep orderStep;
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
        JSONObject post = new JSONObject();
        if (status_id == 1) {
            commOrderRepository.changeStep(oid, finished, eid);//finished 0未接单 1已接单 2已完成非评分 3已完成
            orderStep = new OrderStep(acttime, "工单已转接", orderStatus1, commOrders, engineers);
            orderStepRepository.save(orderStep);
            commOrderRepository.changeFinished(oid, finished, eid);
        } else {
            if (jsonObject.get("description").toString().equals("")) {
                orderStep = new OrderStep(acttime, "", orderStatus1, commOrders, engineers);

            } else {
                orderStep = new OrderStep(acttime,
                        jsonObject.get("description").toString(), orderStatus1, commOrders, engineers);
            }

            orderStepRepository.save(orderStep);
            commOrderRepository.changeFinished(oid, finished, eid);
//            if (finished == 2) {
//                List<CommOrders> commOder = commOrderRepository.findAllByCommOder(oid);
//                jsonObject.put("address", commOder.get(0).getUserHome().getAddress());
//                jsonObject.put("name", commOder.get(0).getUser().getUname());
//                jsonObject.put("mobile", commOder.get(0).getUser().getMobile());
//                jsonObject.put("oid", commOder.get(0).getOid());
//                jsonObject.put("order_type", commOder.get(0).getOrderType().getOt_name());
//                jsonObject.put("finished", 2);
//                jsonObject.put("uname", commOder.get(0).getEngineers().getName());
//                jsonObject.put("to", "ghn");
//                //webSocket(jsonObject);
//            }
        }
        map.put("type", 1);
        map.put("msg", "更改步骤成功");
        map.put("data", "");
        return JSONObject.fromObject(map);

    }

    @PostMapping("/getScore")
    public JSONObject getScoreByEid(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        int role = Integer.parseInt(jsonObject.get("role").toString());
        JSONObject mapObject = new JSONObject();
        boolean flag = false;
        //成绩
        //够且当前工程是没有在前十
        Seperate mycommOrders = seperateRepository.findScoreByEid(eid, 2);
        List<Seperate> commOrders = seperateRepository.findScoreByGid(gid, 2, role);
        JSONObject myjsonstars = JSONObject.fromObject(JSON.toJSONString(mycommOrders, SerializerFeature.WriteNullNumberAsZero));
        JSONArray allStarArray = JSONArray.fromObject(JSON.toJSONString(commOrders, SerializerFeature.WriteNullNumberAsZero));
        SeperateNum seperateNums = seperateNumRepository.findNumberByEid(eid, 2);
        JSONObject myjsonnumber = JSONObject.fromObject(JSON.toJSONString(seperateNums, SerializerFeature.WriteNullNumberAsZero));
        List<SeperateNum> seperateNums1 = seperateNumRepository.findNumberByGid(gid, 2, role);
        JSONArray allNumberArray = JSONArray.fromObject(JSON.toJSONString(seperateNums1, SerializerFeature.WriteNullNumberAsZero));
        sort(allNumberArray, "number", false);
        if (allStarArray.size() <= 10) {
            for (int i = 0; i < allStarArray.size(); i++) {
                if (allStarArray.getJSONObject(i).get("appraise_score") != null) {
                    Double score = Double.parseDouble(allStarArray.getJSONObject(i).get("appraise_score").toString());
                    allStarArray.getJSONObject(i).put("appraise_score", (score / 3) + "");
                } else {
                    allStarArray.getJSONObject(i).put("appraise_score", "0");
                }
            }
            mapObject.put("stars", allStarArray);
            mapObject.put("number", seperateNums1);
        } else {
            for (int i = 0; i < 10; i++) {
                if (allStarArray.getJSONObject(i).get("appraise_score") != null) {
                    Double score = Double.parseDouble(allStarArray.getJSONObject(i).get("appraise_score").toString());
                    allStarArray.getJSONObject(i).put("appraise_score", score / 3 + "");
                    if (Integer.parseInt(allStarArray.getJSONObject(i).get("eid").toString()) == eid) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                } else {
                    allStarArray.getJSONObject(i).put("appraise_score", "0");
                }
            }
            if (flag == false) {
                allStarArray.set(9, myjsonstars);
                allNumberArray.set(9, myjsonnumber);
                allStarArray.remove(10);
                allNumberArray.remove(10);
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
    public JSONObject transfer(@RequestBody JSONObject jsonObject) throws Exception {
        //对方
        int origin = Integer.parseInt(jsonObject.get("eid").toString());
        int oid = Integer.parseInt(jsonObject.get("oid").toString());
        //自己
        int eid = Integer.parseInt(jsonObject.get("originId").toString());
        JSONObject jsonObject1 = new JSONObject();
        commOrderRepository.transfer(eid, oid);
        List<OrderStep> orderSteps = orderStepRepository.findAllByOid(oid);
        Engineers engineers = new Engineers();
        engineers.setEid(origin);
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus_id(orderSteps.get(0).getOrderStatus().getStatus_id());
        CommOrders commOrders = new CommOrders();
        commOrders.setOid(oid);
        OrderStep orderStep = new OrderStep(date, "转单给" + orderSteps.get(0).getEngineers().getName(),
                orderStatus, commOrders, engineers, 0);
        orderStepRepository.save(orderStep);
//        jsonObject1.put("to", engineersRepository.findAllByEid(origin).get(0).getMobile());
//        jsonObject1.put("msg", engineersRepository.findAllByEid(eid).get(0).getName() + "转单给你");
//        //JsonUtils.getLoginSocket("engineer");
//        webSocket(jsonObject1);
        map.put("type", 1);
        map.put("msg", "分配成功");
        map.put("data", "");
        return JSONObject.fromObject(map);
    }

    @PostMapping(value = "/saveLocation")
    public synchronized JSONObject saveLocation(@RequestBody JSONObject jsonObject) {
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

    @PostMapping(value = "/findCommByOid")
    public JSONObject findCommByOid(@RequestBody JSONObject jsonObject) {
        int oid = (int) jsonObject.get("oid");
        List<CommOrders> commOrders = commOrderRepository.findAllByCommOder(oid);
        int strStartIndex, strEndIndex,rid=1;
        JSONArray jsonArray = new JSONArray();
        JSONObject steps2 = new JSONObject();
        JSONObject acttime = new JSONObject();
        //for (int i = 0; i < jsonArray.size(); i++) {
        String comm = JSON.toJSONString(commOrders);
        jsonArray = JSONArray.fromObject(comm);
        if(null!=commOrders.get(0).getEngineers()&&commOrders.get(0).getEngineers().getRoles().getRid()>1){
            rid = commOrders.get(0).getEngineers().getRoles().getRid()-1;
        }
        List<Engineers> engineers = engineersRepository.findAllByRidAndGid(
                rid,
                commOrders.get(0).getServiceShop().getGid(),
                commOrders.get(0).getEngineers().getEid());
        JSONArray steps = JSONArray.fromObject(JSON.toJSONString(
                orderStepRepository.findAllByOidAndEid(commOrders.get(0).getOid())));
        if (steps.size() != 0) {
            steps2 = (JSONObject) steps.getJSONObject(0).get("orderStatus");
            acttime = steps.getJSONObject(0);
        }
        System.out.println(commOrders.get(0));
        jsonArray.getJSONObject(0).put("community", commOrders.get(0).getUserHome().getCommunities().getName());
        jsonArray.getJSONObject(0).put("address", commOrders.get(0).getUserHome().getCommunities().getLocation()
                + commOrders.get(0).getUserHome().getAddress());
        strEndIndex = commOrders.get(0).getUserHome().getCommunities().getLocation().indexOf("市");
        if (commOrders.get(0).getUserHome().getCommunities().getLocation().contains("省")) {
            strStartIndex = commOrders.get(0).getUserHome().getCommunities().getLocation().indexOf("省");
            jsonArray.getJSONObject(0).put("city", commOrders.get(0).getUserHome().getCommunities()
                    .getName().substring(strStartIndex, strEndIndex) + "市");
        } else {
            jsonArray.getJSONObject(0).put("city", commOrders.get(0).getUserHome().getCommunities()
                    .getName().substring(0, strEndIndex) + "市");
        }
        jsonArray.getJSONObject(0).put("order_type", commOrders.get(0).getOrderType().getOrder_type());
        jsonArray.getJSONObject(0).put("user_name", commOrders.get(0).getUser().getUname());
        jsonArray.getJSONObject(0).put("user_phone", commOrders.get(0).getUser().getMobile());
        jsonArray.getJSONObject(0).put("step", steps2.get("status_id"));
        jsonArray.getJSONObject(0).put("matter", commOrders.get(0).getFault().getFaultname());
        jsonArray.getJSONObject(0).put("history", JSONArray.fromObject(JSON.toJSONString(orderStepRepository.
                findAllStepByOid(commOrders.get(0).getOid()), SerializerFeature.DisableCircularReferenceDetect)));
        if (acttime.containsKey("acttime")) {
            jsonArray.getJSONObject(0).put("acttime", acttime.get("acttime"));
        } else {
            jsonArray.getJSONObject(0).put("acttime", 0);
        }
        if (commOrders.get(0).getEngineers() == null) {
            jsonArray.getJSONObject(0).put("engineer_name", "");
        } else {
            jsonArray.getJSONObject(0).put("engineer_name", commOrders.get(0).getEngineers().getName());
        }
        jsonArray.getJSONObject(0).put("other_engineers", JSONArray.fromObject(JSON.toJSONString(engineers)));
        //}
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray.get(0));
        return JSONObject.fromObject(map);
    }

//    @PostMapping(value = "/was1/wdhome/zhinengjia/addhome")
//    public JSONObject addHome(@RequestBody JSONObject jsonObject) {
//        String mobile = jsonObject.get("mobile").toString();
//        int uid = userRepository.selectById(mobile);
//        String homename = jsonObject.get("homename").toString();
//        String address = jsonObject.get("address").toString();
//        User user = new User();
//        user.setUid(uid);
//        UserHome userHome = new UserHome();
//        userHome.setHomename(homename);
//        userHome.setAddress(address);
//        userHome.setUsers(user);
//        userHome.setDevs(new Devs("", ""));
//        userHomeRepository.save(userHome);
//        List<UserHome> userHomes = userHomeRepository.findAllByhomeId(uid);
//        System.err.println(JSON.toJSONString(userHomes));
//        userRoomRepository.addRoom("默认房间", 0, "", userHomes.get(userHomes.size() - 1).getHomeid());
//        UserHome userHome1 = new UserHome();
//        userHome1.setHomeid(userHomes.get(userHomes.size() - 1).getHomeid());
//        ScenceModel scenceModel = new ScenceModel();
//        scenceModel.setUserHome(userHome1);
//        scenceModel.setScenceName("默认模式");
//        scenceModel.setMsgStatus(0);
//        scenceModel.setAction("");
//        scenceModelRepository.save(scenceModel);
//        List<ScenceModel> scenceModels = scenceModelRepository.findAll();
//        List<UserRoom> userRooms = userRoomRepository.findAll();
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("homeid", userHomes.get(userHomes.size() - 1).getHomeid());
//        jsonObject2.put("roomid", userRooms.get(userRooms.size() - 1).getRoomid());
//        jsonObject2.put("smid", scenceModels.get(scenceModels.size() - 1).getSmid());
//        LinkedHashMap map = new LinkedHashMap();
//        map.put("type", 1);
//        map.put("msg", "正常");
//        map.put("data", jsonObject2);
//        JSONObject jsonObject1 = JSONObject.fromObject(map);
//        return jsonObject1;
//    }


    /**
     * 每秒刷新更新late（未用到）
     *
     * @param jsonObject
     */
    @PostMapping(value = "/updateLate")
    @CrossOrigin
    public void updateLate(@RequestBody JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        int step1, step2, step3, step4;
        for (int i = 0; i < jsonArray.size(); i++) {
            step1 = Integer.parseInt(jsonArray.getJSONObject(i).get("step1_timeout").toString());
            step2 = Integer.parseInt(jsonArray.getJSONObject(i).get("step2_timeout").toString());
            step3 = Integer.parseInt(jsonArray.getJSONObject(i).get("step3_timeout").toString());
            step4 = Integer.parseInt(jsonArray.getJSONObject(i).get("step4_timeout").toString());
            if (step1 == 1) {
                orderStepRepository.updateLate(Integer.parseInt(jsonArray.getJSONObject(i).get("step_id").toString()),
                        Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()), 1);
            } else if (step2 == 1) {
                commOrderRepository.updateCommLate(Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()));
                orderStepRepository.updateLate(Integer.parseInt(jsonArray.getJSONObject(i).get("step_id").toString()),
                        Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()), 1);
            } else if (step3 == 1) {
                commOrderRepository.updateCommLate(Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()));
                orderStepRepository.updateLate(Integer.parseInt(jsonArray.getJSONObject(i).get("step_id").toString()),
                        Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()), 3);
            } else if (step4 == 1) {
                commOrderRepository.updateCommLate(Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()));
                orderStepRepository.updateLate(Integer.parseInt(jsonArray.getJSONObject(i).get("step_id").toString()),
                        Integer.parseInt(jsonArray.getJSONObject(i).get("oid").toString()), 4);
            }
        }
    }


    /**
     * 得到本周周一日期
     *
     * @return
     */
    public static Date getWeekMonday() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date first = cal.getTime();
        return first;
    }

    /**
     * 得到本周周六日期
     *
     * @return
     */
    public static Date getWeekFriday() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        Date last = cal.getTime();
        return last;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static Date getMonthFirstday() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = formater.format(c.getTime());
        return c.getTime();
    }

    public static Date getMonthLast() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = formater.format(ca.getTime());
        return ca.getTime();
    }

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

    @GetMapping(value = "/findAllCommOder")
    @CrossOrigin
    public synchronized JSONObject findAllCommOder() {
        List<CommOrders> commOrders = commOrderRepository.findAll();
        JSONArray jsonArray = JSONArray.fromObject(JSON.toJSONString(commOrders));
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
            jsonObject.put("type", commOrders.get(i).getOrderType().getOrder_type());
            jsonObject.put("start_time", sf.format(commOrders.get(i).getOrder_time().getTime()));
            if (commOrders.get(i).getEngineers() != null) {
                jsonObject.put("engineers", commOrders.get(i).getEngineers().getName());
            } else {
                jsonObject.put("engineers", "");
            }
            jsonObject.put("fault", commOrders.get(i).getFault().getFaultname());
            if (commOrders.get(i).getEnd_time() != null) {
                jsonObject.put("end_time", sf.format(commOrders.get(i).getEnd_time().getTime()));
            } else {
                jsonObject.put("end_time", "正在处理中");
            }
            jsonArray1.add(jsonObject);
        }
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray1);
        return JSONObject.fromObject(map);
    }

    @GetMapping(value = "/findAllGroupByFault")
    @CrossOrigin
    public synchronized JSONObject findAllGroupByFault() {
        List<CountFault> countList = countFaultRepository.findCountByFault();
        JSONArray jsonArray1 = new JSONArray();
        int id;
        for (int j = 0; j < countList.size(); j++) {
            id = countList.get(j).getFault_id();
            jsonArray1.add(commOrderRepository.findAllByFault(id).get(0).getFault().getFaultname());
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < countList.size(); i++) {
            jsonArray2.add(countList.get(i));
        }
        jsonObject.put("type", jsonArray1);
        jsonObject.put("count", jsonArray2);
        jsonArray.add(jsonObject);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", JSONArray.fromObject(JSON.toJSONString(jsonArray)));
        return JSONObject.fromObject(map);
    }

    @GetMapping(value = "/findAllGroupByTime")
    @CrossOrigin
    public synchronized JSONObject findAllGroupByTime() {
        List<CommOrders> countList = commOrderRepository.findAll();
        List<CountFault> countFaults = countFaultRepository.findCountByFault();
        Map<String, List<JsonForScreen>> hashmap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String[] dateList = {"04:00:00", "08:00:00", "12:00:00", "16:00:00", "20:00:00", "24:00:00"};
        List<JsonForScreen> list = new ArrayList<>();
        int[] count = new int[6];
        for (int i = 0; i < countList.size(); i++) {//判断是否到结束日期
            if (isInTime("00:00-04:00", sdf.format(countList.get(i).getOrder_time().getTime()))) {
                count[0]++;
            } else if (isInTime("04:00-08:00", sdf.format(countList.get(i).getOrder_time().getTime()))) {
                count[1]++;
            } else if (isInTime("08:00-12:00", sdf.format(countList.get(i).getOrder_time().getTime()))) {
                count[2]++;
            } else if (isInTime("12:00-16:00", sdf.format(countList.get(i).getOrder_time().getTime()))) {
                count[3]++;
            } else if (isInTime("16:00-20:00", sdf.format(countList.get(i).getOrder_time().getTime()))) {
                count[4]++;
            } else {
                count[5]++;
            }
        }
        for (int j = 0; j < 6; j++) {
            list.add(new JsonForScreen(dateList[j], count[j]));
        }
        hashmap.put("all", list);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", JSONArray.fromObject(hashmap));
        return JSONObject.fromObject(map);
    }


    @GetMapping(value = "/findPercent")
    @CrossOrigin
    public synchronized JSONObject findPercent() {
        List<CountFault> countList = countFaultRepository.findCountByFault();
        JSONArray jsonArray1 = new JSONArray();
        int id;
        for (int j = 0; j < countList.size(); j++) {
            id = countList.get(j).getFault_id();
            jsonArray1.add(commOrderRepository.findAllByFault(id).get(0).getFault().getFaultname());
        }
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(1);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        int sum = 0;
        for (int j = 0; j < countList.size(); j++) {
            sum = sum + countList.get(j).getCount();
        }
        System.out.println(sum);
        for (int i = 0; i < countList.size(); i++) {
            jsonArray2.add(Double.parseDouble((countList.get(i).getCount() * 100 / sum) + ""));
        }
        jsonObject.put("type", jsonArray1);
        jsonObject.put("count", jsonArray2);
        jsonArray.add(jsonObject);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray);
        return JSONObject.fromObject(map);
    }


    public static void webSocket(JSONObject message) throws Exception {
        /***
         * 用户认证
         */
        JSONObject openJson = new JSONObject();
        openJson.put("wstype", "logon");
        openJson.put("type", "logon");
        openJson.put("time", new Date());
        openJson.put("version", "1.0");
        openJson.put("from", "engineer");
        openJson.put("fname", "engineer");
        openJson.put("to", "logon");
        openJson.put("tname", "logon");
        JSONObject openJsonObject = new JSONObject();
        openJsonObject.put("name", "engineer");
        openJsonObject.put("type", "add");
        openJsonObject.put("uid", "engineer");
        openJsonObject.put("token", "system");
        openJson.put("logon", openJsonObject);
        /***
         * 发送消息
         */
        JSONObject MsgJson = new JSONObject();
        MsgJson.put("wstype", "info");
        MsgJson.put("type", "invite");
        MsgJson.put("version", "1.0");
        MsgJson.put("from", "engineer");
        MsgJson.put("tname", message.get("to").toString());
        MsgJson.put("to", message.get("to").toString());
        JSONObject info = new JSONObject();
        info.put("content", message.get("msg").toString());
// info.put("homename", homename);
        MsgJson.put("info", info);
// 所有连接协议
        DraftInfo[] draftInfos = {
                new DraftInfo("Draft_17", new Draft_17()),
                new DraftInfo("Draft_10", new Draft_10()),
                new DraftInfo("Draft_76", new Draft_76()),
                new DraftInfo("Draft_75", new Draft_75())};
        selectDraft = draftInfos[0];
        client = new WebSocketClient(new URI("ws://192.168.200.104:8080"), selectDraft.draft) {
            @Override
            public void onOpen(final ServerHandshake serverHandshakeData) {

                System.out.println("wlf：" + "已经连接到服务器【" + getURI() + "】");
                send(openJson.toString()); //认证：自己的手机号 uid：自己的手机号
                //FORM:自己的手机号，tname：别人的手机号 to：别人的手机号);
            }

            @Override
            public void onMessage(final String message) {
                System.out.println("wlf：" + "获取到服务器信息【" + message + "】");
//                client.send(MsgJson.toString());
            }

            @Override
            public void onClose(final int code, final String reason, final boolean remote) {
                System.out.println("wlf：" + "断开服务器连接【" + getURI() + "，状态码： ");
            }

            @Override
            public void onError(Exception e) {

            }


        };
        client.connect();
        Thread.sleep(2000);
        client.send(MsgJson.toString());
       // Thread.sleep(1000);

    }
}
