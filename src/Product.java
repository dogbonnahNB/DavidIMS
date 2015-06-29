
public class Product 
{

	private final String productID;
	private int stockLevel;
	private double cost;
	private String name;
	
	public Product(String id, String name, int stockLevel, double cost)
	{
		this.name = name;
		this.stockLevel = stockLevel;
		this.cost = cost;
		this.productID = id;
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
