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
	}
}
class aaa{
	int a = 1;
}
