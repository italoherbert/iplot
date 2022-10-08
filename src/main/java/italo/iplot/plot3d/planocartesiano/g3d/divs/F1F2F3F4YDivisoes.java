package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class F1F2F3F4YDivisoes extends YDivisoes {
    
    public F1F2F3F4YDivisoes(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }

    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceF1F2F3F4();
    }

    @Override
    public double[][] calculaExtremidades( double h ) {        
        double[] v = planoCartesianoObj3D.getFrenteV1().getP();
        return new double[][] {
            { -v[0], h, v[2] },
            {  v[0], h, v[2] }
        };
    }
    
}

