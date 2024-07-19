#!/bin/sh

# If you can't exec it, check permissions [chmod +x init.sh]
DEV_DIR=./boiler

cp ${DEV_DIR}/.env.example ${DEV_DIR}/.env
rm -rf .git && git init
./key-gen.sh
# rm ./init.sh 
