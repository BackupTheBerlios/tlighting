/*
 * InstrumentInfoWindow.java
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
public class InstrumentInfoWindow extends JDialog implements ActionListener{
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    private JTextField jDesc= new JTextField();
    private JTextField jName= new JTextField();
    private JTextField Jaimx= new JTextField();
    private JTextField Jaimy= new JTextField();
    private JTextField Jaimz= new JTextField();
    private JTextField JR= new JTextField();
    private JTextField JG= new JTextField();
    private JTextField JB= new JTextField();
    private JTextField JRad= new JTextField();
    private JTextField jHeight= new JTextField();
    private JComboBox jDimmer = new JComboBox();
    
    private project proj_class;
    /** Creates a new instance of InstrumentInfoWindow */
    public InstrumentInfoWindow() {
        super(BasicWindow.curWindow,"Instrument Info", true);
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
        jName.setBounds( 100, 30, 250, 20 );
        jpMain.add( jName );
        
        
        JLabel lblDesc = new JLabel( "Description:" );
        lblDesc.setBounds( 50, 60, 150, 20 );
        jpMain.add( lblDesc );
        
        jDesc = new JTextField();
        jDesc.setBounds( 200, 60, 250, 20 );
        jpMain.add( jDesc );
        
        JLabel lblaimx = new JLabel( "Aim X:" );
        lblaimx.setBounds( 50, 90, 150, 20 );
        jpMain.add( lblaimx );
        
        Jaimx = new JTextField();
        Jaimx.setBounds( 200, 90, 250, 20 );
        jpMain.add( Jaimx );
        
        
        JLabel lblaimy = new JLabel( "Aim Y:" );
        lblaimy.setBounds( 50, 120, 150, 20 );
        jpMain.add( lblaimy );
        
        Jaimy = new JTextField();
        Jaimy.setBounds( 200, 120, 250, 20 );
        jpMain.add( Jaimy );
        
        JLabel lblaimz = new JLabel( "Aim Z:" );
        lblaimz.setBounds( 50, 140, 150, 20 );
        jpMain.add( lblaimz );
        
        Jaimz = new JTextField();
        Jaimz.setBounds( 200, 140, 250, 20 );
        jpMain.add( Jaimz );
        
        JLabel lblrad = new JLabel( "Radius:" );
        lblrad.setBounds( 50, 170, 150, 20 );
        jpMain.add( lblrad );
        
        JRad = new JTextField();
        JRad.setBounds( 200, 170, 250, 20 );
        jpMain.add( JRad );
        
        
        JLabel lblh = new JLabel( "Height:" );
        lblh.setBounds( 500, 60, 150, 20 );
        jpMain.add( lblh );
        
        jHeight = new JTextField();
        jHeight.setBounds( 650, 60, 250, 20 );
        jpMain.add( jHeight );
        
        
        
        JLabel lblR = new JLabel( "Red Color:" );
        lblR.setBounds( 500, 90, 150, 20 );
        jpMain.add( lblR );
        
        JR = new JTextField();
        JR.setBounds( 650, 90, 250, 20 );
        jpMain.add( JR );
        
        JLabel lblG = new JLabel( "Green Color:" );
        lblG.setBounds( 500, 120, 150, 20 );
        jpMain.add( lblG );
        
        JG = new JTextField();
        JG.setBounds( 650, 120, 250, 20 );
        jpMain.add( JG );
        
        
        JLabel lblB = new JLabel( "Blue:" );
        lblB.setBounds( 500, 150, 150, 20 );
        jpMain.add( lblB );
        
        JB = new JTextField();
        JB.setBounds( 650, 150, 250, 20 );
        jpMain.add( JB );
        
        JLabel lblDim = new JLabel( "Dimmer:" );
        lblDim.setBounds( 500, 180, 150, 20 );
        jpMain.add( lblDim );
        
        jDimmer = new JComboBox();
        jDimmer.setBounds( 650, 180, 250, 20 );
        if(proj_class.selected_type==3){
            int barid=((instrument)proj_class.instruments.get_object(proj_class.selected_index)).getBarID();
            bar tempbar=(bar)proj_class.bars.get_object(barid);
        
            for(int q=0;q<tempbar.getNum_dimmers();q++){
                jDimmer.addItem(String.valueOf(tempbar.getDimmer(q)));
            }
        }
        jpMain.add( jDimmer );
        
        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        jbOk.setActionCommand("ok");
        jbOk.setBounds(50, 200, 100, 20);
        jpMain.add(jbOk);
        
        JButton jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        jbCancel.setBounds(150, 200, 100, 20);
        jpMain.add(jbCancel);
        
        getContentPane().add(jpMain);
    }
    
    public void populateComponents(){
        
        instrument tempi=((instrument)proj_class.instruments.get_object(proj_class.selected_index));
        if(tempi!=null){
            jDesc.setText( String.valueOf(tempi.getDescription()));
            jName.setText( String.valueOf(tempi.getInventoryID()));
            Jaimx.setText( String.valueOf(tempi.aimx));
            Jaimy.setText( String.valueOf(tempi.aimy));
            Jaimz.setText( String.valueOf(tempi.aimz));
            JR.setText( String.valueOf(tempi.R));
            JG.setText( String.valueOf(tempi.G));
            JB.setText( String.valueOf(tempi.B));
            jHeight.setText(String.valueOf(tempi.worldz));
            JRad.setText(String.valueOf(tempi.radius));
            
            //select the appropriate dimmer #
            instrument ti=((instrument)proj_class.instruments.get_object(proj_class.selected_index));
            for(int i=0;i<jDimmer.getItemCount();i++){
                try{
                    int dimn=Integer.parseInt(jDimmer.getItemAt(i).toString());
                    
                if(dimn==ti.Associated_dimmerID){
                    jDimmer.setSelectedIndex(i);
                }
                }catch(Exception ex){
                    System.out.println("error a dimmer number could not be parsed into an integer");
                }
                
            }
            
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try{
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).setDescription(jDesc.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).setDescription(jName.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).R=Float.parseFloat(JR.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).G=Float.parseFloat(JG.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).B=Float.parseFloat(JB.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimx=Integer.parseInt(Jaimx.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimy=Integer.parseInt(Jaimy.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimz=Integer.parseInt(Jaimz.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).worldz=Integer.parseInt(jHeight.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).radius=Integer.parseInt(JRad.getText());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).Associated_dimmerID=Integer.parseInt(jDimmer.getSelectedItem().toString());
                
            }catch(Exception ex){
                System.out.println("Error editing instrument error:"+ex.getMessage());
            }
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            this.dispose();
            
        }
    }
}
