package italo.iplot.plot2d.planocartesiano.objgrafico.pcrect;

import italo.iplot.plot2d.g2d.ContainerObjeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCContainerObjeto2D;

public class PCRect {
    
    public int[] calculaP0( PCContainerObjeto2D container, Objeto2DGraficoDriver drv ) {                                
        double pcX1 = container.getX() - container.getDX()*.5d;
        double pcY1 = container.getY() + container.getDY()*.5d;
        
        return drv.getMath2D().pontoPX( pcX1, pcY1, drv.getTela() );
    }
    
    public int[] calculaP1( ContainerObjeto2D container, Objeto2DGraficoDriver drv ) {                               
        double pcX2 = container.getX() + container.getDX()*.5d;
        double pcY2 = container.getY() - container.getDY()*.5d;
        
        return drv.getMath2D().pontoPX( pcX2, pcY2, drv.getTela() );
    }
    
}
