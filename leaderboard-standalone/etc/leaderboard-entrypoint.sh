#! /bin/bash -e

set -x

waitForPortWithTimeout.sh localhost 3306 1m

exec /usr/bin/java -Dlog4j.debug -jar -Dlog4j.configuration=file:/root/etc/log4j.properties -Djava.util.logging.config.file=/root/etc/logging.properties -jar leaderboard-0.3.jar
