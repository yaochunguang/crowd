#!/bin/bash
PID=$(ps -ef | grep CrowdProject.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdProject is already stopped
else
echo kill $PID
kill $PID
echo CrowdProject stopped!
fi