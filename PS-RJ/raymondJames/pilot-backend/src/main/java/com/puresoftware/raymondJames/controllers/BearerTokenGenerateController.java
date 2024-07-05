package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Description: This service will generate the bearer token.
 * Usage: Embed this service at the time of token generation for an API.
 */

@RestController
@ControllerAdvice
public class BearerTokenGenerateController {

    @Autowired
    BearerTokenGeneratorConfig bearerTokenGeneratorConfig;

    @SneakyThrows
    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken() {
        String response = bearerTokenGeneratorConfig.generateBearerToken();
        return ResponseEntity.ok(response);
    }
}
