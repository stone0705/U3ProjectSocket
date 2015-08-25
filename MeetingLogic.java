import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MeetingLogic {
	static String firstconnect(String[] commit,Socket socket){
		String key = commit[3];
		String answer = "";
		mainsocket.meeting_id.put(socket, key);
		//DB find meeting	
		if(!mainsocket.meetingFileLock.containsKey(key)){
			ReadWriteLock wrl = new ReadWriteLock();
			mainsocket.meetingFileLock.put(key, wrl);
		}
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
		postMeetingLog(commit[3],socket);
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
	        try {
	        	ReadWriteLock rwl = mainsocket.meetingFileLock.get(key);
				rwl.lockWrite();
				writeMeetingLog(key,Msg,account);
				rwl.unlockWrite();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return "readwritelock:"+e.toString();
			}
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
	/*
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
	*/
	static void postMeetingLog(String meetingid,Socket socket){
		File file = new File(meetingid+".txt");
		ReadWriteLock rwl = mainsocket.meetingFileLock.get(meetingid);
		try {
            BufferedWriter bw;
            bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(file.exists()){
				rwl.lockRead();
				BufferedReader br = new BufferedReader(new FileReader(file));
				while(br.ready()){
					String Line = br.readLine();
					String[] divide = Line.split(":", 2);       
		            bw.write(StringRule.standard("2031",divide[0],divide[1]));
		            //System.out.println(StringRule.standard("2031",divide[0],divide[1]));           
				}
				bw.flush();
				br.close();
				rwl.unlockRead();
			}
            bw.write(StringRule.standard("0000"));
            bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	synchronized static void writeMeetingLog(String meetingid,String msg,String account){
		File file = new File(meetingid+".txt");
			try {
				if(!file.exists()){
				    file.createNewFile();
				}
				FileWriter fw = new FileWriter(file,true);
				fw.append(account+":"+msg+"\n");
				//fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}
		
	}
}
