#!/bin/bash
echo stop CrowdMySqlProvider
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdMySqlProvider.jar > catalina.out  2>&1 &
echo CrowdMySqlProvider started!