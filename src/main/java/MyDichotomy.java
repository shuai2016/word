/**
 * MyDichotomy
 *
 * @author shuai
 * @date 2019/3/25
 */
public class MyDichotomy {
    public static void main(String[] args) {
        int[] arr = new int[]{12, 23, 34, 45, 56, 67, 77, 89, 90};
        System.out.println(search(arr, 12));
        System.out.println(search(arr, 45));
        System.out.println(search(arr, 67));
        System.out.println(search(arr, 89));
        System.out.println(search(arr, 99));
    }

    public static int search(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (arr[middle] < key) {
                start = middle + 1;
            } else if (arr[middle] > key) {
                end = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}
