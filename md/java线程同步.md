java线程同步

```java
public class Hello {
	public static void main(String[] args) {
		MyRun myRun0 = new MyRun();
		new Thread(myRun0, "Thread0").start();
		new Thread(myRun0, "Thread1").start();
		new Thread(myRun0, "Thread2").start();
	}
}

class MyRun implements Runnable {
	private int k = 0;

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + "**********" + i);
			k++;
			if (k <= 3) {
				if ("Thread0".equals(Thread.currentThread().getName())) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + "," + k);
			}
		}
	}
}
```

输出结果

```text
Thread0**********0
Thread1**********0
Thread2**********0
Thread1,2
Thread2,3
Thread1**********1
Thread2**********1
Thread2**********2
Thread1**********2
Thread0,7
Thread0**********1
Thread0**********2
```

说明多线程在某些场景是存在问题的，有时候需要线程同步。

同步 synchronized

同步代码块，`synchronized(obj){}`，obj是一个对象，在这里就相当于一把锁，表示一旦有进程抢到了这把锁的钥匙（就是进入了代码块），其他进程将无法进入该锁的代码块（当前代码块其他进程一定是进不来了，其他地方的代码块如果也是用了这把锁，同样进不去），只有代码块执行完，释放锁后，所有进程再重新抢钥匙。

注意，上同一把锁的代码块都会被锁住，这些代码块可能写在不同方法不同位置上。

被同步代码块包住的代码多个线程只能顺次进入。

```java
synchronized (this) {
	k++;
	if (k <= 3) {
		if ("Thread0".equals(Thread.currentThread().getName())) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "," + k);
	}
}
```

this表示当前对象，这里考虑的只是运行这个方法，不涉及其它类也不涉及这个类的其它地方需要同步问题，所以用this也是可以的。k增加和输出一个流程内只能有一个线程在访问，所以可以得到想要的输出结果

输出结果

```text
Thread0**********0
Thread1**********0
Thread2**********0
Thread0,1
Thread0**********1
Thread2,2
Thread2**********1
Thread1,3
Thread1**********1
Thread0**********2
Thread2**********2
Thread1**********2
```

对方法进行同步，如果存在多线程，每个线程顺次访问该方法

注意，如果一个类里面存在多个同步方法，那么这些同步方法的锁是一个，都是当前对象，所以不同线程想同时访问同一对象的不同方法也是不行的，因为这些方法都上了同一把锁，但是钥匙只有一把，只能一个线程持有。

```java
@Override
public synchronized void run() {
	for (int i = 0; i < 3; i++) {
		System.out.println(Thread.currentThread().getName() + "**********" + i);
		k++;
		if (k <= 3) {
			if ("Thread0".equals(Thread.currentThread().getName())) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "," + k);
		}
	}
}
```

输出结果

```text
Thread0**********0
Thread0,1
Thread0**********1
Thread0,2
Thread0**********2
Thread0,3
Thread2**********0
Thread2**********1
Thread2**********2
Thread1**********0
Thread1**********1
Thread1**********2
```

死锁

```java
public class Hello {
    public static void main(String[] args) {
	    A a = new A();
	    B b = new B();
	    new Thread(new MyRun(a,b)).start();
	    new Thread(new MyRun1(a,b)).start();
    }
}
class MyRun implements Runnable{
	private A a;
	private B b;

	public MyRun(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
    public void run(){
    	a.say(b);
    }
}
class MyRun1 implements Runnable {
	private A a;
	private B b;

	public MyRun1(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		b.say(a);
	}
}
class A{
	public synchronized void say(B b){
		System.out.println("A要知道B的信息");
		b.info();
	}
	public synchronized void info(){
		System.out.println("这是A");
	}
}
class B{
	public synchronized void say(A a){
		System.out.println("B要知道A的信息");
		a.info();
	}
	public synchronized void info(){
		System.out.println("这是B");
	}
}
```



如果两个线程同时进入了两个say方法，就是出现死锁。

关键点在于一个对象的多个同步方法具有相同的锁，都是当前对象。也就是x线程在访问a对象的say方法过程中，y线程是无法访问a对象的info方法的，因为开锁的钥匙已经被x线程抢占了。

上面的程序，如果线程x，y同时进入了两个say方法，a对象同步方法的锁被线程x抢占，b对象同步方法的锁被线程y抢占，此时线程x无法访问b对象的同步方法，线程y无法访问a对象的同步方法。代码中恰好想要访问，所以就出现死锁了。

