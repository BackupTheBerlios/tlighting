/*
 * CueEditor.java
 *
 * Created on May 6, 2005, 2:39 AM
 */

package theatre;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import drawing_prog.*;
import Data_Storage.*;
import PhotonRenderer.*;

/**
 *
 * @author root
 */
public class CueEditor extends JDialog implements MouseListener, ActionListener, AdjustmentListener {
    
    //fills entire screen
    //top 3/4 is a rendering in the x-z plane can change to look down +x -x +y or -y axises
    //bottom 1/4 is a window that is scrollable down to access sliders for each light present
    protected int iScreenHeight =BasicWindow.iScreenWidth-50;
    protected int iScreenWidth = BasicWindow.iScreenHeight-100;
    int quarterheight=(int)(iScreenHeight/4);
    Cue_Drawing display;
    JPanel Controls;
    project proj_class=(project)project.oClass;
    
    JScrollBar[] vert;
    JButton renderButton;
    boolean dataLoaded;
    
    /** Creates a new instance of CueEditor */
    public CueEditor() {
        super(BasicWindow.curWindow,"Cue Editor", true);
        setBounds(0,0,640,680);
        dataLoaded=false;
        init();
        //       loadData();
        this.setVisible(true);
    }
    
    //to draw 3/4 create matrixes for each light that contains what the color would be at the full
    
    //while rendering just run through each pixel adding up each pixel
    
    
    public void init() {
        
        
        display = new Cue_Drawing();
        display.setBounds(0,0,640,480);
        display.setBackground(Color.white);
        display.setDoubleBuffered(true);
        display.setIgnoreRepaint(true);
        //Controls=new JPanel();
        //Controls.setBounds(0,280,640,200);
        
        
        addControls();
        
        getContentPane().add(display);
        //getContentPane().add(Controls);
        
        validate();
    }
    
    
    
    public void addControls(){
        //add render button
        
        renderButton=new JButton("Render");
        renderButton.setBounds(10,480,100,20);
        renderButton.setActionCommand("render");
        renderButton.addActionListener(this);
        getContentPane().add(renderButton);
        renderButton.validate();
        //add a slider for each light present
        int i;
        int lastx=20;
        int lasty=500;
        vert=new JScrollBar[proj_class.instruments.get_num_objects()];
        
        
        for(i=0;i<proj_class.instruments.get_num_objects();i++){
            lastx+=20;
            
            vert[i] = new JScrollBar();
            vert[i].setOrientation(Adjustable.VERTICAL);
            vert[i].addAdjustmentListener(this);
            getContentPane().add(vert[i]);
            vert[i].setBounds(lastx, lasty ,18,50);
            vert[i].setUnitIncrement(1);
            vert[i].setBlockIncrement(10);  //sets increment based on click above/below scroll knob. Check this.
            vert[i].setMaximum(100); //static was 100
            vert[i].setValue(100);
            vert[i].validate();
            
            if(lastx>600){
                lastx=20;
                lasty+=60;
            }
        }
        //Controls.validate();
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getActionCommand()=="render"){
            display.render();
        }
    }
    
    public void mouseClicked(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    
    public void mousePressed(MouseEvent e){
        
    }
    
    public void adjustmentValueChanged(AdjustmentEvent e) {
        
        int i;
       
        for(i=0;i<proj_class.instruments.get_num_objects();i++){
            if(vert!=null){
                if(vert[i]!=null){
                    ((instrument)proj_class.instruments.get_object(i)).setLevel(vert[i].getValue());
                    
                }
            }
            
        }
        //repaint();
    }
    
}


class Cue_Drawing extends JPanel implements MouseListener, AdjustmentListener{
    
    
    public Bitmap[] screens;
    public int numScreens;
    public int[] levels;
    
    Vector lightCells;
    
    boolean firstTimeAround;
    
    int maxX;
    int maxY;
    int minX;
    int minY;
    int scale;
    
