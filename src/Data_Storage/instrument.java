/*
 * instrument.java
 *
 * Created on February 5, 2005, 11:16 AM
 */

package Data_Storage;

/**
 *
 * @author Harikrishna Patel
 */

import drawing_prog.*;
import java.lang.String;


public class instrument extends drawing_prog.General_Object {
    
    public int InventoryID;
    public int worldz;
    public String name;
    public String link_type;
    public String Description;
    public int Associated_barID;
    public int Associated_dimmerID;
    public String misc;
    public int aimx;
    public int aimy;
    public int aimz;
    public float R;
    public float G;
    public float B;
    public int radius;
    int level;
    
    /** Creates a new instance of instrument */
    public instrument() {
        InventoryID = 0;
        worldz=0;
        name="NULL";
        link_type="NULL";
        Description="NULL";
        Associated_barID=0;
        Associated_dimmerID=0;
        misc="NULL";
        oClass=this;
        aimx=-1;
        aimy=-1;
        aimz=-1;
        R=0;
        G=0;
        B=0;
        radius=0;
    }
    
    public instrument(int InvID, int worldzs, String names, String link_types, String Desc, int barID,int dimerID, String miscs){
        InventoryID = InvID;
        worldz = worldzs;
        name = names;
        link_type = link_types;
        Description = Desc;
        Associated_barID = barID;
        Associated_dimmerID = dimerID;
        misc = miscs;
        aimx=-1;
        aimy=-1;
    }
    
     public void setX(int xval)
     {
        worldx = xval; //only one point on instrument, so set first index.
     }    
    
    public void setY(int yval)
    {
        worldy = yval; //only one point on instrument, so set first index.
    }
    
    public void setZ(int zval)
    {
        worldz = zval; //only one point on instrument, so set first index.
    }
    
    public void setInvnetoryID(int id)
    {
        InventoryID = id;
    }
    
    public void setBarID(int barid)
    {
        Associated_barID = barid;
    }
    
    public void setDimmerID(int dimid)
    {
        Associated_dimmerID = dimid;
    }
    
    public void setName(String name)
    {
        name = name;
    }
    
    public void setType(String type)
    {
        this.link_type = type;
    }
    
    public void setDescription(String desc)
    {
        this.Description = desc;
    }
    
    public void setExtras(String extra)
    {
        this.misc = extra;
    }
    
    public int getX()
    {
        return this.worldx;  //only one point on instrument, so set first index.        
    }
    
    public int getY()
    { 
        return this.worldy; //only one point on instrument, so set first index.
    }
    
    public int getZ()
    {
        return this.worldz; //only one point on instrument, so set first index.
    }
    
    public int getInventoryID()
    {
        return this.InventoryID;
    }
    
    public int getBarID()
    {
        return this.Associated_barID;
    }
    
    public int getDimmerId()
    {
        return this.Associated_dimmerID;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getType()
    {
        return this.link_type;
    }
    
    public String getDescription()
    {
        return this.Description;
    }
    
    public String getMisc()
    {
        return this.misc;
    }
    
    public void instrOutput()
    {
        System.out.println("The x coordinate of the instrument is: "+worldx+" .");
        System.out.println("The y coordinate of the instrument is: "+worldy+" .");
        System.out.println("The z coordinate of the instrument is: "+worldz+" .");
        
        System.out.println("The inventory space id related to this instrument is: " + this.InventoryID + " .");
        System.out.println("The bar id related to this instrument is: " + this.Associated_barID + " .");
        System.out.println("The dimmer id related to this instrument is: " + this.Associated_dimmerID + " .");
        
        System.out.println("The name of the instrument is: " + this.name + " .");
        System.out.println("The type of the instrument is: " + this.link_type + " .");
        System.out.println("The description of the instrument is: " + this.Description + " .");
        System.out.println("The miscellaneous accessories of the instrument are: " + this.misc + " .\n\n");
    }
    
    public int getLevel(){
        return level;
    }
    public void setLevel(int l){
        if(l>100){
            l=100;
        }
        if(l<0){
            l=0;
        }
        level=l;
    }
}
