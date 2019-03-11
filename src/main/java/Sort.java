import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sort
 *
 * @author shuai
 * @date 2019/3/11
 */
public class Sort {
	public static void main(String[] args) {
		String str = "rwe4198wf11we3tf1ewq3w4f89we1we6f1we3g1w3g6weg1";
		char[] chars = str.toCharArray();
		List<Object> list = new ArrayList<>();
		for (char aChar : chars) {
			if ('0' <= aChar && aChar <= '9') {
				list.add(aChar);
			}
		}
		int[] arr = new int[list.size()];
		int k = 0;
		for (Object o : list) {
			arr[k] = Integer.parseInt(String.valueOf(o));
			k++;
		}
		for (int i : arr) {
			System.out.print(i + "、");
		}
		Arrays.sort(arr);
		System.out.println();
		for (int i : arr) {
			System.out.print(i + "、");
		}

	}
}
