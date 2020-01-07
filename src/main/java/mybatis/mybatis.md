### 不使用 XML 构建 SqlSessionFactory

aubdiy

推荐于2016-04-09

mybatis官方例子中出了这个类BlogDataSourceFactory，我也没找到，后开看mybatis包结构发现可以使用下面代码 构建DataSource

```java
Properties properties = new Properties();
properties.setProperty("driver", "com.mysql.jdbc.Driver");
properties.setProperty("url","jdbc:mysql://127.0.0.1:3306/mybatis");
properties.setProperty("username", "root");
properties.setProperty("password","123456");
PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
pooledDataSourceFactory.setProperties(properties);
DataSource dataSource = pooledDataSourceFactory.getDataSource();
```

