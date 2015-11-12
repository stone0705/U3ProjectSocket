import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;


public class voteSQL {
	SQLServerConnectionPoolDataSource ds;
	public voteSQL(){
		ds = new SQLServerConnectionPoolDataSource();
        ds.setDatabaseName("U3vote");           
        ds.setUser("U3vote");
        ds.setPassword("1234");
        ds.setPortNumber(49172);   
        ds.setURL("jdbc:sqlserver://114.35.125.242");
	}
	public String putVoteOption(String voteId,String content){
		//Id除以20的餘數決定位置
		int id = Integer.parseInt(voteId);
		int hashId = id % 20;
		try{
			Connection con =ds.getConnection();
			PreparedStatement pst;
			String SQL = "insert into vote"+hashId+"(vote_id,vote_string,number) values(?,?,0)";
			pst = con.prepareStatement(SQL);
			pst.execute();
		}catch(Exception ex){
			return "putVote:"+ex.toString();
		}
		return "putVote成功";
	}
	public void userVote(String voteId,String option_id){
		int id = Integer.parseInt(voteId);
		int hashId = id % 20;
		try{
			Connection con =ds.getConnection();
			PreparedStatement pst;
			String SQL = "update vote"+hashId+" set number = number + 1 where vote_id = ? and option_id = ?";
			pst = con.prepareStatement(SQL);
			pst.setInt(1, id);
			pst.setInt(2, Integer.parseInt(option_id));
			pst.execute();
		}catch(Exception ex){
			System.out.println(ex.getStackTrace());
		}
	}
	public ResultSet getAlloption(String voteId){
		int id = Integer.parseInt(voteId);
		int hashId = id % 20;
		ResultSet rs = null;
		try{
			Connection con =ds.getConnection();
			PreparedStatement pst;
			String SQL = "select Id,vote_string,number from vote"+hashId+" where vote_id = ?";
			pst = con.prepareStatement(SQL);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rs;
	}
}
