
public class Product 
{

	private final String productID;
	private int stockLevel;
	private double cost;
	private String name;
	private static int productIDcounter = 1000;
	
	public Product(String name)
	{
		this.name = name;
		productIDcounter++;
		String idCount = Integer.toString(productIDcounter);
		productID = "NBG" + idCount;
	}
	
	public Product(String name, int stockLevel, int cost, String warehouseLoc)
	{
		this.name = name;
		this.stockLevel = stockLevel;
		this.cost = cost;
		productIDcounter++;
		String idCount = Integer.toString(productIDcounter);
		productID = "NBG" + idCount;
	}
	
	public void changeStockLevel(int level)
	{
		stockLevel = level;		
	}
	
	public int returnStockLevel()
	{
		return stockLevel;
	}
	
	public void changeCost(int cost)
	{
		this.cost = cost;
	}
	
	public double returnCost()
	{
		return cost;
	}
	
	public void changeName(String name)
	{
		this.name = name;		
	}
	
	public String returnName()
	{
		return name;
	}
	
	public String getProductID()
	{
		return productID;
	}
	
}
