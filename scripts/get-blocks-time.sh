#!/usr/bin/env bash


SECONDS=0

curl -s http://35.229.87.236/api/bitcoin/blocks/$1/$2 > /dev/null


duration=$SECONDS
echo "$(($duration / 60)) minutes and $(($duration % 60)) seconds elapsed."