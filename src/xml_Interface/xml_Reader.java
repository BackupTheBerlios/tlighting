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
    /** Creates a new instance of xml_Reader */
    public xml_Reader() {
        project proj_class=(project)project.oClass;
    }
    
    //main run to load
    //in strings are the path of the project folder as well as the bar file.
    public void load_project(String inProjectPath,String inBarPath) {
    }
   
    //individual load functions
    public void get_house() {
    }
    public void get_bars() {
    }
    public void get_set() {
    }
    public void get_stage() {
    }
    public void get_inventory() {
    }
    public void get_knowntypes() {
    }
    public void get_instrument() {
    }
    
    
}
