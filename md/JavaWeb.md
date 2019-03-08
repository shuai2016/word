1. JavaWeb应用的概念

   1. JavaWeb应用由一组Servlet、HTML页、类、以及其它可以被绑定的资源构成。它可以在各种供应商提供的实现Servlet规范的Servlet容器中运行

2. Servlet容器的概念

   1. Servlet容器为JavaWeb应用提供运行时环境，它负责管理Servlet和JSP的生命周期，以及管理他们的共享数据

3. Tomcat的安装和配置

   1. 由于有了Sun的参与和支持，最新的Servlet和JSP规范总是能在Tomcat中得到体现

   2. 配置java_home或jre_home

   3. 任意目录都能启动关闭

      1. startup.bat的文件目录加path
      2. 创建catalina_home，值为tomcat根目录
      3. 使用`catalina run`在当前窗口启动，ctrl+c关闭

   4. tomcat管理程序：tomcat manager

   5. 加载其它路径应用程序

      1. Tomcat根目录\conf\Catalina\localhost下创建xml文件（hello.xml）

         ```xml
         <?xml version="1.0" encoding="UTF-8"?>
         <Context path="/test" docBase="实际的物理路径" reloadable="true"></Context>
         ```

      2. 实际访问路径：localhost:8080/hello/hello.jsp

         1. Tomcat5.5开始，在\conf\Catalina\localhost目录下创建XML配置文件来配置Web应用程序，Tomcat将以XML文件的文件名作为Web应用程序的上下文路径，因此在配置`<Context>`元素时，可以不使用path属性

4. JavaWeb开发的目录结构

   ```properties
   Hello
   |---WEB-INF
   |---|---classes
   |---|---|---包
   |---|---|---|---.class文件
   |---|---lib
   |---|---|---.jar文件
   |---|---web.xml
   |---index.jsp
   
   ```

5. 使用Eclipse开发JavaWeb项目

6. 第一个Servlet

   1. Servlet简介
      1. Java Servlet是和平台无关的服务器端组件，它运行在Servlet容器中。Servlet容器负责Servlet和客户的通信以及调用Servlet的方法，Servlet和客户的通信采用“请求/响应”的模式。
   2. Servlet可以完成的功能
      1. 创建并返回基于客户请求的动态HTML页面
      2. 创建可嵌入现有HTML页面中的部分HTML页面（HTML片段）
      3. 与其它服务器资源（如数据库或基于Java的应用程序）进行通信

