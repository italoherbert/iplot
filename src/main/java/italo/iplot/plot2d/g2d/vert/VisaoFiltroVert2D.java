package italo.iplot.plot2d.g2d.vert;

import italo.iplot.plot2d.g2d.Vertice2D;

public class VisaoFiltroVert2D implements FiltroVert2D {

    @Override
    public double getX( Vertice2D v ) {
        return v.getVisaoX();
    }

    @Override
    public double getY(Vertice2D v) {
        return v.getVisaoY();
    }

    @Override
    public void setX(Vertice2D v, double x) {
        v.setVisaoX( x );
    }

    @Override
    public void setY(Vertice2D v, double y) {
        v.setVisaoY( y ); 
    }

    @Override
    public double[] getPonto2D(Vertice2D v) {
        return v.getVisaoP();
    }

    @Override
    public void setPonto2D(Vertice2D v, double[] p) {
        v.setVisaoP( p ); 
    }

}
