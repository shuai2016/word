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
		ListNode reverse0 = reverse0(node0);
		System.out.println(reverse0);
		ListNode reverse1 = reverse1(reverse0);
		System.out.println(reverse1);
	}

	public static ListNode reverse0(ListNode node){
		ListNode head = node;
		ListNode prev = null;
		while (head !=null){
			ListNode next = head.getNext();
			head.setNext(prev);
			prev = head;
			head = next;
		}
		return prev;
	}
	public static ListNode reverse1(ListNode head){
		if(head == null || head.getNext() == null){
			return head;
		}
		ListNode p  = reverse1(head.getNext());
		head.getNext().setNext(head);
		head.setNext(null);
		return p;

	}

	public static ListNode reverse(ListNode head){
		List<ListNode> list = new ArrayList<>();
		list.add(head);
		ListNode next = head.getNext();
		while (next !=null){
			list.add(next);
			next = next.getNext();
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
