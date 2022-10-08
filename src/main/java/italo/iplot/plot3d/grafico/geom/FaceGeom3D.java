package italo.iplot.plot3d.grafico.geom;

import italo.iplot.gui.grafico.FaceGeom;
import java.util.List;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Vertice3D;

public class FaceGeom3D extends Geom3D implements FaceGeom {
    
    private final Face3D face;
    
    public FaceGeom3D(Face3D face, Geom3DTO geomTO) {
        super( geomTO );
        this.face = face;
    }
        
    public double[][] vertPontos() {
        Vertice3D[] verts = geomTO.getMath3D().verticesNaoColineares( face.getVertices(), geomTO.getVisaoFiltroV3D() );
        return new double[][] {
            geomTO.getVisaoFiltroV3D().getPonto3D( verts[0] ),
            geomTO.getVisaoFiltroV3D().getPonto3D( verts[1] ),
            geomTO.getVisaoFiltroV3D().getPonto3D( verts[2] ),            
        };
    }
        
    public double calculaZ( int x, int y, double[] p0, double[] p1, double[] p2 ) {        
        double[] v = geomTO.getMath3D().verticeNoPlano3D( x, y, geomTO.getTela() );
        return - geomTO.getMath3D().calculaZ( v[0], v[1], p0, p1, p2 );
    }
    
    public List<Vertice3D> getVertices() {
        return face.getVertices();
    }
    
    @Override
    public int[][] pontosPX() {
        int len = face.getVertices().size();
        
        int[] vetorTX = new int[ len ];
        int[] vetorTY = new int[ len ];
        int i = 0;   
        
        for( Vertice3D v : face.getVertices() ) {  
            double[] p = geomTO.getVisaoFiltroV3D().getPonto3D( v );
            int[] xy = geomTO.getMath3D().pontoPX( p, geomTO.getTela() );

            vetorTX[ i ] = xy[0];
            vetorTY[ i ] = xy[1];                                        
            i++;            
        }   
                
        return new int[][] { vetorTX, vetorTY };
    }
    
    public Face3D getFace() {
        return face;
    }
    
}
