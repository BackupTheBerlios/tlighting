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
    //variables to help in drawing the objects inthe draw screen
    public int selected_type;
    //types are defined as follows 0=house 1=stage 2=bar 3=instrument
    public int selected_index;
    public int zoom_factor;
    public int draw_mouse_state;
   //draw mouse stages are defiend as 0=normal 1=drawing bar 2=adding an instruemnt 3= editing a bar 
    //4=editing a house 5=editing a stage 6=moving a bar 7= moving an instrument 
    
    public Object_Drawer stage;
    public Object_Drawer set;
    public Object_Drawer bars;
    public Object_Drawer instruments;
    public Object_Drawer houses;
    
    
    public Object oExplorer;
   // public Object_Drawer inventory; //these are not drawn objects so should not be Object Drawer types
    //public Object_Drawer wiring_sheet;
    
    
    public static Object oClass = null;
    
    
    /** Creates a new instance of hold_project */
    public project() {
        stage=new Object_Drawer();
        set=new Object_Drawer();
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
        
        oClass=this;
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
}
