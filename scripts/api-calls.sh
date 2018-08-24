#!/bin/bash

BASEURL=http://35.229.87.236



for chain in zcash dash
    do
        echo

        echo ========= Running ${chain} calls =========

        height=$RANDOM
#        blockhash=$(curl -s $BASEURL/api/$chain/block/$height | jq  -r '.hash')
        blockhash=$(curl -s ${BASEURL}/api/${chain}/blockhash/${height} | jq -r .)
        transactionhash=$(curl -s ${BASEURL}/api/${chain}/block/${blockhash} | jq  -r '.txids[0]')

        echo blockhash: ${blockhash}
        echo transactionhash: ${transactionhash}

        echo ----- Block/transaction requests -----

        echo ${BASEURL}/api/${chain}/block/${height}
        curl -w "\n\n" ${BASEURL}/api/${chain}/block/${height}

        echo ${BASEURL}/api/${chain}/block/${blockhash}
        curl -w "\n\n" ${BASEURL}/api/$chain/block/${blockhash}
        
        echo ${BASEURL}/api/${chain}/blocks/1/2
        curl -w "\n\n" ${BASEURL}/api/${chain}/blocks/1/2

        echo ${BASEURL}/api/${chain}/transaction/${transactionhash}
        curl -w "\n\n" ${BASEURL}/api/${chain}/transaction/${transactionhash}

        echo ${BASEURL}/api/${chain}/transactions/${blockhash}
        curl -w "\n\n" ${BASEURL}/api/${chain}/transactions/${blockhash}


        echo ----- Other objects -----

        echo ${BASEURL}/api/${chain}/transactionpool
        curl -s ${BASEURL}/api/${chain}/transactionpool -o mempool

        head -c 500 mempool
        echo ...
        tail -c 500 mempool

        echo; echo;

        echo ${BASEURL}/api/${chain}/transactionpoolinfo
        curl -w "\n\n" ${BASEURL}/api/${chain}/transactionpoolinfo

        echo ${BASEURL}/api/${chain}/blockchaininfo
        curl -w "\n\n" ${BASEURL}/api/${chain}/blockchaininfo


        echo ----- Other string requests -----

        echo ${BASEURL}/api/${chain}/bestblockhash
        curl -w "\n\n" ${BASEURL}/api/${chain}/bestblockhash

        echo ${BASEURL}/api/${chain}/blockhash/${height}
        curl -w "\n\n" ${BASEURL}/api/${chain}/blockhash/${height}



    done