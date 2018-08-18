package com.weido.engineer.controller;

import com.alibaba.fastjson.JSON;
import com.weido.engineer.pojo.CommOrders;
import com.weido.engineer.pojo.Engineers;
import com.weido.engineer.repository.CommOrderRepository;
import com.weido.engineer.repository.EngineersRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    CommOrderRepository commOrderRepository;
    @Autowired
    EngineersRepository engineersRepository;
    LinkedHashMap map = new LinkedHashMap();
    LinkedHashMap datemap = new LinkedHashMap();

    @PostMapping("/homePage")
    public JSONObject findOderByTime(@RequestBody JSONObject jsonObject) {
        try {
            int eid = Integer.parseInt(jsonObject.get("eid").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(jsonObject.get("order_time").toString());
            int role = Integer.parseInt(jsonObject.get("role").toString());
            List<CommOrders> day = commOrderRepository.findAllByEidAndCurrentTime(eid, date);
            int daySize = day.size();
            getWeekByDate(date);
            String Monday = datemap.get("Monday").toString();
            String Friday = datemap.get("Friday").toString();
            String first = datemap.get("first").toString();
            String last = datemap.get("last").toString();
            Date Mondaydate = sdf.parse(Monday);
            Date Fridaydate = sdf.parse(Friday);
            Date firstdate = sdf.parse(first);
            Date lastdate = sdf.parse(last);
            List<CommOrders> week = commOrderRepository.findWMByEidAndCurrent(eid, Mondaydate, Fridaydate);
            List<CommOrders> month = commOrderRepository.findWMByEidAndCurrent(eid, firstdate, lastdate);
            String dayString = JSON.toJSONString(day);
            String weekString = JSON.toJSONString(week);
            String monthString = JSON.toJSONString(month);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("day", dayString);
            jsonObject1.put("week", weekString);
            jsonObject1.put("month", monthString);
            if (role == 1) {
                jsonObject1.put("orderSize", 2);
                jsonObject1.put("giveSize", 3);
                jsonObject1.put("daySize", daySize);
            } else {
                //2表示未完成
                List<CommOrders> commOrders = commOrderRepository.findAllByEid(eid, 2);
                int comm = commOrders.size();
                jsonObject1.put("daySize", "");
                jsonObject1.put("giveSize", comm);
            }
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
        return JSONObject.fromObject(map);
    }

    /***
     * 按状态/角色查询所有工单(分报警和维修)
     * @param jsonObject
     * @return
     */
    @PostMapping("/findAllCommByType")
    public JSONObject findAllCommBytype(@RequestBody JSONObject jsonObject) {
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        int finished = Integer.parseInt(jsonObject.get("status_id").toString());
        int order_type = Integer.parseInt(jsonObject.get("order_type").toString());
        List<CommOrders> commOrders = commOrderRepository.findAllByEidAndGidByType(eid, gid, finished,order_type);//finished 0未接单 1已接单 2已完成
        List<Engineers> engineers = engineersRepository.findAllByGid(gid);
        String comm = JSON.toJSONString(commOrders);
        JSONArray jsonArray = JSONArray.fromObject(comm);
        jsonArray.add(engineers);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray);
        return JSONObject.fromObject(map);
    }

    /***
     * 按状态/角色查询所有工单(不分报警和维修)
     * @param jsonObject
     * @return
     */
    @PostMapping("/findAllComm")
    public JSONObject findAllComm(@RequestBody JSONObject jsonObject){
        int eid = Integer.parseInt(jsonObject.get("eid").toString());
        int gid = Integer.parseInt(jsonObject.get("gid").toString());
        int finished = Integer.parseInt(jsonObject.get("status_id").toString());
        List<CommOrders> commOrders = commOrderRepository.findAllByEidAndGid(eid, gid, finished);//finished 0未接单 1已接单 2已完成
        String comm = JSON.toJSONString(commOrders);
        JSONArray jsonArray = JSONArray.fromObject(comm);
        map.put("type", 1);
        map.put("msg", "成功");
        map.put("data", jsonArray);
        return JSONObject.fromObject(map);
    }

    /***
     * 更改接单类型
     * @param jsonObject
     * @return
     */
    @PostMapping("/changeType")
    public void changeType(@RequestBody JSONObject jsonObject){
        int oid = Integer.parseInt(jsonObject.get("oid").toString());
        int finished = Integer.parseInt(jsonObject.get("finished").toString());
        commOrderRepository.changeFinished(oid,finished);//finished 0未接单 1已接单 2已完成
        map.put("type", 1);
        map.put("msg", "成功");
    }

    @PostMapping("/changeStep")
    public void changeStep(@RequestBody JSONObject jsonObject){
        int oid = Integer.parseInt(jsonObject.get("oid").toString());
        int step = Integer.parseInt(jsonObject.get("step").toString());
        commOrderRepository.changeStep(oid,step);//finished 0未接单 1已接单 2已完成
        map.put("type", 1);
        map.put("msg", "成功");
    }
//    @PostMapping("/todayMission")
//    public JSONObject findAllCommByEidAndGid(@RequestBody JSONObject jsonObject) {
//
//            int eid = Integer.parseInt(jsonObject.get("eid").toString());
//            int gid = Integer.parseInt(jsonObject.get("gid").toString());
//            int finished = Integer.parseInt(jsonObject.get("status_id").toString());
//            int order_type = Integer.parseInt(jsonObject.get("order_type").toString());
//            List<CommOrders> commOrders = commOrderRepository.findAllByEidAndGid(eid,gid,finished,order_type);//finished 0未接单 1已接单 2已完成
//            String commString = JSON.toJSONString(commOrders);
//            JSONArray jsonArray = JSONArray.fromObject(commString);
//            map.put("type",1);
//            map.put("msg","成功");
//            map.put("data",jsonArray);
//        return JSONObject.fromObject(map);
//    }



}
