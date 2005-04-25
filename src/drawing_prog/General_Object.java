package drawing_prog;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
/**
 *
 * @author jzawisla
 */
public class General_Object {
    
    //world x and y are anchor point for object
    public int worldx;				//- x position in the world for the first node
    public int worldy;				//- y posiiton in the world for the first node
    public int x[]; 				//- list of x positions for nodes in reference to world variables
    public int y[]; 				//- list of y positions for nodes in reference to world variables
    public int num_nodes; 			//- list the amoutn of nodes
    public int index; 				//- lists the index of an object if grouped
    public boolean grouped; 		//- toggled value to see if an object is included in a group
    public int color_index; 		//- index to what color the object should be
    
    
    private Vector colors; 			//- color palette
    private int minx; 			//- value to hold a quick reference to objects area in reference to world variables
    private int miny; 			//- value to hold a quick reference to objects area in reference to world variables
    private int maxx; 		//- value to hold a quick reference to objects area in reference to world variables
    private int maxy; 		//- value to hold a quick reference to objects area in reference to world variables
    private int linestyle;			//- value of the linestyle that the object should be drawn in
    
    public static Object oClass = null;
    
    /** Creates a new instance of General_Object */
    public General_Object() {	//- Default Constructor
        worldx=0;
        worldy=0;
        x=new int[30];
        y=new int[30];
        num_nodes=0;
        
        index=0;
        grouped =false;
    }
    
    //- constructor with parameters
    public General_Object(int wx, int wy, int[] xs, int[] ys, int n,int in, boolean grp){
        int iter;
        worldx=wx;
        worldy=wy;
        x=new int[30];
        y=new int[30];
        for(iter=0;iter<n;iter++){
            x[iter]=xs[iter];
            y[iter]=ys[iter];
            //calculate the bounding box variabels for global usage
            int tempx=xs[iter]+worldx;
            int tempy=ys[iter]+worldy;
            if(iter==0){
                //first node was added so min and max is set to this one
                minx=tempx;
                miny=tempy;
                maxx=tempx;
                maxy=tempy;
                
            }else{
                
                
                if(tempx<minx){
                    minx=tempx;
                }else if(tempx>maxx){
                    maxx=tempx;
                }
                
                if(tempy<miny){
                    miny=tempy;
                }else if(tempy>maxy){
                    maxy=tempy;
                }
            }
        }
        num_nodes=n;
        index=in;
        grouped=grp;
    }
    
    //- draw the object to a given screen
    public boolean draw(Graphics2D screen){
        return draw(screen,1,0,0);
    }
    
    //- draw the object to a given screen at a set scale
    public boolean draw(Graphics2D screen,double scale){
        return draw(screen,scale,0,0);
    }
    
    //- draw the object to a given screen as a set offset
    public boolean draw(Graphics2D screen,int xoffset, int yoffset){
        
        return draw(screen,1,xoffset,yoffset);
    }
    
    //- draw the object to a given screen as a set scale and offset
    public boolean draw(Graphics2D screen,double scale,int xoffset, int yoffset){
        int iter;
        if(num_nodes<1){
            return false;
        }
        
        int g_x = (int)((worldx+xoffset)*scale);
        int g_y = (int)((worldy+yoffset)*scale);
        Color oc=screen.getColor();
        if(color_index==0){
            screen.setColor(Color.BLACK);
        }else if(color_index==1){
            screen.setColor(Color.BLUE);
        }else if(color_index==2){
            screen.setColor(Color.ORANGE);
        }else if(color_index==3){
            screen.setColor(Color.DARK_GRAY);
        }else if(color_index==4){
            screen.setColor(Color.GRAY);
        }else if(color_index==5){
            screen.setColor(Color.GREEN);
        }else if(color_index==6){
            screen.setColor(Color.LIGHT_GRAY);
        }else if(color_index==7){
            screen.setColor(Color.MAGENTA);
        }else if(color_index==8){
            screen.setColor(Color.CYAN);
        }else if(color_index==9){
            screen.setColor(Color.PINK);
        }else if(color_index==10){
            screen.setColor(Color.RED);
        }else if(color_index==11){
            screen.setColor(Color.YELLOW);
        }else if(color_index==12){
            screen.setColor(Color.BLACK);
        }else if(color_index==13){
            screen.setColor(Color.BLACK);
        }else if(color_index==14){
            screen.setColor(Color.BLACK);
        }else if(color_index==15){
            screen.setColor(Color.BLACK);
        }
        
        BasicStroke stroke = new BasicStroke(4);
        //screen.setStroke(stroke);
        
        for(iter=0;iter<num_nodes-1;iter++){
            
            screen.draw(new Line2D.Double(g_x+(x[iter]*scale), g_y+(y[iter]*scale),
                    g_x+(x[iter+1]*scale), g_y+(y[iter+1]*scale)));
            
            
        }
        screen.draw(new Line2D.Double(g_x+(x[0]*scale), g_y+(y[0]*scale),
                g_x+(x[num_nodes-1]*scale), g_y+(y[num_nodes-1]*scale)));
        screen.setColor(oc);
        return true;
    }
    //- ability to edit where a node is located
    public boolean move_node(int index, int newx, int newy){
        if(index<num_nodes){
            x[index]=newx-worldx;
            y[index]=newy-worldy;
            return true;
        }
        
        return false;
    }
    
