package com.weido.work.service;

import com.weido.work.pojo.RoomToSensor;
import com.weido.work.pojo.Sensor;
import com.weido.work.pojo.SensorType;
import com.weido.work.repository.RSRepository;
import com.weido.work.repository.SensorRepository;
import com.weido.work.repository.SensorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    RSRepository rsRepository;
    @Autowired
    SensorTypeRepository sensorTypeRepository;
    public List<Sensor> findAllSensor(){
        List<Sensor> sensorList= sensorRepository.findAll();
        return sensorList;
    }
//    public List<Sensor> findSensorByRId(){
//        List<Sensor> sensorList = sensorRepository.findSensorByRId();
//        return sensorList;
//    }
    public int findIdBytypeName(String typeName){
        int id = sensorTypeRepository.findIdByName(typeName);
        return id;
    }
    public void addSensor(Sensor sensor){
        sensorRepository.save(sensor);
    }
    public List<Sensor> findAllByHomeId(int homeid){
       List<Sensor> sensorList = sensorRepository.findAllByHomeId(homeid);
       return sensorList;
    }
    public List<Sensor> findAllBySID(String sid,int homeid){
       List<Sensor> sensorList =  sensorRepository.findAllBySID(sid,homeid);
       return sensorList;
    }
    public List<Sensor> findAllByRoomid(String roomid){
        List<Sensor> sensorList = sensorRepository.findAllByRoomid(roomid);
        return sensorList;
    }

}
