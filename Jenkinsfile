pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -X -s clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -s test'
            }
        }
    }
}