    //- adds a node to the end of the list of nodes (up to 30)
    public boolean add_node(int gx,int gy){
        
        if(num_nodes<30){
            //set the nodes locally
            x[num_nodes]=gx;
            y[num_nodes]=gy;
            num_nodes++;
            
            
            //calculate the bounding box variabels for global usage
            gx=gx+worldx;
            gy=gy+worldy;
            if(num_nodes==1){
                //first node was added so min and max is set to this one
                minx=gx;
                miny=gy;
                maxx=gx;
                maxy=gy;
                
            }else{
                
                
                if(gx<minx){
                    minx=gx;
                }else if(gx>maxx){
                    maxx=gx;
                }
                
                if(gy<miny){
                    miny=gy;
                }else if(gy>maxy){
                    maxy=gy;
                }
            }
            return true;
        }
        
        return false;
    }
    
    //- add a node into the middle of the list after the indexed node
    public boolean add_node_after(int gx, int gy, int index){
        
        if(num_nodes<30){
            
            int[] tempx=new int[30];
            int count=0;
            int iter;
            //save nodes from index+1 on
            for(iter = index+1;iter<num_nodes;iter++){
                tempx[count]=x[iter];
                count++;
            }
            
            //place the node into index+1
            x[index+1]=gx;
            y[index+1]=gy;
            num_nodes++;
            
            count=0;
            for(iter = index+2;iter<num_nodes;iter++){
                x[iter]=tempx[count];
                count++;
            }
            //calculate the bounding box variabels for global usage
            gx=gx+worldx;
            gy=gy+worldy;
            if(num_nodes==1){
                //first node was added so min and max is set to this one
                minx=gx;
                miny=gy;
                maxx=gx;
                maxy=gy;
                
            }else{
                
                
                if(gx<minx){
                    minx=gx;
                }else if(gx>maxx){
                    maxx=gx;
                }
                
                if(gy<miny){
                    miny=gy;
                }else if(gy>maxy){
                    maxy=gy;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    //- add a node into the middle of the list before the indexed node
    public boolean add_node_before(int gx, int gy, int index){
        if(num_nodes<30){
            int[] tempx=new int[30];
            int count=0;
            int iter;
            //save nodes from index+1 on
            for(iter = index;iter<num_nodes;iter++){
                tempx[count]=x[iter];
                count++;
            }
            
            //place the node into index+1
            x[index]=gx;
            y[index]=gy;
            num_nodes++;
            
            count=0;
            for(iter = index+1;iter<num_nodes;iter++){
                x[iter]=tempx[count];
                count++;
            }
            
            //calculate the bounding box variabels for global usage
            gx=gx+worldx;
            gy=gy+worldy;
            if(num_nodes==1){
                //first node was added so min and max is set to this one
                minx=gx;
                miny=gy;
                maxx=gx;
                maxy=gy;
                
            }else{
                
                
                if(gx<minx){
                    minx=gx;
                }else if(gx>maxx){
                    maxx=gx;
                }
                
                if(gy<miny){
                    miny=gy;
                }else if(gy>maxy){
                    maxy=gy;
                }
            }
            return true;
        }
        
        return false;
    }
    
    //- remove the indexed node
    public boolean remove_node(int index){
        if(num_nodes>0){
            int[] tempx=new int[30];
            int count=0;
            int iter;
            //save nodes from index+1 on
            for(iter = index;iter<num_nodes;iter++){
                tempx[count]=x[iter];
                count++;
            }
            
            for(iter = index+1;iter<num_nodes;iter++){
                x[iter]=tempx[count];
                count++;
            }
            num_nodes--;
            return true;
        }
        
        return false;
    }
    
    //- checks to see if object is in an area using the quick references
    public boolean in_area(int startx, int starty, int endx, int endy){
        int tl_x = minx;
        int tl_y = miny;
        int br_x = maxx;
        int br_y = maxy;
        
        if((tl_x<endx)&&(tl_x>startx)){
            //check if y is in
            if((tl_y<endy)&&(tl_y>starty)){
                return true;
            }
            if((br_y<endy)&&(br_y>startx)){
                return true;
            }
        }
        if((br_x<endx)&&(br_x>startx)){
            //check if y is in area
            if((tl_y<endy)&&(tl_y>startx)){
                return true;
            }
            if((br_y<endy)&&(br_y>startx)){
                return true;
            }
        }
        
        //none of corners of the object were in the area so check if the area was inside the object
        
        if((endx<br_x)&&(endx>tl_x)){
            //check if y is in
            if((endy<br_y)&&(endy>tl_y)){
                return true;
            }
            if((starty<br_y)&&(starty>tl_y)){
                return true;
            }
        }
        if((startx<br_x)&&(startx>tl_x)){
            //check if y is in
            if((endy<br_y)&&(endy>tl_y)){
                return true;
            }
            if((starty<br_y)&&(starty>tl_y)){
                return true;
            }
        }
        
        
        
        return false;
    }
    
    
    //- check to see if an object is in an area using actual points
    //has a flaw will not detect an object who does not have a point that lies within the search area
    public boolean deatailed_in_area(int startx, int starty, int endx, int endy){
        //uses assumption that the area is not totally inside of object because I am not coding that mess
        
        int iter;
        
        for(iter=0;iter<num_nodes;iter++){
            int cur_x = worldx + x[iter];
            int cur_y = worldy + y[iter];
            
            if((cur_x<endx)&&(cur_x>startx)){
                //check if y is in
                if((cur_y<endy)&&(cur_y>starty)){
                    return true;
                }
            }
        }
        
        
        
        return false;
    }
    
    public int closest_node(int pos_x,int pos_y){
        int iter;
        double closest_dist;
        int closest_index;
        if(num_nodes>0){
            closest_dist=Math.sqrt(((worldx+x[0])-pos_x)*((worldx+x[0])-pos_x)+((worldy+y[0])-pos_y)*((worldy+y[0])-pos_y));
            closest_index=0;
            
            for(iter=1;iter<num_nodes;iter++){
                double dist =Math.sqrt(((worldx+x[iter])-pos_x)*((worldx+x[iter])-pos_x)+((worldy+y[iter])-pos_y)*((worldy+y[iter])-pos_y));
                if(dist<closest_dist){
                    closest_dist=dist;
                    closest_index=iter;
                }
            }
            return closest_index;
        }else{
            return -1;
        }
        
        
    }
    
    
    //- set the value of the objects line style
    public int set_linestyle(int ls){
        int old_linestyle;
        
        old_linestyle=linestyle;
        linestyle=ls;
        
        return old_linestyle;
    }
    
    
    //- set the color of the object
    public int set_color(int c){
        int old_color;
        
        old_color=color_index;
        color_index=c;
        
        return old_color;
    }
    
    
    public void copy_General_Object(General_Object obj){
        
        worldx=obj.worldx;
        worldy=obj.worldy;
        num_nodes=0;
        int iter;
        for(iter=0;iter<obj.num_nodes;iter++){
            add_node(obj.x[iter],obj.y[iter]);
        }
        
        index=obj.index;
        grouped=obj.grouped;
        color_index=obj.color_index;
        
    }
    
    public int getmaxx(){
        return maxx;    
    }
    
    public int getmaxy(){
        return maxy;    
    }
    public int getminx(){
        return minx;    
    }
    public int getminy(){
        return miny;    
    }
}
