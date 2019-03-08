#List接口#

ArrayList

- 针对数组的包装
- 非线程安全，性能更高

LinkedList

- 针对链表的包装

Vector

- 线程安全，性能相对较低

如果List的保存的是自定义的java类，那么使用list的包含`contains`、移除`remove`等方法时，需要给自定义java类覆写equals方法，自定义判断两对象相同的规则。可以开发工具自动生成。

```java
public class Hello {
   public static void main(String[] args) {
      List<A> list = new ArrayList<>();
      list.add(new A("hello", 123));
      list.add(new A("world", 234));
      list.add(new A("hi", 345));
      System.out.println(list.contains(new A("world", 123)));
      list.remove(new A("123", 123));
      System.out.println(list);
   }
}

class A {
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
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      A a = (A) o;
      return Objects.equals(number, a.number);
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

remove方法只是移除第一次出现的那个认为与参数相同的对象，不是所有都移除。

# Set接口#

HashSet

HashSet不存重复数据，如果是自定义java类，应该覆写hashCode和equals两个方法，先判断hashCode再判断equals，自定义规则。开发工具自动生成

```java
@Override
public int hashCode() {
   return Objects.hash(number);
}
```

测试

```java
public class Hello {
   public static void main(String[] args) {
      Set<A> set = new HashSet<>();
      set.add(new A("hello", 123));
      set.add(new A("world", 234));
      set.add(new A("hi", 345));
      System.out.println(set.contains(new A("world", 123)));
      set.remove(new A("123", 123));
      System.out.println(set);
   }
}
```

TreeSet

TreeSet也不存重复数据并且可以排序，判断对象是否相同以及排序依靠的是比较器比较

通常是使TreeSet保存的对象所在的java类实现`implements Comparable<A>`接口并覆写`compareTo(A o)`方法，规则自定义，compareTo方法不但可以判断是否相同，还可以排序。TreeSet不依靠hashCode和equals方法。使用TreeSet保存的java类需要实现Comparable接口，不然运行时报错。

```java
@Override
public int compareTo(A o) {
   return new CompareToBuilder().append(this.number,o.number).toComparison();
}
```

还有挽救的方法，如果java类没有实现comoparable接口，可以另外自定义一个该类对象的比较器，将比较器的对象做为TreeSet构造方法的参数。

```java
class Acomparator implements Comparator<A>{
    @Override
    public int compare(A a1, A a2) {
        return  new CompareToBuilder().append(a1.getNumber(),a2.getNumber()).toComparison();
    }
}
```

此时创建TreeSet对象使用有参构造

```java
Set<A> set = new TreeSet<>(new Acomparator());
```

这里的方法用的是`org.apache.commons.lang3.builder.CompareToBuilder;`类下的方法

pom引用

```pom
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.4</version>
</dependency>
```

测试

```java
public class Hello {
   public static void main(String[] args) {
      Set<A> set = new TreeSet<>();
      set.add(new A("hello", 123));
      set.add(new A("world", 234));
      set.add(new A("hi", 345));
      set.add(new A("hi", 321));
      set.add(new A("hi", 100));
      System.out.println(set.contains(new A("world", 123)));
      set.remove(new A("123", 123));
      System.out.println(set);
   }
}
```

# 迭代输出#

```java
public class Hello {
   public static void main(String[] args) {
      List<A> list = new ArrayList<>();
      list.add(new A("hello", 123));
      list.add(new A("world", 234));
      list.add(new A("hi", 345));
      for (A a : list) {
         System.out.println(a);
      }
      Iterator<A> iterator = list.iterator();
      while (iterator.hasNext()){
         System.out.println(iterator.next());
      }
      ListIterator<A> listIterator = list.listIterator();
      while (listIterator.hasNext()){
         System.out.println(listIterator.next());
      }
      while (listIterator.hasPrevious()){
         System.out.println(listIterator.previous());
      }
   }
}
```

Vector类的特殊迭代

```
public class Hello {
   public static void main(String[] args) {
      Vector<A> vector = new Vector<>();
      vector.add(new A("hello", 123));
      vector.add(new A("world", 234));
      vector.add(new A("hi", 345));
      Enumeration<A> elements = vector.elements();
      while (elements.hasMoreElements()){
         System.out.println(elements.nextElement());
      }
   }
}
```

- foreach通用
- iterator通用
- listIterator（双向迭代，List接口）
- elements（Vector类）