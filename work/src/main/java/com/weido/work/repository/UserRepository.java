package com.weido.work.repository;

import com.weido.work.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    //通过手机号查询用户
    @Query(value = "select u.* from users u where mobile=?1",nativeQuery = true)
    User findUserByMobile(String mobile);
}
