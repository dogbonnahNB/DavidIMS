
public class Product 
{

	private final String productID;
	private int stockLevel;
	private double cost;
	private String name;
	private int minimumThreshold;
	
	public Product(String id, String name, int stockLevel, double cost, int threshold)
	{
		this.name = name;
		this.stockLevel = stockLevel;
		this.cost = cost;
		this.productID = id;
		this.minimumThreshold = threshold;
	}
	
	public void changeStockLevel(int level)
	{
		stockLevel = level;		
	}
	
	public int returnStockLevel()
	{
		return stockLevel;
	}
	
	public void changeCost(double cost)
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
	
	public void changeMinThreshold(int min)
	{
		minimumThreshold = min;
	}
	
	public int getMinThreshold()
	{
		return minimumThreshold;
	}
	
	public boolean isBelowThreshold()
	{
		if(stockLevel < minimumThreshold)
		{ 
			System.out.println(returnName() + " stock is low, please reorder!");
			return true;
		}
		else
		{
			return false;
		}
	}
}