    boolean newRender;
    
    public Bitmap viewer;
    
    Image aImage;
    
    project proj_class;
    Cue_Drawing(){
        firstTimeAround=true;
        proj_class=(project)project.oClass;
        setBackground(Color.white);
        setLayout(null);
        loadData();
        newRender=true;
        
        
    }
    public void loadData(){
        //    if(!dataLoaded){
        
        scale=1;
        maxX=0;
        maxY=0;
        minX=640;
        minY=480;
        numScreens=proj_class.instruments.get_num_objects();
        
        screens=new Bitmap[numScreens];
        levels=new int[numScreens];
        
        int i;
        for(i=0;i<(proj_class.instruments.get_num_objects());i++){
            ((instrument)proj_class.instruments.get_object(i)).setLevel(100);
        }
        
        
        for(i=0;i<numScreens;i++){
            screens[i]=new Bitmap(640,480,false);
            int x,y;
            for(x=0;x<640;x++){
                for(y=0;y<480;y++){
                    screens[i].setPixel(x,y,Color3.BLACK);
                    
                }
            }
            
            levels[i]=0;
            int j;
            for(j=0;j<proj_class.photonmap.getStoredPhotons();j++){
                Photon pho =proj_class.photonmap.getPhoton(j);
                if(pho!=null){
                    if(pho.lightSource==i){
                        
                        screens[i].setPixel((int)pho.x,480-(int)pho.z,new Color3(pho.R,pho.G,pho.B));
                        if((int)pho.x>maxX){
                            maxX=(int)pho.x;
                        }
                        if((480-(int)pho.z)>maxY){
                            maxY=(480-(int)pho.z);
                        }
                        if((int)pho.x<minX){
                            minX=(int)pho.x;
                        }
                        if((480-(int)pho.z)<minY){
                            minY=(480-(int)pho.z);
                        }
                    }
                }
            }
            
            screens[i].save("lightscreen"+i+".png");
        }
        viewer=new Bitmap(640,480,false);
        int j;
        for(i=0;i<viewer.getWidth();i++){
            for(j=0;j<viewer.getHeight();j++){
                viewer.setPixel(i,j,Color3.BLACK);
            }
        }
        //            dataLoaded=true;
        //      }
        int tempx=0;
        int tempy=0;
        if(maxX!=0){
            tempx=640/maxX;
        }
        
        if(maxY!=0){
            tempy=480/maxY;
        }
        
        if(tempx>0){
            if(tempx>tempy){
                scale=tempx;
            }
        }
        if(tempy>0){
            if(tempy>tempx){
                scale=tempy;
            }
        }
        
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int i,j,k;
        System.out.println("Starting painting");
        //draw sets
        
        if(firstTimeAround){
            drawSet(g2);
            render();
            viewer.save("final_product_full.png");
            firstTimeAround=false;
        }
        //render();
       /* for(i=0;i<viewer.getWidth();i++){
            for(j=0;j<viewer.getHeight();j++){
                viewer.setPixel(i,j,Color3.BLACK);
            }
        }
        */
        
        g2.setBackground(Color.BLACK);
        
//        if(newRender){
            for(i=minX;i<maxX;i++){
                //System.out.println("line "+i);
                for(j=minY;j<maxY;j++){
                    
                    g2.setColor(new Color((float)viewer.getPixel(i,j).getR(),(float)viewer.getPixel(i,j).getG(),(float)viewer.getPixel(i,j).getB()));
                    //g2.drawLine(i,j,1,1);
                    Ellipse2D.Double node_circ= new Ellipse2D.Double(i,j,1,1);
                    g2.fill(node_circ);
                    
                    g2.setColor(Color.BLACK);
                    
                    //if(j*scale<viewer.getHeight()){
                    //       j=0;//viewer.getHeight();
                    // }
                    
                }
                // if(i*scale<viewer.getWidth()){
                //    i=0;//viewer.getWidth();
                //  }
            }
            
            //  g2.drawImage(viewer.1)
            newRender=false;
       // }
        System.out.println("end painting");
    }
    
    
    void render(){
        Color3 endColor=Color3.BLACK;
        System.out.println("RE calculating the image");
        int i,j,k;
        for(i=0;i<viewer.getWidth();i++){
            for(j=0;j<viewer.getHeight();j++){
                endColor=Color3.BLACK;
                for(k=0;k<numScreens;k++){
                    float tempr=((float)screens[k].getPixel(i,j).getR())*((float)((instrument)proj_class.instruments.get_object(k)).getLevel()/(float)100.0);
                    float tempg=((float)screens[k].getPixel(i,j).getG())*((float)((instrument)proj_class.instruments.get_object(k)).getLevel()/(float)100.0);
                    float tempb=((float)screens[k].getPixel(i,j).getB())*((float)((instrument)proj_class.instruments.get_object(k)).getLevel()/(float)100.0);
                    if((tempr!=0)||(tempg!=0)||(tempb!=0)){
                        
                        
                        float newr=tempr+(float)endColor.getR();
                        float newg=tempg+(float)endColor.getG();
                        float newb=tempb+(float)endColor.getB();
                        if(newr>1){
                            newr=1;
                        }
                        if(newg>1){
                            newg=1;
                        }
                        if(newb>1){
                            newb=1;
                        }
                        endColor=new Color3(newr,newg,newb);
                        
                    }
                }
                viewer.setPixel(i,j,endColor.toRGB());
            }
        }
        newRender=true;
        repaint();
    }
    
