#!/bin/bash
PID=$(ps -ef | grep CrowdZuul.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdZuul is already stopped
else
echo kill $PID
kill $PID
echo CrowdZuul stopped!
fi