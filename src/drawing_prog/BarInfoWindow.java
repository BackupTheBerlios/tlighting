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
public class BarInfoWindow extends JDialog implements ActionListener{
    
     protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    
    private JTextField jName= new JTextField();
    private JTextField jHeight= new JTextField();
    private JTextField JY= new JTextField();
    private JTextField JX= new JTextField();
  
    private JTextField jDimmers = new JTextField();
    
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
        jName.setBounds( 100, 30, 250, 20 );
        jpMain.add( jName );      
        
        JLabel lblX = new JLabel( "X:" );
        lblX.setBounds( 50, 90, 150, 20 );
        jpMain.add( lblX );
        
        JX = new JTextField();
        JX.setBounds( 100, 90, 250, 20 );
        jpMain.add( JX );
        
        JLabel lblY = new JLabel( "Y:" );
        lblY.setBounds( 50, 120, 150, 20 );
        jpMain.add( lblY );
        
        JY = new JTextField();
        JY.setBounds( 100, 120, 250, 20 );
        jpMain.add( JY );
        
        
        JLabel lblDim = new JLabel( "Dimmers:" );
        lblDim.setBounds( 50, 150, 150, 20 );
        jpMain.add( lblDim );
        
        jDimmers = new JTextField();
        jDimmers.setBounds( 100, 150, 250, 20 );
        jpMain.add( jDimmers );
        
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
        
        if(proj_class.selected_type==2){
            bar tempb=((bar)proj_class.bars.get_object(proj_class.selected_index));
            if(tempb!=null){
                jName.setText(tempb.getID());
            JX.setText( String.valueOf(tempb.worldx));
            JY.setText( String.valueOf(tempb.worldy));
            
            String dimmerText="";
            for(int i=0;i<tempb.getNum_dimmers();i++){
                dimmerText+=String.valueOf(tempb.getDimmer(i))+",";
            }
                jDimmers.setText(dimmerText);
            }
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try{
                
                int x=Integer.parseInt(JX.getText());
                int y=Integer.parseInt(JY.getText());
                
                //parse the bar numbers from string
                String strLeft = jDimmers.getText();
                int dimindex=0;
                while(strLeft.indexOf(",")!=-1){
                    int index=strLeft.indexOf(",");
                    String numstr;
                    if(index<strLeft.length()){
                        if(index>0){
                            numstr=strLeft.substring(0, index);
                            strLeft=strLeft.substring(index,strLeft.length());
                            ((bar)proj_class.bars.get_object(proj_class.selected_index)).setDimmers(dimindex,Integer.parseInt(numstr));
                            dimindex++;
                        }
                        
                    }
                }
                ((bar)proj_class.bars.get_object(proj_class.selected_index)).setNumDim(dimindex);
                
            }catch(Exception ex){
                System.out.println("Error editing bar error:"+ex.getMessage());
            }
            this.dispose();
        }else if (e.getActionCommand().equals("cancel")) {
            this.dispose();
            
        }
    }
}
