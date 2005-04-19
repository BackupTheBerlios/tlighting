/*
 * knowntype.java
 *
 * Created on February 19, 2005, 3:29 PM
 */
package Data_Storage;

import drawing_prog.*;
/**
 *
 * @author Jon Chaplin
 */

public class knowntype extends General_Object
{
    private String name;  //the name of the known type.
    private int height; //height of the known type
    private String desc; //description of the known type
    private boolean aim; //whether or not the known type can be aimed or not.

    /** Creates a new instance of knowntype */
    public knowntype()
    {
        name = "NULL";  //the name of the custom type
        height = 0;  //the height of the custom type
        desc = "NULL"; //a description of what the custom type does
        num_nodes = 0;  //number of points in a custom object.
        aim = false; 
        x = new int[15];  //initialize the arrays of x and y with 15 values. no more than 15 pts. for custom object.
        y = new int[15];
    }

    

    public void setName(String nameval)
    {
        this.name = nameval;
    }

    public void setHeight(int heightval)
    {
        if(heightval < 0)
        {
            System.out.println("ERROR: Height cannot be a negative number");
        }
        else
        {
            this.height = heightval;
        }    
    }

    public void setDesc(String descval)
    {
        this.desc = descval;
    }

    public void setAim(boolean aimval)
    {
        this.aim = aimval;
    }

    public void setNumNodes(int nodes)
    {
        if((nodes > 15) || (nodes < 0) )  //number of points on object between 0 and 15
        {
            System.out.println("ERROR: The number of nodes for known type may not exceed 15 or go below 0.");
        }    
        else
        {    
            this.num_nodes = nodes;
        }
    }    

    public void setX(int xval, int index)
    {
        if((index < 0) || (index > 14)) //max indices for 15 points.
        {
            System.out.println("A known type may have no more than 15 points. Choose a number 0-14");
        }
        else if(index > (this.num_nodes - 1) )
        {
            System.out.println("The entered index for setX exceeds the number of points set.");
        }
        else
        {    
            this.x[index] = xval;
        }
    }

    public void setY(int yval, int index)
    {
        if((index < 0) || (index > 14))  //max indices for 15 points.
        {

            System.out.println("A known type may have no more than 15 points. Choose a number 0-14");
        }
        else if(index > (this.num_nodes - 1) )
        {

            System.out.println("The entered index for setY exceeds the number of points set.");
        }
        else
        {    
            this.y[index] = yval;
        }
    }

    

    public String getName()
    {
        return this.name;
    }

    public int getHeight()
    {
        return this.height;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public boolean getAim()
    {
        return this.aim;
    }

    public int getNumNodes()
    {
        return this.num_nodes;
    }

    public int getX(int index)
    {

        if((index < 0) || (index > 14))//max indices for 15 points.
        {
            System.out.println("A known type may have no more than 15 points. Choose a number 0-14");
        }
        else if(index > this.num_nodes)
        {
            System.out.println("The entered index for getX exceeds the number of points set.");
        }
        else
        {    
            return this.x[index];
        }
        return -1;
    }

    public int getY(int index)
    {

        if((index < 0) || (index > 14))//max indices for 15 points.
        {
            System.out.println("A known type may have no more than 15 points. Choose a number 0-14");
        }
        else if(index > this.num_nodes)
        {
            System.out.println("The entered index for getY exceeds the number of points set.");
        }
        else
        {    
            return this.y[index];
        }
        return -1;
    }



    public void knownTypeOutput()
    {
        System.out.println("The name of the known type is: "+this.name);
        System.out.println("The height of"+this.name+" is: "+this.height);
        System.out.println("The description of"+this.name+" is: "+this.desc);
        if(this.aim == true)
        {
            System.out.println(this.name+" can be aimed. ");
        }
        else
        {
            System.out.println(this.name+" cannot be aimed. ");
        }
        System.out.println(this.name+" consists of the following x and y coordiantes");
        System.out.println("*******BEGIN POINT OUTPUT*******");
        for(int i = 0; i < this.num_nodes; i++)
        {
            System.out.println("\tPair #"+i+": x: "+this.x[i]+" y: "+this.y[i]);
        }
        System.out.println("********END POINT OUTPUT********");
    }

    public void copy(knowntype obj){
        copy_General_Object(obj);
        name=obj.getName();  
        height=obj.getHeight(); 
        desc=obj.getDesc(); 
        aim=obj.getAim(); 
    }
}

