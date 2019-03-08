添加root用户

sudo passwd root

切换至root用户

su root

连接不上ssh，安装openssh-server

先sudo apt-get update

sudo apt-get install openssh-server

配置ssh服务

vi /etc/ssh/sshd_config

28行，PermitRootLogin prohibit-password改成yes

重启

sudo service ssh restart

安装vim

sudo apt-get install vim

安装gcc

sudo apt-get install gcc

如果不可用

sudo apt-get install build-essential

安装jdk

vim /etc/profile

```profile
export JAVA_HOME=/usr/jdk1.8.0_111
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$CLASSPATH:$JAVA_HOME/lib:$JRE_HOME/lib
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export JAVA_HOME=/usr/jdk-8是配置jdk的主目录
export JRE_HOME=$JAVA_HOME/jre是配置jre的目录
export CLASSPATH=.:$CLASSPATH:$JAVA_HOME/lib:$JRE_HOME/lib是配置的CLASSPATH目录
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin将jdk的可执行文件目录添加到系统系统环境目录中
```

使配置生效

source /etc/profile

重命名

mv

安装nginx（保证gcc好用）

- 安装pcre
  - 下载：http://www.pcre.org/
  - 解压，进入目录执行：./configure,make,make install
- 安装zlib 
  - 下载：http://www.zlib.net/
  - 解压，进入目录执行：./configure,make,make install
- 安装nginx
  - 下载
  - 解压，进入目录执行：./configure,make,make install
- 启动
  - 进入：/usr/local/nginx/sbin（默认安装路径）
  - 执行：nginx

---

ubuntu 安装 docker

ubuntu内核版本：uname -r

安装：简单方式：sudo apt-get install -y docker.io

启动：systemctl start docker

docker版本号：docker -v

开机启动：systemctl enable docker

关闭：systemctl stop docker

查找镜像：docker search mysql（就是去这个网站搜：https://hub.docker.com/）

拉取镜像：docker pull tomcat（默认tag:latest）docker pull tomcat:标签

查看所有镜像：docker images

删除镜像：docker rmi 镜像id（IMAGE ID）

启动容器：docker run --name container-name -d image-name（container-name：自定义容器名，-d：后台运行，image-name：指定镜像模板）

	eg：docker run --name mytomcat -d tomcat:latest（latest默认可以不写）

查看运行的容器：docker ps

此时tomcat是在docker容器中启动，并不是在服务器中启动，所以无法直接在外部访问（ip:8080），需要做端口映射（所以这个容器就没用了，删除）

停止运行中的容器：docker stop 容器id（CONTAINER ID）

查看所有的容器：docker ps -a

启动容器：docker start 容器id

删除容器：docker rm 容器id（停止状态下）

运行一个能访问的容器（端口映射）：

	端口映射 -p 主机端口:容器内部端口
	
	eg：docker run --name mytomcat -d -p 8888:8080 tomcat:latest
	
	简写：docker run  -d -p 8888:8080 tomcat（不自定义名字）

访问不了尝试关闭linux防火墙：

centos:

	查看防火墙状态：service firewalld status
	
	临时关闭防火墙：service firewalld stop

ubuntu:

	ufw status
	
	ufw enable
	
	ufw  disable

查看容器日志：docker logs 容器id



新增或者修改文件

_echo 'hello world' > test_