import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class PurchaseOrder extends JFrame
{
	
	private JPanel controlPanel;
	private JTable table; 
	private StockLevelFunctions functions = new StockLevelFunctions(); 
	private PurchaseOrderModel model;
	private ArrayList<Product> productList;
	
	public PurchaseOrder()
	{         
		//build the list
	    functions.readDB();
	    productList = functions.returnProductList();
			         
	    //create the model
	    model = new PurchaseOrderModel(getLowStock());
		
		table = new JTable(model);
		table.setFont(new Font("SanSerif", Font.PLAIN, 12));
		table.setRowHeight(20);
        
		this.setSize(1280, 720);
		this.setTitle("Purchase Order");
		this.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.setPreferredSize(new Dimension(500, 100));
		
        //add the table to the frame
        this.add(new JScrollPane(table), BorderLayout.CENTER); 
        this.add(controlPanel, BorderLayout.PAGE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setVisible(true);
        
        showEvent();
    }
	
	private JFrame returnFrame()
	{
		return this;
	}
	
	private void showEvent() {
		JButton addButton = new JButton("OK");
		addButton.setActionCommand("OK");
		addButton.addActionListener( new BCL());
		addButton.setPreferredSize(new Dimension(100, 40));

		controlPanel.add(addButton);
		this.setVisible(true); 
	}
	
	private ArrayList<Product> getLowStock()
	{
		ArrayList<Product> purchaseOrderList = new ArrayList<Product>();
		for(Product p : productList)
		{
			if (p.returnStockLevel() < p.getMinThreshold())
			{
				purchaseOrderList.add(p);
			}
		}
		
		return purchaseOrderList;
	}
	
	private class BCL implements ActionListener {
		@Override
		public void actionPerformed ( ActionEvent ae) {
		String command = ae.getActionCommand();
		switch (command) {
		case "OK": 
			JOptionPane.showMessageDialog(returnFrame(), "Purchase Order sent");
			returnFrame().setVisible(false);
		break;
		
		}}}
}
