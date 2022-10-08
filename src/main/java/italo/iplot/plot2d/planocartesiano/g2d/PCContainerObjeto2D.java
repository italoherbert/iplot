package italo.iplot.plot2d.planocartesiano.g2d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot2d.g2d.ContainerObjeto2D;

public interface PCContainerObjeto2D extends ContainerObjeto2D {

    public double getIX1();
    
    public double getIX2();
    
    public double getIXFator();
        
    public int getTelaDX();
        
    public void addLegenda( Legenda legenda );
            
}
