#!/usr/bin/env bash
echo "Starting database..."
docker start mcullenm_dev_db
echo "Starting content-manager jar..."
nohup java -jar content-manager-0.5.1.jar &
echo $! > process
sleep 1
echo "Done."
