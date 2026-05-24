pipeline {
    agent any

    environment {
        ARTIFACTORY_URL = "http://localhost:8082/artifactory"
        ARTIFACTORY_REPO = "example-repo-local"
        APK_PATH = "app/build/outputs/apk/debug/app-debug.apk"
        SONAR_TOKEN = credentials("sonar-token")
    }

    stages {
        stage("Checkout") {
            steps {
                checkout scm
            }
        }

        stage("Build Debug APK") {
            steps {
                sh "./gradlew --no-daemon clean assembleDebug"
            }
        }

        stage("SonarQube Analysis") {
            steps {
                withSonarQubeEnv("SonarQube") {
                    sh "./gradlew --no-daemon sonar -Dsonar.token=${SONAR_TOKEN}"
                }
            }
        }

        stage("Archive In Jenkins") {
            steps {
                archiveArtifacts artifacts: "${APK_PATH}", fingerprint: true
            }
        }

        stage("Publish APK To Artifactory") {
            steps {
                withCredentials([string(credentialsId: "artifactory-token", variable: "ARTIFACTORY_TOKEN")]) {
                    sh '''
                        set -eu
                        VERSION="1.0.0"
                        TARGET="${ARTIFACTORY_URL}/${ARTIFACTORY_REPO}/android/hellocicd/${VERSION}/hellocicd-${VERSION}-${BUILD_NUMBER}.apk"
                        curl -f -H "Authorization: Bearer ${ARTIFACTORY_TOKEN}" -T "${APK_PATH}" "${TARGET}"
                        echo "Uploaded to ${TARGET}"
                    '''
                }
            }
        }
    }
}