# Jenkins Setup

## 1. Start Jenkins

Jenkins was not reachable at `http://localhost:8080` when this project was created.

For a local Docker Jenkins later, expose port `8080` and make sure the agent has:

```text
JDK 17
Android SDK
curl
Git
```

## 2. Add Artifactory Credential

In Jenkins:

```text
Manage Jenkins -> Credentials -> System -> Global credentials -> Add Credentials
```

Use:

```text
Kind: Secret text
ID: artifactory-token
Secret: your Artifactory access token
```

## 3. Create The Job

Use a Multibranch Pipeline job for the cleanest PR/merge learning flow:

```text
New Item -> Multibranch Pipeline
```

Configure GitHub as the branch source, then scan the repository.

## 4. Expected Behavior

Pull requests build for validation.

Merges to `main` run the `Publish APK To Artifactory` stage and upload:

```text
hello-cicd-1.0.0-<jenkins-build-number>.apk
```
