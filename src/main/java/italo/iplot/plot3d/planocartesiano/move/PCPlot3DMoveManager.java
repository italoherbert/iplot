package italo.iplot.plot3d.planocartesiano.move;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.plot3d.OperManager3D;
import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DAplic;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class PCPlot3DMoveManager implements OperManager3D {
    
    @Override
    public void execute(Plot3DAplic aplic) {
        DesenhoUI ui = aplic.getDesenhoUI();
                
        ui.atualizaXYs();
        
        int x1 = ui.getX1();
        int y1 = ui.getY1();
        int x2 = ui.getX2();
        int y2 = ui.getY2();

        int deslocX = x2 - x1;                          
        int deslocY = y2 - y1;             
        
        double xh = aplic.getMath3D().verticeUnidade( -deslocY, aplic.getTela() );
        double yh = aplic.getMath3D().verticeUnidade( deslocX, aplic.getTela() );
        
        PlanoCartesianoObjeto3D pc = ((PlanoCartesianoPlot3DAplic)aplic).getPlanoCartesiano();
        pc.mover( xh, yh, aplic );
    }
    
}
