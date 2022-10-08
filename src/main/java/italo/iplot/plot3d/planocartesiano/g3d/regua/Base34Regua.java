package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class Base34Regua extends EixoXRegua {
    
    public Base34Regua(PlanoCartesianoObjeto3D plano) {
        super(plano);
    }

    @Override
    public double[][] calculaExtremidades(double[] v1, double x, double c) {
        return new double[][] {
            { x, v1[1], v1[2] },
            { x, v1[1], v1[2]+c }
        };
    }
        
    @Override
    public int getDirecao() {
        return -1;
    }
    
}