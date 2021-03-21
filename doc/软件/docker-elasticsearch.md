# 一、elasticsearch

## 1、无密码安装

```sh
docker pull elasticsearch:7.4.2
mkdir -p /docker-data/elasticsearch/config
mkdir -p /docker-data/elasticsearch/data
echo "http.host: 0.0.0.0" >> /docker-data/elasticsearch/config/elasticsearch.yml
# 循环授权  --privileged=true 
chmod 777 -R /docker-data/elasticsearch
docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
--restart=always \
-e ES_JAVA_OPTS="-Xms128m -Xmx256m" \
-v /docker-data/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /docker-data/elasticsearch/data:/usr/share/elasticsearch/data  \
-v /docker-data/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.4.2
```

## 2、配置访问密码

```sh
echo "xpack.security.enabled: true" >> /docker-data/elasticsearch/config/elasticsearch.yml
docker restart elasticsearch
# 进入容器
docker exec -it elasticsearch /bin/bash
cd bin
elasticsearch-setup-passwords interactive     #此步为手动设置密码

用户 ：  elastic     apm_system   kibana   kibana_system    logstash_system    beats_system    remote_monitoring_user
```

## 3、集群

[es集群安装地址](https://www.cnblogs.com/woshimrf/p/docker-es7.html)   https://www.cnblogs.com/woshimrf/p/docker-es7.html

## 4、插件

### 1) 、ik 分词：

下载地址：https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.4.2/elasticsearch-analysis-ik-7.4.2.zip

常用的两个分词器

```sh
ik_smart     # 智能分词
ik_max_word  # 最大分词
```

自定义词库



```sh
cd  /docker-data/nginx/html
mkdir es
vi es/fenci.txt

vi /docker-data/elasticsearch/plugins/ik/config/IKAnalyzer.cfg.xml

<properties>
        <comment>IK Analyzer 扩展配置</comment>
        <!--用户可以在这里配置自己的扩展字典 -->
        <entry key="ext_dict"></entry>
         <!--用户可以在这里配置自己的扩展停止词字典-->
        <entry key="ext_stopwords"></entry>
        <!--用户可以在这里配置远程扩展字典 -->
        <entry key="remote_ext_dict">http://66.88.88.200/es/fenci.txt</entry>
        <!--用户可以在这里配置远程扩展停止词字典-->
        <!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>
```

# 二、kibana

## 1、无密码安装

```sh
docker pull kibana:7.4.2
docker run --name kibana -e ELASTICSEARCH_HOSTS=http://66.88.88.200:9200 -p 5601:5601 -d kibana:7.4.2
```

## 2、密码安装

```sh
mkdir -p /docker-data/kibana/conf
vi /docker-data/kibana/conf/kibana.yml

server.name: kibana
# kibana的主机地址 0.0.0.0可表示监听所有IP
server.host: "0.0.0.0"
# kibana访问es的URL
elasticsearch.hosts: [ "http://66.88.88.200:9200" ]
elasticsearch.username: 'kibana'
elasticsearch.password: 'elasticsearch'
# 显示登陆页面
xpack.monitoring.ui.container.elasticsearch.enabled: true
# 语言
i18n.locale: "zh-CN"

docker run --name=kibana -p 5601:5601 \
--restart=always  --privileged=true \
-v /docker-data/kibana/conf/kibana.yml:/usr/share/kibana/config/kibana.yml 
-d kibana:7.4.2
```

