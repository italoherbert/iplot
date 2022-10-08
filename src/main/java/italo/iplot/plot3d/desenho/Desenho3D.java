package italo.iplot.plot3d.desenho;

import italo.iplot.plot3d.g3d.Objeto3DGraficoDriver;
import italo.iplot.desenho.DesenhoListener;
import java.awt.Color;
import java.util.List;
import italo.iplot.plot3d.g3d.Aresta3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.util.Math3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.VisaoFiltroVert3D;
import italo.iplot.plot3d.grafico.geom.FaceGeom3D;
import italo.iplot.plot3d.grafico.geom.ArestaGeom3D;
import italo.iplot.gui.Desenho;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.ParalelepipedoObjeto3D;

public class Desenho3D implements Desenho {
            
    private final Objeto3DGraficoDriver drv;
    private DesenhoListener listener;
    
    public Desenho3D(Objeto3DGraficoDriver drv) {
        this.drv = drv;
    }

    @Override
    public void desenha(Grafico grafico) {     
        if ( listener != null )
            listener.desenhando( this );
                
        if ( drv.getUniversoVirtual() == null )
            return;
                
        if ( drv.isAplicarPerspectiva() )
            drv.getTransformador3D().perspectiva( drv.getUniversoVirtual(), drv.getVisaoFiltroV3D() );            
        
        grafico.setCor( drv.getUniversoVirtual().getCorFundo() );
        grafico.preencheRetangulo( 0, 0, drv.getTela().getTelaLargura(), drv.getTela().getTelaAltura() ); 
        this.desenha( grafico, drv.getUniversoVirtual(), drv.getUniversoVirtual() );         
            
        if ( listener != null )
            listener.desenhou( this );                                 
    }
    
