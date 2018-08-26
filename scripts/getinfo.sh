#!/bin/bash
#curl --user --user ${adminuser}:${adminpw} http://localhost:8080/admin/extraction | python -m json.tool
curl -s --user --user ${adminuser}:${adminpw} http://35.229.87.236/admin/extraction | jq -r .
