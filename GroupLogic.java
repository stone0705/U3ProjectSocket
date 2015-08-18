import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;

public class GroupLogic {
	public static String createGroup(String[] commit,Socket socket){
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(commit[1], commit[2])){
				if(mainsocket.sql.isGroupExist(commit[1], commit[3])){
	                String msg = StringRule.standard("2101");
	                bw.write(msg);
	                // 立即發送
	                bw.flush();
	                return "群組名稱重複";
				}else{
					mainsocket.sql.createGroup(commit[1], commit[3]);
	                String msg = StringRule.standard("2100");
	                bw.write(msg);
	                bw.flush();
					return "創建成功";
				}
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其它裝置登入";	
			}
		}catch(Exception ex){
			return ex.toString();
		}
	}
	public static String searchGroup(String[] commit,Socket socket){
		ResultSet rs = mainsocket.sql.searchGroup(commit[1]);
		BufferedWriter bw;
		try{
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!rs.next()){
				bw.write(StringRule.standard("2103"));
				bw.flush();
				return "查無群組";
			}else{
				bw.write(StringRule.standard("2102",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
				bw.flush();
				while(rs.next()){
					bw.write(StringRule.standard("2102",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
					bw.flush();
				}
				bw.write(StringRule.standard("0000"));
				bw.flush();
			}
		}catch(Exception ex){
			return ex.toString();
		}
		return "";
	}
	public static String applyGroup(String[] commit,Socket socket){
		boolean goodaccess =  mainsocket.sql.compareAndroidID(commit[1], commit[2]);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!goodaccess){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}else{
				if(mainsocket.sql.isInGroup(commit[1], commit[3], commit[4])){
					bw.write(StringRule.standard("2105"));
					bw.flush();
					return "已在群組中";
				}else{
					mainsocket.sql.applyGroup(commit[1], commit[3], commit[4]);
					bw.write(StringRule.standard("2104"));
					bw.flush();
					return "申請成功";
				}
			}
		}catch(Exception ex){
			return "grouplogic applyGroup:"+ex.toString();
		}
	}
}
