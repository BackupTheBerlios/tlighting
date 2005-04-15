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
public class WiringPlot extends JDialog{
    //UI Objects
    public project proj_class;
    private javax.swing.JTable outTable;
    protected JPanel jp;
    protected JRadioButton barB, dimB, lightB;
    
    //Data
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    protected int selection;
    
    /** Creates a new instance of WiringPlot */
    public WiringPlot() {
        super(BasicWindow.curWindow,"Inventory Manager", true);
        proj_class=(project)project.oClass;
        this.setTitle("Wiring Output Diagram");
        this.setBounds(20, 20, iScreenHeight/2,iScreenWidth);
        initAll();
        this.setVisible(true);
        
    }
    private void initAll() {
        getContentPane().setLayout(null);
        jp = new JPanel();
        jp.setLayout(null);
        
        makeTable();
        jp.add(outTable);
        
        barB = new JRadioButton("Sort On Bar");
        barB.setBounds(((iScreenHeight/2)-450), (iScreenWidth-400), ((iScreenHeight/2)-375),(iScreenWidth-360));
        barB.setVisible(true);
        jp.add(barB);
        
        System.out.println("Test 1");
        jp.setBounds(22,22, (iScreenHeight/2)-20,iScreenWidth-20);
        
        getContentPane().add(jp);
    }
    private void makeTable() {
        Object[] columnNames =  {   "Bar",
                                    "Dimmer",
                                    "Light/Item"
                                };

        Object[][]  data    =  populateTable();
        outTable = new javax.swing.JTable(data, columnNames);
        
        outTable.setBounds(30,30,(iScreenHeight/2)-105,iScreenWidth-300);
        outTable.setVisible(true);
    }
    private Object[][] populateTable() {
        int x = 1;
        int y = 1;
        Object[][] retval;
        
        //TEMP
        retval = new Object[][] {
                    {"a",",b","c"}
        };
        if(selection == 1){
            //handle sort on dimmer
            
        }
        else if(selection == 2){
            //handle sort on light
            
        }
        else if(selection == 3){
            //Handle Sort on Bar
            
        }
        else {
            System.out.println("Error In UI Wiring Handled");
            //This section handles extra input situation
        }
        return retval;
    }
}
