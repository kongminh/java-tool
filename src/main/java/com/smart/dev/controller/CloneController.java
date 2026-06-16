package com.smart.dev.controller;

import com.smart.dev.model.CloneModuleRequest;
import com.smart.dev.model.CloneModuleResponse;
import com.smart.dev.service.CloneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clone")
public class CloneController {

    private final CloneService cloneService;

    public CloneController(CloneService cloneService) {
        this.cloneService = cloneService;
    }

    @PostMapping("/module")
    public ResponseEntity<?> cloneModule(@RequestBody CloneModuleRequest request) {
        try {
            CloneModuleResponse response = cloneService.cloneModule(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to clone module: " + e.getMessage());
        }
    }
}
