package italo.iplot.plot3d.grafico.geom;

import italo.iplot.gui.grafico.ArestaGeom;
import italo.iplot.plot3d.g3d.Aresta3D;

public class ArestaGeom3D extends Geom3D implements ArestaGeom {
    
    private final Aresta3D aresta;
    private double fatorSomaNZ = 0;
    
    public ArestaGeom3D( Aresta3D aresta, Geom3DTO geomTO ) {
        super( geomTO );
        this.aresta = aresta;        
    }

    public double calculaZ( int x, int y ) {
        double[] p0 = aresta.getV1().getVisaoP();
        double[] p = aresta.getV2().getVisaoP();
                                        
        double[] v = geomTO.getMath3D().verticeNoPlano3D( x, y, geomTO.getTela() );
        return fatorSomaNZ - geomTO.getMath3D().calculaZ( v[0], v[1], p0, p );
    }
    
    @Override
    public int[] calculaP0() {
        double[] p0 = aresta.getV1().getVisaoP();        
        return geomTO.getMath3D().pontoPX( p0, geomTO.getTela() );
    }
    
    @Override
    public int[] calculaP1() {
        double[] p = aresta.getV2().getVisaoP();        
        return geomTO.getMath3D().pontoPX( p, geomTO.getTela() );
    }        

    public double getFatorSomaNZ() {
        return fatorSomaNZ;
    }

    public void setFatorSomaNZ(double fatorSomaNZ) {
        this.fatorSomaNZ = fatorSomaNZ;
    }
    
    public Aresta3D getAresta() {
        return aresta;
    }
    
}
