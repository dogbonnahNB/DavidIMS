

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBConnection 
{

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String	DB_URL = "jdbc://localhost/nbgschema";
	static final String USER = "username";
	static final String PASS = "password";
	
	private Connection conn;
	private Statement stmt;
	
	public JDBConnection()
	{
		
	}
	
	public void accessDB()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		catch ( Exception e ) 
		{
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		
		System.out.println("Opened database successfully");
	}
	
	public void readDB()
	{
		try 
		{
			accessDB();
		   	conn.setAutoCommit(false);

		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCT;" );
		      
		    while ( rs.next() ) 
		    {
		        String id = rs.getString("id");
		        String  name = rs.getString("name");
		        int level  = rs.getInt("level");
		        double cost = rs.getDouble("cost");
		        
		        System.out.println( "PRODUCT ID = " + id );
		        System.out.println( "PRODUCT NAME = " + name );
		        System.out.println( "STOCK LEVEL = " + level );
		        System.out.println( "COST = " + cost );
		        System.out.println();
		        
		    }
		    
		    rs.close();
		    stmt.close();
		    conn.close();
		} 
		
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		    
		System.out.println("Operation done successfully");
	}
}
