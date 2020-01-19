# 创建变量

```shell
variable_1="i love you,do you love me"
```

# 回显变量的值

```shell
echo $variable_1
```

# 替换变量的值

## 删除：从前往后匹配：#、##

```shell
var1=${variable_1#*ov}
echo $var1
#返回e you,do you love me，#表示从前开始最短匹配，删除匹配到的字符串
var2=${variable_1##*ov}
echo $var2
#返回e me，##表示从前开始最长匹配，删除匹配到的字符串
```

## 删除：从后往前匹配：%、%%

```shell
var3=${variable_1%ov*}
echo $var3
#返回i love you,do you l，%表示从最后开始向前最短匹配，删除匹配到的字符串
var4=${variable_1%%ov*}
echo $var4
#返回i l，%%表示从最后开始向前最长匹配，删除匹配到的字符串
```

## 替换：变量名/旧字符串/新字符串，变量名//旧字符串/新字符串

```shell
echo $PATH
#返回/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
var5=${PATH/bin/BIN}
echo $var5
#返回/usr/local/sBIN:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin，替换第一个匹配到的字符串
var6=${PATH//bin/BIN}
echo $var6
#返回/usr/local/sBIN:/usr/local/BIN:/usr/sBIN:/usr/BIN:/root/BIN，替换所有匹配到的字符串
```

# 计算字符串长度

```shell
var7=${#PATH}
echo $var7
var8=`expr length "$PATH"`
echo $var8
```

# 获取字符索引位置

```shell
variable_2="quickstart is a app"
var9=`expr index "$variable_2" start`
echo $var9
#返回6，实际含义为，start为五个独立的字符，返回最先出现的字符的索引
```

# 获取子串长度

```shell
var10=`expr match "$variable_2" app`
echo $var10
#返回0，子串必须是原字符串开头的位置
var11=`expr match "$variable_2" quick`
echo $var11
#返回5
var12=`expr match "$variable_2" quick.*`
echo $var12
#返回19
```

# 抽取字符串中的子串

```shell
variable_3="kafka hadoop yarn mapreduce"
var13=${variable_3:10}
echo $var13
#返回：op yarn mapreduce，表示从索引为10的字符开始抽取
var14=${variable_3:10:5}
echo $var14
#返回：op ya，表示从索引为10的字符开始抽取5个
var15=${variable_3: -5}
echo $var15
#返回：educe，表示从索引为-5的字符开始抽取，注意-前面有空格，也可以使用()
var16=${variable_3:(-5)}
echo $var16
#使用expr
var17=`expr substr "$variable_3" 10 5`
echo $var17
#返回oop y，表示从第10个字符开始抽取5个
```

# 练习1-字符串处理完整脚本

需求描述：变量string="Bigdata process framework is Hadoop,Hadoop is an open source project"，执行脚本后，打印输出string字符串变量，并给出用户以下选项：

（1）、打印string长度

（2）、删除字符串中所有的Hadoop

（3）、替换第一个Hadoop为Mapreduce

（4）、替换全部Hadoop为Mapreduce

用户输入数字1|2|3|4，可以执行对应的功能；输入q|Q则退出交互模式

思路分析

## 1、将不同的功能模块划分，并编写函数

​	function print_tips

​	function len_of_string

​	function del_hadoop

​	function rep_hadoop_mapreduce_first

​	function rep_hadoop_mapreduce_all

## 2、实现第一步所定义的功能函数

```shell
#!/bin/bash
#

string="Bigdata process framework is Hadoop,Hadoop is an open source project"

function print_tips
# 左括号不能放到后面
{
	echo "*********************************"
	echo "（1）、打印string长度"
	echo "（2）、删除字符串中所有的Hadoop"
	echo "（3）、替换第一个Hadoop为Mapreduce"
	echo "（4）、替换全部Hadoop为Mapreduce"
	echo "*********************************"
}

function len_of_string
{
	echo "${#string}"
}

function del_hadoop
{
	echo "${string//Hadoop/}"
}

function rep_hadoop_mapreduce_first
{
	echo "${string/Hadoop/Mapreduce}"
}

function rep_hadoop_mapreduce_all
{
	echo "${string//Hadoop/Mapreduce}"
}
# 调用函数，直接把函数名字像命令一样写出来即可
# print_tips
```

