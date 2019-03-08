# Map集合#

HashMap子类

- HashMap的key不能相同，依据hashCode方法和equals方法，HashSet的底层就是HashMap
- 非线程安全
- key或value可以为null

Hashtable子类

- 线程安全
- key和value都不可以为null

TreeMap子类

- key不可以为null
- key不能相同，不依据hashCode和equals方法，依据的是实现Comparable接口并覆写comopareTo方法或新定义比较器做为TreeMap对象构造方法的参数。

Map的一些方法

```java
public class Hello {
   public static void main(String[] args) {
      Map<String, Object> map = new HashMap<>();
      map.put("1","one");
      map.put("2","two");
      map.put("3","three");
      map.put("3","four");
      map.put(null,"qwe");
      map.put(null,null);
      map.put(null,"qwerty");
      System.out.println(map);
      System.out.println(map.get("3"));
      System.out.println(map.get(null));
      System.out.println(map.containsKey("3"));
      System.out.println(map.containsValue("three"));
      Set<Map.Entry<String, Object>> entries = map.entrySet();
      for (Map.Entry<String, Object> entry : entries) {
         System.out.println(entry.getKey()+","+entry.getValue());
      }
      Set<String> set = map.keySet();
      for (String s : set) {
         System.out.println(s);
      }
      Collection<Object> values = map.values();
      for (Object value : values) {
         System.out.println(value);
      }
   }
}
```

- 根据key取value：`map.get("3")`
- 判断是否存在key：`map.containsKey("3")`
- 判断是否存在value：`map.containsValue("three")`
- 取得Map.Entry的Set集合：`Set<Map.Entry<String, Object>> entries = map.entrySet();`
  - 取得某个Map.Entry对象的key：`entry.getKey()`
  - 取得某个Map.Entry对象的value：`entry.getValue()`
- 取得key的Set集合：` Set<String> set = map.keySet();`
- 取得value集合：`Collection<Object> values = map.values();`