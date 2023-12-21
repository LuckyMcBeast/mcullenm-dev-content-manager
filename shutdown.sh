#!/usr/bin/env bash
echo "Shutting down content-manager jar..."
cat process | xargs pkill
echo "Shutting down database"
docker stop mcullenm_dev_db
echo "Done."