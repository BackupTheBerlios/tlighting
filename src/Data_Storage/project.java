/*
 * hold_project.java
 *
 * Created on February 5, 2005, 11:13 AM
 */

package Data_Storage;

/**
 *
 * @author Harikrishna Patel
 */

import drawing_prog.*;
import theatre.*;


public class project{
    
    
    public String open_project_name;
    public boolean project_open;
    
    public boolean houseadded;
    public boolean stageadded;
    
    //variables to help in drawing the objects inthe draw screen
    public int selected_type;
    //types are defined as follows 0=house 1=stage 2=bar 3=instrument 4=set
    public int selected_index;
    //keep track of the selected node of the selected item
    public int selected_node;
    
    public int zoom_factor;
    public int draw_mouse_state;
   //draw mouse stages are defiend as 0=normal 1=adding a bar 2=adding an instruemnt 3= editing a bar 
    //4=editing a house 5=editing a stage 6=moving a bar 7= moving an instrument 8=adding a stage 
    //9=adding a set peice 10=editing a set piece
    
    public Object_Drawer stages;
    public Object_Drawer sets;
    public Object_Drawer bars;
    public Object_Drawer instruments;
    public Object_Drawer houses;
    
    
    public Object oExplorer;
   // public Object_Drawer inventory; //these are not drawn objects so should not be Object Drawer types
    //public Object_Drawer wiring_sheet;
    public static Object oClass = null;
    
    // visibilty objects
    boolean bStageVisible = true;
    boolean bSetItemVisible = true;
    boolean bBarVisible = true;
    boolean bInstrumentVisible = true;

    
    /** Creates a new instance of hold_project */
    public project() {
        stages=new Object_Drawer();
        sets=new Object_Drawer();
        bars=new Object_Drawer();
        instruments=new Object_Drawer();
        houses = new Object_Drawer();
    
        selected_type=-1;
        selected_index=-1;
        zoom_factor=-1;
        draw_mouse_state=0;
        open_project_name="";
        project_open=false;
        houseadded=false;
        stageadded=false;
        
        oClass=this;
    }
 
    
    private void resetproject(){
        stages=new Object_Drawer();
        sets=new Object_Drawer();
        bars=new Object_Drawer();
        instruments=new Object_Drawer();
        houses = new Object_Drawer();
    
        selected_type=-1;
        selected_index=-1;
        zoom_factor=-1;
        draw_mouse_state=0;
        open_project_name="";
        project_open=false;
        houseadded=false;
    }
    
    
    public boolean addBar(bar abar){
        int tempnum=bars.get_num_objects();
        abar.setID("Bar"+tempnum);
        System.out.println("Bar "+abar.getID()+" got added");
        if(bars.add_object(abar)==tempnum+1){
            ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
            ib.insertNewTreeNode();
            return true;
        }else{
            return false;
        }
    }
   
    public boolean addHouse(int len, int width, String name){
        //this.resetproject();
        house newHouse= new house();
        newHouse.index=0;
        newHouse.worldx=10;
        newHouse.worldy=10;
        newHouse.add_node(0,0);
        newHouse.add_node(len,0);
        newHouse.add_node(len,width);
        newHouse.add_node(0,width);
        newHouse.setid(name);
        houses.add_object(newHouse);
        forceRepaint();
        return houseadded=true;
        
    }
    
    public boolean addInstrument(instrument aInstrument){
        int tempnum=instruments.get_num_objects();
        if(instruments.add_object(aInstrument)==tempnum+1){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean addStage(stage aStage){
        int tempnum=stages.get_num_objects();
        if(stages.add_object(aStage)==tempnum+1){
            stageadded=true;
            return true;
        }else{
            return false;
        }
        
    }
    
    public boolean addSet(setobject aSet){
        int tempnum=sets.get_num_objects();
        if(sets.add_object(aSet)==tempnum+1){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean SetBar(bar abar,int index){
        if(index<bars.get_num_objects()){
            ((bar)bars.get_object(index)).copyBar(abar);
            return true;
        }
        return false;
    }
    
    public boolean SetHouse(house ahouse,int index){
        if(index<houses.get_num_objects()){
            ((house)houses.get_object(index)).copyHouse(ahouse);            
            return true;
        }
        return false;
    }
    public boolean SetSetObject(setobject aset,int index){
        if(index<sets.get_num_objects()){
            ((setobject)sets.get_object(index)).copySetObject(aset);
            return true;
        }
        return false;
    }
    public boolean SetStage(stage astage,int index){
        if(index<stages.get_num_objects()){
            ((stage)stages.get_object(index)).copyStage(astage); 
            return true;
        }
        return false;
    }
    public void forceRepaint(){
        TransPanel tp=(TransPanel)TransPanel.oClass;
        tp.repaint();
        
    }
    public boolean isStageVisible() { return bStageVisible;}
    public void setStageVisible(boolean b) { bStageVisible = b; }
    
    public boolean isSetItemVisible() { return bSetItemVisible;}
    public void setSetItemVisible(boolean b) { bSetItemVisible = b; }

    public boolean isBarVisible() { return bBarVisible;}
    public void setBarVisible(boolean b) { bBarVisible = b; }

    public boolean isInstrumentVisible() { return bInstrumentVisible;}
    public void setInstrumentVisible(boolean b) { bInstrumentVisible = b; }

}
