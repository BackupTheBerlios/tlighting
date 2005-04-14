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

/**
 *
 * @author Greg Silverstein
 * 4/14/2005
 */
public class WiringPlot extends Window{
    public project proj_class;
    private javax.swing.JTable outTable;
    
    /** Creates a new instance of WiringPlot */
    public WiringPlot() {
        System.out.println("WTF");
        proj_class=(project)project.oClass;
        this.setTitle("Wiring Output Diagram");
        this.setBounds(0, 0, 700, 700);
        this.setVisible(true);
    }
    private void initAll() {
        getContentPane().setLayout(null);
        
        outTable = new javax.swing.JTable();
        
    }
}
