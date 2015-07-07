
public class LogicRule {
	public static String switchcommit(String[] commit){
	String answer = "unfind";
	switch(commit[0]){
	case"1000":{
		return AccountLogic.submit(commit);
	}
	case"1010":{
		return AccountLogic.login(commit);
	}
	}
	return answer;
	}

}
