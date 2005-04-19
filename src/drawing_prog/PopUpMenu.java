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
public class PopUpMenu extends JPopupMenu implements ActionListener {
    JMenuItem edit_house, edit_stage, edit_bar,add_bar, move_bar, move_stage,add_stage;
    JMenuItem add_instrument, add_set, edit_set,move_set,move_house,aim_instrument;
    JMenuItem zoom_in,zoom_out;
    JMenuItem cancel,confirm;
    TransPanel aScreen;
    
    //create the menu depending on the item type that is selected
    public PopUpMenu(TransPanel ascr,int item) {
        aScreen = ascr;
        project proj_class=(project)project.oClass;
        if(proj_class.draw_mouse_state==0){
            
            if(item==0){
                //house selected
                edit_house = new JMenuItem("Edit House Nodes");
                move_house = new JMenuItem("Move House");
                edit_house.addActionListener(this);
                move_house.addActionListener(this);
                
                add(edit_house);
                add(move_house);
            }else if(item==1){
                //stage selected
                edit_stage = new JMenuItem("Edit Stage Nodes");
                move_stage = new JMenuItem("Move Stage");
                
                edit_stage.addActionListener(this);
                move_stage.addActionListener(this);
                
                add(edit_stage);
                add(move_stage);
                
            }else if(item==2){
                //bar selected
                edit_bar = new JMenuItem("Edit Bar");
                move_bar = new JMenuItem("Move Bar");
                add_instrument=new JMenuItem("Add Instrument");
                edit_bar.addActionListener(this);
                move_bar.addActionListener(this);
                add_instrument.addActionListener(this);
                
                add(edit_bar);
                add(move_bar);
                add(add_instrument);
            }else if(item==3){
                //instrument selected
                aim_instrument= new JMenuItem("Aim Instrument");
                aim_instrument.addActionListener(this);
                add(aim_instrument);
            
            }else if(item==4){
                //set selected
                edit_set = new JMenuItem("Edit Set");
                move_set = new JMenuItem("Move Set");
                edit_set.addActionListener(this);
                move_set.addActionListener(this);
                add(edit_set);
                add(move_set);
                
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
        } else if(proj_class.draw_mouse_state==8){
           //adding a stage
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        } else if(proj_class.draw_mouse_state==9){
            //adding a set
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        } else if(proj_class.draw_mouse_state==3){
            //editing a bar
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        } else if(proj_class.draw_mouse_state==4){
          //editing a house
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        } else if(proj_class.draw_mouse_state==5){
           //editing a stage
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel);
        } else if(proj_class.draw_mouse_state==10){
            //editing a set piece
            confirm=new JMenuItem("Commit");
            confirm.addActionListener(this);
            add(confirm);
            
            cancel=new JMenuItem("Cancel");
            cancel.addActionListener(this);
            add(cancel); 
        } else {
            //what ever state was are in we want to be able to get back to state=0
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
    //draw mouse stages are defiend as 0=normal 1=adding a bar 2=adding an instruemnt 3= editing a bar 
    //4=editing a house 5=editing a stage 6=moving a bar 7= moving an instrument 8=adding a stage 
    //9=adding a set peice 10=editing a set piece 11=moving a stage 12=moving a house 13=moving a set
    public void actionPerformed(ActionEvent e) {
        project proj_class=(project)project.oClass;
        if (e.getSource() == edit_house) {
            //System.out.println("editing the house");
            proj_class.draw_mouse_state =4;
            aScreen.temp_house=new house();
            aScreen.temp_house.copyHouse((house)(proj_class.houses.get_object(proj_class.selected_index)));
        } else if (e.getSource() == edit_stage) {
            //System.out.println("editing the stage");
            proj_class.draw_mouse_state =5;
            aScreen.temp_stage=new stage();
            aScreen.temp_stage.copyStage((stage)(proj_class.stages.get_object(proj_class.selected_index)));
        } else if (e.getSource() == edit_bar) {
            //System.out.println("editing the bar");
            proj_class.draw_mouse_state =3;
            aScreen.temp_bar=new bar();
            aScreen.temp_bar.copyBar((bar)(proj_class.bars.get_object(proj_class.selected_index)));
        } else if(e.getSource()==add_bar) {
            //System.out.println("adding a bar");
            proj_class.draw_mouse_state =1;
            aScreen.temp_bar = new bar();
        } else if(e.getSource()==move_bar) {
            //System.out.println("moving a bar");
            proj_class.draw_mouse_state =6;
        } else if(e.getSource()==add_instrument) {
            //System.out.println("Adding an instrument");
            proj_class.draw_mouse_state =2;
            aScreen.temp_instrument = new instrument();
            //temporarily hardcode the nodes for the instruments
            aScreen.temp_instrument.add_node(0,0);
            aScreen.temp_instrument.add_node(20,0);
            aScreen.temp_instrument.add_node(15,5);
            aScreen.temp_instrument.add_node(15,20);
            aScreen.temp_instrument.add_node(5,20);
            aScreen.temp_instrument.add_node(5,5);
            aScreen.temp_instrument.setBarID(proj_class.selected_index);
        } else if(e.getSource()==add_stage) {
            //ADD STAGE
            //System.out.println("adding a stage");
            proj_class.draw_mouse_state =8;
            aScreen.temp_stage = new stage();     
        } else if(e.getSource()==add_set) {
            //ADD SET PIECE
            //System.out.println("adding a stage");
            proj_class.draw_mouse_state =9;
            aScreen.temp_set = new setobject();
        } else if (e.getSource() == edit_set) {
            //System.out.println("editing a set object");
            proj_class.draw_mouse_state =10;
            aScreen.temp_set=new setobject();
            aScreen.temp_set.copySetObject((setobject)(proj_class.sets.get_object(proj_class.selected_index)));
        }else if(e.getSource()==move_bar){
            //move a set object
            proj_class.draw_mouse_state =6;
        }else if(e.getSource()==move_stage){
            //move stage
            proj_class.draw_mouse_state=11;
        }else if(e.getSource()==move_house){
            //move house
            proj_class.draw_mouse_state=12;
        }else if(e.getSource()==move_set){
            //move a set
            proj_class.draw_mouse_state=13;
        }else if(e.getSource()==aim_instrument){
            //aim an instrument
            proj_class.draw_mouse_state=14;
        }else if(e.getSource() == zoom_in) {   //begin chaplin edit.
            if(proj_class.zoom_factor<200){
                proj_class.zoom_factor+=1;
                aScreen.vert.setUnitIncrement((int)((1/(double)proj_class.zoom_factor)*(.05 * aScreen.vert.getHeight())));
                aScreen.horiz.setUnitIncrement((int)((1/(double)proj_class.zoom_factor)*(.05 * aScreen.horiz.getWidth())));
                aScreen.vert.setBlockIncrement((int)((1/(double)proj_class.zoom_factor)*(.25 * aScreen.vert.getHeight())));
                aScreen.horiz.setBlockIncrement((int)((1/(double)proj_class.zoom_factor)*(.25 * aScreen.horiz.getWidth())));
                aScreen.repaint();
            }
        } else if(e.getSource() == zoom_out) {
            if(proj_class.zoom_factor>1){
                proj_class.zoom_factor-=1;
                //aScreen.horiz.setMaximum(600*proj_class.zoom_factor);
                //aScreen.vert.setMaximum(600*proj_class.zoom_factor);
                aScreen.vert.setUnitIncrement((int)((double)(proj_class.zoom_factor)*(.05 * aScreen.vert.getHeight())));
                aScreen.horiz.setUnitIncrement((int)((double)(proj_class.zoom_factor)*(.05 * aScreen.horiz.getWidth())));
                aScreen.vert.setBlockIncrement((int)((double)(proj_class.zoom_factor)*(.25 * aScreen.vert.getHeight())));
                aScreen.horiz.setBlockIncrement((int)((double)(proj_class.zoom_factor)*(.25 * aScreen.horiz.getWidth())));
                //System.out.println("Inside zoom out.\n The zoom factor is: "+proj_class.zoom_factor);
                //System.out.println("the vert unit increment is: "+aScreen.vert.getUnitIncrement()+" the horizontal unit increment"+aScreen.horiz.getUnitIncrement());
                //System.out.println("the vert block increment is: "+aScreen.vert.getBlockIncrement()+"the horizontal unit block increment is:"+aScreen.horiz.getBlockIncrement());
                //end chaplin edit.
                aScreen.repaint();
            }
        } else if(e.getSource() == cancel) {
            if(aScreen.temp_bar!=null){
                aScreen.temp_bar=null;
            }
            if(aScreen.temp_stage!=null){
                aScreen.temp_stage=null;
            }
            if(aScreen.temp_instrument!=null){
                aScreen.temp_instrument=null;
            }
            if(aScreen.temp_set!=null){
                aScreen.temp_set=null;
            }
            if(aScreen.temp_house!=null){
                aScreen.temp_house=null;
            }
            
            proj_class.draw_mouse_state=0;
        } else if(e.getSource()==confirm){
            if(proj_class.draw_mouse_state==8){
                //commit the adding of the stage
                proj_class.addStage(aScreen.temp_stage);
                aScreen.temp_stage=null;
                proj_class.draw_mouse_state=0;
            }else if(proj_class.draw_mouse_state==9){
                proj_class.addSet(aScreen.temp_set);
                aScreen.temp_set=null;
                proj_class.draw_mouse_state=0;
            }else if(proj_class.draw_mouse_state==3){
                //editing a bar
                proj_class.SetBar(aScreen.temp_bar,proj_class.selected_index);
                aScreen.temp_bar=null;
                proj_class.draw_mouse_state=0;
                
            }else if(proj_class.draw_mouse_state==4){
                //editing a house
                proj_class.SetHouse(aScreen.temp_house,proj_class.selected_index);
                aScreen.temp_house=null;
                proj_class.draw_mouse_state=0;
            }else if(proj_class.draw_mouse_state==5){
                //editing a stage
                proj_class.SetStage(aScreen.temp_stage,proj_class.selected_index);
                aScreen.temp_stage=null;
                proj_class.draw_mouse_state=0;
            }else if(proj_class.draw_mouse_state==10){
                //editing a set object
                proj_class.SetSetObject(aScreen.temp_set,proj_class.selected_index);
                aScreen.temp_set=null;
                proj_class.draw_mouse_state=0;
            }
            
        }
        
        aScreen.repaint();
    }
}


