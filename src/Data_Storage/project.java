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
    
    //name of project
    public String open_project_name;
    //indicates if the project is valid
    public boolean project_open;
    
    //indicates if there is a valid house created
    public boolean houseadded;
    //indicates if a stage has been added so you can't add a second one
    public boolean stageadded;
    
    //variables to help in drawing the objects inthe draw screen
    public int selected_type;
    //types are defined as follows 0=house 1=stage 2=bar 3=instrument 4=set 5=inventory
    public int selected_index;
    //keep track of the selected node of the selected item
    public int selected_node;
    
    //shows how much the window is zoomed in
    public int zoom_factor;
    //shows how much the screen is scrolled
    public int scroll_x;
    //shows how much the screen is scrolled
    public int scroll_y;
    //indicates the state the mouse is in
    public int draw_mouse_state;
   //draw mouse stages are defiend as 0=normal(object selection) 1=adding a bar 2=adding an instruemnt 3= editing a bar 
    //4=editing a house 5=editing a stage 6=moving a bar 7= moving an instrument 8=adding a stage 
    //9=adding a set peice 10=editing a set piece 11=moving a stage 12=moving a house 13=moving a set 14=aim a instrument
    /*normal(selection)=0
     *adding a stage=8
     *editing a stage=5
     *moving a stage=11
     *adding a bar=1
     *editing a bar=3
     *moving a bar=6
     *adding an instrument=2
     *moving an instrument=7
     *aim light = 14
     adding set=9
     *editing set=10
     *moving set=13
     *editing a house=4
     *moving a house=12
     *
     */
    
    
    
    //container objects with drawing functionality for objects
    public Object_Drawer stages;
    public Object_Drawer sets;
    public Object_Drawer bars;
    public Object_Drawer instruments;
    public Object_Drawer houses;
    
    //istance of the explorer pane
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
    
    //function that is called to commit a bar being added to the list of bars
    public boolean addBar(bar abar){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                if((checkonborder(abar.getX(0), abar.getY(0)))&&(checkverti(abar.getX(0), abar.getY(0)))&&(checkhorizon(abar.getX(0), abar.getY(0)))){
                    if((checkonborder(abar.getX(1), abar.getY(1)))&&(checkverti(abar.getX(1), abar.getY(1)))&&(checkhorizon(abar.getX(1), abar.getY(1)))){
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
                    else{
                        return false;
                    }
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
   
    //function called to commit adding a house to the project
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
        newHouse.setslopes();
        houses.add_object(newHouse);
        forceRepaint();
        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
        ib.insertNewTreeNode();
        return houseadded=true;
        
    }
    
    //function called to commit adding an instrument to a bar
    public boolean addInstrument(instrument aInstrument){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aInstrument.getX()>curhouse.getminx() && aInstrument.getY()>curhouse.getminy()){
            if(aInstrument.getX()<curhouse.getmaxx() && aInstrument.getY()<curhouse.getmaxy()){
                if((checkonborder(aInstrument.getX(), aInstrument.getY()))&&(checkverti(aInstrument.getX(), aInstrument.getY()))&&(checkhorizon(aInstrument.getX(), aInstrument.getY()))){
                    int tempnum=instruments.get_num_objects();
                    aInstrument.setName("Instrument"+tempnum);
                    if(instruments.add_object(aInstrument)==tempnum+1){
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
        else{
            return false;
        }
    }
    
    //function called to add a tage to the project
    public boolean addStage(stage aStage){
        boolean allset=true;
        int vertics=aStage.getvertices();
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aStage.getminx()>curhouse.getminx() && aStage.getminy()>curhouse.getminy()){
            if(aStage.getmaxx()<curhouse.getmaxx() && aStage.getmaxy()<curhouse.getmaxy()){
                for(int i=0; i<vertics;i++){
                    if((!checkonborder(aStage.getx(i), aStage.gety(i)))||(!checkverti(aStage.getx(i), aStage.gety(i)))||(!checkhorizon(aStage.getx(i), aStage.gety(i)))){
                        allset=false;
                        i=vertics;
                    }
                }
                if(allset){
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
        else{
            return false;
        }
    }
    
    //function called to commit adding a set object to the project
    public boolean addSet(setobject aSet){
        boolean allset=true;
        int vertics=aSet.getvertices();
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aSet.getminx()>curhouse.getminx() && aSet.getminy()>curhouse.getminy()){
            if(aSet.getmaxx()<curhouse.getmaxx() && aSet.getmaxy()<curhouse.getmaxy()){
                for(int i=0; i<vertics;i++){
                    if((!checkonborder(aSet.getx(i), aSet.gety(i)))||(!checkverti(aSet.getx(i), aSet.gety(i)))||(!checkhorizon(aSet.getx(i), aSet.gety(i)))){
                        allset=false;
                        i=vertics;
                    }
                }
                if(allset){
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
        else{
            return false;
        }
    }
    
    //function called when a bar is being moved or edited
    public boolean SetBar(bar abar,int index){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                if((checkonborder(abar.getX(0), abar.getY(0)))&&(checkverti(abar.getX(0), abar.getY(0)))&&(checkhorizon(abar.getX(0), abar.getY(0)))){
                    if((checkonborder(abar.getX(1), abar.getY(1)))&&(checkverti(abar.getX(1), abar.getY(1)))&&(checkhorizon(abar.getX(1), abar.getY(1)))){
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
            else
            {
              return false;  
            }
        }
        else{
            return false;
        }
    }
    
    //function called when a house is being edited by nodes
    public boolean SetHouse(house ahouse,int index){
        if(index<houses.get_num_objects()){
            ((house)houses.get_object(index)).copyHouse(ahouse);            
            return true;
        }
        return false;
    }
    
    //function called when a set object is edited by nodes
    public boolean SetSetObject(setobject aset,int index){

        boolean allset=true;
        int vertics=aset.getvertices();
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(aset.getminx()>curhouse.getminx() && aset.getminy()>curhouse.getminy()){
            if(aset.getmaxx()<curhouse.getmaxx() && aset.getmaxy()<curhouse.getmaxy()){
                for(int i=0; i<vertics;i++){
                    if((!checkonborder(aset.getx(i), aset.gety(i)))||(!checkverti(aset.getx(i), aset.gety(i)))||(!checkhorizon(aset.getx(i), aset.gety(i)))){
                        allset=false;
                        i=vertics;
                    }
                }
                if(allset){
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
        else{
            return false;
        }
    }

    //function called when a stage is edited by nodes
    public boolean SetStage(stage astage,int index){
        boolean allset=true;
        int vertics=astage.getvertices();
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(astage.getminx()>curhouse.getminx() && astage.getminy()>curhouse.getminy()){
            if(astage.getmaxx()<curhouse.getmaxx() && astage.getmaxy()<curhouse.getmaxy()){
                for(int i=0; i<vertics;i++){
                    if((!checkonborder(astage.getx(i), astage.gety(i)))||(!checkverti(astage.getx(i), astage.gety(i)))||(!checkhorizon(astage.getx(i), astage.gety(i)))){
                        allset=false;
                        i=vertics;
                    }
                }
                if(allset){
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
        else{
            return false;
        }
    }
    
    //function to force the drawing to redraw itself with all valid information
    public void forceRepaint(){
        TransPanel tp=(TransPanel)TransPanel.oClass;
        tp.repaint();
        
    }
    
    //checking and setting visibility of objects
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
        return ((x-scroll_x)*zoom_factor);
    }
    public int WorldYtoScreen(int y){
        return ((y-scroll_y)*zoom_factor);
    }
    public int ScreenXtoWorld(int x){
        return ((x/zoom_factor)+scroll_x);
    } 
    public int ScreenYtoWorld(int y){
        return ((y/zoom_factor)+scroll_y);
    }

    public void print_schematic(){
        TransPanel drawing=(TransPanel)TransPanel.oClass;
        PrintUtilities.printComponent(drawing); 
    }

    public boolean checkonborder(int x, int y){
        boolean onborder=false;
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        int vertics=curhouse.getvertices();
        
        int i=0;
        while((onborder!=true)&&(i<vertics)){
            if(curhouse.y[i]==(curhouse.getslope(i)*(curhouse.x[i]-x))+y){
                if(((x>=curhouse.x[i])&&(x<=curhouse.x[i+1]))||((x<=curhouse.x[i])&&(x>=curhouse.x[i+1]))){
                        onborder=true;
                }
                else{
                    i++;
                }
            }
            else{
                i++;
            }
        }
        return onborder;
    }

    public boolean checkverti(int x, int y){
        double results[]=new double[30];
        int lesser=0;
        int greater=0;
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        int vertics=curhouse.getvertices();
        
        for(int i=0; i<vertics-1; i++){
            results[i]=(curhouse.getslope(i)*(x-curhouse.getx(i)))+curhouse.gety(i);
            if(((results[i]<=curhouse.gety(i+1))&&(results[i]>=curhouse.gety(i)))||((results[i]>=curhouse.gety(i+1))&&(results[i]<=curhouse.gety(i)))){
                if(results[i]<y){
                    lesser++;
                }
                else{
                    greater++;
                }
            }
        }
        
        results[vertics-1]=(curhouse.getslope(vertics-1)*(x-curhouse.getx(vertics-1)))+curhouse.gety(vertics-1);
        if(((results[vertics-1]<=curhouse.gety(0))&&(results[vertics-1]>=curhouse.gety(vertics-1)))||((results[vertics-1]>=curhouse.gety(0))&&(results[vertics-1]<=curhouse.gety(vertics-1)))){
            if(results[vertics-1]<y){
                lesser++;
            }
            else{
                greater++;
            }
        }
        
        if((lesser>0)&&(greater>0)){
            return true;
        }
        else{
            return false;
        }
    }
    
       public boolean checkhorizon(int x, int y){
        double results[]=new double[30];
        int lesser=0;
        int greater=0;
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        int vertics=curhouse.getvertices();
        
        for(int i=0; i<vertics-1; i++){
            results[i]=(((y-curhouse.gety(i))/curhouse.getslope(i))+curhouse.getx(i));
            if(((results[i]<=curhouse.getx(i+1))&&(results[i]>=curhouse.getx(i)))||((results[i]>=curhouse.getx(i+1))&&(results[i]<=curhouse.getx(i)))){
                if(results[i]<x){
                    lesser++;
                }
                else{
                    greater++;
                }
            }
        }
        
        results[vertics-1]=(((y-curhouse.gety(vertics-1))/curhouse.getslope(vertics-1))+curhouse.getx(vertics-1));
        if(((results[vertics-1]<=curhouse.gety(0))&&(results[vertics-1]>=curhouse.gety(vertics-1)))||((results[vertics-1]>=curhouse.gety(0))&&(results[vertics-1]<=curhouse.gety(vertics-1)))){
            if(results[vertics-1]<x){
                lesser++;
            }
            else{
                greater++;
            }
        }
        
        if((lesser>0)&&(greater>0)){
            return true;
        }
        else{
            return false;
        }
    }

}
