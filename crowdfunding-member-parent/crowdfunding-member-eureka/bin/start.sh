#!/bin/bash
echo stop CrowdEureka
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdEureka.jar > catalina.out  2>&1 &
echo CrowdEureka started!