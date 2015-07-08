import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MeetingLogic {
	static String firstconnect(String[] commit,Socket socket){
		String key = commit[3]+":"+commit[4];
		String answer = "";
		//DB find meeting	
		if(mainsocket.meetingmap.containsKey(key)){
			addsocket(commit[3]+":"+commit[4],socket);
			//post txt
		}else{
			mainsocket.meetingmap.put(key,new ArrayList<Socket>());
			addsocket(key,socket);
			//post txt
		}
		return answer;
	}
	static String receice(String[] commit,Socket socket){
		String key = commit[3]+":"+commit[4];
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
        String standardMsg = StringRule.standard(3,"2030",account,Msg);
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
	private synchronized static void addsocket(String key,Socket socket){
		ArrayList<Socket> meetingList = mainsocket.meetingmap.get(key);
		meetingList.add(socket);
		mainsocket.meetingmap.put(key, meetingList);
	}
}
