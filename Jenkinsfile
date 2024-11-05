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
                        reportFiles: 'index.html',
                        reportName: 'API Test Report',
                        reportTitles: '',
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        allowScripts: true,
                        sandbox: false
                    ])
                    
                    // Web Test Report
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/web',
                        reportFiles: 'index.html',
                        reportName: 'Web Test Report',
                        reportTitles: '',
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        allowScripts: true,
                        sandbox: false
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