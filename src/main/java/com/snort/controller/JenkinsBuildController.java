package com.snort.controller;


import com.snort.entities.JenkinsBuild;
import com.snort.service.JenkinsBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/builds")
public class JenkinsBuildController {

    @Autowired
    private JenkinsBuildService service;

    @PostMapping("/update")
    public String updateBuildStatus(@RequestBody JenkinsBuild build) {
        service.updateBuildStatus(build);
        return "✅ Build status updated!";
    }
}
