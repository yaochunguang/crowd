#!/bin/bash
echo stop CrowdEureka
source stop.sh
nohup java -jar -Dloader.path=lib,config ../CrowdEureka.jar > ../logs/catalina.out  2>&1 &
