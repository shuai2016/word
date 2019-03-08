# MySQL配置

## my.ini

1. 路径：安装路径根目录

2. 服务端配置：[mysqld]

   ```ini
   #端口号
   port=3306
   #安装目录
   basedir="D:/work/MySQL/MySQL Server 5.5/"
   #文件目录
   datadir="D:/work/MySQL/data/Data/"
   #字符集
   character-set-server=utf8
   #存储引擎
   default-storage-engine=INNODB
   #语法模式
   sql-mode="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"
   #最大连接数
   max_connections=100
   ```

   1. 注意：修改配置后，重启mysql服务

# 服务端登录

## 命令行登录

1. 命令行（管理员）：`mysql -h localhost -P 3306 -u root -proot`
   1. -h：-h 主机名
   2. -P：-P 端口号
   3. -u：-u 用户名
   4. -p：-p密码(-p与密码之间不能有空格，先运行再输入密码，密文)
   5. 本机3306端口访问可简写：`mysql -u root -proot`

# 基本命令

1. 建议：每条命令的结尾使用`;`或者`/g`
2. 查看数据库（管理系统）版本：
   1. sql命令：`select version();`
   2. dos命令：`mysql --version`（`mysql -V`）
3. 查找数据库相关
   1. 查看有哪些数据库：`show databases;`
   2. 进入某个数据库：`use test;`
   3. 查看当前数据库有哪些表：`show tables;`
   4. 查看某数据库的表：`show tables from mysql;`
   5. 查看当前所在数据库：`select database();`
4. 查找数据表相关
   1. 查看有哪些数据表：`show tables;`
   2. 查看表的结构：`desc stuinfo;`

# MySQL语法规范

1. 大小写：建议关键字大写，表名、列名小写
2. 注释：
   1. 单行注释：`#注释文字`
   2. 单行注释：`-- 注释文字`（注意空格）
   3. 多行注释：`/* 注释文字 */`

# 查询DQL

## 基础查询

1. 查询常量值：`SELECT 100;`，字符型和日期型的常量需要加单引号`'`
2. 查询表达式：`SELECT 100%98;`
3. 查询函数：`SELECT VERSION(); `
4. 起别名：`SELECT 100%98 AS 结果;`，别名有特殊字符则别名外加上双引号`"`
5. distinct：去重
   1. 多个字段不起作用：`distinct salary,commission_pct`
6. ”+“的作用：`SELECT '123'+90`，mysql中的”+“仅仅有运算符的作用
   1. ”+“号两边都是数字，直接做加法运算
   2. 存在字符串，则尝试转换为数字，转换失败，转换为0
   3. 存在null（不是字符串），结果为null（不是字符串）
7. 字符串拼接：`SELECT concat(123,null)`，存在null结果为null
8. ifnull：`IFNULL( commission_pct, 0 ) `，判断某字段或表达式是否为null，如果为null返回指定的值，否则返回原本的值
9. isnull：`ISNULL(commission_pct)`，判断某字段或表达式是否为null，如果是则返回1，否则返回0

## 条件查询

1. 不等于：建议`<>`，`!=`也不报错
2. not用法：`NOT ( salary >= 10000 AND salary <= 20000 )`
3. like通配符（可以判断字符型或数值型）：
   1. `%`：任意多个字符，包含0个字符
   2. `_`：任意单个字符
   3. 模糊查询第二个字符为`_`：需要转义
      1. 默认转义：`LIKE '_\_%'`
      2. 指定转义字符：`LIKE '_$_%' ESCAPE '$'`
   4. `like '%%'`：无法查出为null的数据
   5. like可以判断数值型：`LIKE '1__'`
4. `is null`：=或`<>`不能用于判断null值，`IS NULL`或`IS NOT NULL`用于判断null值
5. 安全等于：`<=>`，表示等于
   1. 可以判断null：`commission_pct <=> NULL`
   2. 可以判断普通数值：`salary <=> 12000`

## 排序查询

1. `order by`子句中可以支持单个字段、多个字段、表达式、函数、别名
2. `order by`子句一般是放在查询语句的最后面，limit子句除外

## 常见函数

```sql
SELECT 函数名(实参列表) 【from 表】;
```

### 单行函数

#### 字符函数

1. length：`SELECT LENGTH('张三丰hahaha');`获取参数值的字节个数，一个中文占多个字节（utf8字符集一个中文占3个字节）
2. concat：拼接字符串
3. upper、lower
4. substr、substring 
   1. 索引从1开始
   2. 截取从指定索引处后面所有字符：`SELECT SUBSTR('李莫愁爱上了陆展元',7) out_put;`
   3. 截取从指定索引处指定字符长度的字符：`SELECT SUBSTR('李莫愁爱上了陆展元',1,3) out_put;`
5. instr：`SELECT INSTR('杨不悔爱上了殷六侠','殷六侠') out_put;`，返回字串第一次出现的索引，如果找不到返回0
6. trim：去前后空格
   1. 默认去前后空格：`SELECT TRIM( '  张翠山  ' ) out_put;`
   2. 去前后指定字符：`SELECT TRIM( 'a' FROM 'aaaaa张aaaaa翠山aaaaa' ) out_put;`，中间不会去掉
7. lpad：`SELECT LPAD('殷素素',10,'*') out_put;`，用指定的字符左填充原字符使字符串至指定长度，若原字符串已经超出指定长度则从左截取指定长度返回。
8. rpad：若原字符串已经超出指定长度依然从左截取指定长度返回。
9. replace：`SELECT REPLACE('张无忌爱上了周芷若周芷若周芷若','周芷若','赵敏') out_put;`，替换全部指定字符

#### 数学函数

