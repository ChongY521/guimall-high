# docker有关操作笔记

>  docker stats 查看内存占用情况

## 1、安装mysql

```sh
mkdir -p /docker-data/mysql/conf

vi /docker-data/mysql/conf/my.conf


# my.conf 内容
[client]
default-character-set=utf8

[mysql]
#设置mysql客户端默认字符集
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection=utf8_unicode_ci'
init_connect='SET NAMES utf8'
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
character-set-client=utf8
collation-server=utf8_general_ci
# 允许最大连接数
max_connections=200
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 跳过权限检查
skip-character-set-client-handshake
# 跳过域名解析
skip-name-resolve





docker run -p 3306:3306 --name mysql \
-v /docker-data/mysql/log:/var/log/mysql \
-v /docker-data/mysql/data:/var/lib/mysql \
-v /docker-data/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
--restart=always \
-d mysql:5.7
```

- 查看软件安装的**目录**：`whereis mysql` 

- 应用开机自启： 前提是docker 开机自启 `docker update mysql  --restart=always`

## 2、安装redis

```cmd
mkdir -p /docker-data/reids/conf
vi  /docker-data/reids/conf/redis.conf

appendonly yes  # aof 持久化
requirepass redis   # 访问密码
```

```cmd
docker run -p 6379:6379 --name redis \
-v /docker-data/redis/data:/data \
-v /docker-data/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf 
```

客户端： ` docker exec  -it redis redis-cli`



## 5、 nginx 安装

```sh
docker pull nginx:1.10
docker run --name nginx -d nginx:1.10
docker container cp nginx:/etc/nginx .
mkdir -p /docker-data/nginx/
mv nginx /docker-data/nginx/conf
rm -rf /docker-data/nginx/conf/modules
docker rm -f nginx

docker run --name nginx -p 80:80 \
-v /docker-data/nginx/html:/usr/share/nginx/html \
-v /docker-data/nginx/logs:/var/log/nginx \
-v /docker-data/nginx/conf:/etc/nginx \
-d nginx:1.10
```

## 6、Tomcat 安装

1、拉取镜像 并拷贝其中默认类容

```sh
docker pull tomcat:8.5
docker run --name tomcat -p 8080:8080 -d tomcat:8.5

# 创建文件夹
mkdir -p /docker-data/tomcat
cd /docker-data/tomcat 

# 拷贝默认配置

docker container cp tomcat:/usr/local/tomcat/conf .
docker container cp tomcat:/usr/local/tomcat/webapps .
docker container cp tomcat:/usr/local/tomcat/logs .

# 停止、删除容器
docker stop tomcat
docker rm -f tomcat
```

2、启动容器

```sh
docker run --name tomcat \
-v /docker-data/tomcat/webapps:/usr/local/tomcat/webapps \
-v /docker-data/tomcat/logs:/usr/local/tomcat/logs \
-v /docker-data/tomcat/conf:/usr/local/tomcat/conf \
-p 8080:8080 -d tomcat:8.5
```

3、测试

```sh
cd /docker-data/tomcat/webapps
mkdir abc
echo "<h2>hello tomcat</h2>" >> index.html

#访问   ip:8080/abc
```