## 3、程序主程序的设计

```shell
while true
do
	echo "【string=$string】"
	echo
	print_tips
	# 读取用户输入的数据，放到choice变量中
	read -p "Pls input your choice（1|2|3|4|q|Q）：" choice
	# 采用枚举方式
	case $choice in
		1)
			len_of_string
			;;
		2)
			del_hadoop
			;;
		3)
			rep_hadoop_mapreduce_first
			;;
		4)
			rep_hadoop_mapreduce_all
			;;
		q|Q)
			# 退出
			exit
			;;
		*)
			echo "Error,input only in {1|2|3|4|q|Q}"
			;;
	esac		
done
```

# 命令替换：``或者$()

## 例子1：获取系统的所有用户并输出

```shell
# 查看命令的用法
man cut
# 使用:做为分隔符（默认是TAB）；取分割后的第1部分（d，delimiter；f，fields）
cat /etc/passwd | cut -d ":" -f 1
```

example.sh

```shell
#!/bin/bash
#
index=1

# 使用``将命令括起来，进行命令替换
for user in `cat /etc/passwd | cut -d ":" -f 1`
do
	echo "This is $index user: $user"
	# 使用$(())进行运算，引用变量前面可以不加$
	index=$(($index + 1))
done
```

## 例子2：根据系统时间计算今年或者明年

```shell
# 使用$()进行命令替换，极少数unix可能不支持
# date +%Y表示只显示年
echo "This is $(date +%Y) year"
# 显示明年
echo "This is $(($(date +%Y) + 1)) year"
```

## 例子3：根据系统时间获取今年还剩下多少星期，已经过了多少星期

```shell
# date +%Y表示一年过了多少天
echo "This year have passed $(($(date +%j)/7)) weeks"
echo "There is $(((365 - $(date +%j))/7)) weeks before new year"
```

## 例子4：判定nginx进程是否存在，若不存在则自动拉起该进程

```shell
# 查看nginx进程
# grep -v grep，过滤掉grep自己的进程
# wc -l，统计前面命令执行结果的行数
ps -ef | grep nginx | grep -v grep | wc -l
# 关闭nginx，编译安装的服务需要先通过systemctl管理
systemctl stop nginx
```

example.sh

```shell
#!/bin/bash
#

nginx_process_num=$(ps -ef | grep nginx | grep -v grep | wc -l)
# 判断进程数是否等于0
if [ $nginx_process_num -eq 0 ];then
	# 启动nginx，编译安装的服务需要先通过systemctl管理
	systemctl start nginx
fi
```

# 有类型变量：declare和typeset命令

```shell
# 默认变量做为字符串处理
var2="hello world"
# 声明为只读
declare -r var2
var2="hello java"
#返回：-bash: var2: readonly variable

num1=10
num2=$num1+20
echo $num2
#返回：10+20
expr $num1 + 10
#返回：20
# num3声明为整型，此时num1和数值都会当做整型来处理
declare -i num3
num3=$num1+90
echo $num3
# 返回：100

# 列出系统中可用的函数及函数内容
declare -f
# 列出系统中可用的函数名
declare -F

#定义为数组
declare -a array
# 元素之间用空格分开
array=("jones" "mike" "kobe" "jordan")
# 输出数组内容
# 输出全部内容
echo ${array[@]}
# 输出索引为1的内容
echo ${array[1]}
# 获取字符串长度
# 数组内元素个数
echo ${#array[@]}
# 数组内索引为2的元素长度
echo ${#array[2]}
# 给数组赋值
array[0]="lily"
# 删除元素
# 删除索引为2的元素
unset array[2]
# 清空整个数组
unset array

# 将变量声明为环境变量（常规变量只对当前终端有效，在脚本文件中无法引用）
num5=30
declare -x num5
#test1.sh脚本
#!/bin/bash
#
echo num5

#取消声明的变量
#取消声明num2为整型
declare +i num2
declare +r
declare +a
declare +X 
```

