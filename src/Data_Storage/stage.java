/*
 * stage.java
 *
 * Created on February 15, 2005, 5:13 PM
 */

package Data_Storage;

import drawing_prog.*;

public class stage extends General_Object{
    
    private int height;
    private String notes;
    private String description;
    private int linktohouse;
    private int vertices;
    
    /** Creates a new instance of stage */
    public stage() {
        height=0;
        notes="NULL";
        description="NULL";
        linktohouse=0;
        vertices=0;
        oClass=this;
    }
    
    public stage(int[] xs, int[] ys,int vertices, int hight, String note, String descrip, int linkhouse){
        for(int i=0;i<vertices;i++){
            x[i]=xs[i];
            y[i]=ys[i];
        }
            height=hight;
            notes=note;
            description=descrip;
            linktohouse=linkhouse;
            
        
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
    
    public void setheight(int hight){
        this.height=hight;
    }
    
    public void setnotes(String note){
        this.notes=note;
    }
    
    public void setdescription(String descrip){
        this.description=descrip;
    }
    
    public void setlinktohouse(int linkhouse){
        this.linktohouse=linkhouse;
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
    
    public int getheight(){
        return this.height;
    }
    
    public String getnotes(){
        return this.notes;
    }
    
    public String getdescription(){
        return this.description;
    }
    
    public int getlinktohouse(){
        return this.linktohouse;
    }

    public void copyStage(stage astage){
        copy_General_Object(astage);
        height=astage.getheight();
        notes=astage.getnotes();
        description=astage.getdescription();
        linktohouse=astage.getlinktohouse();
    }
}
