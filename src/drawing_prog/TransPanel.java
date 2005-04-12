package drawing_prog;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

//custom libraries used
import theatre.*;
import Data_Storage.*;

public class TransPanel extends JPanel implements MouseListener, AdjustmentListener {
    AffineTransform at = new AffineTransform();
    int w, h;
    Shape shapes[] = new Shape[3];
    BufferedImage bi;
    JScrollBar horiz;
    JScrollBar vert;
    
    public bar temp_bar;
    public instrument temp_instrument;
    public stage temp_stage;
    public setobject temp_set;
    public house temp_house;
    public int selected_node;
    public project proj_class;
    boolean firstTime = true;
    public int[] x;
    public int[] y;
    public int numedges;
    int selected_object;
    double zoomfactor;
    
    
    public static Object oClass = null;
    
    public TransPanel() {
        setBackground(Color.white);
        setLayout(null);
        
        horiz = new JScrollBar();
        horiz.setOrientation(Adjustable.HORIZONTAL);
        horiz.addAdjustmentListener(this);
        add(horiz);
        
        vert = new JScrollBar();
        vert.setOrientation(Adjustable.VERTICAL);
        vert.addAdjustmentListener(this);
        add(vert);
        //begin chaplin edit
        //horiz.setBounds(15,0,600,15); //x, y, width, height.
        horiz.setBounds(0,BasicWindow.iScreenHeight - 169,BasicWindow.iScreenWidth-(ExplorerBrowserPanel.iWidth*2) - 24, 15);
        horiz.setUnitIncrement((int)(.05 * horiz.getWidth())); //these set how far the scroll bar moves
        System.out.println("the unit increment is: " +horiz.getUnitIncrement());
        horiz.setBlockIncrement((int)(.25 * horiz.getWidth())); //sets increment based on click above/below scroll knob.  Check this.
        System.out.println("the block increment is: " +horiz.getBlockIncrement());
        //this changes with zoom.
        horiz.setMaximum(BasicWindow.iScreenWidth-(ExplorerBrowserPanel.iWidth*2) - 24); //static was 600
        
        vert.setBounds(BasicWindow.iScreenWidth-(ExplorerBrowserPanel.iWidth*2) - 24, 0 ,15,BasicWindow.iScreenHeight - 149);
        //vert.setBounds(0,15,15,600);
        vert.setUnitIncrement( (int)(.05 * vert.getHeight()) );
        System.out.println("the unit increment is: " +vert.getUnitIncrement());
        vert.setBlockIncrement((int)(.25 * vert.getHeight()) );  //sets increment based on click above/below scroll knob. Check this.
        System.out.println("the block increment is: " +vert.getBlockIncrement());
        vert.setMaximum(BasicWindow.iScreenHeight - 149); //static was 600
        //end chaplin edit
        
        proj_class=(project)project.oClass;
        proj_class.selected_type= -1;
        proj_class.selected_index=-1;
        proj_class.zoom_factor=1;
        proj_class.scroll_x=0;
        proj_class.scroll_y=0;
        selected_node=-1;
        oClass=this;
        addMouseListener(this);
    }
    
