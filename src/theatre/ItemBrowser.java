/*
 * ItemBrowser.java
 *
 * Created on January 25, 2005, 12:32 PM
 */

package theatre;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import Data_Storage.*;

/**
 *
 * @author  Administrator
 *edited by Joshua Zawislak 4/20/05
 */
public class ItemBrowser extends Window {
    
    /** Creates new form ItemBrowser */
    public ItemBrowser() {
        initComponents();
        this.setTitle("Item Browser");
        this.setBounds(BasicWindow.iScreenWidth-(BasicWindow.iScreenWidth/5), 0, BasicWindow.iScreenWidth/5-5, BasicWindow.iScreenHeight/2-20);
        this.setVisible(true);
    }
    public static void displayInfo(Object o) {
        if (o instanceof bar) {
            bar b = (bar)o;
            String s = "Bar Name:"+b.getName()+"\n";
            s +="Bar ID: "+b.getID()+"\n";
            s += "Num Nodes: "+b.num_nodes + "\n";
            s += "X: "+b.worldx+"\n";
            s += "Y: "+b.worldy+"\n";
            s += "Height:"+b.getHeight()+"\n";
            s += "Number of Dimmers: " + b.getNum_dimmers()+"\n";
            s+= "Dimmers:"+b.getDimmerString()+"\n";
            jText.setText(s);
        }else if (o instanceof instrument) {
            instrument i = (instrument)o;
            String s = "Instrument ID: "+i.getInventoryID()+"\n";
            s += "Description: "+i.getDescription() + "\n";
            s += "Type:"+i.getType()+"\n";
            s += "X: "+i.worldx+"\n";
            s += "Y: "+i.worldy+"\n";
            s += "Height:"+i.getZ()+"\n";
            s += "Bar: " + i.getBarID()+"\n";
            s += "Dimmer: " + i.getDimmerId()+"\n";
            s += "Color R"+i.R+" G:"+i.G+" B:"+i.B+"\n";
            s += "Level:"+i.getLevel()+"\n";
            s += "Misc: " + i.getMisc()+"\n";
            jText.setText(s);
        }else if (o instanceof setobject) {
            setobject b = (setobject)o;
            String s = "Set ID: "+b.getname()+"\n";
            s += "Description: "+b.getdescription() + "\n";
            s += "x: "+b.worldx+"\n";
            s += "y: "+b.worldy+"\n";
            jText.setText(s);
        }else if (o instanceof stage) {
            stage b = (stage)o;
            String s = "Stage Name: "+b.getdescription()+"\n";
            s += "width: "+b.getmaxx()+"\n";
            s += "length: "+b.getmaxy()+"\n";
            s += "height: " + b.getheight()+"\n";
            s += "x: " + b.worldx+"\n";
            s += "y: " + b.worldy+"\n";
            
            jText.setText(s);
        }else if (o instanceof house) {
            house b = (house)o;
            String s = "House ID: "+b.getid()+"\n";
            s += "Description: " + b.getdescription()+"\n";
            s += "X: "+b.worldx+"\n";
            s += "Y: "+b.worldy+"\n";
            s += "Width: " + b.getmaxx()+"\n";
            s += "Length: " + b.getmaxy()+"\n";
            s += "Height: " + b.getheight()+"\n";
            
            jText.setText(s);
        }else if (o instanceof inventory_item) {
            inventory_item b = (inventory_item)o;
            String s = "Inventory ID: "+b.getInvId()+"\n";
            s += "Description: " + b.getDesc()+"\n";
            s += "Type: "+b.getType()+"\n";
            
            jText.setText(s);
        }
        //jTable1.add
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        //jTable1 = new javax.swing.JTable();
    	jText = new JTextArea();

        getContentPane().setLayout(null);

        /*jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));*/
        getContentPane().add(jText);
        jText.setBounds(0, 0, 230, 210);

        pack();
    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    //public static javax.swing.JTable jTable1;
    public static JTextArea jText;
    // End of variables declaration//GEN-END:variables
    
}
