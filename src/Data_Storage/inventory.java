/*

 * inventory.java

 *

 * Created on February 19, 2005, 3:28 PM

 */



package Data_Storage;

import drawing_prog.*;

import java.util.*;

/**

 *

 * @author Jon Chaplin

 */

public class inventory

{

    

    

    private String inv_name;

    private String proj_id;

    private int num_items;

    private Vector list;

    

    

    /** Creates a new instance of inventory */

    public inventory()

    {

        

        inv_name = "NULL";

        proj_id = "NULL";

        num_items = 0;

        list = new Vector();

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

    void addItem(String type,int id,String desc)

    {

        inventory_item temp = new inventory_item();

        temp.setType(type);

        temp.setInvId(id);

        temp.setDesc(desc);

        this.list.add(temp);

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

    inventory_item getList(int index)

    {

        return (inventory_item)list.get(index);

    }

    

    void inventoryOut()

    {

        System.out.println("The inventory name is: "+this.inv_name);

        System.out.println("The project id is: "+this.proj_id);

        System.out.println("The number of items is: "+this.num_items);

        for(int i = 0; i < 5; i++)

        {

            System.out.println("list item "+i+1+" is: "+list.get(i));

        }

    }

}



class inventory_item

{

    private String type;

    private int inv_id;

    private String desc;

    

    inventory_item(){

        type = "NULL";

        inv_id = 0;

        desc = "NULL";

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

    

    void inventory_itemOut()

    {

        System.out.println("The type is: "+this.type);

        System.out.println("The inventory id is: "+this.inv_id);

        System.out.println("The description is: "+this.desc);

    }

}

