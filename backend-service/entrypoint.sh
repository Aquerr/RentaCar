#!/bin/sh
echo "[INIT] Setting permissions for /rentacar-backend-service/data and /rentacar-backend-service/config"
chown -R 1001:1001 /rentacar-backend-service/data /rentacar-backend-service/config

java -jar /rentacar-backend-service/rentacar-backend-service.jar