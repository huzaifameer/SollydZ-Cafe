package com.huzaifa.cafe.sollydz.restImpl;

import com.huzaifa.cafe.sollydz.constants.CafeConstants;
import com.huzaifa.cafe.sollydz.jwt.JwtFilter;
import com.huzaifa.cafe.sollydz.rest.UserRest;
import com.huzaifa.cafe.sollydz.service.UserService;
import com.huzaifa.cafe.sollydz.utils.CafeUtil;
import com.huzaifa.cafe.sollydz.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserRestImpl implements UserRest {
    final UserService userService;
    final JwtFilter jwtFilter;

    public UserRestImpl(UserService userService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try{
            return userService.signup(requestMap);
        }catch (Exception ex){
            log.warn("Error :",ex);
        }
        return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            return userService.getAllUsers();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateData(Map<String,String> requestMap) {
        try {
            log.info("User request: {}", requestMap);
            return userService.updateData(requestMap);
        } catch (Exception e) {
            log.error("Error while updating user data", e);
            return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
