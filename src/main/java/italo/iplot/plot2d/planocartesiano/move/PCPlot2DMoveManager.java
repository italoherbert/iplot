package italo.iplot.plot2d.planocartesiano.move;

import italo.iplot.plot2d.OperManager2D;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DAplic;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;

public class PCPlot2DMoveManager implements OperManager2D {
    
    @Override
    public void execute(PlanoCartesianoPlot2DAplic aplic) {
        DesenhoUI ui = aplic.getDesenhoUI();
                
        ui.atualizaXYs();
        
        int x1 = ui.getX1();
        int y1 = ui.getY1();
        int x2 = ui.getX2();
        int y2 = ui.getY2();

        int deslocX = x2 - x1;                          
        int deslocY = y2 - y1;             
        
        double xh = aplic.getMath2D().verticeUnidade( deslocX, aplic.getTela() );
        double yh = aplic.getMath2D().verticeUnidade( -deslocY, aplic.getTela() );
        
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();
        pc.mover( xh, yh, aplic );
    }
    
}
