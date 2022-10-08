package italo.iplot.plot2d.planocartesiano;

import italo.iplot.plot2d.Plot2DAplic;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import italo.iplot.plot2d.OperManager2D;

public interface PlanoCartesianoPlot2DAplic extends Plot2DAplic {
    
    public PlanoCartesianoObjeto2D getPlanoCartesiano();
        
    public double getZoom();
    
    public OperManager2D getMoveManager();
    
}
