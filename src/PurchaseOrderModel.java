import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PurchaseOrderModel extends AbstractTableModel {

	private List<Product> purchaseOrderList;
	boolean stockLevelLow = false;
	String stockLevelLowName = "";
    
    private final String[] columnNames = new String[] {
            "Product ID", "Name", "Order Quantity", "Cost per Item", "Total Cost"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class, Integer.class , Double.class, Double.class
    };
 
    public PurchaseOrderModel(List<Product> productList)
    {
        this.purchaseOrderList = productList;
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
 
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
 
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount()
    {
        return purchaseOrderList.size();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if (columnIndex == 2) 
        {
        	return true;
        }    
        
        return false;
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Product row = purchaseOrderList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getProductID();
        }
        else if(1 == columnIndex) {
            return row.returnName();
        }
        else if(2 == columnIndex) {
        	return row.orderQuantity();
        }
        else if(3 == columnIndex) {
            return row.returnCost();
        }
        else if(4 == columnIndex) {
            double total = row.returnCost() * row.orderQuantity();
            return total;
        }
        return null;
    }     
}