1. round：四舍五入
   1. `SELECT ROUND( - 1.5 )`：返回-2
   2. `SELECT ROUND( - 1.55,1 )`：返回-1.6，小数点后保留1位
2. ceil：向上取整，返回>=该参数的最小整数
   1. `SELECT ceil(-1.2)`：返回-1
3. floor：向下取整，返回<=该参数的最大整数
4. truncate：截断
   1. `SELECT TRUNCATE(1.555,2 )`，返回1.55，小数点后保留2位
5. mod：取余，`MOD(a,b)`：表示`a-a/b*b`，所以符号和被除数相同
   1. `SELECT MOD(-10,-3)`：返回-1
   2. `SELECT MOD(-10,3)`：返回-1
6. rand：获取随机数，返回0-1之间的小数（取不到1），`SELECT RAND()`

#### 日期函数

1. now：返回当前系统日期+时间
   1. `SELECT NOW();`：返回 2019-01-25 11:27:29
2. curdate：返回当前系统日期，不包含时间
   1. `SELECT CURDATE();`：返回 2019-01-25
3. curtime：返回当前时间，不包含日期
   1. `SELECT CURTIME();`：返回 11:34:17
4. 获取时间的指定部分，年、月、日、时、分、秒
   1. `SELECT MONTH(NOW());`：返回 1
   2. `SELECT MONTHNAME(NOW());`：返回 January
5. str_to_date：字符转换为日期
   1. `SELECT STR_TO_DATE('2019/1/25 8-12-20','%Y/%c/%d %H-%i-%s');`：返回 2019-01-25 08:12:20
   2. 格式符
      1. `%Y`：四位年份
      2. `%y`：2位年份
      3. `%m`：月份（01,02，……，11,12）
      4. `%c`：月份（1,2，……，11,12）
      5. `%d`：日（01,02）
      6. `%H`：小时（24小时制）
      7. `%h`：小时（12小时制）
      8. `%i`：分钟（00,01，……，59）
      9. `%s`：秒（00,01，……，59）
6. date_format：将日期转换成字符
   1. `SELECT DATE_FORMAT(NOW(),'%Y/%c/%d %H-%i-%s');`：返回 2019/1/25 11-53-26
7. datediff：查询相差天数
   1. `SELECT DATEDIFF(NOW(),'1994-01-01');`，第一个参数为较大日期（较近日期）

#### 其它函数

1. 查看版本号：`SELECT VERSION()`
2. 查看当前数据库：`SELECT DATABASE()`
3. 查看当前的用户：`SELECT USER()`
4. 返回字符串的密码形式：`SELECT PASSWORD('字符');`
5. 返回字符串的md5加密形式：`SELECT MD5( '字符' );`

#### 流程控制函数

1. if函数：`SELECT IF(10<5,'大','小');`

2. case：

   1. 类似switch...case

      ```sql
      /*
      case 要判断的字段或表达式
      when 常量1 then 要显示的值1或语句1;（值不加;）
      when 常量2 then 要显示的值2或语句2;
      ...
      else 要显示的值n或语句n;
      end
      */
      SELECT
      	salary 原始工资,
      	department_id,
      CASE
      	department_id 
      	WHEN 30 THEN salary * 1.1 
      	WHEN 40 THEN salary * 1.2 
      	WHEN 50 THEN salary * 1.3 ELSE salary 
      	END AS 新工资 
      FROM
      	employees;
      ```

   2. 类似多重if

      ```sql
      /*
      case
      when 条件1 then 要显示的值1或语句1
      when 条件2 then 要显示的值2或语句2
      ...
      else 要显示的值n或语句n
      end
      */
      SELECT
      	salary,
      CASE
      	WHEN salary > 20000 THEN 'A' 
      	WHEN salary > 15000 THEN 'B' 
      	WHEN salary > 10000 THEN 'C'
      	ELSE 'D' 
      	END AS 工资级别 
      FROM
      	employees;
      ```

### 分组函数

1. sum、avg一般用于处理数值型，max、min、count可以处理任何类型
2. 以上分组函数都忽略null值
3. 可以和distinct搭配实现去重后计算
4. count函数的效率问题，一般使用count(*)用作统计行数
   1. MYISAM存储引擎下，count(*)的效率高
   2. INNODB存储引擎下，count(*)和count(1)的效率差不多，比count(字段)效率要高一些
5. 和分组函数一同查询的字段要求是group by后出现的字段

## 分组查询

1. 分组查询中的筛选条件分为两类

   |            | 数据源         | 位置                | 关键字 |
   | ---------- | -------------- | ------------------- | ------ |
   | 分组前筛选 | 原始表         | group by 子句的前面 | where  |
   | 分组后筛选 | 分组后的结果集 | group by 子句的后面 | having |

   1. 分组函数做条件肯定是放在having字句中
   2. 能用分组前筛选的，就优先考虑使用分组前筛选

2. 按表达式或函数分组

   ```sql
   SELECT
   	count( * ) c,
   	length( last_name ) len_name 
   FROM
   	employees 
   GROUP BY
   	len_name 
   HAVING
   	c > 5;
   ```

   1. mysql支持group by子句 和 having子句 使用 select子句 的别名

## 连接查询（多表查询）

### 笛卡尔乘积现象

### 分类

#### 按年代分类

##### sql92标准：mysql中仅仅支持内连接

1. 等值连接

   1. 多表等值连接的结果为多表的交集部分
   2. n表连接，至少需要n-1个连接条件
   3. 多表的顺序没有要求
   4. 一般需要为表起别名
   5. 可以搭配前面介绍的所有子句使用，比如排序，分组，筛选

2. 非等值连接

   ```sql
   SELECT
   	salary,
   	grade_level 
   FROM
   	employees e,
   	job_grades g 
   WHERE
   	salary BETWEEN g.lowest_sal 
   	AND g.highest_sal;
   ```

