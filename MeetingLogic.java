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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
		}else{
			mainsocket.meetingmap.put(key,new ArrayList<Socket>());
			ArrayList<Socket> socketlist = mainsocket.meetingmap.get(key);
			socketlist.add(socket);
		}
		switch(mainsocket.sql.MeetingTimeTest(key)){
		case(-1):{
			try{
				BufferedWriter bw;
				bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
				bw.write(StringRule.standard("2197"));
				bw.flush();
				return"會議尚未開始";
			}catch(Exception ex){
				return "firstconnect" + ex.toString();
			}
		}
		case(0):{
			postMeetingLog(key,group,founder,socket,false);
			return answer;
		}
		case(1):{
			postMeetingLog(key,group,founder,socket,true);
			return "會議已經結束";
		}
		}
		return answer;
	}
	static String receice(String[] commit,Socket socket){
		String key = commit[3];
		String account = commit[1];
		String msg = commit[2];	
		switch(mainsocket.sql.MeetingTimeTest(key)){
		case(-1):{
			return"會議尚未開始";
		}
		case(0):{
			return castmsg(account,msg,key,commit[4],socket);
		}
		case(1):{
			return "會議已經結束";
		}
		}
		return "";
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
	static void postMeetingLog(String meetingid,String group,String founder,Socket socket,boolean isFinish){
		String account,msg;
		Timestamp time;
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
            ArrayList<msgData> list = new ArrayList();
    		while(msgLog.next()){
    			account = msgLog.getString(1);
    			time = msgLog.getTimestamp(2);
    			msg = msgLog.getString(3);
    			list.add(new msgData(account,msg,time));
    		}
			Collections.sort(list);
			for(int i = 0;i<list.size();i++){
				msgData data = list.get(i);
				bw.write(StringRule.standard("2031",data.account,data.msg));
				bw.flush();
			}
			if(isFinish){
				bw.write(StringRule.standard("2198"));
			}else{
				bw.write(StringRule.standard("0000"));
			} 
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
