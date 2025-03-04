pipeline {
    agent any

    environment {
        DB_URL = 'jdbc:postgresql://localhost:5432/examportal'
        DB_USER = 'root'
        DB_PASSWORD = '0000'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/MausoofAzam/exam-portal'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                bat 'java -jar target/EXAM_MANAGEMENT-0.0.1-SNAPSHOT.jar'
            }
        }
    }
     post {
            success {
                echo 'Pipeline executed successfully!'
            }
            failure {
                echo 'Pipeline failed. Check logs for errors.'
            }
        }
}
