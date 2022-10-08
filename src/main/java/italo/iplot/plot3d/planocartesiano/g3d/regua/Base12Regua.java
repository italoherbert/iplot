
package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class Base12Regua extends EixoXRegua {
    
    public Base12Regua(PlanoCartesianoObjeto3D plano) {
        super(plano);
    }

    @Override
    public double[][] calculaExtremidades(double[] v1, double a, double c) {
        return new double[][] {
            { a, v1[1], v1[2] },
            { a, v1[1], v1[2]-c }
        };
    }

    @Override
    public int getDirecao() {
        return 1;
    }
    
    
    
}