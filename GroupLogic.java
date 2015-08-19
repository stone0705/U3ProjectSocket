import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class GroupLogic {
	public static String createGroup(String[] commit,Socket socket){
		try{
			BufferedWriter bw;
		    String account = commit[1];
		    String nickname = mainsocket.sql.getNickName(account);
		    String android_id = commit[2];
		    String group = commit[3];
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(mainsocket.sql.isGroupExist(account, group)){
	                String msg = StringRule.standard("2101");
	                bw.write(msg);
	                // 立即發送
	                bw.flush();
	                return "群組名稱重複";
				}else{
					mainsocket.sql.createGroup(account,nickname,group);
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
		return "傳送群組完成";
	}
	public static String applyGroup(String[] commit,Socket socket){
		String account = commit[1];
		String nickname = mainsocket.sql.getNickName(account);
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		boolean goodaccess =  mainsocket.sql.compareAndroidID(account, android_id);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!goodaccess){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}else{
				if(mainsocket.sql.isInGroup(account, group, founder)){
					bw.write(StringRule.standard("2105"));
					bw.flush();
					return "已在群組中";
				}else{
					mainsocket.sql.applyGroup(account,nickname,group,founder);
					bw.write(StringRule.standard("2104"));
					bw.flush();
					return "申請成功";
				}
			}
		}catch(Exception ex){
			return "grouplogic applyGroup:"+ex.toString();
		}
	}
	public static String selectGroup(String[] commit,Socket socket){
		boolean goodaccess =  mainsocket.sql.compareAndroidID(commit[1], commit[2]);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(!goodaccess){
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}else{
				ResultSet rs = mainsocket.sql.selectGroup(commit[1]);
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
				return "傳送群組完成";
			}
		}catch(Exception ex){
			return "grouplogic selectGroup:"+ex.toString();
		}
	}
	public static String postIsJoinList(String[] commit,Socket socket){
		String group = commit[1];
		String founder = commit[2];
		ResultSet rs  = mainsocket.sql.getIsJoinMember(group,founder);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			while(rs.next()){
				String name = rs.getString(1);
				String nickname = rs.getString(2);			
				Boolean addRight = rs.getBoolean(3);
				Boolean removeRight = rs.getBoolean(4);
				Boolean noteRight = rs.getBoolean(5);
				Boolean meetingRight = rs.getBoolean(6);
				Boolean voteRight = rs.getBoolean(7);
				Boolean schRight = rs.getBoolean(8);
				Boolean isfounder = rs.getBoolean(9);
				Timestamp ts = rs.getTimestamp(10);
				bw.write(StringRule.standard("2106",name,nickname,addRight.toString(),removeRight.toString()
						,noteRight.toString(),meetingRight.toString(),voteRight.toString(),schRight.toString()
						,isfounder.toString(),ts.toString()));
				bw.flush();
			}
			bw.write(StringRule.standard("0000"));
			bw.flush();
			return "傳送用戶清單完成";
		}catch(Exception ex){
			return "postIsJoinList"+ex.toString();
		}
	}
	public static String postNotJoinList(String[] commit,Socket socket){
		String group = commit[1];
		String founder = commit[2];
		ResultSet rs  = mainsocket.sql.getNotJoinMember(group,founder);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			while(rs.next()){
				String name = rs.getString(1);
				String nickname = rs.getString(2);			
				Timestamp ts = rs.getTimestamp(3);
				bw.write(StringRule.standard("2107",name,nickname,ts.toString()));
				bw.flush();
			}
			bw.write(StringRule.standard("0000"));
			bw.flush();
			return "傳送用戶清單完成";
		}catch(Exception ex){
			return "postIsJoinList"+ex.toString();
		}
	}
	public static String LetHeIn(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		String u_name = commit[5];
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(mainsocket.sql.isInGroup(account, group, founder)&&mainsocket.sql.isInGroup(u_name, group, founder)){
					if(mainsocket.sql.isAddPermit(account, group, founder)){
						mainsocket.sql.letHeIn(group, founder, u_name);
						bw.write(StringRule.standard("2108"));
						bw.flush();
						return "加入成功";
					}else{
						bw.write(StringRule.standard("2109"));
						return "加入權限不足";
					}
				}else{
					bw.write(StringRule.standard("2079"));
					bw.flush();
					return "不在群組中";
				}
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置上登入";
			}			
		}catch(Exception ex){
			return("LetHeIn GroupLogic:"+ex.toString());
		}
	}
}
