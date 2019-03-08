1. Spring是什么
   1. 简述
      1. 开源框架
      2. 为简化企业级应用开发而生，使用Spring可以使用简单的JavaBean实现以前只有EJB才能实现的功能
      3. IOC（DI）和AOP容器框架
   2. 具体描述
      1. 轻量级：Spring是非侵入性的，基于Spring开发的应用中的对象不依赖于Spring的API
         1. 用Spring的时候不需要实现Spring的接口，不需要继承Spring的父类，而可以是享用Spring提供的功能。
      2. 依赖注入（DI - dependency injection、IOC）
      3. 面向切面编程（AOP - aspect oriented programming）
      4. 容器：Spring是一个容器，因为它包含并且管理应用对象的生命周期
      5. 一站式的框架：
         1. 简单的组件配置组合成一个复杂的应用
         2. 在Spring中可以使用XML和Java注解组合这些对象
         3. 在IOC和AOP的基础上可以整合各种企业应用的框架和优秀的第三方类库（实际上Spring自身也提供了展示层的SpringMVC和持久层的Spring JDBC）
            1. 整合Mybatis，Struts2，Hibernate，...
   3. Spring模块

2. IOC（DI）

   1. Hello world

   2. IOC（DI）概述

   3. 配置bean

      1. 通过工厂方法
      2. 通过FactoryBean
      3. 通过注解
      4. 泛型依赖注入

   4. 属性配置细节

   5. 自动装配

   6. Spring Bean之间关系

      1. 继承（配置上继承），Spring允许继承bean的配置，被继承的Bean称为父Bean，继承这个父Bean的Bean称为子Bean

         1. 子Bean使用parent属性表示继承，继承属性配置

            ```xml
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou"></bean>
            
            <bean id="address1" class="xin.yangshuai.spring.beans.Address"
                  parent="address" p:street="XiErQi"></bean>
            ```

         2. 子Bean可以覆盖父Bean的配置

         3. 父Bean可以作为Bean实例，也可以作为模板（抽象Bean）。作为模板，设置bean中属性abstract为true，这样Spring将不会实例化这个bean。

            ```xml
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou" abstract="true"></bean>
            ```

         4. 并不是bean的所有属性都会被继承，比如autowire，abstract

         5. 父Bean作为模板时（abstract属性为true），子Bean可以忽略父Bean的class属性，指定自己的类，而共享相同的属性配置

      2. 依赖

         1. Spring允许用户通过depends-on属性设定Bean前置依赖的Bean，前置依赖的Bean或者本Bean实例化之前创建好

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello">
                <property name="name" value="hello"/>
            </bean>
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou" depends-on="hello"></bean>
            ```

         2. 前置依赖多个Bean，可以通过逗号，空格的方式配送Bean的名称

   7. Spring Bean作用域

      使用bean的scope属性配置bean的作用域

      1. 单例singleton，使用同一个实例，默认，容器初始化时就创建好了bean。

         1. 配置文件方式配置

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" scope="singleton">
                <property name="name" value="hello"/>
            </bean>
            ```

         2. 注解方式

      2. 原型prototype，每次调用创建一个新的实例，容器初始化时不创建bean，每次调用时再创建。

         1. 配置文件方式配置

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" scope="prototype">
                <property name="name" value="hello"/>
            </bean>
            ```

         2. 注解方式

         3. 使用场景

            1. Spring与Struts2整合的时候，为Struts2的Action指定作用域类型为prototype

      3. request

      4. session

   8. Spring使用外部属性文件

   9. Spring spEL

   10. Spring 管理Bean的生命周期

      Spring IOC 容器可以管理Bean的生命周期，Spring允许在Bean声明周期的特定点执行定制的任务。

      1. Spring IOC 容器对Bean的生命周期进行管理的过程
         1. 通过构造器或工厂方法创建Bean实例

         2. 为Bean的属性设置值和对其他Bean的引用

         3. 调用Bean的初始化方法（bean的init-method属性的值对应的方法）

         4. Bean可以使用了

         5. 容器关闭时，调用Bean的销毁方法（bean的destroy-method属性的值对应的方法）

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" init-method="init2" destroy-method="destroy">
                <property name="name" value="hello"/>
            </bean>
            ```

      2. Bean的后置处理器

         Bean后置处理器允许在调用初始化方法前后对Bean进行额外的处理

         1. Bean后置处理器对IOC容器里的所有Bean实例逐一处理，而并非单一实例（执行契机是init-method前后，不配置init-method也执行），典型应用，检查Bean属性的正确性或根据特定的标准更改Bean的属性

         2. 对Bean后置处理器而言，需要实现`Interface BeanPostProcessor`接口，在初始化方法被调用前后，Spring将把每个Bean实例分别传递给上述接口的以下两个方法：

            1. public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
            2. public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException

         3. 将后置处理器添加到IOC容器中即可，不需要指定id，IOC容器自动识别这是一个BeanPostProcessor

            ```xml
            <bean class="xin.yangshuai.spring.beans.MyBeanPostPrpcessor"></bean>
            ```

         4. Bean的后置处理器可以产生“偷梁换柱”的效果，因为后置处理器两个方法返回值就是返回给用户的Bean

            ```java
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
               System.out.println("postProcessBeforeInitialization: " + bean + ", " + beanName);
               if("address".equals(beanName)){
                  bean = new Hello();
                  ((Hello) bean).setName("hi");
               }
               return bean;
            }
            ```

         5. 因为是处理所有Bean，所有使用时注意过滤

      3. 添加Bean后置处理器后Bean的生命周期

         1. 通过构造器或工厂方法创建Bean实例
         2. 为Bean的属性设置值和对其他Bean的引用
         3. 将Bean实例传递给Bean后置处理器的postProcessBeforeInitialization方法
         4. 调用Bean的初始化方法
         5. 将Bean实例传递给Bean后置处理器的postProcessAfterInitialization方法
         6. Bean可以使用了
         7. 容器关闭时，调用Bean的销毁方法

