#!/bin/bash

BASEURL=http://35.229.87.236



for chain in zcash
    do
        echo

        echo Testing calls for $chain

        height=$RANDOM
        blockhash=$(curl -s $BASEURL/api/$chain/block/$height | jq  -r '.hash')
        transactionhash=`curl -s $BASEURL/api/$chain/block/$height | jq  -r '.txids[0]'`

        echo $BASEURL/api/$chain/block/$height
        curl -w "\n\n" $BASEURL/api/$chain/block/$height

        echo $BASEURL/api/$chain/block/$BLOCKHASH
        curl -w "\n\n" $BASEURL/api/$chain/block/$BLOCKHASH
        
        echo $BASEURL/api/$chain/blocks/1/2
        curl -w "\n\n" $BASEURL/api/$chain/blocks/1/2

        echo

        echo $BASEURL/api/$chain/transaction/$transactionhash
        curl -w "\n\n" $BASEURL/api/$chain/transaction/$transactionhash

        echo $BASEURL/api/$chain/transactions/$blockhash
        curl -w "\n\n" $BASEURL/api/$chain/transactions/$blockhash
        
        echo $BASEURL/api/$chain/transactionpoolinfo
        curl -w "\n\n" $BASEURL/api/$chain/transactionpoolinfo
        
        echo $BASEURL/api/$chain/blockchaininfo
        curl -w "\n\n" $BASEURL/api/$chain/blockchaininfo
        
        echo $BASEURL/api/$chain/bestblockhash
        curl -w "\n\n" $BASEURL/api/$chain/bestblockhash
        
        echo $BASEURL/api/$chain/blockhash/$height
        curl -w "\n\n" $BASEURL/api/$chain/blockhash/$height

    done