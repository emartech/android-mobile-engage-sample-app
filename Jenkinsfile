@Library(['android-pipeline', 'general-pipeline']) _

node {
    withEnv(['DEVELOPMENT_MODE=true']) {
        withSlack channel: 'jenkins', {
            stage("init") {
                deleteDir()
                deviceCount shouldBe: env.ANDROID_DEVICE_COUNT,
                        action: { devices, message ->
                            slackMessage channel: 'jenkins', text: message
                        }
                git url: 'git@github.com:emartech/android-mobile-engage-sample-app.git', branch: 'master'
            }

            stage("build") {
                build andArchive: '**/apk/*.apk'
            }

            stage('lint') {
                lint andArchive: '**/lint-results*.*'
            }

            stage("instrumentation-test") {
                instrumentationTest withScreenOn: true, withLock: env.ANDROID_DEVICE_FARM_LOCK, andArchive: '**/outputs/androidTest-results/connected/*.xml'
            }

            stage('Deploy APK to Amazon S3') {
                sh env.AWS_DEPLOY_COMMAND
            }
        }
    }
}