package italo.iplot.plot2d.g2d.util.corte.aresta;

import italo.iplot.plot2d.g2d.Aresta2D;
import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.util.corte.util.RetaUtil;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;

public abstract class AbstractArestaCortador2D {   
    
    protected final RetaUtil retaUtil = new RetaUtil();
    
    public abstract boolean condicaoCorte( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
    
    public abstract double getX( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
    
    public abstract double getY( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 );
    
    public abstract boolean condicaoAddV1OuV2( double x1, double y1, double x, double y );
    
    public void corte( CorteContainerObjeto2D container, Estrutura2D est, Aresta2D aresta, FiltroVert2D filtro ) {        
        Vertice2D v1 = aresta.getV1();
        Vertice2D v2 = aresta.getV2();
        double x1 = filtro.getX( v1 );
        double x2 = filtro.getX( v2 );
        double y1 = filtro.getY( v1 );
        double y2 = filtro.getY( v2 );

        if ( this.condicaoCorte( container, x1, y1, x2, y2 ) ) {
            double x = this.getX( container, x1, y1, x2, y2 );
            double y = this.getY( container, x1, y1, x2, y2 );
            Vertice2D v = new Vertice2D( x, y );
            if ( this.condicaoAddV1OuV2( x1, y1, x, y ) ) {
                Aresta2D a = new Aresta2D( v, v1 );
                est.copiaConfig( a, aresta );
                est.addAresta( a );
            } else {
                Aresta2D a = new Aresta2D( v2, v );
                est.copiaConfig( a, aresta );
                est.addAresta( a );
            }
            est.removeEstruturaAresta( aresta );
        }        
    }    
    
}
