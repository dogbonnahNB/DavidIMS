import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StockLevelFunctions {
	
	private JDBConnection connect;
	private Statement stmt;
	private ArrayList<Product> productList;
	private static int stockReportCount = 0;
	
	public StockLevelFunctions()
	{
		connect = new JDBConnection();
		productList = new ArrayList<Product>();
	}
	
	public void addProduct(String id, String name, int level, double cost, int thresh)
	{
		try{
			connect.accessDB();
			System.out.println("Creating statement....");	
		    stmt = connect.getConnection().createStatement();
			
			String command = "INSERT INTO product (ProductID, ProductName, StockLevel, ProductCost, MinimumThreshold)"
					+ " VALUES('" + id + "', '" + name + "', " + level + "," + cost + ", " + thresh + ")";
			
			stmt.executeUpdate(command);
					
			
			stmt.close();
			connect.closeDB();
		}
		catch(SQLException e)
		{
			
		}
		
		System.out.println("Product Added successfully");
		
	}
	
	public void updateStockLevel(int level, String productID)
	{
		Product p = null;
		
		readDB();
		p = findProductByID(productID);
				
		try
		{
			connect.accessDB();
			System.out.println("Creating statement...");
			stmt = connect.getConnection().createStatement();
		    String levelAsString = Integer.toString(level);
			String com = "UPDATE product " + "SET StockLevel = " + levelAsString + " where ProductID= '" + productID + "'";
			stmt.executeUpdate(com);
			
			stmt.close();
			connect.closeDB();			
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		
		p.isBelowThreshold();
		System.out.println(p.returnName() + " stock level updated");
		
	}
	
	public void updateMinThreshold(int thresh, String productID)
	{
		Product p = null;
		
		readDB();
		p = findProductByID(productID);
				
		try
		{
			connect.accessDB();
			System.out.println("Creating statement...");
			stmt = connect.getConnection().createStatement();
		    String threshAsString = Integer.toString(thresh);
			String com = "UPDATE product " + "SET MinimumThreshold = " + threshAsString + " where ProductID= '" + productID + "'";
			stmt.executeUpdate(com);
			
			stmt.close();
			connect.closeDB();			
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		
		p.isBelowThreshold();
		System.out.println(p.returnName() + "'s minimum threshold is updated");
		
	}

	public void updateCost(double cost, String productID)
	{
		Product p = null;
		
		readDB();
		p = findProductByID(productID);
				
		try
		{
			connect.accessDB();
			System.out.println("Creating statement...");
			stmt = connect.getConnection().createStatement();
		    String costAsString = Double.toString(cost);
			String com = "UPDATE product " + "SET MinimumThreshold = " + costAsString + " where ProductID= '" + productID + "'";
			stmt.executeUpdate(com);
			
			stmt.close();
			connect.closeDB();			
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}

		System.out.println(p.returnName() + "'s cost is updated");
		
	}
	
	
	public void printStockLevels() throws IOException
	{
		if(stockReportCount == 7) {stockReportCount = 0;}
		stockReportCount++;
		readDB();
		
		FileWriter fw = new FileWriter("stocklevels" + stockReportCount +".txt");
		
		try
		{
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
			Date date = new Date();
			String date1 =  dateFormat.format(date);
			
			String report = "";
			
			report += "\t\t\t" + date1 + " Report  \r\n";
			report += "Product ID \t\t Product Name \t\t Stock Level \t\t ProductCost \r\n";
			
			for(Product p: productList)
			{
				String id = p.getProductID();
		        String  name = p.returnName();
		        int level  = p.returnStockLevel();
		        double cost = p.returnCost();
		        
		        report += id + "\t\t\t " + name + "\t\t " + level + "\t\t\t " + cost + "\r\n";
			}
			
			fw.write(report);
			fw.close();
 
			System.out.println("Product List Printed");
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
			productList.clear();
			
			connect.accessDB();
			stmt = connect.getConnection().createStatement();
			
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM product;" );
		      
		    while ( rs.next() ) 
		    {
		        String id = rs.getString("ProductID");
		        String  name = rs.getString("ProductName");
		        int level  = rs.getInt("StockLevel");
		        double cost = rs.getDouble("ProductCost");
		        int threshold = rs.getInt("MinimumThreshold");
		        
		        Product product = new Product(id, name, level, cost, threshold);
		        productList.add(product);
		        
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
		    
		System.out.println("Product List Updated");
	}
	
	public void printAllProducts()
	{
		readDB();
		for(Product p : productList)
		{
			 System.out.println( "PRODUCT ID = " + p.getProductID() );
		     System.out.println( "PRODUCT NAME = " + p.returnName() );
		     System.out.println( "STOCK LEVEL = " + p.returnStockLevel() );
		     System.out.println( "COST = " + p.returnCost() );
		     System.out.println("");
		}
	}
	
	private Product findProductByID(String id)
	{
		Product p = null;
		int index = 0;
		while(index < productList.size())
		{
			p = productList.get(index);
			if(id.equals(p.getProductID()))
			{
				System.out.println(p.returnName() + " found");
				return p;		
			}			
			index++;
		}
		
		return null;
	}
	
	public ArrayList<Product> returnProductList()
	{
		return productList;
	}
	
}
