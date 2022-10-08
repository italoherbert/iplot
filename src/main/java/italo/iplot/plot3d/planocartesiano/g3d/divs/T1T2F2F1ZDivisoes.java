package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class T1T2F2F1ZDivisoes extends ZDivisoes {
    
    public T1T2F2F1ZDivisoes(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }

    @Override
    public Face3D getFace() {
        return planoCartesianoObj3D.getFaceT1T2F2F1();
    }       
            
    @Override
    public double[][] calculaExtremidades( double h ) {        
        double[] v = planoCartesianoObj3D.getTrazV1().getP();
        double dn = this.getDN();
        return new double[][] {
            { -v[0],  v[1], h },
            {  v[0] ,  v[1], h }
        };
    }
    
}
