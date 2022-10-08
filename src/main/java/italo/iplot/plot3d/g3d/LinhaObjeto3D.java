package italo.iplot.plot3d.g3d;

public class LinhaObjeto3D extends Objeto3D {
    
    protected double[] p1;
    protected double[] p2;
    protected Aresta3D aresta;
    protected boolean arestaPontilhada = false;
    protected int arestaPontosEspPX = 3;
        
    public LinhaObjeto3D( double[] p1, double[] p2 ) {
        this.p1 = p1;
        this.p2 = p2;
        super.pintarArestas = true;
    }
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {        
        Vertice3D v1 = new Vertice3D( p1 );
        Vertice3D v2 = new Vertice3D( p2 );
        
        aresta = new Aresta3D( this, v1, v2 );
        aresta.setArestaPontilhada( arestaPontilhada );
        aresta.setEspacoArestaPX( arestaPontosEspPX ); 
        
        super.getEstrutura().addVertice( v1 );
        super.getEstrutura().addVertice( v2 ); 
        
        super.getEstrutura().addOutrasAresta( aresta ); 
    }
    
    public Aresta3D getAresta() {
        return aresta;
    }
    
    public void pontilharArestas( int espPX ) {
        this.arestaPontosEspPX = espPX;
        this.arestaPontilhada = true;
    }

    public boolean isArestaPontilhada() {
        return arestaPontilhada;
    }

    public void setArestaPontilhada(boolean arestaPontilhada) {
        this.arestaPontilhada = arestaPontilhada;
    }

    public int getArestaPontosEspPX() {
        return arestaPontosEspPX;
    }

    public void setArestaPontosEspPX(int arestaPontosEspPX) {
        this.arestaPontosEspPX = arestaPontosEspPX;
    }
    
}
