
public class RunDB {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseFunctions functions = new DatabaseFunctions();
		try
		{
			functions.readDB();
			functions.printStockLevels();
		}
		catch(Exception e)
		{
			
		}
		
	}

}
