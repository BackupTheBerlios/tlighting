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
        }
        /*
        //if it was a bar list
        get_bars();
         
        //if it was a stage object
        get_stage();
         
        //if it was a set list
        get_set();
         
        //if it was an inventory list
        get_inventory();
         
        //if it was a type list
        get_knowntypes();
         
        //if it was an instrument list
        get_instrument();
         */
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
    private void get_bars() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_bPath);
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing bars");
        }
        //create a temp bar object list and add all teh bars to it
        
        //check if all the bars are valid
        
        //now set the variables in the project class
    }
    private void get_set() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "set.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing set");
        }
        //create a temporary set list
        
        
        //check if all the items in the list are valid
        
        
        //if they are valid add them to the set list
        
        
        //if they are not valid do not add them
    }
    private void get_stage() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "stage.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing stage");
        }
        //create a temporary stage object
        
        //check if the stage object is valid
        
        //if it is valid then load it into
        
    }
    private void get_inventory() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "inventory.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing inventory");
        }
        
        //create a temp inventory list
        
        //compare inventory list to the current one and removce any duplicate obejcts
        
        //then add the new items to the inventory
        
    }
    private void get_knowntypes() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "known.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("Exception parsing known types");
        }
        
        //create a temp list of types
        
        //check if any types are duplicated
        
        //remove any duplicated types
        
        //add teh types to the known list
        
    }
    private void get_instrument() {
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "instrument.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing instruments");
        }
        //create a temp list of instruments
        
        //check to see if the bars exists for teh bars to go on and if they are in the right positions
        //check to see if the instruments are in inventory
        
        //if the lisntruemnts are valid add them
        
        //if the are not valid then remvoe tem
        
    }
    
    
}
