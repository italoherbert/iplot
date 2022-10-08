package italo.iplot.plot2d.g2d;

import java.awt.Color;

public class PoligonoRegularObjeto2D extends Objeto2D implements Raio2D {

    private int quantLados = 6;
    private double raio = 0.4d;
    private Raio2D raio2D = null;
    
    public PoligonoRegularObjeto2D() {
        super.pintarVertices = false;
        super.pintarArestas = false;
        super.pintarFaces = true;
        
        super.cor = Color.RED;
        super.arestasCor = Color.BLACK;
    }
    
    @Override
    public void constroiObjeto2D(Objeto2DTO to) {
        Face2D face = new Face2D();

        double r = this.getRaio();
        double ainc = ( 2 * Math.PI ) / quantLados;
        double adesloc = Math.PI*.5d;
        
        int i;
        for( i = 0; i < quantLados; i++ ) {
            double a = adesloc + ( i * ainc );            
            double x = r * Math.cos( a );
            double y = r * Math.sin( a );
            Vertice2D v = new Vertice2D( x, y );
            
            super.estrutura.addVertice( v );
            face.addVertice( v );
            if ( i > 0 ) {
                Vertice2D vant = super.estrutura.getVertices().get( i-1 );
                super.estrutura.addAresta( new Aresta2D( vant, v ) ); 
            }
        }
        Vertice2D v1 = super.estrutura.getVertices().get( i-1 );
        Vertice2D v2 = super.estrutura.getVertices().get( 0 );
        super.estrutura.addAresta( new Aresta2D( v1, v2 ) ); 
        
        super.estrutura.addFace( face ); 
    }

    public int getQuantLados() {
        return quantLados;
    }

    public void setQuantLados(int quantLados) {
        this.quantLados = quantLados;
    }

    @Override
    public double getRaio() {
        if ( raio2D != null )
            return raio2D.getRaio();
        return raio;
    }

    public Raio2D getRaio2D() {
        return raio2D;
    }

    public void setRaio2D(Raio2D raio2D) {
        this.raio2D = raio2D;
    }
    
    public double getRaioValor() {
        return raio;
    }

    public void setRaioValor(double raio) {
        this.raio = raio;
    }
    
}
