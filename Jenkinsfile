pipeline {
    agent any

    environment {
        ARTIFACTORY_URL = "http://localhost:8082/artifactory"
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

        stage("SonarQube Analysis") {
            steps {
                sh "./gradlew --no-daemon sonar"
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

        stage("Update Jira") {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            // ← last
            steps {
                script {
                    def branchName = env.GIT_BRANCH ?: "unknown"
                    def ticketKey = (branchName =~ /SCRUM-\d+/)
                    if (ticketKey) {
                        jiraComment(
                                site: "rahulgoyalsbl010.atlassian.net",
                                idOrKey: ticketKey[0],
                                body: "✅ Jenkins build #${BUILD_NUMBER} passed. APK uploaded to Artifactory."
                        )
                        jiraTransitionIssue(
                                site: "rahulgoyalsbl010.atlassian.net",
                                idOrKey: ticketKey[0],
                                input: [transition: [name: "In Review"]]
                        )
                    } else {
                        echo "No SCRUM ticket found in branch name: ${branchName}"
                    }
                }
            }
        }
    }
}