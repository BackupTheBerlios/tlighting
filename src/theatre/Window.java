/*
 * Created on Jan 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package theatre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Window extends javax.swing.JInternalFrame
{
    protected boolean wait;
    
    public Window()
    {
        super();
        JPanel glass=new JPanel(){
                public boolean isManagingFocus() { return true; }};

        glass.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me){
                        System.out.println("Mouse Was Clicked and glass pane got it");
                        if (!wait)
                        {
                                Window.this.getGlassPane().setVisible(false);
                                Window.this.requestFocus();
                                try{
                                        Window.this.setSelected(true);
                                        Window.this.toFront();
                                }catch (Exception e){}
                        }
                }
                } );
        glass.addKeyListener(new KeyAdapter() {	public void keyPressed(KeyEvent e) {
                e.consume();}});
        glass.setOpaque(false);
        
		
        this.setGlassPane(glass);
        try{
        	//this.setSelected(true);
        	//this.setEnabled(true);
        	//this.setFocusable(false);
        }catch (Exception e)
		{
        	System.out.println("Unable to set selected: "+e.getMessage());
		}
        this.setBackground(Color.white);
        this.setClosable(false);
        this.setResizable(true);		
        this.setDefaultCloseOperation(Window.DO_NOTHING_ON_CLOSE);
    }
    public void setWaiting(boolean w)
    {
        wait= w;

        Runnable r = new Runnable(){
                public void run()
                {
                        Cursor cursor = Cursor.getPredefinedCursor(
                                wait ? Cursor.WAIT_CURSOR : Cursor.DEFAULT_CURSOR);

                        Window.this.setCursor(cursor);
                        getGlassPane().setCursor(cursor);
                        getGlassPane().setVisible(wait);
                        if (wait)
                                getGlassPane().requestFocus();
                        else 
                                Window.this.requestFocus();				
                }
        };
        if (SwingUtilities.isEventDispatchThread()) {
                r.run();
        } else {		    
                SwingUtilities.invokeLater(r);
                Thread.yield();
        }
    }
}