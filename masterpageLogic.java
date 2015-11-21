import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;

public class masterpageLogic {
	public static String getMeetingList(String[] commit,Socket socket){
		Sql sql = mainsocket.sql;
		String answer = "";
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		ResultSet rs = sql.getMeetingSql(account, group, founder);
		try {
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!sql.compareAndroidID(account,android_id)){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其它裝置登入";	
			}
			if(!mainsocket.sql.isInGroup(account, group, founder)){
				bw.write(StringRule.standard("2079"));
				bw.flush();
				return "不在群組中";	
			}
			if(!mainsocket.sql.isAddInGroup(account, group, founder)){
				bw.write(StringRule.standard("2078"));
				bw.flush();
				return "尚未被加入群組";
			}
			while(rs.next()){ 
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
			return "getMeetingList:"+e.toString();
		}
		return "會議清單傳送完成";
	}
	public static String getVoteList(String[] commit,Socket socket){
		Sql sql = mainsocket.sql;
		String answer = "";
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		ResultSet rs = sql.getVoteSql(account, group, founder);
		try {
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!sql.compareAndroidID(account,android_id)){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其它裝置登入";	
			}
			if(!mainsocket.sql.isInGroup(account, group, founder)){
				bw.write(StringRule.standard("2079"));
				bw.flush();
				return "不在群組中";	
			}
			if(!mainsocket.sql.isAddInGroup(account, group, founder)){
				bw.write(StringRule.standard("2078"));
				bw.flush();
				return "尚未被加入群組";
			}
			while(rs.next()){ 
                String standardMsg = StringRule.standard("2072",rs.getString(1),rs.getString(2),rs.getTimestamp(3).toString(),rs.getTimestamp(4).toString(),rs.getString(5));
                bw.write(standardMsg);
                bw.flush();
			}
			String standardMsg = StringRule.standard("0000");
			bw.write(standardMsg);
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "getMeetingList:"+e.toString();
		}
		return "投票清單傳送完成";
	}
	public static String getRemidList(String[] commit,Socket socket){
		Sql sql = mainsocket.sql;
		String answer = "";
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		String id,title,content,thetime,createman;
		ResultSet rs = sql.getRemindSql(account, group, founder);
		try {
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!sql.compareAndroidID(account,android_id)){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其它裝置登入";	
			}
			if(!mainsocket.sql.isInGroup(account, group, founder)){
				bw.write(StringRule.standard("2079"));
				bw.flush();
				return "不在群組中";	
			}
			if(!mainsocket.sql.isAddInGroup(account, group, founder)){
				bw.write(StringRule.standard("2078"));
				bw.flush();
				return "尚未被加入群組";
			}
			while(rs.next()){ 
				id = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				thetime = rs.getTimestamp(4).toString();
				createman = rs.getString(5);
                String standardMsg = StringRule.standard("2070",id,title,content,thetime,createman);
                bw.write(standardMsg);
                bw.flush();
			}
			String standardMsg = StringRule.standard("0000");
			bw.write(standardMsg);
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "getMeetingList:"+e.toString();
		}
		return "會議清單傳送完成";
	}
}
