// run:
// 	docker run --name jenkins -d -p 8080:8080 -p 50000:50000 -v ~/jenkins:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -u root jenkins/jenkins:latest

def mainDir="./"
def ecrLoginHelper="docker-credential-ecr-login"
def region="ap-northeast-2"
def ecrUrl="152011405160.dkr.ecr.ap-northeast-2.amazonaws.com"
def repository="jenkins"
def deployHost="172.32.20.250"
def aws_credential_name="aws-key"

pipeline {
    agent any
    tools {
    	jdk("docker -java")
    }
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
		chmod +x ./gradlew 
                ./gradlew bootJar
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
                    sh """
		    rm -f ~/.dockercfg ~/.docker/config.json || true
		    """
                    
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
                    sh """
			ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
			'docker stop sb-server && docker kill sb-server' \
                	'docker rm -f sb-server' \
			'aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${ecrUrl};' \
			'docker run -d -p 80:8080 --name sb-server -t ${ecrUrl}/${repository}:${currentBuild.number}'
		    """
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
