package italo.iplot.plot3d.g3d.vert;

import italo.iplot.plot3d.g3d.Vertice3D;

public class VisaoFiltroVert3D implements FiltroVert3D {

    @Override
    public double getX(Vertice3D v) {
        return v.getVisaoX();
    }

    @Override
    public double getY(Vertice3D v) {
        return v.getVisaoY();
    }    

    @Override
    public double getZ(Vertice3D v) {
        return v.getVisaoZ();
    }

    @Override
    public double[] getPonto3D(Vertice3D v) {
        return v.getVisaoP();
    }

    @Override
    public void setX(Vertice3D v, double x) {
        v.setVisaoX( x );
    }

    @Override
    public void setY(Vertice3D v, double y) {
        v.setVisaoY( y );
    }

    @Override
    public void setZ(Vertice3D v, double z) {
        v.setVisaoZ( z );
    }

    @Override
    public void setPonto3D(Vertice3D v, double[] p) {
        v.setVisaoP( p ); 
    }
    
}
