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
 *
 */

import net.n3.nanoxml.*;
import Data_Storage.project;
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
        project proj_class=(project)project.oClass;
    }
    
    //main run to load
    //in strings are the path of the project folder as well as the bar file.
    public void load_project(String inProjectPath,String inBarPath) {
        s_bPath = inBarPath;
        s_pPath = inProjectPath;
        
        //clear all the current project variables
        
        get_house();
        get_bars();
        get_stage();
        get_inventory();
        get_knowntypes();
        get_instrument();
    }
   
    //individual load functions
    private void get_house() {
     
        try{
            IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
            IXMLReader reader = StdXMLReader.fileReader(s_pPath + "house.xml");
            parser.setReader(reader);
            IXMLElement xml = (IXMLElement) parser.parse();
        }catch(Exception e){
            System.out.println("exception parsing house");
        }
          
      
                
                
            
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
        
        
        //now set all the variables in the project class
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
    }
    
    
}
