pipeline {
    agent any

    environment {
        // Define the path to the Maven executable
        MAVEN_HOME = tool(name: 'maven_3_2_1', type: 'maven')
        PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
        // Define the Docker Hub credentials
        DOCKERHUB_CREDENTIALS = credentials('karimelhou-dockerhub')
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Building the Docker image
                    dockerImage = docker.build("authservice-app:${env.BUILD_ID}")
                }
            }
        }

        stage('Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'karimelhou-dockerhub', passwordVariable: 'DOCKERHUB_PSW', usernameVariable: 'DOCKERHUB_USR')]) {
                        sh 'echo $DOCKERHUB_PSW | docker login -u $DOCKERHUB_USR --password-stdin'
                    }
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    // Tagging the image before pushing
                    sh "docker tag authservice-app:${env.BUILD_ID} karimelhou/authservice:${env.BUILD_ID}"
                    // Pushing the image to Docker Hub
                    sh "docker push karimelhou/authservice:${env.BUILD_ID}"
                }
            }
        }

        stage('Docker Run') {
            steps {
                script {
                    echo "BUILD_ID: ${env.BUILD_ID}"
                    sh "docker run -d -p 8082:8082 authservice-app:${env.BUILD_ID}"
                }
            }
        }
       // stage('Trigger Update Job') {
         //   steps {
           //     script {
                    // Triggering another Jenkins job with the current BUILD_ID
             //       build job: 'updatepush', parameters: [string(name: 'IMAGE_TAG', value: "${env.BUILD_ID}")]
              //  }
          //  }
      //  }
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}
