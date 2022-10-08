package italo.iplot.plot2d.g2d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot2d.planocartesiano.g2d.PCContainerObjeto2D;
import java.awt.Color;

public class GrafoObjeto2D extends Objeto2D implements ComponenteObjeto2D, VerticeRaio2D {

    private final double RAIO = 0.02d;

    protected double[][] grafoNos = {};
    protected int[][] grafoArestas = {};
    
    protected ContainerObjeto2D container = null;

    protected String legenda = null;
    protected double verticeRaio = RAIO;

    public GrafoObjeto2D() {
        this( new double[0][2] );
    }
    
    public GrafoObjeto2D( double[][] vertices ) {
        this( vertices, new int[][] {} );
    }

    public GrafoObjeto2D( double[][] vertices, int[][] arestas ) {
        this.grafoNos = vertices;
        this.grafoArestas = arestas;
                
        super.pintarVertices = true;
        super.pintarArestas = true;
        
        super.verticesCor = Color.BLUE;
        super.arestasCor = Color.ORANGE;
        
        super.verticeRaio2D = this;
        super.verticeObjTipo = VPOLIGONO_REGULAR_OBJ;
    }

    @Override
    public void constroiObjeto2D(Objeto2DTO to) {                        
        for( double[] v : grafoNos ) {
            double x = container.calculaX( v[0] );
            double y = container.calculaY( v[1] );
                                    
            Vertice2D vertice = new Vertice2D( x, y );
            super.getEstrutura().addVertice( vertice );
        }
        
        for( int i = 0; i < grafoArestas.length; i++ ) {            
            Vertice2D v1 = super.getEstrutura().getVertices().get( grafoArestas[i][0] );
            Vertice2D v2 = super.getEstrutura().getVertices().get( grafoArestas[i][1] );
            super.getEstrutura().addAresta( new Aresta2D( v1, v2 ) );
        }                
        
        if ( legenda != null && container instanceof PCContainerObjeto2D ) {
            int tipo = ( pintarArestas ? Legenda.LINHA : Legenda.PONTO );
            Color c = ( pintarArestas ? arestasCor : verticesCor );
            ((PCContainerObjeto2D)container).addLegenda( new Legenda( legenda, c, tipo ) );
        }        
    }

    @Override
    public ComponenteObjeto2DLimite calculaLimites() {
        if ( grafoNos.length > 0 ) {
            double x1 = Double.MAX_VALUE;
            double y1 = Double.MAX_VALUE;
            double x2 = Double.MIN_VALUE;
            double y2 = Double.MIN_VALUE;
            for( double[] v : grafoNos ) {
                if ( v[0] < x1 )
                    x1 = v[0];
                if ( v[0] > x2 )
                    x2 = v[0];
                if ( v[1] < y1 )
                    y1 = v[1];
                if ( v[1] > y2 )
                    y2 = v[1];
            }
            x1 -= verticeRaio*.5d;
            y1 -= verticeRaio*.5d;
            x2 += verticeRaio*.5d;
            y2 += verticeRaio*.5d;
            return new ComponenteObjeto2DLimite( x1, x2, y1, y2 );
        }
        return new ComponenteObjeto2DLimite();
    }

    @Override
    public void escalar( double escala, Objeto2DTO to ) {        
        to.getTransformador2D().sub( estrutura.getVertices(), container.getX(), container.getY(), to.getXYFiltroV2D() );
        to.getTransformador2D().escala( this, escala, to.getXYFiltroV2D() );        
        to.getTransformador2D().soma( estrutura.getVertices(), container.getX(), container.getY(), to.getXYFiltroV2D() );        
    }

    @Override
    public ContainerObjeto2D getContainerObjeto2D() {
        return container;
    }

    @Override
    public void setContainerObjeto2D(ContainerObjeto2D container) {
        this.container = container;
    }

    @Override
    public double getVerticeRaio() {
        return verticeRaio;
    }

    public void setVerticeRaio(double verticeRaio) {
        this.verticeRaio = verticeRaio;
    }

    public double[][] getGrafoNos() {
        return grafoNos;
    }

    public void setGrafoNos(double[][] grafoNos) {
        this.grafoNos = grafoNos;
    }

    public int[][] getGrafoArestas() {
        return grafoArestas;
    }

    public void setGrafoArestas(int[][] grafoArestas) {
        this.grafoArestas = grafoArestas;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
    
}