7. Servlet的配置及生命周期方法

   1. Servlet的配置

      1. 创建一个Servlet接口的实现类

         ```java
         public class HelloServlet implements Servlet {
            @Override
            public void init(ServletConfig servletConfig) throws ServletException {
               System.out.println("init");
            }
            @Override
            public ServletConfig getServletConfig() {
               System.out.println("getServletConfig");
               return null;
            }
            @Override
            public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
               System.out.println("service");
            }
            @Override
            public String getServletInfo() {
               System.out.println("getServletInfo");
               return null;
            }
            @Override
            public void destroy() {
               System.out.println("destroy");
            }
         }
         ```

      2. 在web.xml中配置和映射

         ```xml
         <!-- 配置和映射Servlet -->
         <servlet>
             <!--Servlet 注册的名字-->
             <servlet-name>helloServlet</servlet-name>
             <!--Servlet 的全类名-->
             <servlet-class>xin.yangshuai.javaweb.servlet.HelloServlet</servlet-class>
         </servlet>
         <servlet-mapping>
             <!--需要和某一个servlet 节点的 servlet-name 字节点的文本节点一致-->
             <servlet-name>helloServlet</servlet-name>
             <!--映射具体的访问路径：/ 代表当前 WEB 应用的根目录-->
             <url-pattern>/hello</url-pattern>
         </servlet-mapping>
         ```

   2. Servlet容器的作用

      1. 可以创建Servlet，并调用Servlet 的相关生命周期方法
      2. JSP、Filter、Listener、Tag ... 相关的生命周期方法都是由Servlet容器来调用

   3. Servlet 生命周期的方法：都是由Servlet容器负责调用

      1. 构造器：第一次请求Servlet时，创建Servlet 的实例，调用构造器
         1. 这说明Servlet是单实例的
      2. init 方法：只被调用一次，在创建好实例后立刻被调用，用于初始化当前Servlet
      3. service：被多次调用，每次请求都会调用service方法，实际用于响应请求
      4. destroy：只被调用一次，在当前Servlet所在的 WEB 应用被卸载前调用，用于释放当前Servlet所占用的资源

   4. load-on-startup参数

      1. 配置在servlet节点中

         ```xml
         <servlet>
             <servlet-name>helloServlet</servlet-name>
             <servlet-class>xin.yangshuai.javaweb.servlet.HelloServlet</servlet-class>
             <load-on-startup>2</load-on-startup>
         </servlet>
         ```

      2. load-on-startup可以指定Servlet被创建的时机

         1. 若为负数，则在第一次请求时被创建
         2. 若为0或正数，则在当前WEB应用被Servlet容器加载时创建实例，且数值越小越早被创建

   5. Servlet容器响应客户请求的过程

      1. Servlet引擎检测是否已经装载并创建了该Servlet的实例对象。如果是，则直接执行第4步，否则，执行第2步
      2. 装载并创建该Servlet的一个实例对象：调用该Servlet的构造器
      3. 调用Servlet实例对象的init()方法
      4. 创建一个用于封装请求的ServletRequest对象和一个代表响应消息的ServletResponse对象，然后调用Servlet的service()方法并将请求和响应对象作为参数传递进去
      5. WEB应用程序被停止或重新启动之前，Servlet引擎将卸载Servlet，并在卸载之前调用Servlet的destory()方法

   6. Servlet的注册与运行

      1. Servlet程序必须通过Servlet容器来启动运行，并且储存目录有特殊要求，需要存储在<WEB应用程序目录>\WEB-INF\classes\目录中
      2. Servlet程序必须在WEB应用程序的web.xml文件中进行注册和映射其访问路径，才可以被Servlet引擎加载和被外界访问
      3. 一个\<servlet>元素用于注册一个Servlet，它包含有两个主要的子元素：\<servlet-name>和\<servlet-class>，分别用于设置Servlet的注册名称和Servlet的完整类名
      4. 一个\<servlet-mapping>元素用于映射一个已注册的Servlet的一个对外访问路径，它包含有两个子元素：\<servlet-name>和\<url-pattern>，分别用于指定Servlet的注册名称和Servlet的对外访问路径

   7. Servlet映射的细节

      1. 同一个Servlet可以被映射到多个URL上，即多个\<servlet-mapping>元素的\<servlet-name>子元素的设置值可以是同一个Servlet的注册名
      2. 在Servlet映射到的URL中也可以使用\*通配符，但是只能有两种固定的格式：
         1. 一种格式是“\*.扩展名”，
         2. 另一种格式是以正斜杠（/）开头并以“/*”结尾
         3. 注意：/\*.action不合法（既带斜杆又有扩展名的不合法）

8. ServletConfig对象

   1. 封装了Servlet的配置信息，并且可以获取ServletContext对象

   2. 配置Servlet的初始化参数

      ```xml
      <!-- 配置和映射Servlet -->
      <servlet>
          <!--Servlet 注册的名字-->
          <servlet-name>helloServlet</servlet-name>
          <!--Servlet 的全类名-->
          <servlet-class>xin.yangshuai.javaweb.servlet.HelloServlet</servlet-class>
          <!--配置Servlet的初始化参数-->
          <init-param>
              <!--参数名-->
              <param-name>user</param-name>
              <!--参数值-->
              <param-value>root</param-value>
          </init-param>
          <init-param>
              <param-name>password</param-name>
              <param-value>1230</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      ```

      1. 要放到load-on-startup标签前面使用

   3. 获取初始化参数

      ```java
      @Override
      public void init(ServletConfig servletConfig) throws ServletException {
         System.out.println("HelloServlet's init");
      
         Enumeration<String> names = servletConfig.getInitParameterNames();
         while (names.hasMoreElements()){
            String name = names.nextElement();
            String value = servletConfig.getInitParameter(name);
            System.out.println(name + " : " + value);
         }
      }
      ```

      1. getInitParameter(String var1)：获取指定参数名的初始化参数
      2. getInitParameterNames()：获取参数名组成的Enumeration\<String> 对象

   4. 获取Servlet的配置名称

      ```java
      String servletName = servletConfig.getServletName();
      System.out.println(servletName);
      ```

9. ServletContext

   1. 可以由ServletConfig获取

   2. 该对象代表当前WEB应用：可以认为ServletContext是当前WEB应用的一个大管家，可以从中获取到当前WEB应用的各个方面的信息

   3. ServletContext常用的方法

      1. 获取 WEB应用的初始化参数

         1. 配置当前WEB应用的初始化参数，可以为所有的Servlet所获取

            ```xml
            <!--配置当前WEB应用的初始化参数-->
            <context-param>
                <param-name>driver</param-name>
                <param-value>com.mysql.jdbc.Driver</param-value>
            </context-param>
            
            <context-param>
                <param-name>jdbcUrl</param-name>
                <param-value>jdbc:mysql:///test</param-value>
            </context-param>
            ```

         2. 获取 WEB应用的初始化参数

            ```java
            ServletContext servletContext = servletConfig.getServletContext();
            
            Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
            while (initParameterNames.hasMoreElements()){
                String name = initParameterNames.nextElement();
                String value = servletContext.getInitParameter(name);
                System.out.println("ServletContext, " + name + " : " + value);
            }
            ```

      2. 获取当前WEB应用的某一文件在服务器上的绝对路径，而不是部署前的路径

         ```java
         String realPath = servletContext.getRealPath("/test.txt");
         System.out.println("realPath :" + realPath);
         ```

         1. 实际该文件不一定真正存在

      3. 获取当前WEB应用的名称

         ```java
         String contextPath = servletContext.getContextPath();
         System.out.println("contextPath : " + contextPath);
         ```

      4. 获取当前WEB应用的某一个文件对应的输入流

         ```java
         InputStream resourceAsStream = servletContext.getResourceAsStream("/WEB-INF/classes/jdbc.properties");
         System.out.println(resourceAsStream);
         ```

         1. 参数为以当前	WEB 应用根路径（“/”）开始路径

10. HTTP协议GET和POST请求

  1. HTTP简介
    1. WEB浏览器也WEB服务器之间的一问一答的交互过程必须遵循一定的规则，这个规则就是HTTP协议
    2. 超文本传输协议的简写，是TCP/IP协议集中的一个应用层协议，用于定义WEB浏览器与WEB服务器之间交换数据的过程以及数据本身的格式。
  2. GET请求
     1. 在浏览器地址输入某个URL地址或单击网页上的一个超链接时，浏览器发出的HTTP请求方式是GET
     2. 如果网页中的\<form>表单元素的method属性被设置为了“GET”，浏览器提交这个FORM表单时生成的HTTP请求消息的请求方式为GET。使用GET请求方式给WEB服务器传递参数的格式：http:/xxx/xx?name=lc&password=123
     3. 使用GET方式传送的数据量一般限制在1KB以下。
  3. POST请求
     1. POST请求方式主要用于向WEB服务器端程序提交FORM表单中的数据：form表单的method置为POST
     2. POST方式将各个表单字段元素及其数据作为HTTP消息的实体内容发送给WEB服务器，传送的数据量要比使用GET方式传送的数据量大得多。  

11. ServletRequest和ServletResponse

    1. 这个两个接口的实现类都是服务器给予实现的，并在服务器调用service方法时传入

12. ServletRequest

    1. 封装了请求信息，可以从中获取到任何的请求信息

    2. 获取请求参数：

       1. `String getParameter(String var1);`：根据请求参数的名字获取请求值
          1. 若请求参数有多个值（例如多选checkbox），该方法只能取到第一个提交的值，应使用getParameterValues方法
       2. `String[] getParameterValues(String var1);`：根据请求参数的名字，返回请求参数对应的字符串数组
       3. `Enumeration<String> getParameterNames();`：返回参数名对应的Enumeration对象，类似与ServletConfig（或ServletContext）的getInitParameterNames()方法
       4. `Map<String, String[]> getParameterMap();`：返回请求参数的键值对

    3. HttpServletRequest：是ServletRequest的子接口，针对于HTTP请求所定义，里边包含了大量获取HTTP请求相关的方法。

       ```java
       HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
       //获取请求的URI
       String requestURI = httpServletRequest.getRequestURI();
       //获取请求方法
       String method = httpServletRequest.getMethod();
       //若是一个GET请求，获取请求参数对应的那个字符串，即 ? 后的那个字符串。
       String queryString = httpServletRequest.getQueryString();
       //获取请求的Servlet的映射路径
       String servletPath = httpServletRequest.getServletPath();
       ```

13. ServletResponse

    1. 封装了响应信息，如果想给用户什么响应，具体可以是该接口的方法实现

    2. 常用方法

       ```java
       servletResponse.setContentType("application/msword");
       PrintWriter writer = servletResponse.getWriter();
       writer.print("Hello World !");
       ```

       1. `getWriter()`：返回PrintWriter 对象，调用该对象的print()方法，将把print()中的参数直接打印到客户的浏览器上
       2. `setContentType("application/msword");`：设置响应内容类型

    3. HttpServletResponse

       1. `void sendRedirect(String var1) `：请求重定向

14. GenericServlet

    1. 是一个Servlet，是Servlet接口和ServletConfig 接口的实现类，但是一个抽象类，其中的service方法为抽象方法
    2. 如果新建的Servlet程序直接继承GenericServlet会使开发更简洁
    3. 具体实现
       1. 在GenericServlet中声明了一个ServletConfig类型的成员变量，在`init(ServletConfig config) `方法中对其进行了初始化
       2. 利用servletConfig成员变量的方法实现了ServletConfig接口的方法
       3. 还定义了一个init()方法，在`init(ServletConfig config) `中对其进行调用，子类可以直接覆盖init()在其中实现对Servlet的初始化
       4. 不建议直接覆盖`init(ServletConfig config) `，因为如果忘记编写super.init(ServletConfig)，而且用了ServletConfig接口的方法，则会出现空指针异常。
       5. 新建的init()并非Servlet的生命周期方法，而init(ServletConfig)使生命周期相关的方法

15. HttpServlet

    1. 是一个Servlet，继承自GenericServlet，针对于HTTP协议所定制
    2. 在service()方法中直接把ServletRequest和ServletResponse转为HttpServletRequest和HttpServletResponse，并调用了重载的service(HttpServletRequest,HttpServletResponse)，在重载的service(HttpServletRequest,HttpServletResponse)方法中获取了请求方式：request.getMethod()，根据请求方式又创建了doXxx()方法（比如doGet，doPost）
    3. 实际开发中，直接继承HttpServlet，并根据请求方式覆写doXxx()方法即可。
    4. 好处：直接有针对性的覆盖doXxx()方法；直接使用HttpServletRequest和HttpServletResponse，不再需要强转

16. 小结及练习

17. JSP概述

18. JSP页面的9个隐含对象

    1. 具体9个隐含对象
       1. request：HttpServletRequest
       2. response：HttpServletResponse
       3. pageContext：PageContext，可以从该对象获取到其它8个隐含对象
       4. session：HttpSession
       5. application：ServletContext
       6. config：ServletConfig
       7. out：JspWriter
       8. page：指向当前JSP对应的Servlet对象的引用，但为Object类型，只能调用Object类的方法（几乎不使用）
       9. exception
    2. 对属性的作用域的范围从小到大：pageContext，request，session，application

19. JSP语法

20. 域对象的属性操作

21. 请求的转发和重定向

    1. 代码

       1. 转发

          ```java
          /**
           * 请求转发
           * 1. 调用 HttpServletRequest 的 getRequestDispatcher 方法获取 RequestDispatcher 对象
           * 2. 调用 RequestDispatcher 的 forward(request,response) 方法进行请求的转发
           */
          RequestDispatcher dispatcher = request.getRequestDispatcher("/second");
          dispatcher.forward(request,response);
          ```

       2. 重定向

          ```java
          /**
           * 请求重定向
           * 直接调用 response 的 sendRedirect 方法
           */
          response.sendRedirect("second");
          ```

    2. 本质区别

       1. 请求的转发值发出了一次请求，而重定向则发出了两次请求

    3. 具体区别

       1. 地址栏
          1. 转发：地址栏是初次发出请求的地址
          2. 重定向：地址栏不再是初次发出的请求地址，地址栏为最后响应的那个地址
       2. request对象
          1. 转发：在最终的Servlet中，request对象和中转的那个request是同一个对象
          2. 重定向：在最终的Servlet中，request对象和中转的那个request不是同一个对象
       3. 请求资源
          1. 转发：只能转发给当前WEB应用的资源
          2. 重定向：可以重定向到任何资源
       4. “/”
          1. 转发：/ 代表的是当前WEB应用的根目录
          2. 重定向：/ 代表的是当前WEB站点的根目录

22. JSP小结（1）

23. page指令

24. include指令

25. JSP标签

26. 中文乱码问题

    1. post请求

       ```java
       request.setCharacterEncoding("UTF-8");
       ```

    2. get请求

       1. 修改Tomcat 的 server.xml 文件

          ```xml
          <Connector port="8080" protocol="HTTP/1.1"
                         connectionTimeout="20000"
                         redirectPort="8443" useBodyEncodingForURI="true"/>
          ```

       2. 设置请求编码

          ```java
          request.setCharacterEncoding("UTF-8");
          ```

27. JSP小结（2）

28. MVC设计模式

29. MVC案例之查询

30. MVC案例之删除

31. MVC案例之架构分析

32. MVC案例之DAO层设计

33. MVC案例之DAO层实现

34. MVC案例之多个请求对应一个Servlet

35. MVC案例之（模糊）查询

36. MVC案例之删除操作

37. MVC案例之小结（1）

38. MVC案例之新增Customer

39. MVC案例之修改思路分析

40. MVC案例之修改代码实现

41. MVC案例之通过配置切换底层存储源

42. MVC案例之小结（2）

43. Cookie概述

    1. 提出问题
       1. HTTP协议是一种无状态的协议
       2. 作为WEB服务器，必须能够采用一种机制来唯一地标识一个用户，同时记录该用户的状态

    2. 会话和会话状态

       1. 借助会话状态，WEB服务器能够把属于同一会话中的一系列的请求和响应过程关联起来

    3. 实现有状态的会话
       1. 浏览器对其发出的每个请求消息都进行标识，同一个会话中的请求消息都附带同样的标识号，这个标识号称之为会话ID（SessionID）
       2. 在Servlet规范中，常用以下两种机制完成会话跟踪
          1. Cookie
          2. Session

    4. Cookie机制
       1. 在客户端保持HTTP状态信息的方案
       2. Cookie是在浏览器访问WEB服务器的某个资源时，由WEB服务器在HTTP响应消息头中附带传送给浏览器的一个小文本文件
       3. 每次访问该WEB服务器时，都会在HTTP请求头中将这个Cookie回传给WEB服务器
       4. 底层实现原理：WEB服务器通过在HTTP响应消息中增加Set-Cookie响应头字段将Cookie信息发送给浏览器，浏览器则通过在HTTP请求消息中增加Cookie请求头字段将Cookie回传给WEB服务器
       5. 一个Cookie只能标识一种信息，它至少含有一个标识信息的名称（NAME）和设置值（VALUE）
       6. 一个WEB站点可以给一个WEB浏览器发送多个Cookie，一个WEB浏览器也可以存储多个WEB站点提供的Cookie
       7. 浏览器一般只允许存放300个Cookie，每个站点最多存放20个Cookie，每个Cookie的大小限制为4KB

    5. Cookie的发送

       1. 创建Cookie对象
       2. 设置最大时效
       3. 将Cookie放入到HTTP相应报头
          1. 默认是一个会话级别的cookie，存储在浏览器的内存中，用户退出浏览器后被删除
          2. 若希望浏览器将该cookie存储在磁盘上，则需要使用maxAge，并给出一个以秒为单位的时间。将最大时效设为0则表示浏览器立即删除该cookie，若为负数，表示不储存该Cookie
          3. HttpServletResponse的addCookie方法，将cookie插入到一个Set-Cookie HTTP响应报头中。由于这个方法并不修改任何之前指定的Set-Cookie报头，而是创建新的报头，因此将这个方法称为是addCookie，而非setCookie

    6. 会话cookie和持久cookie的区别

       1. 会话cookie，不设置过期时间，会话cookie一般不保存在硬盘上而保存在内存里。
       2. 如果设置里过期时间，浏览器会把cookie保存到硬盘上，关闭后再次打开浏览器，这些cookie依然有效直到超过设定的过期时间
       3. 存储在硬盘上的cookie可以在不同的浏览器进程间共享，比如两个IE窗口。而对于保存在内存的cookie，不同的浏览器有不同的处理方式

    7. Cookie相关的API

       ```java
       //1. 创建一个Cookie对象
       Cookie cookie = new Cookie("name","cookie001");
       //2. setMaxAge：单位：秒，值为0，表示立即删除；值为负数，表示不储存该Cookie；值为正数，表示该Cookie的存储时间
       cookie.setMaxAge(30);
       //3. 设置Cookie 的作用范围
       cookie.setPath(request.getContextPath());
       //4. 调用response的一个方法把Cookie传给客户端
       response.addCookie(cookie);
       //5. 从浏览器读取Cookie
       Cookie[] cookies = request.getCookies();
       ```

44. 利用Cookie进行自动登录

45. 利用Cookie显示最近浏览的商品

46. 设置Cookie的作用路径

    1. Cookie的默认作用范围

       1. 可以作用当前目录和当前目录的子目录，但不能作用于当前目录的上一级目录

    2. 设置Cookie的作用范围

       ```java
       Cookie cookie = new Cookie("cookiepath","cookiepathvalue");
       cookie.setPath(request.getContextPath());
       response.addCookie(cookie);
       ```

       1. 可以通过setPath方法来设置Cookie的作用范围
       2. setPath设置为项目应用的根目录时，注意要有项目名

47. Cookie小结

48. HttpSession概述

    1. Session机制
       1. Session的含义是指一类用来在客户端与服务器之间保持状态的解决方案。有时候Session也用来指这种解决方案的存储结构。session机制采用的是在服务器端保持HTTP状态信息的方案
       2. 服务器使用一种类似于散列表的结构（也可能就是使用散列表）来保存信息
       3. 当程序需要为某个客户端的请求创建一个session时，服务器首先检查这个客户端的请求里是否包含了一个session标识（即sessionId），如果已经包含了一个sessionId则说明以前已经为此客户创建过session，服务器就按照sessionId把这个session检索出来使用（如果检索不到，可能会新建一个，这种情况可能出现在服务端已经删除了该用户对应的session对象，但用户人为地在请求的URL后面附加上一个JSESSION的参数）。如果客户请求不包含sessionId，则为此客户创建一个session并且生成一个与此session相关联的sessionId，这个sessionId将在本次响应中返回给客户端保存。 
    2. 保存session id的几种方式
       1. 保存session id的方式可以采用cookie，这样在交互过程中浏览器可以自动的按照规则把这个标识发送给服务器。
       2. 由于cookie可以被人为的禁用，必须有其它的机制以便在cookie被禁用时仍然能够把session id传递回服务器，经常采用的一种技术叫做URL重写，就是把session id附加在URL路径的后面，附加的方式也有两种，一种时作为URL路径的附加信息，另一种是作为查询字符串附加在URL后面。网络在整个交互过程中始终保持状态，就必须在每个客户端可能请求的路径后面都包含这个session id

49. HttpSession的生命周期

    1. 什么时候创建 HttpSession对象

       1. 对于JSP
          1. 当前的JSP是客户端访问的当前WEB应用的第一个资源，且JSP的page指定的session属性的值为false，则服务器就不会为JSP创建一个HttpSession对象，session="false"表示当前JSP页面禁用sessoin隐含变量，但可以使用其它的显示HttpSession对象。
          2. 当前JSP不是客户端访问的当前WEB应用的第一个资源，且其它页面已经创建一个HttpSession对象，则服务器不会为当前JSP页面创建一个新的HttpSession对象，而会把和当前会话关联的那个HttpSessoin对象返回给当前的JSP页面。
       2. 对于Servlet
          1. 若Servlet是客户端访问的第一个WEB应用的资源，则只有调用了request.getSession()或request.getSession(true)才会创建HttpSession对象

    2. 如何获取HttpSession对象

       1. request.getSession(boolean create)
          1. create 为 false，若没有和当前 JSP 页面关联的HttpSession对象，则返回 null ，若有，返回对象，
          2. request.getSession(true) 等同于 request.getSessoin()

    3. 什么时候销毁HttpSession对象

       1. 调用HttpSsession 的 invalidate() 方法，使HttpSession失效

       2. 服务器卸载当前WEB应用

       3. 超出HttpSsession的过期时间，setMaxInactiveInterval(时间，单位秒)

          1. 在web.xml 文件中设置HttpSession的过期时间，单位为 分钟。

             ```xml
             <session-config>
                 <session-timeout>30</session-timeout>
             </session-config>
             ```

       4. 并不是关闭了浏览器就销毁了HttpSession

50. HttpSession常用方法示例

    1. 获取Session对象：request.getSession()、request.getSession(boolean create)
    2. 属性相关的：setAttribute，getAttribute，removeAttrite
    3. 使HttpSession失效：invalidate()
    4. 设置其最大失效时长：setMaxInactiveInterval

51. HttpSessionURL重写

    1. HttpServletResponse接口中定义了两个用于完成URL重写的方法
       1. encodeURL方法
       2. encodeRedirectURL方法

52. HttpSession小结（1）

53. HttpSession之简易购物车

54. JavaWeb中的相对路径和绝对路径

55. HttpSession之表单的重复提交

56. HttpSession之验证码

57. HttpSession小结（2）

58. 使用JavaBean

59. EL语法

60. EL详解

61. 简单标签的HelloWorld

62. 带属性的自定义标签

63. 带标签体的自定义标签

64. 带父标签的自定义标签

65. EL自定义函数

66. 简单标签小结

67. JSTL表达式操作

68. JSTL流程控制操作

69. JSTL迭代操作

70. JSTL_URL操作

71. JSTL改写MVC案例

72. Filter概述

    1. 过滤器简介

       1. Filter 的基本功能是对Servlet容器调用Servlet的过程进行拦截，从而在Servlet进行响应处理的前后实现一些特殊的功能
       2. 在Servlet API 中定义了三个接口类提供给开发人员编写Filter程序：Filter，FilterChain，FilterConfig
       3. Filter程序是一个实现了Filter接口的Java类，与Servlet程序相似，它由Servlet容器进行调用和执行
       4. Filter程序需要在web.xml文件中进行注册和设置它所能拦截的资源：Filter程序可以拦截Jsp，Servlet，静态图片文件和静态html文件
       5. 与开发Servlet不同的是，Filter接口并没有相应的实现类可供继承，要开发过滤器，只能直接实现Filter接口。

    2. 创建一个Filter

       1. 创建一个Filter类

          ```java
          public class HelloFilter implements Filter {
             @Override
             public void init(FilterConfig filterConfig) throws ServletException {
                System.out.println("HelloFilter's init...");
             }
             @Override
             public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                System.out.println("HelloFilter's doFilter...");
             }
             @Override
             public void destroy() {
                System.out.println("HelloFilter's destroy...");
             }
          }
          ```

       2. 在web.xml文件中配置和注册Filter

          ```xml
          <!--注册Filter-->
          <filter>
              <filter-name>helloFilter</filter-name>
              <filter-class>xin.yangshuai.javaweb.filter.HelloFilter</filter-class>
          </filter>
          <!--映射Filter-->
          <filter-mapping>
              <filter-name>helloFilter</filter-name>
              <url-pattern>/hello</url-pattern>
          </filter-mapping>
          ```

          1. 其中url-pattern 指定该Filter 可以拦截哪些资源，即可以通过哪些url 访问到该Filter

       3. 相关API

          1. public void init(FilterConfig filterConfig)：类似于Servlet 的init方法，在创建Filter对象后，立即被调用，且只被调用一次，该方法用于对当前的Filter进行初始化操作。Filter实例是单例的。
             1. FilterConfig 类似于 ServletConfig
             2. 可以在web.xml 文件中配置当前Filter 的初始化参数，配置方式也和Servlet 类似
          2. public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)：正在 Filter 的逻辑代码需要编写在该方法中，每次拦截都会调用该方法。
             1. FilterChain：Filter链，多个 Filter 可以构成一个Filter链。
                1. doFilter(ServletRequest var1, ServletResponse var2)：把请求传给Filter链的下个Filter，若当前Filter是Filter链的最后一个Filter，将把请求给到目标Servlet（或JSP）
                2. 多个Filter 拦截的顺序和filter-mapping配置的顺序有关，靠前的先被调用
          3. public void destroy()：释放当前Filter所占用的资源费方法。在Filter被销毁之前被调用，且只被调用一次

73. 创建HttpFilter

74. 理解多个Filter代码的执行顺序

75. 配置Filter的dispatcher节点

    1. 指定过滤器所拦截的资源被Servlet 容器调用的方式，可以是REQUEST,INCLUDE,FORWARD和ERROR之一，默认REQUEST，可以设置多个dispatcher子元素来指定Filter 对资源的多种调用方式进行拦截
    2. 注意：
       1. JSP页面，配置指令指定的errorPage跳转页面属于转发（FORWARD）
       2. ERROR：如果目标资源是通过声明式异常处理机制调用的，那么该过滤器将被调用，除此之外，过滤器不会被调用。在web.xml 文件通过error-page节点进行声明。

76. 禁用浏览器缓存的过滤器

    ```java
    public class NoCacheFilter extends MyHttpFilter {
       @Override
       public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
          response.setDateHeader("Expires",-1);
          response.setHeader("Cache-Control","no-cache");
          response.setHeader("Pragma","no-cache");
          chain.doFilter(request,response);
       }
    }
    ```

77. 字符编码过滤器

78. 检查用户是否登录的过滤器

79. Filter小结（1）

80. 权限管理思路分析

81. 权限管理代码实现

82. 权限过滤思路分析

83. 权限过滤代码实现

84. HttpServletRequestWrapper

    ```java
    public class MyHttpServletRequest extends HttpServletRequestWrapper {
       public MyHttpServletRequest(HttpServletRequest request) {
          super(request);
       }
       @Override
       public String getParameter(String name) {
          String parameter = super.getParameter(name);
          return parameter + " Hello World !";
       }
    }
    ```

    1. Servlet API 中提供了一个HttpServletRequestWrapper类来包装原始的request对象，HttpServletRequestWrapper类实现了HttpServletRequest接口中的所有方法，这些方法的内部实现都是仅仅调用了一下所包装的request对象的对应方法

85. Filter小结（2）

86. 监听域对象创建和销毁的Listener

    1. 简介

       1. 监听器：专门用于对其它对象身上发生的事件或状态改变进行监听和相应处理的对象，当被监听的对象发生情况时立即采取相应的行动。
       2. Servlet监听器：Servlet规范中定义的一种特殊类，它用于监听web应用程序中的ServletContext，HttpSession 和 ServletRequest 等域对象的创建与销毁事件，以及监听这些域对象中的属性发生修改的事件。

    2. 按监听的事件类型 Servlet 监听器可分为如下三种类型

       1. 监听域对象自身的创建和销毁的事件监听器：

          1. 创建一个实现ServletContextListener 接口的类

             ```java
             public class HelloServletContextListener implements ServletContextListener {
                @Override
                public void contextInitialized(ServletContextEvent servletContextEvent) {
                   System.out.println("ServletContext 对象被创建。" + servletContextEvent.getServletContext());
                }
                @Override
                public void contextDestroyed(ServletContextEvent servletContextEvent) {
                   System.out.println("ServletContext 对象被销毁。" + servletContextEvent.getServletContext());
                }
             }
             ```

          2. 在web.xml 文件中配置 Listener

             ```xml
             <listener>
                 <listener-class>xin.yangshuai.javaweb.listener.HelloServletContextListener</listener-class>
             </listener>
             ```

          3. 使用场景

             1. ServletContextListener 是最常见的 Listener，可以在当 WEB 应用被加载是对当前 WEB 应用的相关资源进行初始化操作：创建数据库连接池，创建Spring 的 IOC 容器 ，读取当前 WEB 应用的初始化参数 ...

          4. API

             1. public void contextInitialized(ServletContextEvent servletContextEvent)：ServletContext 对象被创建（即当前 WEB 应用被加载）的时候，Servlet 容器调用该方法。
                1. ServletContextEvent：getServletContext() 获取 ServletContext
             2. public void contextDestroyed(ServletContextEvent servletContextEvent)：ServletContext 对象被销毁之前（即当前 WEB 应用被卸载）的时候，Servlet 容器调用该方法。

          5. 类似的ServletRequestListener、HttpSessionListener

       2. 监听域对象中的属性的增加和删除的事件监听器

       3. 监听绑定到 HttpSession 域中的某个对象的状态的事件监听器

87. 通过Listener理解域对象生命周期

    1. request：是一个请求，当一个响应返回时，即被销毁。当发送一个请求时被创建。注意，请求转发的过程是一个request 对象。重定向是两个请求。
    2. session：当第一次访问 WEB 应用的一个JSP 或 Servlet 时，且该 JSP 或 Servlet 中还需要创建 session 对象，此时服务器会创建一个 session 对象。
       1. session 销毁：session 过期、直接 调用 session 的 invalidate 方法、当前 web 应用被卸载（session 可以被持久化）
       2. 关闭浏览器，并不意味着 session 销毁，还可以通过 sessionid 找到服务器中的 session 对象。
       3. application：贯穿于当前的 WEB 应用的生命周期，当前 WEB 应用被加载时创建 application 对象，当前 WEB 应用被卸载时销毁 application 对象。

88. 其它的Servlet监听器

    1. 域对象中属性的变更的事件监听器

       ```java
       public class HelloAttributeListener implements ServletContextAttributeListener,HttpSessionAttributeListener,ServletRequestAttributeListener {
          @Override
          public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("context属性添加：");
             System.out.println(servletContextAttributeEvent.getName());
             System.out.println(servletContextAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("context属性移除：");
             System.out.println(servletContextAttributeEvent.getName());
             System.out.println(servletContextAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("context属性替换：");
             System.out.println(servletContextAttributeEvent.getName());
             System.out.println(servletContextAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("request属性添加：");
             System.out.println(servletRequestAttributeEvent.getName());
             System.out.println(servletRequestAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("request属性移除：");
             System.out.println(servletRequestAttributeEvent.getName());
             System.out.println(servletRequestAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("request属性替换：");
             System.out.println(servletRequestAttributeEvent.getName());
             System.out.println(servletRequestAttributeEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("session属性添加：");
             System.out.println(httpSessionBindingEvent.getName());
             System.out.println(httpSessionBindingEvent.getValue());
             System.out.println(httpSessionBindingEvent.getSession().getId());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("session属性移除：");
             System.out.println(httpSessionBindingEvent.getName());
             System.out.println(httpSessionBindingEvent.getValue());
             System.out.println(httpSessionBindingEvent.getSession().getId());
             System.out.println("---------------------------------------------------");
          }
       
          @Override
          public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("session属性替换：");
             System.out.println(httpSessionBindingEvent.getName());
             System.out.println(httpSessionBindingEvent.getValue());
             System.out.println(httpSessionBindingEvent.getSession().getId());
             System.out.println("---------------------------------------------------");
          }
       }
       ```

       1. 域对象中属性的变更的事件监听器就是用来监听ServletContext，HttpSession，ServletRequest这个三个对象中的属性变更信息（添加，替换，移除）事件的监听器。
       2. 这三个监听器接口分别是ServletContextAttributeListener，HttpSessionAttributeListener，ServletRequestAttributeListener，这三个接口中都定义了三个方法来处理被监听对象中的属性的增加，删除和替换的事件，同一个事件在这个三个接口中对应的方法名称完全相同，只是接受的参数类型不同。
       3. 这三个监听器较少被使用
       4. API：ServletContextAttributeEvent、ServletRequestAttributeEvent、HttpSessionBindingEvent
          1. getName()：获取属性的名字
          2. getValue()：获取属性的值（attributeReplaced方法中获取的是旧值）

    2. 感知Session绑定的事件监听器

       ```java
       public class Customer implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {
          private static final long serialVersionUID = 1054819286381698920L;
          private String name;
          private Integer age;
          public Customer(String name, Integer age) {
             this.name = name;
             this.age = age;
          }
          public String getName() {
             return name;
          }
          public void setName(String name) {
             this.name = name;
          }
          public Integer getAge() {
             return age;
          }
          public void setAge(Integer age) {
             this.age = age;
          }
          @Override
          public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("绑定到session：");
             System.out.println(httpSessionBindingEvent.getName());
             System.out.println(httpSessionBindingEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
          @Override
          public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
             System.out.println("---------------------------------------------------");
             System.out.println("从session中解除绑定：");
             System.out.println(httpSessionBindingEvent.getName());
             System.out.println(httpSessionBindingEvent.getValue());
             System.out.println("---------------------------------------------------");
          }
          @Override
          public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
             System.out.println(httpSessionEvent.getSession());
             System.out.println("从内存中写到磁盘上...");
          }
          @Override
          public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
             System.out.println(httpSessionEvent.getSession());
             System.out.println("从磁盘中读取出来...");
          }
       }
       ```

       1. 保存在Session域中的对象可以有多种状态：绑定到Session中；从Session域中解除绑定；随Session对象持久化到一个存储设备中；随Session对象从一个存储设备中恢复
       2. Servlet规范中定义了两个特殊的监听器接口来帮助JavaBean对象了解自己在Session域中的这些状态：HttpSessionBindingListener 接口和 HttpSessionActivationListener 接口，实现这两个接口的类不需要web.xml文件中进行注册
       3. HttpSessionBindingListener接口：实现了HttpSessionBindingListener接口的JavaBean对象可以感知自己被绑定到Session中和从Session中解除绑定  的事件
          1. 绑定：调用public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent)方法
          2. 解除绑定：调用public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent)方法
          3. 该监听器较少被使用
       4. HttpSessionActivationListener接口：实现了HttpSessionActivationListener 接口的JavaBean对象可以感知自己被活化和钝化的事件，同时需要实现Serializable
          1. 钝化之前：调用public void sessionWillPassivate(HttpSessionEvent httpSessionEvent)方法
          2. 活化之后：调用public void sessionDidActivate(HttpSessionEvent httpSessionEvent)方法
          3. session对象存储在 tomcat 服务器的目录下， SESSION.SER
          4. 该监听器较少被使用

89. Servlet监听器小结

90. 文件上传基础

    1. 表单需要做的准备

       ```html
       <form action="<%= application.getContextPath() %>/upload" method="post" enctype="multipart/form-data">
           <input type="file" name="file">
           <input type="submit" value="提交">
       </form>
       ```

       1. 请求方式：post
       2. 请求编码方式：enctype="multipart/form-data"
       3. 使用file表单域：\<input type="file" name="file"\>

    2. 关于enctype

       1. 默认：enctype="application/x-www-form-urlencoded"
       2. 传输二进制：enctype="multipart/form-data"

    3. 服务端

       1. 不能再使用 request.getParameter("file") 等方式获取请求信息，获取不到，因为请求的编码已经改成multipart/form-data，以二进制的方式来提交请求信息。

       2. 可以使用输入流的方式来获取，但不建议这样做。

          ```java
          InputStream in = request.getInputStream();
          Reader reader = new InputStreamReader(in);
          BufferedReader bufferedReader = new BufferedReader(reader);
          String str = null;
          while ((str = bufferedReader.readLine()) != null){
             System.out.println(str);
          }
          ```

       3. 具体使用 commons-fileupload 组件来完成文件的上传操作。

91. 使用fileupload组件

    1. 搭建环境：加入jar包，commons-io，commons-fileupload

    2. 基本思想：

       1. commons-fileupload 可以解析请求，得到一个 FileItem 对象组成的List

       2. commons-fileupload 把所有的请求信息都解析为 FileItem 对象，无论是一个一般的文本域还是一个文件域。

       3. 可以调用FileItem的 isFormField() 来判断是一个表单域 或不是 表单域（则是一个文件域）

       4. 具体实现

          1. 如果是表单域

             ```java
             if(item.isFormField()){
                 String name = item.getFieldName();
                 String value = item.getString();
                 ....
             }
             ```

          2. 如果不是表单域

             ```java
             if(!item.isFormField()){
                 String fieldName = item.getFieldName();
                 String fileName = item.getName();
                 String contentType = item.getContentType();
                 boolean isInMemory = item.isInMemory();
                 long sizeInBytes = item.getSize();
                 
                 InputStream uploadedStream = item.getInputStream();
                 ...
                 uploadedStream.close();
             }
             ```

          3. 获取FileItem对象数组

             1. 简单方式

                ```java
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                ```

             2. 复杂方式：可以为文件的上传加入一些限制条件和其它属性

                ```java
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //设置内存中最多可以存放的上传文件大小，若超出则把文件写到一个临时文件夹中，以byte 为单位
                factory.setSizeThreshold(yourMaxMemorySize);
                //设置临时文件夹
                factory.setRepository(yourTempDirectory);
                ServletFileUpload upload = new ServletFileUpload(factory);
                //设置上传文件的总的大小，也可以设置单个文件的大小
                upload.setSizeMax(yourMaxRequestSize);
                List<FileItem> items = upload.parseRequest(request);
                ```

          4. 基本代码

             ```java
             File yourTempDirectory = new File("E:\\tempDirectory");
             Integer yourMaxMemorySize = 1024 * 500;
             Integer yourMaxRequestSize = 1024 * 1024 * 5;
             DiskFileItemFactory factory = new DiskFileItemFactory();
             //设置临时文件夹
             factory.setRepository(yourTempDirectory);
             //设置内存中最多可以存放的上传文件大小，若超出则把文件写到一个临时文件夹中，以byte 为单位
             factory.setSizeThreshold(yourMaxMemorySize);
             ServletFileUpload upload = new ServletFileUpload(factory);
             //设置上传文件的总的大小，也可以设置单个文件的大小
             upload.setSizeMax(yourMaxRequestSize);
             try {
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                   if (item.isFormField()) {
                      String name = item.getFieldName();
                      String value = item.getString();
                      System.out.println(name + " : " + value);
                   } else {
                      String fieldName = item.getFieldName();
                      String fileName = item.getName();
                      String contentType = item.getContentType();
                      boolean isInMemory = item.isInMemory();
                      long sizeInBytes = item.getSize();
                      System.out.println(fieldName);
                      System.out.println(fileName);
                      System.out.println(contentType);
                      System.out.println(isInMemory);
                      System.out.println(sizeInBytes);
                      InputStream in = item.getInputStream();
                      byte[] buffer = new byte[1024];
                      int len = 0;
                      fileName = "E:\\tempDirectory\\" + fileName;
                      System.out.println(fileName);
                      OutputStream out = new FileOutputStream(fileName);
                      while ((len = in.read(buffer)) != -1) {
                         out.write(buffer, 0, len);
                      }
                      out.close();
                      in.close();
                   }
                }
             } catch (FileUploadException e) {
                e.printStackTrace();
             }
             ```

92. 文件上传案例_需求

93. 文件上传案例_JS代码

94. 文件上传案例_约束的可配置性

95. 文件上传案例_总体步骤分析

96. 文件上传案例_构建FileUploadBean集合

97. 文件上传案例_完成文件的上传

98. 文件上传案例_复习

99. 文件上传案例_校验及小结

100. 文件下载

  ```java
  response.setContentType("application/x-msdownload");
  String fileName = "图片.jpg";
  response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
  String realPath = getServletContext().getRealPath("/file/wallhaven-697055.jpg");
  ServletOutputStream out = response.getOutputStream();
  InputStream in = new FileInputStream(realPath);
  byte [] buffer = new byte[1024];
  int len = 0;
  while ((len = in.read(buffer)) != -1){
     out.write(buffer,0,len);
  }
  in.close();
  ```

  1. 通知客户端浏览器：这个一个需要下载的文件，不能再按普通的html的方式打开，即设置一个响应的类型：response.setContentType("application/x-msdownload");
  2. 通知客户端浏览器：不再由浏览器来处理该文件，而是交由用户自行处理，即设置用户处理的方式：response.setHeader("Content-Disposition","attachment;filename=123.txt");
  3. 具体文件：可以调用 response.getOutputStream() 的方式，以 IO 流的方式发送给客户端。

101. 国际化之Locale

    1. 概述

      1. 软件本地化：一个软件在某个国家或地区使用时，采用该国家或地区的语言，数字，货币，日期等习惯
      2. 软件的国际化：软件开发时，让它能支持多个国家和地区的本地化应用。
      3. 本地信息敏感数据：随用户区域信息而变化的数据称为本地信息敏感数据
      4. i18n：国际化又称为i18n，internationalization

    2. Locale

      ```java
      Locale locale = request.getLocale();
      System.out.println(locale);
      locale = Locale.CHINA;
      System.out.println(locale);
      locale = new Locale("EN","US");
      System.out.println(locale);
      ```

102. 国际化之DateFormat

    ```java
    Locale locale = Locale.CHINA;
    Date date = new Date();
    System.out.println(date);
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, locale);
    String format = dateFormat.format(date);
    System.out.println(format);
    String str = "2019-01-11 17:20:08";
    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    Date parse = simpleDateFormat.parse(str);
    System.out.println(parse);
    ```

103. 国际化之NumberFormat

    ```java
    locale = Locale.CHINA;
    double d = 123456789.123d;
    //格式化为数字字符串
    NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
    String format1 = numberFormat.format(d);
    System.out.println(format1);
    //格式化为货币字符串
    NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
    String format2 = currencyInstance.format(d);
    System.out.println(format2);
    
    String str1 = "123,456,789.123";
    Double parse1 = (Double) numberFormat.parse(str1);
    System.out.println(parse1);
    str1 = "￥123,456,789.123";
    Double parse2 = (Double) currencyInstance.parse(str1);
    System.out.println(parse2);
    ```

104. 国际化之MessageFormat

    ```java
    locale = Locale.CHINA;
    String str2 = "Date：{0}，Salary：{1}";
    Date date1 = new Date();
    double sal = 12345.12;
    String format3 = MessageFormat.format(str2, date1, sal);
    System.out.println(format3);
    DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    String format4 = dateInstance.format(date1);
    NumberFormat currencyInstance1 = NumberFormat.getCurrencyInstance(locale);
    String format5 = currencyInstance1.format(sal);
    format3 = MessageFormat.format(str2, format4, format5);
    System.out.println(format3);
    ```

    1. MessageFormat：可以格式化模式字符串
    2. 模式字符串：带占位符的字符串："Date：{0}，Salary：{1}"
    3. 可以通过 format 方法对模式字符串进行格式化

105. 国际化之ResourceBundle

    ```java
    date1 = new Date();
    sal = 12345.12;
    locale = Locale.CHINA;
    ResourceBundle i18n = ResourceBundle.getBundle("i18n", locale);
    String dateLable = i18n.getString("date");
    String salLable = i18n.getString("salary");
    String str3 = "{0}：{1}，{2}：{3}";
    dateInstance = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    format4 = dateInstance.format(date1);
    currencyInstance1 = NumberFormat.getCurrencyInstance(locale);
    format5 = currencyInstance1.format(sal);
    format3 = MessageFormat.format(str3, dateLable, format4, salLable, format5);
    System.out.println(format3);
    ```

    1. ResourceBundle类用于描述一个资源包，一个资源包用于包含一组与某个本地环境相关的对象，可以从一个资源包中获取特定于本地环境的对象。对于不同的本地环境，可以有不同的ResourceBundle对象与之关联，关联的ResourceBundle对象中包含该本地环境下专有的对象。
      1. 在类路径下需要有对应的资源文件：baseName.properties，其中 baseName 是基名
      2. 使用 基名\_语言代码\_国家代码.properties 来添加不同国家或地区的资源文件：i18n\_zh\_CN.properties
      3. 要求所有基名相同的资源文件的key 必须完全一致
      4. 可以使用native2ascii 命令来得到 汉字 对应的ascii 码
      5. 可以调用ResourceBundle.getBundle(基名, Local实例) 来获取 ResourceBundle 对象
      6. 可以调用ResourceBundle 的getString(key) 方法来获取资源文件的 value 字符串的值
      7. 结合 DateFormat，NumberFormat，MessageFormat 即可实现国际化。

106. 国际化之fmt标签及小结

    ```jsp
    <c:if test="${sessionScope.locale != null}">
      <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="i18n"/>
    <fmt:message key="date"/> : <fmt:formatDate value="${date1}" dateStyle="FULL"/> ,
    <fmt:message key="salary"/> : <fmt:formatNumber value="${salary1}" type="currency"/>
    <br>
    <a href="<%=request.getContextPath()%>/i18n?locale=zh_CN">中文</a>
    <a href="<%=request.getContextPath()%>/i18n?locale=en_US">英文</a>
    ```

