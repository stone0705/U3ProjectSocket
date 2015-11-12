import java.util.ArrayList;


public class voteData implements Comparable<voteData> {
	String content;
	int option_id;
	int number;
	public voteData(String content,int option_id,int number){
		this.content = content;
		this.option_id = option_id;
		this.number = number;
	}
	@Override
	public int compareTo(voteData another) {
		return Integer.compare(this.number,another.number);
	}
}
