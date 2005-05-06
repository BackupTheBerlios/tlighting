/*
 * PhotonRenderer.java
 *
 * Created on May 5, 2005, 1:45 PM
 */

package PhotonRenderer;


import drawing_prog.*;
import Data_Storage.*;

/**
 *
 * @author josh zawislak
 */
public class PhotonRenderer {
    //variables needed to put data into
    //options
    RenderOptions options;
    //scene
    Scene scene;
    
    project proj_class;
    
    boolean optionsSet;
    boolean sceneSet;
    
    
    /** Creates a new instance of PhotonRenderer */
    public PhotonRenderer() {
        scene=new Scene();
        options=new RenderOptions();
        proj_class=(project)project.oClass;
    }
    
    public boolean setOptions(){
        int minAA=1;
        int maxAA=5;
        double thresAA=0.00005;
        boolean dispAA=true;
        //outputFile="TestOutput.png"
        boolean traceShadows=true;
        int numLightSamples=6;
        int maxDepth=5;
        boolean computerGI=true;
        int numPhotons=50000;
        int numGather=50;
        double irrTol=0.2;
        double irrSpace=0.05;
        int irrSample=315;
        boolean compCaustic=true;
        double photonReduc=.1;
        
        double shaderR=0.50588;
        double shaderG=0.554902;
        double shaderB=0.578431;
        
        
        options.setMinAASamples(minAA);
        options.setMaxAASamples(maxAA);
        options.setAAThreshold(thresAA);
        options.setDisplayAASamples(dispAA);
        //options.setOutputFilename(outputFile);
        options.setTraceShadows(traceShadows);
        options.setNumLightSamples(numLightSamples);
        options.setMaxDepth(maxDepth);
        options.setComputeGI(computerGI);
        options.setNumPhotons(numPhotons);
        options.setNumGather(numGather);
        options.setIrradianceCacheTolerance(irrTol);
        options.setIrradianceCacheSpacing(irrSpace);
        options.setIrradianceSamples(irrSample);
        options.setComputeCaustics(compCaustic);
        options.setPhotonReductionRatio(photonReduc);
        
        optionsSet=true;
        return true;
        
    }
    
    public boolean setScene(){
        if(optionsSet){
            
            //load stage into scene
            if(proj_class.stageadded){
                stage ts=(stage)proj_class.stages.get_object(0);
                addObjectToScene(ts.num_nodes, ts.worldx, ts.worldy, 0, ts.getxs(), ts.getys(), ts.getheight() );
                
            }
            
            //load set objects into the scene
            int i;
            for(i=0;i<proj_class.sets.get_num_objects();i++){
                setobject ts= (setobject)proj_class.sets.get_object(i);
                addObjectToScene(ts.num_nodes, ts.worldx, ts.worldy, 0, ts.getxs(), ts.getys(), ts.getsize());
            }
            
            //load the isntruments into the scene
            for(i=0;i<proj_class.instruments.get_num_objects();i++){
                
                instrument tl= (instrument)proj_class.instruments.get_object(i);
                
                double x=tl.worldx;
                double y=tl.worldy;
                double z=tl.worldz;
                
                double tx=tl.aimx;
                double ty=tl.aimy;
                double tz=tl.aimz;
                
                double radius=tl.radius;
                
                double R=tl.R;
                double G=tl.G;;
                double B=tl.B;
                
                
                scene.addLight(new DirectionalSpotlight(new Point3(x, y, z), new Point3(tx, ty, tz), radius, new Color3(R, G, B))); 
                
            }
            
            
            sceneSet=true;
            return true;
        }
        
       return false;
    }
    
    
    public boolean renderScene(){
        if(optionsSet){
            if(sceneSet){
                
                scene.render(options);
                proj_class.hasPhotons=true;
                proj_class.photonsRendered=true;
                return true;
            }
        }
        
        return false;
    }
    
