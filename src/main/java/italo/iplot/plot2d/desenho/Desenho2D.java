package italo.iplot.plot2d.desenho;

import italo.iplot.gui.Desenho;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.UniversoVirtual2D;
import italo.iplot.desenho.DesenhoListener;
import italo.iplot.plot2d.g2d.Aresta2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.ContainerObjeto2D;
import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.grafico.geom.ArestaGeom2D;
import italo.iplot.plot2d.grafico.geom.FaceGeom2D;
import java.awt.Color;
import java.util.List;

public class Desenho2D implements Desenho {

    private final Objeto2DGraficoDriver drv;
    private DesenhoListener listener;
    
    public Desenho2D( Objeto2DGraficoDriver drv ) {
        this.drv = drv;
    }
    
    @Override
    public void desenha( Grafico grafico ) {   
        
        if ( listener != null )
            listener.desenhando( this );
                
        if ( drv.getUniversoVirtual() == null )
            return;
                        
        grafico.setCor( drv.getUniversoVirtual().getCorFundo() );
        grafico.preencheRetangulo( 0, 0, drv.getTela().getTelaLargura(), drv.getTela().getTelaAltura() );         
        
        this.desenha( grafico, drv.getUniversoVirtual(), drv.getUniversoVirtual() );         
                
        if ( listener != null )
            listener.desenhou( this ); 
    }
    
    public void desenha( Grafico grafico, UniversoVirtual2D uv, Objeto2D obj ) {
        if ( obj == null )
            return;
            
        if ( obj.getGrafico() != null )
            if ( obj.isExecutarGraficoAntesDePintar() )
                obj.getGrafico().desenho( grafico, obj, drv );
        
        
        Estrutura2D est = obj.getEstrutura();
                
        Objeto2D comp = obj;
        boolean achou = comp instanceof ComponenteObjeto2D;
        while( comp != null && !achou ) {
            comp = comp.getGrupo();  
            if ( comp != null )
                achou = comp instanceof ComponenteObjeto2D;
        }
                
        if ( comp != null ) {
            ContainerObjeto2D container = ((ComponenteObjeto2D)comp).getContainerObjeto2D();
            if ( container != null ) {
                if ( container.isCortar() ) {
                    est = est.copia();
                    drv.getCortador2D().corta( container, est, drv.getVisaoFiltroV2D() );                    
                }
            }
        }
        
        List<Vertice2D> vertices = est.getVertices();
        List<Aresta2D> arestas = est.getArestas();
        List<Face2D> faces = est.getFaces();
                            
        obj.antesDeDesenhar();
                
        if ( !obj.isVisivel() )
            return;
                
        if ( !uv.isEsconderTodasAsFaces() && obj.isPintarFaces() ) {
            for( Face2D face : faces ) {
                if ( face.isVisivel() ) {
                    boolean pintarFace = !uv.isEsconderTodasAsFaces() && obj.isPintarFaces();

                    FaceGeom2D fGeom = new FaceGeom2D( face, drv );
                    if ( pintarFace ) {                                         
                        Color cor = ( face.getCor() == null ? obj.getCor(): face.getCor() );
                        grafico.setCor( cor );                    
                        grafico.preencheFace( fGeom );
                    }
                }    
            }                    
        }
            
        if ( !uv.isEsconderTodasAsArestas() && obj.isPintarArestas() ) {                                
            for( Aresta2D a : arestas ) {                  
                ArestaGeom2D linhaGeom = new ArestaGeom2D( a, drv );

                Color cor = ( a.getCor() == null ? obj.getArestasCor(): a.getCor() );

                grafico.setCor( cor );
                if ( a.isArestaPontilhada() ) {
                    int esp = a.getEspacoArestaPontosPX();
                    grafico.desenhaLinhaPontilhada( linhaGeom, esp );
                } else {
                    grafico.desenhaLinha( linhaGeom );
                }
            }            
        }        
            
        if ( !uv.isEsconderTodosOsVertices() && obj.isPintarVertices() && !obj.isVerticesObjetosConstruidos() ) {
            for( Vertice2D v : vertices ) {
                double x = drv.getVisaoFiltroV2D().getX( v );
                double y = drv.getVisaoFiltroV2D().getY( v );
                int[] xy = drv.getMath2D().pontoPX( x, y, drv.getTela() );

                double raio;
                if ( v.getVerticeRaio2D() != null )
                    raio = v.getVerticeRaio2D().getVerticeRaio();
                else if ( obj.getVerticeRaio2D() != null )
                    raio = obj.getVerticeRaio2D().getVerticeRaio();
                else raio = uv.getVerticeRaio();

                int r = (int)drv.getMath2D().telaUnidade( raio, drv.getTela() );                       
                grafico.setCor( obj.getVerticesCor() );
                grafico.preencheCirculo( xy[0], xy[1], r );                                                                         
            }                                     
        }         
                 
        obj.getObjetos().forEach( o -> this.desenha( grafico, uv, o ) );        
        
        if ( obj.getGrafico() != null )
            if ( !obj.isExecutarGraficoAntesDePintar() )
                obj.getGrafico().desenho( grafico, obj, drv );                
    }
    
    public void setDesenho2DListener( DesenhoListener listener ) {
        this.listener = listener;
    }
    
}
