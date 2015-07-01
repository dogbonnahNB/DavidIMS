import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class StockTableModel extends AbstractTableModel {

	private StockLevelFunctions functions = new StockLevelFunctions();
	private final List<Product> productList;
    
    private final String[] columnNames = new String[] {
            "Product ID", "Name", "Product Quantity", "Cost", "Minimum Threshold"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class, Integer.class , Double.class, Integer.class
    };
 
    public StockTableModel(List<Product> productList)
    {
        this.productList = productList;
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
        return productList.size();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if (columnIndex == 2 || columnIndex == 3 || columnIndex == 4) 
        {
        	return true;
        }    
        
        return false;
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Product row = productList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getProductID();
        }
        else if(1 == columnIndex) {
            return row.returnName();
        }
        else if(2 == columnIndex) {
            return row.returnStockLevel();
        }
        else if(3 == columnIndex) {
            return row.returnCost();
        }
        else if(4 == columnIndex) {
            return row.getMinThreshold();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Product row = productList.get(rowIndex);
        if(2 == columnIndex) {
            row.changeStockLevel((Integer) aValue);
            functions.updateStockLevel(row.returnStockLevel(), row.getProductID());
        }
        if(3 == columnIndex) {
            row.changeCost((Double) aValue);
            functions.updateCost(row.returnCost(), row.getProductID());
        }
        if(4 == columnIndex) {
        	row.changeMinThreshold((Integer) aValue);
        	functions.updateMinThreshold(row.getMinThreshold(), row.getProductID());
        }
    }
}