3. 自连接

   ```sql
   SELECT
   	e.employee_id,
   	e.last_name,
   	m.employee_id,
   	m.last_name 
   FROM
   	employees e,
   	employees m 
   WHERE
   	e.manager_id = m.employee_id
   ```

##### sql99标准：mysql支持内连接+外连接（左外和右外）+交叉连接

```sql
select 查询列表
from 表1 别名 【连接类型】
join 表2 别名
on 连接条件
【where 筛选条件】
【group by 分组】
【having 筛选条件】
【order by 排序列表】
```

1. 内连接：inner

   1. 等值连接：【inner】
   2. 非等值连接：【inner】
   3. 自连接：【inner】

2. 外连接：外连接的查询结果为主表中的所有记录，如果从表中有和它匹配的，则显示匹配的值，如果从表中没有和它匹配的，则显示null，外连接查询结果=内连接结果+主表中有而从表没有的记录

   1. 左外连接：left 【outer】：left join 左边的是主表

      ```sql
      SELECT
      	b.`name`
      FROM
      	beauty b
      	LEFT OUTER JOIN boys bo ON b.boyfriend_id = bo.id 
      WHERE
      	bo.id IS NULL;
      ```

   2. 右外连接：right 【outer】：right join 右边的是主表

   3. 全外连接：full 【outer】：（mysql不支持）

3. 交叉连接：cross（笛卡尔乘积）

   ```sql
   SELECT
   	b.*,
   	bo.* 
   FROM
   	beauty b
   	CROSS JOIN boys bo;
   ```

#### 按功能分类

##### 内连接

1. 等值连接
2. 非等值连接
3. 自连接

##### 外连接

1. 左外连接
2. 右外连接（mysql不支持）
3. 全外连接

##### 交叉连接（笛卡尔乘积）

### 注意

1. 如果为表起了别名，则查询的字段就不能使用原来的表名去限定

## 子查询

出现在其它语句内部的select语句称为子查询或内查询，外面的语句可以是insert、update、delete、select等，一般select作为外面语句较多，外面如果为select语句，则此语句称为主查询或外查询。

### 分类

#### 按子查询出现的位置

1. select后面

   1. 仅仅支持标量子查询

      ```sql
      SELECT
      	d.*,
      	( SELECT COUNT( * ) FROM employees e WHERE e.department_id = d.department_id ) 个数 
      FROM
      	departments d;
      ```

2. from后面

   1. 支持表子查询

      ```sql
      SELECT
      	ag_dep.*,
      	g.grade_level 
      FROM
      	( SELECT AVG( salary ) ag, department_id FROM employees GROUP BY department_id ) ag_dep
      	INNER JOIN job_grades g ON ag_dep.ag BETWEEN lowest_sal 
      	AND highest_sal
      ```

      1. 将子查询结果充当一张表，要求必须起别名

3. where或having后面*

   1. 标量子查询*

      ```sql
      SELECT
      	MIN( salary ),
      	department_id 
      FROM
      	employees 
      GROUP BY
      	department_id 
      HAVING
      	MIN( salary ) > ( SELECT MIN( salary ) FROM employees WHERE department_id = 50 );
      ```

   2. 列子查询（多行子查询）*

      ```sql
      SELECT
      	last_name,
      	employee_id,
      	job_id,
      	salary 
      FROM
      	employees 
      WHERE
      	salary < ALL ( SELECT DISTINCT salary FROM employees WHERE job_id = 'IT_PROG' );
      ```

   3. 行子查询（一行多列）

      ```sql
      SELECT
      	* 
      FROM
      	employees 
      WHERE
      	( employee_id, salary ) = ( SELECT MIN( employee_id ), MAX( salary ) FROM employees );
      ```

4. exists后面（相关子查询）

   1. 表子查询

      ```sql
      SELECT
      	department_name 
      FROM
      	departments d 
      WHERE
      	EXISTS ( SELECT * FROM employees e WHERE d.department_id = e.department_id );
      ```

#### 按结果集的行列数不同

1. 标量子查询（结果集只有一行一列）
2. 列子查询（结果集只有一列多行）
3. 行子查询（结果集有一行多列）
4. 表子查询（结果集一般为多行多列）

### 特点

1. 子查询放在小括号内
2. 子查询一般放在条件的右边
3. 标量子查询，一般搭配着单行操作符使用：<、>、=、<=、>=、<>
4. 列子查询，一般搭配着多行操作符使用：IN/NOT IN、ANY/SOME、ALL
5. 子查询的执行优先于主查询执行，主查询的条件用到了子查询的结果

## 分页查询

```sql
select 查询列表
from 表
【join type join 表2
on 连接条件
where 筛选条件
group by 分组字段
having 分组后的筛选
order by 排序的字段】
limit 【offset,】size;
```

1. offset：要显示条目的起始索引（起始索引从0开始，默认从0开始）
2. size：要显示的条目个数

### 特点

1. limit语句放在查询语句的最后

2. 公式

   ```sql
   要显示的页数 page，每页的条目数 size，
   select 查询列表
   from 表
   limit (page-1)*size,size;
   ```

## 联合查询（union）

```sql
SELECT * FROM employees WHERE email LIKE '%a%'
UNION
SELECT * FROM employees WHERE department_id > 90;
```

1. 将多条查询语句的结果合并成一个结果

### 特点

1. 要求多条查询语句的查询列数是一致的
2. 要求多条查询语句的查询的每一列的类型和顺序最好一致
3. union关键字默认去重，如果使用union all 可以包含重复项

# 数据操作语言DML

## 插入语句

### 方式一：经典插入

```sql
insert into 表名(列1,...) values(值1,...),(值1,...);
```

