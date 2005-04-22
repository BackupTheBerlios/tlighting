package theatre;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import Data_Storage.*;
import drawing_prog.*;

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
    
    public final String sSubStage = "Stage";
    
    public final String sSubHouse = "House";
    
    public final String sSubBar = "Bars";
    
    public final String sSubInstr = "Instruments";
    
    public final String sSubInv = "Inventory";
    
    public final String sSubSet = "Set Objects";
    
    public static Object oClass = null;
    
    public project p;
    
    public Vector vInventory = new Vector();
    
    public ExplorerBrowser() {
        super();
        // get the current project state
        p = (project)project.oClass;
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
    
    void loadStructureTopPoint() {
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
    
    
    
    public void mouseClicked(java.awt.event.MouseEvent event) {
        Object object = event.getSource();
        if (object == trStructure)
            trStructure_mouseClicked(event);
    }
    
    public void mouseEntered(java.awt.event.MouseEvent event) {}
    
    public void mouseExited(java.awt.event.MouseEvent event) {}
    
    public void mousePressed(java.awt.event.MouseEvent event) {}
    
    public void mouseReleased(java.awt.event.MouseEvent event) {}
    
    public Vector getExistingNode() {
        return _existingNode;
    }
    
    public void setExistingNode(Vector vExists) {
        _existingNode=vExists;
    }
    
    public void reloadChildren() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(sRootName);
        
        //Create new tree model
        treeModel = new DefaultTreeModel(root);
        
        //Assign root to model
        treeModel.setRoot(root);
        
        
        
        String sNodeName=root.getUserObject().toString();
        
        DefaultMutableTreeNode parent;
        
        DefaultMutableTreeNode child;
        
        parent = new DefaultMutableTreeNode(sSubInv);
        
        treeModel.insertNodeInto(parent, root, 0);
        //add the inventory items
        for (int i = p.inventories.getNumItems()-1; i >= 0 ; i--) {
            // create inventory leaf node
            if(!p.inventories.getItemUsed(i)){
                child = new DefaultMutableTreeNode(String.valueOf(p.inventories.getItemID(i)));
                treeModel.insertNodeInto(child, parent, 0);
            }
        }
        
        //System.out.println("size: "+p.bars.object_list.size());
        
        // needed to add Bar folder only once
        boolean bAddedBar = false;
        
        // needed to add instrument folder only once
        boolean bAddedIns = false;
        
        // needed to add house folder only once
        boolean bAddedHouse = false;
        
        // needed to add stage folder only once
        boolean bAddedStage = false;
        
        // needed to add stage folder only once
        boolean bAddedSet = false;
        // set parent
        
        parent = new DefaultMutableTreeNode(sSubBar);
        
        for (int i = p.bars.object_list.size(); i > 0 ; i--) {
            if (p.bars.object_list.elementAt(i-1) instanceof bar) {
                
                if (!bAddedBar) {
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
        
        //INSTRUMENTS ADDED TO LIST
        parent = new DefaultMutableTreeNode(sSubInstr);
        
        for (int i = p.instruments.object_list.size(); i > 0 ; i--) {
            
            if (p.instruments.object_list.elementAt(i-1) instanceof instrument) {
                if (!bAddedIns) {
                    // create instrument folder
                    treeModel.insertNodeInto(parent, root, 0);
                    bAddedIns = true;
                }
                instrument ins = ((instrument)p.instruments.get_object(i-1));
                // create instrument leaf node with bar id
                child = new DefaultMutableTreeNode(String.valueOf(ins.getInventoryID()));
                treeModel.insertNodeInto(child, parent, 0);
            }
        }
        
        //SET OBJECTS ADDED TO LIST
        parent = new DefaultMutableTreeNode(sSubSet);
        
        for (int i = p.sets.object_list.size(); i > 0 ; i--) {
            if (p.sets.object_list.elementAt(i-1) instanceof setobject) {
                
                if (!bAddedSet) {
                    // create bar folder
                    treeModel.insertNodeInto(parent, root, 0);
                    bAddedSet = true;
                }
                setobject s = (setobject)p.sets.object_list.elementAt(i-1);
                // create bar leaf node with bar id
                child = new DefaultMutableTreeNode(s.getname());
                treeModel.insertNodeInto(child, parent, 0);
            }
        }
        
        //STAGE ADDED TO LIST
        parent = new DefaultMutableTreeNode(sSubStage);
        
        for (int i = p.stages.object_list.size(); i > 0 ; i--) {
            if (p.stages.object_list.elementAt(i-1) instanceof stage) {
                
                if (!bAddedStage) {
                    // create bar folder
                    treeModel.insertNodeInto(parent, root, 0);
                    bAddedStage = true;
                }
                stage s = (stage)p.stages.object_list.elementAt(i-1);
                // create bar leaf node with bar id
                child = new DefaultMutableTreeNode(s.getdescription());
                treeModel.insertNodeInto(child, parent, 0);
            }
        }
        
        //HOUSE OBJECT ADDED TO LIST
        parent = new DefaultMutableTreeNode(sSubHouse);
        
        for (int i = p.houses.object_list.size(); i > 0 ; i--) {
            if (p.houses.object_list.elementAt(i-1) instanceof house) {
                
                if (!bAddedHouse) {
                    // create bar folder
                    treeModel.insertNodeInto(parent, root, 0);
                    bAddedHouse = true;
                }
                house h = (house)p.houses.object_list.elementAt(i-1);
                // create bar leaf node with bar id
                child = new DefaultMutableTreeNode(h.getid());
                treeModel.insertNodeInto(child, parent, 0);
            }
        }
        
        // set the new model
        trStructure.setModel(treeModel);
        trStructure.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        trStructure.setRootVisible(true);
    }
    
    void trStructure_mouseClicked(java.awt.event.MouseEvent event) {
        //handle a new object being selected
        
        //if the drawing state is in selection mode
        if(p.draw_mouse_state==0){
            TreePath path = trStructure.getPathForLocation(event.getX(),event.getY());
            if(path!=null){
                //parent for figureing out what type is selected
                String parent=(String)((DefaultMutableTreeNode)path.getParentPath().getLastPathComponent()).getUserObject();
                String child=(String)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                
                General_Object temp_obj=null;
                //run through the parents to see if a valid object type was selected
                //types are defined as follows 0=house 1=stage 2=bar 3=instrument 4=set 5=inventory
                if(parent.equalsIgnoreCase("House")){
                    p.selected_type=0;
                    p.selected_index=0;
                    temp_obj=p.houses.get_object(0);
                }else if(parent.equalsIgnoreCase("Stage")){
                    p.selected_type=1;
                    p.selected_index=0;
                    temp_obj=p.stages.get_object(0);
                }else if(parent.equalsIgnoreCase("Bars")){
                    p.selected_type=2;
                    //figure out what index it is
                    int i;
                    for(i=0;i<p.bars.get_num_objects();i++){
                        if(((bar)p.bars.get_object(i)).getID().equalsIgnoreCase(child)){
                            p.selected_index=i;
                            temp_obj=p.bars.get_object(i);
                            i=p.bars.get_num_objects();
                        }
                    }
                    
                }else if(parent.equalsIgnoreCase("Instruments")){
                    p.selected_type=3;
                    //figure out what index it is
                    int i;
                    for(i=0;i<p.instruments.get_num_objects();i++){
                        if(((instrument)p.instruments.get_object(i)).getInventoryID()==Integer.parseInt(child)){
                            p.selected_index=i;
                            temp_obj=p.instruments.get_object(i);
                            i=p.instruments.get_num_objects();
                        }
                    }
                }else if(parent.equalsIgnoreCase("Set Objects")){
                    p.selected_type=4;
                    //figure out what index it is
                    int i;
                    for(i=0;i<p.sets.get_num_objects();i++){
                        if(((setobject)p.sets.get_object(i)).getname().equalsIgnoreCase(child)){
                            p.selected_index=i;
                            temp_obj=p.sets.get_object(i);
                            i=p.sets.get_num_objects();
                        }
                    }
                    
                }else if(parent.equalsIgnoreCase("Inventory")){
                    p.selected_type=5;
                    //p.selected_index=p.getIndexbyName()
                }
                
                if(temp_obj!=null){
                    ItemBrowser.displayInfo(temp_obj);
                }
                p.forceRepaint();
            }
        }
        
        
    }
    
    public void insertNewTreeNode() {
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
        
        public ImageIcon getTreeImage(String imageName) {
            ImageIcon icon = null;
            java.net.URL u = ClassLoader.getSystemResource(imageName);
            if (u == null)
                u = getClass().getResource(imageName);
            if (u != null)
                icon = new ImageIcon(u);
            return icon;
        }
        public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,
                boolean expanded,boolean leaf,int row,boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
            
            if (value.toString().substring(value.toString().length()-1).equalsIgnoreCase("D")) {
                setIcon(leafIcon);
            } else {
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