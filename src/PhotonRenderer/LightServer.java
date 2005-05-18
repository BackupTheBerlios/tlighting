package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;
import Data_Storage.*;
import java.util.ArrayList;
import net.n3.nanoxml.*;
import java.io.*;


class LightServer {
    private ArrayList lightList;
    private IntersectionAccelerator intAccel;
    private LightSource[] lights;
    private RenderOptions options;
    project proj_class;
    // indirect illumination
    //public GlobalPhotonMap globalPhotonMap;
    //private CausticPhotonMap causticPhotonMap;
    //private IrradianceCache irradianceCache;
    private int irrM;
    private int irrN;
    
    // direct illumination classification
    private static final int STATUS_SHADOWED = 0;
    private static final int STATUS_PENUMBRA = 1;
    private static final int STATUS_FULL_ILLUM = 2;
    
    LightServer() {
        lightList = new ArrayList();
        lights = null;
        intAccel = new UniformGrid();
        options = null;
        proj_class=(project)project.oClass;
    }
    
    void registerObject(Intersectable object) {
        intAccel.add(object);
        object.getSurfaceShader().setLightServer(this);
    }
    
    void registerLight(LightSource light) {
        lightList.add(light);
    }
    
    void build(RenderOptions options) {
        this.options = options;
        intAccel.build();
        //if (output.isCanceled())
        //   return;
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        //output.println("[LSV] Light Server Init");
        lights = (LightSource[]) lightList.toArray(new LightSource[lightList.size()]);
        
        System.out.println("Starting the build of Photons");
        int numEmitted = 0;
        proj_class.photonmap= new PhotonMap(options.getNumPhotons(), options.getNumGather(), 1e10);
        //globalPhotonMap = options.computeGI() ? new GlobalPhotonMap(options.getNumPhotons(), options.getNumGather(), 1e10) : null;
        //causticPhotonMap = options.computeCaustics() ? new CausticPhotonMap(options.getNumPhotons(), options.getNumGather(), 1e10, 1.1) : null;
        if (lights.length > 0) {
            double[] lightPowers = new double[lights.length];
            for (int i = 0; i < lights.length; i++) {
                lightPowers[i] = lights[i].getAveragePower();
                if (i > 0)
                    lightPowers[i] += lightPowers[i - 1];
            }
            //output.println("[LSV] Tracing photons ...");
            //if (options.computeGI())
            //   output.setTask("Photon Tracing", 0, globalPhotonMap.maxSize());
            //else
            //    output.setTask("Photon Tracing", 0, causticPhotonMap.maxSize());
            QMCSequence photonSampler = new Halton(2, 4);
            while ((!proj_class.photonmap.isFull())&&(numEmitted<options.getNumPhotons()*(lights.length))) {
                
                double[] rnd = photonSampler.getNext();
                double rand1x = rnd[0];
                double rand = rand1x * lightPowers[lights.length - 1];
                int light = -1;
                for (int i = 0; i < lights.length; i++) {
                        /*if (rand < lightPowers[i]) {
                            light = i;
                            if (i == 0)
                                rand1x = rand / lightPowers[0];
                            else
                                rand1x = (rand - lightPowers[i - 1]) / (lightPowers[i] - lightPowers[i - 1]);
                            break;
                        }
                         http://game.theredstick.net - AhDoNo
                    } */
                    light=i;
                    if (light >= 0) {
                        Point3 pt = new Point3();
                        Vector3 dir = new Vector3();
                        Color3 power = new Color3();
                        lights[light].getPhoton(rand1x, rnd[1], rnd[2], rnd[3], pt, dir, power);
                        
                        RenderState aState=RenderState.createPhotonState(new Ray(pt, dir));
                        aState.setCurrentLight(light);
                        //power=Color.RED;
                        
                        tracePhoton(aState, power);
                        numEmitted++;
                    }
                    //if (output.isCanceled())
                    //return;
                }
            }
            
            System.out.println("Done with creation of photonMap");
            // if (options.computeGI())
            //else
            //    output.update(causticPhotonMap.size());
            //if (options.computeGI()) {
            //     output.setTask("Balancing global photon map", 0, 1);
            //    output.println("[LSV] Balancing global photon map ...");
            //    if (options.computeCaustics())
            //      globalPhotonMap.initialize(1.0 / numEmitted / (1.0 - options.getPhotonReductionRatio()));
            //  else
            proj_class.photonmap.initialize(1.0 / numEmitted);
            //  if (output.isCanceled())
            //     return;
            // output.setTask("Precomputing irradiance", 0, 1);
            // output.println("[LSV] Precomputing irradiance ...");
            //proj_class.photonmap.precomputeIrradiance(true, true);
            // if (output.isCanceled())
            //     return;
            //  }
                /*if (options.computeCaustics()) {
                    output.setTask("Balancing caustic photon map", 0, 1);
                    output.println("[LSV] Balancing caustic photon map ...");
                    causticPhotonMap.initialize(1.0 / numEmitted);
                    if (output.isCanceled())
                        return;
                }*/
        }
        //irradianceCache = options.irradianceCaching() ? new IrradianceCache(options.getIrradianceCacheTolerance(), options.getIrradianceCacheSpacing(), intAccel.getBounds()) : null;
        //irrM = (int) Math.max(1, Math.sqrt(options.getIrradianceSamples() / Math.PI));
        //irrN = (int) Math.max(1, (irrM * Math.PI));
        //}
        /*endTime = System.currentTimeMillis();
        output.println("[LSV] Light Server Statistics:");
        output.println("[LSV]   * Light sources found: " + lights.length);
        output.println("[LSV]   * Shadows:             " + (options.traceShadows() ? "on" : "off"));
        output.println("[LSV]   * Light samples:       " + options.getNumLightSamples());
        output.println("[LSV]   * Max raytrace depth:  " + options.getMaxDepth());
        output.println("[LSV]   * Emitted photons:     " + numEmitted);
        output.println("[LSV]   * Global photons:      " + (options.computeGI() ? globalPhotonMap.size() : 0));
        output.println("[LSV]   * Caustic photons:     " + (options.computeCaustics() ? causticPhotonMap.size() : 0));
        output.println("[LSV]   * Irr. cache sampling: " + (options.computeGI() ? ("" + irrM + "x" + irrN) : "0x0"));
        output.println("[LSV]   * Build time:          " + ((endTime - startTime) / 1000.0) + " secs.");
        output.println("[LSV] Done.");*/
    }
    
