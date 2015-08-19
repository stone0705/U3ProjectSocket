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
	public String getNickName(String account){
		try{
			PreparedStatement pst;
			String SQL = "SELECT nick_name from theuser where name = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, account);
			ResultSet rs = pst.executeQuery();
			rs.next();
			return rs.getString(1);
		}catch(Exception ex){
			System.out.println(ex);
		}
		return "";
	}
	public boolean isAddPermit(String account,String group,String founder){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT add_permit from group_user where u_name = ? and g_name = ? and g_founder = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, account);
			pst.setString(2, group);
			pst.setString(3, founder);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				exist = rs.getBoolean(1);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return exist;
	}
	public boolean isGroupExist(String founder,String group){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT * from thegroup where name = ? and founder = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, founder);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				exist = true;
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return exist;
	}
	public boolean isAddInGroup(String account,String group,String founder){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT isjoin from group_user where g_name = ? and g_founder = ? and u_name = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, founder);
			pst.setString(3, account);
			ResultSet rs = pst.executeQuery();
			rs.next();
			exist = rs.getBoolean(1);
		}catch(Exception ex){
			System.out.println("isInGroup:"+ex.toString());
		}
		return exist;
	}
	public boolean isInGroup(String account,String group,String founder){
		boolean exist = false;
		try{
			PreparedStatement pst;
			String SQL = "SELECT * from group_user where g_name = ? and g_founder = ? and u_name = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, founder);
			pst.setString(3, account);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				exist = true;
			}
		}catch(Exception ex){
			System.out.println("isInGroup:"+ex.toString());
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
		String SQL;
		try {
			SQL = "select a.id,a.title,a.start_time,a.end_time "
					+ "from meeting a join group_user b on a.g_name = b.g_name "
					+ "where a.g_founder = ? and a.g_name = ? and b.u_name = ? "
					+ "order by start_time DESC ";
			pst = con.prepareStatement(SQL);
			pst.setString(1, g_founder);
			pst.setString(2, g_name);
			pst.setString(3, name);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet searchGroup(String text){
		ResultSet rs = null;
		PreparedStatement pst;
		String SQL = "select * from thegroup where name like ? escape ?";
		String param = text.replace("!","!!" );
		param = param.replace("%", "!%");
		param = param.replace("_", "!_");
		param = "%" + param + "%";
		try{
			pst = con.prepareStatement(SQL);
			pst.setString(1, param);
			pst.setString(2, "!");
			rs = pst.executeQuery();
			return rs;
		}catch(Exception ex){
			System.out.println("SQL searchGroup:"+ex.toString());
		}
		return rs;
	}
	public ResultSet selectGroup(String account){
		ResultSet rs = null;
		PreparedStatement pst;
		String SQL = "select distinct g_name,founder,number,description "
				+ "from group_user a join thegroup b on a.g_name = b.name and a.g_founder = b.founder "
				+ "where u_name = ?";
		try{
			pst = con.prepareStatement(SQL);
			pst.setString(1, account);
			rs = pst.executeQuery();
		}catch(Exception ex){
			System.out.println("selectGroup SQL:"+ex.toString());
		}
		return rs;
	}
	public void createGroup(String account,String nickname,String group){
		try{
			PreparedStatement pst;
			String SQL = "INSERT into thegroup values(?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, account);
			pst.setInt(3, 1);
			pst.setString(4, "");
			pst.execute();
			long timeInMillis = System.currentTimeMillis();
	        Timestamp ts = new Timestamp(timeInMillis);
			SQL = "INSERT into group_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, account);
			pst.setString(3, account);
			pst.setString(4, nickname);
			pst.setBoolean(5, true);
			pst.setBoolean(6, true);
			pst.setBoolean(7, true);
			pst.setBoolean(8, true);
			pst.setBoolean(9, true);
			pst.setBoolean(10, true);
			pst.setBoolean(11, true);
			pst.setBoolean(12, true);
			pst.setTimestamp(13, ts);
			pst.execute();
		}catch(Exception ex){	
			System.out.println(ex.toString());
		}
	}
	public void applyGroup(String account,String nickname,String group,String founder){
		try{
			PreparedStatement pst;
			long timeInMillis = System.currentTimeMillis();
	        Timestamp ts = new Timestamp(timeInMillis);
			String SQL = "INSERT into group_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, founder);
			pst.setString(3, account);
			pst.setString(4, nickname);
			pst.setBoolean(5, false);
			pst.setBoolean(6, false);
			pst.setBoolean(7, false);
			pst.setBoolean(8, false);
			pst.setBoolean(9, false);
			pst.setBoolean(10, false);
			pst.setBoolean(11, false);
			pst.setBoolean(12, false);
			pst.setTimestamp(13, ts);
			pst.execute();
			SQL = "update thegroup set number = number + 1 where name = ? and founder = ?";
			pst = con.prepareStatement(SQL);
			pst.setString(1, group);
			pst.setString(2, founder);
			pst.execute();
		}catch(Exception ex){
			System.out.println("applyGroup:"+ex.toString());
		}
	}
	public static ResultSet getIsJoinMember(String name,String founder){
		ResultSet rs = null;
		PreparedStatement pst;
		String SQL = "select u_name,u_nick_name,add_permit,remove_permit,note_permit,meeting_permit"
				+ ",vote_permit,schdule_permit,isfounder,enter_time from group_user "
				+ "where g_name = ? and g_founder = ? and isjoin = ?";
		try{
			pst = con.prepareStatement(SQL);
			pst.setString(1, name);
			pst.setString(2, founder);
			pst.setBoolean(3, true);
			rs = pst.executeQuery();
		}catch(Exception ex){
			System.out.println("getIsJoinMember SQL:"+ex.toString());
		}
		return rs;
	}
	public static ResultSet getNotJoinMember(String name,String founder){
		ResultSet rs = null;
		PreparedStatement pst;
		String SQL = "select u_name,u_nick_name,enter_time from group_user "
				+ "where g_name = ? and g_founder = ? and isjoin = ?";
		try{
			pst = con.prepareStatement(SQL);
			pst.setString(1, name);
			pst.setString(2, founder);
			pst.setBoolean(3, false);
			rs = pst.executeQuery();
		}catch(Exception ex){
			System.out.println("getIsJoinMember SQL:"+ex.toString());
		}
		return rs;
	}
	public void letHeIn(String group,String founder,String u_name){
		try{
			PreparedStatement pst;
			String SQL = "update group_user set isjoin = ? where g_name = ? and g_founder = ? and u_name = ?";
			pst = con.prepareStatement(SQL);
			pst.setBoolean(1, true);
			pst.setString(2, group);
			pst.setString(3, founder);
			pst.setString(4, u_name);
			pst.execute();
		}catch(Exception ex){
			System.out.println("LetHeIn SQL:"+ex.toString());
		}
	}
}
