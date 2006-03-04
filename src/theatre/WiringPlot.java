/*
 * WiringPlot.java
 * This will hold the general form for thw wiring diagram
 * Created on April 14, 2005, 3:57 PM
 */

package theatre;
import Data_Storage.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 *
 * @author Greg Silverstein
 * 4/14/2005
 */
public class WiringPlot extends JDialog implements ActionListener{
    //UI Objects
    public project proj_class;
    private javax.swing.JTable outTable;
    protected JPanel jp;
    protected JButton barB, dimB, lightB;
    protected JButton btn_print;
    // Make the table vertically scrollable
    protected JScrollPane scrollPane;
    //Data
    protected int iScreenWidth =BasicWindow.iScreenWidth-50;
    protected int iScreenHeight = BasicWindow.iScreenHeight-50;
    protected int selection;
    
    /** Creates a new instance of WiringPlot */
    public WiringPlot() {
        super(BasicWindow.curWindow,"Inventory Manager", true);
        proj_class=(project)project.oClass;
        this.setTitle("Wiring Output Diagram");
        this.setBounds(20, 20, iScreenWidth-40,iScreenHeight-40);
        initAll();
        this.setVisible(true);
        
    }
    private void initAll() {
        getContentPane().setLayout(null);
        jp = new JPanel();
        jp.setLayout(null);
        
        
        barB = new JButton("Sort On Bar");
        barB.setBounds(10,iScreenHeight-150,130,30);
        barB.setVisible(true);
        barB.addActionListener(this);
        barB.setActionCommand("bar");
        jp.add(barB);
        
        dimB = new JButton("Sort On Dimmer");
        dimB.setBounds(150,iScreenHeight-150,135,30);
        dimB.setVisible(true);
        dimB.addActionListener(this);
        dimB.setActionCommand("dimmer");
        jp.add(dimB);
        
        lightB = new JButton("Sort On Light");
        lightB.setBounds(300,iScreenHeight-150,135,30);
        lightB.setVisible(true);
        lightB.addActionListener(this);
        lightB.setActionCommand("light");
        jp.add(lightB);
        
        btn_print = new JButton("Print");
        btn_print.setBounds(450,iScreenHeight-150,135,30);
        btn_print.setVisible(true);
        btn_print.addActionListener(this);
        btn_print.setActionCommand("print");
        
        jp.add(btn_print);
        
        System.out.println("Test 1");
        jp.setBounds(5,5, iScreenWidth-50,iScreenHeight-50);
        
        //add table default draw on sort by light
        selection = 2;
        makeTable();
        
        getContentPane().add(jp);
    }
  
    
    private void makeTable() {
        String[] columnNames =  {"Instrument","Type","Bar Name","Bar ID",
            "Dimmer","Color","Position","Aim At"
        };
        
        Object[][]  data    = populateTable();
                
                /*{
            {   "Bar1", "Dimmer","Light/Item"
            },
            {   "Bar2","Dimmer","Light/Item"
            },
            {   "Bar3","Dimmer","Light/Item"
            }};
        */
        
        //populateTable();
        outTable = new javax.swing.JTable(data, columnNames);
       
        outTable.setBounds(10,10,iScreenWidth-90,iScreenHeight-200);
        //outTable.setVisible(true);
        outTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Dimension dimen= new Dimension();
        dimen.height=iScreenHeight-210;
        dimen.width=iScreenWidth-100;
        outTable.setPreferredScrollableViewportSize(dimen);
        outTable.setAutoscrolls(true);
        
        // Make the table vertically scrollable
        scrollPane = new JScrollPane(outTable);
        scrollPane.setBounds(10,10, iScreenWidth-90, iScreenHeight-200);    

        jp.add(scrollPane);
        jp.validate();
    }
    private Object[][] populateTable() {
        int x = 1;
        int y = 1;
        Object[][] retval;
        
        //TEMP
        String[] a = new String[3];
        a = new String[] {"bar", "a", "b"};
        
        retval = new Object[proj_class.instruments.get_num_objects()+1][8];
        
        if(selection == 1){
            //handle sort on dimmer
            System.out.println("sorting by dimmer");
        } else if(selection == 2){
            //handle sort on light
            System.out.println("sorting by light");
            
            
            if(proj_class.instruments.get_num_objects()>0){
                //there are lights that we can display
                //get the lowest value instrument
                
                instrument last = getNextInstrument(null);
                
                
                
                int i=1;
                while(last!=null){
                    //add last to the list to display
                    retval[i][0]=new Integer(last.getInventoryID());
                    retval[i][1]=new String(last.getType());
                    retval[i][2]=new String(proj_class.getBarNameByID(last.Associated_barID));
                    retval[i][3]=new Integer(last.Associated_barID);
                    retval[i][4]=new Integer(last.Associated_dimmerID);
                    retval[i][5]=new String("R:"+last.R+" B:"+last.B+" G:"+last.G);
                    retval[i][6]=new String("X:"+last.worldx+" Y:"+last.worldy+" Z:"+last.worldz);
                    retval[i][7]=new String("X:"+last.aimx+" Y:"+last.aimy+" Z:"+last.aimz);
                    i++;
                    last = getNextInstrument(last);
                }
                
                
            }
            
            
            
            
            
        } else if(selection == 3){
            //Handle Sort on Bar
            
            System.out.println("sorting by bar");
            retval[0][0]=new String("Bar");
            retval[0][1]=new String("Instrument");
            retval[0][2]=new String("Dimmer");
            
            if(proj_class.instruments.get_num_objects()>0){
                //there are lights that we can display
                //get the lowest value instrument
                
                instrument last = getNextInstrument(null);
                
                
                
                int i=1;
                while(last!=null){
                    //add last to the list to display
                    retval[i][0]=new Integer(last.getInventoryID());
                    retval[i][1]=new Integer(last.Associated_dimmerID);
                    retval[i][2]=new Integer(last.Associated_barID);
                    i++;
                    last = getNextInstrument(last);
                }
                
                
            }
            
            
            
        } else {
            System.out.println("Error In UI Wiring Handled");
            //This section handles extra input situation
        }
        return retval;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "bar"){
            selection = 3;
            makeTable();
        } else if(e.getActionCommand() == "dimmer"){
            selection = 1;
            makeTable();
        } else if(e.getActionCommand() == "light"){
            selection = 2;
            makeTable();
        } else if(e.getActionCommand() == "print"){
            PrintUtilities.printComponent(scrollPane);
        }
        
    }
    
    
    //get functions to retrieve the next item that should be displayed
    //this method is not the optimal solution but it should work fine
    //will have to test performance after it is done
    
    public instrument getNextInstrument(instrument last){
        instrument temp=null;
        
        for(int i=0;i<proj_class.instruments.get_num_objects();i++){
            //base case
            if(last!=null){
                if(((instrument)proj_class.instruments.get_object(i)).InventoryID>last.InventoryID){
                    if(temp==null){
                        temp=(instrument)proj_class.instruments.get_object(i);
                    } else if(((instrument)proj_class.instruments.get_object(i)).InventoryID<temp.InventoryID){
                        temp=(instrument)proj_class.instruments.get_object(i);
                    }
                }
            }else{
                if(temp==null){
                    temp=(instrument)proj_class.instruments.get_object(i);
                } else if(((instrument)proj_class.instruments.get_object(i)).InventoryID<temp.InventoryID){
                    temp=(instrument)proj_class.instruments.get_object(i);
                }
            }
        }
        
        return temp;
    }
    
    public instrument getNextInstrumentOnBar(instrument last,bar theBar){
        instrument temp=null;
        
        return temp;
    }
    
    
    
}
