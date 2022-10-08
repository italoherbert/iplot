package italo.iplot.plot2d.g2d.util.corte.face;

import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import italo.iplot.plot2d.g2d.util.corte.CorteCondicao;
import italo.iplot.plot2d.g2d.util.corte.cond.TrazFrenteCorteCondicao;
import italo.iplot.plot2d.g2d.util.corte.vertice.FrenteVRCondicao;

public class FrenteFaceCortador2D extends AbstractFaceCortador2D {
                    
    private final CorteCondicao cond = new TrazFrenteCorteCondicao();    
    private final CorteCondicao vrcond = new FrenteVRCondicao();
    
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
    public CorteCondicao getVRCondicao() {
        return vrcond;
    }

    private double getX( CorteContainerObjeto2D container ) {
        return container.getX() + container.getDX()*.5d;
    }
        
}

