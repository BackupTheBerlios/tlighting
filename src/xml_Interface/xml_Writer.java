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
 * edited by JoshZ 3/19/05
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
        
        if(proj_class!=null){
            if(proj_class.houses.get_num_objects()>0){
                set_house(inProjectPath);
                
                if(proj_class.bars.get_num_objects()>0){
                    set_bars(inProjectPath);
                }
                if(proj_class.sets.get_num_objects()>0){
                    set_set(inProjectPath);
                }
                if(proj_class.stages.get_num_objects()>0){
                    set_stage(inProjectPath);
                }
                if(proj_class.instruments.get_num_objects()>0){
                    set_instrument(inProjectPath);
                }
                //set_inventory(inProjectPath);
                //set_knowntypes(inProjectPath);
            }
        }
        
    }
    
    //individual load functions
    
    //*****************************************************************************
    public void set_house(String projPath) {
        File outputfile;
        XMLWriter writer=null;
        FileOutputStream out = null;
        
        try{
            outputfile = new File(projPath+"/house.xml");
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
        
        
        
        XMLElement house_obj=new XMLElement();
        house_obj.createElement("house_obj");
        house_obj.setName("house");
        
        //attributes for house name, overallx, overally, overallz, name, description, id, proj_id
        
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
            house_obj.setAttribute("proj_id","proj_id_0");
        //}
        
        house_obj.setContent("HOUSE EDGES"); //nodes should go here
        
        try{
            if(writer!=null){
            writer.write(house_obj);
            }
        }catch(Exception e){
            System.out.println("Error exporting house object ot xml");
        }
        
        //manually write the xml from the file outputstream
        
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
        
        int i;
        for(i=0;i<proj_class.bars.get_num_objects();i++){
            XMLElement bar_obj=new XMLElement();
            bar_obj.createElement("bar_obj");
            bar_obj.setName("bar");
            
            //attributes for bar id, description, x1, x2, y1, y2, z1, z2, house_id
            if(((bar)proj_class.bars.get_object(i)).getID()!=null){
                bar_obj.setAttribute("id",((bar)proj_class.bars.get_object(i)).getID());
            }
            //if(){
                bar_obj.setAttribute("description","BAR DESCRIPTION");
            //}
            //if(){
                bar_obj.setAttribute("x1",String.valueOf(((bar)proj_class.bars.get_object(i)).getX(0)));
            //}
            //if(){
               bar_obj.setAttribute("x2",String.valueOf(((bar)proj_class.bars.get_object(i)).getX(1)));
            //}        
            //if(){
                bar_obj.setAttribute("y1",String.valueOf(((bar)proj_class.bars.get_object(i)).getY(0)));
            //}
            //if(){
                bar_obj.setAttribute("y2",String.valueOf(((bar)proj_class.bars.get_object(i)).getY(1)));
            //}
            //if(){
               bar_obj.setAttribute("z1",String.valueOf(((bar)proj_class.bars.get_object(i)).getZ(0)));
            //}
            //if(){
                bar_obj.setAttribute("z2",String.valueOf(((bar)proj_class.bars.get_object(i)).getZ(1)));
            //}
            if(((house)proj_class.houses.get_object(0)).getid()!=null){
                bar_obj.setAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
            }
            
            bar_obj.setContent("DIMMERS"); //nodes should go here
            
            try{
                if(writer!=null){
                    writer.write(bar_obj);
                }
            }catch(Exception e){
                System.out.println("Error exporting house object ot xml");
            }
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
            
            set_obj.setContent("SET EDGES"); //nodes should go here
            
            try{
                if(writer!=null){
                writer.write(set_obj);
                }
            }catch(Exception e){
                System.out.println("Error exporting house object ot xml");
            }
        }
    }
    
    //*****************************************************************************
    public void set_stage(String projPath) {
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
        
        
        XMLElement stage_obj=new XMLElement();
        stage_obj.createElement("stage_obj");
        stage_obj.setName("stage");
        
        //attributes for stage name, id, house_id, x, y, z
        
        stage_obj.setAttribute("description",((stage)proj_class.stages.get_object(0)).getdescription());
        stage_obj.setAttribute("notes",((stage)proj_class.stages.get_object(0)).getnotes());
        stage_obj.setAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
        stage_obj.setAttribute("x",String.valueOf(((stage)proj_class.stages.get_object(0)).worldx));
        stage_obj.setAttribute("y",String.valueOf(((stage)proj_class.stages.get_object(0)).worldy));
        stage_obj.setAttribute("z",String.valueOf(((stage)proj_class.stages.get_object(0)).getheight()));
        
        stage_obj.setContent("STAGE EDGES"); //nodes should go here
        
        try{
            if(writer!=null){
            writer.write(stage_obj);
            }
        }catch(Exception e){
            System.out.println("Error exporting house object ot xml");
        }
        
    }
    
    //*****************************************************************************
    public void set_inventory(String projPath) {
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
        
        int i;
        for(i=0;i<proj_class.bars.get_num_objects();i++){
            XMLElement bar_obj=new XMLElement();
            bar_obj.createElement("bar_obj");
            bar_obj.setName("bar");
            
            //attributes for bar
            
            bar_obj.setAttribute("id",((bar)proj_class.bars.get_object(i)).getID());
            
            bar_obj.setContent("HOUSE CONTENT"); //nodes should go here
            
            try{
                if(writer!=null){
                    writer.write(bar_obj);
                }
            }catch(Exception e){
                System.out.println("Error exporting house object ot xml");
            }
        }
    }
    
    //*****************************************************************************
    public void set_knowntypes(String projPath) {
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
        
        int i;
        for(i=0;i<proj_class.bars.get_num_objects();i++){
            XMLElement bar_obj=new XMLElement();
            bar_obj.createElement("bar_obj");
            bar_obj.setName("bar");
            
            //attributes for bar
            
            bar_obj.setAttribute("id",((bar)proj_class.bars.get_object(i)).getID());
            
            bar_obj.setContent("HOUSE CONTENT"); //nodes should go here
            
            try{
                writer.write(bar_obj);
            }catch(Exception e){
                System.out.println("Error exporting house object ot xml");
            }
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
        
        int i;
        for(i=0;i<proj_class.bars.get_num_objects();i++){
            XMLElement ins_obj=new XMLElement();
            ins_obj.createElement("bar_obj");
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
            
            ins_obj.setContent("Instrument Contents"); //nodes should go here
            
            try{
                if(writer!=null){
                    writer.write(ins_obj);
                }
            }catch(Exception e){
                System.out.println("Error exporting house object ot xml");
            }
        }
    }
}