    public void drawobjects(Graphics2D g2) {
        project proj_class=(project)project.oClass;
        proj_class.houses.set_screen(g2);
        proj_class.bars.set_screen(g2);
        
        proj_class.instruments.set_screen(g2);
        
        proj_class.stages.set_screen(g2);
        
        proj_class.sets.set_screen(g2);
        
        //drawing numbered screen grid here.
        //chaplin edit
        g2.setColor(Color.LIGHT_GRAY);
        for(int i = 100; i < (BasicWindow.iScreenWidth-(ExplorerBrowserPanel.iWidth*2) - 24); i+= 100) {
            String temp = ""+i;
            g2.draw(new Line2D.Double(i, 0, i, 1250));
            //maybe change 1250.
            g2.drawString(temp, i, 10);
        }
        for(int i = 100; i < BasicWindow.iScreenHeight - 149; i+= 100) {
            String temp = ""+i;
            g2.draw(new Line2D.Double(0, i, 1000, i));
            //maybe change 1250.
            g2.drawString(temp, 0, i);
        }
        g2.setColor(Color.BLACK);
        //end chaplin edit
        
        proj_class.houses.draw_list(proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
        if(proj_class.isStageVisible()){
            proj_class.stages.draw_list(proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
        }
        if(proj_class.isSetItemVisible()){
            proj_class.sets.draw_list(proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
        }
        if(proj_class.isBarVisible()){
            proj_class.bars.draw_list(proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
        }
        if(proj_class.isInstrumentVisible()){
            proj_class.instruments.draw_list(proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            //draw the aim points for every instrument
            for(int i=0;i<proj_class.instruments.get_num_objects();i++){
                int x=((instrument)proj_class.instruments.get_object(i)).aimx;
                int y=((instrument)proj_class.instruments.get_object(i)).aimy;
                //int lx=((instrument)proj_class.instruments.get_object(i)).worldx;
                //int ly=((instrument)proj_class.instruments.get_object(i)).worldy;
                g2.draw(new Line2D.Double(proj_class.WorldXtoScreen(x-3), proj_class.WorldYtoScreen(y-3),
                        proj_class.WorldXtoScreen(x+3),proj_class.WorldYtoScreen(y+3)));
                g2.draw(new Line2D.Double(proj_class.WorldXtoScreen(x-3), proj_class.WorldYtoScreen(y+3),
                        proj_class.WorldXtoScreen(x+3), proj_class.WorldYtoScreen(y-3)));
                /*Line2D dotted =new Line2D.Double(proj_class.WorldXtoScreen(x), proj_class.WorldYtoScreen(y),
                        proj_class.WorldXtoScreen(lx), proj_class.WorldYtoScreen(ly));
                 
                g2.draw(dotted);
                 */
            }
            
        }
        
        
        //redraw the item that is selected
        //types are defined as follows 0=house 1=stage 2=bar 3=instrument 4=set
        
        if(proj_class.selected_type==0){
            
            //house
            General_Object temp_obj=proj_class.houses.get_object(proj_class.selected_index);
            temp_obj.set_color(1);
            temp_obj.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_obj.set_color(0);
            
        }else if(proj_class.selected_type==1){
            
            //stage
            General_Object temp_obj=proj_class.stages.get_object(proj_class.selected_index);
            temp_obj.set_color(1);
            temp_obj.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_obj.set_color(0);
            
        }else if(proj_class.selected_type==2){
            
            //bar
            General_Object temp_obj=proj_class.bars.get_object(proj_class.selected_index);
            temp_obj.set_color(1);
            temp_obj.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_obj.set_color(0);
            
        }else if(proj_class.selected_type==3){
            
            //instrument
            General_Object temp_obj=proj_class.instruments.get_object(proj_class.selected_index);
            temp_obj.set_color(1);
            temp_obj.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            
            //draw teh selected aim poitn also
            
            int x=((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimx;
            int y=((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimy;
            int lx=((instrument)proj_class.instruments.get_object(proj_class.selected_index)).worldx;
            int ly=((instrument)proj_class.instruments.get_object(proj_class.selected_index)).worldy;
            
            if((x>=0)&&(y>=0)){
                
                /*g2.draw(new Line2D.Double(proj_class.WorldXtoScreen(x-3), proj_class.WorldYtoScreen(y-3),
                        proj_class.WorldXtoScreen(x+3),proj_class.WorldYtoScreen(y+3)));
                g2.draw(new Line2D.Double(proj_class.WorldXtoScreen(x-3), proj_class.WorldYtoScreen(y+3),
                        proj_class.WorldXtoScreen(x+3), proj_class.WorldYtoScreen(y-3)));
                g2.setColor(Color.YELLOW);
                */
                Line2D dotted =new Line2D.Double(proj_class.WorldXtoScreen(x), proj_class.WorldYtoScreen(y),
                        proj_class.WorldXtoScreen(lx), proj_class.WorldYtoScreen(ly));
                g2.draw(dotted);
                
                g2.setColor(Color.BLACK);
            }
            temp_obj.set_color(0);
        }else if(proj_class.selected_type==4){
            
            //set
            General_Object temp_obj=proj_class.sets.get_object(proj_class.selected_index);
            temp_obj.set_color(1);
            temp_obj.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_obj.set_color(0);
        }
        
        
        //draw half drawn bars
        
        
        
        if(temp_bar!=null){
            temp_bar.set_color(2);
            temp_bar.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_bar.set_color(0);
        }
        
        if(temp_instrument!=null){
            temp_instrument.set_color(2);
            
            temp_instrument.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            //proj_class.zoom_factor
            //1/proj_class.zoom_factor
            //System.out.println("heyooooo drawing instrument!");
            //System.out.println("the zoom factor is: "+proj_class.zoom_factor+" scroll x is: "+proj_class.scroll_x+"scroll y is: "+proj_class.scroll_y);
            temp_instrument.set_color(0);
        }
        
        
        
        if(temp_stage!=null){
            temp_stage.set_color(2);
            temp_stage.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_stage.set_color(0);
        }
        
        
        
        if(temp_set!=null){
            temp_set.set_color(2);
            temp_set.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_set.set_color(0);
        }
        
        if(temp_house!=null){
            temp_house.set_color(2);
            temp_house.draw(g2,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
            temp_house.set_color(0);
            
        }
        
        // if(proj_class.selected_type==2){
        
        
        //    proj_class.bars.draw_object(proj_class.selected_index,proj_class.zoom_factor,0-proj_class.scroll_x,0-proj_class.scroll_y);
        
        
        // }
        
        
        // code for displaying the current node being edited
        if(selected_node>=0){
            
            int t=proj_class.selected_type;
            
            if(t==0){
                //house
                if(temp_house!=null){
                    Ellipse2D.Double node_circ= new Ellipse2D.Double(proj_class.WorldXtoScreen(temp_house.worldx+temp_house.x[selected_node]),
                            proj_class.WorldYtoScreen(temp_house.worldy+temp_house.y[selected_node]),6,6);
                    g2.fill(node_circ);
                }
            }else if(t==1){
                
                //stage
                if(temp_stage!=null){
                    Ellipse2D.Double node_circ= new Ellipse2D.Double(proj_class.WorldXtoScreen(temp_stage.worldx+temp_stage.x[selected_node]),
                            proj_class.WorldYtoScreen(temp_stage.worldy+temp_stage.y[selected_node]),6,6);
                    g2.fill(node_circ);
                }
            }else if(t==2){
                
                //bar
                if(temp_bar!=null){
                    Ellipse2D.Double node_circ= new Ellipse2D.Double(temp_bar.worldx+temp_bar.x[selected_node],
                            temp_bar.worldy+temp_bar.y[selected_node],6,6);
                    g2.fill(node_circ);
                }
            }else if(t==3){
                //instrument
            }else if(t==4){
                
                //set
                if(temp_set!=null){
                    Ellipse2D.Double node_circ= new Ellipse2D.Double(temp_set.worldx+temp_set.x[selected_node],
                            temp_set.worldy+temp_set.y[selected_node],6,6);
                    g2.fill(node_circ);
                }
            }
        }
    }
    
    
    
    public void paintComponent(Graphics g) {
        
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
    public void mouseClicked(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {
    }
    
    
    //draw mouse stages are defiend as 0=normal 1=adding a bar 2=adding an instruemnt 3= editing a bar
    //4=editing a house 5=editing a stage 6=moving a bar 7= moving an instrument 8=adding a stage
    //9=adding a set peice 10=editing a set piece 11=moving a stage 12=moving a house 13=moving a set 14=aiming a light
    
    
    /*implemented:
     *selection = 0
     *addiing bar = 1
     *adding an instrument = 2
     *editing a bar = 3
     *editing a house =4
     *editing a stage = 5
     *moving a bar = 6
     *
     *adding a stage = 8
     *adding a set piece = 9
     *editing a set piece = 10
     *
     *
     *
     */
    
    /* Mouse pressed */
    public void mousePressed(MouseEvent e){
        
        project proj_class=(project)project.oClass;
        
        if((e.getButton() == MouseEvent.BUTTON3)||((e.getModifiersEx()&MouseEvent.CTRL_DOWN_MASK)!=0)){
            //System.out.println("Mouse 3 Was Clicked with in stage area at "+e.getX()+" "+e.getY());
            
            PopUpMenu menu = new PopUpMenu(this,proj_class.selected_type);
            
            menu.show((Component)this, e.getX(), e.getY());
        } else {
            //System.out.println("Mouse Was Clicked with in stage area at "+e.getX()+" "+e.getY());
            //**************************************************************************
            if(proj_class.draw_mouse_state==0) {
                Vector allList = new Vector();
                
                int num_in_list=0;
                
                //THE NORMAL STATE
                //try to find out which object was selected
                
                int curx=proj_class.ScreenXtoWorld(e.getX());
                
                int cury=proj_class.ScreenYtoWorld(e.getY());
                
                int search_area=10;
                
                boolean found_item=false;
                
                Random ran_obj= new Random();
                
                //check if the house was selected
                
                Object_Drawer items=proj_class.houses.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);
                
                if(items.get_num_objects()>0) {
                    allList.add(items.get_object(0));
                    num_in_list++;
                }
                //check if the stage was selected
                if(proj_class.isStageVisible()){
                    items=proj_class.stages.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);
                    
                    if(items.get_num_objects()>0) {
                        allList.add(items.get_object(0));
                        num_in_list++;
                    }
                }
                //check if any bars were selected
                if(proj_class.isBarVisible()){
                    items=proj_class.bars.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);
                    
                    if(items.get_num_objects()>0) {
                        
                        int iter;
                        for(iter=0;iter<items.get_num_objects();iter++) {
                            allList.add(items.get_object(iter));
                            num_in_list++;
                        }
                    }
                }
                //check if any instruments were selected
                if(proj_class.isInstrumentVisible()){
                    items=proj_class.instruments.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);
                    
                    if(items.get_num_objects()>0) {
                        int iter;
                        for(iter=0;iter<items.get_num_objects();iter++) {
                            allList.add(items.get_object(iter));
                            num_in_list++;
                        }
                    }
                }
                
                //check if any set objects were selected
                if(proj_class.isSetItemVisible()){
                    items=proj_class.sets.get_objects_in_area(curx-search_area, cury-search_area, curx+search_area,cury+search_area);
                    
                    if(items.get_num_objects()>0) {
                        int iter;
                        for(iter=0;iter<items.get_num_objects();iter++) {
                            allList.add(items.get_object(iter));
                            num_in_list++;
                        }
                    }
                }
                
                if(num_in_list>0){
                    //select a random number from the created list of objects
                    
                    int ran_index=ran_obj.nextInt()%num_in_list;
                    
                    ran_index=Math.abs(ran_index);
                    
                    Object temp_obj=allList.get(ran_index);
                    
                    if(temp_obj instanceof house) {
                        proj_class.selected_type=0;
                    } else if(temp_obj instanceof stage) {
                        proj_class.selected_type=1;
                    } else if(temp_obj instanceof bar) {
                        proj_class.selected_type=2;
                    } else if(temp_obj instanceof instrument) {
                        proj_class.selected_type=3;
                    } else if(temp_obj instanceof setobject) {
                        proj_class.selected_type=4;
                    }
                    
                    proj_class.selected_index=((General_Object)temp_obj).index;
                    ItemBrowser.displayInfo(temp_obj);
                    repaint();
                }
            }
            //*********************************************************************************
            else if(proj_class.draw_mouse_state==1) {
                //creation of a bar
                //System.out.println("Mouse state was 1 adding "+temp_bar.num_nodes+" node");
                //drawing a bar
                if(temp_bar.num_nodes==0) {
                    //begin chaplin edit redited by JoshZ
                    temp_bar.worldx=proj_class.ScreenXtoWorld(e.getX());
                    temp_bar.worldy=proj_class.ScreenYtoWorld(e.getY());
                    
                    //end chaplin edit and reediting by JoshZ
                    
                    temp_bar.add_node(0,0);
                    
                } else {
                    //begin chaplin edit rededited by JOSHZ
                    temp_bar.add_node(proj_class.ScreenXtoWorld(e.getX())-temp_bar.worldx,proj_class.ScreenYtoWorld(e.getY())-temp_bar.worldy);
                    //end chaplin edit and reediting by JoshZ
                    if(temp_bar.num_nodes>=2) {
                        proj_class.addBar(temp_bar);
                        
                        proj_class.draw_mouse_state=0;
                        
                        temp_bar=null;
                    }
                }
                
                //**************************************************************
            } else if(proj_class.draw_mouse_state==2) {
                //adding an instrument to the selected bar
                //since the instrument has to be on the bar that was selected
                //use the x value of the mouse to find out the y value for the
                
                //edit here for instrument on bar problem.
                
                //placement on the bar
                
                int pot_x;
                
                int pot_y=-1;
                
                int barx1=proj_class.bars.get_object(temp_instrument.Associated_barID).x[0]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldx;
                
                int barx2=proj_class.bars.get_object(temp_instrument.Associated_barID).x[1]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldx;
                
                int bary1=proj_class.bars.get_object(temp_instrument.Associated_barID).y[0]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldy;
                
                int bary2=proj_class.bars.get_object(temp_instrument.Associated_barID).y[1]+proj_class.bars.get_object(temp_instrument.Associated_barID).worldy;
                
                //swap the points to make the math easier because assume point 1 is to the right
                
                //begin chaplin edit reediting by JoshZ
                pot_x = proj_class.ScreenXtoWorld(e.getX());
                //end chaplin edit reedited by JoshZ
                if(barx1>barx2) {
                    int temp_int;
                    
                    temp_int=barx1;
                    
                    barx1=barx2;
                    
                    barx2=temp_int;
                    
                    temp_int=bary1;
                    
                    bary1=bary2;
                    
                    bary2=temp_int;
                    
                }
                if((pot_x>barx1)&&(pot_x<barx2)) {
                    //the x is valid find the y value for it
                    
                    //fix here. what if slope undefined or 0? i.e. vertical/horizontal line
                    
                    if(barx1-barx2!=0) {
                        //to find slope y - y1 = m (x - x1)  or m=(y-y1)/(x-x1)
                        
                        //double slope = (bary1-bary2)/(barx1-barx2);
                        
                        //to find y with an x use equation y=m(x-x1)+y1
                        
                        //pot_y=(int)(slope*(double)(barx1-barx2)+bary2);
                        
                        double slope = (double)(bary1-bary2)/ (double)(barx1-barx2);
                        
                        pot_y=(int)(slope*((double)pot_x-(double)barx1)+(double)bary1);
                    }
                }
                
                if(pot_y>=0) {
                    //found a y so place it
                    
                    temp_instrument.worldx=pot_x;
                    
                    temp_instrument.worldy=pot_y;
                    
                    proj_class.addInstrument(temp_instrument);
                    
                    proj_class.draw_mouse_state=0;
                    
                    proj_class.selected_type=3;
                    
                    proj_class.selected_index=temp_instrument.index;
                    
                    temp_instrument=null;
                    
                }
                
                //*******************************************************************
            } else if(proj_class.draw_mouse_state==6) {
                //moving a bar
                bar selected_bar=(bar)proj_class.bars.get_object(proj_class.selected_index);
                
                
                
                
                
                //actually move the bar
                bar tb=new bar();
                tb.copyBar((bar)proj_class.bars.get_object(proj_class.selected_index));
                tb.worldx=proj_class.ScreenXtoWorld(e.getX());
                tb.worldy=proj_class.ScreenYtoWorld(e.getY());
                proj_class.SetBar(tb,proj_class.selected_index);
                
                proj_class.draw_mouse_state=0;
                ///***********************************************************
            } else if(proj_class.draw_mouse_state==8) {
                //adding a stage object
                
                if(temp_stage.num_nodes==0) {
                    temp_stage.worldx=proj_class.ScreenXtoWorld(e.getX());
                    temp_stage.worldy=proj_class.ScreenYtoWorld(e.getY());
                    
                    temp_stage.add_node(0,0);
                    
                } else {
                    
                    temp_stage.add_node(proj_class.ScreenXtoWorld(e.getX())-temp_stage.worldx,proj_class.ScreenYtoWorld(e.getY())-temp_stage.worldy);
                    
                    if(temp_stage.num_nodes>=15) {
                        proj_class.addStage(temp_stage);
                        
                        proj_class.draw_mouse_state=0;
                        
                        temp_stage=null;
                        
                    }
                }
                //*****************************************************************
            } else if(proj_class.draw_mouse_state==9) {
                //adding a stage object
                
                if(temp_set.num_nodes==0) {
                    
                    temp_set.worldx=proj_class.ScreenXtoWorld(e.getX());
                    temp_set.worldy=proj_class.ScreenYtoWorld(e.getY());
                    
                    temp_set.add_node(0,0);
                    
                } else {
                    temp_set.add_node(proj_class.ScreenXtoWorld(e.getX())-temp_set.worldx,proj_class.ScreenYtoWorld(e.getY())-temp_set.worldy);
                    
                    
                    if(temp_set.num_nodes>=15) {
                        proj_class.addSet(temp_set);
                        
                        proj_class.draw_mouse_state=0;
                        
                        temp_set=null;
                    }
                    
                }
                
                //***********************************************************
            } else if(proj_class.draw_mouse_state==4) {
                //edit nodes of house
                //change this??
                
                if(selected_node<0) {
                    //find a node to select
                    
                    selected_node=temp_house.closest_node(proj_class.ScreenXtoWorld(e.getX()),proj_class.ScreenYtoWorld(e.getY()));
                } else {
                    //set the node to the new position
                    
                    temp_house.move_node(selected_node, proj_class.ScreenXtoWorld(e.getX()), proj_class.ScreenYtoWorld(e.getY()));
                    
                    selected_node=-1;
                }
                
                //*********************************************************
            } else if(proj_class.draw_mouse_state==5) {
                //edit node of stage
                //change this??
                
                if(selected_node<0) {
                    //find a node to select
                    
                    selected_node=temp_stage.closest_node(proj_class.ScreenXtoWorld(e.getX()),proj_class.ScreenYtoWorld(e.getY()));
                    
                } else {
                    //set the node to the new position
                    
                    temp_stage.move_node(selected_node, proj_class.ScreenXtoWorld(e.getX()), proj_class.ScreenYtoWorld(e.getY()));
                    
                    selected_node=-1;
                }
                
                //**************************************************************
            } else if(proj_class.draw_mouse_state==3) {
                //edit nodes of bar
                //change this??
                
                if(selected_node<0) {
                    
                    //find a node to select
                    selected_node=temp_bar.closest_node(proj_class.ScreenXtoWorld(e.getX()),proj_class.ScreenYtoWorld(e.getY()));
                    
                } else {
                    //set the node to the new position
                    
                    temp_bar.move_node(selected_node, proj_class.ScreenXtoWorld(e.getX()), proj_class.ScreenYtoWorld(e.getY()));
                    
                    selected_node=-1;
                }
                //proj_class.draw_mouse_state=0;
                
                //**************************************************************
            } else if(proj_class.draw_mouse_state==10) {
                //edit nodes of set object
                //change this??
                if(selected_node<0) {
                    //find a node to select
                    
                    selected_node=temp_set.closest_node(proj_class.ScreenXtoWorld(e.getX()),proj_class.ScreenYtoWorld(e.getY()));
                } else {
                    //set the node to the new position
                    
                    temp_set.move_node(selected_node, proj_class.ScreenXtoWorld(e.getX()), proj_class.ScreenYtoWorld(e.getY()));
                    
                    selected_node=-1;
                }
            }
            //*******************************************************************
            else if(proj_class.draw_mouse_state==7){
                //move an instrument
                
                
                
            }
            ///**************************************************************
            else if(proj_class.draw_mouse_state==11){
                //move the stage
                stage selected_stage=(stage)proj_class.stages.get_object(0);
                
                int old_stage_x;
                old_stage_x=selected_stage.worldx;
                
                int old_stage_y;
                old_stage_y=selected_stage.worldy;
                
                
                int xdiff;
                int ydiff;
                
                xdiff = old_stage_x-proj_class.ScreenXtoWorld(e.getX());
                ydiff = old_stage_y-proj_class.ScreenYtoWorld(e.getY());
                
                //actually move the stage
                //proj_class.stages.get_object(0).worldx=proj_class.ScreenXtoWorld(e.getX());
                //proj_class.stages.get_object(0).worldy=proj_class.ScreenYtoWorld(e.getY());
                stage ts=new stage();
                ts.copyStage((stage)proj_class.stages.get_object(0));
                ts.worldx=proj_class.ScreenXtoWorld(e.getX());
                ts.worldy=proj_class.ScreenYtoWorld(e.getY());
                proj_class.SetStage(ts,0);
                proj_class.draw_mouse_state=0;
                
                
            }
            //****************************************************************
            else if(proj_class.draw_mouse_state==12){
                //moving the house
                house selected_house=(house)proj_class.houses.get_object(0);
                
                int old_x;
                old_x=selected_house.worldx;
                
                int old_y;
                old_y=selected_house.worldy;
                
                
                int xdiff;
                int ydiff;
                
                xdiff = old_x-proj_class.ScreenXtoWorld(e.getX());
                ydiff = old_y-proj_class.ScreenYtoWorld(e.getY());
                
                //actually move the house
                //proj_class.houses.get_object(0).worldx=proj_class.ScreenXtoWorld(e.getX());
                //proj_class.houses.get_object(0).worldy=proj_class.ScreenYtoWorld(e.getY());
                house th=new house();
                th.copyHouse((house)proj_class.houses.get_object(0));
                th.worldx=proj_class.ScreenXtoWorld(e.getX());
                th.worldy=proj_class.ScreenYtoWorld(e.getY());
                proj_class.SetHouse(th,0);
                
                proj_class.draw_mouse_state=0;
                
                
            }
            //*****************************************************************
            else if(proj_class.draw_mouse_state==13){
                //moving a set
                setobject selected_set=(setobject)proj_class.sets.get_object(proj_class.selected_index);
                
                int old_x;
                old_x=selected_set.worldx;
                
                int old_y;
                old_y=selected_set.worldy;
                
                
                
                
                
                int xdiff;
                int ydiff;
                
                xdiff = old_x-proj_class.ScreenXtoWorld(e.getX());
                ydiff = old_y-proj_class.ScreenYtoWorld(e.getY());
                
                //actually move the house
                //proj_class.sets.get_object(proj_class.selected_index).worldx=proj_class.ScreenXtoWorld(e.getX());
                //proj_class.sets.get_object(proj_class.selected_index).worldy=proj_class.ScreenYtoWorld(e.getY());
                setobject ts=new setobject();
                ts.copySetObject((setobject)proj_class.sets.get_object(proj_class.selected_index));
                ts.worldx=proj_class.ScreenXtoWorld(e.getX());
                ts.worldy=proj_class.ScreenYtoWorld(e.getY());
                proj_class.SetSetObject(ts,proj_class.selected_index);
                proj_class.draw_mouse_state=0;
                
                
            }
            //********************************************************************
            else if(proj_class.draw_mouse_state==14){
                //aim a light
                
                //first click set that as the point
                //to reaim the light redo this function
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimx=proj_class.ScreenXtoWorld(e.getX());
                ((instrument)proj_class.instruments.get_object(proj_class.selected_index)).aimy=proj_class.ScreenYtoWorld(e.getY());
                proj_class.draw_mouse_state=0;
            }
            //******************************************
            repaint();
        }
    }
    
    
    public void adjustmentValueChanged(AdjustmentEvent e) {
        project proj_class=(project)project.oClass;
        proj_class.scroll_x=horiz.getValue();
        proj_class.scroll_y=vert.getValue();
        repaint();
    }
    
    
    
}