    public void addObjectToScene(int numvs, int gx, int gy, int gz, int[]xs, int[] ys, int height){
        
        //IXMLElement s_xml=xml.getChildAtIndex(i);
        //get set info
        
        //int gx=Integer.parseInt(s_xml.getAttribute("x","0"));
        //int gy=Integer.parseInt(s_xml.getAttribute("y","0"));
        //int gz=Integer.parseInt(s_xml.getAttribute("z","0"));
        //int[] xs=new int[30];
        //int[] ys=new int[30];
        //int[] zs=new int[30];
        //int numvs=0;
                /*for(j=0;j<s_xml.getChildrenCount();j++){
                    //get node info
                    IXMLElement n_xml=s_xml.getChildAtIndex(j);
                    xs[numvs]=Integer.parseInt(n_xml.getAttribute("x","0"));
                    ys[numvs]=Integer.parseInt(n_xml.getAttribute("y","0"));
                    zs[numvs]=Integer.parseInt(n_xml.getAttribute("z","0"));
                    numvs++;
                 
                }*/
        
        //create the top wire mesh
        //all triangles are two adjacent points connected to the middle point
        //skip the triangles that include the middle point
        
        DiffuseShader shader = new DiffuseShader(Color3.WHITE);
        int middle=numvs/2;
        
        int i;
        for(i=0;i<numvs-1;i++){
            if(i!=middle){
                if(i+1!=middle){
                    //no point being used is the middle point
                    Vertex v1 = new Vertex();
                    Vertex v2 = new Vertex();
                    Vertex v3 = new Vertex();
                    
                    v1.p.x=gx+xs[i];
                    v1.p.y=gy+ys[i];
                    v1.p.z=gz+height;
                    
                    v2.p.x=gx+xs[i+1];
                    v2.p.y=gy+ys[i+1];
                    v2.p.z=gz+height;
                    
                    v3.p.x=gx+xs[middle];
                    v3.p.y=gy+ys[middle];
                    v3.p.z=gz+height;
                    
                    
                    //Color c=new Color();
                    
                    Triangle aTri=new Triangle(shader,v1,v2,v3);
                    scene.addObject(aTri);
                }
            }
        }
        //now do the last point and first point
        i=numvs-1;
        if(0!=middle){
                if(i!=middle){
                    //no point being used is the middle point
                    Vertex v1 = new Vertex();
                    Vertex v2 = new Vertex();
                    Vertex v3 = new Vertex();
                    
                    v1.p.x=gx+xs[i];
                    v1.p.y=gy+ys[i];
                    v1.p.z=gz+height;
                    
                    v2.p.x=gx+xs[0];
                    v2.p.y=gy+ys[0];
                    v2.p.z=gz+height;
                    
                    v3.p.x=gx+xs[middle];
                    v3.p.y=gy+ys[middle];
                    v3.p.z=gz+height;
                    
                    
                    //Color c=new Color();
                    
                    Triangle aTri=new Triangle(shader,v1,v2,v3);
                    scene.addObject(aTri);
                }
            }
        
        //bottom mesh is the same as top with z=0
        for(i=0;i<numvs-1;i++){
            if(i!=middle){
                if(i+1!=middle){
                    //no point being used is the middle point
                    Vertex v1 = new Vertex();
                    Vertex v2 = new Vertex();
                    Vertex v3 = new Vertex();
                    
                    v1.p.x=gx+xs[i];
                    v1.p.y=gy+ys[i];
                    v1.p.z=gz;
                    
                    v2.p.x=gx+xs[i+1];
                    v2.p.y=gy+ys[i+1];
                    v2.p.z=gz;
                    
                    v3.p.x=gx+xs[middle];
                    v3.p.y=gy+ys[middle];
                    v3.p.z=gz;
                    
                    
                    
                    
                    
                    Triangle aTri=new Triangle(shader,v1,v2,v3);
                    scene.addObject(aTri);
                }
            }
        }
        //now do the last point and first point
        i=numvs-1;
        if(0!=middle){
                if(i!=middle){
                    //no point being used is the middle point
                    Vertex v1 = new Vertex();
                    Vertex v2 = new Vertex();
                    Vertex v3 = new Vertex();
                    
                    v1.p.x=gx+xs[i];
                    v1.p.y=gy+ys[i];
                    v1.p.z=gz;
                    
                    v2.p.x=gx+xs[0];
                    v2.p.y=gy+ys[0];
                    v2.p.z=gz;
                    
                    v3.p.x=gx+xs[middle];
                    v3.p.y=gy+ys[middle];
                    v3.p.z=gz;
                    
                    
                    //Color c=new Color();
                    
                    Triangle aTri=new Triangle(shader,v1,v2,v3);
                    scene.addObject(aTri);
                }
            }
        
        //now do the sides so each one has 4 point with 2 triangles
        
        for(i=0;i<numvs-1;i++){
            //points are bottom left top left and bottom right
            //no point being used is the middle point
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Vertex v3 = new Vertex();
            
            v1.p.x=gx+xs[i];
            v1.p.y=gy+ys[i];
            v1.p.z=gz;
            
            v2.p.x=gx+xs[i];
            v2.p.y=gy+ys[i];
            v2.p.z=gz+height;
            
            v3.p.x=gx+xs[i+1];
            v3.p.y=gy+ys[i+1];
            v3.p.z=gz;
            
            Triangle aTri=new Triangle(shader,v1,v2,v3);
            scene.addObject(aTri);
            
            //points are bottom right top right and top left
            
            
            v1.p.x=gx+xs[i+1];
            v1.p.y=gy+ys[i+1];
            v1.p.z=gz;
            
            v2.p.x=gx+xs[i+1];
            v2.p.y=gy+ys[i+1];
            v2.p.z=gz+height;
            
            v3.p.x=gx+xs[i];
            v3.p.y=gy+ys[i];
            v3.p.z=gz+height;
            
            
            Triangle aTri2=new Triangle(shader,v1,v2,v3);
            scene.addObject(aTri2);
            
            
        }
        
        //now finsih off the enclosed object
        i=numvs-1;
        
        //points are bottom left top left and bottom right
            //no point being used is the middle point
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Vertex v3 = new Vertex();
            
            v1.p.x=gx+xs[i];
            v1.p.y=gy+ys[i];
            v1.p.z=gz;
            
            v2.p.x=gx+xs[i];
            v2.p.y=gy+ys[i];
            v2.p.z=gz+height;
            
            v3.p.x=gx+xs[0];
            v3.p.y=gy+ys[0];
            v3.p.z=gz;
            
            Triangle aTri=new Triangle(shader,v1,v2,v3);
            scene.addObject(aTri);
            
            //points are bottom right top right and top left
            
            
            v1.p.x=gx+xs[0];
            v1.p.y=gy+ys[0];
            v1.p.z=gz;
            
            v2.p.x=gx+xs[0];
            v2.p.y=gy+ys[0];
            v2.p.z=gz+height;
            
            v3.p.x=gx+xs[i];
            v3.p.y=gy+ys[i];
            v3.p.z=gz+height;
            
            
            Triangle aTri2=new Triangle(shader,v1,v2,v3);
            scene.addObject(aTri2);
        
        
        
    }
    

    
}
