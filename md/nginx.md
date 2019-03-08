# nignx

- 开源协议
  - GPL
  - BSD
  - apache
- lamp - > lnmp
- nginx目录
  - conf
  - html
  - logs
  - sbin
- 启动nginx
  - ./sbin/nginx
- ps aux|grep nginx
- nginx命令参数
  - nginx -t 测试配置是否正确
  - nginx -s reload 加载最新配置
  - nginx -s stop 立即停止
  - nignx -s quit 优雅停止
  - nignx -s reopen 重新打开日志
  - kill -USER2 ‘cat /usr/local/nginx/logs/nginx.pid’（进程号）快速重启
  - 去官网看有哪些参数
    - TERM,INT fast shutdown
    - QUIT graceful shutdown
    - HUP 保持不中断改配置（不重启加载最新配置）
    - USR1 re-opening log files
    - USR2 upgrading an executable file 升级
    - WINCH graceful shutdown of worker processes
- touch logs/access.log 创建一个新文件
- nginx配置文件conf/nginx.conf
  - worker_processes 1;cpu数量*核数
  - events,worker_connections 1024;一个进程可以保持多少连接
  - http,server,一个server就是一个虚拟主机
- server段

```conf
server {
	listen	80;
	server_name ddd.com;
	location / {
		root	html/ddd;
		index	index.php	index.html;
	}
}
```

- log_format

  _自定义日志格式_

```conf
log_format simple '$remote_addr "$request"';
```

- server里引用自定义日志格式

```conf
server {
	listen       80;
	server_name  qwe.cn;
	access_log logs/qwe/qwe.cn.log simple;
	location / {
		root   html/test;
		index  index.html index.htm;
	}
}
```

_格式是：配置日志 日志位置 日志格式_



