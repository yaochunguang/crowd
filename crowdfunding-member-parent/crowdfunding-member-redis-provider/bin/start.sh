#!/bin/bash
echo stop CrowdRedisProvider
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdRedisProvider.jar > catalina.out  2>&1 &
echo CrowdRedisProvider started!