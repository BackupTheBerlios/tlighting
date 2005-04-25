package drawing_prog;

//standard java libraries
import java.awt.*; 
import java.awt.event.*; 
import java.awt.geom.*; 
import java.awt.image.*; 
import javax.swing.*;
import java.sql.*;
import java.util.*;
//custom libraries used
import theatre.*;
import Data_Storage.*;

/**
 *
 * @author jzawisla
 */
public class DrawScreen extends theatre.Window implements ItemListener, ActionListener
{
    JLabel primLabel, lineLabel, paintLabel, transLabel, strokeLabel; 
    public TransPanel display; 
    static JComboBox primitive, line, paint, trans, stroke; 
    JButton redraw; 
    public static boolean no2D = false; 
    
    public static Object oClass = null;
    public DrawScreen()
    {
    	this.setTitle("Schematic Drawing"); 
        this.setBounds(ExplorerBrowserPanel.iWidth, 0, BasicWindow.iScreenWidth-(ExplorerBrowserPanel.iWidth*2), BasicWindow.iScreenHeight);
        
        //Dimension d= new Dimension();
        //d.height=100;
        //d.width=100;
        //setPreferredSize(d);
        //d.height=90;
        //d.width=90;
        
        //setMinimumSize(d);
        
        this.init(); // <--- UNCOMMENT THIS
        this.setVisible(true);
        oClass=this;
    }
    public void init() 
    { 
        display = new TransPanel();
        display.setBackground(Color.white); 
        
        
        
        getContentPane().add(display);
        validate();
    } 
    
    public void itemStateChanged(ItemEvent e){} 
    public void actionPerformed(ActionEvent e) { 
        //display.setTrans(trans.getSelectedIndex()); 
        //display.printShape();
        //PrintUtilities.printComponent(this);
    } 
/*    public static void main( String[] argv ) { 
        if ( argv.length > 0 && argv[0].equals( "-no2d" ) ) { 
            Main.no2D = true; 
        } 
    } */
} 
