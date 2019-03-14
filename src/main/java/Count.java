import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Count
 *
 * @author shuai
 * @date 2019/3/14
 */
public class Count {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		long num = 100000000L;
		long max = 10 * num;
		long sum = 0L;
		long start = 0L;
		long end = 0L;
		start = System.currentTimeMillis();
		for (long i = 1; i <= max; i++) {
			sum = sum + i;
		}
		end = System.currentTimeMillis();
		System.out.println(sum + "," + (end - start));
		long sum1 = 0L;
		List<FutureTask<Long>> list = new ArrayList<>();
		start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			FutureTask<Long> task = new FutureTask<>(new MyCall(num * i + 1, num * (i + 1)));
			new Thread(task).start();
			list.add(task);
		}

		for (FutureTask<Long> task : list) {
			sum1 = sum1 + task.get();
		}
		end = System.currentTimeMillis();
		System.out.println(sum1 + "," + (end - start));

		CountDownLatch countDownLatch = new CountDownLatch(10);
		long[] array = new long[10];
		start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			new Thread(new myRun(num * i + 1, num * (i + 1), array,i, countDownLatch)).start();
		}
		long sum2 = 0L;
		countDownLatch.await();
		for (Long aLong : array) {
			sum2 = sum2 + aLong;
		}
		end = System.currentTimeMillis();
		System.out.println(sum2 + "," + (end - start));

	}
}

class MyCall implements Callable<Long> {
	private Long start;
	private Long end;

	public MyCall(Long start, Long end) {
		this.start = start;
		this.end = end;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	@Override
	public Long call() throws Exception {
		long count = 0L;
		for (long i = start; i <= end; i++) {
			count = count + i;
		}
		return count;
	}
}

class myRun implements Runnable {
	private Long start;
	private Long end;
	private long[] array;
	private int index;
	private CountDownLatch latch;

	public myRun(Long start, Long end, long[] array, int index, CountDownLatch latch) {
		this.start = start;
		this.end = end;
		this.array = array;
		this.index = index;
		this.latch = latch;
	}

	@Override
	public void run() {
		long sum = 0L;
		try {
			for (long i = start; i <= end; i++) {
				sum = sum + i;
			}
			array[index] = sum;
		} finally {
			latch.countDown();
		}
	}
}
