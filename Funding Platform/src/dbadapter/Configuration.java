package dbadapter;

/**
 * This class is used to declare access data for the SQL server
 * 
 */

public class Configuration {

	
	private static final String SERVER = "localhost";
	private static final String TYPE = "mysql";
	private static final int PORT = 3306;
	private static final String DATABASE = "FundingPlatform";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static String getServer() {
		return SERVER;
	}

	public static String getType() {
		return TYPE;
	}

	public static int getPort() {
		return PORT;
	}

	public static String getDatabase() {
		return DATABASE;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

}
