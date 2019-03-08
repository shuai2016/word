

#打印流#

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt");
      OutputStream outputStream = new FileOutputStream(file);
      PrintStream printStream = new PrintStream(outputStream);
      printStream.print(123);
      printStream.println("hello");
      printStream.println(12.5);
      printStream.close();
   }
}
```

#键盘输入读取到程序中#

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      InputStream in = System.in;
      byte[] data = new byte[100];
      System.out.println("输入数据：");
      int read = in.read(data);
      System.out.println(read);
      System.out.println(new String(data,0,read));
   }
}
```
#扫码流#

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      Scanner scanner = new Scanner(new FileInputStream(new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt")));
      scanner.useDelimiter("\n");
      while (scanner.hasNext()){
         String next = scanner.next();
         System.out.println(next);
      }
      scanner.close();
   }
}
```

`scanner.useDelimiter("\n");`表示以回车（换行）为定界符，回车间为一段扫码的内容。

扫描键盘输入

```java
Scanner scanner = new Scanner(System.in);
```

注意：使用while判断键盘输入，程序可能会无法结束

# 对象序列化#

序列化操作类：ObjectOutputStream，写到文件中

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      A a = new A("hello", 123);
      File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "a.ser");
      OutputStream outputStream = new FileOutputStream(file);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(a);
      objectOutputStream.close();
   }
}

class A implements Serializable {
   private String title;
   private Integer number;

   public A(String title, Integer number) {
      this.title = title;
      this.number = number;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Integer getNumber() {
      return number;
   }

   public void setNumber(Integer number) {
      this.number = number;
   }

   @Override
   public String toString() {
      return "A{" +
            "title='" + title + '\'' +
            ", number=" + number +
            '}';
   }
}
```

实体需要实现可序列化的接口`implements Serializable`，表示一种能力

反序列化操作类：ObjectInputStream，读到程序里

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "a.ser");
      InputStream inputStream = new FileInputStream(file);
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      A a = (A) objectInputStream.readObject();
      System.out.println(a);
   }
}
```

`transient`关键字，实体的属性使用该关键子，进行序列化时该属性值将不会被保存，反序列化的结果为，该属性的值为该属性类型的默认值。

```java
private String title;
private transient Integer number;
```