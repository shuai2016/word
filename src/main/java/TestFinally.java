import java.util.HashMap;
import java.util.Map;

/**
 * TestFinally
 *
 * @author shuai
 * @date 2019/3/12
 */
public class TestFinally {
	public static void main(String[] args) {

		String s = "123";
		String s1 = "123";
		System.out.println(s == s1);
		int i = get(2, 3);
		System.out.println(i);
		mapLength();

		String str1 = "a" + "b" + "c";
		String str2 = "a" + "b";
		str2 = str2 + "c";
		System.out.println(str1);
		System.out.println(str2);

		System.out.println("abc" + null);
		System.out.println(get());
		System.out.println(getA(new A(0)));
	}

	public static int get() {
		int i = 0;
		try {
			int j = i / 0;
			return i = i + 1;
		} catch (Exception e) {
			return i = i + 2;
		} finally {
			 i = i + 4;
			System.out.println(i);
		}
	}

	public static A getA(A a) {
		int i = 0;
		try {
			int j = i / 0;
			a.setI(1);
			return a;
		} catch (Exception e) {
			a.setI(2);
			return a;
		} finally {
			a.setI(3);
		}
	}

	public static int get(int x, int y) {
		try {
			return x + y;
		} finally {
			return x - y;
		}
	}

	public static void mapLength() {
		Map<A, Object> map = new HashMap<>();
		map.put(new A(1), "123");
		map.put(new A(1), "456");
		int size = map.entrySet().size();
		System.out.println(size);

		String str1 = new String("a");
		String str2 = "a";

		Map<String, Object> map1 = new HashMap<>();
		map1.put(str1, "123");
		map1.put(str2, "456");
		int size1 = map1.entrySet().size();
		System.out.println(size1);
	}
}

class A {

	private int i;

	public A(int i) {
		this.i = i;
		System.out.println("A:"+i);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	@Override
	public String toString() {
		return "A{" +
				"i=" + i +
				'}';
	}
}
