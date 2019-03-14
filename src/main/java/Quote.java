import java.util.ArrayList;
import java.util.List;

/**
 * Quote
 *
 * @author shuai
 * @date 2019/3/14
 */
public class Quote {
	public static void main(String[] args) {
		Integer i = new Integer(1);
		get(i);
		System.out.println(i);
		int array[] = {1};
		get(array);
		System.out.println(array[0]);
		List<Integer> list = new ArrayList<>();
		list.add(1);
		get(list);
		System.out.println(list.get(0));
	}
	private static void get(Integer i){
		i = 10;
	}
	private static void get(int[] array){
		array[0] = 10;
	}
	private static void get(List<Integer> list){
		//list.set(0,10);
		list = new ArrayList<>();
		list.add(10);
	}
}
