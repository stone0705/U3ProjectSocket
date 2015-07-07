
public class AccountLogic {
	public static String submit(String[] commit){
		String response = "";
		boolean pass = false;
		rha256 sha = new rha256(commit[2]);
		String account = commit[1];
		String salt = sha.salt;
		String hash = sha.hash;
		//if select account is empty then save salt hash account
		if(pass){
			response = "success";
		}else{
			response = "account already exist";
		}
		return response;
	}
	public static String login(String[] commit){
		String response = "";
		boolean pass = false;
		String DBhash = "";
		String DBsalt = "";
		rha256 sha = new rha256(commit[2],DBsalt);
		//if select account not empty
		if(DBhash.equals(sha.hash)){
			pass = true;
		}
		if(pass){
			response = "PASS";
		}else{
			response = "account or password is wrong";
		}
		return response;
	}
}
