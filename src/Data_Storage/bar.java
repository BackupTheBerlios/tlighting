/*
 * bar.java
 *
 * Created on February 5, 2005, 11:15 AM
 */

package Data_Storage;

import drawing_prog.*;

/**
 *
 * @author Harikrishna Patel
 */




public class bar extends General_Object {
    
    private int[] z; //the array to hold z positions of the bar. Each bar has 2.
    // int worldz;
    private double distance;
    private int num_dimmers;
    private int dimmers[];
    private int dis_dimmers[];
    private int barID;
    private String linkProj;
    private String Name;
    
    /** Creates a new instance of hold_bar */
    public bar() {
        z=new int[30];
        distance=0;
        num_dimmers=0;
        dis_dimmers=new int[10];
        dimmers=new int[4096];
        barID=0;
        Name="Bar";
        linkProj="NULL";
        oClass=this;
        
    }
    
    
    public bar(int[] xs, int[] ys,int[] zs, int num_dimers, int[] dis_dimers, int barId, String linkproj, String name){
        int temp;
        z=new int[30];
        for(int i=0;i<2;i++){
            x[i]=xs[i];
            y[i]=ys[i];
            z[i]=zs[i];
        }
        distance = distance();
        num_dimmers = num_dimers;
        for(temp=0;temp<num_dimmers;temp++){
            dis_dimmers[temp]=dis_dimers[temp];
        }
        barID=barId;
        linkProj=linkproj;
        Name=name;
    }
    
    public double distance(){
        double temp1;
        int temp2;
        /*for (temp2=0;temp2<3;temp2++){
            pow (xyz1s[0]-xyz2s[0]),2)
        }*/
        distance = Math.sqrt((Math.pow((x[0]-x[1]),2)+Math.pow((y[0]-y[1]),2)));
        return distance;
    }
    
