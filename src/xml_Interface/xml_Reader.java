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
        get_house();
        get_bars();
        get_stage();
        get_inventory();
        get_knowntypes();
        get_instrument();
    }
   
    //individual load functions
    private void get_house() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "house.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();

    }
    private void get_bars() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_bPath);
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    private void get_set() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "set.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    private void get_stage() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "stage.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    private void get_inventory() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "inventory.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    private void get_knowntypes() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "known.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    private void get_instrument() {
        IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
        IXMLReader reader = StdXMLReader.fileReader(s_pPath + "instrument.xml");
        parser.setReader(reader);
        IXMLElement xml = (IXMLElement) parser.parse();
    }
    
    
}
