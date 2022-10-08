package italo.iplot.plot3d.grafico.desenho;

import italo.iplot.gui.grafico.GraficoPixel;
import italo.iplot.plot3d.grafico.geom.ArestaGeom3D;
import italo.iplot.plot3d.grafico.geom.FaceGeom3D;

public interface Pixel3D extends GraficoPixel {
    
    public void pintaArestaPixel( ArestaGeom3D geom, int x, int y, double nz, int rgb );
    
    public void pintaFacePixel( FaceGeom3D geom, int x, int y, double nz, int rgb );
    
}
