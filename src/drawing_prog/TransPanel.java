package drawing_prog;

import java.awt.*; 
import java.awt.event.*; 
import java.awt.geom.*; 
import java.awt.image.*; 
import javax.swing.*;
//import java.sql.*;
import java.util.*;
//custom libraries used
import theatre.*;
import Data_Storage.*;

public class TransPanel extends JPanel implements MouseListener, AdjustmentListener
{ 
    AffineTransform at = new AffineTransform(); 
    int w, h; 
    Shape shapes[] = new Shape[3]; 
    BufferedImage bi; 
    
    JScrollBar horiz;
    JScrollBar vert;
    
    public bar temp_bar;
    public instrument temp_instrument; 
    
    public project proj_class;
    
    boolean firstTime = true; 
    public int[] x;
    public int[] y;
    public int numedges;
    Object_Drawer olist; 
    int selected_object;
    double zoomfactor;
    int scroll_x;
    int scroll_y;
    
    public TransPanel(project aproj)
    { 
        proj_class=aproj;
        
        addMouseListener(this);
        setBackground(Color.white); 
        setLayout(null);
        
        horiz = new JScrollBar();
        horiz.setOrientation(Adjustable.HORIZONTAL);
        //horiz.setSize()
        //Dimension d= new Dimension();
        //d.height=20;
        //d.width=BasicWindow.iScreenWidth-(DragandDropPanel.iWidth*2);
        
        //horiz.setBounds(DragandDropPanel.iWidth, 0, BasicWindow.iScreenWidth-(DragandDropPanel.iWidth*2), BasicWindow.iScreenHeight);
        
        //horiz.setPreferredSize(d);
        horiz.addAdjustmentListener(this);
        add(horiz);
      
        
        //horiz.setValue(horiz.getMaximum()/2);
        
        vert = new JScrollBar();
        vert.setOrientation(Adjustable.VERTICAL);
        //d.width=20;
        //d.height=DragandDropPanel.iHeight;
        //vert.setPreferredSize(d);
        vert.addAdjustmentListener(this);
        add(vert);
        
        horiz.setBounds(15,0,600,15);
        horiz.setMaximum(600);
        vert.setBounds(0,15,15,600);
        vert.setMaximum(600);
        //vert.setValue(vert.getMaximum()/2);
        
        
        
        //initialize group of objects
        olist=new Object_Drawer();
        
        General_Object light1;
        General_Object light2;
        General_Object house;
        General_Object bar;
        General_Object stage;
        
        int[] tempx=new int[15];
        int[] tempy=new int[15];
        int wx;
        int wy;
        
        wx=10;
        wy=10;
        tempx[0]=0;
        tempy[0]=0;
        tempx[1]=700;
        tempy[1]=0;
        tempx[2]=700;
        tempy[2]=800;
        tempx[3]=0;
        tempy[3]=800;
        house=new General_Object(wx,wy,tempx,tempy,4,olist.get_num_objects(),true);
        
        wx=20;
        wy=20;
        
        tempx[0]=0;
        tempy[0]=0;
        
        tempx[1]=680;
        tempy[1]=0;
        
        tempx[2]=680;
        tempy[2]=280;
        
        tempx[3]=340;
        tempy[3]=380;
        
        tempx[4]=0;
        tempy[4]=280;
        
        stage=new General_Object(wx,wy,tempx,tempy,5,olist.get_num_objects(),true);
        
        
        olist.add_object(house);
        olist.add_object(stage);
       
        
        proj_class.selected_type= -1;
        proj_class.selected_index=-1;
        proj_class.zoom_factor=1;
        scroll_x=0;
        scroll_y=0;
    } 
    public void drawobjects(Graphics2D g2)
    {
        olist.set_screen(g2); 
        proj_class.bars.set_screen(g2);
        proj_class.instruments.set_screen(g2);
        
        olist.draw_list(proj_class.zoom_factor,0-scroll_x,0-scroll_y);
        proj_class.bars.draw_list(proj_class.zoom_factor,0-scroll_x,0-scroll_y);
        proj_class.instruments.draw_list(proj_class.zoom_factor,0-scroll_x,0-scroll_y);
        
        
        //draw half drawn bars
        if(temp_bar!=null){
            temp_bar.draw(g2,proj_class.zoom_factor,0-scroll_x,0-scroll_y);
        }
        
        if(temp_instrument!=null){
            temp_instrument.draw(g2,proj_class.zoom_factor,0-scroll_x,0-scroll_y);
        }
       // if(proj_class.selected_type==2){
        //    proj_class.bars.draw_object(proj_class.selected_index,proj_class.zoom_factor,0-scroll_x,0-scroll_y);
       // }
        
    }
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        java.sql.Date t=new java.sql.Date(5);
        long start_t;
        long end_t;
        //System.out.println("Painting start");
        start_t=t.getTime();
        drawobjects(g2);
        long diff=0;
       
