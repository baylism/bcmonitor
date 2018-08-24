#!/usr/bin/env bash

BITCOIN_HOSTNAME=bitcoin-0
BITCOIN_PORT=9998
BITCOIN_UN=
BITCOIN_PW=


SECONDS=0


for i in $(seq 1 $1);
do
    if (( $i % 100 == 0 )); then echo block ${i} ${SECONDS} seconds; fi;
    curl -s --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockhash", "params": ['"$i"'] }' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT} > /dev/null

done


duration=$SECONDS
echo "$(($duration / 60)) minutes and $(($duration % 60)) seconds elapsed."