#!/bin/bash
nohup java -jar spring-server-hardware-collector-agent-1.0-SNAPSHOT.jar --server.hardware.collector.port=9390 --server.hardware.collector.ip=x.x.x.x
curl -v http://localhost:8081/api/send-config
