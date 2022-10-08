package italo.iplot.plot3d.g3d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot3d.planocartesiano.g3d.PCContainerObjeto3D;
import java.awt.Color;

public class GrafoObjeto3D extends Objeto3D implements ComponenteObjeto3D, VerticeRaio3D {
    
    private double verticeRaio = 0.02d;
    
    private double[][] grafoNos;
    private int[][] grafoArestas; 
    
    private String legenda = null;
    
    private ContainerObjeto3D container;
                
    public GrafoObjeto3D() {
        this( new double[][]{} );        
    }

    public GrafoObjeto3D( double[][] grafoNos ) {
        this( grafoNos, new int[][] {} );
    }
    
    public GrafoObjeto3D( double[][] grafoNos, int[][] grafoArestas ) {
        this.grafoNos = grafoNos;
        this.grafoArestas = grafoArestas;
        
        super.pintarVertices = true;
        super.pintarArestas = true;
        super.pintarFaces = false;
        
        super.verticesCor = Color.BLUE;
        super.arestasCor = Color.BLACK;
        
        super.verticeRaio3D = this;
        super.verticeObjTipo = VCIRCULO;
    }
    
    @Override
    public void constroiObjeto3D(Objeto3DTO to) {        
        if ( grafoNos == null )
            return;                                                  
                      
        double[][] nos = new double[ grafoNos.length ][3];
        for( int i = 0; i < nos.length; i++ ) {
            double noX = grafoNos[i][0];
            double noY = grafoNos[i][1];
            double noZ = grafoNos[i][2];
            
            double x = container.calculaX( noX );
            double y = container.calculaY( noY );
            double z = container.calculaZ( noZ );
            super.getEstrutura().addVertice( new Vertice3D( x, y, z ) );
        }
        
        if ( grafoArestas != null ) {
            for( int[] a : grafoArestas ) {            
                Vertice3D v1 = super.getEstrutura().getVertices().get( a[0] );
                Vertice3D v2 = super.getEstrutura().getVertices().get( a[1] );

                super.getEstrutura().addOutrasAresta( new Aresta3D( this, v1, v2 ) );
            }        
        }
        
        if ( legenda != null && container instanceof PCContainerObjeto3D ) {
            int tipo = ( pintarArestas ? Legenda.LINHA : Legenda.PONTO );
            Color c = ( pintarArestas ? arestasCor : verticesCor );
            ((PCContainerObjeto3D)container).addLegenda(new Legenda( legenda, c, tipo ) );
        }
    }

    @Override
    public void escalar(double escala, Objeto3DTO to) {
        double[] vc = container.verticeCentral();
        to.getTransformador3D().sub( estrutura.getVertices(), vc, to.getXYZFiltroV3D() );
        to.getTransformador3D().escala( this, escala, to.getXYZFiltroV3D() );        
        to.getTransformador3D().soma( estrutura.getVertices(), vc, to.getXYZFiltroV3D() ); 
    }

    @Override
    public ComponenteObjeto3DLimite calculaLimites() {                
        if ( grafoNos.length > 0 ) {
            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            double minZ = Double.MAX_VALUE;
            double maxZ = Double.MIN_VALUE;
            for( double[] no : grafoNos ) {
                if ( no[0] < minX )
                    minX = no[0];
                if ( no[0] > maxX )
                    maxX = no[0];            
                if ( no[1] < minY )
                    minY = no[1];
                if ( no[1] > maxY )
                    maxY = no[1];
                if ( no[2] < minZ )
                    minZ = no[2];
                if ( no[2] > maxZ )
                    maxZ = no[2];
            }
            return new ComponenteObjeto3DLimite( minX, maxX, minY, maxY, minZ, maxZ );
        }
        return new ComponenteObjeto3DLimite();
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
   
    @Override
    public double getVerticeRaio() {
        return verticeRaio;
    }

    public void setVerticeRaio(double verticeRaio) {
        this.verticeRaio = verticeRaio;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
     
    public ContainerObjeto3D getContainerObjeto3D() {
        return container;
    }
    
    @Override
    public void setContainerObjeto3D(ContainerObjeto3D container) {
        this.container = container;
    }
    
}
