#!/bin/sh
echo "[INIT] Setting permissions for /rentacar-workflow-service/data and /rentacar-workflow-service/config"
chown -R 1001:1001 /rentacar-workflow-service/data /rentacar-workflow-service/config

java -jar /rentacar-workflow-service/rentacar_workflow-service.jar