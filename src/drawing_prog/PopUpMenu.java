/*
 * PopUpMenu.java
 *
 * Created on January 30, 2005, 12:03 AM
 */

package drawing_prog;

/**
 *
 * @author jzawisla
 */
//standard java classes
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//custom classes
import Data_Storage.*;
import theatre.*;

//class to display and handle the menu on the draw screen
public class PopUpMenu extends JPopupMenu implements ActionListener
{
    JMenuItem edit_house, edit_stage, edit_bar,add_bar, move_bar, move_stage,add_stage;
    JMenuItem add_instrument, add_set, edit_set;
    JMenuItem zoom_in,zoom_out;
    JMenuItem cancel,confirm;
    TransPanel aScreen;

    //create the menu depending on the item type that is selected
    public PopUpMenu(TransPanel ascr,int item)
    {
        aScreen = ascr;
        project proj_class=(project)project.oClass;
        if(proj_class.draw_mouse_state==0){    

            if(item==0){    
                edit_house = new JMenuItem("Edit House Nodes");
                edit_house.addActionListener(this);
                add(edit_house);
            }else if(item==1){
                edit_stage = new JMenuItem("Edit Stage Nodes");
                move_stage = new JMenuItem("Move Stage Here");

                edit_stage.addActionListener(this);
                move_stage.addActionListener(this);

                add(edit_stage);
                add(move_stage);

            }else if(item==2){
                edit_bar = new JMenuItem("Edit Bar");
                move_bar = new JMenuItem("Move Bar");
                add_instrument=new JMenuItem("Add Instrument");
                edit_bar.addActionListener(this);
                move_bar.addActionListener(this);
                add_instrument.addActionListener(this); 

                add(edit_bar);    
                add(move_bar);    
                add(add_instrument);
            }

            addSeparator();
            if(proj_class.houseadded){
                if(!proj_class.stageadded){
                    add_stage=new JMenuItem("Add Stage");
                    add_stage.addActionListener(this);
                    add(add_stage);    
                }
                add_bar=new JMenuItem("Add Bar");
                add_bar.addActionListener(this);
                add(add_bar);
                
                add_set=new JMenuItem("Add Set");
                add_set.addActionListener(this);
                add(add_set);
            }
        }
        else if(proj_class.draw_mouse_state==8){
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        }
        else if(proj_class.draw_mouse_state==9){
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        }
        else
        {
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        }
        addSeparator();
        zoom_in = new JMenuItem("Zoom In");
        zoom_out = new JMenuItem("Zoom Out");
            
        zoom_in.addActionListener(this);
        zoom_out.addActionListener(this);
            
        add(zoom_in);    
        add(zoom_out);    
        
        
        
    }

    public void actionPerformed(ActionEvent e)
    {
        project proj_class=(project)project.oClass;
    	if (e.getSource() == edit_house)
    	{
    	    System.out.println("editing the house");
    	    //useRightButton.display.setText("Hello!");
    	}
    	else if (e.getSource() == edit_stage)
    	{
    	    System.out.println("editing the stage");
    	    //useRightButton.display.setText("Hello! Hello!");
    	}
    	else if (e.getSource() == edit_bar)
    	{
    	    System.out.println("editing the bar");
    	    //useRightButton.display.setText("Bye Bye!");
    	}
        else if(e.getSource()==add_bar)
        {
            //System.out.println("adding a bar");
            proj_class.draw_mouse_state =1;
            aScreen.temp_bar = new bar();
        }
         else if(e.getSource()==move_bar)
        {
            System.out.println("moving a bar");
            proj_class.draw_mouse_state =6;
        }
        else if(e.getSource()==add_instrument)
        {
            System.out.println("Adding an instrument");
            proj_class.draw_mouse_state =2;
            aScreen.temp_instrument = new instrument();
            //temporarily hardcode the nodes for the instruments
            aScreen.temp_instrument.add_node(0,0);
            aScreen.temp_instrument.add_node(20,0);
            aScreen.temp_instrument.add_node(15,5);
            aScreen.temp_instrument.add_node(15,20);
            aScreen.temp_instrument.add_node(5,20);
            aScreen.temp_instrument.add_node(5,5);
            aScreen.temp_instrument.setBarID(aScreen.proj_class.selected_index);
            
            
        }
        else if(e.getSource()==add_stage)
        {
            //ADD STAGE
            //System.out.println("adding a stage");
            proj_class.draw_mouse_state =8;
            aScreen.temp_stage = new stage();
            
            
        }
        else if(e.getSource()==add_set)
        {
            //ADD SET PIECE
            //System.out.println("adding a stage");
            proj_class.draw_mouse_state =9;
            aScreen.temp_set = new setobject();    
            
        }
        else if(e.getSource() == zoom_in)
        {
            
            proj_class.zoom_factor+=1;
            aScreen.horiz.setMaximum(600*aScreen.proj_class.zoom_factor);
            aScreen.vert.setMaximum(600*aScreen.proj_class.zoom_factor);
        
        }
        else if(e.getSource() == zoom_out)
        {
            proj_class.zoom_factor-=.5; 
            aScreen.horiz.setMaximum(600*aScreen.proj_class.zoom_factor);
            aScreen.vert.setMaximum(600*aScreen.proj_class.zoom_factor);
        
        
        }
        else if(e.getSource() == cancel)
        {
            proj_class.draw_mouse_state=0;
        }
        else if(e.getSource()==confirm){
            if(proj_class.draw_mouse_state==8){
                //commit the adding of the stage
                proj_class.addStage(aScreen.temp_stage);
                aScreen.temp_stage=null;
                proj_class.draw_mouse_state=0;
            }else if(proj_class.draw_mouse_state==9){
                proj_class.addSet(aScreen.temp_set);
                aScreen.temp_set=null;
                proj_class.draw_mouse_state=0;
            }
            
        }
        
        aScreen.repaint();
    }
}


