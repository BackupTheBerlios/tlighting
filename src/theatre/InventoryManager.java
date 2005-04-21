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
import java.awt.event.*;

import Data_Storage.*;
/**
 * @author Ilya Buzharsky
 *edited by Josh Zawislak 4-18-05
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InventoryManager extends JDialog implements ActionListener {
    protected JPanel jpInstruments = new JPanel();
    protected JPanel jpInstrumentType = new JPanel();
    protected JTabbedPane tpBulletinBoard;
    protected JPanel btnPanel = new JPanel();
    
    
    
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    protected project proj_class;
    
    //components
    private JList instList;
    private JTextField name;
    private JTextField desc;
    private JComboBox jcType = new JComboBox();
    private JTextArea notes;
    private JList typeList;
    private JTextField typeName;
    private JTextField typeDesc;
    private JTextArea typeNotes;
    private JScrollPane jsNotes;
    private JScrollPane jsNotesType;
    public InventoryManager() {
        super(BasicWindow.curWindow,"Inventory Manager", true);
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
        
        createInstrumentTab();
        createInstrumentTypeTab();
        setBtnPanel();
        tpBulletinBoard = new JTabbedPane(SwingConstants.TOP);
        
        tpBulletinBoard.addTab("Instruments", jpInstruments);
        tpBulletinBoard.addTab("Instrument Type", jpInstrumentType);
        
        tpBulletinBoard.setBounds(0,0,800,430);
        btnPanel.setBounds(200, 410, 250,75);
        btnPanel.setVisible(true);
        jpMain.add(tpBulletinBoard);
        jpMain.add(btnPanel);
        
        getContentPane().add(jpMain);
        popInstList();
        popTypeList();
    }
    public void createInstrumentTab() {
        jpInstruments = new JPanel();
        jpInstruments.setLayout( null );
        
        instList = new JList();
        instList.setBounds(0,0,200, iScreenHeight-20);
        jpInstruments.add(instList);
        
        JLabel label1 = new JLabel( "Name (integer):" );
        label1.setBounds( 250, 15, 150, 20 );
        jpInstruments.add( label1 );
        
        name = new JTextField();
        name.setBounds( 375, 15, 250, 20 );
        jpInstruments.add( name );
        
        JLabel label2 = new JLabel( "Description:" );
        label2.setBounds( 250, 40, 150, 20 );
        jpInstruments.add( label2 );
        
        desc = new JTextField();
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
        
        notes = new JTextArea();
        notes.setEditable(true);
        //notes.setBounds( 375, 100, 250, 250 );
        
        jsNotes = new JScrollPane();
        jsNotes.getViewport().add(notes);
        //JTextArea notes = new JTextArea();
        jsNotes.setBounds( 375, 100, 250, 250 );
        jpInstruments.add( jsNotes );
        
        //btnPanel.setVisible(true);
        //jpInstruments.add( btnPanel);
    }
    public void createInstrumentTypeTab() {
        jpInstrumentType = new JPanel();
        jpInstrumentType.setLayout( null );
        
        typeList = new JList();
        typeList.setBounds(0,0,200, iScreenHeight-20);
        jpInstrumentType.add( typeList );
        
        JLabel label1 = new JLabel( "Name:" );
        label1.setBounds( 250, 15, 150, 20 );
        jpInstrumentType.add( label1 );
        
        typeName = new JTextField();
        typeName.setBounds( 375, 15, 250, 20 );
        jpInstrumentType.add( typeName );
        
        JLabel label2 = new JLabel( "Description:" );
        label2.setBounds( 250, 40, 150, 20 );
        jpInstrumentType.add( label2 );
        
        typeDesc = new JTextField();
        typeDesc.setBounds( 375, 40, 250, 20 );
        jpInstrumentType.add( typeDesc );
        
        JLabel label3 = new JLabel( "Notes:" );
        label3.setBounds( 250, 100, 150, 20 );
        jpInstrumentType.add( label3 );
        
        typeNotes = new JTextArea();
        typeNotes.setEditable(true);
        //notes.setBounds( 375, 100, 250, 250 );
        
        jsNotesType = new JScrollPane();
        jsNotesType.getViewport().add(notes);
        //JTextArea notes = new JTextArea();
        jsNotesType.setBounds( 375, 100, 250, 100 );
        jpInstrumentType.add( jsNotesType );
        
        JLabel label4 = new JLabel( "Drawing:" );
        label4.setBounds( 250, 220, 150, 20 );
        jpInstrumentType.add( label4 );
        
        //btnPanel.setVisible(true);
        //jpInstrumentType.add( btnPanel);
    }
    
    
    
    public void setBtnPanel() {
        //btnPanel.setLayout( null );
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setAlignmentX((float).5);
        JButton jbSave = new JButton("Save");
        jbSave.addActionListener(this);
        jbSave.setActionCommand("save");
        btnPanel.add(jbSave);
        btnPanel.add(Box.createHorizontalStrut(20));
        JButton jbRemove = new JButton("Remove");
        jbRemove.addActionListener(this);
        jbRemove.setActionCommand("remove");
        btnPanel.add(jbRemove);
        btnPanel.add(Box.createHorizontalStrut(40));
        JButton jbCancel = new JButton("Exit");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        btnPanel.add(jbCancel);
        
    }
    public void loadItems() {
        jcType.removeAllItems();
        int i;
        for(i=0;i<proj_class.types.size();i++){
            jcType.addItem(((knowntype)proj_class.types.get(i)).getName());
        }
        jcType.validate();
    }
    
    public void popInstList(){
        instList.removeAll();
        int i;
        Vector data=new Vector();
        for(i=0;i<proj_class.inventories.getNumItems();i++){
            
            data.add(String.valueOf(proj_class.inventories.getItemID(i)));
        }
        instList.setListData(data);
        instList.validate();
    }
    public void popTypeList(){
        typeList.removeAll();
        int i;
        Vector data=new Vector();
        for(i=0;i<proj_class.types.size();i++){
            
            data.add(String.valueOf(((knowntype)proj_class.types.get(i)).getName()));
        }
        typeList.setListData(data);
        typeList.validate();
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(tpBulletinBoard.getSelectedIndex()==0){
            //first tab is selected working with instruments
            System.out.println("Event while first tab is selected");
            
            if (e.getActionCommand().equals("save")) {
                System.out.println("SAVE BUTTON HIT");
                //check if there is an instrument with the same name
                int i;
                String n=name.getText();
                int aId=Integer.parseInt(n);
                String aType=(String)jcType.getSelectedItem();
                String aDesc=desc.getText();
                
                boolean itemfound=false;
                int foundindex=-1;
                
                for(i=0;i<proj_class.inventories.getNumItems();i++){
                    if(proj_class.inventories.getItemID(i)==aId){
                        itemfound=true;
                        foundindex=i;
                    }
                }
                
                //if the instrument exists edit the entry
                if(itemfound){
                    proj_class.EditInventory(foundindex, aType, aId, aDesc);
                } else{
                    //else add a new instrument
                    proj_class.AddInventory(aType, aId, aDesc);
                }
                popInstList();
            }else if(e.getActionCommand().equals("remove")){
                System.out.println("REMOVE BUTON HIT)");
                //check if there is a entry with the same name as the
                //instrument shown if there is remove it if not don't do anythin
                int i;
                String n=name.getText();
                int aId=Integer.parseInt(n);
                String aType=(String)jcType.getSelectedItem();
                String aDesc=desc.getText();
                
                boolean itemfound=false;
                int foundindex=-1;
                
                for(i=0;i<proj_class.inventories.getNumItems();i++){
                    if(proj_class.inventories.getItemID(i)==aId){
                        itemfound=true;
                        foundindex=i;
                    }
                }
                if(itemfound){
                    proj_class.RemoveInventory(foundindex);
                }
                popInstList();
            }else if(e.getActionCommand().equals("cancel")){
                System.out.println("CANCEL BUTON HIT)");
                
                //quits
                this.dispose();
            }
            
        }else{
            //the second tab is selected working with instrument types
            System.out.println("Event while sconde tab is selected");
            if (e.getActionCommand().equals("save")) {
                System.out.println("SAVE BUTTON HIT");
                
                //check if a instrument type with the same name exists, if it does then edit the entry if not then add it
                
                int i;
                String n=typeName.getText();
                String aDesc=typeDesc.getText();
                String aNote=jsNotesType.toString();
                boolean itemfound=false;
                int foundindex=-1;
                
                for(i=0;i<proj_class.types.size();i++){
                    if(((knowntype)proj_class.types.get(i)).getName().compareTo(n)==0){
                        itemfound=true;
                        foundindex=i;
                    }
                }
                
                //if the instrument type exists edit the entry
                knowntype tempType=new knowntype();
                tempType.setAim(true);
                tempType.setDesc(aDesc);
                tempType.setHeight(2);
                tempType.setName(n);
                if(itemfound){
                    
                    proj_class.EditType(foundindex,tempType);
                } else{
                    //else add a new instrument type
                    proj_class.AddType(tempType);
                }
                popTypeList();
            }else if(e.getActionCommand().equals("remove")){
                System.out.println("Remove BUTON HIT)");
                //check if there is a type with the same name
                //if there is then remove it and all instruments
                //with the same type
                 int i;
                String n=typeName.getText();
                String aDesc=typeDesc.getText();
                String aNote=jsNotesType.toString();
                boolean itemfound=false;
                int foundindex=-1;
                
                for(i=0;i<proj_class.types.size();i++){
                    if(((knowntype)proj_class.types.get(i)).getName().compareTo(n)==0){
                        itemfound=true;
                        foundindex=i;
                    }
                }
                
                if(itemfound){
                    proj_class.RemoveType(foundindex);
                }
                //if not dont do anything
                popTypeList();
            }else if(e.getActionCommand().equals("cancel")){
                System.out.println("CANCEL BUTON HIT)");
                //quits
                this.dispose();
            }
            loadItems();
        }
    }
}
