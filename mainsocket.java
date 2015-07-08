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
	static String msg;
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
                	System.out.println("run");
					br = new BufferedReader(
					        new InputStreamReader(socket.getInputStream()));
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					while(socket.isConnected()){
						if((msg = br.readLine()) != null){
							System.out.println(msg);
							String[] a = new StringRule(msg).dString;
							String response = LogicRule.switchcommit(a, socket);
							
							for(int i =0;i<a.length;i++){
								System.out.println(a[i]);
							}
						}else{
							break;
						}
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
