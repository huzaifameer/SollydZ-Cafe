package com.huzaifa.cafe.sollydz.restImpl;

import com.huzaifa.cafe.sollydz.constants.CafeConstants;
import com.huzaifa.cafe.sollydz.rest.UserRest;
import com.huzaifa.cafe.sollydz.service.UserService;
import com.huzaifa.cafe.sollydz.utils.CafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try{
            return userService.signup(requestMap);
        }catch (Exception ex){
            log.warn("Error :",ex);
        }
        return CafeUtil.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
