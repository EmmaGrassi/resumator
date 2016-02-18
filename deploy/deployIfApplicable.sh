#!/bin/sh
if [ $(git -C ${SEMAPHORE_PROJECT_DIR} rev-parse --abbrev-ref HEAD) = "master" ]
then
    dockerAddress="${SYTAC_DOCKER_HOST}:${SYTAC_DOCKER_PORT}"

    docker login -p=${DOCKER_REGISTRY_PASSWORD} -u=deployer -e deploy@sytac.io $dockerAddress
    docker tag resumator-service $dockerAddress/resumator-service
    docker tag resumator-ui $dockerAddress/resumator-ui
    docker tag resumator-load-balancer $dockerAddress/resumator-load-balancer
    docker push $dockerAddress/resumator-service
    docker push $dockerAddress/resumator-ui
    docker push $dockerAddress/resumator-load-balancer
fi
