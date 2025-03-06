package com.snort.controller;

import com.snort.entities.JenkinsBuild;
import com.snort.repository.JenkinsBuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jenkins/build")
public class JenkinsBuildController {

    @Autowired
    private JenkinsBuildRepository buildRepository;

    @PostMapping("/start")
    public ResponseEntity<String> startBuild(@RequestBody JenkinsBuild request) {
        JenkinsBuild build = new JenkinsBuild();
        build.setJobName(request.getJobName());
        build.setBuildNumber(request.getBuildNumber());
        build.setStatus("IN_PROGRESS");
        buildRepository.save(build);
        return ResponseEntity.ok("Build started successfully!");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBuild(@RequestBody JenkinsBuild request) {
        Optional<JenkinsBuild> optionalBuild = buildRepository.findByJobNameAndBuildNumber(request.getJobName(), request.getBuildNumber());
        if (optionalBuild.isPresent()) {
            JenkinsBuild build = optionalBuild.get();
            build.markCompleted(request.getStatus());
            buildRepository.save(build);
            return ResponseEntity.ok("Build updated successfully!");
        } else {
            return ResponseEntity.status(404).body("Build not found!");
        }
    }
}
