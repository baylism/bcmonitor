#!/bin/bash

SECONDS=0

if [ $# -eq 0 ]
    then
        echo usage:
        echo -e '\t blockchain \t fromheight \t toheight'

    else
        if [ $# -ne 3 ]; then echo three parameters needed; fi

        if [ $# -eq 3 ]
            then
                curl -s --user ${adminuser}:${adminpw} http://35.229.87.236/admin/extractblock/$1/$2/$3 | jq -r .

                duration=$SECONDS
                echo "$(($duration / 60)) minutes and $(($duration % 60)) seconds elapsed."
        fi
fi