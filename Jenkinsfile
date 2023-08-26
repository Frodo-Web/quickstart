pipeline {
    agent {
        docker {
            image 'maven:3.9.0'
            args '-v /root/.m2:/root/.m2 --network host'
        }
    }
    stages {
        stage('Debug') {
            steps {
                sh 'pwd && ls'
            }
        }      
    }
}
