import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class RunIMSgui extends JFrame
{
	
	private JPanel controlPanel;
	private JTable table; 
	private StockLevelFunctions functions = new StockLevelFunctions(); 
	private StockTableModel model;
	List<Product> productList;

	public RunIMSgui()
	{         
		//build the list
	    functions.readDB();
		productList = functions.returnProductList();
			         
	    //create the model
	    model = new StockTableModel(productList);
		
		table = new JTable(model);
		table.setFont(new Font("SanSerif", Font.PLAIN, 16));
		table.setRowHeight(30);
        
		this.setSize(1280, 720);
		this.setTitle("Inventory Management System");
		this.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.setPreferredSize(new Dimension(1280, 100));
		
        //add the table to the frame
        this.add(new JScrollPane(table), BorderLayout.CENTER); 
        this.add(controlPanel, BorderLayout.PAGE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setVisible(true);
    }
	
	private JFrame returnFrame()
	{
		return this;
	}
	
	private void showEvent() {
		JButton addButton = new JButton("Add Product");
		JButton pslButton = new JButton("Print Stock List");
		JButton poButton = new JButton("Generate Purchase Order");
		addButton.setActionCommand("Add Product");
		pslButton.setActionCommand( "Print Stock List");
		poButton.setActionCommand( "Generate Purchase Order");
		addButton.addActionListener( new BCL());
		pslButton.addActionListener( new BCL());
		poButton.addActionListener( new BCL());
		addButton.setPreferredSize(new Dimension(200, 80));
		pslButton.setPreferredSize(new Dimension(200, 80));
		poButton.setPreferredSize(new Dimension(200, 80));
		controlPanel.add(addButton);
		controlPanel.add(pslButton);
		controlPanel.add(poButton);
		this.setVisible(true); 
	}
	
	private class BCL implements ActionListener {
		@Override
		public void actionPerformed ( ActionEvent ae) {
		String command = ae.getActionCommand();
		switch (command) {
		case "Add Product": 
			String newProductName = "";
			String newStockLeveltemp = "";
			String newCostTemp = "";
			String newMinimumtemp = "";
			String newProductID = (String)JOptionPane.showInputDialog("Please enter the ID of the new product");
			if (!newProductID.equals("")) newProductName = (String)JOptionPane.showInputDialog("Please enter the name of the new product");
			if (!newProductName.equals("")) newStockLeveltemp = JOptionPane.showInputDialog("Please enter the stock level of the new product");
			int newStockLevel = Integer.parseInt(newStockLeveltemp);
			if (!newStockLeveltemp.equals("")) newCostTemp = JOptionPane.showInputDialog("Please enter the cost of the new product");
			double newCost = Double.parseDouble(newStockLeveltemp);
			if (!newCostTemp.equals("")) newMinimumtemp = JOptionPane.showInputDialog("Please enter the minimum of the new product");
			int newMinimum = Integer.parseInt(newMinimumtemp);
			
            int confirmProductName = JOptionPane.showConfirmDialog(null,"Would you like to add the product " + "?","Confirm", JOptionPane.YES_NO_OPTION);
            
            if(confirmProductName == JOptionPane.YES_OPTION){
                Product p = new Product(newProductID, newProductName, newStockLevel, newCost, newMinimum);        
                boolean stockLevelLow = ((StockTableModel)model).add(p);
                if (stockLevelLow) JOptionPane.showConfirmDialog(null,"Stock Level is below minimum. Please reorder!","IMS Warning", JOptionPane.WARNING_MESSAGE);
                
                }else{
                      JOptionPane.showMessageDialog(null, "Request cancelled");
                }
            
		break;
		case "Print Stock List": 
			try {
				functions.printStockLevels();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(returnFrame(), "Stock List has been printed","IMS Message", JOptionPane.PLAIN_MESSAGE);
		break;
		case "Generate Purchase Order": 
			JFrame purchaseList = new PurchaseOrder();
			purchaseList.pack();
			purchaseList.setVisible(true);
		break;
		}}}
	
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	RunIMSgui est = new RunIMSgui();
                est.showEvent();
            }
        });
    }   
}
