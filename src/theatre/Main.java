package theatre;



public class Main 

{

    public Main()

    {



    }

    public static void main(String[] args)

    {

      javax.swing.SwingUtilities.invokeLater(new Runnable() 

        { 

            public void run() 

            {

                    BasicWindow bw = new BasicWindow(); 

                    bw.createAndShowGUI(); 

            } 

        }); 

    }

}

