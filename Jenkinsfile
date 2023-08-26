pipeline {
    agent {
        // docker {
        //     image 'maven:3.9.0'
        //     args '-v /root/.m2:/root/.m2 --network host'
        // }
        label 'agent01'
    }
    stages {
        stage('Build') {
            steps {
                sh 'cd helloworld'
                sh 'mvn clean package'
            }
        }      
    }
}
