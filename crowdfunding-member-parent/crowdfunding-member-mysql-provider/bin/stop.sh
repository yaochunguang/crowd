#!/bin/bash
PID=$(ps -ef | grep CrowdMySqlProvider.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo CrowdMySqlProvider is already stopped
else
echo kill $PID
kill $PID
echo CrowdMySqlProvider stoped!
fi