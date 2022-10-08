package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class Y4Regua extends EixoYRegua {
        
    public Y4Regua(PlanoCartesianoObjeto3D plano) {
        super( plano );
    }
    
    @Override
    public double[][] calculaExtremidades(double[] v1, double y, double c) {
        return new double[][] {
            { v1[0]  , y, v1[2] },
            { v1[0]-c, y, v1[2] }
        };        
    }
    
    
}