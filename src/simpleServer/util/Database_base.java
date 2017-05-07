package simpleServer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;


public abstract class Database_base {

	private Connection con;
	private String DB_URL;
	
	public Database_base(final String db_url){
		if(db_url == null || db_url.isEmpty())
			throw new IllegalArgumentException();
		
		this.DB_URL = db_url;
		this.con = null;
	}
	
	public void openConnection(final String USER, final String PASS) throws ClassNotFoundException, SQLException {
		
		Objects.requireNonNull(USER);
		Objects.requireNonNull(PASS);
	
		Class.forName(Constant.getDatabaseDriver());
		
		con = DriverManager.getConnection(this.DB_URL, USER, PASS);
	}
	
	public void close() throws SQLException{
		con.close();
	}
	
	protected Connection getConnection(){
		return this.con;
	}
}
