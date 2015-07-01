

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnection 
{

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String	DB_URL = "jdbc:mysql://10.50.20.16:3306/nbgardens";
	static final String USER = "root";
	static final String PASS = "netbuilder2015";
	
	private Connection conn;
	
	public JDBConnection()
	{
		
	}
	
	public void accessDB()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
		}
		catch ( Exception e ) 
		{
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		
		System.out.println("Opened database successfully");
	}
	
	public void closeDB()
	{
		try
		{
			if( conn != null)
			{
				conn.commit();
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return conn;
	}
}
