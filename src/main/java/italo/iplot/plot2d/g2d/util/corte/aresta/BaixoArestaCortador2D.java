package italo.iplot.plot2d.g2d.util.corte.aresta;

import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;
import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.util.corte.cond.BaixoCimaCorteCondicao;

public class BaixoArestaCortador2D extends AbstractArestaCortador2D {

    private final CorteCondicao cond = new BaixoCimaCorteCondicao();
    
    @Override
    public boolean condicaoCorte( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        double y = this.getY( container );
        return cond.condicao( x1, y1, x2, y2, 0, y );
    }

    @Override
    public double getX( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        double y = this.getY( container );
        return retaUtil.calcX( x1, y1, x2, y2, y );
    }

    @Override
    public double getY( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        return this.getY( container );
    }

    @Override
    public boolean condicaoAddV1OuV2( double x1, double y1, double x, double y ) {
        return y1 > y;
    }
        
    private double getY( CorteContainerObjeto2D container ) {
        return container.getY() - container.getDY()*.5d;
    }
    
}

