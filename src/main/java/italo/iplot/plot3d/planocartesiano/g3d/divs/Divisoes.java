
package italo.iplot.plot3d.planocartesiano.g3d.divs;

import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.planocartesiano.regua.Regua;
import italo.iplot.planocartesiano.regua.ReguaUtil;

public interface Divisoes extends Regua {
        
    public double getIDesloc();
        
    public Face3D getFace();
        
    public double calculaH( ReguaUtil rutil, int i );
    
    public abstract double[][] calculaExtremidades( double h );
    
}
