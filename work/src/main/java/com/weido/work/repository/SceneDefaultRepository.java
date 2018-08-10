package com.weido.work.repository;

import com.weido.work.pojo.SceneDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SceneDefaultRepository extends JpaRepository<SceneDefault,Integer> {
    @Query(value = "select sd.* from scene_default sd where user_home_homeid=?1"
            ,nativeQuery = true)
    List<SceneDefault> findSceneDefaultByHomeId(int homeid);

    @Query(value = "select sd.* from scene_default sd,user_home h" +
            " where user_home_homeid=h.homeid and" +
            " h.users_uid=?1",nativeQuery = true)
    List<SceneDefault> findAllByUid(int uid);
}
