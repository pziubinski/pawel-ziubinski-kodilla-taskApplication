#!/usr/bin/env bash

if ./runcrud.sh; then
   echo "Runcrud done"
else
   echo "Runcrud failed"
fi

if /usr/bin/open -a "/Applications/Google Chrome.app" 'http://localhost:8080/crud/v1/task/getTasks'; then
   echo "Chrome done"
else
   echo "Chrome failed"
fi
