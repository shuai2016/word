import java.util.*;

/**
 * MyLinkedHashMap
 *
 * @author shuai
 * @date 2019/3/20
 */
public class MyLinkedHashMap {
	public static void main(String[] args) {
		//HashMap<Integer, String> map = new LinkedHashMap<>();
		HashMap<Integer, String> map = new HashMap<>();

		map.put(11,"a");
		map.put(12,"s");
		map.put(13,"d");
		map.put(14,"f");
		map.put(15,"g");
		map.put(16,"h");
		map.put(17,"j");
		map.put(18,"k");
		map.put(19,"l");
		map.put(20,"z");
		map.put(21,"x");
		map.put(22,"c");
		map.put(23,"v");
		map.put(24,"b");
		map.put(25,"n");
		map.put(26,"m");
		map.put(1,"q");
		map.put(2,"w");
		map.put(3,"e");
		map.put(4,"r");
		map.put(5,"t");
		map.put(6,"y");
		map.put(7,"u");
		map.put(8,"i");
		map.put(9,"o");
		map.put(10,"p");

		Map<Integer,Integer> codeMap = new HashMap<>();


		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer i = codeMap.get(entry.hashCode());
			if(i != null){
				codeMap.put(entry.hashCode(),i+1);
			} else {
				codeMap.put(entry.hashCode(),1);
			}
			System.out.print(entry.hashCode()+"、");
		}
		System.out.println();
		for (Map.Entry<Integer, Integer> entry : codeMap.entrySet()) {
			System.out.println(entry.getKey()+"->"+entry.getValue());
		}
		System.out.println();

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			System.out.print(entry.getKey()+"、");
		}

	}
}
