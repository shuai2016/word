1. Java JUC简介

   1. Java5.0提供了java.util.concurrent包
   2. 多线程的目的
      1. 提高效率，尽可能利用系统资源
      2. 如果使用不当，会降低性能
         1. 开销比单线程大
            1. 线程之间的调度
            2. cpu上下文的切换
            3. 线程创建和销毁，同步

2. volatile 关键字-内存可见性

   1. 内存可见性
      1. 什么是内存可见性
         1. JVM会为每一个执行任务的线程分配独立的缓存用来提高效率
      2. 内存可见性问题
         1. 当多个线程操作共享数据时，彼此不可见
      3. 解决
         1. 使用synchronized，每次去主存中读取数据
            1. 效率低
         2. 使用volatile关键字
            1. 当多线程操作共享数据时，可以保证内存中的数据可见
            2. 计算机底层代码，内存栅栏，时时刻刻将缓存的数据刷新到主存
            3. 可以理解为都是在主存中操作，效率也会有所下降（JVM底层优化重排序，使用volatile不能进行重排序）
   2. 与synchronized的区别
      1. 相较于synchronized，volatile是一种较为轻量级的同步策略
      2. volatile不具备”互斥性“
      3. 不能保证变量的“原子性”
         1. 原子性（atomic）：变量不可分割

3. 原子变量-CAS算法

   1. i++的原子性（atomic）问题

      ```java
      int i = 10;
      i = i++; //10
      //计算步骤
      int temp = i;
      i = i + 1;
      i = temp;
      ```

      1. 实际上分为三个步骤：“读-改-写”，不能保证原子性

   2. 原子变量

      1. jdk1.5后，java.util.concurrent.atomic包下提供常用的原子变量。

      2. 性质

         1. volatile，保证内存可见性

         2. CAS（Compare-And-Swap） 算法报纸数据原子性

            1. CAS 算法是硬件对于并发操作共享数据的支持

            2. CAS 包含三个操作数

               ```java
               内存值 V
               预估值 A
               更新值 B
               当且仅当 V == A 时，V = B，否则，将不做任何操作
               ```

            3. 效率

               1. 无锁非阻塞算法
               2. 比同步锁效率高

4. ConcurrentHashMap锁分段机制

   1. java5增加的线程安全的哈希表，介于HashMap和Hashtable之间

   2. 与Hashtable的区别

      1. ConcurrentHashMap
         1. 内部采用“分段锁”，可以理解为“并行”
            1. 并发级别，concurrentLevel，默认16
      2. Hashtable
         1. “独占锁”，锁整张表，“串行”

      ```java
      Hashtable复合操作可能会导致线程不安全
      
      若不存在则添加
      若存在则删除
      
      if(!table.contants()){
      	table.put();
      }
      ```

   3. jdk1.8底层算法改为 CAS（理解为无锁算法，不会阻塞，不涉及上下文切换问题）

   4. 其它用于多线程上下文中的Collection实现

      1. ConcurrentHashMap，多线程访问时，通常优于同步的HashMap
      2. ConcurrentSkipListMap，多线程访问时，通常优于同步的TreeMap
      3. ConcurrentSkipListSet
      4. CopyOnWriteArrayList，当读数和遍历远远大于列表的更新时，CopyOnWriteArrayList优于同步的ArrayList
      5. CopyOnWriteArraySet

5. CountDownLatch闭锁

   1. 概念

      1. 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待
      2. 闭锁，在完成某些运算时，只有其他所有的线程全部完成，当前运算才继续执行。

   2. 具体操作

      ```java
      final CountDownLatch latch = new CountDownLatch(10);
      
      //等待，直到维护的数为0时，放行
      latch.await();
      
      //维护的数减1
      latch.countDown();
      ```

