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
            when {
                anyOf {
                    branch "main"
                    branch "master"
                }
            }
            steps {
                withCredentials([string(credentialsId: "artifactory-token", variable: "ARTIFACTORY_TOKEN")]) {
                    sh '''
                        set -eu
                        VERSION="$(./gradlew -q printVersionName)"
                        TARGET="${ARTIFACTORY_URL}/${ARTIFACTORY_REPO}/android/hello-cicd/${VERSION}/hello-cicd-${VERSION}-${BUILD_NUMBER}.apk"
                        curl -f -H "Authorization: Bearer ${ARTIFACTORY_TOKEN}" -T "${APK_PATH}" "${TARGET}"
                        echo "Uploaded ${TARGET}"
                    '''
                }
            }
        }
    }
}
