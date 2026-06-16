package com.smart.dev.service;

import com.smart.dev.model.OutputFormat;
import com.smart.dev.model.OutputStructure;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {

    public String analyze(String projectSrcPath, OutputFormat format, OutputStructure structure) {
        // Tạm thời trả về thông tin để xác nhận dữ liệu truyền vào
        return String.format("Analyze started for path: %s | Format: %s | Structure: %s", 
                projectSrcPath, format, structure);
    }
}
