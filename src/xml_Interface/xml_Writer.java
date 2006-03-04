/*
 * xml_Writer.java
 *
 * Created on March 2, 2005, 10:05 PM
 * This will contain the xml output routines, from objects to file
 */

package xml_Interface;

import java.io.*;
import net.n3.nanoxml.*;
import Data_Storage.*;
/**
 *
 * @author Greg Silverstein
 * edited by JoshZ 3/19/05 and again 3/20/05 and again 4/20/05
 */
//*****************************************************************************
public class xml_Writer {
    
    public project proj_class;              //Storage point for project
    //Final Holders For Locations
    public String s_bPath, s_pPath;
    
    
    
    /** Creates a new instance of xml_Writer */
    public xml_Writer() {
        proj_class=(project)project.oClass;
    }
    public void save_project(String inProjectPath,String inBarPath) {
        boolean isHouse=false;
        boolean isStage=false;
        boolean isSets=false;
        boolean isBars=false;
        boolean isInstruments=false;
        boolean isInventory=false;
        boolean isTypes=false;
        
        int lastindex=inBarPath.lastIndexOf("/");
        String projName=inBarPath.substring(lastindex+1);
        proj_class.open_project_name=projName;
        
        //make sure the project class is not null is unlikely but can happen
        if(proj_class!=null){
            //check if there is a house object
            if(proj_class.houses.get_num_objects()>0){
                set_house(inProjectPath);
                isHouse=true;
                //there is a house object so check if there are any bars
                if(proj_class.bars.get_num_objects()>0){
                    set_bars(inProjectPath);
                    isBars=true;
                }
                
                //check if there are any set pieces
                if(proj_class.sets.get_num_objects()>0){
                    set_set(inProjectPath);
                    isSets=true;
                }
                //check if there is a stage
                if(proj_class.stages.get_num_objects()>0){
                    set_stage(inProjectPath);
                    isStage=true;
                }
                //check if there are any instruments
                if(proj_class.instruments.get_num_objects()>0){
                    set_instrument(inProjectPath);
                    isInstruments=true;
                }
                if(proj_class.inventories.getNumItems()>0){
                    set_inventory(inProjectPath);
                    isInventory=true;
                }
                if(proj_class.types.size()>0){
                    set_types(inProjectPath);
                    isTypes=true;
                }
                
                //set_inventory(inProjectPath);
                //set_knowntypes(inProjectPath);
            }
            //save teh project
            set_project(inBarPath,isHouse, isStage,isSets, isBars, isInstruments, isInventory, isTypes);
        }
        
    }
    
    //individual save functions
    
