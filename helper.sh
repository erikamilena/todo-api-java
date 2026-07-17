#!/bin/bash

mvn clean package
docker build -t todo-app .
docker compose up