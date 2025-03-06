package com.snort.controller;


import com.snort.entities.JenkinsBuild;
import com.snort.service.JenkinsBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/build")
public class JenkinsBuildController {

    @Autowired
    private JenkinsBuildService service;

    @PostMapping("/status")
    public ResponseEntity<String> updateBuildStatus(@RequestBody Map<String, Object> payload) {
        String jobName = (String) payload.get("jobName");
        Integer buildNumber = (Integer) payload.get("buildNumber");
        String status = (String) payload.get("status");

        if ("IN_PROGRESS".equals(status)) {
            service.logBuildStart(jobName, buildNumber);
        } else {
            service.logBuildCompletion(jobName, buildNumber, status);
        }

        return ResponseEntity.ok("Build status updated successfully.");
    }
}