    //*****************************************************************************
    public void set_house(String projPath) {
        //variables to use to open a file and write xml to it
        File outputfile; //file to open
        XMLWriter writer=null; // xml writer
        FileOutputStream out = null; // output stream to send to xml writer
        
        //catch any exceptions with openeing the file
        try{
            //open a house file
            outputfile = new File(projPath+"/house.xml");
            //if it does not exist then create it
            outputfile.createNewFile();
            //open the output stream
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        //catch any exceptions with openeing the xml writer
        try{
            //if the output stream did not open then can't open the xml writer
            if(out!=null){
                //create a new xml writer
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        //root object to contain all other objects adn hold link to project id
        XMLElement parent_obj=new XMLElement();
        
        //xml house object
        XMLElement house_obj=new XMLElement();
        
        //create the parent element
        parent_obj.createElement("list_root");
        //set the name that will appear in the file
        parent_obj.setName("house_info");
        //give it the proj id as an attribute so we can do some checkign when reading it back in
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        
        //create the house element
        house_obj.createElement("house_obj");
        //set the name of the element to the hosue object
        house_obj.setName("house");
        
        //attributes for house name, overallx, overally, overallz, name, description, id, proj_id
        //set all the attributes that will show up in the house tag
        if(((house)proj_class.houses.get_object(0)).getname()!=null){
            house_obj.setAttribute("name",((house)proj_class.houses.get_object(0)).getname());
        }
        //if((((house)proj_class.houses.get_object(0)).worldx)!=null){
        house_obj.setAttribute("overallx",String.valueOf(((house)proj_class.houses.get_object(0)).worldx));
        //}
        //if((((house)proj_class.houses.get_object(0)).worldy)!=null){
        house_obj.setAttribute("overally",String.valueOf(((house)proj_class.houses.get_object(0)).worldy));
        //}
        //if(((house)proj_class.houses.get_object(0)).getheight()){
        house_obj.setAttribute("overallz",String.valueOf(((house)proj_class.houses.get_object(0)).getheight()));
        //}
        if(((house)proj_class.houses.get_object(0)).getdescription()!=null){
            house_obj.setAttribute("description",((house)proj_class.houses.get_object(0)).getdescription());
        }
        if(((house)proj_class.houses.get_object(0)).getid()!=null){
            house_obj.setAttribute("id",((house)proj_class.houses.get_object(0)).getid());
        }
        //if(){
        house_obj.setAttribute("proj_id",proj_class.open_project_name);
        //}
        
        //now have to create children elements that represent the house points
        
        int i;
        int num_nodes=((house)proj_class.houses.get_object(0)).num_nodes;
        int val;
        for(i=0;i<num_nodes;i++){
            XMLElement node_obj=new XMLElement();
            node_obj.setName("node");
            
            val=((house)proj_class.houses.get_object(0)).getx(i);
            node_obj.setAttribute("x",String.valueOf(val));
            
            val=((house)proj_class.houses.get_object(0)).gety(i);
            node_obj.setAttribute("y",String.valueOf(val));
            
            val=((house)proj_class.houses.get_object(0)).getheight();
            node_obj.setAttribute("z",String.valueOf(val));
            house_obj.addChild(node_obj);
        }
        
        //add the house element to the parent object
        parent_obj.addChild(house_obj);
        
        //catch any errors writing xml to the file
        try{
            //if the writer is invalid then can't write to it you moron
            if(writer!=null){
                //write the xml starting with the parent object and all children recursively from it
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting house object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_bars(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/bars.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("root_List");
        parent_obj.setName("bar_list");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        
        int i;
        //have to create a new tag element of each bar and add it to the parent object as a child
        //parent object will have multiple children. 1 for each bar
        for(i=0;i<proj_class.bars.get_num_objects();i++){
            XMLElement bar_obj=new XMLElement();
            bar_obj.createElement("bar_obj");
            bar_obj.setName("bar");
            
            //attributes for bar id, description, x1, x2, y1, y2, z1, z2, house_id
            if(((bar)proj_class.bars.get_object(i)).getName()!=null){
                bar_obj.setAttribute("Name",((bar)proj_class.bars.get_object(i)).getName());
            }
            bar_obj.setAttribute("id",Integer.toString(((bar)proj_class.bars.get_object(i)).getID()));
            
            //if(){
            bar_obj.setAttribute("description","BAR DESCRIPTION");
            //}
            //if(){
            int x1=((bar)proj_class.bars.get_object(i)).getX(0)+((bar)proj_class.bars.get_object(i)).worldx;
            bar_obj.setAttribute("x1",String.valueOf(x1));
            //}
            //if(){
            
            int x2=((bar)proj_class.bars.get_object(i)).getX(1)+((bar)proj_class.bars.get_object(i)).worldx;
            bar_obj.setAttribute("x2",String.valueOf(x2));
            //}
            //if(){
            int y1=((bar)proj_class.bars.get_object(i)).getY(0)+((bar)proj_class.bars.get_object(i)).worldy;
            bar_obj.setAttribute("y1",String.valueOf(y1));
            //}
            //if(){
            int y2=((bar)proj_class.bars.get_object(i)).getY(1)+((bar)proj_class.bars.get_object(i)).worldy;
            bar_obj.setAttribute("y2",String.valueOf(y2));
            //}
            //if(){
            int z1=((bar)proj_class.bars.get_object(i)).getZ(0);
            bar_obj.setAttribute("z1",String.valueOf(z1));
            //}
            //if(){
            int z2=((bar)proj_class.bars.get_object(i)).getZ(1);
            bar_obj.setAttribute("z2",String.valueOf(z2));
            //}
            if(((house)proj_class.houses.get_object(0)).getid()!=null){
                bar_obj.setAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
            }
            //need to do this add a list of dimmers in as child elments to the bar
            int i2;
            int num_dim=((bar)proj_class.bars.get_object(i)).getNum_dimmers();
            int val;
            for(i2=0;i2<num_dim;i2++){
                XMLElement node_obj=new XMLElement();
                node_obj.setName("dimmer");
                
                val=((bar)proj_class.bars.get_object(i)).getDimmer(i2);
                node_obj.setAttribute("number",String.valueOf(val));
                bar_obj.addChild(node_obj);
            }
            parent_obj.addChild(bar_obj);
        }
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting bar object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_set(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/sets.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        //write the xml header
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("root_List");
        parent_obj.setName("set_list");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        int i;
        for(i=0;i<proj_class.sets.get_num_objects();i++){
            XMLElement set_obj=new XMLElement();
            set_obj.createElement("set_obj");
            set_obj.setName("set");
            
            //attributes for sets name, description, id, house_id, x, y, z,
            
            set_obj.setAttribute("name",((setobject)proj_class.sets.get_object(i)).getname());
            set_obj.setAttribute("description",((setobject)proj_class.sets.get_object(i)).getdescription());
            set_obj.setAttribute("id","0");
            set_obj.setAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
            set_obj.setAttribute("x",String.valueOf(((setobject)proj_class.sets.get_object(i)).worldx));
            set_obj.setAttribute("y",String.valueOf(((setobject)proj_class.sets.get_object(i)).worldy));
            set_obj.setAttribute("z",String.valueOf(((setobject)proj_class.sets.get_object(i)).getsize()));
            
            int i2;
            int num_nodes=((setobject)proj_class.sets.get_object(i)).num_nodes;
            int val;
            for(i2=0;i2<num_nodes;i2++){
                XMLElement node_obj=new XMLElement();
                node_obj.setName("node");
                
                val=((setobject)proj_class.sets.get_object(i)).getx(i2);
                node_obj.setAttribute("x",String.valueOf(val));
                
                val=((setobject)proj_class.sets.get_object(i)).gety(i2);
                node_obj.setAttribute("y",String.valueOf(val));
                
                val=((setobject)proj_class.sets.get_object(i)).getsize();
                node_obj.setAttribute("z",String.valueOf(val));
                set_obj.addChild(node_obj);
            }
            
            parent_obj.addChild(set_obj);
        }
        
        
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting set object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_stage(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/stage.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        //write the xml header
        
        XMLElement stage_obj=new XMLElement();
        
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("list_root");
        parent_obj.setName("stage_info");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        
        stage_obj.createElement("stage_obj");
        stage_obj.setName("stage");
        
        
        //attributes for stage name, id, house_id, x, y, z
        if(((stage)proj_class.stages.get_object(0)).getdescription()!=null){
            stage_obj.setAttribute("description",((stage)proj_class.stages.get_object(0)).getdescription());
        }
        if(((stage)proj_class.stages.get_object(0)).getnotes()!=null){
            stage_obj.setAttribute("notes",((stage)proj_class.stages.get_object(0)).getnotes());
        }
        if(((house)proj_class.houses.get_object(0)).getid()!=null){
            stage_obj.setAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
        }
        stage_obj.setAttribute("x",String.valueOf(((stage)proj_class.stages.get_object(0)).worldx));
        stage_obj.setAttribute("y",String.valueOf(((stage)proj_class.stages.get_object(0)).worldy));
        stage_obj.setAttribute("z",String.valueOf(((stage)proj_class.stages.get_object(0)).getheight()));
        
        int i;
        int num_nodes=((stage)proj_class.stages.get_object(0)).num_nodes;
        int val;
        for(i=0;i<num_nodes;i++){
            XMLElement node_obj=new XMLElement();
            node_obj.setName("node");
            
            val=((stage)proj_class.stages.get_object(0)).getx(i);
            node_obj.setAttribute("x",String.valueOf(val));
            
            val=((stage)proj_class.stages.get_object(0)).gety(i);
            node_obj.setAttribute("y",String.valueOf(val));
            
            val=((stage)proj_class.stages.get_object(0)).getheight();
            node_obj.setAttribute("z",String.valueOf(val));
            stage_obj.addChild(node_obj);
        }
        
        parent_obj.addChild(stage_obj);
        
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting stage object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_inventory(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/inventory.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        //write the xml header
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("root_List");
        parent_obj.setName("inventory_list");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        int i;
        for(i=0;i<proj_class.inventories.getNumItems();i++){
            XMLElement inv_obj=new XMLElement();
            inv_obj.createElement("inv_obj");
            inv_obj.setName("inventory");
            
            inv_obj.setAttribute("id",String.valueOf(proj_class.inventories.getItemID(i)));
            
            inv_obj.setAttribute("type",proj_class.inventories.getItemType(i));
            
            inv_obj.setAttribute("desc",proj_class.inventories.getItemDesc(i));
            
            parent_obj.addChild(inv_obj);
        }
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting inventory object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_types(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/known_types.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("root_List");
        parent_obj.setName("type_list");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        
        int i;
        for(i=0;i<proj_class.types.size();i++){
            XMLElement type_obj=new XMLElement();
            type_obj.createElement("type");
            type_obj.setName("type");
            
            //attributes for bar
            
            type_obj.setAttribute("name",((knowntype)proj_class.types.get(i)).getName());
            type_obj.setAttribute("height",String.valueOf(((knowntype)proj_class.types.get(i)).getHeight()));
            type_obj.setAttribute("desc",((knowntype)proj_class.types.get(i)).getDesc());
            type_obj.setAttribute("aim",String.valueOf(((knowntype)proj_class.types.get(i)).getAim()));
            
            
            int j;
            int num_nodes=((knowntype)proj_class.types.get(i)).getNumNodes();
            int val;
            for(j=0;j<num_nodes;j++){
                XMLElement node_obj=new XMLElement();
                node_obj.setName("node");
                
                val=((knowntype)proj_class.types.get(i)).getX(j);
                node_obj.setAttribute("x",String.valueOf(val));
                
                val=((knowntype)proj_class.types.get(i)).getY(j);
                node_obj.setAttribute("y",String.valueOf(val));
                
                val=((knowntype)proj_class.types.get(i)).getHeight();
                node_obj.setAttribute("z",String.valueOf(val));
                type_obj.addChild(node_obj);
            }
            
            parent_obj.addChild(type_obj);
            
            
        }
        try{
            writer.write(parent_obj,true);
        }catch(Exception e){
            System.out.println("Error exporting types to xml");
        }
    }
    
    //*****************************************************************************
    public void set_instrument(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        
        try{
            outputfile = new File(projPath+"/instruments.xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("root_List");
        parent_obj.setName("instrument_list");
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        
        int i;
        for(i=0;i<proj_class.instruments.get_num_objects();i++){
            XMLElement ins_obj=new XMLElement();
            ins_obj.createElement("instrument_obj");
            ins_obj.setName("instrument");
            
            //attributes for instrument name, description, bar_id, x, y, z, inventory_id, dimmer_id, type
            
            ins_obj.setAttribute("name",((instrument)proj_class.instruments.get_object(i)).getName());
            ins_obj.setAttribute("description",((instrument)proj_class.instruments.get_object(i)).getDescription());
            ins_obj.setAttribute("bar_id",String.valueOf(((instrument)proj_class.instruments.get_object(i)).getBarID()));
            ins_obj.setAttribute("type",((instrument)proj_class.instruments.get_object(i)).getType());
            ins_obj.setAttribute("x",String.valueOf(((instrument)proj_class.instruments.get_object(i)).worldx));
            ins_obj.setAttribute("y",String.valueOf(((instrument)proj_class.instruments.get_object(i)).worldy));
            ins_obj.setAttribute("z",String.valueOf(((instrument)proj_class.instruments.get_object(i)).worldz));
            ins_obj.setAttribute("inventory_id",String.valueOf(((instrument)proj_class.instruments.get_object(i)).getInventoryID()));
            ins_obj.setAttribute("dimmer_id",String.valueOf(((instrument)proj_class.instruments.get_object(i)).getDimmerId()));
            ins_obj.setAttribute("aimX",String.valueOf(((instrument)proj_class.instruments.get_object(i)).aimx));
            ins_obj.setAttribute("aimY",String.valueOf(((instrument)proj_class.instruments.get_object(i)).aimy));
            ins_obj.setAttribute("aimZ",String.valueOf(((instrument)proj_class.instruments.get_object(i)).aimz));
            
            ins_obj.setAttribute("colorR",String.valueOf(((instrument)proj_class.instruments.get_object(i)).R));
            ins_obj.setAttribute("colorG",String.valueOf(((instrument)proj_class.instruments.get_object(i)).G));
            ins_obj.setAttribute("colorB",String.valueOf(((instrument)proj_class.instruments.get_object(i)).B));
            ins_obj.setAttribute("radius",String.valueOf(((instrument)proj_class.instruments.get_object(i)).radius));
            
            
            parent_obj.addChild(ins_obj);
        }
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting instruments object ot xml");
        }
    }
    
    
    //************************************************************
    public void set_project(String path, boolean house, boolean stage,boolean set, boolean bar, boolean instrument, boolean inventory, boolean types){
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        
        try{
            outputfile = new File(path+".xml");
            outputfile.createNewFile();
            out = new FileOutputStream(outputfile);
        }catch(Exception e){
            System.out.println("Exception opening file");
        }
        
        try{
            if(out!=null){
                writer = new XMLWriter((OutputStream)out);
            }
        }catch(Exception e){
            System.out.println("exception opening writer");
        }
        
        //variables
        String pname=proj_class.open_project_name;
        
        XMLElement parent_obj=new XMLElement();
        parent_obj.createElement("project");
        parent_obj.setName("project");
        
        
        parent_obj.setAttribute("project_name",proj_class.open_project_name);
        parent_obj.setAttribute("isHouse",String.valueOf(house));
        parent_obj.setAttribute("isStage",String.valueOf(stage));
        parent_obj.setAttribute("isSets",String.valueOf(set));
        parent_obj.setAttribute("isBars",String.valueOf(bar));
        parent_obj.setAttribute("isInstruments",String.valueOf(instrument));
        parent_obj.setAttribute("isInventory",String.valueOf(inventory));
        parent_obj.setAttribute("isTypes",String.valueOf(types));
        //can put the preferences into here also but that can be done later
        try{
            if(writer!=null){
                writer.write(parent_obj,true);
            }
        }catch(Exception e){
            System.out.println("Error exporting project object to xml");
        }
    }
}
