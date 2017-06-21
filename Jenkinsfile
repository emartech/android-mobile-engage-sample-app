node {
    withEnv(['DEVELOPMENT_MODE=true']) {
      stage('turn on screens') {
          build 'android-utils/android-turn-on-screen'
      }

      stage('init'){
          deleteDir()
          def lines = sh(script: "${env.ANDROID_HOME}platform-tools/adb devices -l", returnStdout: true)
          echo "$lines"
          git url: 'git@github.com:emartech/android-mobile-engage-sample-app.git', branch: 'master'
      }

      stage('build'){
          sh './gradlew clean build -x lint -x test'
          archiveArtifacts '**/apk/*.apk'
      }

      stage('lint'){
          sh './gradlew lint'
          archiveArtifacts '**/lint-results*.*'
      }

      stage('instrumentation-test'){
          lock(env.ANDROID_DEVICE_FARM_LOCK){
              try{
                  retry(2) {
                      sh './gradlew cAT'
                  }
              } catch (e){
                  currentBuild.result = 'FAILURE'
                  throw e
              } finally {
                  junit '**/outputs/androidTest-results/connected/*.xml'
                  archiveArtifacts '**/outputs/androidTest-results/connected/*.xml'
              }
          }
      }

      stage('turn off screens') {
          build 'android-utils/android-turn-off-screen'
      }

      stage('Deploy APK to Amazon S3') {
          sh env.AWS_DEPLOY_COMMAND
      }
    }
}