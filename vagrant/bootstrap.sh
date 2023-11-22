#!/bin/bash
apt-get update -y
#apt-get dist-upgrade -y
apt-get install -y docker.io docker-compose
adduser vagrant docker
cat > /etc/docker/daemon.json <<EOF
{
  "registry-mirrors": ["http://172.18.48.9:5000"]
}
EOF

