package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class T1T2F2F1XDivisoes extends XDivisoes {

    public T1T2F2F1XDivisoes(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }

    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceT1T2F2F1();
    }       
            
    @Override
    public double[][] calculaExtremidades( double h ) {        
        double[] v = planoCartesianoObj3D.getTrazV1().getP();
        return new double[][] {
            { h,  v[1], -v[2] },
            { h,  v[1],  v[2] }
        };
    }
    
}
