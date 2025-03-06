pipeline {
    agent any

    environment {
        DB_HOST = 'localhost'
        DB_PORT = '3306'
        DB_NAME = 'examportal'
        DB_USER = 'root'
        DB_PASSWORD = '0000'
        JOB_NAME = 'SpringBoot-CI-CD'
    }

    stages {
        stage('Initialize Build Tracking') {
            steps {
                script {
                    def buildNumber = currentBuild.number
                    def startTime = new Date().format("yyyy-MM-dd HH:mm:ss")

                    echo "🚀 Inserting build STARTED status into MySQL..."

                    bat """
                        mysql -h %DB_HOST% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -e "INSERT INTO jenkins_builds (job_name, build_number, status, start_time)
                        VALUES ('%JOB_NAME%', ${buildNumber}, 'IN_PROGRESS', '${startTime}');"
                    """
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
                def buildNumber = currentBuild.number
                def endTime = new Date().format("yyyy-MM-dd HH:mm:ss")

                echo "🎉 Build SUCCESS! Updating MySQL..."

                bat """
                    mysql -h %DB_HOST% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -e "UPDATE jenkins_builds SET status = 'SUCCESS', end_time = '${endTime}'
                    WHERE job_name = '%JOB_NAME%' AND build_number = ${buildNumber};"
                """
            }
        }
        failure {
            script {
                def buildNumber = currentBuild.number
                def endTime = new Date().format("yyyy-MM-dd HH:mm:ss")

                echo "🚨 Build FAILED! Updating MySQL..."

                bat """
                    mysql -h %DB_HOST% -u %DB_USER% -p%DB_PASSWORD% -D %DB_NAME% -e "UPDATE jenkins_builds SET status = 'FAILED', end_time = '${endTime}'
                    WHERE job_name = '%JOB_NAME%' AND build_number = ${buildNumber};"
                """
            }
        }
    }
}
