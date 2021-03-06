/*
 * xml_Reader.java
 *
 * Created on March 2, 2005, 10:03 PM
 *This class will contain all the functions to load the xml data into a project.
 */

package xml_Interface;

/**
 *
 * @author Greg Silverstein
 *edited by josh Z 3/21/05 nad on 4/20/05
 */

import net.n3.nanoxml.*;
import Data_Storage.*;
import java.io.*;
public class xml_Reader {
    
    public project proj_class;
    //Parser Items
    IXMLParser parser;
    IXMLReader reader;
    IXMLElement xml;
    //Final Holders For Locations
    public String s_bPath, s_pPath;
    /** Creates a new instance of xml_Reader */
    public xml_Reader() {
        proj_class=(project)project.oClass;
    }
    
    //main run to load
    //in strings are the path of the project folder as well as the bar file.
    public boolean load_project(String inProjectPath,String inBarPath) {
        s_bPath = inBarPath;
        s_pPath = inProjectPath;
        IXMLParser parser=null;
        IXMLReader reader=null;
        IXMLElement xml=null;
        File inputFile;
        //check what type of file was opened
        try{
            inputFile= new File(inBarPath);
            if(inputFile.exists()){
              //I know the file exists
          
            parser = XMLParserFactory.createDefaultXMLParser();
           
            inBarPath="file:///"+inBarPath;
            reader = StdXMLReader.fileReader(inputFile, inBarPath);
            
            parser.setReader(reader);
            xml = (IXMLElement) parser.parse();
                
            }
            
            
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        if(xml==null){
            return false;
        }
        boolean result=false;
        //if it was a house object
        if(xml.getName().equalsIgnoreCase("project")){
            result=get_project(xml,inProjectPath);
        }else if(xml.getName().equalsIgnoreCase("house_info")){
            result=get_house(xml);
        }else if(xml.getName().equalsIgnoreCase("bar_list")){
            //if it was a bar list
            result=get_bars(xml);
        }else if(xml.getName().equalsIgnoreCase("stage_info")){
            //if it was a stage object
            get_stage(xml);
        }else if(xml.getName().equalsIgnoreCase("set_list")){
            //if it was a set list
            result=get_set(xml);
        }else if(xml.getName().equalsIgnoreCase("inventory_list")){
            //if it was an inventory list
            result=get_inventory(xml);
        }else if(xml.getName().equalsIgnoreCase("type_list")){
            //if it was a type list
            result=get_knowntypes(xml);
        }else if(xml.getName().equalsIgnoreCase("instrument_list")){
            //if it was an instrument list
            result=get_instrument(xml);
        }else if(xml.getName().equalsIgnoreCase("photon_info")){
            //if it was an instrument list
            result=get_photons(xml);
        }
        return result;
    }
    
    //individual load functions
    private boolean get_house(IXMLElement xml) {
        //create a temp house object
        IXMLElement h_xml=xml.getFirstChildNamed("house");
        house temp_h=new house();
        
        //attributes for house name, overallx, overally, overallz, name, description, id, proj_id
        String name;
        String desc;
        String id;
        int overallx=500;
        int overally=500;
        int overallz=300;
        String proj_id;
        
        name=h_xml.getAttribute("name","DEFAULT");
        desc=h_xml.getAttribute("description","DEFAULT");
        id=h_xml.getAttribute("id","DEFAULT");
        proj_id=h_xml.getAttribute("project_id","DEFAULT");
        try{
            overallx=Integer.parseInt(h_xml.getAttribute("overallx","100"));
            overally=Integer.parseInt(h_xml.getAttribute("overally","100"));
            overallz=Integer.parseInt(h_xml.getAttribute("overallz","100"));
        }catch(Exception e){
            
            System.out.println("there was an error parseing the size of a house from file");
        }
        temp_h.setname(name);
        temp_h.setdescription(desc);
        temp_h.setid(id);
        
        temp_h.worldx=overallx;
        temp_h.worldy=overally;
        temp_h.setheight(overallz);
        
        //get the nodes for the house
        int num=h_xml.getChildrenCount();
        int i;
        for(i=0;i<num;i++){
            IXMLElement n_xml=null;
            
            try{
                n_xml=h_xml.getChildAtIndex(i);
            }catch(Exception e){
                System.out.println("Error opening nodes for house");
            }
            if(n_xml!=null){
                int x=Integer.parseInt(n_xml.getAttribute("x","10"));
                int y=Integer.parseInt(n_xml.getAttribute("y","10"));
                temp_h.add_node(x,y);
            }
        }
        
        
        //temp_h.add_node(
        
        // see if all other objects fit in it
        
        //if everything passed then add the set the house object to active
        if(proj_class.houses.get_num_objects()>0){
            proj_class.houses.remove_object(0);
        }
        proj_class.houses.add_object(temp_h);
        proj_class.houseadded=true;
        proj_class.draw_mouse_state=0;
        proj_class.project_open=true;
        proj_class.forceRepaint();
        
        return true;
        //if it did not pass do not add the house object
        //return false;
        
        
    }
    private boolean get_bars(IXMLElement xml) {
        
        //create a temp bar object list
        //for each bar in the list
        int num=xml.getChildrenCount();
        int i;
        for(i=0;i<num;i++){
            
            IXMLElement b_xml=xml.getChildAtIndex(i);
            bar temp_b=new bar();
            
            //and add all the bars info to it
            //attributes for bar id, description, x1, x2, y1, y2, z1, z2, house_id
            String bar_name;
            int x1=0,x2=0,y1=0,y2=0,z1=0,z2=0,bar_id=0;
            String house_id;
            bar_name=b_xml.getAttribute("bar_id","DEFAULT");
            house_id=b_xml.getAttribute("house_id","DEFAULT");
            try{
                x1=Integer.parseInt(b_xml.getAttribute("x1","100"));
                x2=Integer.parseInt(b_xml.getAttribute("x2","100"));
                y1=Integer.parseInt(b_xml.getAttribute("y1","100"));
                y2=Integer.parseInt(b_xml.getAttribute("y2","100"));
                z1=Integer.parseInt(b_xml.getAttribute("z1","100"));
                z2=Integer.parseInt(b_xml.getAttribute("z2","100"));
                bar_id=Integer.parseInt(b_xml.getAttribute("id","0"));
            }catch(Exception e){
                
                System.out.println("there was an error parseing the size of the bar from file");
            }
            
            //make the poitns in the correct format
            int wx=x1;
            int wy=y1;
            x1=0;
            y1=0;
            x2=x2-wx;
            y2=y2-wy;
            
            temp_b.worldx=wx;
            temp_b.worldy=wy;
            temp_b.add_node(x1, y1);
            temp_b.add_node(x2, y2);
            temp_b.setID(bar_id);
            temp_b.setName(bar_name);
            temp_b.setZ(0, z1);
            temp_b.setZ(1, z2);
           
            //get all the dimmers on the bar
            int i2;
            int num2=b_xml.getChildrenCount();
            temp_b.setNumDim(num2);
            for(i2=0;i2<num2;i2++){
                IXMLElement n_xml=b_xml.getChildAtIndex(i2);
                int dimmer;
                dimmer=Integer.parseInt(n_xml.getAttribute("number","0"));
                temp_b.setDimmers(i2,dimmer);
            }
            
            //check if the bar is valid
            
            //now add the bar to the project
            
            proj_class.addBar(temp_b);
        }
        return true;
    }
    private boolean get_set(IXMLElement xml) {
        
        //create a temporary set list
        int num=xml.getChildrenCount();
        
        //check if all the items in the list are valid
        int i;
        for(i=0;i<num;i++){
            
            IXMLElement s_xml=xml.getChildAtIndex(i);
            setobject temp_s=new setobject();
            //attributes for sets name, description, id, house_id, x, y, z,
            
            temp_s.setname(s_xml.getAttribute("name","default"));
            temp_s.setdescription(s_xml.getAttribute("description","default"));
            //s_xml.getAttribute("id","0");
            //s_xml.getAttribute("house_id",((house)proj_class.houses.get_object(0)).getid());
            temp_s.worldx=Integer.parseInt(s_xml.getAttribute("x","0"));
            temp_s.worldy=Integer.parseInt(s_xml.getAttribute("y","0"));
            temp_s.setsize(Integer.parseInt(s_xml.getAttribute("z","0")));
            
            //get each node for the set object
            int i2;
            int num2=s_xml.getChildrenCount();
            for(i2=0;i2<num2;i2++){
                IXMLElement n_xml=s_xml.getChildAtIndex(i2);
                int x,y,z;
                x=Integer.parseInt(n_xml.getAttribute("x","0"));
                y=Integer.parseInt(n_xml.getAttribute("y","0"));
                z=Integer.parseInt(n_xml.getAttribute("z","0"));
                
                temp_s.add_node(x,y);
            }
            
            //if they are valid add them to the set list
            proj_class.addSet(temp_s);
            
            //if they are not valid do not add them
        }
        return true;
    }
    private boolean get_stage(IXMLElement xml) {
        int num=xml.getChildrenCount();
        if(num<1){return false;}
        //create a temporary stage object
        IXMLElement s_xml=xml.getChildAtIndex(0);
        stage temp_s=new stage();
        //attributes for stage name, id, house_id, x, y, z
        temp_s.worldx=Integer.parseInt(s_xml.getAttribute("x","0"));
        temp_s.worldy=Integer.parseInt(s_xml.getAttribute("y","0"));
        temp_s.setheight(Integer.parseInt(s_xml.getAttribute("z","0")));
        
        //nodes
        int i2;
        int num2=s_xml.getChildrenCount();
        for(i2=0;i2<num2;i2++){
            IXMLElement n_xml=s_xml.getChildAtIndex(i2);
            int x,y,z;
            x=Integer.parseInt(n_xml.getAttribute("x","0"));
            y=Integer.parseInt(n_xml.getAttribute("y","0"));
            z=Integer.parseInt(n_xml.getAttribute("z","0"));
            
            temp_s.add_node(x,y);
        }
        //check if the stage object is valid
        proj_class.addStage(temp_s);
        //if it is valid then load it into
        return true;
    }
    private boolean get_inventory(IXMLElement xml) {
        int num=xml.getChildrenCount();
        
        //create a temp inventory list
        int i;
        for(i=0;i<num;i++){
            IXMLElement i_xml=xml.getChildAtIndex(i);
            int id=Integer.parseInt(i_xml.getAttribute("id","0"));
            
            String type=i_xml.getAttribute("type","none");
            
            String desc=i_xml.getAttribute("desc","none");
            
            if(proj_class.getInstrumentByID(id)==-1){
                //add it
                proj_class.AddInventory(type, id, desc);
            }
        }
        //compare inventory list to the current one and removce any duplicate obejcts
        
        //then add the new items to the inventory
        return true;
    }
    private boolean get_knowntypes(IXMLElement xml) {
        int num=xml.getChildrenCount();
        //create a temp list of types
        int i;
        for(i=0;i<num;i++){
            knowntype tempk=new knowntype();
            IXMLElement t_xml=xml.getChildAtIndex(i);
            String name=t_xml.getAttribute("name","default");
            int height=Integer.parseInt(t_xml.getAttribute("height","0"));
            String desc=t_xml.getAttribute("desc","default");
            boolean aim=Boolean.getBoolean(t_xml.getAttribute("aim","true"));
            
            tempk.setAim(aim);
            tempk.setDesc(desc);
            tempk.setHeight(height);
            tempk.setName(name);
            
            
            //get nodes
            int i2;
            int num2=t_xml.getChildrenCount();
            for(i2=0;i2<num2;i2++){
                IXMLElement n_xml=t_xml.getChildAtIndex(i2);
                int x,y,z;
                x=Integer.parseInt(n_xml.getAttribute("x","0"));
                y=Integer.parseInt(n_xml.getAttribute("y","0"));
                z=Integer.parseInt(n_xml.getAttribute("z","0"));
                
                tempk.add_node(x,y);
            }
            //check if any types are duplicated
        
            if(proj_class.getTypeByName(tempk.getName())==-1){
                //add the type to the known list
        
                proj_class.AddType(tempk);
            }
        }
        
        
        return true;
    }
    
    
    private boolean get_instrument(IXMLElement xml) {
        int num=xml.getChildrenCount();
        //create a temp list of instruments
        int i;
        for(i=0;i<num;i++){
            instrument tempi=new instrument();
            IXMLElement i_xml=xml.getChildAtIndex(i);
            
           try{ 
            tempi.setName(i_xml.getAttribute("name","default"));
            tempi.setDescription(i_xml.getAttribute("description","default"));
            tempi.setBarID(Integer.parseInt(i_xml.getAttribute("bar_id","0")));
            tempi.setType(i_xml.getAttribute("type","default"));
            tempi.worldx=Integer.parseInt(i_xml.getAttribute("x","0"));
            tempi.worldy=Integer.parseInt(i_xml.getAttribute("y","0"));
            tempi.worldz=Integer.parseInt(i_xml.getAttribute("z","0"));
            tempi.setInvnetoryID(Integer.parseInt(i_xml.getAttribute("inventory_id","0")));
            tempi.setDimmerID(Integer.parseInt(i_xml.getAttribute("dimmer_id","0")));
            tempi.aimx=Integer.parseInt(i_xml.getAttribute("aimX","-1"));
            tempi.aimy=Integer.parseInt(i_xml.getAttribute("aimY","-1"));
            tempi.aimz=Integer.parseInt(i_xml.getAttribute("aimZ","-1"));
            
            tempi.R=Float.parseFloat(i_xml.getAttribute("colorR","0.0"));
            tempi.G=Float.parseFloat(i_xml.getAttribute("colorG","0.0"));
            tempi.B=Float.parseFloat(i_xml.getAttribute("colorB","0.0"));
            
            tempi.radius=Integer.parseInt(i_xml.getAttribute("radius","0"));
           }catch(Exception ex){
               System.out.println("Error parseing instrument data");
           }
            //check to see if the bars exists for teh bars to go on and if they are in the right positions
            //check to see if the instruments are in inventory
            int i2;
            int num2=i_xml.getChildrenCount();
            for(i2=0;i2<num2;i2++){
                IXMLElement n_xml=i_xml.getChildAtIndex(i2);
                int x,y,z;
                x=Integer.parseInt(n_xml.getAttribute("x","0"));
                y=Integer.parseInt(n_xml.getAttribute("y","0"));
                z=Integer.parseInt(n_xml.getAttribute("z","0"));
                
                tempi.add_node(x,y);
            }
            //if the lisntruemnts are valid add them
            proj_class.addInstrument(tempi);
            //if the are not valid then remvoe tem
        }
        return true;
    }
    
    private boolean get_project(IXMLElement xml,String path){
        //reset the project to a create a new slate
        IXMLParser parser=null;
        IXMLReader reader=null;
        IXMLElement housexml=null;
        IXMLElement stagexml=null;
        IXMLElement setxml=null;
        IXMLElement barxml=null;
        IXMLElement instxml=null;
        IXMLElement invxml=null;
        IXMLElement typexml=null;
        File inputFile;
        
        //if(xml==null){
        //    return false;
        //}
        
        
        proj_class.resetproject();
        
        String pname=xml.getAttribute("project_name","default");
        String h=xml.getAttribute("isHouse","false");
        
        boolean house=convertBool(h);
        boolean stage=convertBool(xml.getAttribute("isStage","false"));
        boolean set=convertBool(xml.getAttribute("isSets","false"));
        boolean bar=convertBool(xml.getAttribute("isBars","false"));
        boolean ins=convertBool(xml.getAttribute("isInstruments","false"));
        boolean inv=convertBool(xml.getAttribute("isInventory","false"));
        boolean type=convertBool(xml.getAttribute("isTypes","false"));
        
        //if there is a house open it
        if(house){
            try{
                parser = XMLParserFactory.createDefaultXMLParser();
                String housePath=path+"/house.xml";
                inputFile=new File(housePath);
                if((inputFile.exists())&&(inputFile!=null)){
                    housePath="file:///"+housePath;
                    reader = StdXMLReader.fileReader(inputFile,housePath);
                    parser.setReader(reader);
                    housexml = (IXMLElement) parser.parse();
                }
            }catch(Exception e){
                System.out.println("exception parsing house");
            }
            if(housexml!=null){
                get_house(housexml);
            }
            
            //is there a stage
            if(stage){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String stagePath=path+"/stage.xml";
                    inputFile=new File(stagePath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        stagePath="file:///"+stagePath;
                        reader = StdXMLReader.fileReader(inputFile,stagePath);
                        parser.setReader(reader);
                        stagexml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing stage");
                }
                if(stagexml!=null){
                    get_stage(stagexml);
                }
            }
            //is there a set
            if(set){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String setPath=path+"/sets.xml";
                    inputFile=new File(setPath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        setPath="file:///"+setPath;
                        reader = StdXMLReader.fileReader(inputFile,setPath);
                        parser.setReader(reader);
                        setxml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing set");
                }
                if(setxml!=null){
                    get_set(setxml);
                }
            }
            //is there bars
            if(bar){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String barPath=path+"/bars.xml";
                    inputFile=new File(barPath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        barPath="file:///"+barPath;
                        reader = StdXMLReader.fileReader(inputFile,barPath);
                        parser.setReader(reader);
                        barxml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing bars");
                }
                if(barxml!=null){
                    get_bars(barxml);
                }
            }
            //is there types
            if(type){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String typePath=path+"/known_types.xml";
                    inputFile=new File(typePath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        typePath="file:///"+typePath;
                        reader = StdXMLReader.fileReader(inputFile,typePath);
                        parser.setReader(reader);
                        typexml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing types");
                }
                if(typexml!=null){
                    get_knowntypes(typexml);
                }
            }
            //is there inventory
            if(inv){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String inventoryPath=path+"/inventory.xml";
                    inputFile=new File(inventoryPath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        inventoryPath="file:///"+inventoryPath;
                        reader = StdXMLReader.fileReader(inputFile,inventoryPath);
                        parser.setReader(reader);
                        invxml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing inventory");
                }
                if(invxml!=null){
                    get_inventory(invxml);
                }
            }
            //is there a instrument
            if(ins){
                try{
                    parser = XMLParserFactory.createDefaultXMLParser();
                    String insPath=path+"/instruments.xml";
                    inputFile=new File(insPath);
                    if((inputFile.exists())&&(inputFile!=null)){
                        insPath="file:///"+insPath;
                        reader = StdXMLReader.fileReader(inputFile,insPath);
                        parser.setReader(reader);
                        instxml = (IXMLElement) parser.parse();
                    }
                }catch(Exception e){
                    System.out.println("exception parsing instrument");
                }
                if(instxml!=null){
                    get_instrument(instxml);
                }
            }
            
            
        }
        
        
        return true;
    }
    
    
    public boolean get_photons(IXMLElement xml){
         //loading information about photons
            
            int num=xml.getChildrenCount();
            //add photons
            int i,j;
            for(i=0;i<num;i++){
                IXMLElement n_xml=xml.getChildAtIndex(i);
                //n_xml is a photon
                
                double x=Double.parseDouble(n_xml.getAttribute("x","0"));
                double y=Double.parseDouble(n_xml.getAttribute("y","0"));;
                double z=Double.parseDouble(n_xml.getAttribute("z","0"));;
                double nx=Double.parseDouble(n_xml.getAttribute("nx","0"));;
                double ny=Double.parseDouble(n_xml.getAttribute("ny","0"));;
                double nz=Double.parseDouble(n_xml.getAttribute("nz","0"));;
                double dx=Double.parseDouble(n_xml.getAttribute("dx","0"));;
                double dy=Double.parseDouble(n_xml.getAttribute("dy","0"));;
                double dz=Double.parseDouble(n_xml.getAttribute("dz","0"));;
                int light=Integer.parseInt(n_xml.getAttribute("light","0"));;
                float R=Float.parseFloat(n_xml.getAttribute("r","0"));;
                float G=Float.parseFloat(n_xml.getAttribute("g","0"));;
                float B=Float.parseFloat(n_xml.getAttribute("b","0"));;
                
                 proj_class.addPhoton(x,y,z,nx,ny,nz, dx,dy,dz,R,G,B,light); 
                
                
            }
            proj_class.hasPhotons=true;
            proj_class.photonsRendered=true;
            return true;
    }
    
    
    public boolean convertBool(String b){
        if(b.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
