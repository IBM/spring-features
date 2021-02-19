#
# Copyright 2020- IBM Inc. All rights reserved
# SPDX-License-Identifier: Apache2.0
#
#!/bin/bash

# Set some variables
DOCKER_MACHINE_IP=127.0.0.1
EUREKA_PORT=8761
ZIPKIN_PORT=9411

echo ---- BUILD ALL PROJECTS ----
cd service-addressbook
mvn clean package
cd ..

cd service-client
mvn clean package
cd ..

cd service-eureka
mvn clean package
cd ..

cd service-gateway
mvn clean package
cd ..

cd service-hystrix
mvn clean package
cd ..

cd service-monitor
mvn clean package
cd ..

cd service-people
mvn clean package
cd ..

cd service-zipkin
mvn clean package
cd ..

echo ---- START ALL PROJECTS ----
cd service-eureka
mvn -DDOCKER_MACHINE_IP=${DOCKER_MACHINE_IP} -DEUREKA_PORT=${EUREKA_PORT} -DZIPKIN_PORT=${ZIPKIN_PORT} spring-boot:run &
cd ..

cd service-addressbook
mvn -DDOCKER_MACHINE_IP=${DOCKER_MACHINE_IP} -DEUREKA_PORT=${EUREKA_PORT} -DZIPKIN_PORT=${ZIPKIN_PORT} spring-boot:run &
cd ..

cd service-people
mvn -DDOCKER_MACHINE_IP=${DOCKER_MACHINE_IP} -DEUREKA_PORT=${EUREKA_PORT} -DZIPKIN_PORT=${ZIPKIN_PORT} spring-boot:run &
cd ..

cd service-client
mvn -DDOCKER_MACHINE_IP=${DOCKER_MACHINE_IP} -DEUREKA_PORT=${EUREKA_PORT} -DZIPKIN_PORT=${ZIPKIN_PORT} spring-boot:run &
cd ..