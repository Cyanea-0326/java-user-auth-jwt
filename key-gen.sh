#!/bin/sh

mkdir keys && cd keys

openssl genrsa -out jwt.key.p1 2048 && openssl rsa -in jwt.key.p1 -pubout -outform PEM -out jwt.pub
openssl pkcs8 -in jwt.key.p1 -out jwt.key -topk8 -nocrypt -outform PEM

DESTINATION_DIR=boiler/services/user-api/src/main/resources/

cd ..
if [ -d "$DESTINATION_DIR" ]; then
	echo "Destination directory exists. Removing contents..."
	rm -rf "$DESTINATION_DIR"/keys
fi
sleep 2

mv keys $DESTINATION_DIR
