#! /bin/bash -e

USAGE="Usage: $0 destination port timeout"
EXAMPLE1="$O localhost 80 141s"
EXAMPLE2="$O 191.65.9.101 3m"


if [ "$#" != "3" ]; then
    echo -e "$USAGE"
    echo -e "\texample: $EXAMPLE1"
    echo -e "\texample: $EXAMPLE2"
    exit 99
fi

DST=$1
PORT=$2
TIMEOUT=$3

exec /usr/bin/timeout -k 3s $TIMEOUT /usr/local/bin/waitForPortEndlessly.sh $DST $PORT
