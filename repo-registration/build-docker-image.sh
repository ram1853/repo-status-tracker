#!/bin/bash

<<<<<<< HEAD
=======
echo "Start maven build"

./mvnw clean package -Dmaven.test.skip=true

echo "Maven build completed"

>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92
VERSION=1.0.0

echo "Start docker build"

<<<<<<< HEAD
# Problem: When docker pulls the graalvm native image, it pulls the variant based on
# your host machine (here macbook - so pulls ARM64 variant).
# But AWS Lambda runs on AMD64 architecture - hence the docker image built here in my
# mac won't work when deployed to Lambda.
# Same problem even if you build the executable locally (outside of lambda) - native
# executable built on mac architecture won't work in lambda.

# Workaround - use '--platform linux/amd64' flag, so docker pulls the amd64 variant
# of the docker image - but your mac has to emulate this and the build time (particularly
# for native compilation) was taking forever (around 30 minutes - I terminated the build,
# as I feel it's not feasible for development)

# Working solution - Change Lambda Architecture to ARM64 (if other devs have a windows/linux
# machine and if they push their image, it won't work on lambda as it is in arm64 now)

docker build --provenance=false -t repo-register:${VERSION} .
=======
docker buildx build --platform linux/amd64 --provenance=false -t repo-register:${VERSION} .
>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92

echo "Docker image built"