package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot3d.g3d.ContainerObjeto3D;

public interface PCContainerObjeto3D extends ContainerObjeto3D {
    
    public double getPCX();
    
    public double getPCY();
    
    public double getPCLargura();
        
    public double getPCAltura();
    
    public void addLegenda( Legenda legenda );
    
}
