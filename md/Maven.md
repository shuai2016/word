1. # 构建过程环节

   1. 清理：编译之前删除旧的class字节码文件
   2. 编译：将java源程序编译成class字节码文件
   3. 测试：自动测试，自动调用junit程序
   4. 报告：测试程序执行结果
   5. 打包：动态web工程打war包，java工程打jar包
   6. 安装：Maven特定的概念——将打包得到的文件复制到”仓库“中的指定位置
   7. 部署：将动态Web工程生成的war包复制到servlet容器的指定目录下，使其可以运行。

2. # 安装Maven核心程序

   1. 检查JAVA_HOME环境变量
   2. 解压Maven核心程序
   3. 配置maven相关环境变量
      1. MAVEN_HOME或M2_HOME
      2. Path
      3. 运行mvn -v命令查看Maven版本

3. # Maven的核心概念

   1. ## 约定的目录结构

      1. 目录结构

         1. 根目录：工程名
         2. src目录：源码
         3. pom.xml文件：Maven工程的核心配置文件
         4. main目录：存放主程序
         5. test目录：存放测试程序
         6. java目录：存放java源文件
         7. resources目录：存放框架或其他工具的配置文件

      2. 结构图

         ```properties
         Hello
         |---src
         |---|---main
         |---|---|---java
         |---|---|---resources
         |---|---test
         |---|---|---java
         |---|---|---resources
         |---pom.xml
         ```

      3. 为什么使用规定的目录结构

         1. Maven负责项目的自动化构建，编译时，Maven需要知道Java源文件保存的位置
         2. 自定义的内容如何让框架或工具知道？
            1. 以配置的方式明确告诉框架
            2. 遵守框架内部已经存在的约定
         3. 约定>配置>编码

   2. ## POM

      1. 含义：Project Object Model项目对象模型
         1. Dom Document Object Model 文档对象模型
      2. pom.xml对于Maven工程是核心配置文件，与构建过程相关的一切设置都在这个文件中进行配置。
         1. 重要程度相当于web.xml对于动态Web工程

   3. ## 坐标

      1. 数学中的坐标：

         1. 在平面上，使用X、Y两个向量可以唯一的定位平面中的任何一个点
         2. 在空间中，使用X、Y、Z三个向量可以唯一的定位空间中的任何一个点

      2. Maven的坐标（GAV）

         1. 使用三个向量在仓库中唯一定位一个Maven工程

            1. **g**roupid：公司或组织域名倒序+项目名

               `<groupid>com.shuai.maven</groupid>`

            2. **a**rtifactid：模块名

               `<artifactid>Hello</artifactid>`

            3. **v**ersion：版本

               `<version>1.0.0</version>`

      3. Maven工程的坐标 与仓库中路径的对应关系

         1. 位置

            1. 路径：`D:\work\mavenCK\org\springframework\spring-core\4.3.20.RELEASE`
            2. 文件名：`spring-core-4.3.20.RELEASE.jar`

         2. pom

            1. 坐标

               ```properties
               <groupId>org.springframework</groupId>
               <artifactId>spring-core</artifactId>
               <version>4.3.20.RELEASE</version>
               ```

            2. 命名规则

               1. 路径

                  `groupId(.换成\)\artifactId\version`

               2. 文件名

                  `artifactId-version.jar`

   4. ## 依赖

      1. Maven解析依赖信息时会到本地仓库中查找被依赖的jar包。
         1. 对于我们自己开发的Maven工程，使用`mvn install`命令安装后就可以进入仓库。

         2. 引用一个依赖

            ```properties
            <dependency>
                <groupId>com.shuai</groupId>
                <artifactId>Hello</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <scope>compile</scope>
            </dependency>
            ```

      2. 依赖的范围（scope）

         1. compile范围依赖
            1. 对主程序是否有效：有效
            2. 对测试程序是否有效：有效
            3. 是否参与打包：参与
            4. 是否参与部署：参与
            5. 典型例子：spring-core
         2. test范围依赖
            1. 对主程序是否有效：无效
            2. 对测试程序是否有效：有效
            3. 是否参与打包：不参与
            4. 是否参与部署：不参与
            5. 典型例子：junit
         3. provided
            1. 对主程序是否有效：有效
            2. 对测试程序是否有效：有效
            3. 是否参与打包：不参与
            4. 是否参与部署：不参与
            5. 典型例子：servlet-api.jar

   5. ## 仓库

      1. 仓库分类
         1. 本地仓库：当前电脑上部署的仓库目录，为当前电脑上所有Maven工程服务
         2. 远程仓库
            1. 私服（Nexus私服）：搭建在局域网环境中，为局域网范围内的所有Maven工程服务
            2. 中央仓库：架设在Internet上，为全世界所有Maven工程服务
            3. 中央仓库镜像：分担中央仓库的流量，提升用户访问速度
      2. 仓库中保存的内容：Maven工程
         1. Maven自身所需要的插件
         2. 第三方框架或工具的jar包（第一方：jdk，第二方：自己）
         3. 我们自己开发的Maven工程

   6. ## 生命周期/插件/目标

      1. 各个构建环节执行的顺序：不能打乱顺序，必须按照既定的正确顺序来执行。

      2. Maven的核心程序中定义了抽象的生命周期，生命周期中各个阶段的具体任务是由插件来完成的。

      3. Maven核心程序为了更好的实现自动化构建，按照这一特点执行生命周期中的各个阶段：不论现在要执行生命周期中的哪一个阶段，都是从这个生命周期的最初的位置开始执行。

         1. `mvn compile`

            ```properties
            maven-resources-plugin:2.6:resources
            maven-compiler-plugin:2.5.1:compile
            ```

         2. `mvn test`

            ```properties
            maven-resources-plugin:2.6:resources
            maven-compiler-plugin:2.5.1:compile
            maven-resources-plugin:2.6:testResources
            maven-compiler-plugin:2.5.1:testCompile
            maven-surefire-plugin:2.12.4:test
            T E S T S
            ```

      4. 插件和目标

         1. 生命周期的各个阶段仅仅定义了要执行的任务是什么

         2. 各个阶段和插件的目标是对应的

         3. 相似的目标由特定的插件来完成

            | 生命周期阶段 | 插件目标    | 插件                  |
            | ------------ | ----------- | --------------------- |
            | compile      | compile     | maven-compiler-plugin |
            | test-compile | testCompile | maven-compiler-plugin |

         4. 可以将目标看作“调用插件功能的命令”

   7. ## 依赖[高级]

      1. 依赖的传递性

         1. 好处：可以传递的依赖不必在每个模块工程中都重复声明，在“最下面”的工程中依赖一次即可。
         2. 注意：非compile范围的依赖不能传递。所以在各个工程模块中，如果有需要就得重复声明依赖。

      2. 依赖的排除

         1. 依赖排除的设置方式

            ```properties
            <dependency>
            	<groupId>com.shuai.maven</groupId>
            	<artifactId>HelloFriend</artifactId>
            	<version>0.0.1-SNAPSHOT</version>
            	<scope>compile</scope>
            	<exclusions>
            		<exclusion>
            			<groupId>commons-logging</groupId>
            			<artifactId>commons-logging</artifactId>
            		</exclusion>
            	</exclusions>
            </dependency>
            ```

      3. 依赖的原则

         1. 作用：解决模块工程之间的jar包冲突问题，maven根据自己的内置原则自动处理冲突
         2. 原则：
            1. 路径最短者优先原则
            2. 先声明者优先：dependency标签的声明顺序

      4. 统一管理依赖的版本

         1. 建议配置方式

            1. 使用properties标签，标签内使用自定义标签统一声明版本号

               ```properties
               <properties>
               	<spring.version>4.3.13.RELEASE</spring.version>
               </properties>
               ```

            2. 在需要统一版本的位置，使用`${自定义标签名}`引用声明的版本号

               ```properties
               <dependency>
               	<groupId>org.springframework</groupId>
               	<artifactId>spring-core</artifactId>
               	<version>${spring.version}</version>
               </dependency>
               ```

            3. 其实properties标签配合自定义标签声明数据的配置并不是只能用于声明依赖的版本号，凡是需要统一声明后再引用的场合都可以使用。

   8. ## 继承

      1. 现状
         1. Hello依赖的junit：4.0
         2. HelloF人多依赖的junit：4.0
         3. MakeFriends依赖的junit：4.9
         4. 由于test范围的依赖不能传递，所以必然会分散在各个模块工程中，很容易造成版本不一致。

      2. 需求：统一管理各个模块工程中对junit依赖的版本。

      3. 解决思路：将junit依赖统一提取到“父”工程中，在子工程中声明junit依赖时不指定版本，以父工程中统一设定的为准。同时也便于修改。

      4. 操作步骤

         1. 创建一个Maven工程做为父工程，注意：打包方式pom

         2. 在子工程中声明对父工程的引用

            ```properties
            <!--子工程声明父工程-->
            <parent>
            	<groupId>com.shuai.maven</groupId>
            	<artifactId>maventest</artifactId>
            	<version>0.0.1-SNAPSHOT</version>
            
            	<!--以当前文件为基准的父工程pom.xml文件的相对路径-->
            	<relativePath>../pom.xml</relativePath>
            </parent>
            ```

         3. 将子工程的坐标中与父工程坐标重复的内容删除

         4. 在父工程中统一junit的依赖

            ```properties
            <!--配置依赖的管理-->
            <dependencyManagement>
            	<dependencies>
            		<dependency>
            			<groupId>junit</groupId>
            			<artifactId>junit</artifactId>
            			<version>4.12</version>
            			<scope>test</scope>
            		</dependency>
            	</dependencies>
            </dependencyManagement>
            ```

         5. 在子工程中删除junit依赖的版本号部分

            ```properties
            <dependency>
            	<groupId>junit</groupId>
            	<artifactId>junit</artifactId>
            	<scope>test</scope>
            </dependency>
            ```

      5. 注意：配置继承后，执行安装命令时要先安装父工程

   9. ## 聚合

      1. 作用：一键安装各个模块工程

      2. 配置方式：在一个“总的聚合工程”中配置各个参与聚合的模块

         ```properties
         <!--配置聚合-->
         <modules>
         	<!--指定各个子工程的相对路径-->
         	<module>Hello</module>
         	<module>HelloFriend</module>
         	<module>MakeFriends</module>
         </modules>
         ```

      3. 使用方式：在聚合工程上运行`mvn install`

