/*
 * Inventory_Item.java
 *
 * Created on April 24, 2005, 2:00 PM
 */

package Data_Storage;

/**
 *
 * @author joshua zawislak
 */
public class inventory_item
{
    private String type;
    private int inv_id;
    private String desc;
    private boolean used;
    
    public inventory_item(){
        type = "NULL";
        inv_id = 0;
        desc = "NULL";
        used=false;
    }

    public int getInvId()
    {
        return this.inv_id;
    }

    public String getType()
    {
        return this.type;
    }
    public String getDesc()
    {
        return this.desc;
    }

    public void setType(String typeval)
    {
        this.type = typeval;
    }

    public void setInvId(int id)
    {
        if(id > 0)
            this.inv_id = id;
        else
            System.out.println("You must enter a non-negative inventory id.");
    }
    
    public void setDesc(String descval)
    {
        this.desc = descval;
    }

    public void inventory_itemOut()
    {
        System.out.println("The type is: "+this.type);
        System.out.println("The inventory id is: "+this.inv_id);
        System.out.println("The description is: "+this.desc);
    }
    
    boolean getUsed(){
        return used;
    }
    
    void setUsed(boolean t){
        used=t;
    }
}