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
    JMenuItem remove_house, remove_bar, remove_set, remove_instr, remove_stage;//check these against object list.
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
                
                remove_house = new JMenuItem("Remove House");
                
                edit_house.addActionListener(this);
                move_house.addActionListener(this);
                
                remove_house.addActionListener(this);
                
                add(edit_house);
                add(move_house);
                add(remove_house);
            }else if(item==1){
                //stage selected
                edit_stage = new JMenuItem("Edit Stage Nodes");
                move_stage = new JMenuItem("Move Stage");
                
                remove_stage = new JMenuItem("Remove Stage");
                
                edit_stage.addActionListener(this);
                move_stage.addActionListener(this);
                
                remove_stage.addActionListener(this);
                
                add(edit_stage);
                add(move_stage);
                add(remove_stage);
                
            }else if(item==2){
                //bar selected
                edit_bar = new JMenuItem("Edit Bar");
                move_bar = new JMenuItem("Move Bar");
                
                remove_bar = new JMenuItem("Remove Bar");
                
                add_instrument = new JMenuItem("Add Instrument");
                
                edit_bar.addActionListener(this);
                move_bar.addActionListener(this);
                remove_bar.addActionListener(this);
                add_instrument.addActionListener(this);
                
                add(edit_bar);
                add(move_bar);
                
                add(remove_bar);
                
                add(add_instrument);
            }else if(item==3){
                //instrument selected
                aim_instrument = new JMenuItem("Aim Instrument");
                
                remove_instr = new JMenuItem("Remove Instrument");
                
                aim_instrument.addActionListener(this);
                
                remove_instr.addActionListener(this);
                
                add(aim_instrument);
                
                add(remove_instr);
            }else if(item==4){
                //set selected
                edit_set = new JMenuItem("Edit Set");
                move_set = new JMenuItem("Move Set");
                remove_set = new JMenuItem("Remove Set");
                
                edit_set.addActionListener(this);
                move_set.addActionListener(this);
                remove_set.addActionListener(this);
                
                add(edit_set);
                add(move_set);
                add(remove_set);
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
    public void actionPerformed(ActionEvent e)
    {
        project proj_class=(project)project.oClass;
        if (e.getSource() == edit_house)
        {
            //System.out.println("editing the house");
            proj_class.draw_mouse_state =4;
            aScreen.temp_house=new house();
            aScreen.temp_house.copyHouse((house)(proj_class.houses.get_object(proj_class.selected_index)));
        }
        else if (e.getSource() == edit_stage)
        {
            //System.out.println("editing the stage");
            proj_class.draw_mouse_state =5;
            aScreen.temp_stage=new stage();
            aScreen.temp_stage.copyStage((stage)(proj_class.stages.get_object(proj_class.selected_index)));
        }
        else if (e.getSource() == edit_bar)
        {
            //System.out.println("editing the bar");
            proj_class.draw_mouse_state =3;
            aScreen.temp_bar=new bar();
            aScreen.temp_bar.copyBar((bar)(proj_class.bars.get_object(proj_class.selected_index)));
        }
        else if(e.getSource()==add_bar)
        {
            //System.out.println("adding a bar");
            proj_class.draw_mouse_state =1;
            aScreen.temp_bar = new bar();
        }
        else if(e.getSource()==move_bar)
        {
            //System.out.println("moving a bar");
            proj_class.draw_mouse_state =6;
        }
        else if(e.getSource()==add_instrument)
        {
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
        }
        else if (e.getSource() == remove_stage)
        {//begin chaplin edit.
             //proj_class.stages.object_list.removeElementAt(proj_class.stages.object_list.indexOf(proj_class.stages.object_list.get(proj_class.selected_index)));
            //proj_class.stages.set_num_objects(proj_class.stages.object_list.size());
            
            proj_class.stages.remove_object(proj_class.selected_index);
            
            proj_class.selected_index = -1;
            proj_class.selected_type = -1;
            proj_class.draw_mouse_state = 0;
            proj_class.stageadded = false;
            //proj_class.draw_mouse_state = 15;
        }
        else if (e.getSource() == remove_set)
        {
            //proj_class.sets.object_list.removeElementAt(proj_class.sets.object_list.indexOf(proj_class.sets.object_list.get(proj_class.selected_index)));
            //proj_class.sets.set_num_objects(proj_class.sets.object_list.size());
            
            proj_class.sets.remove_object(proj_class.selected_index);
            
            proj_class.selected_index = -1;
            proj_class.selected_type = -1;
            proj_class.draw_mouse_state = 0;
        }
        else if (e.getSource() == remove_bar)
        {
            int barIndex = 0;            
            
            if(proj_class.instruments.object_list.size() > 0)//then there are instruments in array.
            {
                int size = proj_class.instruments.object_list.size(), i = 0;                                
                barIndex = proj_class.selected_index;
                while(i < size)
                {                    
                    instrument aInstrument =(instrument)proj_class.instruments.object_list.get(i);
                    int barId = aInstrument.getBarID();
                    if(aInstrument.getBarID() >= 0)
                    {             
                         if(barId==barIndex)
                         {
                            //proj_class.instruments.object_list.removeElementAt(proj_class.instruments.object_list.indexOf(proj_class.instruments.object_list.get(i)));
                            //proj_class.instruments.set_num_objects(proj_class.instruments.object_list.size()-1);
                                proj_class.instruments.remove_object(i);
                             size -= 1;                                                                                  
                         }    
                         else
                         {
                             i++;
                         }
                    }
                 }                              
            }            
            proj_class.bars.remove_object(proj_class.selected_index);
            //proj_class.bars.set_num_objects(proj_class.bars.get_num_objects()-1);
            //remove the bar itself
            for(int j = 0; j < proj_class.instruments.object_list.size(); j++)
            {
               //need the start point.
               instrument tempInst = (instrument)proj_class.instruments.get_object(j);  
               if(tempInst.Associated_barID > barIndex)
                   tempInst.Associated_barID -= 1;
            }
             //mod bar id as well.
            for(int k = 0; k < proj_class.bars.object_list.size(); k++)
            {
              bar tempBar = (bar)proj_class.bars.object_list.get(k);  
              if(tempBar.index > barIndex)
                  tempBar.index -= 1;
            }                          
            proj_class.selected_index = -1;
            proj_class.selected_type = -1;
            proj_class.draw_mouse_state = 0;
            System.out.println("Selected Type action performed: "+proj_class.selected_type+"\n");
            System.out.println("Selected Index action performed: "+proj_class.selected_index+"\n");
            System.out.println("Draw mouse state action performed: "+proj_class.draw_mouse_state);
            
        }
        else if (e.getSource() == remove_instr)
        {
            //System.out.println("Size is: "+proj_class.instruments.object_list.size());
            //System.out.println("Number of objects: "+proj_class.instruments.get_num_objects()+"\n");
            //proj_class.instruments.object_list.removeElementAt(proj_class.instruments.object_list.indexOf(proj_class.instruments.object_list.get(proj_class.selected_index)));
            //proj_class.instruments.set_num_objects(proj_class.instruments.object_list.size());
            
            //System.out.println("Size is: "+proj_class.instruments.object_list.size());
            //System.out.println("Number of objects: "+proj_class.instruments.get_num_objects()+"\n");
            int index=proj_class.getInstrumentByID(((instrument)proj_class.instruments.get_object(proj_class.selected_index)).getInventoryID());
            proj_class.inventories.setItemUsed(index,false);
            proj_class.instruments.remove_object(proj_class.selected_index);
            
            proj_class.selected_index = -1;
            proj_class.selected_type = -1;
            proj_class.draw_mouse_state = 0;
            //proj_class.draw_mouse_state = 18;
        }
        else if (e.getSource() == remove_house)
        {   //loop over instruments, bars, stage et. to get rid of.
            
            System.out.println("*Before* Instr size is: "+proj_class.instruments.object_list.size());
            while(proj_class.instruments.object_list.size() > 0)
            {    
                //proj_class.instruments.object_list.remove(0);
                //proj_class.instruments.set_num_objects(proj_class.instruments.object_list.size());
                int inv_index=proj_class.getInstrumentByID(((instrument)proj_class.instruments.get_object(0)).getInventoryID());
                proj_class.inventories.setItemUsed(inv_index,false);
                proj_class.instruments.remove_object(0);
            }   
            
            System.out.println("*After* Instr size is: "+proj_class.instruments.object_list.size());
            while(proj_class.bars.object_list.size() > 0)
            {    
                proj_class.bars.remove_object(0);
                //proj_class.bars.set_num_objects(proj_class.bars.object_list.size());
            } 
            while(proj_class.sets.object_list.size() > 0)
            {
                proj_class.sets.remove_object(0);
                //proj_class.sets.set_num_objects(proj_class.sets.object_list.size());
            }    
            while(proj_class.stages.object_list.size() > 0)
             {    
                proj_class.stages.remove_object(0);
                //proj_class.stages.set_num_objects(proj_class.stages.object_list.size());
             }
            proj_class.stageadded = false;
            while(proj_class.houses.object_list.size() > 0)
            {
                proj_class.houses.remove_object(0);
                //proj_class.houses.set_num_objects(proj_class.houses.object_list.size());
            }
            proj_class.selected_type = -1;
            proj_class.selected_index = -1;
            proj_class.houseadded = false;
            //proj_class.draw_mouse_state = 19;
        }
        else if(e.getSource() == zoom_in)
        {   //begin chaplin edit.
            if(proj_class.zoom_factor<1){
                proj_class.zoom_factor+=.1;
                aScreen.vert.setUnitIncrement((int)((1/(double)proj_class.zoom_factor)*(.05 * aScreen.vert.getHeight())));
                aScreen.horiz.setUnitIncrement((int)((1/(double)proj_class.zoom_factor)*(.05 * aScreen.horiz.getWidth())));
                aScreen.vert.setBlockIncrement((int)((1/(double)proj_class.zoom_factor)*(.25 * aScreen.vert.getHeight())));
                aScreen.horiz.setBlockIncrement((int)((1/(double)proj_class.zoom_factor)*(.25 * aScreen.horiz.getWidth())));
                aScreen.repaint();
            }else if(proj_class.zoom_factor<100){
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
            }else if((proj_class.zoom_factor<=1)&&(proj_class.zoom_factor>.2)){
                proj_class.zoom_factor-=.1;
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
        } 
        else if(e.getSource()==confirm)
        {
            if(proj_class.draw_mouse_state==8)
            {
                //commit the adding of the stage
                proj_class.addStage(aScreen.temp_stage);
                aScreen.temp_stage=null;
                proj_class.draw_mouse_state=0;
            }
            else if(proj_class.draw_mouse_state==9)
            {
                proj_class.addSet(aScreen.temp_set);
                aScreen.temp_set=null;
                proj_class.draw_mouse_state=0;
            }
            else if(proj_class.draw_mouse_state==3){
                //editing a bar
                proj_class.SetBar(aScreen.temp_bar,proj_class.selected_index);
                aScreen.temp_bar=null;
                proj_class.draw_mouse_state=0;
                
            }
            else if(proj_class.draw_mouse_state==4)
            {
                //editing a house
                proj_class.SetHouse(aScreen.temp_house,proj_class.selected_index);
                aScreen.temp_house=null;
                proj_class.draw_mouse_state=0;
            }
            else if(proj_class.draw_mouse_state==5)
            {
                //editing a stage
                proj_class.SetStage(aScreen.temp_stage,proj_class.selected_index);
                aScreen.temp_stage=null;
                proj_class.draw_mouse_state=0;
            }
            else if(proj_class.draw_mouse_state==10)
            {
                //editing a set object
                proj_class.SetSetObject(aScreen.temp_set,proj_class.selected_index);
                aScreen.temp_set=null;
                proj_class.draw_mouse_state=0;
            }    
        }
        aScreen.repaint();
    }
}


