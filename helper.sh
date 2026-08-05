#!/bin/bash

mvn test

mvn clean package -DskipTests

docker build -t todo-app .

docker compose down

docker compose up -d