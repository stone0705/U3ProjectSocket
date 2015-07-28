import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class AccountLogic {
	public static String submit(String[] commit,Socket socket){
		Sql sql = new Sql();
		String response = "";
		rha256 sha = new rha256(commit[2]);
		String account = commit[1];
		String salt = sha.salt;
		String hash = sha.hash;
		String nick_name = commit[3];
		boolean exist = sql.isAccountExist(account);
		//if select account is empty then save salt hash account
		
		if(!exist){
			sql.submitSql(account, hash, salt, nick_name);
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
		sql.close();
		return response;
	}
	public static String login(String[] commit,Socket socket){
		Sql sql = new Sql();
		String account = commit[1];
		String response = "";
		boolean pass = false;
		boolean isExist = sql.isAccountExist(account);
		System.out.println("帳號:"+account+"  帳號存在"+isExist);
		if(isExist){
			String[] DBset = sql.loginSql(account);
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
		sql.close();
		return response;
	}
}
