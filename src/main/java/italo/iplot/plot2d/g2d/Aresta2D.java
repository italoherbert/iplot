package italo.iplot.plot2d.g2d;

import java.awt.Color;

public class Aresta2D {
    
    private Vertice2D v1;
    private Vertice2D v2;
    
    private Color cor = null;    
    private boolean arestaPontilhada = false;
    private int espacoArestaPontosPX = 3;
    
    public Aresta2D( Vertice2D v1, Vertice2D v2 ) {
        this.v1 = v1;
        this.v2 = v2;
        
        v1.addAresta( this );
        v2.addAresta( this ); 
    }

    public Vertice2D getV1() {
        return v1;
    }

    public void setV1(Vertice2D v1) {        
        this.v1 = v1;
        this.v1.addAresta( this );
    }

    public Vertice2D getV2() {
        return v2;
    }

    public void setV2(Vertice2D v2) {        
        this.v2 = v2;
        this.v2.addAresta( this );
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public boolean isArestaPontilhada() {
        return arestaPontilhada;
    }

    public void setArestaPontilhada(boolean arestaPontilhada) {
        this.arestaPontilhada = arestaPontilhada;
    }

    public int getEspacoArestaPontosPX() {
        return espacoArestaPontosPX;
    }

    public void setEspacoArestaPontosPX(int espacoArestaPontosPX) {
        this.espacoArestaPontosPX = espacoArestaPontosPX;
    }
    
}
