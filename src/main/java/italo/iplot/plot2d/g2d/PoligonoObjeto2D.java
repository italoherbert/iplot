package italo.iplot.plot2d.g2d;

import java.util.ArrayList;
import java.util.List;

public class PoligonoObjeto2D extends Objeto2D implements ComponenteObjeto2D {
    
    protected final List<double[]> pontos = new ArrayList();

    private ContainerObjeto2D container = null;
    
    @Override
    public void constroiObjeto2D(Objeto2DTO to) {
        int size = pontos.size();
        
        for( int i = 0; i < size; i++ ) {
            double[] p = pontos.get( i );
            super.getEstrutura().addVertice( new Vertice2D( p[0], p[1] ) ); 
        }

        Face2D f = new Face2D();
        for( int i = 0; i < size; i++ ) {
            Vertice2D v1 = super.getEstrutura().getVertices().get( i );
            Vertice2D v2 = super.getEstrutura().getVertices().get( i == size-1 ? 0 : i+1 );
            
            f.addVertice( v1 ); 
            super.getEstrutura().addAresta( new Aresta2D( v1, v2 ) ); 
        }
        
        super.getEstrutura().addFace( f ); 
    }
    
    
    @Override
    public ComponenteObjeto2DLimite calculaLimites() {
        if ( pontos.isEmpty() )
            return new ComponenteObjeto2DLimite();
        
        double x1 = Double.MAX_VALUE;
        double y1 = Double.MAX_VALUE;
        double x2 = Double.MIN_VALUE;
        double y2 = Double.MIN_VALUE;
        for( double[] v : pontos ) {
            if ( v[0] < x1 )
                x1 = v[0];
            if ( v[0] > x2 )
                x2 = v[0];
            if ( v[1] < y1 )
                y1 = v[1];
            if ( v[1] > y2 )
                y2 = v[1];
        }
        
        return new ComponenteObjeto2DLimite( x1, x2, y1, y2 );
    }

    @Override
    public void escalar( double escala, Objeto2DTO to ) { 
        to.getTransformador2D().sub( estrutura.getVertices(), container.getX(), container.getY(), to.getXYFiltroV2D() );
        to.getTransformador2D().escala( this, escala, to.getXYFiltroV2D() );        
        to.getTransformador2D().soma( estrutura.getVertices(), container.getX(), container.getY(), to.getXYFiltroV2D() );        
    }
         
    public void addPonto( double x, double y ) {
        pontos.add( new double[] { x, y } );
    }
    
    public void removePontos() {
        pontos.clear();
    }

    public ContainerObjeto2D getContainerObjeto2D() {
        return container;
    }

    @Override
    public void setContainerObjeto2D(ContainerObjeto2D container) {
        this.container = container;
    }
    
}