    /*void display(Camera cam) {
        if (options.computeGI())
            globalPhotonMap.display(cam, "gphotons.png");
        if (options.computeCaustics())
            causticPhotonMap.display(cam, "cphotons.hdr");
    }*/
    
    /*void storePhoton(RenderState state, Vector3 dir, Color3 power,int curLight) {
        //boolean isCaustic = (state.getDiffuseDepth() == 0) && (state.getSpecularDepth() > 0);
        //if (options.computeGI() && (!options.computeCaustics() || (Math.random() >= options.getPhotonReductionRatio())))
     
     
        Photon pho=new Photon(p,d,c);
     
        proj_class.photonmap.storePhoton(state, dir, power, state.getDepth() == 0, isCaustic,curLight);
     
    }*/
    
    public void storePhoton(Point3 p, Vector3 n, Vector3 dir, Color3 power,boolean isDirect, int curLight) {
        //if(globalPhotonMap!=null){
        
        Color c=new Color((float)power.getR(),(float)power.getG(),(float)power.getB());
        
        Photon pho=new Photon(p,dir,c);
        pho.setLightSource(curLight);
        proj_class.photonmap.storePhoton(pho);
        //}
    }
    
    private void tracePhoton(RenderState state, Color3 power) {
        if (state.getDepth() >= options.getMaxDepth())
            return;
        intAccel.intersect(state);
        if (state.hit()) {
            
            state.getObject().setSurfaceLocation(state);
            state.getObject().getSurfaceShader().scatterPhoton(state, power);
        }
    }
    
    void traceDiffusePhoton(RenderState previous, Ray r, Color3 power) {
        if (!options.computeGI())
            return;
        RenderState state = RenderState.createDiffuseBounceState(previous, r);
        tracePhoton(state, power);
    }
    
