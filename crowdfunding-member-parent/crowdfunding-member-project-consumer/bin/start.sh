#!/bin/bash
echo stop CrowdProject
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdProject.jar > catalina.out  2>&1 &
echo CrowdProject started!