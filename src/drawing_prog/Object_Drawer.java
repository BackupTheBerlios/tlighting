/*
 * Object_Drawer.java
 *Functions for drawing 2D objects to the java screen
 *
 * Created on January 12, 2005, 7:29 PM
 */
package drawing_prog;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
/**
 *
 * @author jzawisla
 */




public class Object_Drawer {

    private Graphics2D global_screen;		//- screen object that will be drawn to
	public Vector object_list;				//- list of objects that can be drawn
	private int num_objects;				//- number of objects in the list
	private boolean screen_set;				//- keeps track if there is a screen to draw to
        private boolean node_selected;
        public static Object oClass = null;


    /** Creates a new instance of Object_Drawer */
    public Object_Drawer() {  		//-default constructor
       num_objects =0;
       screen_set=false;
        System.out.println("new object list created");       
       object_list = new Vector();

       oClass=this;
    }

	//- set the screen to draw to
    public void set_screen(Graphics2D screen){
        global_screen = screen;
        screen_set=true;
    }

	//- checks if the screen is set
    public boolean is_screen_set(){
        return screen_set;
    }

	//- returns the amount of objects in list
    public int get_num_objects(){
        return num_objects;
    }

	//- adds an object to the list
    public  int add_object(General_Object obj){
        obj.index=num_objects;
        
        object_list.add(obj);
        num_objects++;
        return num_objects;
    }

	//- removes an object from the list by position
	public boolean remove_object(int index){
            if(index<object_list.size()){
                object_list.remove(index);
                return true;
            }
            
            return false;
	}

	//- general drawing function that takes an object
    public boolean draw_general_object(General_Object obj){
		if(screen_set){
	   		return obj.draw(global_screen);
		}else{
			return false;
		}
    }

	//- general drawing function that takes raw data
    public boolean draw_raw_object(int xlist[] ,int ylist[],int num){
        int iter;
			if(!screen_set){
				  return false;
			}
			if(num<1){
		    	return false;
		   	}
		    for(iter=0;iter<num_objects-1;iter++){
		    	global_screen.draw(new Line2D.Double(xlist[iter], ylist[iter], xlist[iter+1], ylist[iter+1]));
		    }
		    global_screen.draw(new Line2D.Double(xlist[0],ylist[0],xlist[num-1],ylist[num-1]));
        return true;

    }

    //- draws an object in the list by index
    public boolean draw_object(int index){
                draw_object(index,1,0,0);
        
		return true;
	}

	//- draws a scaled object in the list by index
	public boolean draw_objet(int index, int scale){
               draw_object(index,scale,0,0);
 
		return true;
	}

	//- draws an object in the list by index offset
	public boolean draw_object(int index, int xoffset, int yoffset){
               draw_object(index,1,xoffset,yoffset);
 
		return true;
	}

	//- draws an object in the list by index scaled and offset
	public boolean draw_object(int index, double scale, int xoffset, int yoffset){
                if(screen_set){
                   if(index<object_list.size()){
                        General_Object temp=(General_Object)object_list.elementAt(index); 
                        temp.draw(global_screen,scale,xoffset,yoffset);
                   } 
                }
        
		return true;
	}

	//- draws all the items in the list
	public boolean draw_list(){
                draw_list(1,0,0);
		return true;
	}

	//- draws all the items in the list scaled
	public boolean draw_list(double scale){
                draw_list(scale,0,0);
		return true;
	}

	//- draws all the items in the list offset
	public boolean draw_list(int xoffset, int yoffset){
                draw_list(1,xoffset,yoffset);
		return true;
	}

	//- draws all the items in the list scaled and offset
	public boolean draw_list(double scale, int xoffset, int yoffset){
                int iter;
            
                for(iter=0;iter<object_list.size();iter++){
                    draw_object(iter,scale,xoffset,yoffset);
                }
		return true;
	}

	//- draws all the items in the list in a given area
	public boolean draw_list(int topx, int topy, int bottomx, int bottomy){
                draw_list(topx, topy, bottomx, bottomy, 1, 0, 0);
		return true;
	}

	//- draws all the items in the list in a given area then scaled
	public boolean draw_list(int topx, int topy, int bottomx, int bottomy, double scale){
                draw_list(topx, topy, bottomx, bottomy, scale, 0, 0);
		return true;
	}

	//- draws all the items in the list in a given area then offset
	public boolean draw_list(int topx, int topy, int bottomx, int bottomy, int offsetx, int offsety){
                draw_list(topx, topy, bottomx, bottomy, 1, offsetx, offsety);
		return true;
	}

	//- draws all the items in a given area then scaled and offset
	public boolean draw_list(int topx, int topy, int bottomx, int bottomy, double scale, int offsetx, int offsety){
                Object_Drawer tempv;
                int iter;
                
                tempv=get_objects_in_area(topx,topy,bottomx,bottomy);
                
                for(iter=0;iter<tempv.num_objects;iter++){
                    tempv.draw_object(iter,scale,offsetx,offsety);                    
                }
                
            
		return true;
	}


	//- returns a vector off all objects within an area
	public Object_Drawer get_objects_in_area(int startx,int starty, int endx, int endy){
                Object_Drawer tempv;
                int iter;
                tempv=new Object_Drawer();
                
                for(iter=0;iter<object_list.size();iter++){
                    General_Object temp=(General_Object)object_list.elementAt(iter);
                    int t_index=temp.index;
                    if(temp.in_area(startx,starty,endx,endy)){
                        tempv.add_object(temp);
                        temp.index=t_index;
                    }
                }
                
		return tempv;
	}

        public General_Object get_object(int index){
            return (General_Object)object_list.get(index);
        }
}
