package italo.iplot.plot2d.g2d;

import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;

public class RetanguloObjeto2D extends PoligonoObjeto2D implements CorteContainerObjeto2D {

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
        
    public RetanguloObjeto2D( double x1, double y1, double x2, double y2 ) {
        this.setPontos( x1, y1, x2, y2 );
    }
    
    public final void setPontos( double x1, double y1, double x2, double y2 ) {
        synchronized( super.pontos ) {
            super.pontos.clear();
            super.addPonto( x1, y1 ); 
            super.addPonto( x2, y1 );
            super.addPonto( x2, y2 );
            super.addPonto( x1, y2 );
        }
        
        dx = Math.abs( x2 - x1 );
        dy = Math.abs( y2 - y1 );
        x = x1 + dx*.5d;
        y = y1 + dy*.5d;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getDX() {
        return dx;
    }

    @Override
    public double getDY() {
        return dy;
    }
  
    public Vertice2D getV1() {
        if ( super.construido )
            return super.getEstrutura().getVertices().get( 0 );
        return null;
    }
    
    public Vertice2D getV2() {
        if ( super.construido )
            return super.getEstrutura().getVertices().get( 1 );
        return null;
    }
    
    public Vertice2D getV3() {
        if ( super.construido )
            return super.getEstrutura().getVertices().get( 2 );
        return null;
    }
    
    public Vertice2D getV4() {
        if ( super.construido )
            return super.getEstrutura().getVertices().get( 3 );
        return null;
    }
  
}
