
public class RunDB {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JDBConnection connection = new JDBConnection();
		try
		{
			connection.printStockLevels();
		}
		catch(Exception e)
		{
			
		}
		
	}

}
