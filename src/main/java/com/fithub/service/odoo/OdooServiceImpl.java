package com.fithub.service.odoo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OdooServiceImpl implements OdooService{
    private final RestTemplate restTemplate;
    private String sessionCookie;

    public void authenticate(String url, String db, String login, String password) {
        String authUrl = url + "/web/session/authenticate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("jsonrpc", "2.0");
        requestBody.put("method", "call");
        requestBody.put("params", Map.of("db", db, "login", login, "password", password));
        requestBody.put("id", 1);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.POST, requestEntity, String.class);

        // Extract session cookie from response
        sessionCookie = response.getHeaders().getFirst("Set-Cookie");
    }

    public String getRecords(String url, String model, String[] fields) {
        String dataUrl = url + "/web/dataset/call_kw";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", sessionCookie);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("jsonrpc", "2.0");
        requestBody.put("method", "call");
        requestBody.put("params", Map.of(
                "model", model,
                "method", "search_read",
                "args", new Object[]{new Object[]{}, fields},
                "kwargs", new HashMap<>()
        ));
        requestBody.put("id", 1);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(dataUrl, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }
}
