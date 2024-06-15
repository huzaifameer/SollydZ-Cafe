package com.huzaifa.cafe.sollydz.jwt;

import com.huzaifa.cafe.sollydz.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.huzaifa.cafe.sollydz.pojo.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = userDao.findByEmailId(username);
        if (!Objects.isNull(userDetail))
            return new User(userDetail.getEmail(), userDetail.getPassword(),new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found..!");
    }
}
