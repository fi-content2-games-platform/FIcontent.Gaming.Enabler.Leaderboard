#! /bin/bash -e

trap "exit 1" INT
trap "exit 1" TERM

until nc -z $1 $2; do
    echo "Waiting for '$1' on '$2' ..."
    sleep 2s
done
