def GITHUB_HOST = 'https://github.optum.com/optumid'
def GITHUB_PARENT = "${GITHUB_HOST}/oid-parent.git"
def buildCmd = "--batch-mode -U clean install ";
env.PARENT_URL = GITHUB_PARENT;


stage 'Build'
    node('docker-maven-slave') {
        withEnv([
        'USR_EMAIL=abhinaba.chakraborty@optum.com',
        'OSE_PROJECT=insurance-system',
        'OSE_APP=employeeapp',
        'OSE_SERVER=https://ose-elr-dmz.optum.com',
        'DOCK_REPO=employeeapp',
        'DOCKER=docker',
        'DOCKER_HOST=jenkins.optum.com:30303',
        'DOCKER_HUB=docker.optum.com']) {
            withCredentials([[
            $class: 'UsernamePasswordMultiBinding',
            credentialsId: 'DockerCredsAbhi',
            passwordVariable: 'DOCKER_PW',
            usernameVariable: 'DOCKER_USER']]) {
                try {


                   git credentialsId: 'AbhiOptumGitCreds', url: 'https://github.optum.com/achakr13/employeeapp.git'

                   env.PATH = "/tools/maven/apache-maven-${env.MAVEN_VERSION}/bin:${env.PATH}"
                   dir("")
                   {
                   sh 'ls'
                   sh 'mvn --batch-mode -U clean install'

                   step([$class: 'ArtifactArchiver',
                            artifacts: '**/*.war, **/*.jar',
                            excludes: null])

                    stage 'Docker Image build & Push'
                    //clean
                    sh '$DOCKER -H $DOCKER_HOST images|grep "$DOCKER_USER/$DOCK_REPO"|awk \' { print $3 } \'|xargs -0 $DOCKER -H $DOCKER_HOST rmi -f > /dev/null || true'

                    //build from docker file and login to registry
                    sh '$DOCKER -H $DOCKER_HOST build --build-arg profiles=cloud --force-rm --no-cache --pull --rm=true -t $DOCKER_USER/$DOCK_REPO .'
                    sh '$DOCKER -H $DOCKER_HOST login -u $DOCKER_USER -p $DOCKER_PW $DOCKER_HUB'

                    //push the images with different tags
                    sh '$DOCKER -H $DOCKER_HOST tag $DOCKER_USER/$DOCK_REPO $DOCKER_HUB/$DOCKER_USER/$DOCK_REPO:latest'
                    sh '$DOCKER -H $DOCKER_HOST push $DOCKER_HUB/$DOCKER_USER/$DOCK_REPO:latest'
                    }
                }
                catch (err) {
                    notify("Failed ${err}")
                    currentBuild.result = 'FAILURE'
                }
            }
        }
    }


input 'Deploy to Dev Environment?'

node('docker-maven-slave') {
    withEnv([
    'DOCKER_USER=achakr13',
    'USR_NAME_OSE=achakr13',
    'USR_EMAIL=abhinaba.chakraborty@optum.com',
    'OSE_PROJECT=insurance-system',
    'OSE_APP=employeeapp',
    'OSE_SERVER=https://ose-elr-dmz.optum.com',
    'DOCK_REPO=employeeapp',
    'DOCKER=docker',
    'OC=/tools/oc/oc-3.2.1.1/oc',
    'DCKER_HOST=jenkins.optum.com:30303',
    'DOCKER_HUB=docker.optum.com']){
        withCredentials([[
        $class: 'UsernamePasswordMultiBinding',
        credentialsId: 'AbhiOSECred',
        passwordVariable: 'DOC_OSE_PW',
        usernameVariable: 'USR_NAME_OSE']]) {
            try{
                stage 'Deploy to Dev'
                sh '''
                    oc login --server=$OSE_SERVER -u $USR_NAME_OSE -p $DOC_OSE_PW --insecure-skip-tls-verify=true
                    oc project $OSE_PROJECT
                    BUILD_CONFIG=`oc get dc | grep ${OSE_APP} | tail -1 | awk \'{print $1}\'`
                    if [ "$BUILD_CONFIG" == "$OSE_APP" ]; then
                        oc delete rc $(oc get rc | grep $OSE_APP | awk '$2 == 0 {print $1}') || true
                        #/tools/oc/oc-3.2.1.1/oc import-image $OSE_APP:latest --all=false
                        /tools/oc/oc-3.2.1.1/oc deploy $OSE_APP --latest -n $OSE_PROJECT
                        sleep 10
                        #oc deploy $OSE_APP --latest -n $OSE_PROJECT
                    else
                        oc new-app docker.optum.com/$DOCKER_USER/$DOCK_REPO:latest --name=$OSE_APP
                        oc deploy $OSE_APP --latest -n $OSE_PROJECT
                        oc env dc/$OSE_APP PLATFORM=dev
                    fi;'''
            }
            catch (err) {
                currentBuild.result = 'FAILURE'
            }
        }
    }

}