1. 插入的值的类型要与列的类型一致或兼容
2. 列数和值的个数必须一致
3. 可以省略列名，默认所有列，而且列的顺序和表中的列的顺序一致

### 方式二

```sql
insert into 表名
set 列名=值,列名=值,...
```

### 两种插入方式对比

1. 方式一支持插入多行，方式二不支持
2. 方式一支持子查询，方式二不支持

### 其它插入方式

```sql
INSERT INTO my_employees
SELECT 1,'Patel','Ralph','Rpatel',985 UNION 
SELECT 2,'Dancs','Betty','Bdancs',860;
```

## 修改语句

### 修改单表的记录

```sql
update 表名
set 列=新值,列=新值,...
where 筛选条件;
```

### 修改多表的记录

```sql
sql92语法
update 表1 别名,表2 别名
set 列=值,...
where 连接条件
and 筛选条件;

sql99语法
update 表1 别名
inner|left|right join 表2 别名
on 连接条件
set 列=值,...
where 筛选条件;
```

## 删除语句

### 方式一：delete

#### 单表的删除

```sql
delete from 表名 where 筛选条件 limit 条目数
```

#### 多表的删除

```sql
sql92语法
delete 表1的别名,表2的别名（删除哪个表数据写哪个表）
from 表1 别名,表2 别名
where 连接条件
and 筛选条件;

sql99语法
delete 表1的别名,表2的别名
from 表1 别名
inner|left|right join 表2 别名 on 连接条件
where 筛选条件;
```

### 方式二：truncate

```sql
truncate table 表名;
```

### 两种删除方式的区别

1. delete 可以加 where 条件， truncate不能加
2. truncate删除，效率较高
3. 假如要删除的表中有自增长列，如果用delete删除后，再插入数据，自增长列的值从断点开始，而truncate删除后，在插入数据，自增长列的值从1开始
4. truncate删除没有返回值，delete删除有返回值
5. truncate删除不能回滚，delete删除可以回滚

# 数据定义语言DDL

库和表的管理创建（create）、修改（alter）、删除（drop）

## 库的管理

### 库的创建

```sql
CREATE DATABASE [if not exists] 库名 [character set 字符集];
```

### 库的修改

#### 库名的修改（废弃）

```sql
RENAME DATABASE bookes TO 新库名;
```

#### 更改库的字符集

```sql
ALTER DATABASE books CHARACTER SET gbk;
```

### 库的删除

```sql
DROP DATABASE [IF EXISTS] books;
```

## 表的管理

### 表的创建

```sql
create table [IF NOT EXISTS] 表名(
	列名 列的类型[(长度) 约束],
	列名 列的类型[(长度) 约束],
	列名 列的类型[(长度) 约束],
	...
	列名 列的类型[(长度) 约束]
)
```

### 表的修改

```sql
alter table 表名 add|drop|modify|change column 列名 [列类型 约束]
```

#### 修改列名

```sql
ALTER TABLE book CHANGE COLUMN publishdate(旧名) pubDate(新名) DATETIME(类型);
```

#### 修改列的类型或约束

```sql
ALTER TABLE book MODIFY COLUMN pubdate TIMESTAMP [新约束];
```

#### 添加新列

```sql
ALTER TABLE author ADD COLUMN annual DOUBLE [first|after 字段名];
```

#### 删除列

```sql
ALTER TABLE author DROP COLUMN annual;
```

#### 修改表名

```sql
ALTER TABLE author RENAME [TO] book_author;
```

### 表的删除

```sql
DROP TABLE [IF EXISTS] book_author;
```

### 表的复制

#### 仅复制表结构

```sql
CREATE TABLE copy(新表) LIKE author(已经存在的表);
```

#### 复制表的结构及数据

```sql
CREATE TABLE copy2 SELECT * FROM author;
```

#### 仅复制某些字段

```sql
CREATE TABLE copy4
SELECT id,au_name
FROM author
WHERE 0;
```

## 常见的数据类型

### 数值型

#### 整型

tinyint（1个字节）、smallint（2个字节）、mediumint（3个字节）、int（integer，4个字节）、bigint（8个字节）

##### 设置无符号和有符号

```sql
CREATE TABLE tab_int(
	t1 INT,
    t2 INT UNSIGNED
)
```

##### 特点

1. 如果不设置无符号还是有符号，默认是有符号，如果想设置无符号，需要添加unsigned关键字
2. 如果插入的数值超出了整型的范围，会报out of range异常，并且插入临界值
3. 如果不设置长度，会有默认的长度，长度代表了显示的最大宽度，如果不够会用0在左边填充，但必须搭配zerofill使用，如果使用zerofill，则变为无符号

#### 小数

##### 浮点数

float(M,D) ，4个字节

double(M,D)，8个字节

##### 定点数

dec(M,D)

decimal(M,D)

##### 特点

1. M表示整数部位+小数部位，D表示小数部位，如果超过范围，则插入临界值
2. M和D都可以省略，如果是decimal，则M默认为10，D默认为0，如果是float和double，则会根据插入的数值的精度来决定精度
3. 定点型的精确度较高，如果要求插入数值的精度较高如货币运算则考虑使用

### 字符型

#### 较短的文本

1. char、varchar

   | 类型    | 写法       | M的意思                                             | 特点           | 空间的消耗 | 效率 |
   | ------- | ---------- | --------------------------------------------------- | -------------- | ---------- | ---- |
   | char    | char(M)    | 最大的字符数（一个汉字一个字符），可以省略，默认为1 | 固定长度的字符 | 比较耗费   | 高   |
   | varchar | varchar(M) | 最大的字符数（一个汉字一个字符），不可用省略        | 可变长度的字符 | 比较节省   | 低   |

2. binary和varbinary类型类似与char和varchar，不同的是它们包含二进制字符串而不包含非二进制字符串。

