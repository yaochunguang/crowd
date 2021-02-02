#!/bin/bash
PID=$(ps -ef | grep CrowdRedisProvider.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdRedisProvider is already stopped
else
echo kill $PID
kill $PID
echo CrowdRedisProvider stopped!
fi