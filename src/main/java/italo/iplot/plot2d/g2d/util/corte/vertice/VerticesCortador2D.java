package italo.iplot.plot2d.g2d.util.corte.vertice;

import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;
import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;

public class VerticesCortador2D {
    
    public void corte( CorteContainerObjeto2D container, VerticesRemovedor vertsRemovedor, CorteCondicao cond, FiltroVert2D filtro ) {        
        double x1 = container.getX() - container.getDX()*.5d;
        double y1 = container.getY() - container.getDY()*.5d;
        double x2 = container.getX() + container.getDX()*.5d;
        double y2 = container.getY() + container.getDY()*.5d;
                
        int size = vertsRemovedor.getVertices().size();
        for( int i = 0; i < size; i++ ) {
            Vertice2D v = vertsRemovedor.getVertices().get( i );
            double x = filtro.getX( v );
            double y = filtro.getY( v );
            if ( cond.condicao( x1, y1, x2, y2, x, y ) ) {                
                vertsRemovedor.remove( v );                
                i--;
                size--;
            }
        }        
    }
    
}
