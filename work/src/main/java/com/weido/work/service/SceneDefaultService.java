package com.weido.work.service;

import com.weido.work.pojo.SceneDefault;
import com.weido.work.repository.SceneDefaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneDefaultService {
    @Autowired
    SceneDefaultRepository sceneDefaultRepository;
    public List<SceneDefault> findAllSceneDefault(){
       List<SceneDefault> sceneDefaultList =  sceneDefaultRepository.findAll();
       return sceneDefaultList;
    }
    public List<SceneDefault> findSceneDefaultByHomeId(int homeid){
        List<SceneDefault> sceneDefaultList = sceneDefaultRepository
                .findSceneDefaultByHomeId(homeid);
        return sceneDefaultList;
    }
    public List<SceneDefault> findAllByUid(int uid){
        List<SceneDefault> sceneDefaultList = sceneDefaultRepository.findAllByUid(uid);
        return sceneDefaultList;
    }
}
