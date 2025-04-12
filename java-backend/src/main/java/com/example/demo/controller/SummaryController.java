package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

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
        String dummySummary = "This is a dummy summary for: " + request.url;
        history.add(new HistoryEntry(request.url, dummySummary));
        return new SummaryResponse(dummySummary);
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

