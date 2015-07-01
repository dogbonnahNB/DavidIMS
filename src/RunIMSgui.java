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
	StockLevelFunctions functions = new StockLevelFunctions();   

	public RunIMSgui()
	{         
		//build the list
	    functions.readDB();
		List<Product> productList = functions.returnProductList();
			         
	    //create the model
	    StockTableModel model = new StockTableModel(productList);
		
		JTable table = new JTable(model);
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
		addButton.setActionCommand("Add Product");
		pslButton.setActionCommand( "Print Stock List");
		addButton.addActionListener( new BCL());
		pslButton.addActionListener( new BCL());
		addButton.setPreferredSize(new Dimension(200, 80));
		pslButton.setPreferredSize(new Dimension(200, 80));
		controlPanel.add(addButton);
		controlPanel.add(pslButton);
		this.setVisible(true); 
	}
	
	private class BCL implements ActionListener {
		@Override
		public void actionPerformed ( ActionEvent ae) {
		String command = ae.getActionCommand();
		switch (command) {
		case "Add Product": 
		break;
		case "Print Stock List": 
			try {
				functions.printStockLevels();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(returnFrame(), "Stock List has been printed");
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
