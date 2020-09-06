pipeline {
    agent any
    parameters {
            string(name: 'SONAR_HOST', defaultValue: 'http://localhost:9000', description: 'Sonar hostname')
             password(name: 'SONAR_TOKEN', defaultValue: '838532c7b6f7ecb9e94f794263d5dd7c814997ad', description: 'Sonar access token')
        }
    tools {
        maven 'maven'
        jdk 'jdk'
		dockerTool 'docker'
    }

    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Compile') {
            steps {
                sh 'mvn clean compile test-compile' 
            }
        }
		
		stage ('Unit Test') {
            steps {
                sh 'mvn test' 
            }
        }

        stage('SonarCloud') {
            environment {
                 SCANNER_HOME = tool 'SonarQubeScanner'
                 ORGANIZATION = "customerMs"
                 PROJECT_NAME = "com.mybank:customerMs"
               }
          steps {
            withSonarQubeEnv('sonar') {
                sh '''$SCANNER_HOME/bin/sonar-scanner -Dsonar.java.binaries=build/classes/java/ -Dsonar.sources=.'''
            }
          }
        }
		
		stage ('Package') {
            steps {
                sh 'mvn package verify -Dmaven.test.skip=true -Dmaven.skip.tests' 
            }
        }
		
		stage ('Build Docker Image') {
			steps {
				echo 'Building the docker image'
				sh 'chmod 755 target/*.jar'
				sh 'docker build -f Dockerfile -t arunajc/customerms:latest --no-cache .'
			}
        }
		
		stage ('Publish Docker Image') {
			steps {
				echo 'Publishing the docker image'
				sh 'docker push arunajc/customerms:latest'
				echo 'Publishing docker image done'
			}
        }
    }
}