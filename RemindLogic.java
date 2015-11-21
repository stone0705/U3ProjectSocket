import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;


public class RemindLogic {
	public static String createRemind(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		String title = commit[5];
		String content = commit[6];
		Timestamp time = Timestamp.valueOf(commit[7]);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(mainsocket.sql.isInGroup(account, group, founder)){
					if(mainsocket.sql.isRemindPermit(account, group, founder)){
						mainsocket.sql.createRemind(group, founder, account, title, content, time);
						bw.write(StringRule.standard("2110"));
						bw.flush();
						return "建立提醒成功";
					}else{
						bw.write(StringRule.standard("2192"));
						bw.flush();
						return "提醒權限不足";
					}
				}else{
					bw.write(StringRule.standard("2079"));
					bw.flush();
					return "不在群組中";
				}
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}
		}catch(Exception ex){
			return "createMeeting:"+ex.toString();
		}
	}
}
