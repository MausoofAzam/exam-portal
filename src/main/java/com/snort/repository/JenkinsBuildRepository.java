package com.snort.repository;

import com.snort.entities.JenkinsBuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JenkinsBuildRepository extends JpaRepository<JenkinsBuild,Long> {

    Optional<JenkinsBuild> findByJobNameAndBuildNumber(String jobName, Integer buildNumber);


}