3. 枚举enum（不区分大小写）

   ```sql
   CREATE TABLE tab_char(
       c1 ENUM('a','b','c')
   )
   ```

4. 集合set（不区分大小写）

   ```sql
   CREATE TABLE tab_set(
       s1 SET('a','b','c','d')
   );
   INSERT INTO tab_set VALUES('a');
   INSERT INTO tab_set VALUES('B');
   INSERT INTO tab_set VALUES('a,b');
   ```

#### 较长的文本

text、blob（较长的二进制数据）

### 日期型

| 类型      | 字节 | 范围      | 时区影响 |
| --------- | ---- | --------- | -------- |
| datetime  | 8    | 1000-9999 | 不受     |
| timestamp | 4    | 1970-2038 | 受       |

1. date：只保存日期
2. time：只保存时间
3. year：只保存年
4. datetime：保存日期+时间
5. timestamp：保存日期+时间

### 使用原则

1. 所选则的类型越简单越好，能保存数值的类型越小越好

## 常见约束

一种限制，用于限制表中的数据，为了保证表中的数据的准确和可靠性

### 分类：六大约束

1. NOT NULL：非空，用于保证该字段的值不能为空
2. DEFAULT：默认，用于保证该字段有默认值
3. PRIMARY KEY：主键，用于保证该字段的值具有唯一性，并且非空
4. UNIQUE：唯一，用于保证该字段的值具有唯一性，可以为空
5. CHECK：检查约束【mysql中不支持】
6. FOREIGN KEY：外键，用于限制两个表的关系，用于保证该字段的值必须来自于主表的关联列的值，在从表添加外键约束，用于引用主表中某列的值

### 约束的添加分类

```sql
CREATE TABLE 表名(
    字段名 字段类型 列级约束,
    字段名 字段类型,
    表级约束
)
```

1. 列级约束：六大约束语法上都支持，但外键约束没有效果
2. 表级约束：除了非空、默认，其它的都支持

### 创建表时添加约束

#### 添加列级约束

```sql
CREATE TABLE stuinfo(
    id INT PRIMARY KEY,#主键
    stuName VARCHAR(20) NOT NULL,#非空
    gender CHAR(1) CHECK(gender='男' OR gender='女'),#检查，mysql不支持
    seat INT UNIQUE,#唯一
    age INT DEFAULT 18,#默认
    majorId INT REFERENCES major(id) #外键，没有效果
);
CREATE TABLE major(
    id INT PRIMARY KEY,
    majorName VARCHAR(20)
);
DESC stuinfo;
SHOW INDEX FROM stuinfo;#查看stuinfo中的所有索引，包括主键、外键、唯一
```

1. 直接在字段名和类型后面追加约束类型即可
2. 只支持：默认、非空、主键、唯一

#### 添加表级约束

```sql
CREATE TABLE stuinfo(
    id INT,
    stuName VARCHAR(20),
    gender CHAR(1),
    seat INT,
    age INT,
    majorId INT,
    CONSTRAINT pk PRIMARY KEY(id),#主键
    CONSTRAINT uq UNIQUE(seat),#唯一键
    CONSTRAINT ck CHECK(gender = '男' OR gender = '女'),#检查
    CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id)#外键
);
```

1. 在各个字段的最下面添加表级约束，`[constraint 约束名] 约束类型(字段名)`
2. 给主键起约束名无效

### 主键和唯一键的对比

| 约束 | 唯一性 | 是否允许为空           | 一个表中可以有多少该约束 | 是否允许组合                            |
| ---- | ------ | ---------------------- | ------------------------ | --------------------------------------- |
| 主键 | 是     | 否                     | 至多有1个                | 允许，PRIMARY KEY(id,stuname)，但不推荐 |
| 唯一 | 是     | 是（只允许有一个null） | 可以有多个               | 允许，UNIQUE(seat,seat2)，但不推荐      |

### 外键的特点

1. 要求在从表设置外键关系
2. 从表的外键列的类型和主表的关联列的类型要求一致或兼容，名称无所谓
3. 主表的关联列必须是一个key（一般是主键或唯一）
4. 插入数据时，先插入主表，再插入从表，删除数据时，先删除从表，再删除主表

### 有外键关联的主表数据删除

1. 级联删除

   ```sql
   ALTER TABLE stuinfo ADD CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id) ON DELETE CASCADE;
   ```

2. 级联置空

   ```sql
   ALTER TABLE stuinfo ADD CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id) ON DELETE SET NULL;
   ```

### 修改表时添加约束

```sql
CREATE TABLE stuinfo(
    id INT,
    stuname VARCHAR(20),
    gender CHAR(1),
    seat INT,
    age INT,
    majorId INT
);
```

1. 添加非空约束

   ```sql
   ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NOT NULL;
   ```

2. 添加默认约束

   ```sql
   ALTER TABLE stuinfo MODIFY COLUMN age INT DEFAULT 18;
   ```

3. 添加主键

   1. 列级约束

      ```sql
      ALTER TABLE stuinfo MODIFY COLUMN id INT PRIMARY KEY;
      ```

   2. 表级约束

      ```sql
      ALTER TABLE stuinfo ADD PRIMARY KEY(id);
      ```

4. 添加唯一

   1. 列级约束

      ```sql
      ALTER TABLE stuinfo MODIFY COLUMN seat INT UNIQUE;
      ```

   2. 表级约束

      ```sql
      ALTER TABLE stuinfo ADD UNIQUE(seat);
      ```

5. 添加外键

   ```sql
   ALTER TABLE stuinfo ADD [CONSTRAINT fk_stuinfo_major] FOREIGN KEY(majorid) REFERENCES major(id);
   ```

