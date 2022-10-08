package italo.iplot.plot2d.g2d;

import italo.iplot.gui.Tela;
import java.awt.Color;

public class UniversoVirtual2D extends RetanguloObjeto2D {
    
    private final int BORDA_PX = 0;
    
    private double verticeRaio = 0.02d;  
    private int bordaPX = BORDA_PX;
    private Color corFundo = Color.BLACK;
    private double uvRot = 0;
    
    private boolean esconderTodasAsFaces = false;
    private boolean esconderTodasAsArestas = false;
    private boolean esconderTodosOsVertices = false;
             
    public UniversoVirtual2D() {
        super( 0, 0, 0, 0 );
        super.pintarVertices = false;
        super.pintarArestas = false;
        super.pintarFaces = false;        
    }
    
    @Override
    public void constroiObjeto2D(Objeto2DTO to) {         
        Tela tela = to.getTela();
        double larg = to.getMath2D().verticeUnidade( tela.getTelaLargura(), tela );
        double alt = to.getMath2D().verticeUnidade( tela.getTelaAltura(), tela );
        double b = to.getMath2D().verticeUnidade( bordaPX, tela );
        
        super.setPontos( -larg*.5d + b, -alt*.5d + b, larg*.5d - b, alt*.5d - b ); 
        super.constroiObjeto2D( to ); 
        
        super.addSomenteUmaConstroiObj2DListener( new ConstroiObj2DAdapter() {
            @Override
            public void construiu(Objeto2D obj, Objeto2DTO to) {
                to.getTransformador2D().rot(obj, uvRot, to.getXYFiltroV2D() ); 
                obj.aplicaTransformacoes();
            }            
        } );        
    }
            
    public void zeraRot() {
        uvRot = 0;
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

    public double getUVRot() {
        return uvRot;
    }

    public void setUVRot(double uvRot) {
        this.uvRot = uvRot;
    }

    public int getBordaPX() {
        return bordaPX;
    }

    public void setBordaPX(int bordaPX) {
        this.bordaPX = bordaPX;
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
