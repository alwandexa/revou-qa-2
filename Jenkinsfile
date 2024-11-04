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
                    sh '''
                        mkdir -p ${WORKSPACE}/apps
                        curl -L -o ${WORKSPACE}/apps/sauce-demo.apk https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/Android-MyDemoAppRN.1.3.0.build-244.apk
                        chmod -R 777 ${WORKSPACE}/apps
                    '''
                }
            }
        }

        stage('Wait for Emulator') {
            steps {
                script {
                    sh '''
                        # Check if emulator container is running
                        if ! docker ps | grep android-emulator; then
                            echo "Android emulator container is not running!"
                            exit 1
                        fi
                        
                        # Wait for emulator to boot (timeout after 5 minutes)
                        timeout 300 bash -c '
                        until docker exec android-emulator adb shell getprop sys.boot_completed 2>/dev/null | grep -m 1 "1"; do
                            echo "Waiting for emulator to start..."
                            # Print emulator status
                            docker exec android-emulator adb devices
                            docker exec android-emulator adb shell getprop init.svc.bootanim || true
                            sleep 10
                        done
                        '
                        
                        # Additional wait to ensure stability
                        sleep 30
                        
                        # Print final status
                        docker exec android-emulator adb devices
                        docker exec android-emulator adb shell getprop init.svc.bootanim
                    '''
                }
            }
        }

        stage('Install App') {
            steps {
                script {
                    sh '''
                        # Ensure ADB server is running
                        docker exec android-emulator adb start-server
                        
                        # Copy and install app
                        docker cp ${WORKSPACE}/apps/sauce-demo.apk android-emulator:/root/sauce-demo.apk
                        docker exec android-emulator adb install -r /root/sauce-demo.apk
                        
                        # Verify installation
                        docker exec android-emulator adb shell pm list packages | grep saucelabs
                    '''
                }
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