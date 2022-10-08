package italo.iplot.plot3d.rot;

import italo.iplot.plot3d.OperManager3D;
import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.Tela;

public class IncPlot3DRotManager implements OperManager3D {

    @Override
    public void execute( Plot3DAplic aplic ) {
        DesenhoUI ui = aplic.getDesenhoUI();
                
        ui.atualizaXYs();
        
        int x1 = ui.getX1();
        int y1 = ui.getY1();
        int x2 = ui.getX2();
        int y2 = ui.getY2();
        
        Tela tela = aplic.getTela();
        double meioH = aplic.getMath3D().getMeioH( tela );
                         
        UniversoVirtual3D uv = aplic.getUniversoVirtual();
                       
        double xRot;
        double yRot;
                
        double xf = Math.abs( x2 - x1 ) / meioH;            
        if ( x2 >= x1 ) {               
            yRot = uv.getUVYRot() + ( xf * Math.PI );
        } else {
            yRot = uv.getUVYRot() - ( xf * Math.PI );
        }        
                        
        double yf = Math.abs( y2 - y1 ) / meioH;             
        if ( y2 >= y1 ) {
            xRot = uv.getUVXRot() - ( yf * Math.PI );           
        } else {
            xRot = uv.getUVXRot() + ( yf * Math.PI );           
        } 
                                
        uv.setUVXRot( xRot );
        uv.setUVYRot( yRot );
    }
    
}
