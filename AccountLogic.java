import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class AccountLogic {
	public static String submit(String[] commit,Socket socket){
		String response = "";
		rha256 sha = new rha256(commit[2]);
		String account = commit[1];
		String salt = sha.salt;
		String hash = sha.hash;
		String nick_name = commit[3];
		boolean exist = mainsocket.sql.isAccountExist(account);
		//if select account is empty then save salt hash account
		
		if(!exist){
			mainsocket.sql.submitSql(account, hash, salt, nick_name);
			response = "success";
            try{
        		BufferedWriter bw;
        		bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                // 寫入訊息到串流
                String msg = StringRule.standard("2000");
                bw.write(msg);
                // 立即發送
                bw.flush();
            }catch(Exception ex){	
            }
		}else{
			response = "account already exist";
            try{
        		BufferedWriter bw;
        		bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                // 寫入訊息到串流
                String msg = StringRule.standard("2001");
                bw.write(msg);
                // 立即發送
                bw.flush();
            }catch(Exception ex){	
            }
		}
		return response;
	}
	public static String login(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[3];
		String response = "";
		boolean pass = false;
		boolean isExist = mainsocket.sql.isAccountExist(account);
		System.out.println("帳號:"+account+"  帳號存在"+isExist);
		if(isExist){
			String[] DBset = mainsocket.sql.loginSql(account);
			String DBhash = DBset[0];
			String DBsalt = DBset[1];
			rha256 sha = new rha256(commit[2],DBsalt);
			if(DBhash.equals(sha.hash)){
				pass = true;
			}
		}
		//pass = true;
		if(pass){    
			response = "login success";
            try{
        		BufferedWriter bw;
        		bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                // 寫入訊息到串流
                String msg = StringRule.standard("2010");
                bw.write(msg);
                // 立即發送
                bw.flush();
                mainsocket.sql.insertSaveuser(account, android_id);
            }catch(Exception ex){	
            }
		}else{
			response = "login error";
            try{
        		BufferedWriter bw;
        		bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                // 寫入訊息到串流
                String msg = StringRule.standard("2011");
                bw.write(msg);
                // 立即發送
                bw.flush();
            }catch(Exception ex){	
            }
		}
		return response;
	}
}
