import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
}
