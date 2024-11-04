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
        
        stage('Setup App Directory') {
            steps {
                sh '''
                    mkdir -p apps
                    wget https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/Android-MyDemoAppRN.1.3.0.build-244.apk -O apps/sauce-demo.apk
                '''
            }
        }

        stage('Wait for Emulator') {
            steps {
                // Wait for emulator to be ready (timeout after 2 minutes)
                sh '''
                    timeout 120 bash -c '
                    until docker exec android-emulator adb shell getprop sys.boot_completed 2>/dev/null | grep -m 1 "1"; do
                        echo "Waiting for emulator to start..."
                        sleep 5
                    done
                    '
                '''
            }
        }

        stage('Install App') {
            steps {
                sh '''
                    # Ensure ADB server is running
                    docker exec android-emulator adb start-server
                    
                    # Install the app
                    docker exec android-emulator adb install -r /root/apps/sauce-demo.apk
                    
                    # Verify installation
                    docker exec android-emulator adb shell pm list packages | grep saucelabs
                '''
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
                        reportDir: 'target/cucumber-reports',
                        reportFiles: 'cucumber-pretty.html',
                        reportName: 'Cucumber Report',
                        reportTitles: ''
                    ])
                    
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
            cleanWs()
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
            sh '''
                # Print emulator logs for debugging
                docker logs android-emulator
                docker exec android-emulator adb logcat -d
            '''
        }
    }
}