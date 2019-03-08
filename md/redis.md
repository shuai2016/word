NoSQL  not only sql

高并发读写

海量数据的高效率存储和访问

高可扩展性和高可用性

NoSQL数据库的四大分类

键值对

列存储

文档数据库

图形数据库

特点

易扩展

灵活的数据模型

大数据量，高性能

高可用



字符串类型

列表类型

有序集合类型

散列类型

集合类型



缓存

任务队列

网站访问统计

应用排行榜

数据过期处理

分布式集群架构中的session分离



bin redis-cli shutdown



del key

keys *

auth 密码



jedis.close();



五种数据结构

字符串（String）

字符串列表（list）

有序字符串集合（sorted set）

哈希（hash）

字符串集合（set）



key定义

不要太长

不要过短

统一的命名规范



存储String

set key value

get key

getset key value

del key

incr key

decr key

incrby key number

decrby key number

append key value

setlen key 获取值的长度



存储Hash

hset key 名字 值

hmset key 名字 值 名字 值

hget key 名字

hmget key 名字 名字

hgetall key

hdel key 名字 名字

del key

hincrby key 名字 number

hexists key 名字   1  0

hlen key

hkeys key

hvals key



存储list

lpush key 值 值 值

rpush key 值 值 值

lrange key number number （查看，负数从后查）

lpop key 左侧弹出

llen key （个数）

lpushx key 值 （key存在，添加到最上）

rpushx key 值

lrem key 数 值    （从头删除几个值）

lrem key 负数 值    （从尾删除几个值）

lset key 数 值 （替换指定脚本的值）

linsert key before 原来的值 值 （左侧开始，指定值前面添加）

linsert key after 原来的值 值  （左侧开始，指定值后面添加）

rpoplpush key1 key2  （key1尾部弹出压入到key2头部）



存储Set

不允许出现重复元素

sadd key 值 值 值

srem key 值 值   （删除）

smembers key （查看）

sismember key 值    （是否存在，1存在，0不存在）

sdiff key1 key2  （差集key1-key2）

sinter key1 key2 交集

sunion key1 key2 并集

scard key （数量）

srandmember key （随机返回一个成员）

sdiffstore key key1 key2 （将key1-key2存储到key）

sinterstore key key1 key2 交集存起来

sunionstore key key1 key2 并集存起来



存储sorted-set

zadd key 分数 值 分数 值 分数 值

zscore key 值   （获取值的分数）

zcard key （个数）

zrem key 值 值 （删除）

zrange key 数 数  （显示值，分数由小到大）

zrange key 数 数 withscores    （显示值和分数，分数由小到大排序）

zrevrange key 数 数 withscores    （由大到小）

zremrangebyrank key 数 数   （按照范围删除，排列顺序分数由小到大）

zremrangebyscore key 数 数     （根据分数范围删除）

zrangebyscore key 数 数  （显示分数在范围内的值，显示值）

zrangebyscore key 数 数 withscores （显示值和分数）

zrangebyscore key 数 数 withscores limit 数 数   （显示分数范围内的值和分数，并从脚标为数的位置开始，查找数个）

zincrby key 数 值     （给某个值的分数加数个）

zcount key 数 数   （分数在指定范围内的个数）



keys的通用操作

keys *    查看所有key

keys my？  查看my开头的key

del key1 key2

exists key    （key是否存在1,0）

rename key newkey （重命名）

expire key 时间     （设置过期时间，单位秒）

ttl key   （看key所剩的时间，没设置过期时间-1，没有该key-2）

persist key 取消过期

type key  （获取key的类型）



redis特性

select 数    （选则数据库，默认是0，一共16个）

move key  数   （将key移到数号数据库）



flushall 清空数据库





事务命令

multi exec discard

multi （开启事务）

exec （提交事务）

discard （回滚事务）



redis持久化

RDB方式

AOF方式

无持久化

同时使用RDB和AOF



RDB方式

定时存储

save 900 1

save 300 10

save 60 10000



AOF持久化

appendonly no  变成yes

appendfsync always



关闭数据库，redis-cli shutdown