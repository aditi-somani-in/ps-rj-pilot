package com.puresoftware.raymondJames.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puresoftware.raymondJames.pojo.BearerTokenGeneratorDetails;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.GlobalUtils.GlobalUtilHeaders.ACCESSTOKEN;
import static com.puresoftware.raymondJames.utils.GlobalUtils.GlobalUtilHeaders.CONTENT_TYPE;

@Service
@Slf4j
public class BearerTokenGeneratorConfig {
    private static Logger logger = LoggerFactory.getLogger(BearerTokenGeneratorConfig.class);

    @Value("${bearer.token.url}")
    private String bearerTokenUrl;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${grant.type}")
    private String grantType;

    @Value("${x.www.form.urlencoded}")
    private String contentTypeEncoded;

    @SneakyThrows
    public String generateBearerToken() {

        logger.debug("Generating Bearer Token....!!");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> tokenRequestBody = new LinkedMultiValueMap<String, String>();

        HttpEntity request = new HttpEntity(tokenRequestBody, headers);
        BearerTokenGeneratorDetails.BearerTokenGeneratorResponse bearerTokenGeneratorResponse = new BearerTokenGeneratorDetails.BearerTokenGeneratorResponse();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(GRANT_TYPE,grantType);
        headers.set(CLIENT_ID, clientId);
        headers.set(CLIENT_SECRET_KEY,clientSecret);
        headers.set(CONTENT_TYPE,contentTypeEncoded);
        tokenRequestBody.add(GRANT_TYPE, grantType);
        tokenRequestBody.add(CLIENT_ID, clientId);
        tokenRequestBody.add(CLIENT_SECRET_KEY,clientSecret);

        ResponseEntity<String> response = restTemplate.exchange(bearerTokenUrl, HttpMethod.POST, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode readingResponse = objectMapper.readTree(response.getBody());
        bearerTokenGeneratorResponse.setAccessToken(readingResponse.get(ACCESSTOKEN).asText());
        try {
            System.out.println(bearerTokenGeneratorResponse.getAccessToken());
            return bearerTokenGeneratorResponse.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
