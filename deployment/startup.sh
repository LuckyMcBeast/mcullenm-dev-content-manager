#!/usr/bin/env bash
echo "Starting content-manager jar..."
nohup java -jar contentmanager-0.3.0.jar &
sleep 1
echo "Done."
