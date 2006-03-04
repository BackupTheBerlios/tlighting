/*
 * BarInfoWindow.java
 *
 * Created on September 1, 2005, 6:24 PM
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
public class BarInfoWindow extends JDialog implements ActionListener, ItemListener{
    
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    private JTextField jName= new JTextField();
    private JTextField jHeight= new JTextField();
    private JTextField JY= new JTextField();
    private JTextField JX= new JTextField();
    
    private JTextField jDimmers = new JTextField();
    private JCheckBox jcbLights = new JCheckBox();
    private boolean changeLightHeights=false;
    private project proj_class;
    /** Creates a new instance of InstrumentInfoWindow */
    public BarInfoWindow() {
        super(BasicWindow.curWindow,"Bar Info", true);
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
        jName.setBounds( 120, 30, 250, 20 );
        jpMain.add( jName );
        
        JLabel lblX = new JLabel( "X:" );
        lblX.setBounds( 50, 90, 150, 20 );
        jpMain.add( lblX );
        
        JX = new JTextField();
        JX.setBounds( 120, 90, 250, 20 );
        jpMain.add( JX );
        
        JLabel lblY = new JLabel( "Y:" );
        lblY.setBounds( 50, 120, 150, 20 );
        jpMain.add( lblY );
        
        JY = new JTextField();
        JY.setBounds( 120, 120, 250, 20 );
        jpMain.add( JY );
        
        JLabel lblHeight=new JLabel("Height:");
        lblHeight.setBounds(50, 150, 150,20);
        jpMain.add(lblHeight);
        
        jHeight=new JTextField();
        jHeight.setBounds(120,150, 150,20);
        jpMain.add(jHeight);
        
        JLabel lblDim = new JLabel( "Dimmers:" );
        lblDim.setBounds( 50, 180, 150, 20 );
        jpMain.add( lblDim );
        
        jDimmers = new JTextField();
        jDimmers.setBounds( 120, 180, 250, 20 );
        jpMain.add( jDimmers );
        
        jcbLights= new JCheckBox("Change Light Height");
        jcbLights.setBounds(50, 210, 200, 20);
        jcbLights.addItemListener(this);
        jpMain.add(jcbLights);
        
        
        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        jbOk.setActionCommand("ok");
        jbOk.setBounds(50, 240, 100, 20);
        jpMain.add(jbOk);
        
        JButton jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);
        jbCancel.setActionCommand("cancel");
        jbCancel.setBounds(150, 240, 100, 20);
        jpMain.add(jbCancel);
        
        getContentPane().add(jpMain);
    }
    
    public void populateComponents(){
        
        if(proj_class.selected_type==2){
            bar tempb=((bar)proj_class.bars.get_object(proj_class.selected_index));
            if(tempb!=null){
                jName.setText(tempb.getName());
                JX.setText( String.valueOf(tempb.worldx));
                JY.setText( String.valueOf(tempb.worldy));
                jHeight.setText(String.valueOf(tempb.getZ(0)));
                String dimmerText=tempb.getDimmerString();
              
                jDimmers.setText(dimmerText);
            }
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try{
                int x=0;
                try{
                x=Integer.parseInt(JX.getText());
                }catch(Exception ex){
                    System.out.println("Error x value for bar is not an integer");
                }
                
                int xd;
                
                int y=0;
                try{
                y=Integer.parseInt(JY.getText());
                }catch(Exception ex){
                    System.out.println("Error y value for bar is not an integer");
                }
                
                int yd;
                
                int h=0;
                try{
                    h=Integer.parseInt(jHeight.getText());
                }catch(Exception ex){
                    System.out.println("Error h value for bar is not an integer");    
                }
                
                
                int oldx1,oldy1;
                
                //parse the bar numbers from string
                String strLeft = jDimmers.getText();
                int dimindex=0;
                while(strLeft.indexOf(",")!=-1){
                    int index=strLeft.indexOf(",");
                    String numstr;
                    if(index<strLeft.length()){
                        if(index>0){
                            numstr=strLeft.substring(0, index);
                            strLeft=strLeft.substring(index+1,strLeft.length());
                            int ndim=((bar)proj_class.bars.get_object(proj_class.selected_index)).getNum_dimmers()+1;
                            ((bar)proj_class.bars.get_object(proj_class.selected_index)).setNumDim(ndim);
                            ((bar)proj_class.bars.get_object(proj_class.selected_index)).setDimmers(dimindex,Integer.parseInt(numstr));
                            dimindex++;
                        }
                        
                    }
                }
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).setNumDim(dimindex);
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).worldx=x;
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).worldy=y;
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).setZ(0, h);
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).setZ(1, h);
                if(changeLightHeights){
                    //go through all the lights connected to the bar and change thier heights
                    System.out.println("Changing light heights to match the bar");
                    for(int i=0;i<proj_class.instruments.get_num_objects();i++){
                        instrument curInst=(instrument)proj_class.instruments.get_object(i);
                        bar curBar=((bar)proj_class.bars.get_object(proj_class.selected_index));
                        if(curInst.getBarID()==curBar.getID()){
                            curInst.worldz=curBar.getZ(0);
                            
                        }
                    }
                    
                }
                
            }catch(Exception ex){
                System.out.println("Error editing bar error:"+ex.getMessage());
            }
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            this.dispose();
            
        }
    }
    
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if(source==jcbLights){
            changeLightHeights=!changeLightHeights;
        }
    }
}
