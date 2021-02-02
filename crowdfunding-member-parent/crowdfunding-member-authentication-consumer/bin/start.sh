#!/bin/bash
echo stop CrowdPortal
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdPortal.jar > catalina.out  2>&1 &
echo CrowdPortal started!