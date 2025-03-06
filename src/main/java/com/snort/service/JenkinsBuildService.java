//package com.snort.service;
//
//import com.snort.entities.JenkinsBuild;
//import com.snort.repository.JenkinsBuildRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class JenkinsBuildService {
//
//    @Autowired
//    private JenkinsBuildRepository repository;
//
//    public JenkinsBuild logBuildStart(String jobName, Integer buildNumber) {
//        JenkinsBuild build = new JenkinsBuild();
//        build.setJobName(jobName);
//        build.setBuildNumber(buildNumber);
//        build.setStatus("IN_PROGRESS");
//        return repository.save(build);
//    }
//
//    public JenkinsBuild logBuildCompletion(String jobName, Integer buildNumber, String status) {
//        Optional<JenkinsBuild> buildOpt = repository.findByJobNameAndBuildNumber(jobName, buildNumber);
//        if (buildOpt.isPresent()) {
//            JenkinsBuild build = buildOpt.get();
//            build.setStatus(status);
//            build.setEndTime(LocalDateTime.now());
//            return repository.save(build);
//        }
//        return null;
//    }
//}
//
