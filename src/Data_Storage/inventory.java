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
 * last edited by joshua Zawislak 4-18-05
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

    public void setInvName(String nameval)
    {
        this.inv_name = nameval;
    }

    public void setProjId(String projval)
    {
        this.proj_id = projval;
    }

    public void setNumItems(int numitems)
    {
        if(numitems > 0)
            this.num_items = numitems;
        else
            System.out.println("You must enter a non-negative number of items.");
    }

    public void addItem(String type,int id,String desc)
    {
        inventory_item temp = new inventory_item();
        temp.setType(type);
        temp.setInvId(id);
        temp.setDesc(desc);
        this.list.add(temp);
        num_items++;
    }

    public void removeItem(int index){
        if(index<list.size()){
            list.remove(index);
            num_items--;
        }
    }

   

    public String getInvName()
    {
        return this.inv_name;
    }

    public String getProjId()
    {
        return this.proj_id;
    }

    public int getNumItems()
    {
        return this.num_items;
    }

    public inventory_item getList(int index)
    {
        return (inventory_item)list.get(index);
    }

    public String getItemType(int index){
        if(index<num_items){
            return ((inventory_item)list.get(index)).getType();
        }else{
            return "NULL";
        }
    }
    public int getItemID(int index){
        if(index<num_items){
            return ((inventory_item)list.get(index)).getInvId();
        }else{
            return -1;
        }
    }
    public String getItemDesc(int index){
        if(index<num_items){        
            return ((inventory_item)list.get(index)).getDesc();
        }else{
            return "NULL";
        }
    }
    
    public boolean getItemUsed(int index){
        if(index<list.size()){
            return ((inventory_item)list.get(index)).getUsed();
        }
        return false;
    }
    public void setItemUsed(int index,boolean used){
        if(index<list.size()){
            ((inventory_item)list.get(index)).setUsed(used);
        }
    }
    
    public int getItembyID(int id){
        int i;
        for(i=0;i<list.size();i++){
            if(((inventory_item)list.get(i)).getInvId()==id){
                return i;
            }
        }
        return -1;
    }
    
    public void inventoryOut()
    {
        System.out.println("The inventory name is: "+this.inv_name);
        System.out.println("The project id is: "+this.proj_id);
        System.out.println("The number of items is: "+this.num_items);
        for(int i = 0; i < 5; i++)
        {
            System.out.println("list item "+i+1+" is: "+list.get(i));
        }
    }
    
    public void editEntry(int index,int aID,String aType, String aDesc){
        if(index<num_items){
            ((inventory_item)list.get(index)).setDesc(aDesc);
            ((inventory_item)list.get(index)).setInvId(aID);
            ((inventory_item)list.get(index)).setType(aType);
            
        }
        
    }
}






