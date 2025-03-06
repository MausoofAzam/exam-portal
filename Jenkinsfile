pipeline {
    agent any

    environment {
        API_URL = 'http://localhost:8080/api/build-status'  // API to store build status
        JOB_NAME = 'SpringBoot-CI-CD'
    }

    triggers {
        githubPush()  // Trigger on push to GitHub
    }

    stages {
        stage('Initialize Build Status') {
            steps {
                script {
                    def buildNumber = env.BUILD_NUMBER
                    def jsonPayload = """{
                        "jobName": "${JOB_NAME}",
                        "buildNumber": ${buildNumber},
                        "status": "IN_PROGRESS"
                    }"""

                    echo "📡 Sending build start status..."
                    bat "curl -X POST ${API_URL} -H \"Content-Type: application/json\" -d '${jsonPayload}'"
                }
            }
        }

        stage('Clone Repository') {
            steps {
                script {
                    try {
                        git branch: 'develop', url: 'https://github.com/MausoofAzam/exam-portal.git'
                        echo "✅ Repository cloned successfully"
                    } catch (Exception e) {
                        error "❌ Failed to clone repository"
                    }
                }
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    try {
                        bat 'mvn clean package'
                        echo "✅ Build completed successfully"
                    } catch (Exception e) {
                        error "❌ Build failed"
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    try {
                        bat 'mvn test'
                        echo "✅ Tests passed successfully"
                    } catch (Exception e) {
                        error "❌ Tests failed"
                    }
                }
            }
        }

        stage('Stop Existing Application') {
            steps {
                script {
                    echo "🛑 Stopping any running application..."
                    bat 'taskkill /F /IM java.exe || echo "No existing application running"'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    try {
                        bat 'java -jar target/EXAM_MANAGEMENT-0.0.1-SNAPSHOT.jar'
                        echo "✅ Deployment successful"
                    } catch (Exception e) {
                        error "❌ Deployment failed"
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                def buildNumber = env.BUILD_NUMBER
                def jsonPayload = """{
                    "jobName": "${JOB_NAME}",
                    "buildNumber": ${buildNumber},
                    "status": "SUCCESS"
                }"""

                echo "📡 Sending success status..."
                bat "curl -X POST ${API_URL} -H \"Content-Type: application/json\" -d '${jsonPayload}'"
            }
            echo "🎉 Build SUCCESS! Everything worked fine."
        }

        failure {
            script {
                def buildNumber = env.BUILD_NUMBER
                def jsonPayload = """{
                    "jobName": "${JOB_NAME}",
                    "buildNumber": ${buildNumber},
                    "status": "FAILED"
                }"""

                echo "📡 Sending failure status..."
                bat "curl -X POST ${API_URL} -H \"Content-Type: application/json\" -d '${jsonPayload}'"
            }
            echo "🚨 Build FAILED! Check logs for errors."
        }
    }
}
