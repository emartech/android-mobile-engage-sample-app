@Library(['android-pipeline', 'general-pipeline']) _

node('android') {
    withEnv(['DEVELOPMENT_MODE=true']) {
        timeout(15) {
            withSlack channel: 'jenkins', {
                stage("init") {
                    deleteDir()
                    deviceCount shouldBe: env.ANDROID_DEVICE_COUNT,
                            action: { devices, message ->
                                slackMessage channel: 'jenkins', text: message
                            }
                    git url: 'git@github.com:emartech/android-mobile-engage-sample-app.git', branch: 'master'

                    def testFileCount = sh(returnStdout: true, script: 'find . -name  "*Test.java" | wc -l').trim() as Integer
                    def timeoutRuleCount = sh(returnStdout: true, script: 'grep -r "^\\s*public Timeout globalTimeout = Timeout.seconds(30);" . | wc -l').trim() as Integer
                    if (testFileCount != timeoutRuleCount) {
                        error("$testFileCount tests found, but only $timeoutRuleCount timeout rules!")
                    }
                }

                stage("build") {
                    androidBuild andArchive: '**/apk/*.apk'
                }

                stage('lint') {
                    androidLint andArchive: '**/lint-results*.*'
                }

                stage("instrumentation-test") {
                    androidInstrumentationTest withScreenOn: true, withLock: env.ANDROID_DEVICE_FARM_LOCK, withRetryCount: 2, andArchive: '**/outputs/androidTest-results/connected/*.xml'
                }

                stage('Deploy APK to Amazon S3') {
                    sh env.AWS_DEPLOY_COMMAND
                }
            }
        }
    }
}