# Bash数学运算之expr

```shell
# 方法一，操作符（operator）前后要有空格
# +、-、*、/、%，只能对整数进行运算
# <、<=、=、!=、>、>=：是，返回1；否，返回0
# num1 | num2，num1不为空且非0，返回num1，否则返回num2
# num1 & num2，num1不为空且非0，返回num1，否则返回0
# 管道符号需要转义：\|、\&、\<、\>、\*
expr $num1 operator $num2
# 方法二
$(($num1 operator $num2))
```

## 练习例子：提示用户输入一个正整数num，然后计算1+2+3+...+num的值；必须对num是否为正整数做判断，不符合应当允许再次输入。

sum.sh

```shell
#!/bin/bash
#

while true
do
    read -p "pls input a positive number： " num
    # 不希望将结果输出到终端，定向到垃圾桶
    # 做数学运算，只有整数可以做数学运算，非整数会报错
    expr $num + 1 &> /dev/null
    # $?获取上一条命令的退出状态
    # 判断整数，上条命令可以做数学运算，$?则返回0
    if [ $? -eq 0 ];then
        #判断正数
        if [ `expr $num \> 0` -eq 1 ];then
            # 两个括号，固定格式
            for((i=1;i<=$num;i++))
            do
                sum=`expr $sum + $i`
            done
            echo "1+2+3+...+$num = $sum"
            exit
        fi
    fi
    echo "error,input illegal"
done
```

# Bash数学运算之bc

```shell
# bc是bash内建的计算器，支持浮点数运算
# +、-、*、/、%、^

#交互界面
bc
# 设置精确度，默认是0
# 保留小数点后两位
scale=2
23 / 5
#返回：4.60

# 脚本中使用
# 把结果传给bc
echo "12 + 12.3" | bc
echo "scale=4;23.3 / 3.5" | bc
```

example.sh

```shell
#!/bin/bash
#
read -p "num1: " num1
read -p "num2: " num2

echo "scale=4;$num1*$num2" | bc
```

# 函数

```shell
# 第一种格式
name()
{
	command1
	command2
	...
	commandn
}
# 第二种格式
function name
{
	command1
	command2
	...
	commandn
}

# 终端可以直接创建函数

# 调用：直接使用函数名调用
function_name $1 $2
```

## 例子：写一个监控Nginx的脚本；如果Nginx服务宕掉，则该脚本可以检测到并将进程启动

nginx_daemon.sh

```shell
#!/bin/bash
#

# 执行当前脚本，产生的子进程的pid
this_pid=$$

# 成为守护进程，写一个死循环
while true
do

    # 根据返回值判断nginx是否启动
    # 干扰一：执行命令会形成含有nginx的进程
    # 干扰二：如果脚本名字含有nginx。也会形成含有nginx的进程
    ps -ef | grep nginx | grep -v grep | grep -v $this_pid &> /dev/null

    if [ $? -eq 0 ];then
        echo "Nginx is running well"
        # 休眠3秒
        sleep 3
    else
        systemctl start nginx
        echo "Nginx is down,Start it ..."
    fi

done
```

不在前台输出：nohup命令

```shell
# 将日志输出到文件中（nohup.out）
nohup sh nginx_daemon.sh &
```

##  函数传参

```shell
function name
{
	echo "Hello $1"
	echo "Hello $2"
}
```

calculate.sh

```shell
#!/bin/bash
#

function calcu
{
	case $2 in
		+)
			echo "`expr $1 + $3`"
			;;
		-)
			echo "`expr $1 - $3`"
			;;
		\*)
			echo "`expr $1 \* $3`"
			;;
		/)
			echo "`expr $1 / $3`"
			;;
	esac
}

#函数调用
calcu $1 $2 $3

# 执行脚本：sh calculate.sh 200 / 30
```

## 函数返回值

