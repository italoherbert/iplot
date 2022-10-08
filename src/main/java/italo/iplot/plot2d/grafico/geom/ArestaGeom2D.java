package italo.iplot.plot2d.grafico.geom;

import italo.iplot.gui.grafico.ArestaGeom;
import italo.iplot.plot2d.g2d.Aresta2D;

public class ArestaGeom2D  extends Geom2D implements ArestaGeom {
    
    private Aresta2D aresta;
    
    public ArestaGeom2D( Aresta2D aresta, Geom2DTO geomTO ) {
        super( geomTO );
        this.aresta = aresta;
    }
    
    @Override
    public int[] calculaP0() {
        double x = geomTO.getVisaoFiltroV2D().getX( aresta.getV1() );
        double y = geomTO.getVisaoFiltroV2D().getY( aresta.getV1() );
        return geomTO.getMath2D().pontoPX( x, y, geomTO.getTela() );
    }
    
    @Override
    public int[] calculaP1() {
        double x = geomTO.getVisaoFiltroV2D().getX( aresta.getV2() );
        double y = geomTO.getVisaoFiltroV2D().getY( aresta.getV2() );
        return geomTO.getMath2D().pontoPX( x, y, geomTO.getTela() );
    }

    public Aresta2D getAresta() {
        return aresta;
    }

    public void setAresta(Aresta2D aresta) {
        this.aresta = aresta;
    }
    
}

