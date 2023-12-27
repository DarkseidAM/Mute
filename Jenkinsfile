pipeline {
    agent any

    environment {
        APP_ARCHIVE_NAME = 'app'
        CHANGELOG_CMD = 'git log --date=format:"%Y-%m-%d" --pretty="format: * %s% b (%an, %cd)" | head -n 10 > commit-changelog.txt'
    }

    tools {
        jdk "Java 17"
    }

    options {
        // prevent multiple builds from running concurrently, that come from the same git branch
        disableConcurrentBuilds()
    }

    stages {
        stage("PR") {
            when {
                changeRequest target: 'main'
            }
            stages {
                stage("Build") {
                    steps {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build assembleRelease app:bundleRelease'
                    }
                }
            }
        }
        stage("Push") {
            environment {
                MUTE_STORE_PASSWORD = "${env.MUTE_STORE_PASSWORD}"
                MUTE_KEY_PASSWORD = "${env.MUTE_KEY_PASSWORD}"
            }
            when {
                branch 'main'
            }
            stages {
                stage("Build") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh 'chmod +x gradlew'
                        sh './gradlew clean assembleRelease bundleRelease'
                    }
//                     post {
//                         always {
//                             archiveArtifacts "app/build/outputs/apk/release/${APP_ARCHIVE_NAME}-release.apk"
//                         }
//                     }
                }
//                 stage("App Distribution") {
//                     steps {
//                         sh "${CHANGELOG_CMD}"
//                         sh './gradlew appDistributionUploadAlpha'
// //                        sh "${FIREBASE_APP_DIST_CMD}"
//                     }
//                 }
//                 stage("Deploy to Play Store") {
//                     steps {
//                         sh "./gradlew publishReleaseBundle --artifact-dir app/build/outputs/bundle/release"
//                     }
//                     post {
//                         always {
//                             archiveArtifacts "app/build/outputs/bundle/release/${APP_ARCHIVE_NAME}-release.aab"
//                         }
//                     }
//                 }
            }
        }
//         stage("Increment Version Code") {
//             when {
//                 anyOf { branch 'master'; branch 'beta'; branch 'release' }
//             }
//             steps {
//                 sh './gradlew incrementVersionCode'
//             }
//         }
    }

//     post {
//         failure {
//             mail(to: 'jeffdcamp@gmail.com',
//                     subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) has failed",
//                     body: "Please go to ${env.BUILD_URL}.")
//
//             // Notify build breaker
//             step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])])
//         }
//     }
}