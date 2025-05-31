package com.oauth2proj.utils.security;

import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenTools {
    ObjectMapper objectMapper = new ObjectMapper();

    public String createJWT(){
        try {
            String base64Header = Base64.getEncoder().encodeToString(createHeader().getBytes());
            String base64Payload = Base64.getEncoder().encodeToString(createPayload().getBytes());

            RsaTools rsaTools = new RsaTools();
            String toSign = base64Header + "." + base64Payload;
            String base64UrlSignature = rsaTools.signToken(toSign);

            return toSign + "." + base64UrlSignature;


        } catch (Exception e) {
            
            return "";
        }
    }   
    
    private String createHeader() throws JsonProcessingException {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "RS256");
        headerMap.put("typ", "JWT");

        return this.objectMapper.writeValueAsString(headerMap);
    }

    private String createPayload() throws JsonProcessingException {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("username", "RS256");
        payloadMap.put("targetSystem", "example");
        payloadMap.put("exp", Instant.now().getEpochSecond() + 3600);
        payloadMap.put("iat", Instant.now().getEpochSecond());
        payloadMap.put("role", "user");

        return this.objectMapper.writeValueAsString(payloadMap);

    }
    
}
