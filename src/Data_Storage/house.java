/*
 * house.java
 *
 * Created on February 15, 2005, 5:14 PM
 */

package Data_Storage;

import drawing_prog.*;

public class house extends General_Object{
    
    private String name;
    private String description;
    private int height;
    private String uniqueid;
    private int vertices;
    
    /** Creates a new instance of house */
    public house() {
        name="NULL";
        description="NULL";
        height=0;
        uniqueid="NULL";
        vertices=0;
    }
    
    public house(int[] xs, int[] ys,int vertices, String nam, String descrip, int hight, String id){
         for(int i=0;i<vertices;i++){
            x[i]=xs[i];
            y[i]=ys[i];
        }
         name=nam;
         description=descrip;
         height=hight;
         uniqueid=id;
    }
    
    public void setx(int[] xs, int vertices){
        for(int i=0;i<vertices;i++){
            this.x[i]=xs[i];
        }
        
    }
    
    public void setx(int xs, int index){
        this.x[index]=xs;
    }
    
    public void sety(int[] ys, int vertices){
        for(int i=0;i<vertices;i++){
            this.y[i]=ys[i];
        }
    }
    
    public void sety(int ys, int index){
        this.y[index]=ys;
    }
    
    public void setname(String nam){
        this.name=nam;
    }
    
    public void setdescription(String descrip){
        this.description=descrip;
    }
    
    public void setheight(int hight){
        this.height=hight;
    }
    
    public void setid(String id){
        this.uniqueid=id;
    }
    
    
    public int[] getxs(){
        return this.x;
    } 
    
    public int getx(int index){
        return this.x[index];
    }
    
    public int[] getys(){
        return this.y;
    }
    
    public int gety(int index){
        return this.y[index];
    }
    
    public String getname(){
        return this.name;
    }
    
    public String getdescription(){
        return this.description;
    }
    
    public int getheight(){
        return this.height;
    }
    
    public String getid(){
        return this.uniqueid;
    }
}
