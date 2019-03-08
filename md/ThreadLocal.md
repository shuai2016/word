ThreadLocal

个人认为：使用ThreadLocal可以使某一变量，在不同的线程中有不同的对象，互不干扰。

```java
public class Hello {
    public static void main(String[] args) {
        new Thread(new MyRun()).start();
        new Thread(new MyRun1()).start();
        new Thread(new MyRun2()).start();
    }
}

class MyThreadLocal {
    private static ThreadLocal<A> threadLocal = new ThreadLocal();

    public static void set(A a) {
        threadLocal.set(a);
    }

    public static A get() {
        return threadLocal.get();
    }
}

class MyRun implements Runnable {

    @Override
    public void run() {
        A a = new A();
        a.setNumber(12);
        a.setTitle("hello");
        MyThreadLocal.set(a);
        this.getA();
    }

    private void getA() {
        System.out.println(Thread.currentThread().getName() + "-get");
        System.out.println(MyThreadLocal.get());
    }
}

class MyRun1 implements Runnable {

    @Override
    public void run() {
        A a = new A();
        a.setNumber(24);
        a.setTitle("world");
        MyThreadLocal.set(a);
        this.getA();

    }

    private void getA() {
        System.out.println(Thread.currentThread().getName() + "-get");
        System.out.println(MyThreadLocal.get());
    }
}

class MyRun2 implements Runnable {

    @Override
    public void run() {
        this.getA();

    }

    private void getA() {
        System.out.println(Thread.currentThread().getName() + "-get");
        System.out.println(MyThreadLocal.get());
    }
}

class A {
    private String title;
    private Integer number;

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

