#!/bin/sh

HOST="localhost"

echo "Entering FIC2Lab smoke test sequence. Vendor's validation procedure of the Leaderboard SE engaged. Target host: $HOST"

#echo "Run smoke test for <your specific test description>"

# create a game table 'checkrunning'
curl -H "Content-Type: application/json" -v -X PUT http://130.206.83.3:4567/lb/checkrunning
# get return code of command to show ranked list; 200 is ok, anything else is an error
RETCODE=$(curl --max-time 5 -o /dev/null -sw '%{http_code}' http://130.206.83.3:4567/lb/checkrunning/rankedlist)
if [ "$RETCODE" == "200" ]; then
	echo "Curl command for leaderboard is OK."
else
	echo "Curl command for leaderboard failed. Validation procedure terminated."
	echo "Debug information: HTTP code $RETCODE instead of expected 200 from SHOST"
	exit 1;
fi

echo "Smoke test completed. Vendor component validation procedure succeeded. Over."
