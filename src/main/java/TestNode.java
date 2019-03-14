import java.util.ArrayList;
import java.util.List;

/**
 * TestNode
 *
 * @author shuai
 * @date 2019/3/14
 */
public class TestNode {
	public static void main(String[] args) {
		ListNode node0 = new ListNode(0);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		node0.setNext(node1);
		node1.setNext(node2);
		node2.setNext(node3);
		System.out.println(node0);
		System.out.println(node3.getNext());
		ListNode reverse = reverse(node0);
		System.out.println(reverse);
	}

	public static ListNode reverse(ListNode head){
		List<ListNode> list = new ArrayList<>();
		list.add(head);
		while (head.getNext() !=null){
			list.add(head.getNext());
		}
		for (int i = list.size() -1; i >= 0 ; i--) {
			if (i!= 0){
				list.get(i).setNext(list.get(i-1));
			} else {
				list.get(i).setNext(null);
			}
		}
		return list.get(list.size()-1);
	}

}

class ListNode {
	private int val;
	private ListNode next;

	public ListNode(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public ListNode getNext() {
		return next;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "ListNode{" +
				"val=" + val +
				", next=" + next +
				'}';
	}
}
