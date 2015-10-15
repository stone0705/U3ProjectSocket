import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;


public class msgSQL {
	SQLServerConnectionPoolDataSource ds;
	public msgSQL(){
		ds = new SQLServerConnectionPoolDataSource();
        ds.setDatabaseName("U3msg");           
        ds.setUser("U3msg");
        ds.setPassword("1234");
        ds.setPortNumber(49172);   
        ds.setURL("jdbc:sqlserver://122.116.189.126");
	}
	public String putMeetingMsg(String meetingId,String account,String msg){
		//meetingId除以20的餘數決定位置
		int id = Integer.parseInt(meetingId);
		int hashId = id % 20;
		try{
			Connection con =ds.getConnection();
			PreparedStatement pst;
			String SQL = "insert into msg"+hashId+" values(?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setInt(1, id);
			pst.setString(2, account);
			pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pst.setString(4, msg);
			pst.execute();
		}catch(Exception ex){
			return "putMeetingMsg:"+ex.toString();
		}
		return "putMeetingMsg成功";
	}
	public ResultSet getMeetingMsg(String meetingId){
		//meetingId除以20的餘數決定位置
		int id = Integer.parseInt(meetingId);
		int hashId = id % 20;
		ResultSet rs = null;
		try{
			Connection con =ds.getConnection();
			PreparedStatement pst;
			String SQL = "select username,msg_time,msg_string from msg"+hashId;
			pst = con.prepareStatement(SQL);
			rs = pst.executeQuery();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rs;
	}
}
