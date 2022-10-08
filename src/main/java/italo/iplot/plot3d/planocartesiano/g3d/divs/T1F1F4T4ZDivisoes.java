package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class T1F1F4T4ZDivisoes extends ZDivisoes {

    public T1F1F4T4ZDivisoes(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }
    
    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceT1F1F4T4();
    }
    
    @Override
    public double[][] calculaExtremidades(double h) {
        double[] v = planoCartesianoObj3D.getTrazV1().getP(); 
        return new double[][] {
            { v[0], -v[1], h },
            { v[0],  v[1], h }
        };
    }
    
}
