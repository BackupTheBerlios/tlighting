/*
 * hold_project.java
 *
 * Created on February 5, 2005, 11:13 AM
 */

package Data_Storage;

/**
 *
 * @author Harikrishna Patel
 * last edited by joshua zawislak 4-25-05
 */

import drawing_prog.*;
import theatre.*;
import Data_Storage.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

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
    public double zoom_factor;
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
     * 15 = remove stage, 16 = remove set, 17 = remove bar, 18 = remove instrument, 19 = remove house //chaplin edit.
     */
    
    
    
    //container objects with drawing functionality for objects
    public Object_Drawer stages;
    public Object_Drawer sets;
    public Object_Drawer bars;
    public Object_Drawer instruments;
    public Object_Drawer houses;
    public inventory inventories;
    public Vector types;
    
    public PhotonMap photonmap;
    
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
    public boolean bPhotonsVisible=false;
    public boolean bAimvisible=false;
    
    public boolean hasPhotons=false;
    public boolean photonsRendered=false;
    public int selectedArraySize = -1;  //the size of the array in the selected area.
    public int selectedListIndex = -1;
    
    public int templightid;
    
    public double[] lightpowers;
    
    //preference values
    
    public int prefNumPhotons=500000;
    public boolean prefSnapToGrid=true;
    public int prefSnapToGridValue=5;
    
    public int prefSetColor=0;
    public int prefBarColor=0;
    public int prefStageColor=0;
    public int prefHouseColor=0;
    
    public int prefLineWidth=1;
    
    public int prefColorSelected=2;
    public int prefColorSelectedNode=3;
    public int prefColorBackground=4;
    
    public boolean prefColorInstruments=false;
    
    /** Creates a new instance of hold_project */
    public project() {
        stages=new Object_Drawer();
        sets=new Object_Drawer();
        bars=new Object_Drawer();
        instruments=new Object_Drawer();
        houses = new Object_Drawer();
        inventories=new inventory();
        types=new Vector();
        
        photonmap=new PhotonMap(500000,50,1e10);
        
        selected_type=-1;
        selected_index=-1;
        zoom_factor=1;
        scroll_x=0;
        scroll_y=0;
        draw_mouse_state=0;
        open_project_name="";
        project_open=false;
        houseadded=false;
        stageadded=false;
        hasPhotons=false;
        photonsRendered=false;
        bPhotonsVisible=false;
        oClass=this;
    }
    
    
    public void resetproject(){
        stages=new Object_Drawer();
        sets=new Object_Drawer();
        bars=new Object_Drawer();
        instruments=new Object_Drawer();
        houses = new Object_Drawer();
        //inventories= new inventory();
        //types= new Vector();
        selected_type=-1;
        selected_index=-1;
        zoom_factor=1;
        scroll_x=0;
        scroll_y=0;
        draw_mouse_state=0;
        open_project_name="";
        project_open=false;
        houseadded=false;
        hasPhotons=false;
        photonsRendered=false;
    }
    
    //function that is called to commit a bar being added to the list of bars
    public boolean addBar(bar abar){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                if(ObjectInside(curhouse,abar)){
                    int tempnum=bars.get_num_objects();
                    boolean nameused=true;
                    String tempname="Bar"+tempnum;
                    
                    while(nameused){
                        nameused=false;
                        int iter;
                        for(iter=0;iter<bars.get_num_objects();iter++){
                            if(tempname.equalsIgnoreCase(((bar)bars.get_object(iter)).getName())){
                                nameused=true;
                            }
                        }
                        if(nameused){
                            tempnum++;
                            tempname="Bar"+tempnum;
                        }
                    }
                    
                    boolean numUsed=true;
                    int lastnum=bars.get_num_objects();
                    if(lastnum>0){
                        while(numUsed){
                            int iter;
                            for(iter=0;iter<bars.get_num_objects();iter++){
                                if(lastnum==((bar)bars.get_object(iter)).getID()){
                                    lastnum++;
                                }else{
                                    numUsed=false;
                                }
                            }
                        }
                    }
                    abar.setName(tempname);
                    abar.setID(lastnum);
                    //System.out.println("Bar "+abar.getID()+" got added");
                    if(bars.add_object(abar)==tempnum+1){
                        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                        ib.insertNewTreeNode();
                        return true;
                    } else{
                        return false;
                    }
                }else{
                    return false;
                    
                }
            } else {
                return false;
            }
        } else{
            return false;
        }
        
    }
    
    //function called to commit adding a house to the project
    public boolean addHouse(int len, int width, String name){
        this.resetproject();
        house newHouse= new house();
        newHouse.index=0;
        newHouse.worldx=10;
        newHouse.worldy=10;
        newHouse.add_node(0,0);
        newHouse.add_node(len,0);
        newHouse.add_node(len,width);
        newHouse.add_node(0,width);
        newHouse.setVertices();
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
                if(ObjectInside(curhouse,aInstrument)){
                    int tempnum=instruments.get_num_objects();
                    aInstrument.setName("Instrument"+tempnum);
                    if(instruments.add_object(aInstrument)==tempnum+1){
                        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                        ib.insertNewTreeNode();
                        return true;
                    } else{
                        return false;
                    }
                } else{
                    return false;
                }
            } else{
                return false;
            }
        } else{
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
                if(ObjectInside(curhouse,aStage)){
                    
                    int tempnum=stages.get_num_objects();
                    aStage.setdescription("Stage");
                    if(stages.add_object(aStage)==tempnum+1){
                        stageadded=true;
                        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                        ib.insertNewTreeNode();
                        return true;
                    } else{
                        return false;
                    }
                } else{
                    return false;
                }
            } else{
                return false;
            }
        } else{
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
                if(ObjectInside(curhouse,aSet)){
                    int tempnum=sets.get_num_objects();
                    aSet.setname("Set"+tempnum);
                    if(sets.add_object(aSet)==tempnum+1){
                        ExplorerBrowser ib = (ExplorerBrowser)ExplorerBrowser.oClass;
                        ib.insertNewTreeNode();
                        return true;
                    } else{
                        return false;
                    }
                } else{
                    return false;
                }
            } else{
                return false;
            }
        } else{
            return false;
        }
    }
    
    //function called when a bar is being moved or edited
    public boolean SetBar(bar abar,int index){
        int current=houses.get_num_objects()-1;
        house curhouse = (house)houses.get_object(current);
        if(abar.getminx()>curhouse.getminx() && abar.getminy()>curhouse.getminy()){
            if(abar.getmaxx()<curhouse.getmaxx() && abar.getmaxy()<curhouse.getmaxy()){
                if(ObjectInside(curhouse,abar)){
                    if(index<bars.get_num_objects()){
                        bar old_bar=new bar();
                        old_bar.copyBar((bar)bars.get_object(index));
                        
                        int xdiff = old_bar.worldx-abar.worldx;
                        int ydiff = old_bar.worldy-abar.worldy;
                        
                        
                        
                        for(int iter=0;iter<instruments.get_num_objects();iter++) {
                            if(((instrument)instruments.get_object(iter)).getBarID()==index) {
                                instruments.get_object(iter).worldx-=xdiff;
                                
                                instruments.get_object(iter).worldy-=ydiff;
                            }
                        }
                        
                        
                        ((bar)bars.get_object(index)).copyBar(abar);
                        
                        
                        return true;
                    }
                    return false;
                    
                } else{
                    return false;
                }
            } else {
                return false;
            }
        } else{
            return false;
        }
    }
    
    //function called when a house is being edited by nodes
    public boolean SetHouse(house ahouse,int index){
        //check that all objects will be inside the house
        int i;
        if(stageadded){
            if(!ObjectInside(ahouse,stages.get_object(0))){
                return false;
            }
        }
        for(i=0;i<sets.get_num_objects();i++){
            if(!ObjectInside(ahouse,sets.get_object(i))){
                return false;
            }
        }
        for(i=0;i<bars.get_num_objects();i++){
            if(!ObjectInside(ahouse,bars.get_object(i))){
                return false;
            }
        }
        for(i=0;i<instruments.get_num_objects();i++){
            if(!ObjectInside(ahouse,instruments.get_object(i))){
                return false;
            }
        }
        
        
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
        int current=0;
        house curhouse = (house)houses.get_object(current);
        if(aset.getminx()>curhouse.getminx() && aset.getminy()>curhouse.getminy()){
            if(aset.getmaxx()<curhouse.getmaxx() && aset.getmaxy()<curhouse.getmaxy()){
                if(ObjectInside(curhouse,aset)){
                    if(index<sets.get_num_objects()){
                        ((setobject)sets.get_object(index)).copySetObject(aset);
                        return true;
                    }
                    return false;
                } else{
                    return false;
                }
            } else{
                return false;
            }
        } else{
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
                if(ObjectInside(curhouse,astage)){
                    if(index<stages.get_num_objects()){
                        ((stage)stages.get_object(index)).copyStage(astage);
                        return true;
                    }
                    return false;
                } else{
                    return false;
                }
            } else{
                return false;
            }
        } else{
            return false;
        }
    }
    
    //function to force the drawing to redraw itself with all valid information
    public void forceRepaint(){
        TransPanel tp=(TransPanel)TransPanel.oClass;
        tp.repaint();
        //ExplorerBrowser b=(ExplorerBrowser)ExplorerBrowser.oClass;
        //b.reloadChildren();
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
        return (int)((x-scroll_x)*zoom_factor);
    }
    public int WorldYtoScreen(int y){
        return (int)((y-scroll_y)*zoom_factor);
    }
    public int ScreenXtoWorld(int x){
        return (int)((x/zoom_factor)+scroll_x);
    }
    public int ScreenYtoWorld(int y){
        return (int)((y/zoom_factor)+scroll_y);
    }
    
    public void print_schematic(){
        TransPanel drawing=(TransPanel)TransPanel.oClass;
        //need to scale the house to a size that fits on 8x11
        //height needs to be less then 600 pixels
        //width needs to be less then 400 pixels
        int oldsx=scroll_x;
        int oldsy=scroll_y;
        double oldz=zoom_factor;
        
        int housex=houses.get_object(0).getmaxx()+houses.get_object(0).worldx;
        int housey=houses.get_object(0).getmaxy()+houses.get_object(0).worldy;
        
        if(housex>400){
            zoom_factor=1/(housex/400);
        }
        if(housey>600){
            zoom_factor=1/(housey/600);
        }
        forceRepaint();
        PrintUtilities.printComponent(drawing);
        
        scroll_x=oldsx;
        scroll_y=oldsy;
        zoom_factor=oldz;
        forceRepaint();
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
                } else{
                    i++;
                }
            } else{
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
                } else{
                    greater++;
                }
            }
        }
        
        results[vertics-1]=(curhouse.getslope(vertics-1)*(x-curhouse.getx(vertics-1)))+curhouse.gety(vertics-1);
        if(((results[vertics-1]<=curhouse.gety(0))&&(results[vertics-1]>=curhouse.gety(vertics-1)))||((results[vertics-1]>=curhouse.gety(0))&&(results[vertics-1]<=curhouse.gety(vertics-1)))){
            if(results[vertics-1]<y){
                lesser++;
            } else{
                greater++;
            }
        }
        
        if((lesser>0)&&(greater>0)){
            return true;
        } else{
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
                } else{
                    greater++;
                }
            }
        }
        
        results[vertics-1]=(((y-curhouse.gety(vertics-1))/curhouse.getslope(vertics-1))+curhouse.getx(vertics-1));
        if(((results[vertics-1]<=curhouse.gety(0))&&(results[vertics-1]>=curhouse.gety(vertics-1)))||((results[vertics-1]>=curhouse.gety(0))&&(results[vertics-1]<=curhouse.gety(vertics-1)))){
            if(results[vertics-1]<x){
                lesser++;
            } else{
                greater++;
            }
        }
        
        if((lesser>0)&&(greater>0)){
            return true;
        } else{
            return false;
        }
    }
    
    //function to detect if obj2 is fully contained inside obj1
    public boolean ObjectInside(General_Object obj1, General_Object obj2){
        if(obj1==null){
            return false;
        }
        if(obj2==null){
            return false;
        }
        
        
        
        //create a polygon class for obj1
        Polygon poly=new Polygon();
        for(int i=0;i<obj1.num_nodes;i++){
            poly.addPoint(obj1.x[i]+obj1.worldx, obj1.y[i]+obj1.worldy);
        }
        //for all points in obj2 check if they are contained by the polygon
        for(int i=0;i<obj2.num_nodes;i++){
            if(!poly.contains(obj2.x[i]+obj2.worldx,obj2.y[i]+obj2.worldy)){
                return false;
                
            }
        }
        
        
        return true;
    }
    
    
    //fucntions to handle inventory
    
    public void AddInventory(String aType,int aId,String aDesc){
        inventories.addItem(aType,aId,aDesc);
    }
    public void EditInventory(int index, String aType,int aId,String aDesc){
        if(index<inventories.getNumItems()){
            inventories.getList(index).setType(aType);
            inventories.getList(index).setInvId(aId);
            inventories.getList(index).setDesc(aDesc);
        }
    }
    public void RemoveInventory(int index){
        inventories.removeItem(index);
    }
    
    //functions to edit types
    public void AddType(knowntype obj){
        types.add(obj);
    }
    public void EditType(int index, knowntype obj){
        if(types.size()>index){
            ((knowntype)types.get(index)).copy(obj);
        }
    }
    public void RemoveType(int index){
        if(index<types.size()){
            types.remove(index);
        }
    }
    
    public int getInstrumentByID(int id){
        boolean found=false;
        int i=0;
        while((!found)&&(i<inventories.getNumItems())){
            if(inventories.getItemID(i)==id){
                found=true;
                return i;
            }
            
            i++;
        }
        return -1;
    }
    
    public int getTypeByName(String name){
        int i=0;
        for(i=0;i<types.size();i++){
            if(((knowntype)types.get(i)).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    
    public int getInstrumentIndex(int invID){
        int i;
        for(i=0;i<instruments.get_num_objects();i++){
            if(((instrument)instruments.get_object(i)).getInventoryID()==invID){
                return i;
            }
        }
        return -1;
    }
    
    public String getBarNameByID(int ID){
        return ((bar)this.bars.get_object(getBarByID(ID))).getName();
    }
    
    public int getBarByName(String bname){
        int i;
        for(i=0;i<bars.get_num_objects();i++){
            if(((bar)bars.get_object(i)).getName().equals(bname)){
                return i;
            }
        }
        return -1;
    }
    
    public int getBarByID(int id){
        int i;
        for(i=0;i<bars.get_num_objects();i++){
            if(((bar)bars.get_object(i)).getID()==id){
                return i;
            }
        }
        return -1;
        
    }
    
    public void verifyData(){
        //check if all instruments are in the house
        boolean somethingRemoved=false;
        
        int i;
        if(houses.get_num_objects()>0){
            //there is a house
            
            //check the stage
            if(stages.get_num_objects()>0){
                //there is a stage
                if(!ObjectInside(houses.get_object(0),stages.get_object(0))){
                    stages.set_num_objects(0);
                    
                }
            }
            //check the sets
            for(i=0;i<sets.get_num_objects();i++){
                if(!ObjectInside(houses.get_object(0),sets.get_object(i))){
                    sets.remove_object(i);
                    somethingRemoved=true;
                }
            }
            //check the bars
            for(i=0;i<bars.get_num_objects();i++){
                if(!ObjectInside(houses.get_object(0),bars.get_object(i))){
                    bars.remove_object(i);
                    somethingRemoved=true;
                }
            }
            //checks the instruments
            for(i=0;i<instruments.get_num_objects();i++){
                if(!ObjectInside(houses.get_object(0),instruments.get_object(i))){
                    instruments.remove_object(i);
                    somethingRemoved=true;
                }
            }
        }
        
        //make sure all types have at least two node to represent it
        for(i=0;i< types.size();i++){
            if(((knowntype)types.get(i)).getNumNodes()<=1){
                types.remove(i);
                somethingRemoved=true;
            }
        }
        
        
        //make sure there is a type for all instruments in inventory
        for(i=0;i<instruments.get_num_objects();i++){
            int type=getTypeByName(((instrument)instruments.get_object(i)).getType());
            if(type==-1){
                instruments.remove_object(i);
                somethingRemoved=true;
            }else{
                //make sure all instruments have the correct nodes for their type
                ((instrument)instruments.get_object(i)).num_nodes=0;
                int j;
                for(j=0;j<((knowntype)types.get(type)).getNumNodes();j++){
                    ((instrument)instruments.get_object(i)).add_node(((knowntype)types.get(type)).getX(j),((knowntype)types.get(type)).getY(j));
                    
                }
            }
        }
        
        //make sure all the instruemnts have a bar that exists
        for(i=0;i<instruments.get_num_objects();i++){
            if(((instrument)instruments.get_object(i)).getBarID()<bars.get_num_objects()){
                //make sure all the instruments are on a valid point in a bar
                bar tbar=(bar)bars.get_object(getBarByID(((instrument)instruments.get_object(i)).getBarID()));
                boolean vert=false;
                boolean hori=false;
                if(tbar!=null){
                    //then it is on bar
                    if((tbar.getmaxx()==tbar.getminx())){
                        vert=true;
                    }
                    if(tbar.getmaxy()==tbar.getminy()){
                        hori=true;
                    }
                    if((!vert)&&(!hori)){
                        if(!instruments.get_object(i).in_area(tbar.getminx(),tbar.getminy(),tbar.getmaxx(),tbar.getmaxy())){
                            instruments.remove_object(i);
                            somethingRemoved=true;
                        }
                    }
                    if(vert){
                        //just make sure the y's are in range
                        if(!instruments.get_object(i).in_area(tbar.getminx()-1,tbar.getminy(),tbar.getmaxx()+1,tbar.getmaxy())){
                            instruments.remove_object(i);
                            somethingRemoved=true;
                        }
                    }
                    if(hori){
                        //just make sure the x's are in range
                        if(!instruments.get_object(i).in_area(tbar.getminx(),tbar.getminy()-1,tbar.getmaxx(),tbar.getmaxy()+1)){
                            instruments.remove_object(i);
                            somethingRemoved=true;
                        }
                    }
                }else{
                    //remove the instrument
                    instruments.remove_object(i);
                    
                    somethingRemoved=true;
                }
            }
        }
        
        //make sure all the instruments are marked as used
        
        for(i=0;i<inventories.getNumItems();i++){
            int inst_index=getInstrumentIndex(inventories.getItemID(i));
            if(inst_index!=-1){
                //make sure it is set to used
                inventories.setItemUsed(i,true);
                
            }else{
                //make sure it is not set to used
                
                inventories.setItemUsed(i,false);
            }
        }
        for(i=0;i<instruments.get_num_objects();i++){
            int inv_index=getInstrumentByID(((instrument)instruments.get_object(i)).getInventoryID());
            if(inv_index!=-1){
                inventories.setItemUsed(inv_index, true);
            }else{
                //remove it because instrument is not in inventory
                instruments.remove_object(i);
                somethingRemoved=true;
            }
        }
        
        
        
        //make sure all the indexes are correct for the objects in lists
        
        //house
        for(i=0;i<houses.get_num_objects();i++){
            houses.get_object(i).index=i;
        }
        //stage
        for(i=0;i<stages.get_num_objects();i++){
            stages.get_object(i).index=i;
        }
        //set
        for(i=0;i<sets.get_num_objects();i++){
            sets.get_object(i).index=i;
        }
        //bar
        for(i=0;i<bars.get_num_objects();i++){
            bars.get_object(i).index=i;
        }
        //instrument
        for(i=0;i<instruments.get_num_objects();i++){
            instruments.get_object(i).index=i;
        }
        
        if(stages.get_num_objects()<=0){
            stageadded=false;
        }else{
            stageadded=true;
        }
        
        
        if(somethingRemoved==true){
            JInternalFrame error_Window;
            error_Window = new JInternalFrame();
            error_Window.setLocation(0,0);
            error_Window.setBounds(0,0, 400,400);
            error_Window.setVisible(true);
            JOptionPane.showMessageDialog(error_Window,"There was a Data Error and some objects have been removed");
        }
        //refresh all the displays
        forceRepaint();
        ExplorerBrowser b=(ExplorerBrowser)ExplorerBrowser.oClass;
        //ItemBrowser ib = (ItemBroswer)ItemBrowser.oClass;
        b.reloadChildren();
        
        //ib.displayInfo();
        
    }
    
    public boolean addPhoton(double x,double y, double z,double nx, double ny, double nz, double dx, double dy, double dz, float R, float G, float B, int light){
        if(photonmap!=null){
            if(photonmap.isFull()){
                return false;
            }
            
            Point3 p=new Point3(x,y,z);
            Vector3 dir=new Vector3(dx,dy,dz);
            Color power = new Color((float)R,(float)G,(float)B);
            
            
            Photon pho= new Photon(p, dir, power);
            pho.setN(nx,ny, nz);
            photonmap.storePhoton(pho);
            
            return true;
        }
        
        
        return false;
    }
    
    public void setLightPower(int index,double power){
        
    }
    
    
    public double getLightPower(int index){
        
        
        return 0;
    }
    
}
