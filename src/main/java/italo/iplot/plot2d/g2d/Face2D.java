package italo.iplot.plot2d.g2d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Face2D implements Objeto2DVisivel {
    
    private Objeto2D objeto; 
    private boolean visivel = true;
    private Color cor = null;        
    
    private final List<Vertice2D> vertices = new ArrayList();
    
    private Face2DVisivel face2DVisivel;    
            
    public Face2D() {}
    
    public Face2D( Face2DVisivel face2DVisivel ) {
        this.face2DVisivel = face2DVisivel;
    }
                
    @Override
    public boolean isVisivel() {
        return visivel && ( face2DVisivel == null ? true : face2DVisivel.isVisivel( this ) );
    }
            
    public void addVertice(Vertice2D vertice) {
        vertice.addFace( this ); 
        vertices.add( vertice );        
    }
    
    public boolean removeVertice( Vertice2D v ) {
        return vertices.remove( v );
    }
        
    public List<Vertice2D> getVertices() {
        return vertices;
    }

    public Objeto2D getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto2D objeto) {
        this.objeto = objeto;
    }

    public Face2DVisivel getFace3DVisivel() {
        return face2DVisivel;
    }

    public void setFace2DVisivel(Face2DVisivel face2DVisivel) {
        this.face2DVisivel = face2DVisivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }        
    
}
