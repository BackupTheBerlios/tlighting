/*
 * StageInfoWindow.java
 *
 * Created on May 5, 2005, 5:04 PM
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
public class StageInfoWindow extends JDialog implements ActionListener {
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    /** Creates a new instance of StageInfoWindow */
    //editable info
    
    //name
    //nodes
    //height
    //notes;
    //description;
    
    private JTextField jName= new JTextField();
    private JTextField jHeight= new JTextField();
    private JTextField jNotes= new JTextField();
    private JTextField jDesc= new JTextField();
    private project proj_class;
    
    
    public StageInfoWindow() {
        super(BasicWindow.curWindow,"Stage Info", true);
        //setTitle("Inventory Manager");
        proj_class=(project)project.oClass;
        
        addComponents();
        populateComponents();
        setBounds(10,50, iScreenHeight,iScreenWidth);
        //setSize(500,500);
        setResizable(true);
        setVisible(true);
    }
    
    public void addComponents() {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(null);
        
        
        
        JLabel lblDesc = new JLabel( "Description:" );
        lblDesc.setBounds( 50, 60, 150, 20 );
        jpMain.add( lblDesc );
        
        jDesc = new JTextField();
        jDesc.setBounds( 250, 60, 250, 20 );
        jpMain.add( jDesc );
        
        
        JLabel lblNotes = new JLabel( "Notes:" );
        lblNotes.setBounds( 50, 90, 150, 20 );
        jpMain.add( lblNotes );
        
        jNotes = new JTextField();
        jNotes.setBounds( 250, 90, 250, 20 );
        jpMain.add( jNotes );
        
        JLabel lblHeight = new JLabel( "Height:" );
        lblHeight.setBounds( 50, 120, 150, 20 );
        jpMain.add( lblHeight );
        
        jHeight = new JTextField();
        jHeight.setBounds( 250, 120, 250, 20 );
        jpMain.add( jHeight );
        
        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        jbOk.setActionCommand("ok");
        jbOk.setBounds(50, 150, 100, 20);
        jpMain.add(jbOk);
        
        JButton jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        jbCancel.setBounds(200, 150, 100, 20);
        jpMain.add(jbCancel);
        
        getContentPane().add(jpMain);
    }
    
    public void populateComponents(){
        stage temps=((stage)proj_class.stages.get_object(0));
        
        if(temps!=null){
            
            jHeight.setText(String.valueOf(temps.getheight()));
            jNotes.setText(temps.getnotes());
            jDesc.setText(temps.getdescription());
            
        }
        
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            ((stage)proj_class.stages.get_object(0)).setdescription(jDesc.getText());
            
            ((stage)proj_class.stages.get_object(0)).setheight(Integer.parseInt(jHeight.getText()));
            
            ((stage)proj_class.stages.get_object(0)).setnotes(jNotes.getText());
            
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            
            this.dispose();
        }
    }
}
