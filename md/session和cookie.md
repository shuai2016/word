### session###

- 获取session，没有就创建

```java
HttpSession session = request.getSession;
```

- 获得session ID

```java
session.getId();
```

- 获取属性的值

```java
session.getAttribute("list");
```

- 设置属性的值

```java
session.setAttribute("list",list);
```

- 获取所有属性名称

```java
Enumeration<?> enumeration = session.getAttributeNames();
```

- 是否存在

```java
session.isNew();
```

### cookie###

- 设置cookie

```java
Cookie cookie = new Cookie("asd", sessionId);
response.addCookie(cookie);
```

- 获取所有的cookie

```java
Cookie[] cookies = request.getCookies();
for (Cookie cookie : cookies) {
    System.out.println(cookie.getName()+":"+cookie.getValue());
}
```

- 浏览器禁用cookie需要的两个方法

```java
String url = request.getContextPath()+"/buyBook?id=" + book.getId();
url = response.encodeURL(url);//将超链接的url地址进行重写
```

```java
String url = request.getContextPath()+"/total";
url = response.encodeRedirectURL(url);
```

### 输出###

```java
PrintWriter out = response.getWriter();
out.write("hello world");
```





