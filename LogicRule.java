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
		return MeetingLogic.createMeeting(commit, socket);
	}
	case"1031":{
		return MeetingLogic.firstconnect(commit, socket);
	}
	case"1032":{
		return MeetingLogic.receice(commit, socket);
	}
	case"1071":{
		return masterpageLogic.getMeetingList(commit, socket);
	}
	case"1100":{
		return GroupLogic.createGroup(commit, socket);
	}
	case "1101":{
		return GroupLogic.searchGroup(commit, socket);
	}
	case "1102":{
		return GroupLogic.applyGroup(commit, socket);
	}
	}
	return answer;
	}

}
