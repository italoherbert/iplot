package italo.iplot.plot3d.g3d.vert;

import italo.iplot.plot3d.g3d.Vertice3D;

public class InicialFiltroVert3D implements FiltroVert3D {
        
    @Override
    public double getX(Vertice3D v) {
        return v.getX0();
    }

    @Override
    public double getY(Vertice3D v) {
        return v.getY0();
    }
    
    @Override
    public double getZ(Vertice3D v) {
        return v.getZ0();
    }

    @Override
    public double[] getPonto3D(Vertice3D v) {
        return v.getP0();
    }

    @Override
    public void setX(Vertice3D v, double x0) {
        v.setX0( x0 );
    }

    @Override
    public void setY(Vertice3D v, double y0) {
        v.setY0( y0 );
    }

    @Override
    public void setZ(Vertice3D v, double z0) {
        v.setZ0( z0 );
    }

    @Override
    public void setPonto3D(Vertice3D v, double[] p0) {
        v.setP0( p0 ); 
    }
    
}
