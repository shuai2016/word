#字节输出流，输出到文件中（写）#

OutputStream抽象类

选好文件

```java
File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt");
```

子类实例化，需要抛异常

稍后传输的数据覆盖原内容

```java
OutputStream output = new FileOutputStream(file);
```

稍后传输的数据追加在原数据之后

```java
OutputStream output = new FileOutputStream(file,true);
```

选好数据，转换为字节数组

```java
String msg = "你好\r\n世界";
byte data[] = msg.getBytes();
```

- windows系统txt文件换行用`\r\n`

输出到文件（向文件中写数据）

```java
output.write(data);
```

写入部分数据

```
output.write(data,3,8);
```

- 从字节数组data的脚标为3开始8个字节写入此输出流。

关闭输出流

```java
output.close();
```

#字节输入流，文件中数据读取到程序中（读）#

InputStream抽象类

选好文件

```java
File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt");
```

子类实例化，需要抛异常

```java
FileInputStream input = new FileInputStream(file);
```

读取数据，并将数据保存到指定字节数组中

```java
byte data[] = new byte[100];
int len = input.read(data);
```

- 字节数组的长度需要选的合适，如果从文件中读取的内容的的字节数超过了指定字节数组的长度，那么超过的部分将无法保存到指定的字节数组中，这样内容就会丢失，如果遇到正文这样一个字占多个字节的，也可能出现乱码。

三种读取数据方式

```java
byte data[] = new byte[100];
int len = input.read(data);
```

从此输入流中将最多  `data.length` 个字节的数据读入`data`数组中。在某些输入可用之前，此方法将阻塞。  

返回：读入缓冲区的字节总数，如果因为已经到达文件末尾而没有更多的数据，则返回  `-1`。  

```java
byte data[] = new byte[100];
int len = input.read(data,5,14);
```
从此输入流中将最多14个字节的数据读入`data`数组中，读入位置为data数组后偏移5个位置，即读入的开始位置是数组脚标为5的位置

返回：读入缓冲区的字节总数，如果因为已经到达文件末尾而没有更多的数据，则返回  `-1`。  

```java
byte data[] = new byte[100];
int i = 0;
int read = input.read();
while (read != -1){
   data[i++] = (byte)read;
   read = input.read();
}
```

从此输入流中读取一个数据字节。如果没有输入可用，则此方法将阻塞。  

返回：下一个数据字节；如果已到达文件末尾，则返回  `-1`。  

注意有参数的和无参数的read方法的返回值是不一样的，有参数的返回的是读取字节的个数，无参的返回的是字节的int值，如果要保存到字节数组，还需要强转为byte型。

将字节数组转换为字符串输出到控制台

```
String x = new String(data);
System.out.println(x);
```

关闭输入流

```
input.close();
```

# 字符输出流：Writer#

```java
public class Hello {
   public static void main(String[] args) throws Exception{
      File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt");
      Writer writer = new FileWriter(file);
      writer.write("你好\r\nworld");
      writer.close();
   }
}
```

write方法的参数直接写字符串即可。

必须要关闭字符输出流，不然数据只是保存在缓冲区中，并没有写入文件。

# 字符输入流：Reader#

```java
public class Hello {
   public static void main(String[] args) throws Exception {
      File file = new File("E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "123.txt");
      char data[] = new char[100];
      Reader reader = new FileReader(file);
      reader.read(data);
      String x = new String(data);
      System.out.println(x);
      reader.close();
   }
}
```

read方法是将读取的数据保存到字符数组中。