    void traceSpecularPhoton(RenderState previous, Ray r, Color3 power) {
        RenderState state = RenderState.createSpecularBounceState(previous, r);
        tracePhoton(state, power);
    }
    
    Color3 getRadiance(Ray r,int i) {
        RenderState temps=RenderState.createState(r);
        temps.setCurrentLight(i);
        return getRadiance(temps);
    }
    
    private Color3 getRadiance(RenderState state) {
        if (state.getDepth() >= options.getMaxDepth())
            return new Color3(Color3.BLACK);
        intAccel.intersect(state);
        if (state.hit()) {
            state.getObject().setSurfaceLocation(state);
            
            
            return state.getObject().getSurfaceShader().getRadiance(state);
            
        } else
            return new Color3(Color3.BLACK);
    }
    
    Color3 traceDiffuse(RenderState previous, Ray r) {
        return getRadiance(RenderState.createDiffuseBounceState(previous, r));
    }
    
    Color3 traceSpecular(RenderState previous, Ray r) {
        return getRadiance(RenderState.createSpecularBounceState(previous, r));
    }
    
    /*Color3 getIrradiance(RenderState state) {
        if (!options.computeGI())
            return new Color3(Color3.BLACK);
        if (irradianceCache == null || state.getDiffuseDepth() > 0)
            return globalPhotonMap.getIrradiance(state.getVertex().p, state.getVertex().n);
        Color3 irr = irradianceCache.getIrradiance(state.getVertex().p, state.getVertex().n);
        if (irr == null) {
            // compute new sample
            irr = new Color3(Color3.BLACK);
            OrthoNormalBasis onb = OrthoNormalBasis.makeFromW(state.getVertex().n);
            int hits = 0;
            double invR = 0.0;
            Vector3 w = new Vector3();
     
            // irradiance gradients
            Color3[] rotGradient = new Color3[3];
            Color3[] transGradient1 = new Color3[3];
            Color3[] transGradient2 = new Color3[3];
            for (int i = 0; i < 3; i++) {
                rotGradient[i] = new Color3(Color3.BLACK);
                transGradient1[i] = new Color3(Color3.BLACK);
                transGradient2[i] = new Color3(Color3.BLACK);
            }
     
            // irradiance gradients temp variables
            Vector3 vi = new Vector3();
            Color3 rotGradientTemp = new Color3();
            Vector3 ui = new Vector3();
            Vector3 vim = new Vector3();
            Color3 transGradient1Temp = new Color3();
            Color3 transGradient2Temp = new Color3();
            Color3 lijm = new Color3(); // L_i,j-1
            Color3[] lim = new Color3[irrM]; // L_i-1,j
            Color3[] l0 = new Color3[irrM]; // L_0,j
            for (int i = 0; i < irrM; i++) {
                lim[i] = new Color3();
                l0[i] = new Color3();
            }
            double rijm = 0; // R_i,j-1
            double[] rim = new double[irrM]; // R_i-1,j
            double[] r0 = new double[irrM]; // R_0, j
            for (int i = 0; i < irrN; i++) {
                double xi = (i + Math.random()) / irrN;
                double phi = 2 * Math.PI * xi;
                double cosPhi = Math.cos(phi);
                double sinPhi = Math.sin(phi);
                vi.x = -sinPhi; //Math.cos(phi + Math.PI * 0.5);
                vi.y = cosPhi; //Math.sin(phi + Math.PI * 0.5);
                vi.z = 0.0;
                onb.transform(vi);
                rotGradientTemp.set(Color3.BLACK);
                ui.x = cosPhi;
                ui.y = sinPhi;
                ui.z = 0.0;
                onb.transform(ui);
                double phim = (2.0 * Math.PI * i) / irrN;
                vim.x = Math.cos(phim + (Math.PI * 0.5));
                vim.y = Math.sin(phim + (Math.PI * 0.5));
                vim.z = 0.0;
                onb.transform(vim);
                transGradient1Temp.set(Color3.BLACK);
                transGradient2Temp.set(Color3.BLACK);
                for (int j = 0; j < irrM; j++) {
                    double xj = (j + Math.random()) / irrM;
                    double sinTheta = Math.sqrt(xj);
                    double cosTheta = Math.sqrt(1.0 - xj);
                    w.x = cosPhi * sinTheta;
                    w.y = sinPhi * sinTheta;
                    w.z = cosTheta;
                    onb.transform(w);
                    Color3 lij = Color3.BLACK;
                    RenderState temp = RenderState.createFinalGatherState(state, new Ray(state.getVertex().p, w));
                    intAccel.intersect(temp);
                    if (temp.hit()) {
                        invR += (1.0 / temp.getT());
                        hits++;
                        temp.getObject().setSurfaceLocation(temp);
                        lij = temp.getObject().getSurfaceShader().getRadiance(temp);
                        irr.add(lij);
                        // increment rotational gradient
                        rotGradientTemp.madd(-sinTheta / cosTheta, lij);
                    }
     
                    // increment translational gradient
                    double rij = temp.getT();
                    double sinThetam = Math.sqrt((double) j / irrM);
                    if (j > 0) {
                        double k = (sinThetam * (1.0 - ((double) j / irrM))) / Math.min(rij, rijm);
                        transGradient1Temp.add(Color3.sub(lij, lijm).mul(k));
                    }
                    if (i > 0) {
                        double sinThetap = Math.sqrt((double) (j + 1) / irrM);
                        double k = (sinThetap - sinThetam) / Math.min(rij, rim[j]);
                        transGradient2Temp.add(Color3.sub(lij, lim[j]).mul(k));
                    } else {
                        r0[j] = rij;
                        l0[j].set(lij);
                    }
     
                    // set previous
                    rijm = rij;
                    lijm.set(lij);
                    rim[j] = rij;
                    lim[j].set(lij);
                }
     
                // increment rotational gradient vector
                rotGradient[0].madd(vi.x, rotGradientTemp);
                rotGradient[1].madd(vi.y, rotGradientTemp);
                rotGradient[2].madd(vi.z, rotGradientTemp);
                // increment translational gradient vectors
                transGradient1[0].madd(ui.x, transGradient1Temp);
                transGradient1[1].madd(ui.y, transGradient1Temp);
                transGradient1[2].madd(ui.z, transGradient1Temp);
                transGradient2[0].madd(vim.x, transGradient2Temp);
                transGradient2[1].madd(vim.y, transGradient2Temp);
                transGradient2[2].madd(vim.z, transGradient2Temp);
            }
     
            // finish computing second part of the translational gradient
            vim.x = 0.0;
            vim.y = 1.0;
            vim.z = 0.0;
            onb.transform(vim);
            transGradient2Temp.set(Color3.BLACK);
            for (int j = 0; j < irrM; j++) {
                double sinThetam = Math.sqrt((double) j / irrM);
                double sinThetap = Math.sqrt((double) (j + 1) / irrM);
                double k = (sinThetap - sinThetam) / Math.min(r0[j], rim[j]);
                transGradient2Temp.add(Color3.sub(l0[j], lim[j]).mul(k));
            }
            transGradient2[0].madd(vim.x, transGradient2Temp);
            transGradient2[1].madd(vim.y, transGradient2Temp);
            transGradient2[2].madd(vim.z, transGradient2Temp);
            // scale first part of translational gradient
            double scale = (2.0 * Math.PI) / irrN;
            transGradient1[0].mul(scale);
            transGradient1[1].mul(scale);
            transGradient1[2].mul(scale);
            // sum two pieces of translational gradient
            transGradient1[0].add(transGradient2[0]);
            transGradient1[1].add(transGradient2[1]);
            transGradient1[2].add(transGradient2[2]);
            scale = Math.PI / (irrM * irrN);
            irr.mul(scale);
            rotGradient[0].mul(scale);
            rotGradient[1].mul(scale);
            rotGradient[2].mul(scale);
            invR = (hits > 0) ? (hits / invR) : 0.0;
            irradianceCache.insert(state.getVertex().p, state.getVertex().n, invR, irr, rotGradient, transGradient1);
            if (options.displayIrradianceSamples())
                return new Color3(Color3.YELLOW).mul(1e6);
        }
        return irr;
    }*/
    
