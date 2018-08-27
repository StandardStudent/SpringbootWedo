package com.weido.engineer.controller;

import com.weido.engineer.pojo.SensorLog;
import com.weido.engineer.repository.OrderAlertRepository;
import com.weido.engineer.repository.SensorLogRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OderAlertController {
    @Autowired
    SensorLogRepository sensorLogRepository;
    @Autowired
    OrderAlertRepository orderAlertRepository;
    @PostMapping(value = "/changeFinished")
    @CrossOrigin
    public void changeFinished(@RequestBody JSONObject jsonObject){
        int logId = Integer.parseInt(jsonObject.get("log_id").toString());
        String findId = sensorLogRepository.findAllByLog_id(logId);
        if (findId!=null){
            orderAlertRepository.changeFinishedByAlertId(Integer.parseInt(findId));
        }
        //        for(int i=0;i<sensorLogs.size();i++){
//            int alert_id=sensorLogs.get(0).getOrderAlert().getAlert_id();
//            System.out.println(alert_id);

//        }
    }
}
