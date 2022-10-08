package italo.iplot.plot3d.g3d;

public class RetanguloObjeto3D extends Objeto3D {
    
    private Vertice3D v1;
    private Vertice3D v2;
    private Vertice3D v3;
    private Vertice3D v4;
    
    private Face3D face;
    private Face3D face2;
    private boolean duasFaces = true;
    
    private double dx = 1.0d;
    private double dz = 1.0d;
    
    private boolean inverterVN = false;

    public RetanguloObjeto3D() {
        super.addNovaFaceAposCorte = false;
    }
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {
        double mx = dx / 2;
        double mz = dz / 2;
        
        v1 = new Vertice3D( -mx, 0, -mz );
        v2 = new Vertice3D(  mx, 0, -mz );
        v3 = new Vertice3D(  mx, 0,  mz );
        v4 = new Vertice3D( -mx, 0,  mz );
        
        super.getEstrutura().addVertice( v1 );
        super.getEstrutura().addVertice( v2 );
        super.getEstrutura().addVertice( v3 );
        super.getEstrutura().addVertice( v4 );
        
        face = new Face3D();
        face.setInverterVN( inverterVN );
        face.addVertice( v1 );
        face.addVertice( v2 );
        face.addVertice( v3 );
        face.addVertice( v4 );              
        
        super.getEstrutura().addFace( face, to ); 
            
        if ( duasFaces ) {
            face2 = new Face3D();
            face2.setInverterVN( !inverterVN );        
            face2.addVertice( v1 );
            face2.addVertice( v2 );
            face2.addVertice( v3 );
            face2.addVertice( v4 ); 
            
            super.getEstrutura().addFace( face2, to ); 
        }                                                        
    }
  
    public void setDXDZ( double dx, double dz ) {
        this.dx = dx;
        this.dz = dz;
    }
    
    public double getDX() {
        return dx;
    }

    public void setDX(double dx) {
        this.dx = dx;
    }

    public double getDZ() {
        return dz;
    }

    public void setDZ(double dz) {
        this.dz = dz;
    }

    public boolean isInverterVN() {
        return inverterVN;
    }

    public void setInverterVN(boolean inverterVN) {
        this.inverterVN = inverterVN;
    }

    public Vertice3D getV1() {
        return v1;
    }

    public void setV1(Vertice3D v1) {
        this.v1 = v1;
    }

    public Vertice3D getV2() {
        return v2;
    }

    public void setV2(Vertice3D v2) {
        this.v2 = v2;
    }

    public Vertice3D getV3() {
        return v3;
    }

    public void setV3(Vertice3D v3) {
        this.v3 = v3;
    }

    public Vertice3D getV4() {
        return v4;
    }

    public void setV4(Vertice3D v4) {
        this.v4 = v4;
    }

    public Face3D getFace() {
        return face;
    }

    public Face3D getFace2() {
        return face2;
    }

    public boolean isDuasFaces() {
        return duasFaces;
    }

    public void setDuasFaces(boolean duasFaces) {
        this.duasFaces = duasFaces;
    }
    
}
