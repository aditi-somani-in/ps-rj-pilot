package com.puresoftware.raymondJames.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConfig {

    public ResponseEntity<String> ResponseOutput(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
