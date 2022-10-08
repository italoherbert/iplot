package italo.iplot.plot3d.g3d;
import java.util.ArrayList;
import java.util.List;

public class ParalelepipedoObjeto3D extends Objeto3D implements ContainerObjeto3D {
    
    protected double dx = 1.0d;
    protected double dy = 1.0d;
    protected double dz = 1.0d;
    
    protected double minX;
    protected double maxX;
    protected double minY;
    protected double maxY;
    protected double minZ;
    protected double maxZ;

    protected Vertice3D frenteV1;
    protected Vertice3D frenteV2;
    protected Vertice3D frenteV3;
    protected Vertice3D frenteV4;

    protected Vertice3D trazV1;
    protected Vertice3D trazV2;
    protected Vertice3D trazV3;
    protected Vertice3D trazV4;  
    
    protected Face3D faceT1T2T3T4;
    protected Face3D faceF1F2F3F4;
    protected Face3D faceT1F1F4T4;    
    protected Face3D faceT1T2F2F1;
    protected Face3D faceT2T3F3F2;
    protected Face3D faceT4T3F3F4;

    protected List<ComponenteObjeto3D> plotObjs = new ArrayList();
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {
        double mx = dx / 2;
        double my = dy / 2;
        double mz = dz / 2;
                                
        frenteV1 = new Vertice3D( -mx,  my,  mz );
        frenteV2 = new Vertice3D(  mx,  my,  mz );
        frenteV3 = new Vertice3D(  mx, -my,  mz );
        frenteV4 = new Vertice3D( -mx, -my,  mz );

        trazV1 = new Vertice3D( -mx,  my, -mz );
        trazV2 = new Vertice3D(  mx,  my, -mz );
        trazV3 = new Vertice3D(  mx, -my, -mz );
        trazV4 = new Vertice3D( -mx, -my, -mz );
                
        faceF1F2F3F4 = new Face3D();
        
        faceF1F2F3F4.setInverterVN( true );
        faceF1F2F3F4.addVertice( frenteV1 );
        faceF1F2F3F4.addVertice( frenteV2 );
        faceF1F2F3F4.addVertice( frenteV3 );
        faceF1F2F3F4.addVertice( frenteV4 );                
        
        faceT1T2T3T4 = new Face3D();
        faceT1T2T3T4.addVertice( trazV1 );
        faceT1T2T3T4.addVertice( trazV2 );
        faceT1T2T3T4.addVertice( trazV3 );
        faceT1T2T3T4.addVertice( trazV4 );
        
        faceT1F1F4T4 = new Face3D();
        faceT1F1F4T4.setInverterVN( true );
        faceT1F1F4T4.addVertice( trazV1 );
        faceT1F1F4T4.addVertice( frenteV1 );
        faceT1F1F4T4.addVertice( frenteV4 );
        faceT1F1F4T4.addVertice( trazV4 );
        
        faceT1T2F2F1 = new Face3D();
        faceT1T2F2F1.setInverterVN( true );
        faceT1T2F2F1.addVertice( trazV1 );
        faceT1T2F2F1.addVertice( trazV2 );
        faceT1T2F2F1.addVertice( frenteV2 );
        faceT1T2F2F1.addVertice( frenteV1 );
        
        faceT2T3F3F2 = new Face3D();
        faceT2T3F3F2.setInverterVN( true );
        faceT2T3F3F2.addVertice( trazV2 );
        faceT2T3F3F2.addVertice( trazV3 );
        faceT2T3F3F2.addVertice( frenteV3 );
        faceT2T3F3F2.addVertice( frenteV2 );
        
        faceT4T3F3F4 = new Face3D();
        faceT4T3F3F4.setInverterVN( true ); 
        faceT4T3F3F4.addVertice( trazV3 );
        faceT4T3F3F4.addVertice( trazV4 );        
        faceT4T3F3F4.addVertice( frenteV4 );
        faceT4T3F3F4.addVertice( frenteV3 );
                         
        super.estrutura.addVertice( trazV1 );
        super.estrutura.addVertice( trazV2 );
        super.estrutura.addVertice( trazV3 );
        super.estrutura.addVertice( trazV4 );
                       
        super.estrutura.addVertice( frenteV1 );
        super.estrutura.addVertice( frenteV2 );
        super.estrutura.addVertice( frenteV3 );
        super.estrutura.addVertice( frenteV4 );
        
        super.estrutura.addFace( faceT1T2T3T4, to );        
        super.estrutura.addFace( faceF1F2F3F4, to ); 
        super.estrutura.addFace( faceT1F1F4T4, to );
        super.estrutura.addFace( faceT1T2F2F1, to );
        super.estrutura.addFace( faceT2T3F3F2, to );
        super.estrutura.addFace( faceT4T3F3F4, to );                                                                              
        
        this.calculaIntervalos();                
    }
    