    public void desenha( Grafico grafico, UniversoVirtual3D uv, Objeto3D obj ) {
        if ( obj == null )
            return;
        
        Estrutura3D est = obj.getEstrutura();                             
                        
        boolean cortar = false;
        if ( obj.isCortavel() ) {            
            Objeto3D container = obj;
            while( container != null && !cortar ) {
                container = container.getGrupo();  
                if ( container != null )
                    cortar = container.isCortarFilhos();
            }          
            if ( cortar ) {            
                est = est.copia( drv.getVisaoFiltroV3D(), drv );                 
                drv.getCortador3D().corte( container, est, obj.isAddNovaFaceAposCorte(), obj.isRemoverNovasArestasAposCorte(), drv.getVisaoFiltroV3D(), drv );                                                                                                    
            }            
        }
        
        Face3D faceContainerBase = null;
        Objeto3D gobj = obj.getGrupo();
        if ( gobj != null ) {
            while( !( gobj instanceof ParalelepipedoObjeto3D ) && gobj.getGrupo() != null )
                gobj = gobj.getGrupo();            
            
            if ( gobj != null )
                faceContainerBase = ((ParalelepipedoObjeto3D)gobj).getFaceT1T2F2F1();
        }
        
        List<Vertice3D> vertices = est.getVertices();
        List<Face3D> faces = est.getFaces();
        List<Aresta3D> todasAsArestas = est.todasAsArestas();
                        
        obj.antesDeDesenhar();
                
        if ( !obj.isVisivel() )
            return;
                                
        Math3D math = drv.getMath3D();
        VisaoFiltroVert3D visaoFiltroV3D = drv.getVisaoFiltroV3D();
        double[][] luzes = drv.getLuzes();
                        
        if ( faces != null ) {
            double[] minMaxDs = { 0, 0 };            
            if ( obj.getPreenchimento() == Objeto3D.Preenchimento.GRADIENTE && faceContainerBase != null ) {
                minMaxDs = drv.getMath3D().calculaMinMaxD( faceContainerBase, vertices, visaoFiltroV3D );
            }
                                                        
            List<Face3D> facesOrdenadas = math.facesOrganizadasPorZ( faces, visaoFiltroV3D );                
            
            for( Face3D f : facesOrdenadas ) {                                                
                if ( !f.isVisivel() )
                    continue;                        

                double[] vn = drv.getMath3D().vetorNormal( f, visaoFiltroV3D );                                                                                    
                if ( obj.isInverterVetoresNormais() )
                    vn = drv.getMath3D().mult( vn, -1.0d );
                
                boolean faceTrazeira = ( vn[2] < 0 );

                boolean pintarFace = !faceTrazeira && !uv.isEsconderTodasAsFaces() && obj.isPintarFaces();
                boolean desenharFace = ( !faceTrazeira || obj.isDesenharFacesDeTraz() ) && !uv.isEsconderTodasAsFaces() && obj.isDesenharFaces();

                if ( pintarFace ) {                               
                    FaceGeom3D fGeom = new FaceGeom3D( f, drv );
                    
                    Color cor;
                    if ( obj.getPreenchimento() == Objeto3D.Preenchimento.GRADIENTE ) {
                        Color[] gradienteCores = obj.getGradienteCores();
                        cor = drv.getMath3D().gradienteCor( faceContainerBase, f, minMaxDs, gradienteCores, visaoFiltroV3D );
                    } else {
                        cor = ( f.getCor() == null ? obj.getCor() : f.getCor() );                                                
                    }
                    
                    if ( obj.isAplicarIluminacaoAFace() )
                        cor = math.calculaCor( luzes, f, cor, visaoFiltroV3D );
                    
                    grafico.setCor( cor ); 
                    grafico.preencheFace( fGeom );  
                }                                                                        

                if ( desenharFace ) {
                    grafico.setCor( obj.getFaceArestasCor() );
                    
                    List<Aresta3D> faceArestas = est.calculaArestas( f );
                    for( Aresta3D a : faceArestas ) {                        
                        if ( !a.isNovaAresta() )                            
                            grafico.desenhaLinha( new ArestaGeom3D( a, drv ) );                                            
                    }
                }
                
                if ( uv.isPintarVetoresNormais() ) {        
                    double[] vn_p1 = math.pontoMedio( f, visaoFiltroV3D ); 
                    double[] vn2 = math.alteraComprimento( vn, uv.getComprimentoVetorNormal() );                

                    double[] vn_p2 = math.soma( vn_p1, vn2 );   
                    
                    Vertice3D v1 = new Vertice3D( vn_p1 );
                    Vertice3D v2 = new Vertice3D( vn_p2 );
                    Aresta3D a = new Aresta3D( obj, v1, v2 );
                    
                    ArestaGeom3D linhaGeom = new ArestaGeom3D( a, drv );         

                    grafico.setCor( uv.getVetorNormalCor() ); 
                    grafico.desenhaLinha( linhaGeom );
                }                                    

            }    
        }
         
        if ( !uv.isEsconderTodasAsArestas() && obj.isPintarArestas() ) {                                
            double[] minMaxDs = { 0, 0 };
            if ( vertices != null && obj.getArestaPreenchimento() == Objeto3D.Preenchimento.GRADIENTE && faceContainerBase != null )
                minMaxDs = drv.getMath3D().calculaMinMaxD( faceContainerBase, vertices, visaoFiltroV3D );
            
            for( Aresta3D a : todasAsArestas ) {                    
                ArestaGeom3D linhaGeom = new ArestaGeom3D( a, drv );
                
                Color cor;                                    
                if ( obj.getArestaPreenchimento() == Objeto3D.Preenchimento.GRADIENTE ) {
                    Color[] gradienteCores = obj.getGradienteCores();
                    cor = drv.getMath3D().gradienteCor( faceContainerBase, a, minMaxDs, gradienteCores, visaoFiltroV3D );
                } else {
                    cor = ( a.getCor() == null ? obj.getArestasCor(): a.getCor() );
                }

                if ( obj.isAplicarIluminacaoAAresta() )
                    cor = drv.getMath3D().calculaCor( luzes, a, cor, visaoFiltroV3D );                    
                
                grafico.setCor( cor );
                if ( a.isArestaPontilhada() ) {
                    int esp = a.getEspacoArestaPX();
                    grafico.desenhaLinhaPontilhada( linhaGeom, esp );
                } else {
                    grafico.desenhaLinha( linhaGeom );
                }
            } 
        }
                
        if ( !uv.isEsconderTodosOsVertices() && obj.isPintarVertices() ) {
            for( Vertice3D v : vertices ) {
                double[] p = drv.getVisaoFiltroV3D().getPonto3D( v );
                int[] xy = drv.getMath3D().pontoPX( p, drv.getTela() );

                double raio;
                if ( v.getVerticeRaio3D() != null )
                    raio = v.getVerticeRaio3D().getVerticeRaio();
                else if ( obj.getVerticeRaio3D() != null )
                    raio = obj.getVerticeRaio3D().getVerticeRaio();
                else raio = uv.getVerticeRaio();

                int r = (int)drv.getMath3D().telaUnidade( raio, drv.getTela() );                       
                grafico.setCor( obj.getVerticesCor() );
                grafico.preencheCirculo( xy[0], xy[1], r );                                                   
            }                                     
        }         

        obj.getObjetos().forEach( o -> this.desenha( grafico, uv, o ) );
        
        if ( obj.getGrafico() != null )
            obj.getGrafico().desenho( grafico, obj, drv );
    }
            
    public void setDesenho3DListener( DesenhoListener listener ) {
        this.listener = listener;
    }
    
}
