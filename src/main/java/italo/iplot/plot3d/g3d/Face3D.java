package italo.iplot.plot3d.g3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Face3D implements Objeto3DVisivel, VetorNormalFace3D {
        
    private Objeto3D objeto; 
    private boolean inverterVN = false;
    private boolean visivel = true;
    private Color cor = null;        
    
    private final List<Vertice3D> vertices = new ArrayList();
    
    private Face3DVisivel face3DVisivel;    
    
    private Vertice3D[] verticesParaVNCalc = null;
    
    private double ordZ = 0;
    
    private double fatorSomaNZ = 0;
        
    public Face3D() {}
    
    public Face3D( Face3DVisivel face3DVisivel ) {
        this.face3DVisivel = face3DVisivel;
    }
        
    public void calculaVerticesParaVNCalc( Objeto3DTO to ) {
        verticesParaVNCalc = to.getMath3D().verticesNaoColineares( vertices, to.getXYZFiltroV3D() );
    }
    
    @Override
    public boolean isVisivel() {
        return visivel && ( face3DVisivel == null ? true : face3DVisivel.isVisivel( this ) );
    }
            
    public void addVertice(Vertice3D vertice) {
        vertice.addFace( this ); 
        vertices.add( vertice );        
    }
    
    public boolean removeVertice( Vertice3D v ) {
        return vertices.remove( v );
    }
        
    public List<Vertice3D> getVertices() {
        return vertices;
    }

    public Objeto3D getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto3D objeto) {
        this.objeto = objeto;
    }

    public boolean isInverterVN() {
        return inverterVN;
    }

    public void setInverterVN(boolean inverterVN) {
        this.inverterVN = inverterVN;
    }
  
    @Override
    public Vertice3D[] getVerticesParaVNCalc() {
        return verticesParaVNCalc;
    }

    public void setVerticesParaVNCalc(Vertice3D[] verticesParaVNCalc) {
        this.verticesParaVNCalc = verticesParaVNCalc;
    }

    public Face3DVisivel getFace3DVisivel() {
        return face3DVisivel;
    }

    public void setFace3DVisivel(Face3DVisivel face3DVisivel) {
        this.face3DVisivel = face3DVisivel;
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

    public double getOrdZ() {
        return ordZ;
    }

    public void setOrdZ(double ordPZ) {
        this.ordZ = ordPZ;
    }

}
