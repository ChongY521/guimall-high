## 1、虚拟机安装

VirtualBox 下载地址  ： https://www.virtualbox.org/wiki/Downloads

vagrant 下载地址 ： https://www.vagrantup.com/downloads

验证： `vagrant  出现提示信息即可

vagrant 镜像地址： https://app.vagrantup.com/boxes/search

- 创建虚拟机 `vagrant  init centos/7`  用户下有一个Vagrantfile

- 启动 ： `vagrant up`

- 停止： `vagrant stop `

- 重启： `vargrant reload`

- 接入 ： `vagrant ssh `

- sudo 密码： vagrant

  ![端口转发](.\assert\端口转发.png)

修改 网络地址： Vagrantfile   

```cmd
config.vm.network "private_network" ,ip:"192.168.6.6"  (ifconfig 看window地址 ，只需要改最后一位ip地址)
vi etc/ssh/sshd_config      PasswordAuthentication yes # 允许密码登陆
service sshd restart     # 重启服务
```

### 配置yum源

```
# 1、备份yum 源
mv /etc/yum.repos.d/CentOS-Base.repo  /etc/yum.repos.d/CentOS-Base.repo.backup
# 2、 使用新的yum 源
curl -o /etc/yum.repos.d/CentOS-Base.repo  http://mirrors.163.com/.help/CentOS-Base-163.repo
# 3、生成缓存
yum makecache
```

### 配置网络

```sh
 vi /etc/sysconfig/network-scripts/eth0

TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
DEVICE=ens33
ONBOOT=yes
IPADDR=66.88.88.200
GATEWAY=66.88.88.1
NETMASK=255.255.255.0
DNS1=8.8.8.8
DNS2=114.114.114.114
IPV6_PRIVACY=no

service network restart 重启网络
free -m  查看内存使用情况
```



## 2、docker安装

虚拟化容器技术。Docker基于镜像，可以秒级启动各种容器。每一种容器都是一个完整的运行 环境，容器之间互相隔离。

![docker结构](.\assert\docker结构.png)



