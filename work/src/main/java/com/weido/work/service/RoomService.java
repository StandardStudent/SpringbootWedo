package com.weido.work.service;

import com.weido.work.pojo.*;
import com.weido.work.repository.CityRepository;
import com.weido.work.repository.HomeRepository;
import com.weido.work.repository.RoomRepository;
import com.weido.work.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HomeRepository homeRepository;
    @Autowired
    CityRepository cityRepository;
    public List<UserRoom> findAllRoom(){
        List<UserRoom> list = roomRepository.findAll();
        return list;
    }
    public void addRoom(UserRoom userRoom){
        roomRepository.save(userRoom);
    }
    public User findUser(int uid){
        User u = userRepository.getOne(uid);
        return u;
    }
    public List<UserHome> findAllHome(){
        List<UserHome> homelist = homeRepository.findAll();
        return homelist;
    }
    public List<UserHome> findHomeByUidAndCid(int uid,int cid){
        List<UserHome> homeList = homeRepository.findHomeByUidAndCid(uid,cid);
        return homeList;
    }
    public User findUserByMobile(String mobile){
        User user = userRepository.findUserByMobile(mobile);
        return user;
    }
    public City findCityByCityName(String cityName){
        City city = cityRepository.findAllByCity_name(cityName);
        return city;
    }
    public void leaveHome(int enable,int homeid){
        roomRepository.leaveHome(enable,homeid);
    }
    public List<UserRoom> findAllRoomByHomeId(int homeid){
        List<UserRoom> userRoomList = roomRepository.findAllByhoomid(homeid);
        return userRoomList;
    }
    public List<UserRoom> findAllRoomByHomeIdAndUid(int homeid,int uid){
        List<UserRoom> userRoomList = roomRepository.
                findAllByhomeidAndUid(homeid,uid);
        return userRoomList;
    }
    public List<UserRoom> findAllByUid(int uid){
        List<UserRoom> userRoomList = roomRepository.findAllByUid(uid);
        return userRoomList;
    }

}
