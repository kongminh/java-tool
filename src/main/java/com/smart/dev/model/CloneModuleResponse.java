package com.smart.dev.model;

import java.util.List;
import java.util.Map;

public class CloneModuleResponse {
    private String status;
    private String message;
    private Map<String, Object> metrics;
    private List<String> previewFiles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }

    public List<String> getPreviewFiles() {
        return previewFiles;
    }

    public void setPreviewFiles(List<String> previewFiles) {
        this.previewFiles = previewFiles;
    }
}
