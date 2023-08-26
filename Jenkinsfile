pipeline {
    agent {
        // docker {
        //     image 'maven:3.9.0'
        //     args '-v /root/.m2:/root/.m2 --network host'
        // }
        label 'agent01'
    }
    stages {
        stage('Preparations') {
            steps {
                sh 'cp settings.xml $HOME/.m2'
                sh 'cd helloworld'
            }
        }
        stage('Build') {
            steps {
                sh 'cd helloworld'
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn -Drepo.id=wildflyBuilds -Drepo.login=admin -Drepo.pwd=test -Drepo.url=http://172.20.17.14:8081 wildfly:deploy'
            }
        }
    }
}
