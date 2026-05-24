# Hello CICD Android

Tiny Android Hello World app used to learn this CI/CD path:

```text
feature branch -> pull request -> merge to main -> Jenkins build -> Artifactory upload
```

## Local Build

```sh
./gradlew clean assembleDebug
```

Output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Pipeline

The `Jenkinsfile` builds the debug APK, archives it in Jenkins, and uploads it to local Artifactory:

```text
http://localhost:8082/artifactory/example-repo-local/android/hello-cicd/
```

See [docs/learning-pipeline.md](docs/learning-pipeline.md) for the step-by-step learning guide.
