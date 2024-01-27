#!/bin/sh
cd ../doctorarrival
chmod +x mvnw
./mvnw clean install -DskipTests
cd ../mock-hospital
chmod +x mvnw
./mvnw clean package -DskipTests

docker build -t xftxyz/mock:0.0.1 .
