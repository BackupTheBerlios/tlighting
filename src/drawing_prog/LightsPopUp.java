/*
 * LightsPopUp.java
 *
 * Created on April 20, 2005, 12:23 AM
 */

package drawing_prog;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import Data_Storage.*;
import theatre.*;

/**
 *
 * @author root
 */
public class LightsPopUp extends JDialog implements ActionListener{
    protected project proj_class;
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    //components
    private JComboBox jcInst = new JComboBox();
    private JTextArea jText= new JTextArea();
    /** Creates a new instance of LightsPopUp */
    public LightsPopUp() {
        super(BasicWindow.curWindow,"Instrument Selector", true);
        //setTitle("Inventory Manager");
        proj_class=(project)project.oClass;
        
        addComponents();
        setBounds(10,50, iScreenHeight,iScreenWidth);
        //setSize(500,500);
        setResizable(true);
        setVisible(true);
    }
    
    public void addComponents() {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(null);
        
        jText = new JTextArea();
        jText.setBounds(250, 100, 230, 210);
        jpMain.add(jText);
        
        loadItems();
        jcInst.setBounds(250, 15, 150, 20 );
        jcInst.addActionListener(this);
        jcInst.setActionCommand("listaction");
        jpMain.add( jcInst );
        
        
        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        jbOk.setActionCommand("ok");
        jbOk.setBounds(250, 70, 150, 20);
        jpMain.add(jbOk);
        
        JButton jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        jbCancel.setBounds(375, 70, 250, 20);
        jpMain.add(jbCancel);
        
        
        
        
        getContentPane().add(jpMain);
    }
    
    public void loadItems() {
        jcInst.removeAllItems();
        int i;
        for(i=0;i<proj_class.inventories.getNumItems();i++){
            if(!proj_class.inventories.getItemUsed(i)){
                jcInst.addItem(String.valueOf(proj_class.inventories.getItemID(i)));
            }
        }
        jcInst.validate();
        jcInst.setSelectedIndex(0);
        //find out which item was selected 
            int inv_id = Integer.parseInt((String)jcInst.getSelectedItem());
            int index=proj_class.getInstrumentByID(inv_id);
        
            //populate the text area
            String s = "Inventory ID: "+proj_class.inventories.getItemID(index)+"\n";
            s += "Description: " + proj_class.inventories.getItemDesc(index)+"\n";
            s += "Type: "+proj_class.inventories.getItemType(index)+"\n";
            
            jText.setText(s);
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            proj_class.templightid=Integer.parseInt((String)jcInst.getSelectedItem());
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            proj_class.templightid=-1;
            this.dispose();
        }else if(e.getActionCommand().equals("listaction")){
            
            //find out which item was selected 
            int inv_id = Integer.parseInt((String)jcInst.getSelectedItem());
            int index=proj_class.getInstrumentByID(inv_id);
        
            //populate the text area
            String s = "Inventory ID: "+proj_class.inventories.getItemID(index)+"\n";
            s += "Description: " + proj_class.inventories.getItemDesc(index)+"\n";
            s += "Type: "+proj_class.inventories.getItemType(index)+"\n";
            
            jText.setText(s);
        
        }
    }
}
