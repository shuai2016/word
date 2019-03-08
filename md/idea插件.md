- codeglance（代码缩略图）

- jaypeevoss的Sudoku Plugin

- daddy-bear的Nyan progress bar（漂亮进度条）

- BackgroundImage （背景图片）

  _ctrl+shift+a_

- Background Image Plus

- Free Mybatis plugin（mapper和xml跳转）

- Translation（翻译）

- jrebel（热部署）

- activate-power-mode（特效，冒泡）

- Power mode II（加强上面）

- Grep console（自定义日志颜色）

- MyBatis Log Plugin（格式化mybatis的输出脚本）

  _tool菜单栏_

- String Manipulation（字符串转换）

- Lombok（自动生成setter，getter）（需要添加依赖）

- Key promoter（快捷键提示）

- Gsonformat（根据json快速生成java实体类）

  _alt+insert,alt+s_

- RestfulToolkit（找到controller）

  _ctrl+\\_

---

2018/6/4

- 创建了生成注释

  File-->settings-->Editor-->File and Code Templates-->Files -->Class

  ```java
  /**
   *@ClassName ${NAME}
   *@Description TODO
   *@Author ${USER}
   *@Date ${DATE} ${TIME}
   *@Version 1.0
  **/
  ```

- Lombok

  ```java
  <dependency>  
            <groupId>org.projectlombok</groupId>  
            <artifactId>lombok</artifactId>   
  </dependency>
  
  @Setter
  @Getter
  @ToString
  @EqualsAndHashCode
  public class People {
      private String name;
      private int age;
      private String male;
  }
  ```

