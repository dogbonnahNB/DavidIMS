

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	private void accessDB()
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
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM product;" );
		      
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
		    closeDB();
		} 
		
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		    
		System.out.println("Operation done successfully");
	}
	
	public void updateStockLevel(int level, String productID)
	{
		try
		{
			accessDB();
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
		    String levelAsString = Integer.toString(level);
			String com = "UPDATE product " + "SET StockLevel = " + levelAsString + " where productID= " + productID;
			stmt.executeQuery(com);
			
			conn.commit();
			
			stmt.close();
			closeDB();
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}

	}
	
	public void printStockLevels() throws IOException
	{
		try
		{
			accessDB();
			stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM product;" );
		    
		    File file = new File("stocklevels.txt");
		    
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			while(rs.next())
			{
				
			}
			
			bw.write("");
			bw.close();
 
			System.out.println("Done");
		}
		catch (SQLException se)
		{
			
		}
		
	}
	
	private void closeDB()
	{
		try
		{
			if( conn != null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
