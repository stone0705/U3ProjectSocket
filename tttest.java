import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class tttest {
	public static void main(String[] args) throws ParseException{
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
		boolean ddd = true;
		System.out.println(ddd);
		Boolean dd  =true;
		
		String adad = dd.toString();
		System.out.println(adad);
		dd = Boolean.valueOf(adad);
		System.out.println(dd);
		ddd =dd;
		Timestamp ts = Timestamp.valueOf("2015-8-25 11:7:0");
		System.out.println(ts.toString());
	}
}
class aaa{
	int a = 1;
}
