pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-v /root/.m2:/root/.m2 --network host'
            reuseNode true
        }
        // label 'agent01'
    }
    stages {
        stage('Preparations') {
            steps {
                sh 'cp settings.xml $HOME/.m2'
            }
        }
        stage('Build') {
            steps {
                dir('helloworld') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Deploy') {
            steps {
                dir('helloworld') {
                    // sh 'mvn -Drepo.id=wildflyBuilds -Drepo.login=admin -Drepo.pwd=test -Drepo.url=http://172.20.17.14:8081 deploy'
                    // sh 'mvn -X \
                    //         -DaltSnapshotDeploymentRepository=maven-snapshots::default::http://172.20.17.14:8081/repository/maven-snapshots/ \
                    //         -DaltReleaseDeploymentRepository=maven-releases::default::http://172.20.17.14:8081/repository/releases/ \
                    //         deploy'
                    sh 'mvn -Drepo.login=admin -Drepo.pwd=test deploy'
                }
            }
        }
    }
}
