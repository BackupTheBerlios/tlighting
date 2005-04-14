/*
 * Created on Apr 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package theatre;

import javax.swing.*;
import java.util.*;
import java.awt.*;
/**
 * @author Ilya Buzharsky
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InventoryManager extends JDialog 
{
	protected JPanel jpInstruments = new JPanel();
	protected JPanel jpInstrumentType = new JPanel();
	
	protected JPanel btnPanel = new JPanel();
	
	protected JComboBox jcType = new JComboBox();
	
	protected int iScreenHeight =BasicWindow.iScreenWidth-50;
	protected int iScreenWidth = BasicWindow.iScreenHeight-100;

	public InventoryManager()
	{
		super(BasicWindow.curWindow,"Inventory Manager", true);
		//setTitle("Inventory Manager");
		addComponents();
		setBounds(10,50, iScreenHeight,iScreenWidth);
		//setSize(500,500);
		setResizable(true);
		setVisible(true);
	}
	public void addComponents()
	{
		JPanel jpMain = new JPanel();
		jpMain.setLayout(null);
		
		createInstrumentTab();
		createInstrumentTypeTab();
		setBtnPanel();
		JTabbedPane tpBulletinBoard = new JTabbedPane(SwingConstants.TOP);
		tpBulletinBoard.addTab("Instruments", jpInstruments);
		tpBulletinBoard.addTab("Instrument Type", jpInstrumentType);
		
		tpBulletinBoard.setBounds(0,0,800,430);
		btnPanel.setBounds(500, 410, 175,75);
		btnPanel.setVisible(true);
		jpMain.add(tpBulletinBoard);
		jpMain.add(btnPanel);
		getContentPane().add(jpMain);
	}
	public void createInstrumentTab()
	{
		jpInstruments = new JPanel();
		jpInstruments.setLayout( null );

		JList jl = new JList();
		jl.setBounds(0,0,200, iScreenHeight-20);
		jpInstruments.add( jl );
		
		JLabel label1 = new JLabel( "Name:" );
		label1.setBounds( 250, 15, 150, 20 );
		jpInstruments.add( label1 );

		JTextField name = new JTextField();
		name.setBounds( 375, 15, 250, 20 );
		jpInstruments.add( name );

		JLabel label2 = new JLabel( "Description:" );
		label2.setBounds( 250, 40, 150, 20 );
		jpInstruments.add( label2 );

		JTextField desc = new JTextField();
		desc.setBounds( 375, 40, 250, 20 );
		jpInstruments.add( desc );
		
		JLabel list = new JLabel( "Type:" );
		list.setBounds( 250, 70, 150, 20 );
		jpInstruments.add( list );

		loadItems();
		jcType.setBounds(375, 70, 250, 20 );
		jpInstruments.add( jcType );
		
		JLabel label3 = new JLabel( "Notes:" );
		label3.setBounds( 250, 100, 150, 20 );
		jpInstruments.add( label3 );

		JTextArea notes = new JTextArea();
		notes.setEditable(true);
		//notes.setBounds( 375, 100, 250, 250 );

		JScrollPane jsNotes = new JScrollPane();
		jsNotes.getViewport().add(notes);
		//JTextArea notes = new JTextArea();
		jsNotes.setBounds( 375, 100, 250, 250 );
		jpInstruments.add( jsNotes );
		
		//btnPanel.setVisible(true);
		//jpInstruments.add( btnPanel);
	}
	public void createInstrumentTypeTab()
	{
		jpInstrumentType = new JPanel();
		jpInstrumentType.setLayout( null );

		JList jl = new JList();
		jl.setBounds(0,0,200, iScreenHeight-20);
		jpInstrumentType.add( jl );
		
		JLabel label1 = new JLabel( "Name:" );
		label1.setBounds( 250, 15, 150, 20 );
		jpInstrumentType.add( label1 );

		JTextField name = new JTextField();
		name.setBounds( 375, 15, 250, 20 );
		jpInstrumentType.add( name );

		JLabel label2 = new JLabel( "Description:" );
		label2.setBounds( 250, 40, 150, 20 );
		jpInstrumentType.add( label2 );

		JTextField desc = new JTextField();
		desc.setBounds( 375, 40, 250, 20 );
		jpInstrumentType.add( desc );
		
		JLabel label3 = new JLabel( "Notes:" );
		label3.setBounds( 250, 100, 150, 20 );
		jpInstrumentType.add( label3 );

		JTextArea notes = new JTextArea();
		notes.setEditable(true);
		//notes.setBounds( 375, 100, 250, 250 );

		JScrollPane jsNotes = new JScrollPane();
		jsNotes.getViewport().add(notes);
		//JTextArea notes = new JTextArea();
		jsNotes.setBounds( 375, 100, 250, 100 );
		jpInstrumentType.add( jsNotes );

		JLabel label4 = new JLabel( "Drawing:" );
		label4.setBounds( 250, 220, 150, 20 );
		jpInstrumentType.add( label4 );

		//btnPanel.setVisible(true);
		//jpInstrumentType.add( btnPanel);
	}
	
	
	
	public void setBtnPanel()
	{
		//btnPanel.setLayout( null );
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

		JButton jbSave = new JButton("Save");
		btnPanel.add(jbSave);
		btnPanel.add(Box.createHorizontalStrut(30));
		JButton jbCanel = new JButton("Cancel");
		btnPanel.add(jbCanel);

	}
	public void loadItems()
	{
		jcType.addItem("Bar");
		jcType.addItem("Light");
	}
	
}
