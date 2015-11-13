import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;


public class VoteLogic {
	static String createVote(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		String title = commit[5];
		Timestamp sts = Timestamp.valueOf(commit[6]);
		Timestamp ets = Timestamp.valueOf(commit[7]);
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(mainsocket.sql.isInGroup(account, group, founder)){
					if(mainsocket.sql.isVotePermit(account, group, founder)){
						String vote_id =  mainsocket.sql.createNewVote(group, founder, account, title, sts, ets);
						bw.write(StringRule.standard("2040",vote_id));
						bw.flush();
						return "傳送Vote_id";
					}else{
						bw.write(StringRule.standard("2194"));
						bw.flush();
						return "投票權限不足";
					}
				}else{
					bw.write(StringRule.standard("2079"));
					bw.flush();
					return "不在群組中";
				}
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}
		}catch(Exception ex){
			return "createMeeting:"+ex.toString();
		}
	}
	static String addOptionToVote(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String group = commit[3];
		String founder = commit[4];
		String vote_id = commit[5];
		String option_string = commit[6];
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(mainsocket.sql.isInGroup(account, group, founder)){
					if(mainsocket.sql.isVotePermit(account, group, founder)){
						mainsocket.votesql.putVoteOption(vote_id, option_string);
						bw.write(StringRule.standard("2112"));
						bw.flush();
						return "增加選項完成";
					}else{
						bw.write(StringRule.standard("2194"));
						bw.flush();
						return "投票權限不足";
					}
				}else{
					bw.write(StringRule.standard("2079"));
					bw.flush();
					return "不在群組中";
				}
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}
		}catch(Exception ex){
			return "createMeeting:"+ex.toString();
		}
	}
}
