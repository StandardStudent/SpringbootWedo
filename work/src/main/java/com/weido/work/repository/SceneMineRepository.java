package com.weido.work.repository;

import com.weido.work.pojo.SceneMine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SceneMineRepository extends JpaRepository<SceneMine,Integer> {
    @Query(value = "select sm.* from scene_mine sm where user_home_homeid=?1",
            nativeQuery = true)
    List<SceneMine> findSceneMineByHomeId(int homeid);
    @Query(value = "select sm.* from scene_mine sm,user_home h" +
            " where user_home_homeid=h.homeid and" +
            " h.users_uid=?1",nativeQuery = true)
    List<SceneMine> findAllByUid(int uid);
}
