package theatre;











import javax.swing.*;





import java.awt.*;





import java.awt.event.*;





import theatre.*;





import Data_Storage.*;











public class ExplorerBrowserPanel extends Window


        


{


    


    public static int iWidth = 0;


    


    public static int iHeight = 0;


    


    public ExplorerBrowserPanel()


    


    {


        


        getContentPane().setLayout(null);


        


        


        


        //        JPanel glass=new JPanel();


        


/*       glass.addMouseListener(new MouseAdapter()


 


        {


 


                public void mousePressed(MouseEvent me)


 


                {


 


                }


 


        } );*/


        


        //        glass.setOpaque(false);


        


        this.setTitle("Item Explorer ");


        


        this.setBackground(Color.WHITE);


        


        


        


        ExplorerBrowser ib = new ExplorerBrowser();


        


        ib.loadStructureTopPoint();


        


        getContentPane().add(ib);


        


        


        


        // set the size based on the system resolution


        


        iWidth = BasicWindow.iScreenWidth/5;


        


        iHeight = BasicWindow.iScreenHeight;


        


        this.setBounds(0, 0, iWidth, iHeight);


        


        


        


        this.setVisible(true);


        


    }


    


}





