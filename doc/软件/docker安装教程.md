docker官方文档地址： https://docs.docker.com/engine/install/centos/

## centos 7.5 docker安装教程

官方安装地址: https://docs.docker.com/install/linux/docker-ce/centos/

1、查看Linux版本

```sh
cat /etc/redhat-release
```

2、yum安装gcc相关

```sh
yum -y install gcc gcc-c++
```

3、卸载旧版本

```sh
yum -y remove docker docker-common docker-selinux docker-engine
```

4、安装需要的软件包

```sh
yum install -y yum-utils device-mapper-persistent-data lvm2
```

5、设置stable镜像仓库

```sh
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

6、更新yum软件包索引

```sh
yum makecache fast
```

7、安装DOCKER CE

```sh
yum -y install docker-ce
```

8、启动docker

```sh
systemctl start docker  # 启动
systemctl status docker # 查看docker 状态
```

9、配置镜像加速

```sh
mkdir -p /etc/docker
tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://eqhg7vv5.mirror.aliyuncs.com"]
}
EOF
systemctl daemon-reload
systemctl restart docker
```

10、docker 开机自启

```sh
sudo systemctl enable docker.service
```

11、卸载

```sh
systemctl stop docker 
yum -y remove docker-ce
rm -rf /var/lib/docker
```

systemctl list-unit-files
