import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class mainsocket {
	static BufferedReader br;
	static BufferedWriter bw;
	static HashMap<Socket,String> meeting_id = new HashMap();
	static HashMap<String,ReadWriteLock> meetingFileLock = new HashMap();
	static Object obj = new Object();
    private static int serverport = 5050;
    static ServerSocket serverSocket;
    static HashMap<String,ArrayList<Socket>> meetingmap = new HashMap();
	public static void main(String[] args){
		try{
			serverSocket = new ServerSocket(serverport);
			while(true){
				Socket socket = serverSocket.accept();
				if(socket.isConnected()){
					System.out.println("connect");
					connect(socket);
				}
				
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	public static void connect(final Socket socket){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
                try {
                	String msg;
                	System.out.println("run");
					br = new BufferedReader(
					        new InputStreamReader(socket.getInputStream()));
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					while(socket.isConnected()){
						if((msg = br.readLine()) != null){
							System.out.println(msg);
							String[] a = StringRule.divide(msg);
							String response = LogicRule.switchcommit(a, socket);
							System.out.println(response);
						}else{
							break;
						}
						}
					if(meeting_id.containsKey(socket)){
						ArrayList<Socket> socketlist = meetingmap.get(meeting_id.get(socket));
						socketlist.remove(socket);
						System.out.println("刪除socket");
					}
					System.out.println("斷開連接");			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});
		t.start();
	}

}
