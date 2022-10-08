package italo.iplot.plot2d.g2d;

import java.util.ArrayList;
import java.util.List;

public class Vertice2D {          
    
    private final int ARESTAS_LISTA_SIZE = 2;
    private final int FACES_LISTA_SIZE = 1;
    
    private double x;
    private double y;
    
    private double x0;
    private double y0;
    
    private double visaoX;
    private double visaoY;
    
    private List<Aresta2D> arestas; 
    private List<Face2D> faces;
    
    private VerticeRaio2D raio2D;

    public Vertice2D(double x, double y) {
        this.x0 = this.visaoX = this.x = x;
        this.y0 = this.visaoY = this.y = y;
    }
            
    public void copiaP0ParaP() {
        this.x = this.x0;
        this.y = this.y0;
    }
    
    public void copiaParaVisao() {
        this.visaoX = this.x;
        this.visaoY = this.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }
    
    public double getVisaoX() {
        return visaoX;
    }

    public void setVisaoX(double x) {
        this.visaoX = x;
    }

    public double getVisaoY() {
        return visaoY;
    }

    public void setVisaoY(double y) {
        this.visaoY = y;
    }
    
    public double[] getP0() {
        return new double[] { x0, y0 };
    }
    
    public void setP0(double[] p0) {
        this.x0 = p0[0];
        this.y0 = p0[1];
    }
    
    public double[] getP() {
        return new double[] { x, y };
    }
    
    public void setP(double[] p) {
        this.x = p[0];
        this.y = p[1];
    }
    
    public double[] getVisaoP() {
        return new double[] { visaoX, visaoY };
    }
    
    public void setVisaoP(double[] visaoP) {
        this.visaoX = visaoP[0];
        this.visaoY = visaoP[1];
    }
        
    public void addAresta( Aresta2D aresta ) {
        if ( arestas == null )
            arestas = new ArrayList( ARESTAS_LISTA_SIZE );
        arestas.add( aresta );
    }
    
    public void addFace( Face2D face ) {
        if ( faces == null )
            faces = new ArrayList( FACES_LISTA_SIZE );
        faces.add( face );
    }

    public VerticeRaio2D getVerticeRaio2D() {
        return raio2D;
    }

    public void setVerticeRaio2D(VerticeRaio2D raio2D) {
        this.raio2D = raio2D;
    }

    public List<Aresta2D> getArestas() {
        return arestas;
    }

    public List<Face2D> getFaces() {
        return faces;
    }

}
