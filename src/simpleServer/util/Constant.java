package simpleServer.util;

public final class Constant {
	
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	private Constant(){}
	
	public static int getMinServerPort(){
		return Ports.getInstance().getMinServerPort();
	}
	
	public static int getMaxServerPort(){
		return Ports.getInstance().getMaxServerPort();
	}
	
	public static void updateDatabaseDriver(final String driver){
		Constant.JDBC_DRIVER = driver;
	}
	
	public static String getDatabaseDriver(){
		return Constant.JDBC_DRIVER;
	}
}
