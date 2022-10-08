package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class Base41Regua extends EixoZRegua {
    
    public Base41Regua(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }
    
    @Override
    public double[][] calculaExtremidades(double[] v1, double z, double c) {
        return new double[][] {
            { v1[0]  , v1[1], z},
            { v1[0]-c, v1[1], z }
        };
    }

    @Override
    public int getDirecao() {
        return -1;
    }
    
}