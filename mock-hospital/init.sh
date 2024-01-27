#!/bin/sh
mkdir -p ~/appconfig
docker run -p 8999:8999 --name mock -d xftxyz/mock:0.0.1
docker cp mock:/app/config ~/appconfig/mock
docker rm -f mock
