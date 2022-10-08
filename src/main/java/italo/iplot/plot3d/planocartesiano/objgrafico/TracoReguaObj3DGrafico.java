package italo.iplot.plot3d.planocartesiano.objgrafico;

import java.awt.geom.Rectangle2D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DGrafico;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.planocartesiano.g3d.Regua3DTipo;
import italo.iplot.plot3d.planocartesiano.g3d.TracoRequaPlanoCartesianoObjeto3D;
import italo.iplot.gui.grafico.Grafico;
import java.awt.Font;
import italo.iplot.plot3d.g3d.Objeto3DGraficoDriver;

public class TracoReguaObj3DGrafico implements Objeto3DGrafico {
    
    @Override
    public void desenho( Grafico grafico, Objeto3D obj, Objeto3DGraficoDriver drv ) {
        TracoRequaPlanoCartesianoObjeto3D t = (TracoRequaPlanoCartesianoObjeto3D)obj;
                        
        Vertice3D v = t.getRotuloVertice();
        double[] vp = v.getVisaoP().clone();
                
        int[] xy = drv.getMath3D().pontoPX( vp, drv.getTela() );
        int x = xy[0];
        int y = xy[1];
        
        String rotulo = drv.getRotuloDecimalFormat().format( t.getValor() );

        Font reguaValoresFont = t.getReguaObj().getPlanoObj().getReguaValoresFont();
        Rectangle2D rotuloRT = grafico.stringLimites( rotulo, reguaValoresFont );
                
        if ( t.getReguaObj().getReguaTipo() == Regua3DTipo.Y ) {
            x -= ( rotuloRT.getMaxX()-rotuloRT.getMinX() );
            y += ( rotuloRT.getMaxY()-rotuloRT.getMinY() )*.5d;
        } else {        
            x -= ( rotuloRT.getMaxX()-rotuloRT.getMinX() );
            y += ( rotuloRT.getMaxY()-rotuloRT.getMinY() )*.5d;            
        }
               
        grafico.getGraphics().setFont( reguaValoresFont ); 
        grafico.desenhaTexto( rotulo, x, y );
    }
    
}
