package italo.iplot.plot2d.g2d.vert;

import italo.iplot.plot2d.g2d.Vertice2D;

public interface FiltroVert2D {
    
    public double getX( Vertice2D v );
    
    public double getY( Vertice2D v );
        
    public void setX( Vertice2D v, double x );
    
    public void setY( Vertice2D v, double y );
        
    public double[] getPonto2D( Vertice2D v );
    
    public void setPonto2D( Vertice2D v, double[] p );
    
}
