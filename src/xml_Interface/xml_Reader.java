/*
 * xml_Reader.java
 *
 * Created on March 2, 2005, 10:03 PM
 *This class will contain all the functions to load the xml data into a project.
 */

package xml_Interface;

/**
 *
 * @author Greg Silverstein
 *edited by josh Z 3/21/05
 */

import net.n3.nanoxml.*;
import Data_Storage.*;
public class xml_Reader {
    
    public project proj_class;
    //Parser Items
    IXMLParser parser;
    IXMLReader reader;
    IXMLElement xml;
    //Final Holders For Locations
    public String s_bPath, s_pPath;
    /** Creates a new instance of xml_Reader */
    public xml_Reader() {
        proj_class=(project)project.oClass;
    }
    
    //main run to load
    //in strings are the path of the project folder as well as the bar file.
    public boolean load_project(String inProjectPath,String inBarPath) {
        s_bPath = inBarPath;
        s_pPath = inProjectPath;
        IXMLParser parser=null;
        IXMLReader reader=null;
        IXMLElement xml=null;
        
        //check what type of file was opened
        try{
            parser = XMLParserFactory.createDefaultXMLParser();
            reader = StdXMLReader.fileReader(inBarPath);
            parser.setReader(reader);
            xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing house");
        }
        
        if(xml==null){
            return false;
        }
        boolean result=false;
        //if it was a house object
        if(xml.getName().equalsIgnoreCase("house_info")){
            result=get_house(xml);
        }else if(xml.getName().equalsIgnoreCase("bar_list")){
            //if it was a bar list
            get_bars(xml);
        }else if(xml.getName().equalsIgnoreCase("stage_info")){
            //if it was a stage object
            get_stage();
        }else if(xml.getName().equalsIgnoreCase("set_list")){
            //if it was a set list
            get_set();
        }else if(xml.getName().equalsIgnoreCase("inventory_list")){
            //if it was an inventory list
            get_inventory();
        }else if(xml.getName().equalsIgnoreCase("type_list")){
            //if it was a type list
            get_knowntypes();
        }else if(xml.getName().equalsIgnoreCase("instrument_list")){
            //if it was an instrument list
            get_instrument();
        }
        return result;
    }
    
    //individual load functions
    private boolean get_house(IXMLElement xml) {
        //create a temp house object
        IXMLElement h_xml=xml.getFirstChildNamed("house");
        house temp_h=new house();
        
        //attributes for house name, overallx, overally, overallz, name, description, id, proj_id
        String name;
        String desc;
        String id;
        int overallx=500;
        int overally=500;
        int overallz=300;
        String proj_id;
        
        name=h_xml.getAttribute("name","DEFAULT");
        desc=h_xml.getAttribute("description","DEFAULT");
        id=h_xml.getAttribute("id","DEFAULT");
        proj_id=h_xml.getAttribute("project_id","DEFAULT");
        try{
            overallx=Integer.parseInt(h_xml.getAttribute("overallx","100"));
            overally=Integer.parseInt(h_xml.getAttribute("overally","100"));
            overallz=Integer.parseInt(h_xml.getAttribute("overallz","100"));
        }catch(Exception e){
            
            System.out.println("there was an error parseing the size of a house from file");
        }
        temp_h.setname(name);
        temp_h.setdescription(desc);
        temp_h.setid(id);
        
        temp_h.worldx=overallx;
        temp_h.worldy=overally;
        temp_h.setheight(overallz);
        
        //get the nodes for the house
        int num=h_xml.getChildrenCount();
        int i;
        for(i=0;i<num;i++){
            IXMLElement n_xml=null;
            
            try{
                n_xml=h_xml.getChildAtIndex(i);
            }catch(Exception e){
                System.out.println("Error opening nodes for house");
            }
            if(n_xml!=null){
                int x=Integer.parseInt(n_xml.getAttribute("x","10"));
                int y=Integer.parseInt(n_xml.getAttribute("y","10"));
                temp_h.add_node(x,y);
            }
        }
        
        
        //temp_h.add_node(
        
        // see if all other objects fit in it
        
        //if everything passed then add the set the house object to active
        if(proj_class.houses.get_num_objects()>0){
            proj_class.houses.remove_object(0);
        }
        proj_class.houses.add_object(temp_h);
        proj_class.houseadded=true;
        proj_class.draw_mouse_state=0;
        proj_class.project_open=true;
        proj_class.forceRepaint();
        
        return true;
        //if it did not pass do not add the house object
        //return false;
        
        
    }
    private void get_bars(IXMLElement xml) {
        
        //create a temp bar object list
        //for each bar in the list
        int num=xml.getChildrenCount();
        int i;
        for(i=0;i<num;i++){
            
            IXMLElement b_xml=xml.getChildAtIndex(i);
            bar temp_b=new bar();
            
            //and add all the bars info to it
            //attributes for bar id, description, x1, x2, y1, y2, z1, z2, house_id
            String bar_id;
            int x1=0,x2=0,y1=0,y2=0,z1=0,z2=0;
            String house_id;
            bar_id=b_xml.getAttribute("bar_id","DEFAULT");
            house_id=b_xml.getAttribute("house_id","DEFAULT");
            try{
                x1=Integer.parseInt(b_xml.getAttribute("x1","100"));
                x2=Integer.parseInt(b_xml.getAttribute("x2","100"));
                y1=Integer.parseInt(b_xml.getAttribute("y1","100"));
                y2=Integer.parseInt(b_xml.getAttribute("y2","100"));
                z1=Integer.parseInt(b_xml.getAttribute("z1","100"));
                z2=Integer.parseInt(b_xml.getAttribute("z2","100"));
            }catch(Exception e){
                
                System.out.println("there was an error parseing the size of the bar from file");
            }
            
            //make the poitns in the correct format
            int wx=x1;
            int wy=y1;
            x1=0;
            y1=0;
            x2=x2-wx;
            y2=y2-wy;
            
            temp_b.worldx=wx;
            temp_b.worldy=wy;
            temp_b.add_node(x1, y1);
            temp_b.add_node(x2, y2);
            
            //get all the dimmers on the bar
            int i2;
            int num2=b_xml.getChildrenCount();
            for(i2=0;i2<num2;i2++){
                IXMLElement n_xml=b_xml.getChildAtIndex(i2);
                int dimmer;
                dimmer=Integer.parseInt(n_xml.getAttribute("dimmer","0"));
                temp_b.setDimmers(i2,dimmer);
            }
            
            //check if the bar is valid
            
            //now add the bar to the project
            
            proj_class.addBar(temp_b);
        }
    }
    private void get_set() {
        
        //create a temporary set list
        
        
        //check if all the items in the list are valid
        
        
        //if they are valid add them to the set list
        
        
        //if they are not valid do not add them
    }
    private void get_stage() {
        
        //create a temporary stage object
        
        //check if the stage object is valid
        
        //if it is valid then load it into
        
    }
    private void get_inventory() {
        
        
        //create a temp inventory list
        
        //compare inventory list to the current one and removce any duplicate obejcts
        
        //then add the new items to the inventory
        
    }
    private void get_knowntypes() {
        
        
        //create a temp list of types
        
        //check if any types are duplicated
        
        //remove any duplicated types
        
        //add teh types to the known list
        
    }
    private void get_instrument() {
        
        //create a temp list of instruments
        
        //check to see if the bars exists for teh bars to go on and if they are in the right positions
        //check to see if the instruments are in inventory
        
        //if the lisntruemnts are valid add them
        
        //if the are not valid then remvoe tem
        
    }
    
    
}
