package italo.iplot.plot3d.g3d;

import java.awt.Color;

public class PoligonoRegularObjeto3D extends Objeto3D implements Raio3D {

    private int quantLados = 6;
    private double raio = 0.4d;
    private Raio3D raio3D = null;
    
    public PoligonoRegularObjeto3D() {
        super.pintarVertices = false;
        super.pintarArestas = false;
        super.pintarFaces = true;
        
        super.cor = Color.RED;
        super.arestasCor = Color.BLACK;
    }
    
    @Override
    public void constroiObjeto3D(Objeto3DTO to) {
        Face3D face = new Face3D();

        double r = this.getRaio();
        double ainc = ( 2 * Math.PI ) / quantLados;
        double adesloc = Math.PI*.5d;
        
        int i;
        for( i = 0; i < quantLados; i++ ) {
            double a = adesloc + ( i * ainc );            
            double x = r * Math.cos( a );
            double y = r * Math.sin( a );
            Vertice3D v = new Vertice3D( x, y, 0 );
            
            super.estrutura.addVertice( v );
            face.addVertice( v );            
        }
                
        super.estrutura.addFace( face, to ); 
    }

    public int getQuantLados() {
        return quantLados;
    }

    public void setQuantLados(int quantLados) {
        this.quantLados = quantLados;
    }

    @Override
    public double getRaio() {
        if ( raio3D != null )
            return raio3D.getRaio();
        return raio;
    }

    public Raio3D getRaio3D() {
        return raio3D;
    }

    public void setRaio3D(Raio3D raio2D) {
        this.raio3D = raio2D;
    }
    
    public double getRaioValor() {
        return raio;
    }

    public void setRaioValor(double raio) {
        this.raio = raio;
    }
    
}

