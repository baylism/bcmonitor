#!/bin/bash

BASEURL=http://35.229.87.236



for chain in bitcoin zcash dash
    do
        echo

        echo ========= Running ${chain} calls =========

        height=$RANDOM
        echo random height: ${height}

        blockhash=$(curl -s ${BASEURL}/api/${chain}/blockhash/${height})
        transactionhash=$(curl -s ${BASEURL}/api/${chain}/block/${blockhash} | jq  -r '.txids[0]')

        echo blockhash: ${blockhash}
        echo transactionhash: ${transactionhash}

        echo ----- Block/transaction requests -----

        echo ${BASEURL}/api/${chain}/block/${height}
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/block/${height} | jq -r . ; echo;

        echo ${BASEURL}/api/${chain}/block/${blockhash}
        curl -s -w "\n\n" ${BASEURL}/api/$chain/block/${blockhash} | jq -r . ; echo;

        echo ${BASEURL}/api/${chain}/blocks/1/2
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/blocks/1/2 | jq -r . ; echo;

        echo ${BASEURL}/api/${chain}/transaction/${transactionhash}
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/transaction/${transactionhash} | jq -r . ; echo;

        echo ${BASEURL}/api/${chain}/transactions/${blockhash}
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/transactions/${blockhash} | jq -r . ; echo;


        echo ----- Other objects -----

        echo ${BASEURL}/api/${chain}/transactionpool
        curl -s -s ${BASEURL}/api/${chain}/transactionpool -o mempool | jq -r . ; echo;

        head -c 500 mempool
        echo ...
        tail -c 500 mempool

        echo; echo;

        echo ${BASEURL}/api/${chain}/transactionpoolinfo
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/transactionpoolinfo | jq -r . ; echo;

        echo ${BASEURL}/api/${chain}/blockchaininfo
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/blockchaininfo | jq -r . ; echo;


        echo ----- Other string requests -----

        echo ${BASEURL}/api/${chain}/bestblockhash
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/bestblockhash

        echo ${BASEURL}/api/${chain}/blockhash/${height}
        curl -s -w "\n\n" ${BASEURL}/api/${chain}/blockhash/${height}



    done