#!/bin/bash
echo stop CrowdZuul
source stop.sh
nohup java -jar -Dloader.path=lib,resources CrowdZuul.jar > catalina.out  2>&1 &
echo CrowdZuul started!