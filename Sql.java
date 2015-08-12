import java.sql.*;
public class Sql {
	static Connection con;
	public Sql(){
		try{
			String connectionUrl = "jdbc:sqlserver://122.116.189.126:49172;"+"databaseName=U3project;user=U3sa;password=1234;";
			con = DriverManager.getConnection(connectionUrl);
			/*Class.forName("com.mysql.jdbc.Driver");
		      con = DriverManager.getConnection( 
		    	      "jdbc:mysql://127.0.0.1:3306/U3project?useUnicode=true", 
		    	      "stone","Abcd1234"); */
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
	public boolean isGroupExist(String account,String group){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT * from thegroup where name = ? and founder = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, account);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				exist = true;
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return exist;
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
	public String getAndroidId(String account){
		String id = "";
		try{
			PreparedStatement pst;
			String SQL = "SELECT android_id from saveuser where u_name = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, account);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				id = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return id;
	}
	public boolean compareAndroidID(String account,String android_id){
		String SQLandroid_id = getAndroidId(account);
		if(SQLandroid_id.equals(android_id)){
			return true;
		}else{
			return false;
		}
	}
	public void insertSaveuser(String account,String android_id){
		String sqlid = getAndroidId(account);
		if(sqlid.equals("")){
			try{
				PreparedStatement pst;
				String SQL = "INSERT into saveuser values(?,?)";
				pst = con.prepareStatement(SQL);
				pst.setString(1, account);
				pst.setString(2, android_id);
				pst.execute();
			}catch(Exception ex){
				System.out.println(ex);
			}
		}else{
			try{
				PreparedStatement pst;
				String SQL = "update saveuser set android_id = ? where u_name = ?";
				pst = con.prepareStatement(SQL);
				pst.setString(1, android_id);
				pst.setString(2, account);
				pst.execute();
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
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
	public void createGroup(String account,String group){
		try{
			PreparedStatement pst;
			String SQL = "INSERT into thegroup values(?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, account);
			pst.setInt(3, 1);
			pst.execute();
			SQL = "INSERT into group_user values(?,?,?,?,?,?,?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, account);
			pst.setString(3, account);
			pst.setBoolean(4, true);
			pst.setBoolean(5, true);
			pst.setBoolean(6, true);
			pst.setBoolean(7, true);
			pst.setBoolean(8, true);
			pst.setBoolean(9, true);
			pst.setBoolean(10, true);
			pst.execute();
		}catch(Exception ex){	
			System.out.println(ex.toString());
		}
	}
}