3. AOP

   1. 使用代理模拟AOP

      ```java
      public class ArithmeticCalculatorLoggingProxy {
      
         private ArithmeticCalculator target;
      
         public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
            this.target = target;
         }
      
         public ArithmeticCalculator getLoggingProxy() {
            ArithmeticCalculator proxy = null;
      
            ClassLoader loader = target.getClass().getClassLoader();
      
            Class[] interfaces = new Class[]{ArithmeticCalculator.class};
      
            InvocationHandler h = new InvocationHandler() {
               @Override
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  String methodName = method.getName();
                  System.out.println("The method " + methodName + " begin with " + Arrays.asList(args));
                  Object result = method.invoke(target, args);
                  System.out.println("The method " + methodName + " end with " + result);
                  return result;
               }
            };
            proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);
            return proxy;
         }
      }
      ```

   2. AOP简介

   3. AOP术语

   4. AspectJ

      1. Java社区里最完整最流行的AOP框架，在Spring2.0以上版本，可以使用基于AspectJ注解或基于XML配置的AOP
      2. Spring自身也有AOP框架的实现，但是AspectJ更值得推荐和使用

   5. 基于注解配置AOP

      1. 使AspectJ注解生效（修改配置文件）

         1. 设置扫描包，扫描IOC容器的Bean

            ```xml
            <context:component-scan base-package="xin.yangshuai.spring.aop"></context:component-scan>
            ```

         2. 使AspectJ注解生效

            ```xml
            <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
            ```

      2. 声明切面（创建一个类）

         ```java
         @Aspect
         @Component
         public class LoggingAspect {
         }
         ```

         1. 声明为IOC容器中的实例，使用`@Component`注解
         2. 声明为切面，使用`@Aspect`注解

      3. 前置通知

         ```java
         @Before("execution(public int xin.yangshuai.spring.aop.ArithmeticCalculator.add(int, int))")
         public void beforeMethod(JoinPoint joinPoint){
            String methodName = joinPoint.getSignature().getName();
            List<Object> args = Arrays.asList(joinPoint.getArgs());
            System.out.println("The method "+ methodName +" begins with " + args);
         }
         ```

         1. 切面类的一个方法，加入注解`@Before`表示前置通知
         2. 将切入点表达式的值作为注解值`execution(public int xin.yangshuai.spring.aop.ArithmeticCalculator.add(int, int))`

      4. 后置通知

      5. 返回通知

      6. 异常通知

      7. 环绕通知

      8. 切点表达式进一步抽象

         ```java
         @Before("execution(* xin.yangshuai.spring.aop.*.*(..))")
         ```

         1. 匹配任意修饰符，任意返回类型：*
         2. 匹配包里面的所有类：*
         3. 匹配任意方法：*
         4. 匹配任意包和子包：..
         5. 匹配任意数量和类型的参数：..

      9. JoinPoint参数

         1. 在通知方法中声明一个类型为JoinPoint的参数，然后就能访问连接细节，如方法名称和参数值。

      10. 切面优先级

         1. 使用`@Order(2)`注解指定切面的优先级，值越小优先级越高

      11. 重用切点表达式

          1. 声明切入点表达式

             ```java
             @Pointcut("execution(* xin.yangshuai.spring.aop.*.*(..))")
             public void declareJointPointExpression(){}
             ```

             1. 定义一个方法，使用`@Pointcut`注解，切入点表达式的值作为注解值，一般方法中不需要添入其它代码

          2. 引用

             ```java
             @Before("xin.yangshuai.spring.aop.LoggingAspect.declareJointPointExpression()")
             ```

             1. 通知直接使用方法名来引用当前的切入点表达式
             2. 不同类中引用使用全类名加方法名

   6. 基于配置文件配置AOP

      ```xml
      <bean id="loggingAspect" class="xin.yangshuai.spring.aop.xml.LoggingAspect"></bean>
      
      <bean id="validationAspect" class="xin.yangshuai.spring.aop.xml.ValidationAspect"></bean>
      
      <aop:config>
          <aop:pointcut id="pointcut" expression="execution(* xin.yangshuai.spring.aop..*.*(..))"></aop:pointcut>
          <aop:aspect ref="loggingAspect" order="2">
              <aop:before method="beforeMethod" pointcut-ref="pointcut"/>
          </aop:aspect>
          <aop:aspect ref="validationAspect" order="1">
              <aop:before method="validateArgs" pointcut-ref="pointcut"/>
          </aop:aspect>
      </aop:config>
      ```

