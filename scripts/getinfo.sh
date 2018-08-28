#!/bin/bash
curl -s --user ${adminuser}:${adminpw} http://35.229.87.236/admin/extraction | jq -r .
