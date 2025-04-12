package com.example.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class SummaryController {

    static class SummaryRequest {
        public String url;
    }

    static class SummaryResponse {
        public String summary;
        public SummaryResponse(String summary) {
            this.summary = summary;
        }
    }

    static class HistoryEntry {
        public String url;
        public String summary;
        public HistoryEntry(String url, String summary) {
            this.url = url;
            this.summary = summary;
        }
    }

    private final List<HistoryEntry> history = new ArrayList<>();

    @PostMapping("/summarize")
    public SummaryResponse summarize(@RequestBody SummaryRequest request) {
        String summary = callPythonSummarizer(request.url);
        history.add(new HistoryEntry(request.url, summary));
        return new SummaryResponse(summary);
    }

    private String callPythonSummarizer(String text) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8000/summarize";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("text", text);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            return response.getBody().get("summary").toString();
        } catch (Exception e) {
            return "Error calling Python summarizer: " + e.getMessage();
        }
    }

    @GetMapping("/history")
    public List<HistoryEntry> getHistory() {
        return history;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
