package italo.iplot.plot3d.g3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public final class Aresta3D {
    
    private Vertice3D v1 = null;
    private Vertice3D v2 = null;
    private List<Face3D> faces = null;    
    private Color cor = null;
    private boolean arestaPontilhada = false;
    private int espacoArestaPX = 3;
    private boolean novaAresta = false;
    private Objeto3D obj;
            
    public Aresta3D( Objeto3D obj, Vertice3D v1, Vertice3D v2 ) {
        this( obj, v1, v2, null );
    }
    
    public Aresta3D( Objeto3D obj, Vertice3D vert1, Vertice3D vert2, Color cor ) {         
        this.cor = cor;
        this.obj = obj;
        this.setV1( vert1 );
        this.setV2( vert2 );
    }
    
    public void addFace( Face3D face ) {
        if ( faces == null )
            faces = new ArrayList();        
        faces.add( face );
    }
    
    public void setV1(Vertice3D v) {
        v.addAresta( this );        
        this.v1 = v;
    }
    
    public void setV2(Vertice3D v) {
        v.addAresta( this );        
        this.v2 = v;
    }

    public Vertice3D getV1() {
        return v1;
    }
    
    public Vertice3D getV2() {
        return v2;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public List<Face3D> getFaces() {
        if ( faces == null )
            return new ArrayList(0);
        return faces;
    }

    public boolean isArestaPontilhada() {
        return arestaPontilhada;
    }

    public void setArestaPontilhada(boolean arestaPontilhada) {
        this.arestaPontilhada = arestaPontilhada;
    }

    public int getEspacoArestaPX() {
        return espacoArestaPX;
    }

    public void setEspacoArestaPX(int espacoArestaPX) {
        this.espacoArestaPX = espacoArestaPX;
    }

    public boolean isNovaAresta() {
        return novaAresta;
    }

    public void setNovaAresta(boolean novaAresta) {
        this.novaAresta = novaAresta;
    }

    public Objeto3D getObjeto() {
        return obj;
    }

    public void setObjeto(Objeto3D obj) {
        this.obj = obj;
    }
    
}
