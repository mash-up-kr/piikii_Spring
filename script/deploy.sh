#!/bin/bash
source .env

REGISTRY_URL=${REGISTRY_URL}
IMAGE_NAME="piikii"
TAG="latest"
CONTAINER_NAME="piikii"

docker login -u ${NCP_API_ACCESS_KEY} -p ${NCP_API_SECRET_KEY} ${REGISTRY_URL}

docker pull ${REGISTRY_URL}/${IMAGE_NAME}:${TAG}

if [ "$(docker ps -a -q -f name=${CONTAINER_NAME})" ]; then
  docker stop ${CONTAINER_NAME}
  docker rm ${CONTAINER_NAME}
fi

docker run -d --name ${CONTAINER_NAME} \
  -e SECRET_MANAGER_TOKEN=${SECRET_MANAGER_TOKEN} \
  -e SECRET_MANAGER_WORKSPACE=${SECRET_MANAGER_WORKSPACE} \
  -e NCP_API_ACCESS_KEY=${NCP_API_ACCESS_KEY} \
  -e NCP_API_SECRET_KEY=${NCP_API_SECRET_KEY} \
  ${REGISTRY_URL}/${IMAGE_NAME}:${TAG}
