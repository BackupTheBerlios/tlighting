package theatre;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import Data_Storage.*;

public class ExplorerBrowser extends JPanel  implements MouseListener//,ActionListener,KeyListener
{
	protected TreePath tpPath;
	protected String sAttachToPoint;
	protected DefaultMutableTreeNode attachToNode;
	protected DefaultTreeModel treeModel;

	protected javax.swing.JTree trStructure = new javax.swing.JTree(new Object [0]);
	protected javax.swing.JScrollPane scrTree = new javax.swing.JScrollPane(trStructure);

	protected Font font = new Font("Dialog", Font.PLAIN, 10);
	protected Vector _existingNode = new Vector();
	
	public final String sRootName = "Items"; 
	public final String sSubRoot1 = "Bars";
	public final String sSubRoot2 = "Instruments";
	public final String sSubRoot3 = "Inventory";
	
	public static Object oClass = null;
	public project p;
	public Vector vInventory = new Vector();

	public ExplorerBrowser(project proj)
	{
		super();
		// get the current project state
		p = proj;
		
		setLayout(new BorderLayout());

		trStructure.setShowsRootHandles(true);
		trStructure.setRootVisible(false);
		trStructure.setVisibleRowCount(5);
		trStructure.setFont(font);
		trStructure.addMouseListener(this);

		scrTree.setOpaque(true);
		setSize(BasicWindow.iScreenWidth/5-20,BasicWindow.iScreenHeight-BasicWindow.iScreenHeight/5);
		add(scrTree, BorderLayout.CENTER);
		setVisible(true);
		// set the instance of this object, needed for interaction
		oClass = this;
	}
	void loadStructureTopPoint()
	{
		//Create new tree root
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(sRootName);
		//Create new tree model
		treeModel = new DefaultTreeModel(root);
		//Assign root to model
		treeModel.setRoot(root);
		//Assign model to tree
		trStructure.setModel(treeModel);

		//Set tree selection
		trStructure.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//Set root visible
		trStructure.setRootVisible(true);
		trStructure.setCellRenderer(new MyRenderer());
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		vInventory.add("Object1");
		vInventory.add("Object2");
		vInventory.add("Object3");
		vInventory.add("Object4");
		
		// load children
		reloadChildren();
	}

	public void mouseClicked(java.awt.event.MouseEvent event)
	{
		Object object = event.getSource();
		if (object == trStructure)
			trStructure_mouseClicked(event);
	}
	public void mouseEntered(java.awt.event.MouseEvent event) {}
	public void mouseExited(java.awt.event.MouseEvent event) {}
	public void mousePressed(java.awt.event.MouseEvent event) {}
	public void mouseReleased(java.awt.event.MouseEvent event) {}

	public Vector getExistingNode()
	{
		return _existingNode;
	}
	public void setExistingNode(Vector vExists)
	{
		_existingNode=vExists;
	}
	public void reloadChildren()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(sRootName);
		//Create new tree model
		treeModel = new DefaultTreeModel(root);
		//Assign root to model
		treeModel.setRoot(root);

		String sNodeName=root.getUserObject().toString();
		DefaultMutableTreeNode parent;
		DefaultMutableTreeNode child;
		parent = new DefaultMutableTreeNode(sSubRoot3);
		treeModel.insertNodeInto(parent, root, 0);
		for (int i = vInventory.size(); i > 0 ; i--)
		{
			// create inventory leaf node 
			child = new DefaultMutableTreeNode(vInventory.elementAt(i-1).toString());
			treeModel.insertNodeInto(child, parent, 0);
		}
System.out.println("size: "+p.bars.object_list.size());
		// needed to add Bar folder only once
		boolean bAddedBar = false;
		// needed to add instrument folder only once
		boolean bAddedIns = false;
		// set parent 
		parent = new DefaultMutableTreeNode(sSubRoot1);
		for (int i = p.bars.object_list.size(); i > 0 ; i--)
		{
			if (p.bars.object_list.elementAt(i-1) instanceof bar)
			{
				if (!bAddedBar)
				{
					// create bar folder
					treeModel.insertNodeInto(parent, root, 0);
					bAddedBar = true;
				}
				bar b = (bar)p.bars.object_list.elementAt(i-1);
				// create bar leaf node with bar id
				child = new DefaultMutableTreeNode(b.getID().toString());
				treeModel.insertNodeInto(child, parent, 0);
			}
		}
		parent = new DefaultMutableTreeNode(sSubRoot2);
		for (int i = p.bars.object_list.size(); i > 0 ; i--)
		{
			if (p.bars.object_list.elementAt(i-1) instanceof instrument)
			{
				System.out.println("Instrument");
				if (!bAddedIns)
				{
					// create instrument folder
					treeModel.insertNodeInto(parent, root, 0);
					bAddedIns = true;
				}
				instrument ins = (instrument)p.bars.object_list.elementAt(i-1);
				// create instrument leaf node with bar id
				child = new DefaultMutableTreeNode(ins.getName());
				treeModel.insertNodeInto(child, parent, 0);
			}
		}
		// set the new model
		trStructure.setModel(treeModel);
		trStructure.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		trStructure.setRootVisible(true);
	}
	void trStructure_mouseClicked(java.awt.event.MouseEvent event)
	{
	}
    public void insertNewTreeNode()
    {
    	// this is used by other classes to create new leaf nodes
		reloadChildren();
    }
	public class MyRenderer extends DefaultTreeCellRenderer {
		ImageIcon leafIcon, openIcon, closedIcon;

		public MyRenderer() {
			leafIcon = getTreeImage("Leaf.gif");
			openIcon = getTreeImage("Open.gif");
			closedIcon = getTreeImage("Closed.gif");
		}

		public ImageIcon getTreeImage(String imageName)
		{
			ImageIcon icon = null;

			java.net.URL u = ClassLoader.getSystemResource(imageName);

			if (u == null)
				u = getClass().getResource(imageName);
			if (u != null)
				icon = new ImageIcon(u);

			return icon;
		}

		public Component getTreeCellRendererComponent(JTree tree,
													  Object value,
													  boolean sel,
													  boolean expanded,
													  boolean leaf,
													  int row,
													  boolean hasFocus)
	   {

			super.getTreeCellRendererComponent(tree, value, sel,
											   expanded, leaf, row,
											   hasFocus);
			if (value.toString().substring(value.toString().length()-1).equalsIgnoreCase("D"))
			{
				setIcon(leafIcon);
			}
			else
			{
				if (expanded)
					setIcon(openIcon);
				else
					setIcon(closedIcon);
			}
			String node = (String)((DefaultMutableTreeNode)value).getUserObject();
			if (_existingNode.contains(node))
	   			setForeground(Color.red);
			return this;
		}
	}
}