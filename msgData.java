import java.sql.Timestamp;


public class msgData implements Comparable<msgData> {
	String account;
	String msg;
	Timestamp time;
	public msgData(String account,String msg,Timestamp time){
		this.account = account;
		this.msg = msg;
		this.time = time;
	}
	@Override
	public int compareTo(msgData another) {
		return this.time.compareTo(another.time);
	}
}
