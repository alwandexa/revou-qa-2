pipeline {
    agent any
    
    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Publish Cucumber HTML reports
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports',
                        reportFiles: 'cucumber-pretty.html',
                        reportName: 'Cucumber Report',
                        reportTitles: ''
                    ])
                    
                    // Publish TestNG reports
                    step([
                        $class: 'Publisher',
                        reportFilenamePattern: '**/testng-results.xml'
                    ])
                }
            }
        }
    }
    
    post {
        always {
            // Clean workspace after build
            cleanWs()
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
        }
    }
} 