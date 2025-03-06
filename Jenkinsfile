pipeline {
    agent any

    environment {
        API_URL = 'http://localhost:8080/jenkins/build' // Update if hosted elsewhere
        JOB_NAME = 'exam-portal'
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Start Build') {
            steps {
                script {
                    bat """
                    curl -X POST "%API_URL%/start" -H "Content-Type: application/json" -d "{\\"jobName\\": \\"%JOB_NAME%\\", \\"buildNumber\\": %BUILD_NUMBER%}"
                    """
                }
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    try {
                        bat 'mvn clean package'
                    } catch (Exception e) {
                        error "Build failed"
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    try {
                        bat 'mvn test'
                    } catch (Exception e) {
                        error "Tests failed"
                    }
                }
            }
        }

        stage('Stop Existing Application') {
            steps {
                script {
                    bat 'taskkill /F /IM java.exe || echo "No existing application running"'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    try {
                        bat 'java -jar target/EXAM_MANAGEMENT-0.0.1-SNAPSHOT.jar'
                    } catch (Exception e) {
                        error "Deployment failed"
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                bat """
                curl -X PUT "%API_URL%/update" -H "Content-Type: application/json" -d "{\\"jobName\\": \\"%JOB_NAME%\\", \\"buildNumber\\": %BUILD_NUMBER%, \\"status\\": \\"SUCCESS\\"}"
                """
            }
        }
        failure {
            script {
                bat """
                curl -X PUT "%API_URL%/update" -H "Content-Type: application/json" -d "{\\"jobName\\": \\"%JOB_NAME%\\", \\"buildNumber\\": %BUILD_NUMBER%, \\"status\\": \\"FAILED\\"}"
                """
            }
        }
    }
}
