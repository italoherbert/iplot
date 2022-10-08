package italo.iplot.plot2d.g2d.vert;

import italo.iplot.plot2d.g2d.Vertice2D;

public class XYFiltroVert2D implements FiltroVert2D {

    @Override
    public double getX( Vertice2D v ) {
        return v.getX();
    }

    @Override
    public double getY(Vertice2D v) {
        return v.getY();
    }

    @Override
    public void setX(Vertice2D v, double x) {
        v.setX( x );
    }

    @Override
    public void setY(Vertice2D v, double y) {
        v.setY( y ); 
    }

    @Override
    public double[] getPonto2D(Vertice2D v) {
        return v.getP();
    }

    @Override
    public void setPonto2D(Vertice2D v, double[] p) {
        v.setP( p ); 
    }
    
}
