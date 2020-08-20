#!/bin/bash

if [ $# -eq 0 ]
    then
        echo usage:
        echo -e '\t enablesync \t \t [blockchain]'
        echo -e '\t disablesync \t \t [blockchain]'
        echo -e '\t enabletracking \t [blockchain]'
        echo -e '\t disabletracking \t [blockchain]'
        echo -e '\t setinitialheight \t [blockchain] \t [height]'

    else
        if [ $# -eq 1 ]; then echo at least two parameters needed; fi

        if [ $# -eq 2 ]
            then
                curl -s --user ${adminuser}:${adminpw} http://${BASEURL}/admin/$1/$2 | jq -r .
            else
                if [ $# -eq 3 ]
                    then
                        curl -s --user ${adminuser}:${adminpw} http://${BASEURL}/admin/$1/$2/$3 | jq -r .
                fi
        fi
fi
