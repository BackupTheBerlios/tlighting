/*
 * SetInfoWindow.java
 *
 * Created on May 5, 2005, 5:03 PM
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
public class SetInfoWindow extends JDialog implements ActionListener{
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    private JTextField jName= new JTextField();
    private JTextField jDesc= new JTextField();
    private JTextField jHeight= new JTextField();
    private project proj_class;
    
    /** Creates a new instance of SetInfoWindow */
    public SetInfoWindow() {
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
        
        JLabel lblName = new JLabel( "Name:" );
        lblName.setBounds( 50, 30, 150, 20 );
        jpMain.add( lblName );
        
        jName = new JTextField();
        jName.setBounds( 250, 30, 250, 20 );
        jpMain.add( jName );
        
        
        JLabel lblDesc = new JLabel( "Description:" );
        lblDesc.setBounds( 50, 60, 200, 20 );
        jpMain.add( lblDesc );
        
        jDesc = new JTextField();
        jDesc.setBounds( 250, 60, 250, 20 );
        jpMain.add( jDesc );
        
        JLabel lblHeight = new JLabel( "Height:" );
        lblHeight.setBounds( 50, 90, 200, 20 );
        jpMain.add( lblHeight );
        
        jHeight = new JTextField();
        jHeight.setBounds( 250, 90, 250, 20 );
        jpMain.add( jHeight );
        
        
        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        jbOk.setActionCommand("ok");
        jbOk.setBounds(50, 120, 150, 20);
        jpMain.add(jbOk);
        
        JButton jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        jbCancel.setBounds(200, 120, 150, 20);
        jpMain.add(jbCancel);
        
        getContentPane().add(jpMain);
    }
    
    public void populateComponents(){
        setobject temps= ((setobject)proj_class.sets.get_object(proj_class.selected_index));
        if(temps!=null){
            
            jName.setText(temps.getname());
            jDesc.setText(temps.getdescription());
            jHeight.setText(String.valueOf(temps.getsize()));
        }
        
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            ((setobject)proj_class.sets.get_object(proj_class.selected_index)).setname(jName.getText());
            ((setobject)proj_class.sets.get_object(proj_class.selected_index)).setdescription(jDesc.getText());
            ((setobject)proj_class.sets.get_object(proj_class.selected_index)).setsize(Integer.parseInt(jHeight.getText()));
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            this.dispose();
        }
    }
}
