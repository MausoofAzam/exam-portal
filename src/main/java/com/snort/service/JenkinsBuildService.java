package com.snort.service;

import com.snort.entities.JenkinsBuild;
import com.snort.repository.JenkinsBuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JenkinsBuildService {

    @Autowired
    private JenkinsBuildRepository repository;

    public void updateBuildStatus(JenkinsBuild build) {
        JenkinsBuild existingBuild = repository.findByJobNameAndBuildNumber(build.getJobName(), build.getBuildNumber());

        if (existingBuild == null) {
            // Insert new build status
            repository.save(build);
        } else {
            // Update existing record
            existingBuild.setStatus(build.getStatus());

            if ("SUCCESS".equals(build.getStatus()) || "FAILED".equals(build.getStatus())) {
                existingBuild.setEndTime(LocalDateTime.now());
            }

            repository.save(existingBuild);
        }
    }
}
