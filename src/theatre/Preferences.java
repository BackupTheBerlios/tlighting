/*
 * LightsPopUp.java
 *
 * Created on June 18, 2005 by Joshua Zawislak
 */

package theatre;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import Data_Storage.*;
import drawing_prog.*;

/*From the preferences page a user can custom define the look of the program.
 * line width, snap to grid on/opff and distance, color of drawning for set objects, house, 
 *bars, and stage, enable coloring for instrument objects, number of photons to store,
 *color of objects being edited, selected nodes, background for the schematic drawing, and more
 */


public class Preferences extends JDialog implements ActionListener{
     protected project proj_class;
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    private JTextField txtLineWidth= new JTextField();
    private JTextField txtSnapDistance= new JTextField();
    private JComboBox jcColorSet = new JComboBox();
    private JComboBox jcColorStage = new JComboBox();
    private JComboBox jcColorBar = new JComboBox();
    private JComboBox jcColorHouse = new JComboBox();
    private JComboBox jcColorSelected = new JComboBox();
    private JComboBox jcColorNodes = new JComboBox();
    private JComboBox jcBackground = new JComboBox();
    private JTextField txtPhotonNumber= new JTextField();
    private Checkbox cbSnapGrid = new Checkbox();
    private Checkbox cbColorLights = new Checkbox();
    private JButton btnSave = new JButton();
    private JButton btnCancel = new JButton();
    
    
    public Preferences(){
        super(BasicWindow.curWindow,"Instrument Selector", true);
        //setTitle("Inventory Manager");
        proj_class=(project)project.oClass;
        
        addComponents();
        setValues();
        setBounds(10,50, iScreenHeight,iScreenWidth);
        //setSize(500,500);
        setResizable(true);
        setVisible(true);
        
        
    }
    
    public void addComponents() {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(null);
     
        //line width
        JLabel lblLineWidth = new JLabel( "Line Width:" );
        lblLineWidth.setBounds( 20, 20, 150, 20 );
        jpMain.add( lblLineWidth );
        
        txtLineWidth.setBounds(150,20, 100,20);
        jpMain.add(txtLineWidth);
        
        
        
        //snap to grid on/off
        JLabel lblSnapGrid = new JLabel( "Snap To Grid:" );
        lblSnapGrid.setBounds( 20, 50, 150, 20 );
        jpMain.add( lblSnapGrid );
        
        cbSnapGrid.setBounds(150,50,10,20);
        jpMain.add(cbSnapGrid);
        
        
        //snap to grid distance
        JLabel lblSnapDistance = new JLabel( "Snap To Grid Distance:" );
        lblSnapDistance.setBounds( 20, 80, 150, 20 );
        jpMain.add( lblSnapDistance );
        
        txtSnapDistance.setBounds(150, 80,150,20);
        jpMain.add(txtSnapDistance);
        
        //color of set objects
        JLabel lblColorSet = new JLabel( "Color of Set:" );
        lblColorSet.setBounds( 20, 110, 150, 20 );
        jpMain.add( lblColorSet );
        
        jcColorSet.setBounds(160,110,140,20);
        for(int i=0;i<16;i++){
            jcColorSet.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorSet);
       
        //color of house object,
        JLabel lblColorHouse = new JLabel( "Color of House:" );
        lblColorHouse.setBounds( 20, 140, 150, 20 );
        jpMain.add( lblColorHouse );
        
        jcColorHouse.setBounds(160,140,140,20);
        for(int i=0;i<16;i++){
            jcColorHouse.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorHouse);
       
        
        //color of bar objects 
        JLabel lblColorBar = new JLabel( "Color of Bar:" );
        lblColorBar.setBounds( 20, 170, 150, 20 );
        jpMain.add( lblColorBar );
       
        jcColorBar.setBounds(160,170,140,20);
        for(int i=0;i<16;i++){
            jcColorBar.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorBar);
       
        
        //color of stage object
        JLabel lblColorStage = new JLabel( "Color of Stage:" );
        lblColorStage.setBounds( 20, 200, 150, 20 );
        jpMain.add( lblColorStage );
        
        jcColorStage.setBounds(160,200,140,20);
        for(int i=0;i<16;i++){
            jcColorStage.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorStage);
       
        
        //enable coloring for instrument objects
        JLabel lblColorInstruments = new JLabel( "Coloring Instruments:" );
        lblColorInstruments.setBounds( 20, 230, 150, 20 );
        jpMain.add( lblColorInstruments );
        
        cbColorLights.setBounds(150,230, 10,20);
        jpMain.add(cbColorLights);
        
        
        //number of photons to store
        JLabel lblPhotons = new JLabel( "Number of Photons:" );
        lblPhotons.setBounds( 320, 20, 150, 20 );
        jpMain.add( lblPhotons );
        
        txtPhotonNumber.setBounds(470,20,150,20);
        jpMain.add(txtPhotonNumber);
        
        
        //color of objects being edited
        JLabel lblColorEdit = new JLabel( "Selected Object Colors:" );
        lblColorEdit.setBounds( 320, 50, 150, 20 );
        jpMain.add( lblColorEdit );
        
        jcColorSelected.setBounds(480,50,140,20);
        for(int i=0;i<16;i++){
            jcColorSelected.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorSelected);
       
        
        
        //color of selected nodes
        JLabel lblColorNode = new JLabel( "Color of Selected Node:" );
        lblColorNode.setBounds( 320, 80, 150, 20 );
        jpMain.add( lblColorNode );
        
        jcColorNodes.setBounds(480,80,140,20);
        for(int i=0;i<16;i++){
            jcColorNodes.addItem(String.valueOf(i));
        }    
        jpMain.add(jcColorNodes);
       
        
        //color of the background for the schematic drawing
        JLabel lblBackground = new JLabel( "Background Color:" );
        lblBackground.setBounds( 320, 110, 150, 20 );
        jpMain.add( lblBackground );
        
        jcBackground.setBounds(480,110,140,20);
        for(int i=0;i<16;i++){
            jcBackground.addItem(String.valueOf(i));
        }    
        jpMain.add(jcBackground);
       
        
        btnSave.setBounds(20, 270, 100,20);
        btnSave.setText("Save");
        btnSave.addActionListener(this);
        jpMain.add(btnSave);
        
        btnCancel.setBounds(140, 270, 100,20);
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this);
        jpMain.add(btnCancel);
        
