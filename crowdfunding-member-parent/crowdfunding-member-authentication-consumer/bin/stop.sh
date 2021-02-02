#!/bin/bash
PID=$(ps -ef | grep CrowdPortal.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdPortal is already stopped
else
echo kill $PID
kill $PID
echo CrowdPortal stopped!
fi