/*
 * setobject.java
 *
 * Created on February 15, 2005, 5:13 PM
 */

package Data_Storage;

import drawing_prog.*;

public class setobject extends General_Object{
    
    private int size;
    private String name;
    private String description;
    private int vertices;
    
    /** Creates a new instance of space */
    public setobject() {
        size=0;
        name="NULL";
        description="NULL";
        vertices=0;
        oClass=this;
    }
    
    public setobject(int[] xs, int[] ys, int vertices, int siz, String nam, String descrip){
        for(int i=0;i<vertices;i++){
            x[i]=xs[i];
            y[i]=ys[i];
        }
        size=siz;
        name=nam;
        description=descrip;
               
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
    
    public void setsize(int siz){
        this.size=siz;
    }
    
    public void setname(String nam){
        this.name=nam;
    }
    
    public void setdescription(String descrip){
        this.description=descrip;
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
    
    public int getvertices(){
        return this.vertices;
    }
    
    public int getsize(){
        return this.size;
    }
    
    public String getname(){
        return this.name;
    }
    
    public String getdescription(){
        return this.description;
    }
 
    public void copySetObject(setobject aset){
        copy_General_Object(aset);
        size=aset.getsize();
        name=aset.getname();
        description=aset.getdescription();
        
    }
    
}
