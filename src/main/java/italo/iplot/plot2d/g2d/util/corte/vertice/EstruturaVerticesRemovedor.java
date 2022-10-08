package italo.iplot.plot2d.g2d.util.corte.vertice;

import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import java.util.List;

public class EstruturaVerticesRemovedor implements VerticesRemovedor {
    
    private final Estrutura2D est;
        
    public EstruturaVerticesRemovedor( Estrutura2D est ) {
        this.est = est;
    }

    @Override
    public void remove( Vertice2D v ) {
        est.removeEstruturaVertice( v ); 
    }

    @Override
    public List<Vertice2D> getVertices() {
        return est.getVertices();
    }
    
}
