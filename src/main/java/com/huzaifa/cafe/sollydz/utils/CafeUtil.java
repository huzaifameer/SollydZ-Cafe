package com.huzaifa.cafe.sollydz.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtil {
    private CafeUtil(){

    }
    public static ResponseEntity<String> getResponseEntity(String resMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\":\""+resMessage+"\"}", httpStatus);
    }
}
