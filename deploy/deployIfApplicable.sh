#!/bin/bash

function tag(){
    IMAGE="${1}"
    docker tag ${IMAGE} ${DOCKER_ADDRESS}/${IMAGE}:${TAG}
}

function push(){
    IMAGE=${1}
    docker push ${DOCKER_ADDRESS}/${IMAGE}:${TAG}
}

function docker_tag(){
    BRANCH=$(git status | head -n1 | cut -f3 -d' ')
    if [ "${BRANCH}" == "master" ]
    then
        TAG="latest"
    else
        TAG="develop"
    fi
}

docker_tag
if [ "${TAG}x" != "x" ]
then
    DOCKER_ADDRESS="${SYTAC_DOCKER_HOST}:${SYTAC_DOCKER_PORT}"
    docker login -p=${DOCKER_REGISTRY_PASSWORD} -u=deployer -e deploy@sytac.io $DOCKER_ADDRESS
    for IMAGE in resumator-service resumator-ui resumator-load-balancer
    do
        tag ${IMAGE}
        push ${IMAGE}
    done
fi