6. 添加列级约束和表级约束的语法

   1. 添加列级约束：`alter table 表名 modify column 字段名 字段类型 新约束；`
   2. 添加表级约束：`alter table 表名 add [constraint 约束名] 约束类型(字段名) [外键的引用]`

### 修改表时删除约束

1. 删除非空约束

   ```sql
   ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NULL;
   ```

2. 删除默认约束

   ```sql
   ALTER TABLE stuinfo MODIFY COLUMN age INT;
   ```

3. 删除主键

   ```sql
   ALTER TABLE stuinfo DROP PRIMARY KEY;
   ```

4. 删除唯一

   ```sql
   ALTER TABLE stuinfo DROP INDEX seat;
   ```

5. 删除外键

   ```sql
   ALTER TABLE stuinfo DROP FOREIGN KEY fk_stuinfo_major;
   ```

## 标识列

又称为自增长列，含义：可以不用手动的插入值，系统提供默认的序列值。默认从1开始，步长为1

### 创建表时设置标识列

```sql
CREATE TABLE tab_identity(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20)
)
```

### 查看自增长相关信息

```sql
SHOW VARIABLES LIKE '%auto_increment%';
```

### 设置步长

```sql
SET auto_increment_increment=3;
```

1. 不能用配置修改起始值，可以手动插入一条数据，设置起始值

### 特点

1. 标识列必须和主键搭配吗？不一定，但要求是一个key（mysql）
2. 一个表中可以有几个标识列？至多一个
3. 标识列的类型只能是数值型

### 修改表时设置标识列

```sql
ALTER TABLE tab_identity MODIFY COLUMN id INT PRIMARY KEY AUTO_INCREMENT;
```

### 修改表时删除标识列

```sql
ALTER TABLE tab_identity MODIFY COLUMN id INT;
```

# 事务控制语言TCL

Transaction Control Language

## 事务

一个或一组sql语句组成一个执行单元，这个执行单元要么全部执行，要么全部不执行。

## 存储引擎（表类型）

1. 在mysql中的数据用各种不同的技术存储在文件（或内存）中

2. 查看mysql支持的存储引擎

   ```sql
   show engines;
   ```

3. innodb支持事务，而myisam、memory等不支持事务

## 事务的ACID属性

1. 原子性（Atomicity）：事务是一个不可分割的工作单位。
2. 一致性（Consistency）：事务必须使数据库从一个一致性状态转换到另外一个一致性状态。
3. 隔离性（Isolation）：一个事务的执行不能被其它事务干扰。
4. 持久性（Durability）：一个事务一旦被提交，它对数据库中数据的改变就是永久性的。

## 事务的创建

### 隐式事务

事务没有明显的开启和结束的标记，比如insert、update、delete语句

### 显式事务

事务具有明显的开启和结束的标记，前提：必须先设置自动提交功能为禁用

### 事务自动提交

1. 查看自动提交

   ```sql
   SHOW VARIABLES LIKE 'autocommit';
   ```

2. 禁用自动提交（仅对当前事务有效）

   ```sql
   set autocommit=0;
   ```

### 开启事务步骤

```sql
#1、开启步骤
set autocommit=0;
start transaction;#可选的
#2、编写事务中的sql语句（select insert update delete）
语句1;
语句2;
...
#3、结束事务
commit;#提交事务
rollback;#回滚事务

#SAVEPOINT设置保存点
set autocommit=0;
start transaction;
语句1;
SAVEPOINT a;
语句2;
rollback to a;#回滚到保存点，savepoint只搭配rollback使用

```

## 数据库的隔离级别

1. 没有采取必要的隔离机制导致的并发问题
   1. 脏读
   2. 不可重复读
   3. 幻读

2. 数据库的事务隔离级别
   1. Oracle支持的2种隔离级别：READ COMMITED（默认），SERIALIZABLE
   2. MySql支持4种事务隔离级别：READ UNCOMMITTED（读未提交数据），READ COMMITED（读已提交），REPEATABLE READ（可重复读，默认），SERIALIZABLE（串行化）

3. 查看当前的事务隔离级别

   ```sql
   SELECT @@tx_isolation;
   ```

4. 设置MySql事务隔离级别（全局/当前会话）

   ```sql
   set session|global transaction isolation level read uncommitted;
   ```

5. 事务的隔离级别与出现的现象

   |                  | 脏读 | 不可重复读 | 幻读 |
   | ---------------- | ---- | ---------- | ---- |
   | read uncommitted | 是   | 是         | 是   |
   | read committed   | 否   | 是         | 是   |
   | repeatable read  | 否   | 否         | 是   |
   | serializable     | 否   | 否         | 否   |

# 视图

MySql从5.0.1版本开始提供视图功能。应用场景：

1. 多个地方用到同样的查询结果
2. 该查询结果使用的sql语句较复杂

## 创建视图

```sql
create view 视图名
as
查询语句;
```

## 视图的好处

1. 重用sql语句
2. 简化复杂的sql操作，不必知道它的查询细节
3. 保护数据，提高安全性

## 视图的修改

1. 方式一

   ```sql
   create or replace view 视图名
   as
   查询语句;
   ```

2. 方式二

   ```sql
   alter view 视图名
   as
   查询语句;
   ```

## 删除视图

```sql
drop view 视图名,视图名,...;
```

## 查看视图

```sql
DESC 视图名;
SHOW CREATE VIEW 视图名[\G];
```

## 视图数据的增删改

具备以下特点的视图不允许更新

1. 包含以下关键字的sql语句：分组函数、distinct、group by、having、union、union all
2. 常量视图
3. select中包含子查询
4. join（可以update）
5. from一个不能更新的视图
6. where子句的子查询引用了from子句中的表

# 变量

## 系统变量

1. 说明，变量由系统提供，不是用户定义，属于服务器层面

