### iso-8859-1

**iso-8859-1编码不包含汉字**：iso-8859-1编码采用单字节编码（8位），可存储的字符有限，并没有收录中文字符。在java中，使用iso-8859-1编码时，会将未收录的字符指向63号编码（==iso-8859-1编码中?对应的编码==）。所以，==如果中文字符使用iso-8859-1编码时，字符都会被指向63号编码。转回字符时，都会变成?字符==

```java
String str = "联通";
byte[] bytes = str.getBytes("iso-8859-1");
for (byte aByte : bytes) {
    System.out.print(aByte + "、");
}
// 控制台打印：63、63、
```

**iso-8859-1编码的重要特性**：单字节编码。==传输和存储其他任何编码的字节流都不会被抛弃==，比如一个汉字，GBK编码方式会用两个字节表示一个汉字，此时用iso-8859-1编码依然可以找到对应的字符，虽然可能不是我们想要的，但是至少不会出现编码变化。

```java
String str = "联通";
byte[] bytes = str.getBytes("GBK");
for (byte aByte : bytes) {
    System.out.print(aByte + "、");
}
//控制台打印：-63、-86、-51、-88、
System.out.println();
//使用字节数组
byte[] b = new byte[]{-63,-86,-51,-88};
//使用iso-8859-1编码
String s = new String(b,"iso-8859-1");
System.out.println(s);
//控制台打印：ÁªÍ¨
byte[] bytes1 = s.getBytes("iso-8859-1");
for (byte b1 : bytes1) {
    System.out.print(b1 + "、");
}
//控制台打印：-63、-86、-51、-88、
//虽然中间的字符不认识，但是并不会改变编码
System.out.println();
```

gbk或utf-8等编码，一个字符可能需要多个字节，字节之间存在关联。错误的编码组合可能无法对应字符，会被替换为其它的编码，导致编码变化。

```java
String str = "联通";
byte[] bytes = str.getBytes("GBK");
for (byte aByte : bytes) {
    System.out.print(aByte + "、");
}
//控制台打印：-63、-86、-51、-88、
System.out.println();
//使用新的字节数组（-63变为60）
byte[] b = new byte[]{60,-86,-51,-68};
String s = new String(b,"GBK");
System.out.println(s);
//控制台打印：<�
byte[] bytes1 = s.getBytes("GBK");
for (byte b1 : bytes1) {
    System.out.print(b1 + "、");
}
//控制台打印：60、-86、-51、63、
//-88变为了63
System.out.println();
```

