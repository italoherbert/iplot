package italo.iplot.plot3d.g3d.vert;

import italo.iplot.plot3d.g3d.Vertice3D;

public interface FiltroVert3D {
    
    public double getX(Vertice3D v);
    
    public double getY(Vertice3D v);
    
    public double getZ(Vertice3D v);
    
    public void setX(Vertice3D v, double x);
    
    public void setY(Vertice3D v, double y);
    
    public void setZ(Vertice3D v, double z);
    
    public double[] getPonto3D(Vertice3D v);
    
    public void setPonto3D(Vertice3D v, double[] p);
    
}
