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
import Data_Storage.*;


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
    public int scroll_x;
    public int scroll_y;
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
        zoom_factor=0;
        scroll_x=0;
        scroll_y=0;
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
        zoom_factor=1;
        scroll_x=0;
        scroll_y=0;
        draw_mouse_state=0;
        open_project_name="";
        project_open=false;
        houseadded=false;
    }
    
    public boolean addBar(bar abar){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                int tempnum=bars.get_num_objects();
                abar.setID("Bar"+tempnum);
                //System.out.println("Bar "+abar.getID()+" got added");
                if(bars.add_object(abar)==tempnum+1){
                    ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                    ib.insertNewTreeNode();
                    return true;
                }
                else{
                    return false;
                }
            }
            else
            {
              return false;  
            }
        }
        else{
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
        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
        ib.insertNewTreeNode();
        return houseadded=true;
        
    }
    
    public boolean addInstrument(instrument aInstrument){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aInstrument.getX()>curhouse.getminx() && aInstrument.getY()>curhouse.getminy()){
            if(aInstrument.getX()<curhouse.getmaxx() && aInstrument.getY()<curhouse.getmaxy()){
                int tempnum=instruments.get_num_objects();
                aInstrument.setName("Instrument"+tempnum);
                if(instruments.add_object(aInstrument)==tempnum+1){
                    ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                    ib.insertNewTreeNode();
                    return true;
                }else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean addStage(stage aStage){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aStage.getminx()>curhouse.getminx() && aStage.getminy()>curhouse.getminy()){
            if(aStage.getmaxx()<curhouse.getmaxx() && aStage.getmaxy()<curhouse.getmaxy()){
                int tempnum=stages.get_num_objects();
                aStage.setdescription("Stage");
                if(stages.add_object(aStage)==tempnum+1){
                    stageadded=true;
                    ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                    ib.insertNewTreeNode();
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean addSet(setobject aSet){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aSet.getminx()>curhouse.getminx() && aSet.getminy()>curhouse.getminy()){
            if(aSet.getmaxx()<curhouse.getmaxx() && aSet.getmaxy()<curhouse.getmaxy()){
                int tempnum=sets.get_num_objects();
                aSet.setname("Set"+tempnum);
                if(sets.add_object(aSet)==tempnum+1){
                    ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                    ib.insertNewTreeNode();
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean SetBar(bar abar,int index){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                if(index<bars.get_num_objects()){
                    ((bar)bars.get_object(index)).copyBar(abar);
                    return true;
                }
                return false;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean SetHouse(house ahouse,int index){
        if(index<houses.get_num_objects()){
            ((house)houses.get_object(index)).copyHouse(ahouse);            
            return true;
        }
        return false;
    }
    public boolean SetSetObject(setobject aset,int index){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aset.getminx()>curhouse.getminx() && aset.getminy()>curhouse.getminy()){
            if(aset.getmaxx()<curhouse.getmaxx() && aset.getmaxy()<curhouse.getmaxy()){
                if(index<sets.get_num_objects()){
                    ((setobject)sets.get_object(index)).copySetObject(aset);
                    return true;
                }
                return false;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean SetStage(stage astage,int index){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(astage.getminx()>curhouse.getminx() && astage.getminy()>curhouse.getminy()){
            if(astage.getmaxx()<curhouse.getmaxx() && astage.getmaxy()<curhouse.getmaxy()){
                if(index<stages.get_num_objects()){
                    ((stage)stages.get_object(index)).copyStage(astage);
                    return true;
                }
                return false;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
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

    //conversion fucntions to translate world and screen coordinates to each other
    public int WorldXtoScreen(int x){    
        return ((x/zoom_factor)+scroll_x);
    }
    public int WorldYtoScreen(int y){
        return ((y/zoom_factor)+scroll_y);
    }
    public int ScreenXtoWorld(int x){
        return ((x*zoom_factor)-scroll_x);
    } 
    public int ScreenYtoWorld(int y){
        return ((y*zoom_factor)-scroll_y);
    }
    
    
}
