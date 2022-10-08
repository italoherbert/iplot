package italo.iplot.plot2d.g2d.util.corte.aresta;

import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;
import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.util.corte.cond.TrazFrenteCorteCondicao;

public class FrenteArestaCortador2D extends AbstractArestaCortador2D {

    private final CorteCondicao cond = new TrazFrenteCorteCondicao();
    
    @Override
    public boolean condicaoCorte( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        double x = this.getX( container );
        return cond.condicao( x1, y1, x2, y2, x, 0 );
    }

    @Override
    public double getX( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        return this.getX( container );
    }

    @Override
    public double getY( CorteContainerObjeto2D container, double x1, double y1, double x2, double y2 ) {
        double x = this.getX( container );
        return retaUtil.calcY( x1, y1, x2, y2, x );
    }

    @Override
    public boolean condicaoAddV1OuV2( double x1, double y1, double x, double y ) {
        return x1 < x;
    }
        
    private double getX( CorteContainerObjeto2D container ) {
        return container.getX() + container.getDX()*.5d;
    }
    
}



