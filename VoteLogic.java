import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;
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
	static String enterVote(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String vote_id = commit[3];
		ResultSet rs;
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				rs = mainsocket.votesql.getAlloption(vote_id);
				while(rs.next()){
					bw.write(StringRule.standard("2041",rs.getString(1),rs.getString(2),rs.getString(3)));
					System.out.println(StringRule.standard("2041",rs.getString(1),rs.getString(2),rs.getString(3)));
					bw.flush();
				}
				bw.write(StringRule.standard("0000"));
				bw.flush();
				return "傳送選項完畢";
			}else{
				bw.write(StringRule.standard("2077"));
				bw.flush();
				return "帳號已在其他裝置登入";
			}
		}catch(Exception ex){
			return "createMeeting:"+ex.toString();
		}
	}
	static String vote(String[] commit,Socket socket){
		String account = commit[1];
		String android_id = commit[2];
		String vote_id = commit[3];
		String option_id = commit[4];
		try{
			BufferedWriter bw;
			bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			if(mainsocket.sql.compareAndroidID(account, android_id)){
				if(!mainsocket.sql.isUserVote(vote_id, account)){
					mainsocket.sql.userVote(vote_id, option_id, account);
					mainsocket.votesql.userVote(vote_id, option_id);
					bw.write(StringRule.standard("2042"));
					bw.flush();
					return "投票成功";
				}else{
					bw.write(StringRule.standard("2043"));
					bw.flush();
					return "已經投過票了";
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
