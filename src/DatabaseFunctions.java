import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseFunctions {
	
	private JDBConnection connect;
	private Statement stmt;
	private ArrayList<Product> productList;
	
	public DatabaseFunctions()
	{
		connect = new JDBConnection();
		productList = new ArrayList<Product>();
	}
	
	public void addProduct(String id, String name, int level, double cost)
	{
		try{
			connect.accessDB();
			System.out.println("Creating statement....");	
		    stmt = connect.getConnection().createStatement();
			
			String command = "INSERT INTO product (ProductID, ProductName, StockLevel, ProductCost)"
					+ " VALUES('" + id + "', '" + name + "', " + level + "," + cost + ")";
			
			stmt.executeUpdate(command);
					
			
			stmt.close();
			connect.closeDB();
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
			connect.accessDB();
			System.out.println("Creating statement...");
			stmt = connect.getConnection().createStatement();
		    String levelAsString = Integer.toString(level);
			String com = "UPDATE product " + "SET StockLevel = " + levelAsString + " where productID= " + productID;
			stmt.executeQuery(com);
			
			stmt.close();
			connect.closeDB();
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
			
			for(Product p: productList)
			{
				String id = p.getProductID();
		        String  name = p.returnName();
		        int level  = p.returnStockLevel();
		        double cost = p.returnCost();
		        
		        pw.println(id + "\t\t\t " + name + "\t\t " + level + "\t\t\t " + cost);
			}
			
			pw.close();
 
			System.out.println("Done");
		}
		catch (IOException se)
		{
			System.err.println( se.getClass().getName() + ": " + se.getMessage());
		    System.exit(0);
		}
		
	}
	
	public void readDB()
	{
		try 
		{
			connect.accessDB();
			stmt = connect.getConnection().createStatement();
			
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM product;" );
		      
		    while ( rs.next() ) 
		    {
		        String id = rs.getString("ProductID");
		        String  name = rs.getString("ProductName");
		        int level  = rs.getInt("StockLevel");
		        double cost = rs.getDouble("ProductCost");
		        
		        Product product = new Product(id, name, level, cost);
		        productList.add(product);
		        
		        System.out.println( "PRODUCT ID = " + id );
		        System.out.println( "PRODUCT NAME = " + name );
		        System.out.println( "STOCK LEVEL = " + level );
		        System.out.println( "COST = " + cost );
		        System.out.println();
		        
		    }
		    
		    rs.close();
		    
		    stmt.close();
		    connect.closeDB();
		} 
		
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		    
		System.out.println("Operation done successfully");
	}
	
}
