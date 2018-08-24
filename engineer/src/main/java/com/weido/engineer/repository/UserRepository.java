package com.weido.engineer.repository;

import com.weido.engineer.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select u.* from users u where mobile = ?1",nativeQuery = true)
    List<User> findByMobile(String mobile);

    @Query(value = "select u.uid from users u where mobile=?1",nativeQuery = true)
    int selectById(String mobile);
}
