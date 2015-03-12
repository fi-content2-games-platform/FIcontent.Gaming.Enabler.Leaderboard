#!/bin/sh
# create a game table 'checkrunning'
curl -H "Content-Type: application/json" -v -X PUT http://130.206.83.3:4567/lb/checkrunning
# get return code of command to show ranked list; 200 is ok, anything else is an error
RETCODE=$(curl --max-time 5 -o /dev/null -sw '%{http_code}' http://130.206.83.3:4567/lb/checkrunning/rankedlist)
if [ "$RETCODE" == "200" ]; then exit 0; else exit 1; fi