    public void setX(int index, int xval) {
        if(index > 1)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("ERROR: A bar object has no more than 2 points! \n");
        } else {
            this.x[index] = xval;
        }
    }
    
    public void setY(int index, int yval) {
        if(index > 1)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("ERROR: A bar object has no more than 2 points!\n");
        } else {
            this.y[index] = yval;
        }
    }
    
    public void setZ(int index, int zval) {
        if(index > 1)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("ERROR: A bar object has no more than 2 points!");
        } else {
            this.z[index] = zval;
        }
    }
    
    public void setDistance(int dis){
        this.distance = dis;
    }
    
    public void setNumDim(int num) {
        if(num > 4096) {
            System.out.println("ERROR: The entered number exceeds the maximum dimmer size of 4096. Please change it.\n");
        } else {
            this.num_dimmers = num;
        }
    }
    
    public void setID(int bid) {
        this.barID = bid;
    }
    
    public void setDimmers(int index, int val) {
        //s^12 is the cap on the number of dimmers
        if(index > 4096)//if the specified index to set exceeds the number of dimmers.
        {
            System.out.println("ERROR: A bar has no more than 4096 dimmers, please enter a number that is 4096 or lower!\n");
        } else if(index > (this.num_dimmers - 1))//else if the specified index exceeds the number of dimmers set for object.
        {
            System.out.println("ERROR: The entered dimmer number exceeds the number of dimmers on the bar. Please change the value passed to this function or the numDim parameter.\n");
        } else {
            this.dimmers[index] = val;
        }
    }
    
    public void setDis_dimmer(int index, int dist) {
        //2^12 is cap on number of dimmers
        if(index > 4096) {
            System.out.println("ERROR: A bar has no more than 4096 dimmers, please enter a number that is 4096 or lower!\n");
        } else if(index > (this.num_dimmers - 1))//else if the specified index exceeds the number of dimmers set for object.
        {
            System.out.println("ERROR: The entered dimmer number exceeds the number of dimmers on the bar. Please change the value passed to this function or the numDim parameter.\n");
        } else {
            this.dis_dimmers[index] = dist;
        }
    }
    
    public void setProjectName(String name) {
        this.linkProj = name;
    }
    
    public void setName(String name){
        this.Name=name;
    }
    
    public int getX(int index) {
        if(index > 2)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("A bar object has no more than 2 points!");
            return -1;
        } else {
            return this.x[index];
        }
    }
    
    public int getY(int index) {
        if(index > 2)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("ERROR: A bar object has no more than 2 points!\n");
            return -1;
        } else {
            return this.y[index];
        }
    }
    
    public int getZ(int index) {
        if(index > 2)//2 points make bar. 0, 1 are the indices in the array.
        {
            System.out.println("ERROR: A bar object has no more than 2 points!\n");
            return -1;
        } else {
            return this.z[index];
        }
    }
    public int getHeight(){
        if(this.z[0]>this.z[1]){
            return this.z[0];
        }else{
            return this.z[1];
        }
    }
    
    public double getDistance() {
        return this.distance;
    }
    
    public int getNum_dimmers() {
        return this.num_dimmers;
    }
    
    public int getID() {
        return this.barID;
    }
    
    public int getDimmer(int index) {
        if(index > 4096) {
            System.out.println("ERROR: A bar has no more than 4096 dimmers, please enter a number that is 4096 or lower!\n");
            return -1;
        } else if(index > (this.num_dimmers - 1))//else if the specified index exceeds the number of dimmers set for object.
        {
            System.out.println("ERROR: The entered dimmer number exceeds the number of dimmers on the bar. Please change the value passed to this function or the numDim parameter.\n");
            return -1;
        } else {
            return this.dimmers[index];
        }
    }
    
    public int getDis_dimmers(int index) {
        if(index > 4096) {
            System.out.println("ERROR: A bar has no more than 4096 dimmers, please enter a number that is 4096 or lower!\n");
            return -1;
        } else if(index > (this.num_dimmers - 1))//else if the specified index exceeds the number of dimmers set for object.
        {
            System.out.println("ERROR: The entered dimmer number exceeds the number of dimmers on the bar. Please change the value passed to this function or the numDim parameter.\n");
            return -1;
        } else {
            return this.dis_dimmers[index];
        }
    }
    
    public String getProjectName() {
        return this.linkProj;
    }
    
    public String getName(){
        return this.Name;
    }
    
    public void copyBar(bar abar){
        int iter;
        copy_General_Object(abar);
        
        for(iter=0;iter<abar.num_nodes;iter++){
            z[iter]=abar.z[iter];
        }
        distance=abar.distance;
        num_dimmers=abar.num_dimmers;
        for(iter=0;iter<num_dimmers;iter++){
            dimmers[iter]=abar.dimmers[iter];
            dis_dimmers[iter]=abar.dis_dimmers[iter];
        }
        barID=abar.barID;
        linkProj=abar.linkProj;
        Name=abar.Name;
        
    }
    
    public void barOutput() {
        System.out.println("The x coordinate of the first point is: "+this.x[0]+" .");
        System.out.println("The x coordinate of the second point is: "+this.x[1]+" .");
        System.out.println("The y coordinate of the first point is: "+this.y[0]+" .");
        System.out.println("The y coordinate of the second point is: "+this.y[1]+" .");
        System.out.println("The z coordinate of the first point is: "+this.z[0]+" .");
        System.out.println("The z coordinate of the second point is: "+this.z[1]+" .");
        System.out.println("The Diatnce between ends of the bar is: "+this.distance);
        System.out.println("The number of dimmers on the bar is: "+this.num_dimmers);
        System.out.println("The ID of the bar is: "+this.barID);
        System.out.println("***Begin Dimmer List Output***");
        for(int i = 0; i < this.num_dimmers; i++) {
            System.out.println("\tDimmer number "+ (i+1) + " has the ID " + this.dimmers[i] + " and is "+this.dis_dimmers[i]+ " down the bar.");
        }
        System.out.println("***End Dimmer List Output***");
        System.out.println("The project name is: "+this.linkProj+"\n\n");
    }
    
    public String getDimmerString(){
        String dimmerText="";
        try{
            int i=0;
            for(i=0;i<this.getNum_dimmers();i++){
                dimmerText+=String.valueOf(this.getDimmer(i))+",";
            }
        }catch(Exception e){
            System.out.println("Error convertering dimmer values to string");
        }
    
    return dimmerText;
    }
    
}