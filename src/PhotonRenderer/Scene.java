package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;
import Data_Storage.*;
/**
 * Represents a entire scene, defined as a collection of objects viewed by a camera.
 */
public class Scene {
    private LightServer lightServer;
    //private Camera camera;
    project proj_class; 
    
    public boolean hasPhotons;
    /**
     * Creates an empty scene with default anti-aliasing parameters.
     */
    public Scene() {
        lightServer = new LightServer();
        //camera = null;
        hasPhotons=false;
        proj_class=(project)project.oClass; 
    }
    
    /**
     * Sets the current camera (no support for multiple cameras yet).
     *
     * @param camera camera to be used as the viewpoint for the scene
     */
    //public void addCamera(Camera camera) {
    //    this.camera = camera;
    //}
    
    /**
     * Adds an object to the scene.
     *
     * @param object object to be added to the scene
     */
    public void addObject(Intersectable object) {
        lightServer.registerObject(object);
    }
    
    /**
     * Adds a light source to the scene. Note that for area light sources you will need to call
     * {@link #addObject(Intersectable)} in order to make the light source visible to the raytracer.
     *
     * @param light light to be added to the scene
     */
    public void addLight(LightSource light) {
        lightServer.registerLight(light);
    }
    
    /**
     * Render the current scene and save the output to the location indicated by the output filename. Prints out
     * progress information to <code>System.out</code>.
     *
     * @param options rendering options
     * @param output method to use to display progress, defaults to System.out output if <code>null</code>
     */
    public void render(RenderOptions options) {
        //if (camera == null)
        //    return;
        //if (output == null)
        //    output = new ConsoleProgressDisplay();
        
        if (options == null)
            options = new RenderOptions();
        options.validate();
        
        //if(!hasPhotons){
            lightServer.build(options);
            //if (output.isCanceled())
                //return;
            //output.setTask("Saving photon images", 0, 1);
           // lightServer.savePhotonsToXML();
            hasPhotons=true;
       // }
        //lightServer.display(camera);
        
       /* int minN = options.getMinAASamples();
        int maxN = options.getMaxAASamples();
        boolean dispAA = options.displayAASamples();
        double threshold = options.getAAThreshold();
        
        int imageWidth = camera.getImageWidth();
        int imageHeight = camera.getImageHeight();
        Bitmap aliased = dispAA ? new Bitmap(imageWidth, imageHeight, true) : null;
        Bitmap[] result= new Bitmap[3];
        int i;
        for(i=0;i<3;i++){
            result[i]= new Bitmap(imageWidth, imageHeight, false);
        }
        QMCSequence pixelSampler = new Halton(2, 2);
        Color sum = new Color();
        Color var = new Color();
        Color[] samples = new Color[maxN];
        Color[] greyScale = dispAA ? new Color[maxN] : null;
        if (dispAA)
            for (i = 0; i < maxN; i++)
                greyScale[i] = new Color((double) i / maxN, (double) i / maxN, (double) i / maxN);
        
        if(hasPhotons){
            output.println("[SCN] Rendering ...");
            output.setTask("Rendering", 0, imageHeight - 1);
            long startTime = System.currentTimeMillis();
            
            for(i=0;i<lightServer.getNumLights();i++){
                for (int y = 0; y < imageHeight; y++) {
                    output.update(y);
                    int[] scanLine = new int[imageWidth];
                    for (int x = 0; x < imageWidth; x++) {
                        if (maxN == 1){
                            Color tempc=lightServer.getRadiance(camera.getRay(x, y, 0, 0),i);
                            
                            
                            //Color tempc=Color.WHITE;
                            //camera.getRay(x, y, 0, 0) find all photons around the intersection of this ray and the first object
                            int j;
                            //for(j=0;j<)
                            //camera.getRay(x, y, 0, 0)
                            
                            //sum up the photons from the current light source
                            
                            result[i].setPixel(x, y, tempc);
                        }else {
                            int n = 0;
                            sum.set(Color.BLACK);
                            pixelSampler.reset();
                            while (true) {
                                double[] rnd = pixelSampler.getNext();
                                double offx = (minN == maxN) ? ((n + 0.5) / maxN) : rnd[0];
                                double offy = (minN == maxN) ? rnd[0] : rnd[1];
                                offx = (offx < 0.5) ? (-1.0 + Math.sqrt(2.0 * offx)) : (1.0 - Math.sqrt(2.0 - (2.0 * offx)));
                                offy = (offy < 0.5) ? (-1.0 + Math.sqrt(2.0 * offy)) : (1.0 - Math.sqrt(2.0 - (2.0 * offy)));
                                
                                Color c = lightServer.getRadiance(camera.getRay(x + offx, y + offy, 0, 0),i);
                                sum.add(c);
                                System.out.println("sum is now "+sum.toString());
                                samples[n] = c;
                                n++;
                                if (n == maxN)
                                    break;
                                if (n >= minN) {
                                    // compute variance of samples taken so far
                                    var.set(Color.BLACK);
                                    Color avg = Color.mul(1.0 / n, sum);
                                    for (int q = 0; q < n; q++) {
                                        Color c2 = Color.sub(samples[q], avg);
                                        var.add(c2.mul(c2));
                                    }
                                    if (var.getLuminance() <= ((n - 1) * threshold))
                                        break;
                                }
                            }
                            if (dispAA)
                                aliased.setPixel(x, y, greyScale[n - 1]);
                            result[i].setPixel(x, y, sum.mul(1.0 / n));
                        }//*/
    /*
                        scanLine[x] = result[i].getPixel(x, y).toRGB();
                    }
                    output.updateScanLine(y, scanLine);
                    if (output.isCanceled())
                        return;
                }
                result[i].save(i+options.getOutputFilename());
            }
            
            int y,x;
            Bitmap end_result= new Bitmap(imageWidth, imageHeight, false);
            
            for(y=0;y<imageHeight;y++){
                for(x=0;x<imageWidth;x++){
                    int asum=0;
                    for(i=0;i<lightServer.getNumLights();i++){
                        asum+=result[i].getPixel(x,y).toRGB();
                    }
                    end_result.setPixel(x,y,asum);
                }
            }
            
            long endTime = System.currentTimeMillis();
            output.println("[SCN] Rendering time: " + ((endTime - startTime) / 1000.0) + " secs.");
            output.println("[SCN] Saving images  ...");
            output.setTask("Saving images", 0, 1);
            
            if (dispAA){
                aliased.save("AAsamples-" + options.getOutputFilename());
            }
            
            output.println("[SCN] Rendering Done.");
        }
        output.println("[SCN] Done.");*/
    }
    
    
    public void addPhoton(double x, double y, double z, double nx, double ny, double nz, double dx, double dy, double dz, Color3 power, int light,RenderOptions options){
        if(lightServer==null){
            return;
        }
        if(proj_class.photonmap==null){
            proj_class.photonmap =  new PhotonMap(options.getNumPhotons(), options.getNumGather(), 1e10);
            
        }
        Point3 p=new Point3(x,y,z);
        Vector3 n=new Vector3(nx,ny,nz);
        Vector3 dir=new Vector3(dx,dy,dz);
        
        lightServer.storePhoton(p,n,dir, power, false, light);
    }
    
}