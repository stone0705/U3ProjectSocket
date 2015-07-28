import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class masterpageLogic {
	public static String getMeetingList(String[] commit,Socket socket){
		String answer = "";
		Sql sql = new Sql();
		ResultSet rs = sql.getMeetingSql(commit[1], commit[3], commit[4]);
		try {
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(rs == null){
				bw.write(StringRule.standard("2079"));
				bw.flush();
				return "不在群組中";	
			}
			while(rs.next()){            
                String standardMsg = StringRule.standard("2072",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                bw.write(standardMsg);
                bw.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}
}
