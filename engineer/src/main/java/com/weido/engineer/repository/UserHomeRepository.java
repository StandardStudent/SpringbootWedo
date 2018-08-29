package com.weido.engineer.repository;

import com.weido.engineer.pojo.UserHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserHomeRepository extends JpaRepository<UserHome,Integer> {

    @Query(value = "select h.* from user_home h where users_uid=?1",nativeQuery = true)
    List<UserHome> findAllByhomeId(int homeid);

}
