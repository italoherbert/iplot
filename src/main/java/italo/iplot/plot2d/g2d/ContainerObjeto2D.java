package italo.iplot.plot2d.g2d;

import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;

public interface ContainerObjeto2D extends CorteContainerObjeto2D {
    
    public double calculaX( double x );
    
    public double calculaY( double y );
            
    public boolean isCortar();
        
}
