

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBConnection 
{

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String	DB_URL = "jdbc:mysql://localhost:3306/nbgardens";
	static final String USER = "root";
	static final String PASS = "netbuilder2015";
	
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
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM product;" );
		      
		    while ( rs.next() ) 
		    {
		        String id = rs.getString("ProductID");
		        String  name = rs.getString("ProductName");
		        int level  = rs.getInt("StockLevel");
		        double cost = rs.getDouble("ProductCost");
		        
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
	
	public void addProduct(String id, String name, int level, double cost)
	{
		try{
			accessDB();
			conn.setAutoCommit(false);
			System.out.println("Creating statement....");	
		    stmt = conn.createStatement();
			
			String command = "INSERT INTO product (ProductID, ProductName, StockLevel, ProductCost)"
					+ " VALUES('" + id + "', '" + name + "', " + level + "," + cost + ")";
			
			stmt.executeUpdate(command);
					
			conn.commit();
			
			stmt.close();
			closeDB();
		}
		catch(SQLException e)
		{
			
		}
		
		System.out.println("Database updated successfully");
		
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
			PrintWriter pw = new PrintWriter(fw);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
			Date date = new Date();
			String date1 =  dateFormat.format(date);
			
			pw.println("\t\t\t" + date1 + "Report");
			pw.println("Product ID \t\t Product Name \t\t Stock Level \t\t ProductCost");
			
			while(rs.next())
			{
				String id = rs.getString("ProductID");
		        String  name = rs.getString("ProductName");
		        int level  = rs.getInt("StockLevel");
		        double cost = rs.getDouble("ProductCost");
		        
		        pw.println(id + "\t\t\t " + name + "\t\t " + level + "\t\t\t " + cost);
			}
			
			pw.close();
			
			closeDB();
 
			System.out.println("Done");
		}
		catch (SQLException se)
		{
			
		}
		
	}
	
	public void closeDB()
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
