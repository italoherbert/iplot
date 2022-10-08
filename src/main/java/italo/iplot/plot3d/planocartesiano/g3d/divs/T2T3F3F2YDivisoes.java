package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class T2T3F3F2YDivisoes extends YDivisoes {
   
    public T2T3F3F2YDivisoes(PlanoCartesianoObjeto3D plano) {
        super(plano);
    }
    
    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceT2T3F3F2();
    }
    
    @Override
    public double[][] calculaExtremidades(double h) {
        double[] v = planoCartesianoObj3D.getTrazV2().getP();
        return new double[][] {
            { v[0], h, -v[2] },
            { v[0], h,  v[2] }
        };
    }
    
}
