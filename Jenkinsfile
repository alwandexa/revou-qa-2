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
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/api',
                        reportFiles: 'index.html',
                        reportName: 'API Test Report',
                        reportTitles: ''
                    ])
                    
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/web',
                        reportFiles: 'index.html',
                        reportName: 'Web Test Report',
                        reportTitles: ''
                    ])

                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/cucumber-reports/app',
                        reportFiles: 'index.html',
                        reportName: 'App Test Report',
                        reportTitles: ''
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