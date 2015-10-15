import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MeetingLogic {
	static String firstconnect(String[] commit,Socket socket){
		String key = commit[3];
		String group = commit[4];
		String founder = commit[5];
		String answer = "";
		mainsocket.meeting_id.put(socket, key);
		//DB find meeting	
		if(mainsocket.meetingmap.containsKey(key)){
			ArrayList<Socket> socketlist = mainsocket.meetingmap.get(key);
			socketlist.add(socket);
			//post txt
		}else{
			mainsocket.meetingmap.put(key,new ArrayList<Socket>());
			ArrayList<Socket> socketlist = mainsocket.meetingmap.get(key);
			socketlist.add(socket);
			//post txt
		}
		postMeetingLog(key,group,founder,socket);
		return answer;
	}
	static String receice(String[] commit,Socket socket){
		String key = commit[3];
		String account = commit[1];
		String msg = commit[2];	
		//save to txt
		return castmsg(account,msg,key,commit[4],socket);
	}
	private static String castmsg(String account,String Msg,String key,String android_id,Socket mysocket){
		if(mainsocket.sql.compareAndroidID(account, android_id)){
	        // 創造socket陣列
	        Socket[] ps=new Socket[mainsocket.meetingmap.get(key).size()];
	        // 將players轉換成陣列存入ps
	        mainsocket.meetingmap.get(key).toArray(ps);
	        // 走訪ps中的每一個元素
	        String standardMsg = StringRule.standard("2030",account,Msg);
	        for (Socket socket :ps ) {
	            try {
	                // 創造網路輸出串流
	                BufferedWriter bw;
	                bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
	 
	                // 寫入訊息到串流
	                bw.write(standardMsg);
	 
	                // 立即發送
	                bw.flush();
	            } catch (IOException e) {
	            	return "castmsg:"+e.toString();
	            }
	        }
        	System.out.println(mainsocket.msgSql.putMeetingMsg(key, account, Msg));
		}else{
            try {
                // 創造網路輸出串流
                BufferedWriter bw;
                bw = new BufferedWriter( new OutputStreamWriter(mysocket.getOutputStream()));
                // 寫入訊息到串流
                bw.write(StringRule.standard("2077"));
                // 立即發送
                bw.flush();
                return "android不合";
            } catch (IOException e) {}
		}
        return "傳送訊息完成";
	}
	static void postMeetingLog(String meetingid,String group,String founder,Socket socket){
		String account,msg;
		ResultSet msgLog = mainsocket.msgSql.getMeetingMsg(meetingid);
		try {
            BufferedWriter bw;
            bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			ResultSet ANT = mainsocket.sql.getANT(group,founder);
			while(ANT.next()){
				bw.write(StringRule.standard("2032",ANT.getString(1),ANT.getString(2)));
			}
            bw.write(StringRule.standard("0000"));
            bw.flush();
    		while(msgLog.next()){
    			account = msgLog.getString(1);
    			msg = msgLog.getString(3);
    			bw.write(StringRule.standard("2031",account,msg));
    			bw.flush();
    		}
            bw.write(StringRule.standard("0000"));
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