            end_t=t.getTime();
            diff=end_t-start_t;
        
        
        //System.out.println("Painting done "+diff);
        //repaint();
    } 
      /* Blank mouse listener methods  */
    public void mouseClicked(MouseEvent event) {
    
    }
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {
    
    }
	
    /* Mouse pressed */
    public void mousePressed(MouseEvent e){
        
        if(e.getButton() == MouseEvent.BUTTON3){
            //System.out.println("Mouse 3 Was Clicked with in stage area at "+e.getX()+" "+e.getY());    
            PopUpMenu menu = new PopUpMenu(this,proj_class.selected_type);
            menu.show((Component)this, e.getX(), e.getY());
        }else{
            //System.out.println("Mouse Was Clicked with in stage area at "+e.getX()+" "+e.getY());
            if(proj_class.draw_mouse_state==0){
                //THE NORMAL STATE
                //try to find out which object was selected
                int curx=e.getX();
                int cury=e.getY();
                int search_area=10;
                boolean found_item=false;
                //if the currently select object is the house skip it
                if(proj_class.selected_type!=0){

                }
                //if the currently selected object was the stage skip searching this
                if(proj_class.selected_type!=1){

                }
                //if the currently selected object was a bar skip searching this container
                if(proj_class.selected_type!=2){

                    Object_Drawer items=proj_class.bars.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);

                    if(items.get_num_objects()>0){
                        //there was an item returned
                        //TODO find a good algorithm to select throught the list of items
                        proj_class.selected_type=2;
                        General_Object temp_obj=items.get_object(0);
                        proj_class.selected_index=temp_obj.index;
                        temp_obj.color_index=1;
                        //proj_class.selected_index=
                        found_item=true;
                    }
                }
                
                
                {
                   //else they are trying to select an instrument so see if there are any
                    Object_Drawer items=proj_class.instruments.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);

                    if(items.get_num_objects()>0){
                        //there was an item returned
                        //TODO find a good algorithm to select throught the list of items
                        proj_class.selected_type=3;
                        General_Object temp_obj=items.get_object(0);
                        proj_class.selected_index=temp_obj.index;
                        temp_obj.color_index=1;
                        //proj_class.selected_index=
                        found_item=true;
                    }
                }
                
                
                if(!found_item){
                    if(proj_class.selected_type==2){
                        if(proj_class.selected_index>=0){
                            General_Object temp_obj=proj_class.bars.get_object(proj_class.selected_index);
                            temp_obj.color_index=0;
                        }
                    }else if(proj_class.selected_type==3){
                        if(proj_class.selected_index>=0){
                            General_Object temp_obj=proj_class.instruments.get_object(proj_class.selected_index);
                            temp_obj.color_index=0;
                        }
                    }
                    proj_class.selected_type=-1;
                    proj_class.selected_index=-1;
                }
                repaint();
            }
            else if(proj_class.draw_mouse_state==1)
            {
                //creation of a bar
                //System.out.println("Mouse state was 1 adding "+temp_bar.num_nodes+" node");
                //drawing a bar
                
                if(temp_bar.num_nodes==0){
                    temp_bar.worldx=e.getX();
                    temp_bar.worldy=e.getY();
                    temp_bar.add_node(0,0);
                    
                }else{
                    temp_bar.add_node(e.getX()-temp_bar.worldx,e.getY()-temp_bar.worldy);
                
                    if(temp_bar.num_nodes>=2){
                        proj_class.bars.add_object(temp_bar);
                        proj_class.draw_mouse_state=0;
                        temp_bar=null;
                    }
                }
            }else if(proj_class.draw_mouse_state==2){
                //adding an instrument to the selected bar
                
                //since the instrument has to be on the bar that was selected
                //use the x value of the mouse to find out the y value for the 
                //placement on the bar
                int pot_x=e.getX();
                int pot_y=-1;
                int barx1=proj_class.bars.get_object(temp_instrument.Associated_barID).x[0]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldx;
                int barx2=proj_class.bars.get_object(temp_instrument.Associated_barID).x[1]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldx;
                int bary1=proj_class.bars.get_object(temp_instrument.Associated_barID).y[0]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldy;
                int bary2=proj_class.bars.get_object(temp_instrument.Associated_barID).y[1]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldy;
                
                //swap the points to make the math easier because assume point 1 is to the right
                if(barx1>barx2){
                    int temp_int;
                    temp_int=barx1;
                    barx1=barx2;
                    barx2=temp_int;
                    
                    temp_int=bary1;
                    bary1=bary2;
                    bary2=temp_int;
                
                }
                
                if((pot_x>barx1)&&(pot_x<barx2)){
                    //the x is valid find the y value for it
                    if(barx1-barx2!=0){
                        //to find slope y - y1 = m (x - x1)  or m=(y-y1)/(x-x1)
                        //double slope = (bary1-bary2)/(barx1-barx2);   
                        
                        //to find y with an x use equation y=m(x-x1)+y1
                        //pot_y=(int)(slope*(double)(barx1-barx2)+bary2);
                    		
                        double slope = (double)(bary1-bary2)/ (double)(barx1-barx2);
                        pot_y=(int)(slope*((double)pot_x-(double)barx1)+(double)bary1);
                    }
                                
                }
                
                if(pot_y>=0){
                    //found a y so place it
                    temp_instrument.worldx=pot_x;
                    temp_instrument.worldy=pot_y;
                    proj_class.instruments.add_object(temp_instrument);
                    proj_class.draw_mouse_state=0;
                    proj_class.selected_type=3;
                    proj_class.selected_index=temp_instrument.index;
                    
                }
                
               
            }else if(proj_class.draw_mouse_state==6){
                //moving a bar
                bar selected_bar=(bar)proj_class.bars.get_object(proj_class.selected_index);
                int old_bar_x;
                old_bar_x=selected_bar.worldx;
                int old_bar_y;
                old_bar_y=selected_bar.worldy;
                //move all the instrumetns attached to the bar
                //need to compensate for zoom factor just to tired to do it tonight
                int xdiff=old_bar_x-e.getX();
                int ydiff=old_bar_y-e.getY();
                
                for(int iter=0;iter<proj_class.instruments.get_num_objects();iter++){
                    if(((instrument)proj_class.instruments.get_object(iter)).getBarID()==proj_class.selected_index){
                        proj_class.instruments.get_object(iter).worldx-=xdiff;
                        proj_class.instruments.get_object(iter).worldy-=ydiff;
                    }
                }
                
                //actually move the bar
                proj_class.bars.get_object(proj_class.selected_index).worldx=e.getX();
                proj_class.bars.get_object(proj_class.selected_index).worldy=e.getY();
                
                
                
                proj_class.draw_mouse_state=0;
            }
            
            repaint();
        }
        
        //y[numedges]=e.getY();
        //x[numedges]=e.getX();
        //numedges++;
    }
    
    public void adjustmentValueChanged(AdjustmentEvent e) 
    { 
        scroll_x=horiz.getValue(); 
        scroll_y=vert.getValue();
        repaint();
    }
}