    void drawSet(Graphics2D g2){
        
    }
    
    
    /* Blank mouse listener methods  */
    public void mouseClicked(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    
    /* Mouse pressed */
    public void mousePressed(MouseEvent e){
        
    }
    
    
    public void adjustmentValueChanged(AdjustmentEvent e) {
        
    }
    
    public Color3 getColorAt(int a,int b){
        Color3 endColor=Color3.BLACK;
        int i;
        for(i=0;i<proj_class.photonmap.getStoredPhotons();i++){
            Photon pho=proj_class.photonmap.getPhoton(i);
            if(pho!=null){
                if(((int)pho.x==a)&&((int)pho.z==b)){
                    
                    float tempr=((float)pho.R)*(((instrument)proj_class.instruments.get_object(pho.lightSource)).getLevel()/100);
                    float tempg=((float)pho.G)*(((instrument)proj_class.instruments.get_object(pho.lightSource)).getLevel()/100);
                    float tempb=((float)pho.B)*(((instrument)proj_class.instruments.get_object(pho.lightSource)).getLevel()/100);
                    
                    
                    if(tempr!=0){
                        int fake=0;
                        fake++;
                    }
                    
                    endColor=endColor.add(new Color3(tempr,tempg,tempb));
                    float newr=(float)endColor.getR();
                    float newg=(float)endColor.getG();
                    float newb=(float)endColor.getB();
                    if(newr>1){
                        newr=1;
                    }
                    if(newg>1){
                        newg=1;
                    }
                    if(newb>1){
                        newb=1;
                    }
                    endColor=new Color3(newr,newg,newb);
                    
                }
                
            }
            
        }
        
        return endColor;
    }
    
    
    
}


class cell {
    int x;
    int y;
    Color3 c;
    
    public cell(){
        x=0;
        y=0;
        c=Color3.BLACK;
    }
    
    public cell(int ax, int ay, Color3 ac){
        x=ax;
        y=ay;
        c=ac;
    }
    
    public int getX(){
        return x;
        
    }
    
    public int getY(){
        return y;
    }
    
    public Color3 getColor(){
        return c;
    }
    
    public void setColor(float r, float g, float b){
        c=new Color3(r,g,b);
        
    }
    
    public void setX(int ax){
        x=ax;
    }
    
    public void setY(int ay){
        y=ay;
    }
    
    
    
}