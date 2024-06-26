package com.huzaifa.cafe.sollydz.dao;

import com.huzaifa.cafe.sollydz.pojo.User;
import com.huzaifa.cafe.sollydz.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUsers();
    List<String> getAllAdmins();

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
}
