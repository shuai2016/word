shiro基本流程

```java
Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro2.ini");
SecurityManager securityManager = factory.getInstance();
SecurityUtils.setSecurityManager(securityManager);
Subject subject = SecurityUtils.getSubject();
UsernamePasswordToken token = new UsernamePasswordToken("admin2","hello2");
subject.login(token);
```

Subject的一些方法

```java
subject.getPrincipal();
subject.hasRole("role2");
subject.hasAllRoles(list);
boolean[]  array = subject.hasRoles(list);
subject.isPermitted("add2");
```

shiro.ini

```properties
[users]
admin=hello,role1,role2
admin2=hello2,role2,role3

[roles]
role1=add1,delete1,update1,list1
role2=add2,delete2,update2,list2
role3=add3,delete3,update3,list3
```

结合Spring，在applicationContext.xml添加内容

```xml
<bean id="myRealm3" class="shirotest.MyRealm3"/>
```

```xml
<!-- 配置SecurityManager的管理 -->
<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
    <!-- 配置需要使用的Realms -->
    <property name="realm" ref="myRealm3"/>
</bean>

<!-- 配置shiro过滤器 -->
<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
    <!-- 配置一个安全管理器 -->
    <property name="securityManager" ref="securityManager"/>
    <!-- 出现错误之后的跳转路径配置 -->
    <property name="loginUrl" value="/manage/enduser/login1"/>
    <!-- 认证失败之后的跳转路径页面 -->
    <property name="unauthorizedUrl" value="/manage/enduser/unauthorized"/>
    <!-- shiro需要针对所有路径进行配置 -->
    <!--<property name="filterChainDefinitions">
            <value>
                /manage/enduser/list11=authc,roles[角色1],perms[1-list]
            </value>
        </property>-->
    <property name="filters">
        <map>
            <entry key="perms">
                <bean
                      class="shirotest.PermissionAuthorizationFilter" />
            </entry>
        </map>
    </property>
    <!-- shiro连接约束配置,在这里使用自定义的动态获取资源类 -->
    <property name="filterChainDefinitionMap" ref="chainDefinitionSectionMetaSource" />
</bean>


<bean id="chainDefinitionSectionMetaSource" class="shirotest.ChainDefinitionSectionMetaSource">

    <property name="filterChainDefinitions">
        <value>
            /manage/enduser/list=authc,perms[/manage/enduser/ajax/5-delete,/manage/enduser/ajax/1-delete11]
            /manage/enduser/roleList=authc
        </value>
    </property>
</bean>
```

MyRealm3.java有两个方法

```java
@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
```

用来登陆验证，我们可以从入参获取用户填写的用户名和密码

- 获取用户名

```java
String username = (String)token.getPrincipal();
```

- 获取密码

```java
String password = new String((char[])token.getCredentials());
```

- 最后返回

```java
return new SimpleAuthenticationInfo(username,password,"testRealm");
```

- 抛出异常

  *用户名不存在或密码错误抛出相应的异常*

```java
throw new UnknownAccountException("用户名不存在") ;
```

```java
throw new IncorrectCredentialsException("密码错误") ;
```

第二个方法

```java
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
```

用来添加角色权限的，第一个方法通过，说明用户登陆成功了，之后调用这个方法，将该用户拥有的角色权限保存到session中。

- 获取登录名

```java
String username = (String)principalCollection.getPrimaryPrincipal();
```

- 携带角色，权限，返回

```java
SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
auth.setRoles(roles);
auth.setStringPermissions(stringPermissions);
return auth;
```

其中的roles，stringPermissions是Set

controller写法

```java
Subject subject = SecurityUtils.getSubject();
UsernamePasswordToken token = new UsernamePasswordToken(username, password);
subject.login(token);
```



ChainDefinitionSectionMetaSource.java，用来定义路径的访问权限。

```java
@Override
public Ini.Section getObject() throws Exception
```

```java
Ini ini = new Ini();
//加载默认的url
ini.load(filterChainDefinitions);
Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
```

```java
section.put(key, MessageFormat.format(PREMISSION_STRING, value));
```

section.put大概的意思就是

路径=权限，路径就是那个key，权限就是后面的MessageFormat.format(PREMISSION_STRING, value)

它有一定的格式

```java
/**
 * 默认premission字符串，要与配置文件中自定义的key一致
 */
public static final String PREMISSION_STRING="perms[\"{0}\"]";
```

- 判断是不是ajax访问

  *就看请求头有没有字符串XMLHttpRequest，有就认为是ajax请求*

```java
//值为XMLHttpRequest表示ajax请求
String requestType = httpServletRequest.getHeader("X-Requested-With");
if("XMLHttpRequest".equals(requestType)){
    System.out.println("AJAX请求..");
}
```

自定义一个权限规则类

```java
public class PermissionAuthorizationFilter extends AuthorizationFilter
```

这个类有两个方法，第一个方法

```java
protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException
```

权限验证不通过就走这个方法，所以在这里可以判断是否为ajax请求。

第二个方法

```java
public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException
```

用来权限验证，是有默认验证规则的，这里是重写，可以更改验证规则，比如

```xml
/manage/enduser/list=authc,perms[/manage/enduser/ajax/5-delete,/manage/enduser/ajax/1-delete11]
```

默认规则为：指定路径要同时满足多个权限，即：路径/manage/enduser/list要同时满足/manage/enduser/ajax/5-delete和/manage/enduser/ajax/1-delete11两个权限。也就是说，当前用户要有具备两个权限/manage/enduser/ajax/5-delete和/manage/enduser/ajax/1-delete11才可以访问/manage/enduser/list路径。自己重新可以改变这个规则，变成或的关系。

```java
Set<String> perms = CollectionUtils.asSet(permsArray);
for (String perm : perms) {
    if (subject.isPermitted(perm)) {
        return true;  
    }
}  
return false;
```

只要有满足的即返回true。

默认写法

```java
public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
    Subject subject = this.getSubject(request, response);
    String[] perms = (String[])((String[])mappedValue);
    boolean isPermitted = true;
    if (perms != null && perms.length > 0) {
        if (perms.length == 1) {
            if (!subject.isPermitted(perms[0])) {
                isPermitted = false;
            }
        } else if (!subject.isPermittedAll(perms)) {
            isPermitted = false;
        }
    }

    return isPermitted;
}
```

注解设置权限,修改springMVC-servlet.xml配置

```xml
<!-- 保证实现了Shiro内部lifecycle函数的bean执行 -->
<bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

<!-- 注解授权：AOP式方法级权限检查  -->
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator" depends-on="lifecycleBeanPostProcessor">
    <property name="proxyTargetClass" value="true" />
</bean>

<bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
    <property name="securityManager" ref="securityManager"/>
</bean>
```



