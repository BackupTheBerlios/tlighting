/*
 * ControlPanel.java
 *
 * Created on January 25, 2005, 11:57 AM
 */

package theatre;
/**
 *
 *  Code Generated: 1/11/2005
 *  Author:         Greg Silverstein
 *  This is the layout for the control panel form
 */
public class ControlPanel extends Window 
{
    /** Creates new form ControlPanel */
	public ControlPanel() 
    {
		initComponents();
		this.setTitle("Control Panel");
		this.setBounds(BasicWindow.iScreenWidth-(BasicWindow.iScreenWidth/5), BasicWindow.iScreenHeight/2-20, (BasicWindow.iScreenWidth/5-5), BasicWindow.iScreenHeight/2-20);
        this.setVisible(true);
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        jRadioButton2.setText("Stage Set View");
        getContentPane().add(jRadioButton2);
        jRadioButton2.setBounds(0, 60, 394, 30);

        jRadioButton3.setText("Set View");
        getContentPane().add(jRadioButton3);
        jRadioButton3.setBounds(0, 20, 394, 30);

        jRadioButton4.setText("Bar View");
        getContentPane().add(jRadioButton4);
        jRadioButton4.setBounds(0, 40, 394, 30);

        jRadioButton5.setText("Stage View");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        getContentPane().add(jRadioButton5);
        jRadioButton5.setBounds(0, 0, 394, 30);

        jRadioButton6.setText("Entire House View");
        getContentPane().add(jRadioButton6);
        jRadioButton6.setBounds(0, 80, 394, 30);

        jButton1.setText("View Panel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1);
        jButton1.setBounds(20, 110, 130, 25);

        jButton2.setText("View Wiring Plot");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2);
        jButton2.setBounds(20, 140, 130, 25);

        jButton3.setText("View Inventory");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton3);
        jButton3.setBounds(20, 170, 130, 25);

        jButton4.setText("Export Tools");
        getContentPane().add(jButton4);
        jButton4.setBounds(20, 200, 130, 25);

        pack();
    }//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton5ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    // End of variables declaration//GEN-END:variables
    
}
