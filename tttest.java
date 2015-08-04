import java.util.ArrayList;
import java.util.HashMap;


public class tttest {
	public static void main(String[] args){
		HashMap<Integer,aaa> a = new HashMap();
		aaa b = new aaa();
		a.put(1, b);
		aaa c = a.get(1);
		c.a = 2;
		aaa d = a.get(1);
		System.out.println(c.a+"    "+d.a);
		HashMap<Integer,ArrayList<String>> map = new HashMap();
		ArrayList<String> list = new ArrayList<String>();
		list.add("asdasd");
		map.put(1, list);
		ArrayList<String> list2 = map.get(1);
		list2.add("sdfsdf");
		list2.add("sdfsdf");
		System.out.println(list2.size()+"  "+map.get(1).size());
	}
}
class aaa{
	int a = 1;
}
