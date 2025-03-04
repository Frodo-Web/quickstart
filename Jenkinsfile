pipeline {
    agent {
        docker {
            image 'maven:latest'
            args '-u 0 -v /home/dev/Practice/Jenkins/m2-dind:/root/.m2 --network host'
            reuseNode true
        }
        // label 'agent01'
    }
    stages {
        stage('Preparations') {
            steps {
                sh 'curl -Ss -L -o jq https://github.com/jqlang/jq/releases/download/jq-1.6/jq-linux64'
                sh 'chmod +x jq'
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
        stage('Deploy to Nexus') {
            steps {
                dir('helloworld') {
                    sh 'mvn -Drepo.login=admin -Drepo.pwd=test deploy'
                }
            }
        }
        stage('Deploy to Wildfly') {
            steps {
                sh 'curl -Ss -o helloworld.war -L -X GET "http://172.20.17.14:8081/service/rest/v1/search/assets/download?sort=version&repository=maven-snapshots&maven.groupId=org.wildfly.quickstarts&maven.artifactId=helloworld&maven.extension=war"'
                script {
                    def UNDEPLOY_AND_REMOVE = '''
                      result=`curl -Ss -H "content-Type: application/json" -d '{"operation":"undeploy", "address":[{"deployment":"helloworld.war"}]}' --digest http://frodo:test@172.20.17.13:9990/management`
                      result=`curl -Ss -H "content-Type: application/json" -d '{"operation":"remove", "address":[{"deployment":"helloworld.war"}]}' --digest http://frodo:test@172.20.17.13:9990/management`
                    '''
                    def UPLOAD_AND_DEPLOY = '''
                      bytes_value=`curl -Ss -F "file=@./helloworld.war" --digest http://frodo:test@172.20.17.13:9990/management/add-content | ./jq -r .result.BYTES_VALUE`
                      result=`curl -Ss -H "Content-Type: application/json" \
                           -d '{"content":[{"hash": {"BYTES_VALUE" : "'${bytes_value}'"}}], "address": [{"deployment":"helloworld.war"}], "operation":"add", "enabled":"true"}' \
                           --digest http://frodo:test@172.20.17.13:9990/management`
                    '''
                    sh(script: UNDEPLOY_AND_REMOVE, returnStdout: true).trim()
                    sh(script: UPLOAD_AND_DEPLOY, returnStdout: true).trim()
                }
            }
        }
    }
}