4. Spring使用JdbcTemplate

   1. 简介
      1. 为了使JDBC更加易于使用，Spring在JDBC API上定义了一个抽象层，以此建立一个JDBC存取框架
      2. 作为Spring JDBC框架的核心，JDBC模板的设计目的是为不同类型的JDBC操作提供模板方法。每个模板方法都能控制整个过程，并允许覆盖过程中的特定任务。通过这种方式，可以在尽可能保留灵活性的情况下，将数据库存取的工作量降到最低
      3. JdbcTemplate类被设计成为线程安全的，所以可以在IOC容器中声明它的单个实例，并将这个实例注入到所有的DAO实例中

   2. 配置

      1. 资源文件

         ```properties
         jdbc.user=root
         jdbc.password=root
         jdbc.driverClass=com.mysql.jdbc.Driver
         jdbc.jdbcUrl=jdbc:mysql:///test
         jdbc.initPoolSize=5
         jdbc.maxPoolSize=10
         ```

      2. Spring配置文件

         ```xml
         <!-- 导入资源文件 -->
         <context:property-placeholder location="classpath:xin/yangshuai/spring/jdbc/db.properties"></context:property-placeholder>
         <!-- 配置C3P0数据源 -->
         <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
             <property name="user" value="${jdbc.user}"></property>
             <property name="password" value="${jdbc.password}"></property>
             <property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
             <property name="driverClass" value="${jdbc.driverClass}"></property>
             <property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
             <property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
         </bean>
         <!-- 配置Spring的JdbcTemplate -->
         <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
             <property name="dataSource" ref="dataSource"></property>
         </bean>
         ```

      3. 取得实例

         ```java
         private ApplicationContext ctx = null;
         private JdbcTemplate jdbcTemplate;
         {
            ctx = new ClassPathXmlApplicationContext("xin/yangshuai/spring/jdbc/applicationContext.xml");
            jdbcTemplate = ctx.getBean(JdbcTemplate.class);
         }
         ```

   3. 基本用法

      1. 更新操作

         ```java
         @Test
         public void testUpdate(){
            String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
            jdbcTemplate.update(sql,"Jack",5);
         }
         ```

      2. 批量更新

         ```java
         @Test
         public void testBatchUpdate(){
            String sql = "INSERT INTO employees(last_name,email,dept_id) VALUES (?,?,?)";
            List<Object[]> batchArgs = new ArrayList<>();
            batchArgs.add(new Object[]{"AA","aa@qq.com",1});
            batchArgs.add(new Object[]{"BB","bb@qq.com",2});
            jdbcTemplate.batchUpdate(sql,batchArgs);
         }
         ```

         1. 最后一个参数是Object[]的List类型: 因为修改一条记录需要一个Object的数组

      3. 从数据库获取一条数据，实际得到对应的一个对象

         ```java
         @Test
         public void testQueryForObject(){
            String sql = "SELECT id,last_name lastName,email,dept_id FROM employees WHERE id = ?";
            RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
            Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 6);
            System.out.println(employee);
         }
         ```

         1. RowMapper指定如何去映射结果集的行,常用的实现类为BeanPropertyRowMapper
         2. 使用SQL 中列的别名完成列名和类的属性的映射,例如: last_name lastName
         3. 不支持级联是属性, JdbcTemplate 到底是一个JDBC 的小工具,而不是 ORM 框架

      4. 查询实体类的集合

         ```java
         @Test
         public void testQueryForList(){
            String sql = "SELECT id,last_name lastName,email FROM employees WHERE id > ?";
            RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
            List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 1);
            System.out.println(employees);
         }
         ```

         1. 调用的不是queryForList方法

      5. 获取单个列的值，或做统计查询

         ```java
         @Test
         public void testQueryForObject2() {
             String sql = "SELECT count(id) FROM employees";
             Long count = jdbcTemplate.queryForObject(sql, Long.class);
             System.out.println(count);
         }
         ```

         1. 使用queryForObject(String sql, Class<T> requiredType)方法

