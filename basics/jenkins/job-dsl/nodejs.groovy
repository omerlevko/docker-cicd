job('NodeJS example') {
    scm {
        git('git://github.com/omerlevko/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('NodeJS-11') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install")
    }
}

job('NodeJS Docker example') {
    scm {
        git('git://github.com/omerlevko/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('NodeJS-11') 
    }
    steps {
        dockerBuildAndPublish {
            buildContext('./basics')
            repositoryName('devopscourse2019/devops') //qa / dev
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}

pipelineJob('pipeline-boilerplate-fromcode'){
    def repo = 'git://github.com/omerlevko/docker-cicd.git'
    
        triggers {
        scm('H/5 * * * *')
    }
    description("pipeline example")
        definition {
        cpsScm {
            scm {
                git{
                    remote {url(repo)}
                    branches('master')
                    scriptPath('./basics/misc/Jenkinsfile')
                    extensions{}
                    
            }
        }
    }
}
}