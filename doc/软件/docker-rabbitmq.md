```sh
docker run -d --name rabbitmq  \
-p 5671:5671 -p 5672:5672 \
-p 4369:4369 -p 25672:25672 \
-p 15671:15671 -p 15672:15672 \
--restart=always \
rabbitmq:management

4369 , 25672 (erlang 发现 & 集群端口)
5671 , 5672 AMQP 端口
61613,61614  STQMP 协议端口
15671
15672 web管理后台端口
1883，8883 MQTT 协议端口

5671,5672,4369,25672,15671,15672
guest/guest
```

