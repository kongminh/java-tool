package com.smart.dev.controller;

import com.smart.dev.model.OutputFormat;
import com.smart.dev.model.OutputStructure;
import com.smart.dev.service.AnalyzerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analyzer")
public class AnalyzerController {

    private final AnalyzerService analyzerService;

    public AnalyzerController(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam String projectSrcPath,
            @RequestParam(defaultValue = "JSON") OutputFormat format,
            @RequestParam(defaultValue = "UNION") OutputStructure structure) {
        
        return analyzerService.analyze(projectSrcPath, format, structure);
    }
}
