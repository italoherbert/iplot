package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class T4T3F3F4XDivisoes extends XDivisoes {
    
    public T4T3F3F4XDivisoes(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }

    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceT4T3F3F4();
    }       
            
    @Override
    public double[][] calculaExtremidades( double h ) {        
        double[] v = planoCartesianoObj3D.getTrazV4().getP();
        return new double[][] {
            { h,  v[1], -v[2] },
            { h,  v[1],  v[2] }
        };
    }
    
}