```shell
# return，只能返回1-255的整数，一般用来供其他地方调用获取状态，通常：0表示成功，1表示失败
# echo，可以返回任何字符串结果
```

### return

nginx.sh

```shell
#!/bin/bash
#

# 执行当前脚本，产生的子进程的pid
this_pid=$$

function is_nginx_running
{
	# 根据返回值判断nginx是否启动
    # 干扰一：执行命令会形成含有nginx的进程
    # 干扰二：如果脚本名字含有nginx。也会形成含有nginx的进程
    ps -ef | grep nginx | grep -v grep | grep -v $this_pid &> /dev/null

    if [ $? -eq 0 ];then
        # return表示return 0
        return
    else
        return 1
    fi
}
# 执行函数，根据状态值做下一步判断
is_nginx_running && echo "Nginx is running" || echo "Nginx is stoped"

#查看执行过程：sh -x nginx.sh
```

### echo

get_sys_user.sh

```shell
#!/bin/bash
#
function get_users
{
	users=`cat /etc/passwd | cut -d: -f1`
	echo $users
}

#命令替换，获取函数的返回值，函数的返回值不再打印在终端
user_list=`get_users`

index=1 
for u in $user_list
do
	echo "The $index user is : $u"
	index=$(($index + 1))
done
```

## 局部变量和全局变量

test.sh

```shell
# 不做特殊说明，Shell中变量都是全局变量（脚本内部）
# Tips：大型脚本程序中函数中慎用全局变量

# 局部变量
# 定义变量时，使用local关键字
# 函数内和外若存在同名变量，则函数内部变量覆盖外部变量。

#!/bin/bash
#

var1="Hello World"

function test
{
	var2=87
}

echo $var1
echo $var2

test

echo $var1
echo $var2
# 执行test函数后可以获取到var2的值

function test1
{
	echo $var2
}

test1


#!/bin/bash
#

var1="Hello World"

function test
{
	# 定义局部变量
	local var2=87
}

test

echo $var1
echo $var2
# var2定义为局部变量，此时无法获取var2的值
```

## 函数库

例子：定义一个函数库，该函数库实现以下几个函数：

​	1、加法函数add

​	2、减法函数reduce

​	3、乘法函数multiple

​	4、除法函数divide

​	5、打印系统运行情况的函数sys_load，该函数可以显示内存运行情况

base_function

```shell
function add
{
	echo "`expr $1 + $2`"
}

function reduce
{
	echo "`expr $1 - $2`"
}

function multiple
{
	echo "`expr $1 \* $2`"
}
 
function divide
{
	echo "`expr $1 / $2`"
}

function sys_load
{
	echo "Memory Info"
	echo
	# 显示内存信息
	free -m
	echo
	
	echo "Disk Usage"
	echo
	# 显示磁盘使用情况
	df -h
	echo
}
```

库文件名的后缀是任意的，但一般使用.lib

库文件通常没有可执行选项

第一行一般使用#!/bin/echo，输出警告信息，避免用户执行

calculate.sh

```shell
#!/bin/bash
#

# 使用.引人入文件
. /home/shuai/shell/lib/base_function

add 12 23

reduce 90 30

multiple 12 12

divide 12 2
```

# 文件查找之find命令

## 查找

```shell
# 语法格式
# find [路径] [选项] [操作]
# 选项，可以多个选项一起用
# -name：根据文件名查找（递归搜索）
# -iname：根据文件名查找，忽略大小写
# -prune：该选项可以排除某些查找目录
# -mtime -n | +n：根据文件更改时间查找（天）
# -type：按文件类型查找
# -size -n +n：按文件大小查找
#
# find /etc -name '*conf'：查找/etc目录下以conf结尾的文件
# -type f：文件，d：目录，c：字符设备文件，b：块设备文件，l：链接文件，p：管道文件
# find . -type f：文件
# find /etc -size -10000c：查找/etc目录下小于10000字节的文件
# find /etc -size +1M：查找/etc目录下大于1M的文件
# ll -h 文件，可以显示文件大小
# -size 不能精确匹配
#
# find /etc -mtime -3：三天内修改过的文件（modify time）
# find /etc -mtime +3：三天之前修改过的文件
# find /etc mtime 3
#
# find /etc -mmin -30：查找/etc目录下30分钟之内修改的文件（目录）
#
# 有 -mindepth（-maxdepth）选项时，要将-mindepth（-maxdepth）紧跟在路径后面
# find /etc -mindepth 3：在/etc下的3级目录开始搜索（当前目录是1级）
# find /etc -maxdepth 1：最多搜索到1级目录（当前目录是1级）
#
# find . -perm 777：查看权限是777的文件
#
#find . -path ./etc -prune -o -type f：查找当前目录下所有普通文件，但排除test目录
#
# 逻辑运算
# -a 与（默认是与操作）
# -o 或
# find /var/log -name '*.log' +mtime +7 等同于 find /var/log -name '*.log' -a +mtime +7
```

