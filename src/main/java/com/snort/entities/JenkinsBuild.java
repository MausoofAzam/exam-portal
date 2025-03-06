package com.snort.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "jenkins_builds")
public class JenkinsBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
    private Integer buildNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public JenkinsBuild() {
        this.startTime = LocalDateTime.now();
    }
    public void markCompleted(String status) {
        this.status = status;
        this.endTime = LocalDateTime.now();
    }
}