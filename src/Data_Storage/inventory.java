/*
 * inventory.java
 *
 * Created on February 19, 2005, 3:28 PM
 */

package Data_Storage;
import drawing_prog.*;
/**
 *
 * @author Jon Chaplin
 */
public class inventory
{
    private String type;
    private int inv_id;
    private String desc;
    
    private String inv_name;
    private String proj_id;
    private int num_items;
    private int[] list;
    
    /** Creates a new instance of inventory */
    public inventory()
    {
        type = "NULL";
        inv_id = 0;
        desc = "NULL";
        inv_name = "NULL";
        proj_id = "NULL";
        num_items = 0;
        list = new int[5];
    }
    
    void setType(String typeval)
    {
        this.type = typeval;
    }
    void setInvId(int id)
    {
        if(id > 0)
            this.inv_id = id;
        else
            System.out.println("You must enter a non-negative inventory id.");
    }
    void setDesc(String descval)
    {
        this.desc = descval;
    }
    void setInvName(String nameval)
    {
        this.inv_name = nameval;
    }
    void setProjId(String projval)
    {
        this.proj_id = projval;
    }
    void setNumItems(int numitems)
    {
        if(numitems > 0)
            this.num_items = numitems;
        else
            System.out.println("You must enter a non-negative number of items.");
    }
    void setList(int index, int val)
    {
        this.list[index] = val;
    }
    
    int getInvId()
    {
        return this.inv_id;
    }
    String getType()
    {
        return this.type;
    }
    String getDesc()
    {
        return this.desc;
    }
    String getInvName()
    {
        return this.inv_name;
    }
    String getProjId()
    {
        return this.proj_id;
    }
    int getNumItems()
    {
        return this.num_items;
    }
    int getList(int index)
    {
        return this.list[index];
    }
    
    void inventoryOut()
    {
        System.out.println("The type is: "+this.type);
        System.out.println("The inventory id is: "+this.inv_id);
        System.out.println("The description is: "+this.desc);
        System.out.println("The inventory name is: "+this.inv_name);
        System.out.println("The project id is: "+this.proj_id);
        System.out.println("The number of items is: "+this.num_items);
        for(int i = 0; i < 5; i++)
        {
            System.out.println("list item "+i+1+" is: "+list[i]);
        }
    }
}