## 操作

```shell
# -print：打印输出
# find . -type f 等同于 find . -type f -print
#
# -exec：对搜索到的文件执行特定的操作，格式为-exec 'command' {} \;
# 'command'表示一个操作动作，{}表示查询的结果，\;为固定写法
# find ./etc -name '*.conf' -exec rm -rf {} \;
# find ./etc -size +1M -exec cp {} ./test_5/ \;
#
# -ok：和exec功能一样，只是每次操作都会给用户提示（操作每一个文件都会提示）
```

## find、locate、whereis、which总结及使用场景分析

### locate

```shell
# 所属软件包：mlocate
# find命令是在整块磁盘中搜索，locate命令在数据库文件中查找（理解为linux系统会执行定时任务将新建文件夹等信息保存到数据库文件里，也可以手动执行命令及时更新数据库）
# find是默认全部匹配，locate则是默认部分匹配（只要有对应的字符串就会匹配上）

# 更新命令：updatedb（手动更新）
# 数据库位置：/var/lib/mlocate/mlocate.db
# 所使用的配置文件：/etc/updatedb.conf
# 该命令在后台cron计划任务中定期执行

# 例子
touch abc.txt
# 看mlocate.db文件更新时间
ll /var/lib/mlocate/mlocate.db
locate abc.txt
updatedb
# 看mlocate.db文件更新时间
ll /var/lib/mlocate/mlocate.db
locate abc.txt
```

### whereis

```shell
# 选项：含义
# -b：只返回二进制文件
# -m：只返回帮助文档文件
# -s：只返回源代码文件

whereis mysql
whereis -b mysql
whereis -m mysql
```

### which

```shell
# 作用：仅查找二进制程序文件（只查找程序的可执行文件，常用于查找程序的绝对路径）
# 选项：含义
# -b：只返回二进制文件

#列出mysql可执行程序文件
which mysql
```

# grep和egrep

```shell
# 过滤器
#
# 在文件中查找，以行为单位，pattern可以是字符串，也可以是正则表达式
# grep [option] [pattern] [file1,file2...]
#
# 接收上一个命令的标准输出，在标准输出中查找
# command | grep [option] [pattern]
#
# 选项，含义（可以组合）
# -v：不显示匹配行信息（反向选择，没有匹配到的行）
# -i：搜索时忽略大小写
# -n：显示行号
# -r：递归搜索
# -E：支持扩展正则表达式
# -F：不按正则表达式匹配，按照字符串字面意思匹配
# -c：只显示匹配行总是
# -w：匹配整词
# -x：匹配整行
# -l：只显示文件名，不显示内容
# -s：不显示错误信息

# 在文件中查询包含py的行
# .*表示任意长度的任意字符，普通扩展正则表达式，grep默认支持
grep "py.*" file
# 在文件中查询包含py.*字面意思的行
grep -F "py.*" file
# 在文件中查找包含python或PYTHON的行
# |是扩展正则表达式符号，grep本身不支持扩展正则表达式，此时会当做普通字符串处理
grep "python|PYTHON" file
# 用-E，支持扩展正则表达式
grep -E "python|PYTHON" file

# 递归搜索当前文件及文件夹下的所有文件，包含love字符的行
grep -r love
# 匹配某一行的所有内容为i love python
grep -x "i love python" file

# grep默认不支持扩展正则表达式，只支持基础正则表达式
# 使用egrep可以支持扩展正则表达式，与grep -E等价

# 在标准输出下查找
cat /etc/passwd | grep "bash"
```

