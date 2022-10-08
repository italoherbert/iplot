package italo.iplot.exemplos.plot3d.principal.gui.gaussiana;

import java.awt.Dimension;
import italo.iplot.gui.DesenhoUI;

public interface GaussianaUI {
    
    public Dimension addGaussianaUI( DesenhoUI ui );

    public void setGaussianaListener( GaussianaGUIListener listener );
    
    public int getNDivX();
    
    public int getNDivZ();
    
    public boolean isPintarVertices();
    
    public boolean isDesenharFaces();
    
    public boolean isPintarFaces();
    
    public double getX1();
    
    public double getX2();
    
    public double getZ1();
    
    public double getZ2();
    
    public double getFuncRaio();
    
    public double getFuncAltura();
    
    public void setNDivX( int nDivX );
    
    public void setNDivZ( int nDivZ );
    
    public void setPintarVertices( boolean verticesVisiveis );
    
    public void setDesenharFaces( boolean arestasVisiveis );
    
    public void setPintarFaces( boolean facesVisiveis );
    
    public void setX1( double x1 );
    
    public void setX2( double x2 );
    
    public void setZ1( double z1 );
    
    public void setZ2( double z2 );
    
    public void setFuncRaio( double raio );
    
    public void setFuncAltura( double altura );
    
}
