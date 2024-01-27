#!/bin/sh
docker run \
--name mock \
--restart unless-stopped \
-p 8999:8999 \
--network doctorarrival-network \
--privileged=true \
-v ~/appconfig/mock:/app/config \
-d xftxyz/mock:0.0.1
