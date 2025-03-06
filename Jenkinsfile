pipeline {
    agent any

    environment {
        DB_URL = 'jdbc:mysql://localhost:3306/examportal'
        DB_USER = 'root'
        DB_PASSWORD = '0000'
    }

    triggers {
        githubPush()  // Triggers pipeline on push/merge to GitHub
    }

    stages {
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
            echo "🎉 Build SUCCESS! Everything worked fine."
        }
        failure {
            echo "🚨 Build FAILED! Check logs for errors."
        }
    }
}