2. 语法
   1. 查看所有的系统变量

      ```sql
      show global|[session] variables;
      ```

   2. 查看满足条件的部分系统变量

      ```sql
      show global|[session] variables like '%char%';
      ```

   3. 查看指定的某个系统变量的值

      ```sql
      select @@global|[session].系统变量名;
      ```

   4. 为某个系统变量赋值

      1. 方式一

         ```sql
         set global|[session] 系统变量名 = 值;
         ```

      2. 方式二

         ```sql
         set @@global|[session].系统变量名 = 值;
         ```

3. 分类

   1. 全局变量，服务器每次重启将为所有的全局变量赋初始值，针对于所有的会话（连接）有效，但不能跨重启
   2. 会话变量，仅仅针对于当前会话（连接）有效

## 自定义变量

1. 用户变量

   1. 作用域：针对于当前会话（连接）有效，同于会话变量的作用域，应用在任何地方，也就是begin end里面或begin end外面

   2. 声明并初始化

      ```sql
      SET @用户变量名=值;
      SET @用户变量名:=值;
      SELECT @用户变量名:=值;
      ```

   3. 赋值（更新用户变量的值）

      1. 通过SET或SELECT，重新初始化

      2. 通过SELECT INTO

         ```sql
         SELECT 字段 INTO @变量名
         FROM 表;
         #示例
         SET @count=1;
         SELECT COUNT(*) INTO @count
         FROM employees;
         ```

   4. 使用（查看用户变量名的值）

      ```sql
      SELECT @用户变量名;
      ```

2. 局部变量

   1. 作用域：仅仅在定义它的begin end中有效，且只能放在第一句

   2. 声明

      ```sql
      DECLARE 变量名 类型;
      DECLARE 变量名 类型 DEFAULT 值;
      ```

   3. 赋值

      1. 通过SET或SELECT

         ```sql
         SET 局部变量名=值;
         SET 局部变量名:=值;
         SELECT @局部变量名:=值;
         ```

      2. 通过SELECT INTO

         ```sql
         SELECT 字段 INTO 局部变量名
         FROM 表;
         ```

      3. 使用

         ```sql
         SELECT 局部变量名;
         ```

# 存储过程和函数

一组预先编译好的SQL语句的集合，理解成批处理语句，类似于java中的方法

1. 提高代码的重用性
2. 简化操作
3. 减少了编译次数并且减少了和数据库服务器的连接次数，提高了效率

## 存储过程

### 创建语法

```sql
CREATE PROCEDURE 存储过程名(参数列表)
BEGIN
	存储过程体(一组合法的SQL语句)
END
```

注意：

1. 参数列表包含三部分：

   参数模式  参数名  参数类型

   举例：`IN stuname VARCHAR(20)`

   参数模式：

   1. IN：该参数可以作为输入，也就是该参数需要调用方传入值
   2. OUT：该参数可以作为输出，也就是该参数可以作为返回值
   3. INOUT：该参数既可以作为输入又可以作为输出，也就是该参数既需要传入值，又可以返回值

2. 如果存储过程体仅仅只有一句话，BEGIN END 可以省略

3. 存储过程体中的每条SQL语句的结尾要求必须加分号。

   存储过程的结尾可以使用 DELIMITER重新设置

   语法：`DELIMITER  结束标记`

   举例：`DELIMITER  $`

### 调用语法

```sql
CALL 存储过程名(实参列表);
```

### 空参列表

```sql
#创建
DELIMITER $
CREATE PROCEDURE myp1()
BEGIN
	INSERT INTO admin(username,password)
	values('john1','0001'),('john2','0002');
END $
#调用
CALL myp1()$
```

### IN模式参数

```sql
#创建
CREATE PROCEDURE myp2(IN beautyName VARCHAR(20))
BEGIN
	SELECT bo.*
	FROM boys bo
	RIGHT JOIN beauty b ON bo.id = b.boyfriend_id
	WHERE b.name = beautyName;
END $
#调用
CALL myp2('小昭')$

#创建
CREATE PROCEDURE myp3(IN username VARCHAR(20),IN password varchar(20))
BEGIN
	DECLARE result INT DEFAULT 0;
	
	SELECT COUNT(*) INTO result
	FROM admin
	WHERE admin.username = username
	AND admin.password = password;
	
	SELECT IF(result>0,'成功','失败');
END $
#调用
CALL myp3('张飞','8888')$
```

### OUT模式参数

```sql
#根据女神名，返回对应的男神名
CREATE PROCEDURE myp5(IN beautyName VARCHAR(20),OUT boyName VARCHAR(20))
BEGIN
	SELECT bo.boyName INTO boyName
	FROM boys bo
	INNER JOIN beauty b ON bo.id = b.boyfriend_id
	WHERE b.name = beautyName;
END $
#调用
CALL myp5('小昭',@bName)$
SELECT @bName$

#根据女神名，返回对应的男神名和男神魅力值
CREATE PROCEDURE myp6(IN beautyName VARCHAR(20),OUT boyName VARCHAR(20),OUT userCP INT)
BEGIN
	SELECT bo.boyName,bo.userCP INTO boyName,userCP #多个变量同时在一个INTO后面赋值
	FROM boys bo
	INNER JOIN beauty b ON bo.id = b.boyfriend_id
	WHERE b.name = beautyName;
END $
#调用
CALL myp6('小昭',@bName,@userCP)$
SELECT @bName,@userCP$
```

### INOUT模式参数

```sql
#传入a和b两个值，最终a和b都翻倍并返回
CREATE PROCEDURE myp8(INOUT a INT,INOUT b INT)
BEGIN
	SET a = a*2;
	SET b = b*2;
END $
#调用
SET @m = 10$
SET @n = 20$
CALL myp8(@m,@n)$
SELECT @m,@n$
```

