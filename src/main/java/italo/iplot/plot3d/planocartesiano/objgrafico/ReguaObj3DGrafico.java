package italo.iplot.plot3d.planocartesiano.objgrafico;

import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DGrafico;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.Regua3DTipo;
import italo.iplot.plot3d.planocartesiano.g3d.ReguaPlanoCartesianoObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3DGraficoDriver;

public class ReguaObj3DGrafico implements Objeto3DGrafico {
    
    @Override
    public void desenho( Grafico grafico, Objeto3D obj, Objeto3DGraficoDriver drv ) {
        ReguaPlanoCartesianoObjeto3D reguaObj = (ReguaPlanoCartesianoObjeto3D)obj;
        PlanoCartesianoObjeto3D planoObj = reguaObj.getPlanoObj();
        
        if ( planoObj.isPintarEixoRotulos() ) {
            grafico.setCor( planoObj.getEixoRotulosCor() );
            grafico.getGraphics().setFont( planoObj.getEixoRotuloFont() );
                                    
            String rotulo = reguaObj.getRotulo();
            if ( rotulo != null ) {                                                                
                double[] vp1 = reguaObj.getAresta().getV1().getVisaoP();
                double[] vp2 = reguaObj.getAresta().getV2().getVisaoP();
                
                int[] p1 = drv.getMath3D().pontoPX( vp1, drv.getTela() );
                int[] p2 = drv.getMath3D().pontoPX( vp2, drv.getTela() );
                
                grafico.desenhaLinha( p1[0], p1[1], p2[0], p2[1] ); 
                
                double a = Math.atan2( p2[1]-p1[1], p2[0]-p1[0] );
                
                if ( reguaObj.getReguaTipo() != Regua3DTipo.Y )
                    a += Math.PI;
                                            
                Vertice3D v = reguaObj.getEixoRotuloVertice();
                int[] xy = drv.getMath3D().pontoPX( v.getVisaoP(), drv.getTela() );
                grafico.desenhaTexto( rotulo, xy[0], xy[1], a );
            }
        }
    }
    
}
