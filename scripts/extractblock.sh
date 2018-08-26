#!/bin/bash

if [ $# -eq 0 ]
    then
        echo usage:
        echo -e '\t fromheight \t toheight'


    else
        if [ $# -eq 1 ]; then echo at least two parameters needed; fi

        if [ $# -eq 2 ]
            then
                curl -s --user ${adminuser}:${adminpw} http://35.229.87.236/admin/extractblock/$1/$2 | jq -r .
        fi
fi