# sed

```shell
# sed（Stream Editor），流编辑器。对标准输出或文件逐行进行处理

# 语法格式
# 第一种形式，处理标准输出：stdout | sed [option] "pattern command"
# 第二种形式，处理文件：sed [option] "pattern command" file

# 选项，含义
# -n：只打印模式匹配行（静默模式）
# -e：直接在命令行进行sed编辑，默认选项
# -f：编辑动作保存在文件中，指定文件执行
# -r：支持扩展正则表达式
# -i：直接修改文件内容

# 打印（所有）原始行及匹配行
sed 'p' sed.txt
# p是命令，表示打印
# 只打印匹配行
sed -n 'p' sed.txt

# 打印包含python字符串的行（原始的行都会被打印，即使没有匹配到）
sed '/python/p' sed.txt
# /匹配模式/ 为固定写法，匹配模式可以是字符串也可以是正则表达式
# 只打印匹配行
sed -n '/python/p' sed.txt
# 打印包含python或PYTHON的行（多个匹配模式要写出-e，一个匹配模式默认可以不写）
sed -n -e '/python/p' -e '/PYTHON/p' sed.txt

# 使用文件中的匹配模式，对文本进行流编辑处理
sed -n -f edit.sed sed.txt
# edit.sed内容为：/python/p

# 支持扩展正则表达式：-r
sed -n -r '/python|PYTHON/p' sed.txt

# 将love改成like，'s/原始数据/修改后数据/g'为固定写法，实际原文件内容并没有改变
sed -n 's/love/like/g' sed.txt
# 输出
sed -n 's/love/like/g;p' sed.txt
# 修改源文件：-i
sed -i 's/love/like/g' sed.txt
```

## sed中的pattern（匹配模式）

```shell
# 匹配模式，含义
# 10command：匹配到第10行
# 10,20command：匹配从第10行开始，到第20行结束（11行）
# 10,+5command：匹配从第10行开始，到第15行结束（6行）
# /pattern1/command：匹配到pattern1的行
# /pattern1/,/pattern2/command：匹配到pattern1的行开始，到匹配到pattern2的行结束（包括这两行）
# 10,/pattern1/command：匹配从第10行开始，到匹配到pattern1的行结束（找不到则执行到到最后一行）
# /pattern1/,10commmand：匹配到pattern1的行开始，到第10行匹配结束（匹配到pattern1的行在第10行之后，则只执行匹配到pattern1的行）

# 匹配字符串包含/需要转义
sed -n '/\/sbin\/nologin/p' /etc/passwd

# 匹配正则表达式，打印以hdfs开头的行
sed -n '/^hdfs/p' /etc/passwd
```

## sed中的command（命令）

```shell
# 命令，含义
# 查询
# p：打印
# 增加
# a：行后追加（append）
# i：行前追加
# r：外部文件读入，行后追加
# w：匹配行写入外部文件
# 删除
# d：删除
# 修改
# s/old/new：将行内第一个old替换为new
# s/old/new/g：将行内全部的old替换为new
# s/old/new/2g：将行内前2个old替换为new
# s/old/new/ig：将行内old全部替换为new，忽略大小写
```

例子

```shell
# 查询
# 1、打印file文件的第17行
sed -n "17p" file
# 2、打印file文件的10到20行
sed -n "10,20p" file
# 3、打印file文件中从第10行开始，往后面加5行的所有行
sed -n "10,+5p" file
# 4、打印file文件中以root开头的行
sed -n "/^root/p" file

# 删除
# 删除passwd文件的第一行（命令行删除，时间文件并没有被修改）
sed '1d' passwd
# 实际删除
sed -i '1d' passwd
# 删除1到3行
sed -i '1,3d' passwd



6-4 0936
```



