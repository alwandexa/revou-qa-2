pipeline {
    agent any
    
    tools {
        maven 'Maven'  
        jdk 'Java'     
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
                sh 'mvn test -DENV=grid'
            }
            post {
                always {
                    // API Test Report
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/api',
                        reportFiles: 'cucumber.html',
                        reportName: 'API Test Report',
                        reportTitles: '',
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        allowMissing: false,
                        includes: '**/*',
                        escapeUnderscores: true,
                        useWrapperFileDirectly: true
                    ])
                    
                    // Web Test Report
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/web',
                        reportFiles: 'cucumber.html',
                        reportName: 'Web Test Report',
                        reportTitles: '',
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        allowMissing: false,
                        includes: '**/*',
                        escapeUnderscores: true,
                        useWrapperFileDirectly: true
                    ])
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Tests executed successfully!'
        }
    }
}