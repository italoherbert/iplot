package italo.iplot.plot3d.g3d;

import java.util.ArrayList;
import java.util.List;
import italo.iplot.funcs.g3d.Func3D;

public class FuncObjeto3D extends Objeto3D implements GrelhaObjeto3D {

    private int divX = 20;
    private int divZ = 20;
    
    private double dx = 1.0d;
    private double dz = 1.0d;
       
    private Func3D func3D;
    
    private final Grelha grelha = new Grelha();
    
    @Override
    public void constroiObjeto3D(Objeto3DTO to) {
        double retx = dx / divX;
        double retz = dz / divZ;
        
        List<Vertice3D> paraRemover = new ArrayList();
        
        for( int i = 0; i <= divX; i++ ) {
            for( int j = 0; j <= divZ; j++ ) {        
                double x = -dx*.5d + ( i * retx );
                double z = -dz*.5d + ( j * retz );
                double y = 0;
                
                boolean remover = false;
                
                if ( func3D != null ) {
                    try {
                        y = func3D.getY( x, z ); 
                        
                        if ( y == Double.NaN || y == Double.NEGATIVE_INFINITY || y == Double.POSITIVE_INFINITY ) {
                            remover = true;
                        }
                    } catch ( ArithmeticException e ) {
                        remover = true;
                    }
                }
                         
                Vertice3D v = new Vertice3D( x, y, z );
                
                super.getEstrutura().addVertice( v ); 
                
                if ( remover )
                    paraRemover.add( v );
            }
        }
        
        grelha.constroiFaces( this, to );
        
        paraRemover.forEach( v -> {
            super.getEstrutura().removeEstruturaVertice( v );            
        } );
    }

    @Override
    public List<Vertice3D> getVertices() {
        return super.getEstrutura().getVertices();
    }

    @Override
    public void addFace(Face3D face, Objeto3DTO to ) {
        super.getEstrutura().addFace( face, to ); 
    }

    @Override
    public int getXNDivs() {
        return divX;
    }

    public void setDivX(int divX) {
        this.divX = divX;
    }

    public int getZNDivs() {
        return divZ;
    }

    public void setDivZ(int divZ) {
        this.divZ = divZ;
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

    public Func3D getFunc3D() {
        return func3D;
    }

    public void setFunc3D(Func3D func3D) {
        this.func3D = func3D;
    }
    
}
