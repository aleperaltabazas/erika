pipeline {

    agent any

    tools {
        jdk "$JDK_VERSION"
        maven "$MAVEN_VERSION"
    }

    environment {
        MAVEN_VERSION = 'mvn3.5.2'
        SBT_VERSION = 'sbt1.3.0'
        JDK_VERSION = 'jdk1.8'
        PROJECT_ROOT = "."
        REPO_URL = "aleperaltabazas/scala-uml"
    }

    stages {
        stage("checkout") {
            steps {
                checkout scm
            }
        }

        stage("compile") {
            steps {
                dir(PROJECT_ROOT) {
                    sh "sbt clean compile"
                }
            }
        }

        stage("test") {
            steps {
                sh "sbt test"
            }
        }

    }
}
