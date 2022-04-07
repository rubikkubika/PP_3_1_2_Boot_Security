#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar \
    root@176.119.156.129:home/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa root@176.119.156.129 << EOF
pgrep java | xargs kill -9
nohup java -jar home/spring-boot_security-demo-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'