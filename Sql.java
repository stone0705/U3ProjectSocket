import java.sql.*;
public class Sql {
	static Connection con;
	public Sql(){
		try{
			String connectionUrl = "jdbc:sqlserver://122.116.189.126:49172;"+"databaseName=U3project;user=U3sa;password=1234;";
			con = DriverManager.getConnection(connectionUrl);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isAccountExist(String name){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT * from theuser where name = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				exist = true;
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return exist;
	}
	public String[] loginSql(String name){
		String[] answer = new String[2];
		PreparedStatement pst;
		String SQL = "SELECT password,salt from theuser where name = ?";
		try{
			pst = con.prepareStatement(SQL);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			rs.next();
			answer[0] = rs.getString(1);
			answer[1] = rs.getString(2);
		}catch(Exception ex){			
		}
		return answer;
	}
	public void submitSql(String name,String hash,String salt,String nick_name){
		boolean answer = true;
		try{
			PreparedStatement pst;
			String SQL = "INSERT into theuser values(?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, name);
			pst.setString(2, hash);
			pst.setString(3, salt);
			pst.setString(4, nick_name);
			pst.execute();
		}catch(Exception ex){			
		}
	}
	public ResultSet getMeetingSql(String name,String g_name,String g_founder){
		ResultSet rs = null;
		PreparedStatement pst;
		String SQL = "select * "
				+ "from theuser a join group_user b on a.name = b.u_name "
				+ "where b.g_name = ? and b.g_founder = ? and a.name = ?";
		try {
			pst = con.prepareStatement(SQL);
			pst.setString(1, g_name);
			pst.setString(2, g_founder);
			pst.setString(3, name);
			rs = pst.executeQuery();
			if(rs.next()){
				SQL = "select a.id,a.title,a.start_time,a.end_time,b.isjoin "
						+ "from meeting a join group_user b on a.g_name = b.g_name "
						+ "where a.g_founder = ? and a.g_name = ? "
						+ "order by start_time DESC ";
				pst = con.prepareStatement(SQL);
				pst.setString(1, g_founder);
				pst.setString(2, g_name);
				rs = pst.executeQuery();
			}else{
				rs = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rs;
	}
}
