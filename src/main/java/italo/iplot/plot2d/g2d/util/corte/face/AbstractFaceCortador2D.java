package italo.iplot.plot2d.g2d.util.corte.face;

import italo.iplot.plot2d.g2d.util.corte.vertice.VerticesCortador2D;
import italo.iplot.plot2d.g2d.util.corte.vertice.VerticesRemovedor;
import italo.iplot.plot2d.g2d.util.corte.vertice.FaceVerticesRemovedor;
import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.util.corte.util.RetaUtil;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;
import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;

public abstract class AbstractFaceCortador2D {
    
    private final VerticesCortador2D vertsCortador = new VerticesCortador2D();
    protected final RetaUtil retaUtil = new RetaUtil();
    
    public abstract boolean condicaoCorte( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
    
    public abstract double getX( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
    
    public abstract double getY( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
            
    public abstract CorteCondicao getVRCondicao();
    
    public void corte( CorteContainerObjeto2D container, Estrutura2D est, Face2D face, FiltroVert2D filtro ) {        
        VerticesRemovedor vertsRemovedor = new FaceVerticesRemovedor( est, face );
        
        int size = face.getVertices().size();
        for( int i = 0; i < size; i++ ) {
            Vertice2D v1 = face.getVertices().get( i );
            Vertice2D v2 = face.getVertices().get( i == size-1 ? 0 : i+1 ); 
            double x1 = filtro.getX( v1 );
            double x2 = filtro.getX( v2 );
            double y1 = filtro.getY( v1 );
            double y2 = filtro.getY( v2 );

            if ( this.condicaoCorte( container, x1, y1, x2, y2 ) ) {
                double x = this.getX( container, x1, y1, x2, y2 );
                double y = this.getY( container, x1, y1, x2, y2 );
                Vertice2D v = new Vertice2D( x, y );
                est.insereVertice( face, v1, v2, v );
                i--;
                size++;
            }
        }        
        
        CorteCondicao cond = this.getVRCondicao();
        vertsCortador.corte( container, vertsRemovedor, cond, filtro );
    }
            
}
