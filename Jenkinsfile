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
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
//                sh 'mvn -Drepo.id=wildflyBuilds -Drepo.login=admin -Drepo.pwd=test -Drepo.url=http://172.20.17.14:8081 deploy'
                withMaven(
                    mavenSettingsFilePath: '/home/jenkins/.m2/settings.xml',
                    mavenSettingsConfig: '79124993-ee18-48ed-97dc-7345abef9dab'
                ) {
                    // sh 'mvn -X -Drepo.login=admin -Drepo.pwd=test deploy'
                    sh 'mvn -X deploy'
                }
            }
        }
    }
}
