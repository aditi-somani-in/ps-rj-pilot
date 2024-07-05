package com.puresoftware.raymondJames.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.GlobalUtils.GlobalUtilHeaders.*;

@Service
public class HeaderConfig {

    @Autowired
    BearerTokenGeneratorConfig bearerTokenGeneratorConfig;

    @Value("${application.json}")
    private String applicationJson;

    @SneakyThrows
    public HttpHeaders addHeadersValue() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorConfig.generateBearerToken());
        return headers;
    }



}
