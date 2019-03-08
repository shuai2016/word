IDEA创建类时自动生成类注解（细节）

File -> Settings -> Editor -> File and Code Templates

###创建一个模板

Includes页签，新建一个java_header模板（后面有一些可用的模板）

Name:java_header，Extension:java

```properties
/** 
 * @PACKAGE_NAME: ${PACKAGE_NAME}
 * @NAME: ${NAME}
 * @USER: ${USER}
 * @DATE: ${DATE}
 * @TIME: ${TIME}
 * @YEAR: ${YEAR}
 * @MONTH: ${MONTH}
 * @MONTH_NAME_SHORT: ${MONTH_NAME_SHORT}
 * @MONTH_NAME_FULL: ${MONTH_NAME_FULL}
 * @DAY: ${DAY}
 * @DAY_NAME_SHORT: ${DAY_NAME_SHORT}
 * @DAY_NAME_FULL: ${DAY_NAME_FULL}
 * @HOUR: ${HOUR}
 * @MINUTE: ${MINUTE}
 * @PROJECT_NAME: ${PROJECT_NAME}
**/
```

###引用模板

Files页签，选择Class，修改第二行

第二行默认为

`#parse("File Header.java")`

表示引用Includes页签的File Header模板

修改引用自定义的模板

`#parse("java_header")`

如果公用的模板可以写在Includes页签的File Header里，Class、Interface、Enum等默认引用的就是File Header模板。

保存即可

###效果

新创建一个Class类即可自动生成设置好的注释

```java
package com.shuai.controller;

/**
 * @PACKAGE_NAME: com.shuai.controller
 * @NAME: HeaderController
 * @USER: yangs
 * @DATE: 2018/11/18
 * @TIME: 16:50
 * @YEAR: 2018
 * @MONTH: 11
 * @MONTH_NAME_SHORT: 十一月
 * @MONTH_NAME_FULL: 十一月
 * @DAY: 18
 * @DAY_NAME_SHORT: 星期日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 16
 * @MINUTE: 50
 * @PROJECT_NAME: spring-boot-shuai
 **/
public class HeaderController {
}
```

网上的一些模板

```properties
/**
 * ${NAME}
 * 
 * @author shuai
 * @date ${DATE}
 */
```

```properties
/**
 *@ClassName ${NAME}
 *@Description TODO
 *@Author ${USER}
 *@Date ${DATE} ${TIME}
 *@Version 1.0
**/
```





可以使用的变量

| 标识                | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| ${PACKAGE_NAME}     | name of the package in which the new file is created         |
| ${USER}             | current user system login name                               |
| ${DATE}             | current system date                                          |
| ${TIME}             | current system time                                          |
| ${YEAR}             | current year                                                 |
| ${MONTH}            | current month                                                |
| ${MONTH_NAME_SHORT} | first 3 letters of the current month name. Example: Jan, Feb, etc. |
| ${MONTH_NAME_FULL}  | full name of the current month. Example: January, February, etc. |
| ${DAY}              | current day of the month                                     |
| ${DAY_NAME_SHORT}   | first 3 letters of the current day name. Example: Mon, Tue, etc. |
| ${DAY_NAME_FULL}    | full name of the current day. Example: Monday, Tuesday, etc. |
| ${HOUR}             | current hour                                                 |
| ${MINUTE}           | current minute                                               |
| ${PROJECT_NAME}     | the name of the current project                              |
| ${NAME}             | name of the new class specified by you in the Create New Class dialog |



### 已有的类后添加类注释

一些已经创建好的类忘了设置类注释，或者需要再修改类注释的时候

File -> Settings -> Editor -> Live Templates，点击右侧绿色加号，选择1.Live Templates，此时会自动创建一个user的group，并且让我们开始编辑模板

Abbreviation:*header（缩写，快捷键启动的字符串），Description:类注释（描述）

Template text编写模板内容（和上面创建类自动生成的类注释使用同样的模板）

```properties
/**
 * $NAME$
 * 
 * @author shuai
 * @date $DATE$
 */
```

注意此时使用`$key$`占位

选择下面的Define（曾经编辑过选择Change），选中里面的Java复选框（Java及内部都选中）。

点击右侧的Edit variables按钮，弹出页面会列出模板中占位的变量，在Expression列选择需要的值，点击ok。

修改右侧Options内的Expand with，改触发方式，默认Tab，可以改成Enter，点击ok。

此时设置好了

在类的指定位置输入快捷键`*header`（会有提示），点击回车键（Enter）触发，即可生成想要的注释。
