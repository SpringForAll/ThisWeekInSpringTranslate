#!/bin/bash
source /etc/profile
#pname=$1
pname=officialblog-1.0-SNAPSHOT.jar
puser=root


pid=`ps aux | grep $pname | grep $puser | grep -v grep | awk '{print $2}'`

if [[ -z $pid ]]; then
    echo "I can NOT find $pname running by $puser"
fi

chmod 777 /home/blog/officialblog-1.0-SNAPSHOT.jar
kill -9 $pid >/dev/null 2>&1
cd /home/blog/
exec nohup java  -jar $pname --cacheType=single  --spring.profiles.active=prod 5 >>server.log &
tail -f server.log
