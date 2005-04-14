package theatre;
/*
 * Main.java
 *
 * Created on December 21, 2004, 11:08 PM
 */
import java.awt.*; 
import java.awt.event.*; 
import java.util.*;
import javax.swing.*;
import drawing_prog.*;
import Data_Storage.*;
import xml_Interface.*;
import java.io.*;

/**
 *
 * @author  jzawisla
 */
public class BasicWindow extends JFrame implements ItemListener, ActionListener
{
    //i dont know what this does it was in the code i based this off of
    public static boolean no2D = false;
    public static int iScreenWidth = 0;
    public static int iScreenHeight = 0;
    
    public static BasicWindow curWindow;
    //other variables that are used in the window
    //Where the GUI is created:
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem;
    xml_Reader load_XMLFILE;
    xml_Writer save_XMLFILE;
    JFileChooser m_fileChooser;
    String s_barPath, s_projPath;
    JInternalFrame file_Window,error_Window;
    //public project project_class;
    
    public BasicWindow()
    {
    	super();
    	setResolution();
    }
    
    //this is the initialization for the windows menu
    public JMenuBar initmenu() 
    {
        //Create the menu bar.
        menuBar = new JMenuBar();
        
        //Build the File menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "Basic Program functionality");
        
        menuBar.add(menu);
        
        
        //A New option to the file menu
        menuItem = new JMenuItem("New",
                                 KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Create a new design");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //a Open option to the file menu
        menuItem = new JMenuItem("Open...",
                                 KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Open a design");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //add save option to the file menu
        menuItem = new JMenuItem("Save..");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save a design");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //add print option to the file menu
        menuItem = new JMenuItem("Print");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Print the schematic drawing");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //add quit option to the file menu
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save a design");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Build the Edit menu.
        menu = new JMenu("Edit");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "Basic editing functionality");
        menuBar.add(menu);
        
        //add Cut option to the Edit menu
        menuItem = new JMenuItem("Cut",
                                 KeyEvent.VK_O);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Cut the selected object");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //add copy option to the edit menu
        menuItem = new JMenuItem("Copy");
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Copy a selectedf object");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //add Paste option to the edit menu
        menuItem = new JMenuItem("Paste");
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Paste the last item cut or copied");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Build the Options menu.
        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "Options for the program");
        menuItem.addActionListener(this);
        menuBar.add(menu);
        
        //add Cut option to the Edit menu
        menuItem = new JMenuItem("Preferences",
                                 KeyEvent.VK_O);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Change settings for the program");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Build the Options menu.
        menu = new JMenu("Views");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription(
                "Options for the program");
        menuBar.add(menu);
        
        ButtonGroup group = new ButtonGroup();
        
        rbMenuItem = new JRadioButtonMenuItem("Theater Design");
        rbMenuItem.setSelected(true);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("Inventory Management");   
        rbMenuItem.addActionListener(this);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Wiring Diagram");   
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
		      
        menuBar.add(menu);
        return menuBar;
    }
    public void itemStateChanged(ItemEvent e){
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println("Item Event "+source.getText());
    } 
    public void actionPerformed(ActionEvent e) { 
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println("Action Event "+source.getText());
        
        if(source.getText()=="New"){
        //New
            //for right now just create a default house that can be edited to be the right size
            project proj=(project)project.oClass;
            //proj=new project();
            
            proj.addHouse(600,1800,"House_Object");
            
        }else if(source.getText()=="Open..."){
            //Addition Made By Greg Silverstein 3/3
            //Open Functionality
            
            load_XMLFILE= new xml_Reader();
            boolean result=false;
            file_Window = new JInternalFrame();
            file_Window.setLocation(0,0);
            file_Window.setBounds(0,0, 400,400);
            file_Window.setVisible(true);
            m_fileChooser = new JFileChooser();
            int retval = m_fileChooser.showOpenDialog(file_Window);
            if (retval == JFileChooser.APPROVE_OPTION) {
                //... The user selected a file, process it.
                m_fileChooser.setVisible(true);
                File file = m_fileChooser.getSelectedFile();
                //check for that it is a .bar file extension for xml bar files
                if(file.isFile() == true && file.getAbsolutePath().endsWith(".xml")==true){
                    s_barPath = file.getAbsolutePath();
                    s_projPath = file.getParent();
                    result =load_XMLFILE.load_project(s_projPath,s_barPath);
                }
                else {
                    //not a valid selection so error and stop
                    error_Window = new JInternalFrame();
                    error_Window.setLocation(0,0);
                    error_Window.setBounds(0,0, 400,400);
                    error_Window.setVisible(true);
                    JOptionPane.showMessageDialog(error_Window,"Error Improper File Type");
                }
                if(result==true){
                    //house is loaded properly
                    error_Window = new JInternalFrame();
                    error_Window.setLocation(0,0);
                    error_Window.setBounds(0,0, 400,400);
                    error_Window.setVisible(true);
                    JOptionPane.showMessageDialog(error_Window,"File is successfully loaded");
                    
                }else{
                    //house is not loaded properly
                    error_Window = new JInternalFrame();
                    error_Window.setLocation(0,0);
                    error_Window.setBounds(0,0, 400,400);
                    error_Window.setVisible(true);
                    JOptionPane.showMessageDialog(error_Window,"File failed loaded");
                }
            }
            //End Addition For Load
        }else if(source.getText()=="Save.."){
            //Revision By Greg Silverstein 3/3 For Save then by JoshZ 3/19/05
            //Open Functionality
            
            save_XMLFILE = new xml_Writer();
            
            file_Window = new JInternalFrame();
            file_Window.setLocation(0,0);
            file_Window.setBounds(0,0, 400,400);
            file_Window.setVisible(true);
            m_fileChooser = new JFileChooser();
            int retval = m_fileChooser.showOpenDialog(file_Window);
            if (retval == JFileChooser.APPROVE_OPTION) {
                //... The user selected a file, process it.
                m_fileChooser.setVisible(true);
                File file = m_fileChooser.getSelectedFile();
                
                s_barPath = file.getAbsolutePath();
                s_projPath = file.getParent();
                save_XMLFILE.save_project(s_projPath, s_barPath);
            }
            
        }else if(source.getText()=="Print"){
            //print the schematic drawing as it is currently seen
            project proj_class=(project)project.oClass;
            proj_class.print_schematic();
            
        }else if(source.getText()== "Inventory Management"){
            InventoryManager im = new InventoryManager();
System.out.println("inventory manager selected");           
        }else if(source.getText()=="Quit"){
            System.exit(0);
        }
        
        
    }
    public void setResolution()
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	iScreenWidth = screenSize.width;
    	iScreenHeight = (screenSize.height-screenSize.height/250);
    }
    /** * Create the GUI and show it. For thread safety, * this method should be invoked from the * event-dispatching thread. */
    public static void createAndShowGUI() 
    { 
        
        //create a new project class to hold info
        project project_class=new project();
        
        
    	BasicWindow bwin = new BasicWindow();

    	// Create a Desktop for the Application
        final JDesktopPane desktop = new JDesktopPane();

        //Create and set up the window. 
        JFrame frame = new JFrame("Lighting Design & Instrument Inventory Managment");
        // close the application when X is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // get the resolution set on the system
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // set the size based on the system resolution
        //frame.setSize(screenSize.width, screenSize.height-(screenSize.height/20));
        frame.setSize(BasicWindow.iScreenWidth, BasicWindow.iScreenHeight-(screenSize.height/20));
		
        // Create the Drag and Drop Panel
        ExplorerBrowserPanel dd = new ExplorerBrowserPanel();
        // Create the stage Panel
        DrawScreen stage = new DrawScreen();
        // Create the ControlPanel
        ControlPanel cp = new ControlPanel();
        // Create the ItemBrowser
        ItemBrowser ib = new ItemBrowser();
        desktop.add(dd); 
        desktop.add(stage);
        desktop.add(cp);
        desktop.add(ib);
        Container content = frame.getContentPane();
        content.add(desktop, BorderLayout.CENTER);

         
        frame.setJMenuBar(bwin.initmenu());
        frame.setVisible(true);
		
        try{
                //dd.setSelected(true);
                //stage.setSelected(true);
                //cp.setSelected(true);
                //ib.setSelected(true);
        }catch(Exception e)
        {
                System.out.println("setting focus failed for Stage Items panel");
        }
    } 
    public static void main(String[] args) 
    { 
        //Schedule a job for the event-dispatching thread: 
        //creating and showing this application's GUI.
        curWindow = new BasicWindow();
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        javax.swing.SwingUtilities.invokeLater(new Runnable() { 
            public void run() { 
            	createAndShowGUI();
            } 
        }
      ); 
        
        
    }
 
}