6. 实现Callable接口

   1. 与实现Runnable接口的区别

      1. 有返回值
      2. 可以抛出异常

   2. 具体操作

      ```java
      ThreadDemo1 td = new ThreadDemo1();
      //1、执行Callable方式，需要FutureTask 实现类的支持，用于接收运算结果
      FutureTask<Integer> result = new FutureTask<>(td);
      new Thread(result).start();
      //2、接收线程运算后的结果
      try {
          Integer sum = result.get();  //FutureTask 可用于闭锁
          System.out.println(sum);
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
      
      
      class ThreadDemo1 implements Callable<Integer>{
      	@Override
      	public Integer call() throws Exception {
      		int sum = 0;
      		for (int i = 0; i <= 100; i++) {
      			sum += i;
      		}
      		return sum;
      	}
      }
      ```

      1. 线程运行结束才会计算结果，所以FutureTask 可用于闭锁

7. Lock同步锁

   1. 用于解决多线程安全问题的方式
      1. synchronized（jdk1.5之前），隐式锁，JVM底层支持的关键字，JVM自己维护
         1. 同步代码块
         2. 同步方法
      2. Lock（jdk1.5之后），显式锁，更加灵活
         1. 通过lock()方法上锁
         2. 通过unlock()方法进行释放（通常写在finally里）

8. 虚假唤醒

   1. 为了避免虚假唤醒问题，wait应该总是使用在循环中

9. Condition控制线程通信

   1. 配合Lock使用，单个Lock可能与多个Condition对象关联，Condition实例被绑定到一个锁上。

      ```java
      private Condition condition = lock.newCondition();
      
      condition.await();
      
      condition.signalAll();
      ```

   2. 与wait、notify、notifyAll方法对应的分别是await、signal和signalAll

10. 线程按序交替

11. ReadWriteLock读写锁

    1. 维护一对锁（read lock，write lock）

    2. 读锁可以并发持有，写锁独占

       ```java
       private ReadWriteLock lock = new ReentrantReadWriteLock();
       
       lock.readLock().lock();//上锁
       
       finally {
           lock.readLock().unlock();
       }
       
       lock.writeLock().lock();
       
       finally {
           lock.writeLock().unlock();
       }
       ```

12. 线程八锁

    1. 非静态方法的锁默认为 this，静态方法的锁为 对应的Class 实例
    2. 某一个时刻内，只能有一个线程持有锁，无论几个方法。

13. 线程池

    1. 作用

       1. 提供了一个线程队列，队列中保存和所有等待状态的线程。避免了创建与销毁线程的额外开销，提高了响应的速度。

    2. 体系结构

       1. java.util.concurrent.Executor：负责线程的使用与调度的根接口
          1. ExecutorService 子接口：线程池的主要接口
             1. ThreadPoolExecutor 实现类
             2. ScheduledExecutorService 子接口：负责线程的调度
                1. ScheduleThreadPoolExecutor：继承了ThreadPoolExecutor，实现了ScheduledExecutorService

    3. 工具类Executors

       1. ExecutorService newFixedThreadPool()：创建固定大小的线程池

       2. ExecutorService newCachedThreadPool()：缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量

       3. ExecutorService newSingleThreadExecutor()：创建单个线程池。线程池中只有一个线程

       4. ScheduledExecutorService newScheduledThreadPool()：创建固定大小的线程，可以延迟或定时的执行任务。

          ```java
          List<Future<Integer>> list = new ArrayList<>();
          //1、创建线程池
          ExecutorService pool = Executors.newFixedThreadPool(5);
          //2、为线程池中的线程分配任务
          for (int i = 0; i < 10; i++) {
             Future<Integer> funture = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                   int sum = 0;
                   for (int i = 0; i <= 100; i++) {
                      sum += i;
                   }
                   return sum;
                }
             });
             list.add(funture);
          }
          //3、关闭线程池
          pool.shutdown();
          for (Future<Integer> future : list) {
             System.out.println(future.get());
          }
          ```

14. 线程调度

    ```java
    ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
    for (int i = 0; i < 10; i++) {
       Future<Integer> funture = pool.schedule(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
             int num = new Random().nextInt(100);//生成随机数
             System.out.println(Thread.currentThread().getName() + " : " + num);
             return num;
          }
       },1,TimeUnit.SECONDS);
       System.out.println(funture.get());
    }
    pool.shutdown();
    ```

15. ForkJoinPool分支、合并框架 工作窃取

    1. jdk1.7以后