    void initLightSamples(RenderState state, boolean getCaustics, boolean getIndirectDiffuse) {
    /*    if (getIndirectDiffuse && (state.getDiffuseDepth() > 0))
            return;
        int max = options.getNumLightSamples();
        if (options.computeCaustics() && getCaustics)
            max += options.getNumGather();
        state.initSamples(max);
        if (options.getNumLightSamples() > 0) {
            QMCSequence sampler = new Halton(2, 2);
            int numVisibleLights = 0;
            int[] shadowStatus = new int[lights.length];
            int[] visibleLights = new int[lights.length];
            for (int i = 0; i < lights.length; i++) {
                shadowStatus[i] = lights[i].isVisible(state) ? STATUS_PENUMBRA : STATUS_SHADOWED;
                if ((shadowStatus[i] != STATUS_SHADOWED)&&(state.getCurrentLight()==i))
                    visibleLights[numVisibleLights++] = i;
            }
            if (numVisibleLights > 0) {
                //need to filter out all light sources so they do not get mixed together
     
                for (int i = 0; i < options.getNumLightSamples(); i++) {
                    LightSample sample = new LightSample();
                    sample.getRadiance().set(Color3.BLACK);
                    // pick a light among the visible lights
                    double[] rnd = sampler.getNext();
                    double rndx = rnd[0];
                    int lidx = (int) (rndx * numVisibleLights);
                    LightSource ls = lights[visibleLights[lidx]];
                    rndx = (rndx * numVisibleLights) - lidx;
                    // pick sample on light source
                    // and set direction
                    ls.getSample(rndx, rnd[1], state, sample);
                    // acount for probability of selecting this light amongst all visible ones
                    sample.getRadiance().mul((double) numVisibleLights / (double) options.getNumLightSamples());
                    if (shadowStatus[lidx] == STATUS_FULL_ILLUM)
                        sample.setShadowRay(null);
                    state.addSample(sample);
                }
            }
        }
        if (options.computeCaustics() && getCaustics)
            causticPhotonMap.getNearestPhotons(state);*/
    }
    
