#!/bin/bash

curl --user max:01cecfb951713cc9ac820f8c2e0b695b http://localhost:8080/admin/$1/$2 | python -m json.tool