### 删除存储过程

```sql
DROP PROCEDURE 存储过程名;#一次只能删除一个存储过程
```

### 查看存储过程

```sql
SHOW CREATE PROCEDURE myp2;
```

## 函数

### 与存储过程的区别

1. 存储过程：可以有0个返回，也可以有多个返回，适合做批量插入、批量更新
2. 函数：有且仅有1个返回，适合做处理数据后返回一个结果

### 创建语法

```sql
CREATE FUNCTION 函数名(参数列表) RETURNS 返回类型
BEGIN
	函数体
END
```

注意：

1. 参数列表包含两部分

   参数名 参数类型

2. 函数体

   肯定会有return语句，如果没有会报错

   如果return语句没有放在函数体的最后也不报错，但不建议

3. 函数体中仅有一句话，则可以省略begin end

4. 使用 delimiter 语句设置结束标记

### 调用语法

```sql
SELECT 函数名(参数列表)
```

### 无参有返回

```sql
#创建
CREATE FUNCTION myf1() RETURNS INT
BEGIN
	DECLARE c INT DEFAULT 0;#定义局部变量
	SELECT COUNT(*) INTO c#赋值
	FROM employees;
	RETURN c;
END $
#调用
SELECT myf1()$
```

### 有参有返回

```sql
#创建
CREATE FUNCTION myf2(empName VARCHAR(20)) RETURNS DOUBLE
BEGIN
	SET @sal = 0;#定义用户变量
	SELECT salary INTO @sal #赋值
	FROM employees
	WHERE last_name = empName;
	RETURN @sal;
END $
#调用
SELECT myf2('kochhar')$
```

### 查看函数

```sql
SHOW CREATE FUNCTION myf3;
```

### 删除函数

```sql
DROP FUNCTION myf3;
```

# 流程控制结构

顺序结构：程序从上往下依次执行

分支结构：程序从两条或多条路径中选择一条去执行

循环结构：程序在满足一定条件的基础上，重复执行一段代码

## 分支结构

1. if函数

2. case结构

   1. 可以作为表达式，镶嵌在其它语句中使用，可以放在任何地方，BEGIN END 中或 BEGIN END 的外面
   2. 可以作为独立的语句去使用，只能放在 BEGIN END 中
   3. ELSE 可以省略，如果ELSE省略了，并且所有WHEN条件都不满足，则返回NULL

   ```sql
   #创建存储过程，根据传入的成绩，来显示等级，比如传入的成绩：90-100，显示A，80-90，显示B，60-80，显示C，否则，显示D
   DELIMITER $
   CREATE PROCEDURE test_case(IN SCORE INT)
   BEGIN
   	CASE
   	WHEN score >= 90 AND score <=100 THEN SELECT 'A';
   	WHEN score >= 80 THEN SELECT 'B';
   	WHEN score >= 60 THEN SELECT 'C';
   	ELSE SELECT 'D';
   	END CASE;
   END $
   #调用
   CALL test_case(95)$
   ```

3. if结构

   ```sql
   if 条件1 then 语句1;
   elseif 条件2 then 语句2;
   ...
   [else 语句n;]
   end if;
   ```

   1. 应用在begin end中

   ```sql
   DELIMITER $
   CREATE FUNCTION test_if(SCORE INT) RETURNS CHAR
   BEGIN
   	IF score>=90 AND score<=100 THEN RETURN 'A';
   	ELSEIF score>=80 THEN RETURN 'B';
   	ELSEIF score>=60 THEN RETURN 'C';
   	ELSE RETURN 'D';
   	END IF;
   END $
   #调用
   SELECT test_if(95)$
   ```

## 循环结构

位置：BEGIN END中

### 分类

1. while

   ```sql
   [标签:]while 循环条件 do
   	循环体;
   end while [标签];
   #先判断后执行
   ```

2. loop

   ```sql
   [标签:]loop
       循环体;
   end loop [标签];
   #可以用来模拟简单的死循环
   ```

3. repeat

   ```sql
   [标签:]repeat
   	循环体;
   	until 结束循环的条件
   end repeat [标签];
   #先执行后判断
   ```

### 循环控制

1. iterate：类似于 continue，继续，结束本次循环，继续下一次
2. leave：类似于 break，跳出，结束当前所在的循环

### 无循环控制语句

```sql
#批量插入，根据次数插入到admin表中多条记录
CREATE PROCEDURE pro_while1(IN insertCount INT)
BEGIN
	DECLARE i INT DEFAULT 1;
	WHILE i < insertCount DO
		INSERT INTO admin(username,password) VALUES(CONCAT('Rose',i),'666');
		SET i=i+1;
	END WHILE;
END $
#调用
CALL pro_while1(100)$
```

### 包含leave语句

```sql
#批量插入，根据次数插入到admin表中多条记录
CREATE PROCEDURE pro_while1(IN insertCount INT)
BEGIN
	DECLARE i INT DEFAULT 1;
	a:WHILE i < insertCount DO
		INSERT INTO admin(username,password) VALUES(CONCAT('John',i),'0000');
		IF i>=20 THEN LEAVE a;
		END IF;
		SET i=i+1;
	END WHILE a;
END $
#调用
CALL pro_while1(100)$
```

### 包含iterate语句

```sql
#批量插入，根据次数插入到admin表中多条记录
CREATE PROCEDURE pro_while1(IN insertCount INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	a:WHILE i < insertCount DO
		SET i=i+1;
		IF MOD(i,2)!=0 THEN ITERATE a;
		END IF;
		INSERT INTO admin(username,password) VALUES(CONCAT('xiaohua',i),'0000');
	END WHILE a;
END $
#调用
CALL pro_while1(100)$
```

