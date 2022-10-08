package italo.iplot.plot3d.g3d.vert;

import italo.iplot.plot3d.g3d.Vertice3D;

public class XYZFiltroVert3D implements FiltroVert3D {
        
    @Override
    public double getX(Vertice3D v) {
        return v.getX();
    }

    @Override
    public double getY(Vertice3D v) {
        return v.getY();
    }
    
    @Override
    public double getZ(Vertice3D v) {
        return v.getZ();
    }

    @Override
    public double[] getPonto3D(Vertice3D v) {
        return v.getP();
    }

    @Override
    public void setX(Vertice3D v, double x0) {
        v.setX( x0 );
    }

    @Override
    public void setY(Vertice3D v, double y0) {
        v.setY( y0 );
    }

    @Override
    public void setZ(Vertice3D v, double z0) {
        v.setZ( z0 );
    }

    @Override
    public void setPonto3D(Vertice3D v, double[] p0) {
        v.setP( p0 ); 
    }
    
}
