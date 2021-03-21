# 1、docker-compose.yml

```sh
mkdir -p /docker-data/fastdfs/environment
vi /docker-data/fastdfs docker-compose.yml
```

```sh
version: '2.0'
services:
  fastdfs:
    build: environment
    restart: always
    container_name: fastdfs
    volumes:
      - ./storage:/fastdfs/storage
    network_mode: host
```

```sh
cd /docker-data/fastdfs/environment
vi /docker-data/fastdfs/environment/Dockerfile
```

```sh
FROM centos
MAINTAINER aqiang9

# 更新数据源
WORKDIR /etc/apt

# 安装 wget 源
RUN yum install -y wget

# 配置yum 源
RUN wget /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
RUN yum clean all
RUN yum makecache

# 安装依赖
RUN yum install make gcc libpcre3-dev zlib1g-dev

# 复制工具包
ADD fastdf.tar.gz /usr/local/src
ADD fastdfs-nginx-module.tar.gz /usr/local/src
ADD libfastcommon.tar.gz /usr/local/src
ADD nginx.tar.gz /usr/local/src 

# 安装 libfastcommon
WORKDIR /usr/local/src/libfastcommon
RUN ./make.sh && ./make.sh install

# 安装 FastDFS
WORKDIR /usr/local/src/fastdfs
RUN ./make.sh && ./make.sh install

# 配置 FastDFS 跟踪器
ADD tracker.conf /etc/fdfs
RUN mkdir -p /fastdfs/tracker

# 配置 FastDFS 存储
ADD storage.conf /etc/fdfs
RUN mkdir -p /fastdfs/storage

# 配置 FastDFS 客户端
ADD client.conf /etc/fdfs

# 配置 fastdfs-nginx-module
ADD config /usr/local/src/fastdfs-nginx-module/src

# FastDFS 与 Nginx 集成
WORKDIR /usr/local/src/nginx
RUN ./configure --add-module=/usr/local/src/fastdfs-nginx-module/src
RUN make && make install
ADD mod_fastdfs.conf /etc/fdfs

WORKDIR /usr/local/src/fastdfs/conf
RUN cp http.conf mime.types /etc/fdfs/

# 配置 Nginx
ADD nginx.conf /usr/local/nginx/conf

COPY entrypoint.sh /usr/local/bin/
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

WORKDIR /
EXPOSE 8888
CMD ["/bin/bash"]
```

## entrypoint.sh

```sh
#!/bin/sh
/etc/init.d/fdfs_trackerd start
/etc/init.d/fdfs_storaged start
/usr/local/nginx/sbin/nginx -g 'daemon off;'
```

## tracker.conf

```sh
base_path=/fastdfs/tracker
```







请参考： https://blog.csdn.net/qq_40369435/article/details/91512431









记录



```sh
docker pull delron/fastdfs
# 构建tracker
docker run -d --network=host --name tracker \
-v /docker-data/fastdfs/tracker:/var/fdfs delron/fastdfs tracker
# 构建storage
docker run -d --network=host --name storage -e TRACKER_SERVER=ip:22122 \
-v /docker-data/fastdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage


```

