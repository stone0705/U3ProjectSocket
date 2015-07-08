import java.net.Socket;


public class LogicRule {
	public static String switchcommit(String[] commit,Socket socket){
	String answer = "unfind";
	switch(commit[0]){
	case"1000":{
		return AccountLogic.submit(commit,socket);
	}
	case"1010":{
		return AccountLogic.login(commit,socket);
	}
	case"1030":{
		return MeetingLogic.firstconnect(commit, socket);
	}
	case"1031":{
		return MeetingLogic.receice(commit, socket);
	}
	}
	return answer;
	}

}