    /*boolean isShadowed(LightSample sample) {
        // is in shadow?
        if (sample.isValid())
            if (!options.traceShadows() || (sample.getShadowRay() == null) || !intAccel.intersects(sample.getShadowRay()))
                return false;
     
        return true;
    }*/
    
    
    public Photon[] getAllPhotonsAt(Point3 apoint){
        int i;
        Photon[] parray = new Photon[30];
        for(i=0;i<30;i++){
            parray[i]=new Photon();
        }
        
        int f=0;
        for(i=0;i<proj_class.photonmap.getStoredPhotons();i++){
            if((proj_class.photonmap.getPhoton(i).x>=apoint.x-1)&&(proj_class.photonmap.getPhoton(i).x<=apoint.x+1)){
                if((proj_class.photonmap.getPhoton(i).y>=apoint.y-1)&&(proj_class.photonmap.getPhoton(i).y<=apoint.y+1)){
                    if((proj_class.photonmap.getPhoton(i).z>=apoint.z-1)&&(proj_class.photonmap.getPhoton(i).z<=apoint.z+1)){
                        parray[f]=proj_class.photonmap.getPhoton(i);
                        f++;
                    }
                }
            }
        }
        return parray;
    }
    
    public void savePhotonsToXML(){
        //variables to use to open a file and write xml to it
        File outputfile; //file to open
        XMLWriter writer=null; // xml writer
        FileOutputStream out = null; // output stream to send to xml writer
        int j;
        
        int numLights;
        int[] lightParts=new int[5000];
        System.out.println("Saving photonmap");
        for(j=0;j<lights.length;j++){
            //catch any exceptions with openeing the file
            try{
                //open a house file
                outputfile = new File("photonmaps/LightsPhotons"+j+".xml");
                //if it does not exist then create it
                outputfile.createNewFile();
                //open the output stream
                out = new FileOutputStream(outputfile);
            }catch(Exception e){
                System.out.println("Exception opening file");
                return;
            }
            //catch any exceptions with openeing the xml writer
            try{
                //if the output stream did not open then can't open the xml writer
                if(out!=null){
                    //create a new xml writer
                    writer = new XMLWriter((OutputStream)out);
                }
            }catch(Exception e){
                System.out.println("exception opening writer");
                return;
            }
            //root object to contain all other objects adn hold link to project id
            XMLElement parent_obj=new XMLElement();
            //create the parent element
            parent_obj.createElement("photon_list");
            //set the name that will appear in the file
            parent_obj.setName("photon_info");
            //give it the proj id as an attribute so we can do some checkign when reading it back in
            parent_obj.setAttribute("light_number",String.valueOf(j));
            parent_obj.setAttribute("part","0");
            
            int i;
            int part=0;
            int temp_i=0;
            for(i=1;i<proj_class.photonmap.getStoredPhotons();i++){
                
                
                if(temp_i>1000){
                    try{
                        if(writer!=null){
                            writer.write(parent_obj,true);
                        }
                    }catch(Exception e){
                        System.out.println("Error exporting project object to xml");
                    }
                    part++;
                    temp_i=0;
                    
                    try{
                        //open a house file
                        outputfile = new File("photonmaps/LightsPhotons"+j+"_part"+part+".xml");
                        //if it does not exist then create it
                        outputfile.createNewFile();
                        //open the output stream
                        out = new FileOutputStream(outputfile);
                    }catch(Exception e){
                        System.out.println("Exception opening file");
                        return;
                    }
                    //catch any exceptions with openeing the xml writer
                    try{
                        //if the output stream did not open then can't open the xml writer
                        if(out!=null){
                            //create a new xml writer
                            writer = new XMLWriter((OutputStream)out);
                        }
                    }catch(Exception e){
                        System.out.println("exception opening writer");
                        return;
                    }
                    //root object to contain all other objects adn hold link to project id
                    parent_obj=new XMLElement();
                    //create the parent element
                    parent_obj.createElement("photon_list");
                    //set the name that will appear in the file
                    parent_obj.setName("photon_info");
                    //give it the proj id as an attribute so we can do some checkign when reading it back in
                    parent_obj.setAttribute("light_number",String.valueOf(j));
                    parent_obj.setAttribute("part",String.valueOf(part));
                    
                    
                }
                
                Photon pho=proj_class.photonmap.getPhoton(i);
                if(pho!=null){
                    if(pho.lightSource==j){
                        temp_i++;
                        // System.out.println("Saving photon "+i);
                        //xml photon object
                        XMLElement p_obj=new XMLElement();
                        
                        
                        p_obj.createElement("photon");
                        p_obj.setName("photon");
                        
                        p_obj.setAttribute("x",String.valueOf(pho.x));
                        p_obj.setAttribute("y",String.valueOf(pho.y));
                        p_obj.setAttribute("z",String.valueOf(pho.z));
                        p_obj.setAttribute("nx",String.valueOf(pho.nx));
                        p_obj.setAttribute("ny",String.valueOf(pho.ny));
                        p_obj.setAttribute("nz",String.valueOf(pho.nz));
                        p_obj.setAttribute("dx",String.valueOf(pho.dx));
                        p_obj.setAttribute("dy",String.valueOf(pho.dy));
                        p_obj.setAttribute("dz",String.valueOf(pho.dz));
                        p_obj.setAttribute("power",String.valueOf(pho.power));
                        p_obj.setAttribute("r",String.valueOf(pho.R));
                        p_obj.setAttribute("g",String.valueOf(pho.G));
                        p_obj.setAttribute("b",String.valueOf(pho.B));
                        p_obj.setAttribute("light",String.valueOf(j));
                        parent_obj.addChild(p_obj);
                    }
                }
            }
            
            try{
                if(writer!=null){
                    writer.write(parent_obj,true);
                }
            }catch(Exception e){
                System.out.println("Error exporting project object to xml");
            }
            
        }
        
        System.out.println("Saving done");
        
        
        //save management file saying howmany lights and how many parts were saved for each light
        
    }
    
    
    public int getNumLights(){
        return lights.length;
    }
}