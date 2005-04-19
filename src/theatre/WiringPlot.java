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
    
    //Data
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    protected int selection;
    
    /** Creates a new instance of WiringPlot */
    public WiringPlot() {
        super(BasicWindow.curWindow,"Inventory Manager", true);
        proj_class=(project)project.oClass;
        this.setTitle("Wiring Output Diagram");
        this.setBounds(20, 20, 600,700);
        initAll();
        this.setVisible(true);
        
    }
    private void initAll() {
        getContentPane().setLayout(null);
        jp = new JPanel();
        jp.setLayout(null);
        
        
        barB = new JButton("Sort On Bar");
        barB.setBounds(30,500,135,30);
        barB.setVisible(true);
        barB.addActionListener(this);
        barB.setActionCommand("bar");
        jp.add(barB);
        
        dimB = new JButton("Sort On Dimmer");
        dimB.setBounds(30,550,135,30);
        dimB.setVisible(true);
        dimB.addActionListener(this);
        dimB.setActionCommand("dimmer");
        jp.add(dimB);
        
        lightB = new JButton("Sort On Light");
        lightB.setBounds(30,600,135,30);
        lightB.setVisible(true);
        lightB.addActionListener(this);
        lightB.setActionCommand("light");
        jp.add(lightB);
        
        btn_print = new JButton("Print");
        btn_print.setBounds(195,500,135,30);
        btn_print.setVisible(true);
        btn_print.addActionListener(this);
        btn_print.setActionCommand("print");
        
        jp.add(btn_print);
        
        System.out.println("Test 1");
        jp.setBounds(22,22, 780,695);
        
        //add table default draw on sort by light
        selection = 2;
        makeTable();
        
        getContentPane().add(jp);
    }
    private void makeTable() {
        Object[] columnNames =  {   "Bar",
                                    "Dimmer",
                                    "Light/Item"
                                };

        Object[][]  data    =  populateTable();
        outTable = new javax.swing.JTable(data, columnNames);
        
        outTable.setBounds(30,30,500,465);
        outTable.setVisible(true);
        jp.add(outTable);
    }
    private Object[][] populateTable() {
        int x = 1;
        int y = 1;
        Object[][] retval;
        
        //TEMP
        String[] a = new String[5];
        a = new String[] {"bar", "a", "b","c","d"};
        
        retval = new Object[][] {
                    {a,a,a}
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
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "bar"){
            selection = 3;
            makeTable();
        }
        else if(e.getActionCommand() == "dimmer"){
            selection = 1;
            makeTable();
        }
        else if(e.getActionCommand() == "light"){
            selection = 2;
            makeTable();
        }
        else if(e.getActionCommand() == "print"){
            PrintUtilities.printComponent(outTable);
        }
        
    }
}
