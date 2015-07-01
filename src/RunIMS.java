import java.io.IOException;
import java.util.Scanner;

public class RunIMS {

	private StockLevelFunctions functions = new StockLevelFunctions();
	private Scanner reader = new Scanner(System.in);
    // got rid of empty constructor
    
    public void runIO()
    {        
        int choice = getOption();        
        while (choice != 5)
        {            
            // process choice
            if      (choice == 1){printAllProducts();}
            else if (choice == 2){updateStockLevel();}
            else if (choice == 3){saveReport();}
            else if (choice == 4){addProduct();}            
            // output menu & get choice
            choice = getOption();
        }
        System.out.println("Thank you");
    }   

	private int getOption()
    {
       System.out.println("What would you like to do ?");
       System.out.println("1. List Products");
       System.out.println("2. Update Stock Level");
       System.out.println("3. Save Stock Level to File");
       System.out.println("4. Add Product");
       System.out.println("5. Quit");
       System.out.println("Enter your choice");
       // read choice
       int option = reader.nextInt();
       reader.nextLine();
       System.out.println("");
       return option;
    }
    
    private void printAllProducts()
    {
    	functions.printAllProducts();
    	System.out.println("");
    }
    
    private void updateStockLevel()
    {
        System.out.println("Enter Stock Quantity");
        int level = reader.nextInt();
        reader.nextLine();
        System.out.println("Enter Product ID");
        String id = reader.nextLine();
        functions.updateStockLevel(level, id);
        System.out.println("");
    }
    
    private void addProduct() {
		// TODO Auto-generated method stub
    	System.out.println("Enter Unique ID");
        String id = reader.nextLine();
        System.out.println("Enter Product Name");
        String name = reader.nextLine();
        System.out.println("Enter Stock Level");
        int level = reader.nextInt();
        reader.nextLine();
        System.out.println("Enter cost");
        double cost = reader.nextDouble();
        reader.nextLine();
        System.out.println("Enter the products minimum threshold");
        int thresh = reader.nextInt();
        reader.nextLine();
        
        functions.addProduct(id, name, level, cost, thresh);
        System.out.println("");
	}

	private void saveReport() {
		// TODO Auto-generated method stub
    	try
    	{
    		functions.printStockLevels();
    		System.out.println("");
    	}
    	catch(IOException e)
    	{
    		
    	}
		
	}

}
