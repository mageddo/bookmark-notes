node {

	try {

		stage('checkout'){
			checkout scm
		}
		
		stage('build'){
			sh './gradlew release --stacktrace --info'
		}

		stage('deploy'){
			sh "deploy-image mageddo.com docker-compose.yml prod hub"
		}

		currentBuild.result = "SUCCESS"
	}catch (err) {
		currentBuild.result = "FAILURE"
		throw err
	}

}