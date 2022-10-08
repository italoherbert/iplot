package italo.iplot.plot2d.grafico.geom;

import italo.iplot.gui.grafico.FaceGeom;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import java.util.List;

public class FaceGeom2D extends Geom2D implements FaceGeom {
    
    private final Face2D face;
    
    public FaceGeom2D( Face2D face, Geom2DTO geomTO ) {
        super( geomTO );
        this.face = face;
    }
            
    public List<Vertice2D> getVertices() {
        return face.getVertices();
    }
    
    @Override
    public int[][] pontosPX() {
        int len = face.getVertices().size();
        
        int[] vetorTX = new int[ len ];
        int[] vetorTY = new int[ len ];
        int i = 0;   

        for( Vertice2D v : face.getVertices() ) {  
            double x = geomTO.getVisaoFiltroV2D().getX( v );
            double y = geomTO.getVisaoFiltroV2D().getY( v );
            int[] xy = geomTO.getMath2D().pontoPX( x, y, geomTO.getTela() );

            vetorTX[ i ] = xy[0];
            vetorTY[ i ] = xy[1];                                        
            i++;
        }   
        return new int[][] { vetorTX, vetorTY };
    }
    
}