5. Spring使用JdbcDaoSupport

   ```java
   @Component
   public class DepartmentDao extends JdbcDaoSupport {
       @Autowired
       public void setDataSource2(DataSource dataSource){
           setDataSource(dataSource);
       }
       public Department get(Integer id){
           String sql = "SELECT id, dept_name name From departments WHERE id = ?";
           BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
           return getJdbcTemplate().queryForObject(sql, rowMapper, id);
       }
   }
   ```

   1. 必须要注入dataSource或者jdbcTemplate，但是set方法是final的。可以采取“曲线救国”的方式注入。
   2. 不推荐使用JdbcDaoSupport，而推荐直接使用JdbcTemplate作为Dao类的成员变量

6. Spring使用NamedParameterJdbcTemplate

   1. 配置namedParameterJdbcTemplate

      ```xml
      <!-- 配置namedParameterJdbcTemplate -->
      <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
          <constructor-arg ref="dataSource"></constructor-arg>
      </bean>
      ```

      1. 该对象可以使用具名参数，其没有无数的构造器，所以必须为其构造器指定参数

   2. 基本用法

      1. 更新操作为参数起名字

         ```java
         @Test
         public void testNamedParameterJdbcTemplate(){
             String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:ln,:email,:deptId)";
             Map<String, Object> paramMap = new HashMap<>();
             paramMap.put("ln","FF");
             paramMap.put("email","ff@qq.com");
             paramMap.put("deptId",2);
             namedParameterJdbcTemplate.update(sql,paramMap);
         }
         ```

         1. 好处：若有多个参数，则不用再去对应位置，直接对应参数名，便于维护
         2. 缺点：较为麻烦

      2. 更新操作使用对象

         ```java
         @Test
         public void testNamedParameterJdbcTemplate2(){
             String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:lastName,:email,:deptId)";
             Employee employee = new Employee();
             employee.setLastName("XYZ");
             employee.setEmail("xyz@qq.com");
             employee.setDeptId(3);
             SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
             namedParameterJdbcTemplate.update(sql,paramSource);
         }
         ```

         1. 使用具名参数时，可以使用update(String sql, SqlParameterSource paramSource)方法进行更新操作
         2. SQL语句中的参数名和类的属性一致
         3. 使用SqlParameterSource的BeanPropertySqlParameterSource实现类做为参数。

