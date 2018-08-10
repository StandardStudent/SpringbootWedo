package com.weido.work.service;

import com.weido.work.pojo.SceneMine;
import com.weido.work.repository.SceneMineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneMineService {
    @Autowired
    SceneMineRepository sceneMineRepository;
    public List<SceneMine> findAllSceneMine(){
        List<SceneMine> sceneMineList = sceneMineRepository.findAll();
        return sceneMineList;
    }
    public List<SceneMine> findSceneMineByHomeId(int homeId){
        List<SceneMine> sceneMineList = sceneMineRepository.
                findSceneMineByHomeId(homeId);
        return sceneMineList;
    }
    public List<SceneMine> findAllByUid(int uid){
        List<SceneMine> sceneDefaultList = sceneMineRepository.findAllByUid(uid);
        return sceneDefaultList;
    }
}
