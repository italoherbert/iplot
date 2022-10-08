package italo.iplot.plot3d.planocartesiano.g3d.regua;

import italo.iplot.planocartesiano.regua.Regua;

public interface Regua3D extends Regua {
    
    public double getDesloc();
            
    public double[][] calculaExtremidades( double[] v1, double h, double c );
    
    public int getDirecao();
    
}
