def mainDir="./"
def ecrLoginHelper="docker-credential-ecr-login"
def region="ap-northeast-2"
def ecrUrl="152011405160.dkr.ecr.ap-northeast-2.amazonaws.com"
def repository="jenkins"
def deployHost="172.32.20.250"
def aws_crendential_name="aws-key"

pipeline {
    agent any

    stages {
        stage('Pull Codes from Github'){
            steps{
                checkout scm
            }
	    post {
                success {
                    echo 'success clone project'
                }
                failure {
                    error 'fail clone project' // exit pipeline
                }
            }
        }
        stage('Build Codes by Gradle') {
            steps {
                sh """
                cd ${mainDir}
                ./gradlew clean build
                """
            }
	    post {
                success {
                    echo 'success build project'
                }
                failure {
                    error 'fail build project' // exit pipeline
                }
            }
        }
	stage('dockerizing project by dockerfile') {
            steps {
		script{
                	sh """
        		docker build -t ${ecrUrl}/${repository}:${currentBuild.number} .
        		docker tag ${ecrUrl}/${repository}:${currentBuild.number} ${ecrUrl}/${repository}:latest
			"""
		}
            }
            post {
                success {
                    echo 'success dockerizing project'
                }
                failure {
                    error 'fail dockerizing project' // exit pipeline
                }
            }
        }
	stage('upload aws ECR') {
             steps {
                script{   	
                    // cleanup current user docker credentials
                    sh 'rm -f ~/.dockercfg ~/.docker/config.json || true'
                    
		    docker.withRegistry("https://${ecrUrl}", "ecr:${region}:${aws_credential_name}") {
                      docker.image("${ecrUrl}/${repository}:${currentBuild.number}").push()
                      docker.image("${ecrUrl}/${repository}:latest").push()
        	    }
		}
	    }
            post {
                success {
                    echo 'success upload image'
                }
                failure {
                    error 'fail upload image' // exit pipeline
                }
            }
        }
        stage('Deploy to AWS EC2 VM'){
            steps{
                sshagent(credentials : ["deploy-key"]) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                     'aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}/${repository}; \
                      docker run -d -p 80:8080 -t ${ecrUrl}/${repository}:${currentBuild.number};'"
                }
            }
	    post {
                success {
                    echo 'success upload image'
                }
                failure {
                    error 'fail upload image' // exit pipeline
                }
            }

        }
    }
}