        getContentPane().add(jpMain);
    }
    
    
    public void saveValues(){
        try{
            //line width
            proj_class.prefLineWidth = Integer.parseInt(txtLineWidth.getText());
            //snapto grid
            proj_class.prefSnapToGrid = cbSnapGrid.getState();
            //snap grid distance
            proj_class.prefSnapToGridValue = Integer.parseInt(txtSnapDistance.getText());
            //color set
            proj_class.prefSetColor = Integer.parseInt((String)jcColorSet.getSelectedItem());
            //color stage
            proj_class.prefStageColor = Integer.parseInt((String)jcColorStage.getSelectedItem());
            //color house
            proj_class.prefHouseColor = Integer.parseInt((String)jcColorHouse.getSelectedItem());
            //color bar
            proj_class.prefBarColor= Integer.parseInt((String)jcColorBar.getSelectedItem());
            //color selected
            proj_class.prefColorSelected = Integer.parseInt((String) jcColorSelected.getSelectedItem());
            //color selected ndoes
            proj_class.prefColorSelectedNode = Integer.parseInt((String) jcColorNodes.getSelectedItem());
        
            //color instruments
            proj_class.prefColorInstruments = cbColorLights.getState();
        
            //color background
            proj_class.prefColorBackground = Integer.parseInt((String) jcBackground.getSelectedItem());
        }catch(Exception e){
            System.out.print("Error Loading data into the preference screen "+e.getMessage());
        } 
    
    }
    
    public void setValues(){
        
        try{
        //line width
        txtLineWidth.setText(String.valueOf(proj_class.prefLineWidth));
        //snapto grid
        cbSnapGrid.setState(proj_class.prefSnapToGrid);
        //snap grid distance
        txtSnapDistance.setText(String.valueOf(proj_class.prefSnapToGridValue));
        //color set
        jcColorSet.setSelectedIndex(proj_class.prefSetColor);
        //color stage
        jcColorStage.setSelectedIndex(proj_class.prefStageColor);
        //color house
        jcColorHouse.setSelectedIndex(proj_class.prefHouseColor);
        //color bar
        jcColorBar.setSelectedIndex(proj_class.prefBarColor);
        //color selected
        jcColorSelected.setSelectedIndex(proj_class.prefColorSelected);
        //color selected ndoes
        jcColorNodes.setSelectedIndex(proj_class.prefColorSelectedNode);
        
        //color instruments
        cbColorLights.setState(proj_class.prefColorInstruments);
        
        //color background
        jcBackground.setSelectedIndex(proj_class.prefColorBackground);
        }catch(Exception e){
            
            System.out.print("Error Loading data into the preference screen " + e.getMessage());
            
        }
    }
    
      public void actionPerformed(java.awt.event.ActionEvent e) {
          if(e.getActionCommand()=="Save"){
              saveValues();
              this.dispose();
          }else{
              this.dispose();
          }
      }
}