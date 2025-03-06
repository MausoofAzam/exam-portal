package com.snort.repository;

import com.snort.entities.JenkinsBuild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JenkinsBuildRepository extends JpaRepository<JenkinsBuild,Long> {

    JenkinsBuild findByJobNameAndBuildNumber(String jobName, Integer buildNumber);

}