    protected void calculaIntervalos() {
        if ( plotObjs.isEmpty() ) {
            this.minX = ComponenteObjeto3DLimite.MINX;
            this.maxX = ComponenteObjeto3DLimite.MAXX;
            this.minY = ComponenteObjeto3DLimite.MINY;
            this.maxY = ComponenteObjeto3DLimite.MAXY   ;
            this.minZ = ComponenteObjeto3DLimite.MINZ;
            this.maxZ = ComponenteObjeto3DLimite.MAXZ;
        } else {
            this.minX = Double.MAX_VALUE;
            this.maxX = Double.MIN_VALUE;
            this.minY = Double.MAX_VALUE;
            this.maxY = Double.MIN_VALUE;
            this.minZ = Double.MAX_VALUE;
            this.maxZ = Double.MIN_VALUE;

            for( ComponenteObjeto3D obj : plotObjs ) {
                ComponenteObjeto3DLimite lims = obj.calculaLimites();
                if ( lims.getMinX() < minX )
                    minX = lims.getMinX();
                if ( lims.getMaxX() > maxX )
                    maxX = lims.getMaxX();            
                if ( lims.getMinY() < minY )
                    minY = lims.getMinY();
                if ( lims.getMaxY() > maxY )
                    maxY = lims.getMaxY();
                if ( lims.getMinZ() < minZ )
                    minZ = lims.getMinZ();
                if ( lims.getMaxZ() > maxZ )
                    maxZ = lims.getMaxZ();        
            } 
        }
    }

    @Override
    public double[] verticeCentral() {
        this.calculaIntervalos();
        return new double[] {
            ( maxX - minX ) *.5d,
            ( maxY - minY ) *.5d,
            ( maxZ - minZ ) *.5d
        };
    }
    
    public double xCentral() {
        return ( maxX - minX ) *.5d;
    }
    
    public double yCentral() {
        return ( maxY - minY ) *.5d;        
    }
    
    public double zCentral() {
        return ( maxZ - minZ ) *.5d;
    }
    
    public void addComponenteObj3D( ComponenteObjeto3D plotObj3d ) {
        super.addObjeto( (Objeto3D)plotObj3d ); 
        plotObjs.add( plotObj3d );
        plotObj3d.setContainerObjeto3D( this ); 
    }

    @Override
    public double calculaX(double x) {
        return -dx*.5d + ( ( Math.abs(x-minX)/Math.abs(maxX-minX) ) * dx );
    }

    @Override
    public double calculaY(double y) {
        return -dy*.5d + ( ( Math.abs(y-minY)/Math.abs(maxY-minY) ) * dy );
    }

    @Override
    public double calculaZ(double z) {
        return -dz*.5d + ( ( Math.abs(z-minZ)/Math.abs(maxZ-minZ) ) * dz );
    }

    public double getDX() {
        return dx;
    }

    public void setDX(double dx) {
        this.dx = dx;
    }

    public double getDY() {
        return dy;
    }

    public void setDY(double dy) {
        this.dy = dy;
    }

    public double getDZ() {
        return dz;
    }

    public void setDZ(double dz) {
        this.dz = dz;
    }

    public Vertice3D getFrenteV1() {
        return frenteV1;
    }

    public Vertice3D getFrenteV2() {
        return frenteV2;
    }

    public Vertice3D getFrenteV3() {
        return frenteV3;
    }

    public Vertice3D getFrenteV4() {
        return frenteV4;
    }

    public Vertice3D getTrazV1() {
        return trazV1;
    }

    public Vertice3D getTrazV2() {
        return trazV2;
    }

    public Vertice3D getTrazV3() {
        return trazV3;
    }

    public Vertice3D getTrazV4() {
        return trazV4;
    }

    public Face3D getFaceT1T2T3T4() {
        return faceT1T2T3T4;
    }

    public Face3D getFaceF1F2F3F4() {
        return faceF1F2F3F4;
    }

    public Face3D getFaceT1F1F4T4() {
        return faceT1F1F4T4;
    }

    public Face3D getFaceT1T2F2F1() {
        return faceT1T2F2F1;
    }

    public Face3D getFaceT2T3F3F2() {
        return faceT2T3F3F2;
    }

    public Face3D getFaceT4T3F3F4() {
        return faceT4T3F3F4;
    }

}
