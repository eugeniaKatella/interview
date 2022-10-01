ARG _BRANCH=develop
FROM eu.gcr.io/quinyx-cloud-infrastructure/neo-service-11:latest-$_BRANCH-UTC

COPY target/*.jar /opt/quinyx/service.jar
EXPOSE 8010