7. Spring事务

   1. 事务简介
      1. 用来确保数据的完整性和一致性
      2. 事务就是一系类的动作，他们被当做一个单独的工作单元，这些动作要么全部完成，要么全部不起作用
      3. 事务的四个关键属性（ACID）
         1. 原子性（atomicity）：事务是一个原子操作，由一系列动作组成，事务的原子性确保动作要么全部完成，要么完全不起作用
         2. 一致性（consistency）：一旦所用事务动作完成，事务就被提交，数据和资源就处于一种满足业务规则的一致性状态中
         3. 隔离性（isolation）：可能有许多事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏
         4. 持久性（durability）：一旦事务完成，无论发生什么系统错误，它的结果都不应该受到影响，通常情况下，事务的结果被写到持久化存储器中

   2. Spring中的事务管理
      1. Spring在不同的事务管理API之上定义了一个抽象层，而应用程序开发人员不必了解底层的事务管理API，就可以使用Spring的事务管理机制。有了这些事务机制，事务管理代码就能独立于特定的事务技术了
      2. Spring的核心事务管理抽象是`interface PlatformTransactionManager`管理封装了一组独立于技术的方法，无论使用Spring的哪种事务管理策略（编程式和声明式），事务管理器是必须的
      3. 编程式事务管理
         1. 将事务管理代码嵌入到业务方法中来控制事务的提交和回滚，在编程式管理事务时，必须在每个事务操作中包含额外的事务管理代码
      4. 声明式事务管理
         1. 大多数情况下比编程式事务管理更好用
         2. 将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理
         3. 事务管理做为一种横切关注点，可以通过AOP方法模块化，Spring通过SpringAOP框架支持声明式事务管理
      5. Spring中事务管理器的不同实现
         1. `class DataSourceTransactionManager`：在应用程序中只需要处理一个数据源，而且通过JDBC存取
         2. `class JtaTransactionManager`：在JavaEE应用服务器上用JTA（Java Transaction API）进行事务管理
         3. `class HibernateTransactionManager`：用Hibernate框架存取数据库
         4. 事务管理器以普通的Bean形式声明在Spring IOC容器中

   3. 声明式事务

      1. 配置事务管理器 

         ```xml
         <!-- 配置事务管理器 -->
         <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
             <property name="dataSource" ref="dataSource"></property>
         </bean>
         ```

      2. 启用事务注解

         ```xml
         <!-- 启用事务注解 -->
         <tx:annotation-driven transaction-manager="transactionManager"/>
         ```

      3. 在对应的方法上面加入`@Transactional`注解

         ```java
         //添加事务注解
         @Transactional
         @Override
         public void purchase(String username, String isbn) {
            //1. 获取书的单价
            int price = bookShopDao.findBookPriceByIsbn(isbn);
            //2. 更新书的库存
            bookShopDao.updateBookStock(isbn);
            //3. 更新用户余额
            bookShopDao.updateUserAccount(username,price);
         }
         ```

   4. 事务的传播行为（Transaction Propagation）

      使用propagation指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时，如何使用事务

      1. Spring支持的事务传播行为

         1. REQURED：默认的传播行为。如果有事务在运行，当前方法就在这个事务内运行，否则，就启动一个新的事务，并在自己的事务内运行
         2. REQUIRES_NEW：当前的方法必须启动新事务，并在它自己的事务内运行，如果有事务正在运行，应该将它挂起
         3. SUPPORTS
         4. NOT_SUPPORTED
         5. MANDATORY
         6. NEVER
         7. NESTED

      2. 示例

         ```java
         @Transactional
         @Override
         public void checkout(String username, List<String> isbns) {
            for (String isbn : isbns) {
               bookShopService.purchase(username,isbn);
            }
         }
         
         //添加事务注解
         //使用propagation指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时
         //如何使用事务，默认取值为REQUIRED，即使用调用方法的事务
         //REQUIRES_NEW：使用自己的事务，调用的事务方法的事务被挂起
         @Transactional(propagation = Propagation.REQUIRES_NEW)
         @Override
         public void purchase(String username, String isbn) {
             //1. 获取书的单价
             int price = bookShopDao.findBookPriceByIsbn(isbn);
             //2. 更新书的库存
             bookShopDao.updateBookStock(isbn);
             //3. 更新用户余额
             bookShopDao.updateUserAccount(username,price);
         }
         ```

   5. 事务的其他属性

      1. 事务的隔离级别：isolation
         1. 并发事务所导致的问题：当一个应用程序或者多个应用程序中的多个事务在同一个数据集上并发执行时，可能会出现许多意外的问题：
            1. 脏读
            2. 不可重复读
            3. 幻读
         2. 常用的属性值：`isolation = Isolation.READ_COMMITTED`（读已提交）
      2. 事务的回滚属性
         1. 默认情况下Spring的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置，通常情况取默认值即可
         2. rollbackFor属性：对指定异常也回滚（可配置多个）：`rollbackFor = {Exception.class}`
         3. noRollbackFor属性：对指定异常不回滚（可配置多个）：`noRollbackFor = {UserAccountException.class}`
      3. 事务的只读属性
         1. 只读事务属性：表示这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务，若真的是一个只读取数据库值的方法，应设置`readOnly = true`
      4. 事务的过期属性
         1. 使用timeout指定强制回滚之前事务可以占用的时间：`timeout = 3`（秒）

   6. 基于配置文件配置事务

      ```xml
      <!-- 1. 配置事务管理器 -->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"></property>
      </bean>
      
      <!-- 2. 配置事务属性 -->
      <tx:advice id="txAdvice" transaction-manager="transactionManager">
          <tx:attributes>
              <!-- 根据方法名指定事务的属性 -->
              <tx:method name="purchase" propagation="REQUIRES_NEW"/>
              <tx:method name="get*" read-only="true"/>
              <tx:method name="find*" read-only="true"/>
              <tx:method name="*"/>
          </tx:attributes>
      </tx:advice>
      
      <!-- 3. 配置事务切入点，以及把事务切入点和事务属性关联起来 -->
      <aop:config>
          <aop:pointcut id="txPointCut" expression="execution(* xin.yangshuai.spring.tx.xml.service.*.*(..))"></aop:pointcut>
          <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
      </aop:config>
      ```

8. Spring整合Hibernate

   1. 基本配置
   2. 不使用Hibernate配置文件

9. 在WEB中使用Spring的基本思路

10. Spring集成Struts2