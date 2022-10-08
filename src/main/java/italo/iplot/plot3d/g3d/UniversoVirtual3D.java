
package italo.iplot.plot3d.g3d;

import java.awt.Color;

public class UniversoVirtual3D extends ParalelepipedoObjeto3D {
                    
    private final double inicialXRot = -Math.PI / 8.0d;
    private final double inicialYRot = Math.PI / 4.5d;
    
    private boolean pintarVetoresNormais = false;
    
    private boolean esconderTodasAsFaces = false;
    private boolean esconderTodasAsArestas = false;
    private boolean esconderTodosOsVertices = false;
    
    private double verticeRaio = 0.01d; 
    private double comprimentoVetorNormal = 0.1d;
    private Color vetorNormalCor = Color.WHITE;
    
    private Color corFundo = Color.BLACK;
         
    private double uvXRot = 0;
    private double uvYRot = 0;   
                
    public UniversoVirtual3D() {
        super.pintarVertices = true;
        super.pintarArestas = true;
        super.pintarFaces = true;
    }
    
    @Override
    public void constroiObjeto3D(Objeto3DTO to) {                         
        super.calculaIntervalos();
        super.addSomenteUmaConstroiObj3DListener( new ConstroiObj3DAdapter() {
            @Override
            public void construiuParcialmente(Objeto3D obj, Objeto3DTO to) {
                to.getTransformador3D().rotY( obj, uvYRot, to.getXYZFiltroV3D() ); 
                to.getTransformador3D().rotX( obj, uvXRot, to.getXYZFiltroV3D() ); 
                obj.aplicaTransformacoes();                       
            }            
        } );        
    }
        
    public void inicialRot() {
        uvXRot = inicialXRot;
        uvYRot = inicialYRot;
    }
    
    public void zeraRot() {
        uvXRot = 0;
        uvYRot = 0;
    }

    public Color getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(Color corFundo) {
        this.corFundo = corFundo;
    }

    public double getVerticeRaio() {
        return verticeRaio;
    }

    public void setVerticeRaio(double verticeRaio) {
        this.verticeRaio = verticeRaio;
    }

    public boolean isPintarVetoresNormais() {
        return pintarVetoresNormais;
    }

    public void setPintarVetoresNormais(boolean desenharVetoresNormais) {
        this.pintarVetoresNormais = desenharVetoresNormais;
    }
    
    public double getComprimentoVetorNormal() {
        return comprimentoVetorNormal;
    }

    public void setComprimentoVetorNormal(double comprimentoVetorNormal) {
        this.comprimentoVetorNormal = comprimentoVetorNormal;
    }

    public Color getVetorNormalCor() {
        return vetorNormalCor;
    }

    public void setVetorNormalCor(Color corVetorNormal) {
        this.vetorNormalCor = corVetorNormal;
    }

    public double getUVXRot() {
        return uvXRot;
    }

    public void setUVXRot(double uvXRot) {
        this.uvXRot = uvXRot;
    }

    public double getUVYRot() {
        return uvYRot;
    }

    public void setUVYRot(double uvYRot) {
        this.uvYRot = uvYRot;
    }

    public boolean isEsconderTodasAsFaces() {
        return esconderTodasAsFaces;
    }

    public void setEsconderTodasAsFaces(boolean esconderTodasAsFaces) {
        this.esconderTodasAsFaces = esconderTodasAsFaces;
    }

    public boolean isEsconderTodasAsArestas() {
        return esconderTodasAsArestas;
    }

    public void setEsconderTodasAsArestas(boolean esconderTodasAsArestas) {
        this.esconderTodasAsArestas = esconderTodasAsArestas;
    }

    public boolean isEsconderTodosOsVertices() {
        return esconderTodosOsVertices;
    }

    public void setEsconderTodosOsVertices(boolean esconderTodosOsVertices) {
        this.esconderTodosOsVertices = esconderTodosOsVertices;
    }
            
}
