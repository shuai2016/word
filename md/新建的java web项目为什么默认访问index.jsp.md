新建的java web项目为什么默认访问index.jsp

```java
1 org.apache.catalina.deploy.WebXml

public void configureContext(Context context)

i$ = this.servletMappings.entrySet().iterator();

while(i$.hasNext()) {
    entry = (Entry)i$.next();
    context.addServletMapping((String)entry.getKey(), (String)entry.getValue());
}

2 org.apache.catalina.core.StandardContext

public void addServletMapping(String pattern, String name) {
    this.addServletMapping(pattern, name, false);
}

3 org.apache.tomcat.util.http.mapper.Mapper

public void addWrapper(String path, Object wrapper, boolean jspWildCard, boolean resourceOnly) {
    this.addWrapper(this.context, path, wrapper, jspWildCard, resourceOnly);
}
    
protected void addWrapper(Mapper.ContextVersion context, String path, Object wrapper, boolean jspWildCard, boolean resourceOnly)
```







1. Tomcat默认的设置

   1. 默认的两个Servlet

      1. default

         ```xml
         <servlet>
             <servlet-name>default</servlet-name>
             <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
             <init-param>
                 <param-name>debug</param-name>
                 <param-value>0</param-value>
             </init-param>
             <init-param>
                 <param-name>listings</param-name>
                 <param-value>false</param-value>
             </init-param>
             <load-on-startup>1</load-on-startup>
         </servlet>
         
         <servlet-mapping>
             <servlet-name>default</servlet-name>
             <url-pattern>/</url-pattern>
         </servlet-mapping>
         ```

      2. jsp

         ```xml
         <servlet>
             <servlet-name>jsp</servlet-name>
             <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
             <init-param>
                 <param-name>fork</param-name>
                 <param-value>false</param-value>
             </init-param>
             <init-param>
                 <param-name>xpoweredBy</param-name>
                 <param-value>false</param-value>
             </init-param>
             <load-on-startup>3</load-on-startup>
         </servlet>
         
         <servlet-mapping>
             <servlet-name>jsp</servlet-name>
             <url-pattern>*.jsp</url-pattern>
             <url-pattern>*.jspx</url-pattern>
         </servlet-mapping>
         ```

   2. 默认的welcome-file-list

          <welcome-file-list>
              <welcome-file>index.html</welcome-file>
              <welcome-file>index.htm</welcome-file>
              <welcome-file>index.jsp</welcome-file>
          </welcome-file-list>

2. tomcat启动后，发出请求，都会调用`org.apache.tomcat.util.http.mapper.Mapper`类的`internalMapWrapper`方法

   ```java
   private final void internalMapWrapper(Mapper.ContextVersion contextVersion, CharChunk path, MappingData mappingData) throws Exception
   ```

