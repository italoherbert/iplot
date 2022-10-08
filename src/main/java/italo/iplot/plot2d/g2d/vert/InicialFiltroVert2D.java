package italo.iplot.plot2d.g2d.vert;

import italo.iplot.plot2d.g2d.Vertice2D;

public class InicialFiltroVert2D implements FiltroVert2D {
    
    @Override
    public double getX( Vertice2D v ) {
        return v.getX0();
    }

    @Override
    public double getY(Vertice2D v) {
        return v.getY0();
    }

    @Override
    public void setX(Vertice2D v, double x) {
        v.setX0( x );
    }

    @Override
    public void setY(Vertice2D v, double y) {
        v.setY0( y ); 
    }

    @Override
    public double[] getPonto2D(Vertice2D v) {
        return v.getP0();
    }

    @Override
    public void setPonto2D(Vertice2D v, double[] p) {
        v.setP0( p ); 
    }
    
}
