import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;

public class masterpageLogic {
	public static String getMeetingList(String[] commit,Socket socket){
		Sql sql = new Sql();
		String answer = "";
		ResultSet rs = sql.getMeetingSql(commit[1], commit[3], commit[4]);
		try {
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!sql.compareAndroidID(commit[1], commit[2])){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其它裝置登入";	
			}
			if(rs == null){
				bw.write(StringRule.standard("2079"));
				bw.flush();
				return "不在群組中";	
			}
			while(rs.next()){ 
				if(!rs.getBoolean(5)){
					bw.write(StringRule.standard("2078"));
					bw.flush();
					return "尚未被加入群組";	
				}
                String standardMsg = StringRule.standard("2071",rs.getString(1),rs.getString(2),rs.getTimestamp(3).toString(),rs.getTimestamp(4).toString());
                bw.write(standardMsg);
                bw.flush();
			}
			String standardMsg = StringRule.standard("0000");
			bw.write(standardMsg);
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}
}
