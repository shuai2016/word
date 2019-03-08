#ubuntu 安装 docker

ubuntu内核版本：

```shell
uname -r
```

安装：简单方式：

```shell
sudo apt-get install -y docker.io
```

# docker服务

启动：

```shell
systemctl start docker
```

docker版本号：

```shell
docker -v
```

开机启动：

```shell
systemctl enable docker
```

关闭：

```shell
systemctl stop docker
```

#docker收录的软件镜像

查找镜像：

```shell
docker search mysql
```

（就是去这个网站搜：https://hub.docker.com/）

拉取镜像：

```shell
docker pull tomcat:标签
```

不写标签默认latest

```shell
docker pull tomcat
```

查看所有镜像：

```shell
docker images
```

删除镜像：

```shell
docker rmi 镜像id（IMAGE ID）
```

#docker软件容器

启动（创建）容器：

```shell
docker run --name container-name -d image-name
```

- container-name：自定义容器名，-d：后台运行，image-name：指定镜像模板

eg：

```shell
docker run --name mytomcat -d tomcat:latest（latest默认可以不写）
```

查看运行的容器：

```shell
docker ps
```

- 可以查看到容器id

此时tomcat是在docker容器中启动，并不是在服务器中启动，所以无法直接在外部访问（主机ip:8080），需要做端口映射（所以这样创建的容器就没用了，删除）

停止运行中的容器：

```shell
docker stop 容器id（CONTAINER ID）
```

查看所有的容器：

```shell
docker ps -a
```

启动容器：

```shell
docker start 容器id
```

删除容器：

```shell
docker rm 容器id（停止状态下）
```

创建一个能访问的容器（端口映射）：原来基础上增加端口映射（-p 主机端口:容器内部端口）

eg：

```shell
docker run --name mytomcat -d -p 8888:8080 tomcat:latest
```

简写：

```shell
docker run  -d -p 8888:8080 tomcat（不自定义名字）
```

访问不了尝试关闭linux防火墙：

centos:

查看防火墙状态：

```shell
service firewalld status
```

临时关闭防火墙：

```shell
service firewalld stop
```

ubuntu:

```shell
ufw status
ufw enable
ufw disable
```

查看容器日志：

```shell
docker logs 容器id
```

进入容器的路径结构：

```shell
docker exec -it 容器id /bin/bash
```

部署项目：

```shell
docker cp 项目包 容器名:/usr/local/tomcat/webapps/文件路径
```