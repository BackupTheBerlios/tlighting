/*
 * xml_Writer.java
 *
 * Created on March 2, 2005, 10:05 PM
 * This will contain the xml output routines, from objects to file
 */

package xml_Interface;

import net.n3.nanoxml.*;
import Data_Storage.project;
/**
 *
 * @author Greg Silverstein
 */
public class xml_Writer {
    
    public project proj_class;              //Storage point for project
    /** Creates a new instance of xml_Writer */
    public xml_Writer() {
        project proj_class=(project)project.oClass;
    }
     public void save_project(String inProjectPath,String inBarPath) {
    }
   
    //individual load functions
    public void set_house() {
    }
    public void set_bars() {
    }
    public void set_set() {
    }
    public void set_stage() {
    }
    public void set_inventory() {
    }
    public void set_knowntypes() {
    }
    public void set_instrument() {
    }
}