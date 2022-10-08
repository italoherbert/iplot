package italo.iplot.plot2d.g2d;

public class LinhaObjeto2D extends Objeto2D {
    
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    protected Aresta2D aresta;
        
    public LinhaObjeto2D( double x1, double y1, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        super.pintarArestas = true;
    }
    
    @Override
    public void constroiObjeto2D( Objeto2DTO to ) {        
        Vertice2D v1 = new Vertice2D( x1, y1 );
        Vertice2D v2 = new Vertice2D( x2, y2 );
        
        aresta = new Aresta2D( v1, v2 );
        
        super.getEstrutura().addVertice( v1 );
        super.getEstrutura().addVertice( v2 ); 
        
        super.getEstrutura().addAresta( aresta ); 
    }
    
    public Aresta2D getAresta() {
        return aresta;
    }
  
}
