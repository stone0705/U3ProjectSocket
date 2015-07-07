
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class login {
	public static void main(String[] args){
		Socket socket = new Socket();
		Scanner key = new Scanner(System.in);
		try {
			socket.connect(new InetSocketAddress("127.0.0.1",5050));
			BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			while(true){
				String pass = key.next();
				pass=pass.replaceAll("/", "//");
				pass=pass.replaceAll(":", "/:");
				System.out.println("ok   "+pass);
				if(pass!=null){
					bw.write(11010+":"+pass+":"+"afsdfwe:");
					bw.write("\n");
					bw.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
