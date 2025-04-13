package com.example.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Summary;
import com.example.demo.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.utils.TextCleaner;
import com.example.utils.DBLogger;

import java.util.*;

@RestController
public class SummaryController {
    
    @Autowired
    private SummaryRepository summaryRepository;

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
        // Temporary test
        System.out.println("Before Scala call");
        String cleaned = TextCleaner.cleanText("Check Scala@123!");
        System.out.println("After Scala call: " + cleaned);
        // DBLogger.logSummary("https://example.com", "This is a test summary.");

        String summaryText = callPythonSummarizer(request.url);

        Summary summary = new Summary(request.url, summaryText);
        summaryRepository.save(summary);

        return new SummaryResponse(summaryText);
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
    public List<Summary> getHistory() {
        return summaryRepository.findAll();
    }

    // @GetMapping("/history")
    // public List<Summary> getHistory() {
    //     return summaryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    // }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
