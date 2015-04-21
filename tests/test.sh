#!/usr/bin/env bash

# How to use: ./test.sh HOST PORT
HOST=$1
PORT=$2
[ "$HOST" ] || HOST="localhost"
[ "$PORT" ] || PORT="8000"
echo "Entering FIC2Lab smoke test sequence. Vendor's validation procedure of the Leaderboard SE engaged. Target host: $HOST"


# wait for service
echo -n "Waiting service to launch"
while ! (netcat -vz localhost $2 &> /dev/null); do echo -n "."; sleep 5; done
echo ""
echo "service is running."


#echo "Run smoke test for Leaderboard"

# create a game table 'checkrunning'
curl -H "Content-Type: application/json" -v -X PUT http://$1:$2/lb/checkrunning
# get return code of command to show ranked list; 200 is ok, anything else is an error
RETCODE=$(curl --max-time 5 -o /dev/null -sw '%{http_code}' http://$1:$2/lb/checkrunning/rankedlist)
if [ "$RETCODE" == "200" ]; then
	echo "Curl command for leaderboard is OK."
else
	echo "Curl command for leaderboard failed. Validation procedure terminated."
	echo "Debug information: HTTP code $RETCODE instead of expected 200 from SHOST"
	exit 1
fi

echo "Smoke test completed. Vendor component validation procedure succeeded. Over."
