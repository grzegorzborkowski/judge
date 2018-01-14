#!/usr/bin/env bash
BACKEND_ADDRESS=$1

if [ -z "$BACKEND_ADDRESS" ]
then
    echo "Please provide backend address param"
    exit 1
fi

echo "Backend server address: $BACKEND_ADDRESS"

##########################################################################################################################

#git clone https://github.com/grzegorzborkowski/judge.git

#########################################################################################################################

### Get required dependencies

cd judge-external-runner/go
bash external_dependencies.sh
cd ..
cd ..
##########################################################################################################################

### Create a JSON file with backend_address and save it to new file so that frontend can retrieve it

cd judge-frontend/
printf '{"BACKEND_ADDRESS": "%s"}\n' "$BACKEND_ADDRESS" > backend_address.json