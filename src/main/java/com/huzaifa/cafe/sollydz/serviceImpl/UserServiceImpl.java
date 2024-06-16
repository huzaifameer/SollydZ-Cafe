package com.huzaifa.cafe.sollydz.serviceImpl;

import com.huzaifa.cafe.sollydz.constants.CafeConstants;
import com.huzaifa.cafe.sollydz.dao.UserDao;
import com.huzaifa.cafe.sollydz.jwt.CustomerUserDetailsService;
import com.huzaifa.cafe.sollydz.jwt.JwtFilter;
import com.huzaifa.cafe.sollydz.jwt.JwtUtil;
import com.huzaifa.cafe.sollydz.pojo.User;
import com.huzaifa.cafe.sollydz.service.UserService;
import com.huzaifa.cafe.sollydz.utils.CafeUtil;
import com.huzaifa.cafe.sollydz.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    final UserDao userDao;
    final JwtUtil jwtUtil;
    final AuthenticationManager authenticationManager;
    final CustomerUserDetailsService customerUserDetailsService;
    final JwtFilter jwtFilter;

    public UserServiceImpl(CustomerUserDetailsService customerUserDetailsService, UserDao userDao, JwtUtil jwtUtil, AuthenticationManager authenticationManager, JwtFilter jwtFilter) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {

        log.info("Inside Signup {}",requestMap);
        try {
            if (validateSignupMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtil.getResponseEntity("Signup is successfully completed...!", HttpStatus.OK);
                } else {
                    return CafeUtil.getResponseEntity("Email Already exist..!", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtil.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login...");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getRole())+ "\"}",
                    HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            log.error("{}",e);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUsers(),HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateData(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (optional.isPresent()){
                    log.info("User: ",optional);
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return CafeUtil.getResponseEntity("User status updated successfully....!",HttpStatus.OK);
                }else {
                    return CafeUtil.getResponseEntity("User ID Doesn't exist",HttpStatus.OK);
                }
            }else{
                return CafeUtil.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignupMap(Map<String, String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")
                && requestMap.containsKey("password")){
            return true;
        }else{
            return false;
        }
    }
    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
