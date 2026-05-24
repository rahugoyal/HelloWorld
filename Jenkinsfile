pipeline {
    agent any

    environment {
        ARTIFACTORY_URL = "http://host.docker.internal:8082/artifactory"
        ARTIFACTORY_REPO = "example-repo-local"
        APK_PATH = "app/build/outputs/apk/debug/app-debug.apk"
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