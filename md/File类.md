File类

构造方法

可以是一个目录，也可以是一个文件，就看后面的方法如何对待了。

```java
File file = new File("E:"+File.separator+"myFile");
```

常量

与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。 

```java
File.separator
```

普通方法

测试此抽象路径名表示的文件或目录是否存在。 

```java
boolean exists = file.exists();
```

测试此抽象路径名表示的文件是否是一个目录。 

```java
boolean directory = file.isDirectory();
```

测试此抽象路径名表示的文件是否是一个标准文件。 

```java
boolean file1 = file.isFile();
```

当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。 

此方法需要try-catch

只有目录存在的情况下才会创建文件，即除了最后一级的文件，之前的路径是存在的才行，即存在父路径

如果文件存在则不会覆盖

```java
file.createNewFile();
```

创建此抽象路径名指定的目录。

如果只是最后一级目录不存在使用mkdir即可，多级目录不存在使用mkdirs

```java
file.mkdir();
```

创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。 

```java
file.mkdirs();
```

删除此抽象路径名表示的文件或目录。 

文件或目录存在，删除最后一级的目录或文件

如果是目录，但目录里面有内容也无法删除

```java
file.delete();
```