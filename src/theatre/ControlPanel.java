package theatre;



import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import Data_Storage.*;



/**
 *
 *
 *
 *
 *
 *
 *
 *  Code Generated: 1/11/2005
 *
 *
 *
 *  Author:         Greg Silverstein
 *
 *
 *
 *  This is the layout for the control panel form
 *
 * edited by Josh Z 3.21.05
 *
 */



public class ControlPanel extends Window implements ActionListener {
    
    
    
    private javax.swing.JRadioButton jRadioButton1;
    
    private javax.swing.JRadioButton jRadioButton2;
    
    private javax.swing.JRadioButton jRadioButton3;
    
    private javax.swing.JRadioButton jRadioButton4;
    
    private javax.swing.JRadioButton jRadioButton5;
    
    private javax.swing.JRadioButton jRadioButton6;
    
    private JPanel myPanel;
    
    private int iWidth = 0;
    
    
    
    
    
    
    
    /** Creates new form ControlPanel */
    
    
    
    public ControlPanel() {
        
        initComponents();
        
        this.setTitle("Control Panel");
        
        iWidth = (BasicWindow.iScreenWidth/5-5);
        
        this.setBounds(BasicWindow.iScreenWidth-(BasicWindow.iScreenWidth/5), BasicWindow.iScreenHeight/2-20, iWidth, BasicWindow.iScreenHeight/2-20);
        
        
        
        this.setVisible(true);
        
    }
    
    
    
    
    
    
    
    private void initComponents() {
        
        
        
        jRadioButton1 = new javax.swing.JRadioButton();
        
        jRadioButton2 = new javax.swing.JRadioButton();
        
        jRadioButton3 = new javax.swing.JRadioButton();
        
        jRadioButton4 = new javax.swing.JRadioButton();
        
        jRadioButton5 = new javax.swing.JRadioButton();
        
        jRadioButton6 = new javax.swing.JRadioButton();
        
        
        myPanel = new JPanel();
        
        myPanel.setLayout(null);
        
        
        
        jRadioButton1.setSize(iWidth,20);
        
        jRadioButton1.setText("View Stage");
        
        jRadioButton1.setBounds(0, 0, 300, 40);
        
        jRadioButton1.setSelected(true);
        
        
        
        myPanel.add(jRadioButton1);
        
        
        
        jRadioButton2.setSize(iWidth,20);
        
        jRadioButton2.setText("View Set Items");
        
        jRadioButton2.setBounds(0, 40, 300, 40);
        
        jRadioButton2.setSelected(true);
        
        
        
        myPanel.add(jRadioButton2);
        
        
        
        jRadioButton3.setSize(iWidth,20);
        
        jRadioButton3.setText("View Bar");
        
        jRadioButton3.setBounds(0, 80, 300, 40);
        
        jRadioButton3.setSelected(true);
        
        
        
        myPanel.add(jRadioButton3);
        
        
        
        jRadioButton4.setSize(iWidth,20);
        
        jRadioButton4.setText("View Bar Instruments");
        
        jRadioButton4.setBounds(0, 120, 300, 40);
        
        jRadioButton4.setSelected(true);
        
        
        
        myPanel.add(jRadioButton4);
        
        
        
        jRadioButton5.setSize(iWidth,20);
        
        jRadioButton5.setText("Instrument Aiming");
        
        jRadioButton5.setBounds(0, 160, 300, 40);
        
        myPanel.add(jRadioButton5);
        
        
        jRadioButton6.setSize(iWidth,20);
        
        jRadioButton6.setText("View Photon Map");
        
        jRadioButton6.setBounds(0, 200, 300, 40);
        
        jRadioButton6.setSelected(false);
        
        myPanel.add(jRadioButton6);
        
        
        
        jRadioButton1.addActionListener(this);
        
        jRadioButton2.addActionListener(this);
        
        jRadioButton3.addActionListener(this);
        
        jRadioButton4.addActionListener(this);
        
        jRadioButton5.addActionListener(this);
        
        jRadioButton6.addActionListener(this);
        
        
        getContentPane().add(myPanel);
        
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        
        project proj_class=(project)project.oClass;
        
        JRadioButton je = (JRadioButton)e.getSource();
        
        
        
        project p = (project)project.oClass;
        
        
        
        if (je.getLabel().equals("View Stage")) {
            
            
            p.setStageVisible(je.isSelected());
            
            
        } else if (je.getLabel().equals("View Set Items")) {
            
            
            p.setSetItemVisible(je.isSelected());
            
            
        } else if (je.getLabel().equals("View Bar")) {
            
            
            p.setBarVisible(je.isSelected());
            
            
        } else if (je.getLabel().equals("View Bar Instruments")) {
            
            
            p.setInstrumentVisible(je.isSelected());
        } else if (je.getLabel().equals("View Instrument Aiming")){
            p.setInstrumentVisible(je.isSelected());
        }
         else if (je.getLabel().equals("View Photon Map")){
            p.bPhotonsVisible=je.isSelected();
        }
        
        proj_class.selected_index=-1;
        
        proj_class.selected_type=-1;
        
        proj_class.forceRepaint();
        
        
        
    }
    
    
    
}



