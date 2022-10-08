package italo.iplot.plot2d.g2d.util.corte.vertice;

import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import java.util.List;

public class FaceVerticesRemovedor implements VerticesRemovedor {
    
    private final Face2D face;
    private final Estrutura2D est;

    public FaceVerticesRemovedor( Estrutura2D est, Face2D face ) {
        this.est = est;
        this.face = face;
    }

    @Override
    public void remove( Vertice2D v ) {
        est.removeFaceVertice( face, v );
    }

    @Override
    public List<Vertice2D> getVertices() {
        return face.getVertices();
    }

}
