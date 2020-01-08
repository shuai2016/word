## 入门

### 安装

使用maven构建，使用mysql，将下面的 dependency 代码置于 pom.xml 文件中：

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.38</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.3</version>
</dependency>
```

### 从 XML 中构建 SqlSessionFactory

使用Resources的时候，注意路径是从Resources Root下一级路径开始写（idea可以设置资源根路径，一般java和resources文件夹设置为资源根目录）

mybatis-config.xml需要依靠properties文件绑定变量

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 引入外部配置文件 -->
    <properties resource="mybatis/config/mysql.properties"/>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mybatis/mapper/BlogMapper.xml"/>
    </mappers>
</configuration>
```

这里使用mysql.properties文件

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
username=root
password=root
```

### 不使用 XML 构建 SqlSessionFactory

引用百度知道的回答，[BlogDataSourceFactory 在哪个包中](https://zhidao.baidu.com/question/388026776.html)

> aubdiy
>
> 推荐于2016-04-09
>
> mybatis官方例子中出了这个类BlogDataSourceFactory，我也没找到，后开看mybatis包结构发现可以使用下面代码 构建DataSource
>
> ```java
> Properties properties = new Properties();
> properties.setProperty("driver", "com.mysql.jdbc.Driver");
> properties.setProperty("url","jdbc:mysql://127.0.0.1:3306/mybatis");
> properties.setProperty("username", "root");
> properties.setProperty("password","123456");
> PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
> pooledDataSourceFactory.setProperties(properties);
> DataSource dataSource = pooledDataSourceFactory.getDataSource();
> ```

### 从 SqlSessionFactory 中获取 SqlSession

