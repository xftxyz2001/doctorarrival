#!/bin/sh
cd ../doctorarrival
./mvnw clean install -DskipTests
cd ../mock-hospital
./mvnw clean package -DskipTests

docker build -t xftxyz/mock:0.0.1 .
