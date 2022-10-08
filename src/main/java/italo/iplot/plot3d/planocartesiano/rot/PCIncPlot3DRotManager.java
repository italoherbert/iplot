package italo.iplot.plot3d.planocartesiano.rot;

import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.Tela;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DAplic;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import italo.iplot.plot3d.OperManager3D;

public class PCIncPlot3DRotManager implements OperManager3D {

    @Override
    public void execute( Plot3DAplic aplic ) {
        PlanoCartesianoPlot3DAplic pcAplic = (PlanoCartesianoPlot3DAplic)aplic;
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        DesenhoUI ui = pcAplic.getDesenhoUI();
                
        ui.atualizaXYs();
        
        int x1 = ui.getX1();
        int y1 = ui.getY1();
        int x2 = ui.getX2();
        int y2 = ui.getY2();
        
        Tela tela = aplic.getTela();
        double meioH = aplic.getMath3D().getMeioH( tela );
                                                
        double xRot;
        double yRot;
                
        double xf = Math.abs( x2 - x1 ) / meioH;            
        if ( x2 >= x1 ) {               
            yRot = pc.getYRot() + ( xf * Math.PI );
        } else {
            yRot = pc.getYRot() - ( xf * Math.PI );
        }        
                        
        double yf = Math.abs( y2 - y1 ) / meioH;             
        if ( y2 >= y1 ) {
            if ( ( pc.getXRot() - (yf * Math.PI) ) > -Math.PI*.5d ) {
                xRot = pc.getXRot() - ( yf * Math.PI );
            } else {
                xRot = -Math.PI*.5d;
            }
        } else {
            if ( ( pc.getXRot() + ( yf * Math.PI ) ) < Math.PI*.5d ) { 
                xRot = pc.getXRot() + ( yf * Math.PI );
            } else {                
                xRot = Math.PI*.5d;
            }
        } 
                          
        pc.setXRot( xRot );
        pc.setYRot( yRot );
    }
    
}