3. `internalMapWrapper`方法的第一个参数`Mapper.ContextVersion contextVersion`，在启动Tomcat的时候被赋值，里面包含各种servlet相关的信息，具体怎么赋值看源码，可以从`org.apache.catalina.deploy.WebXml`的`public void configureContext(Context context)`方法开始看，这里就简单说下赋了什么值，看看比较有用的几个属性

   ```java
   public String[] welcomeResources = new String[0];
   public Mapper.Wrapper defaultWrapper = null;
   public Mapper.Wrapper[] exactWrappers = new Mapper.Wrapper[0];
   public Mapper.Wrapper[] wildcardWrappers = new Mapper.Wrapper[0];
   public Mapper.Wrapper[] extensionWrappers = new Mapper.Wrapper[0];
   ```

   1. 注意

      1. defaultWrapper是Wrapper的对象（不是数组）
      2. exactWrappers，wildcardWrappers，extensionWrappers都是Wrapper的对象数组

   2. welcomeResources：就是welcome-file-list的值

      ```java
      默认情况下的值为：[index.html,index.htm,index.jsp]
      ```

   3. 关注`Wrapper`父类`Mapper.MapElement`的两个属性

      ```java
      public final String name;
      public final Object object;
      ```

      1. defaultWrapper：

         ```java
         name="";
         object=StandardEngine[Catalina].StandardHost[localhost].StandardContext[].StandardWrapper[default]
         ```

      2. exactWrappers

         ```java
         默认为空
         ```

      3. wildcardWrappers

         ```java
         默认为空
         ```

      4. extensionWrappers：

         ```java
         0;
         name=jsp;
         object=StandardEngine[Catalina].StandardHost[localhost].StandardContext[].StandardWrapper[jsp]
         1;
         name=jspx;
         object=StandardEngine[Catalina].StandardHost[localhost].StandardContext[].StandardWrapper[jsp]
         ```

   4. wrapper的装配规则，具体可以看源码，这里就大概说一下

      1. 每个Servlet的名字和映射访问路径组成一组数据，也就是说，如果一个Servlet有多个映射的访问路径那就组成多组数据，把一组数据按照某种规则组装到不同的Wrapper中
      2. 不同的映射路径形式，被组装到不同的Wrapper中
         1. 精确匹配：\<url-pattern>中配置的项必须与url完全精确匹配
            1. 精确匹配向`Mapper.Wrapper[] exactWrappers`添加数组的Wrapper对象
         2. 路径匹配（通配符）：以“/”字符开头，并以“/*”结尾的字符串用于路径匹配
            1. 路径匹配向`Mapper.Wrapper[] wildcardWrappers`添加数组的Wrapper对象
         3. 扩展名匹配（后缀）：以“*.”开头的字符串被用于扩展名匹配
            1. `扩展名匹配向Mapper.Wrapper[] extensionWrappers`添加数组的Wrapper对象
            2. 不自定义Servelt也有两个，因为Tomcat中jsp Servlet 有两个扩展名匹配
         4. 缺省匹配：配置为“/”
            1. 缺省匹配向`Mapper.Wrapper defaultWrapper`添加Wrapper对象
            2. 注意defaultWrapper不是数组，所以只有一个Wrapper对象，所以如果自己配置缺省匹配了，那么就会替换默认的default Servlet

4. 为`internalMapWrapper`方法的第三个参数`MappingData mappingData`的`public Object wrapper = null;`属性赋值，属性的值就是`Mapper.ContextVersion`中所有的Wrapper 中的其中一个的object属性， `internalMapWrapper`方法的第二个参数`CharChunk path`，可以理解为访问路径，整体可以理解为根据访问路径找到合适的Wrapper。简单说下具体的规则：

   1. 在exactWrappers（精确匹配）中寻找是否有符合规则的Wrapper，没有转wildcardWrappers
   2. 在wildcardWrappers（通配符匹配）中寻找是否有符合规则的Wrapper，没有转extensionWrappers
   3. 在extensionWrappers（后缀匹配）中寻找是否有符合规则的Wrapper
   4. 如果还没有符合规则的Wrapper
      1. 如果此时访问带有自定义路径
         1. 选择defaultWrapper
      2. 如果此时访问不带有自定义路径
         1. 路径后面依次加welcomeResources的值组成新的路径
            1. 在exactWrappers（精确匹配）中寻找是否有符合规则的Wrapper，没有转wildcardWrappers
            2. 在wildcardWrappers（通配符匹配）中寻找是否有符合规则的Wrapper
            3. 如果还没有符合规则的Wrapper
               1. 判断路径是否可以找到实际的文件
                  1. 可以找到
                     1. 在extensionWrappers（后缀匹配）中寻找是否有符合规则的Wrapper，没有转defaultWrapper
                     2. 选择defaultWrapper
            4. 如果还没有符合规则的Wrapper
               1. 在extensionWrappers（后缀匹配）中寻找是否有符合规则的Wrapper（此时需要Wrapper是自定义的），没有转defaultWrapper
               2. 选择defaultWrapper

5. fsafsa
