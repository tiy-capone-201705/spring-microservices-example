#!/usr/bin/env bash
echo "Starting web consumer"
pushd web-consumer
mvn spring-boot:run 1>/dev/null &
sleep 5s
popd

echo "Starting RESTful microservice"
pushd restful-microservice
mvn spring-boot:run 1>/dev/null &
popd

echo "Starting M@ microservice"
pushd mq-microservice
mvn spring-boot:run 1>/dev/null &
popd

