#!/bin/bash
curl -s --user ${adminuser}:${adminpw} ${BASEURL}/admin/extraction | jq -r .
