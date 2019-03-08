# java.util.ConcurrentModificationException异常

## 1、异常产生

迭代ArrayList的时候删除（使用ArrayList的remove方法删除）某个元素，会产生java.util.ConcurrentModificationException异常

```java
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ListDemo
 *
 * @author shuai
 * @date 2018/12/6
 */
public class ListDemo {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("AA");
		list.add("BB");
		list.add("CC");
		list.add("DD");
		list.add("EE");
		list.add("FF");
		list.add("GG");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			if ("CC".equals(next)) {
				list.remove(next);
			}
		}
	}
}
```

1. jdk：1.8
2. ArrayList

## 2、分析源码

### 1、Itr类

ArrayList对象调用iterator方法，返回的是一个Itr对象，这个对象实现Iterator接口，Itr是ArrayList的内部类（父类AbstractList中也有这个内部类）

```java
public Iterator<E> iterator() {
    return new Itr();
}
```

Itr类的几个方法（hasNext，next）

```java
private class Itr implements Iterator<E> {
    //cursor默认为0
    int cursor;       // index of next element to return
    //lastRet默认为-1
    int lastRet = -1; // index of last element returned; -1 if no such
    //protected transient int modCount = 0;codCount是父类AbstractList的属性，默认为0
    //expectedModCount默认为0
    int expectedModCount = modCount;

    public boolean hasNext() {
        //如果cursor不等于列表的长度，返回true
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        checkForComodification();
        //把cursor的值给i，cursor初始值为0
        int i = cursor;
        //如果cursor大于等于列表长度，抛出异常
        if (i >= size)
            throw new NoSuchElementException();
        //集合的元素组装成的数组
        Object[] elementData = ArrayList.this.elementData;
        //如果cursor大于等于数组的长度（列表长度较小时，数组长度为10），抛出异常
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        //cursor自增1；
        cursor = i + 1;
        //lastRet等于i（自增1之前的cursor）
        //返回序号为i的元素。
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    final void checkForComodification() {
        //如果expectedModCount不等于expectedModCount，抛出异常
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

### 3、Itr类next方法对属性的修改

1. cursor增加1
2. lastRet增加1（比cursor小1）

### 4、Itr类调用next方法时抛出ConcurrentModificationException异常的情况

从Itr类的源码可以看出调用next方法产生ConcurrentModificationException异常有以下两点

1. modCount不等于expectedModCount
   1. Iterator的next()方法并没有涉及改变这俩个值
2. cursor大于等于数组长度

### 5、ArrayList的remove方法

ArrayList的remove方法本身是重载的方法，这里只看参数为对象的方法

```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}

private void fastRemove(int index) {
    //modCount自增1
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        //内部数组结构变化
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    //size自减1
    elementData[--size] = null; // clear to let GC do its work
}
```

### 6、ArrayList的remove方法对属性的修改

1. modCount增加1
   1. ArrayList的remove方法调用了fastRemove方法，fastRemove方法使modCount加1

## 3、产生ConcurrentModificationException异常的原因

	

1. ArrayList的remove方法本身并不抛出ConcurrentModificationException异常，但是修改了modCount的值，所以下次再调用Itr类的next方法的时候，导致modCount不等于expectedModCount，所以就抛出ConcurrentModificationException异常了。

## 4、解决方法

## 5、参考资料

https://www.cnblogs.com/dolphin0520/p/3933551.html