4. # 常用的Maven命令

   1. 执行与构建过程相关的Maven命令，必须进入pom.xml所在的目录
      1. 编译、测试、打包、……
   2. 常用命令
      1. mvn clean：清理
      2. mvn compile：编译主程序
      3. mvn test-compile：编译测试程序
      4. mvn test：执行测试
      5. mvn package：打包
      6. mvn install：安装
      7. mvn site：生成站点

5. # 关于联网的问题

   1. Maven的核心程序仅仅定义了抽象的生命周期，但是具体的工作必须由特定的插件来完成，而插件本身并不包含在Maven的核心程序中。

   2. 当我们执行的Maven命令需要用到某些插件时，Maven核心程序会首先到本地仓库查找

   3. 本地仓库找不到插件，会自动连接外网到中央仓库下载，如果无法连接外网，则构建失败。

   4. 修改默认本地仓库的位置

      `<localRepository>D:\work\mavenCK</localRepository>`

6. # 自动部署插件

   1. 使用命令：`mvn deploy`

   2. 插件内容

      ```properties
      <plugin>
      	<!--cargo是一家专门从事“启动Servlet容器”的组织-->
      	<groupId>org.codehaus.cargo</groupId>
      	<artifactId>cargo-maven2-plugin</artifactId>
      	<version>1.2.3</version>
      
      	<!--针对插件进行的配置-->
      	<configuration>
      		<container>
      			<containerId>tomcat7x</containerId>
      			<home>D:\work\apache-tomcat-7.0.85-8080</home>
      		</container>
      		<configuration>
      			<type>existing</type>
      			<home>D:\work\apache-tomcat-7.0.85-8080</home>
      			<!--如果Tomcat端口为默认值8080则不必设置该属性-->
      			<!--<properties>
      				<cargo.servlet.port>8989</cargo.servlet.port>
      			</properties>-->
      		</configuration>
      	</configuration>
      
      	<!--配置插件在什么情况下执行-->
      	<executions>
      		<execution>
      			<id>cargo-run</id>
      			<!--生命周期的阶段-->
      			<phase>install</phase>
      			<goals>
      				<!--插件的目标-->
      				<goal>run</goal>
      			</goals>
      		</execution>
      	</executions>
      </plugin>
      ```

   3. 插件网站

      [cargo](https://codehaus-cargo.github.io/cargo/Home.html)

7. # maven依赖信息网站

   1. [https://mvnrepository.com/](https://mvnrepository.com/)

8. # 参考教程

   1. [http://www.gulixueyuan.com/my/course/42](http://www.gulixueyuan.com/my/course/42)
