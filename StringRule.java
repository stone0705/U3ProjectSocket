import java.util.HashMap;

//split with ":" and first is rule number 
public class StringRule {
	String[] dString;
	static HashMap rulemap = new HashMap<String,Integer>();
	public static String[] divide(String msg){
		String[] findrule = msg.split(":");	
		int number;
		try{
			number = rule(findrule[0]);
		}catch(Exception ex){
			String[] a = new String[1];
			a[0] = "???";
			System.out.println("錯誤訊息格式");
			return a;
		}
		String[] answer = new String[number];
		int state = 0;
		int index = 0;
		int temp  = 0;
		for(int i = 0;i<msg.length();i++){
			switch(msg.charAt(i)){
			case '/':{
				if(state == 0){
					state = 1;
				}else{
					state = 0;
				}
				break;
			}
			case ':':{
				if(state == 0){
					if(temp == 0){
						answer[index] = msg.substring(temp, i);
						temp = i;
					}else{
						answer[index] = msg.substring(temp+1, i);
						temp = i;
					}
					index++;
				}else if(state == 1){
					state = 0;
				}
				break;
			}
			}
		}
		for(int i =0;i<answer.length;i++){
			answer[i] = answer[i].replaceAll("//", "/");
			answer[i] = answer[i].replaceAll("/:", ":");
		}
		return answer;
	}
	
	static int rule(String findrule){
		//傳送完成 0000(傳送連續封包時使用)
		rulemap.put("n0000", 1);
        //註冊 1000 帳號 密碼 暱稱
        rulemap.put("n1000", 4);
        //登入  1010 帳號 密碼 android_id
        rulemap.put("n1010", 4);
        //創建提醒 1020 帳號 android_id 群組 founder title content 預定時間
        rulemap.put("n1020", 8);
        //創建 meeting 1030 帳號 android_id 群組 founder title start_time end_time
        rulemap.put("n1030", 8);
        //進入 meeting 1031 帳號 android_id 群組 meeting_id group founder
        rulemap.put("n1031", 6);
        //客戶端傳送訊息 1032 帳號 text meeting_id android_id
        rulemap.put("n1032", 5);
        //創建 Vote 1040 帳號 android_id 群組 founder title start_time end_time
        rulemap.put("n1040",8);
        //創建Vote選項  1041 帳號 android_id 群組 founder vote_id option_string
        rulemap.put("n1041",7);
        //進入投票 1042 帳號 android_id vote_id
        rulemap.put("n1042", 4);
        //投票 1043 帳號 android_id vote_id option_id
        rulemap.put("n1043", 5);
        //獲得提醒列表 1070 帳號 android_id 群組 founder
        rulemap.put("n1070", 5);
        //獲得會議列表 1071 帳號 android_id 群組 founder
        rulemap.put("n1071", 5);
        //獲得投票列表 1072 帳號 android_id 群組 founder
        rulemap.put("n1072", 5);
        //創建群組 1100 帳號 android_id 群組
        rulemap.put("n1100", 4);
        //尋找群組 1101 關鍵字
        rulemap.put("n1101", 2);
        //申請加入群組 1102 帳號 android_id 群組 founder
        rulemap.put("n1102", 5);
        //尋找自己的群組 1103 帳號 android_id
        rulemap.put("n1103", 3);
        //取得已加入群組的會員 1104 群組 founder
        rulemap.put("n1104", 3);
        //取得待加入群組的會員 1105 群組 founder
        rulemap.put("n1105", 3);
        //同意加入 1106 帳號 android_id 群組 founder u_name
        rulemap.put("n1106", 6);
        //修改會員權限 1107 帳號 android_id 群組 founder u_name add_permit remove_permit note_permit meeting_permit vote_permit sch_permit
        rulemap.put("n1107", 12);
        //伺服器端傳送訊息 2030 帳號 text
        rulemap.put("n2030", 3);
        //伺服器端傳送歷史訊息 2031 帳號 text
        rulemap.put("n2031", 3);
        //伺服器傳送ANT列表 2032 帳號 nickname
        rulemap.put("n2032",3);
        //傳送Vote_id 2040 vote_id
        rulemap.put("n2040", 2);
        //傳送vote選項  2041 id content number
        rulemap.put("n2041", 4);
        //投票成功 2042
        rulemap.put("n2042", 1);
        //已經投過票了 2043
        rulemap.put("n2043", 1);
        //註冊成功 2000
        rulemap.put("n2000", 1);
        //註冊失敗 有相同的帳號 2001
        rulemap.put("n2001", 1);
        //登入成功 2010 
        rulemap.put("n2010", 1);
        //登入失敗 帳號或密碼錯誤 2011
        rulemap.put("n2011", 1);
        //傳送提醒列表  2070  id title content thetime createman
        rulemap.put("n2070", 6);
        //傳送會議列表   2071  id title start_time end_time
        rulemap.put("n2071", 5);
        //傳送投票列表   2072  id title start_time end_time 發起人
        rulemap.put("n2072", 6);
        rulemap.put("n2073", 5);
        //帳號在其他裝置上登入
        rulemap.put("n2077", 1);
        //尚未被加入群組
        rulemap.put("n2078", 1);
        //不在群組中
        rulemap.put("n2079", 1);
        //創建群組成功
        rulemap.put("n2100", 1);
        //群組名稱重複
        rulemap.put("n2101", 1);
        //傳送搜尋到的群組 2102 群組名稱 創立者 人數 群組簡介
        rulemap.put("n2102", 5);
        //查無群組 2103
        rulemap.put("n2103", 1);
        //申請成功 2104
        rulemap.put("n2104", 1);
        //已在群組中 2105
        rulemap.put("n2105", 1);
        //傳送群組中的成員 2106 name nickname addRight removeRight noteRight meetingRight voteRight schRight isfounder enterTime
        rulemap.put("n2106", 11);
        //傳送群組中待加入的成員 2107 name nickname enterTime
        rulemap.put("n2107", 4);
        //加入成功 2108
        rulemap.put("n2108", 1);
        //踢除成功 2109
        rulemap.put("n2109", 1);
        //創建提醒成功 2110
        rulemap.put("n2110", 1);
        //創建會議成功 2111
        rulemap.put("n2111", 1);
        //創建投票成功 2112
        rulemap.put("n2112", 1);
        //會員權限修改成功 2114
        rulemap.put("n2114", 1);
        //加入權限不足 2190
        rulemap.put("n2190", 1);
        //踢除權限不足
        rulemap.put("n2191", 1);
        //提醒權限不足
        rulemap.put("n2192", 1);
        //會議權限不足
        rulemap.put("n2193", 1);
        //投票權限不足
        rulemap.put("n2194", 1);
        //不是founder 2196
        rulemap.put("n2196", 1);
        //尚未開始
        rulemap.put("n2197", 1);
        //已經結束
        rulemap.put("n2198", 1);
		return (int) rulemap.get("n"+findrule);
	}
    public static String standard(String... word){
    	int number = rule(word[0]);
        StringBuilder answer = new StringBuilder();
        for(int i = 0;i<number;i++){
            String temp = word[i];
            temp=temp.replaceAll("/", "//");
            temp=temp.replaceAll(":", "/:");
            answer.append(temp+":");
        }
        return answer.toString()+"\n";
    }

}
