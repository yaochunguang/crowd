#!/bin/bash
PID=$(ps -ef | grep CrowdEureka.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdEureka is already stopped
else
echo kill $PID
kill $PID
fi