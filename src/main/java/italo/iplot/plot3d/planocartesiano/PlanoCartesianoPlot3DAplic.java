package italo.iplot.plot3d.planocartesiano;

import italo.iplot.plot3d.OperManager3D;
import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public interface PlanoCartesianoPlot3DAplic extends Plot3DAplic {
            
    public PlanoCartesianoObjeto3D getPlanoCartesiano();
                       
    public double getZoom();
               
    public OperManager3D getMoveManager();
    
}
