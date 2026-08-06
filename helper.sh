#!/bin/bash

echo "Step 3: Building Docker image"
docker build -t todo-app .

echo "Step 4: Stopping containers"
# Maven steps removed because they are now run in GitHub Actions!"
docker compose down

echo "Step 5: Starting containers"
docker compose up -d

echo "Deployment completed!"