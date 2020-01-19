# centos7安装nginx之前可能需要的环境

安装之前需要的环境

安装gcc：

​	使用yum命令安装：yum -y install gcc gcc-c++ kernel-devel //安装gcc、c++编译器以及内核文件

安装pcre

​	下载：wget https://ftp.pcre.org/pub/pcre/pcre-8.43.tar.gz

​	解压：tar -zxvf ...

​	编译：./configure,make,make install

安装zlib

​	下载：wget http://www.zlib.net/zlib-1.2.11.tar.gz，解压，编译

# centos7安装nginx

直接安装：利用操作系统自带的工具：yum，apt-get

编译：nginx二进制文件会把模块直接编译进来，官方模板并不是都默认开启，添加第三方模块必须使用编译模式安装

下载：wget http://nginx.org/download/nginx-1.16.1.tar.gz

解压：tar -zxvf ...

进入源码目录：

​	auto：辅助configure脚本执行

​	CHANGE：版本特性

​	conf：示例文件，nginx安装好以后，方便运维配置，会把示例文件拷贝到安装目录

​	configure：脚本，生成中间文件，执行编译前必备动作

​	contrib：提供vim工具（vim打开配置文件颜色没有变化，nginx语法没有配置到vim中），使用vim工具：复制contrib中所有vim文件到自己的目录中：cp -r contrib/vim/* ~/.vim，没有文件先创建（mkdir ~/.vim）

​	html：两个标准html文件

​	man：nginx帮助文件：man ./nginx.8

​	src：源代码

查看nginx支持的参数：./configure --help | more

​	第一类参数：nginx执行中寻找的路径：一般指定prefix目录即可，其它目录会在prefix目录下建相应的文件夹

​	第二类参数：nginx使用或不使用的模块：前缀通常是with（默认不会编译）或without（默认编译）

​	第三类参数：特殊参数，使用gcc编译需要什么优化参数，打印debug日志，添加第三方模块等

编译：./configure --prefix=/home/nginx（nginx安装目录）

configure执行完成后，会生成objs文件夹，里面是生成的中间文件：

​	ngx_modules.c文件：决定接下来编译哪些模块会被编译进nginx

执行make编译：make

编译完成后，会生成大量的中间文件及最终运行的nginx二进制文件，也在objs目录下

​	如果nginx版本升级，不能执行make install，还需要从这里把目标文件nginx拷贝到nginx安装目录中

​	c语言编译时生成的所有的中间文件都会放到src目录下

​	如果使用动态模块，动态模块编译会生成so动态文件，也会放在objs目录下

使用make install，首次安装时可以使用这个命令

安装完成后，去prefix指定的安装目录

​	最主要的nginx二进制文件在sbin目录下

​	决定nginx功能的配置文件在conf目录下，conf目录下的文件其实是源代码中conf目录下文件拷贝了一份过来

​	access.log和error.log在logs目录下

# Nginx配置语法

配置文件由指令与指令块构成

每条指令以分号（;）结尾，指令与参数间以空格符号分割

指令块以大括号（{}）将多条指令组织在一起

include语句允许组合多个配置文件以提升可维护性

使用#符号添加注释，提高可读性

使用$符号使用变量

部分指令的参数支持正则表达式：location

时间，空间

http配置的指令块

​	http：http模块去解析

​	upstream：上游服务，与tomcat等企业内网其他服务交互

​	server：对应一个或一组域名

​	location：url表达式

# Nginx命令行

格式：nginx -s reload

帮助：-? -h

使用指定的配置文件：-c，人为的指定其它的配置文件，-c 配置文件路径

指定配置指令：-g，config目录里面有很多条指令，我们可以在命令行中覆盖

指定运行目录：-p，替换config中定义好的目录，log

发送信号：-s，操作运行中的进程

​	stop：立刻停止服务

​	quit：优雅的停止服务

​	reload：重载配置文件（优雅的停止服务方式）

​	reopen：重新开始记录日志文件

测试配置文件是否有语法错误：-t -T

打印nginx的版本信息、编译信息等：-v（版本信息）， -V（编译时所有的参数）

默认情况下，编译出来的nginx会寻找执行configure时指定的位置的配置文件

重载配置文件：修改配置文件，./nginx -s reload

热部署：

​	备份原来的文件夹（sbin里面的nginx文件夹）：cp nginx nginx.old

​	更换二进制文件，将编译好的最新版本nginx的二进制文件复制过来，替换正在运行的进程的nginx二进制文件：cp -r nginx 运行的nginx -f

​	给正在运行的nignx master进程发送信号（告诉它开始热部署）：kill -USR2 master进程号

​	告诉老master进程优雅的关闭work进程：kill -WINCH 老master进程号

​	老master进程不会自动退出，可以使用reload命令重新把老的work进程拉起来，允许版本回退

切割日志文件：

​	拷贝一份日志：mv access.log bak.log

​	执行命令：../sbin/nginx -s reopen，重新生成access.log（向nginx主进程发送USR1信号，kill -USR1 进程号）

# 做为静态资源web服务器

将静态资源（dlib）拷贝到nginx安装目录（nginx/dlib）

配置nginx.conf

```conf
#如果请求出现403 Forbidden，可能需要设置user
user root;
...
http{
	server{
        # 设置监听的端口
        listen	8080;
        # 记录日志，日志位置，采用格式
        access_log logs/access.log main;
        ...
        # 请求的url，指定url后缀与文件目录后缀一一对应
        location / {
            # 配置静态资源文件夹
            # 使用alias
            # 使用root会把url路径带到文件目录中来
            alias dlib/;
            # 展示文件及文件夹目录结构信息（共享静态资源）
            autoindex on;
            # 内置变量，限制访问速度，每秒向浏览器传输1k字节
            set $limit_rate 1k;
        }
    }
    # 文本文件可以做gzip压缩，传输的字节数会大幅度极少。打开gzip，off置为on
    gzip on;
    # 小于多少字节不进行压缩
    gzip_min_length 1;
    # 压缩级别
    gzip_comp_level 2;
    # 针对某些类型的文件进行gzip压缩
    gzip_types text/plain application/x-javascript ...

    # $remote_addr：远端的ip地址（浏览器客户端的ip地址）
    # $remote_user
    # $time_local：当时时间
    # $request
    # $status：200,301（重定向），403（拒绝访问）
    # $body_bytes_sent
    # $http_referer
    # $http_user_agent
    # $http_x_forwarded_for
    # $gzip_ratio：当时使用的压缩比率
    # access日志格式，命名为main
    log_format main '$remote_addr'
    ...
}
...
```

重新加载：../sbin/nginx -s reload

文本文件压缩传输：gzip on;

展示文件及文件夹目录结构信息：autoindex on;

公网带宽有限，并发用户访问形成争抢关系。有时候需要限制访问大文件速度，以期望分离出足够的带宽给必要的小文件：set $limit_rate 1k;

记录access日志：log_format，access_log

外部访问不到可能需要先关闭防火墙：sudo systemctl stop firewalld #临时关闭

# 做为反向代理

上游服务要处理很复杂的业务逻辑，讲求开发效率，性能并不怎么样，使用nginx做为反向代理，可以由一台nginx，把请求按照负载均衡算法代理给多台上游服务器工作，这样就实现了水平扩展，在用户无感知的情况下添加更多的上游服务器来提升处理性能，当上游服务器出现问题的时候，nginx可以自动的把请求从有问题出现灾难的服务器转交给正常的服务器。

上游服务器通常对公网不提供访问（nginx服务器做为上游服务器，修改nginx.conf）

```conf
server{
	# 加上127.0.0.1表示只能本机的进程来访问打开的8080端口。
	listen	127.0.0.1:8080;
}
```

搭建nginx反向代理

```conf
# 上游服务器，这一批服务器命名为local
upstream local {
	server 127.0.0.1:8080;
	...
}
server {
	# 配置域名
	server_name yangshuai.xin;
	listen 80;
	location / {
		# 因为存在反向代理，所以我们拿的变量或者值可能就会出错
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		...
		# 在需要做缓存的url路径下添加proxy_cache
		proxy_cache my_cache;
		# 设置key（做为一个整体的key），同一个url访问时对不同用户可能展示不一样，用户的变量就要放到key中
		proxy_cache_key $host$uri$is_args$args;
		# 哪些响应不返回
		proxy_cache_valid 200 304 302 1d;
		...
		# 对所有的请求代理到上游服务里
		proxy_pass http://local;
	}
	
}
...
# 配置缓存服务器，设置缓存文件目录，文件的命名方式，文件关键字（key）放到共享内存中，
proxy_cache_path /tmp/nginxcache levels=1:2 keys_zone=my_cache:10m max_size=10g inactive=60m use_temp_path=off;
```

配置上游服务器地址：upstream

所有请求代理到上游服务：proxy_pass

配置缓存服务器

# 编译安装的nginx服务通过systemctl管理

systemctl管理的服务文件目录：/usr/lib/systemd/system

创建nginx.service文件，绑定nginx服务

```shell
[Unit]
Description=The NGINX HTTP and reverse proxy server
Documentation=http://nginx.org/en/docs/
After=syslog.target network.target remote-fs.target nss-lookup.target
 
[Service]
Type=forking
PIDFile=/home/shuai/nginx/logs/nginx.pid  
ExecStartPre=/home/shuai/nginx/sbin/nginx -t
ExecStart=/home/shuai/nginx/sbin/nginx
ExecReload=/home/shuai/nginx/sbin/nginx -s reload
ExecStop=/usr/bin/kill -s QUIT $MAINPID
PrivateTmp=true
 
[Install]
WantedBy=multi-user.target
```

启动nginx：systemctl start nginx

关闭nginx：systemctl stop nginx

