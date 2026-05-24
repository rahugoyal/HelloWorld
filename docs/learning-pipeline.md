# Android Hello World CI/CD Learning Path

## Goal

Build a tiny Android app, raise a pull request, merge it to `main`, let Jenkins build the APK, then upload the APK to Artifactory.

## Flow

1. Developer works on a feature branch.
2. Developer raises a PR to `main`.
3. Reviewer approves and merges.
4. Jenkins job watching `main` runs `./gradlew clean assembleDebug`.
5. Jenkins archives the APK.
6. Jenkins uploads the APK to Artifactory `example-repo-local`.

## Local Artifactory

Artifactory is running at:

```text
http://localhost:8082
```

The Jenkinsfile uses this URL from inside a Docker-based Jenkins agent:

```text
http://host.docker.internal:8082/artifactory
```

## Jenkins Credential

Create a Jenkins secret text credential:

```text
ID: artifactory-token
Type: Secret text
Value: your Artifactory access token
```

## Jenkins Job

For learning, use a Multibranch Pipeline job:

1. New Item -> Multibranch Pipeline.
2. Add your GitHub repository as the branch source.
3. Set build strategy to discover branches and pull requests.
4. Save and scan repository.

After a PR is merged to `main`, Jenkins will run the `Jenkinsfile` from `main` and upload the APK.

## Commands

Build locally:

```sh
./gradlew clean assembleDebug
```

The APK appears here:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Upload target pattern:

```text
example-repo-local/android/hello-cicd/<version>/hello-cicd-<version>-<jenkins-build-number>.apk
```
