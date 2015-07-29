import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MeetingLogic {
	static String createMeeting(String[] commit,Socket socket){
		String answer = "";
		
		return answer;
	}
	static String firstconnect(String[] commit,Socket socket){
		String key = commit[3];
		String answer = "";
		mainsocket.meeting_id.put(socket, key);
		//DB find meeting	
		if(mainsocket.meetingmap.containsKey(key)){
			adddelsocket(true,commit[3],socket);
			//post txt
		}else{
			mainsocket.meetingmap.put(key,new ArrayList<Socket>());
			adddelsocket(true,key,socket);
			//post txt
		}
		return answer;
	}
	static String receice(String[] commit,Socket socket){
		String key = commit[3];
		String account = commit[1];
		String msg = commit[2];
		castmsg(account,msg,key);
		//save to txt
		return "";
	}
	private static void castmsg(String account,String Msg,String key){
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
 
            }
        }
	}
	synchronized static void adddelsocket(boolean choice,String key,Socket socket){
		if(choice){
			ArrayList<Socket> meetingList = mainsocket.meetingmap.get(key);
			meetingList.add(socket);
			mainsocket.meetingmap.put(key, meetingList);
		}else{
			ArrayList<Socket> meetingList = mainsocket.meetingmap.get(key);
			meetingList.remove(socket);
			mainsocket.meeting_id.remove(socket);
			mainsocket.meetingmap.put(key, meetingList);
		}
	}
}
