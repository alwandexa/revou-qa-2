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
                script {
                    // Create directory with proper permissions
                    sh 'mkdir -p ${WORKSPACE}/apps'
                    
                    // Download app using curl (which is usually pre-installed)
                    sh '''
                        if command -v curl > /dev/null; then
                            curl -L -o ${WORKSPACE}/apps/sauce-demo.apk https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/Android-MyDemoAppRN.1.3.0.build-244.apk
                        else
                            echo "curl is not installed. Please install curl in your Jenkins container."
                            exit 1
                        fi
                    '''
                    
                    // Ensure proper permissions
                    sh 'chmod -R 777 ${WORKSPACE}/apps'
                }
            }
        }

        stage('Wait for Emulator') {
            steps {
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
                    # Copy app to emulator
                    docker cp ${WORKSPACE}/apps/sauce-demo.apk android-emulator:/root/sauce-demo.apk
                    
                    # Install the app
                    docker exec android-emulator adb install -r /root/sauce-demo.apk
                    
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