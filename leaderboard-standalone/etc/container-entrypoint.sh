#! /bin/bash -e

set -x

echo "starting '$0' inside '$(pwd)'"

function make_password {
    apg -c "$(hostname)$(date +%s%N)" -a 1 -n 1 -x 10 -m 64 -M SNCL  | tr '"`/$&# ' '^%~|)_.' | tr "\'\\" '><'
    #apg -c "$(hostname)$(date +%s%N)" -a 1 -n 1 -x 10 -m 64 -M nc -E GHIJKLMNOPQRSTUVWXYZ;
    return $?
}

MYSQL_ROOT_PASSWORD=$(make_password)
echo "MYSQL_ROOT_PASSWORD='$MYSQL_ROOT_PASSWORD'" > /root/.env

sed -i "s/password = .*/password = $MYSQL_ROOT_PASSWORD/g" config.properties

exec /usr/local/bin/forego start -e /root/.env -f ../Procfile
