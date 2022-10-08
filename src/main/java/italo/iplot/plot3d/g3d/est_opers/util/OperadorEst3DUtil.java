package italo.iplot.plot3d.g3d.est_opers.util;

import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Aresta3D;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.util.Cortador3D;
import italo.iplot.plot3d.g3d.est_opers.util.ListaEncadeadaEst3DUtil.ListaEncadeada;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class OperadorEst3DUtil {

    private Estrutura3DUtil estUtil;
    private GrafoEst3DUtil grafoUtil;
    private ListaEncadeadaEst3DUtil listaEncUtil;
    private Cortador3D corte;
    
    public OperadorEst3DUtil( Estrutura3DUtil estUtil, GrafoEst3DUtil grafoUtil, ListaEncadeadaEst3DUtil listaUtil, Cortador3D corte ) {
        this.estUtil = estUtil;
        this.grafoUtil = grafoUtil;
        this.listaEncUtil = listaUtil;
        this.corte = corte;
    }
           
    public void alteraEstrutura( Estrutura3D est1, Estrutura3D est2, 
            List<Face3D> todasAsFaces, List<Vertice3D> novosVertices, List<Estrutura3D> novasEstruturas, 
            FiltroVert3D filtro, Objeto3DTO to ) {                        
                
        List<Face3D> paraRemover = new ArrayList();
        List<NovaFace> novasFaces = new ArrayList();

        List<Face3D> facesObj2 = est2.getFaces();                
                
        to.setObjeto( new Debug() ); 
        
        for( Face3D f : est1.getFaces() ) {                                    
        //Face3D f = est1.getFaces().get( 4 );
            if ( !novosVertices.isEmpty() ) {                
                ListaEncadeada listaENC = listaEncUtil.geraListaEncadeada( todasAsFaces, f, novosVertices, filtro, to );            
                if ( listaENC == null )
                    continue;
                    //return;

                List<Vertice3D> intersecoes = listaEncUtil.intersecoes( listaENC );            
                this.alteraEstruturaNovaFaceNaoInterna( f, intersecoes, facesObj2, novasFaces, filtro, to );            
                paraRemover.add( f );
                    
                /*
                List<Vertice3D> intersecoes = listaEncUtil.intersecoes( listaENC );            
                int cont = estUtil.contaVertsColinearesAAlgumaAresta( f, intersecoes, filtro, to );
                if ( cont >= 2 ) {       
                    this.alteraEstruturaNovaFaceNaoInterna( f, intersecoes, facesObj2, novasFaces, filtro, to );            
                    paraRemover.add( f );                
                } else {            
                    Estrutura3D novaEst = this.alteraEstruturaNovaFaceInterna( f, est2, novosVertices, intersecoes, filtro, to );
                    novasEstruturas.add( novaEst );
                }
                */
            }
        }        
        
        
        paraRemover.forEach( f2 -> est1.removeFace( f2 ) ); 
                
        int size = novasFaces.size();
        for( int i = 0; i < size; i++ ) {
            NovaFace novaFace = novasFaces.get( i );
            est1.addFaceECopiarVNDirecao( novaFace.nova, novaFace.pai, filtro, to );
        }                 
    }
    
    public Estrutura3D alteraEstruturaNovaFaceInterna( Face3D f, Estrutura3D est2, 
            List<Vertice3D> novosVertices, List<Vertice3D> intersecoes, 
            FiltroVert3D filtro, Objeto3DTO to ) {
        
        Estrutura3D novaEst = est2.copia( filtro, to );
        novaEst.recalculaArestas();
        
        double[][] pontos = to.getMath3D().planoPontos( f, filtro );
        boolean inverterVN = !f.isInverterVN();
        Face3D novaF = corte.cortaFace( novaEst, pontos, inverterVN, true, null, filtro, to );                                

        novosVertices.addAll( novaF.getVertices() );

        List<Vertice3D> vertsParaRemover = new ArrayList();
        for( Vertice3D v : intersecoes ) {
            double[] v_p = filtro.getPonto3D( v );

            boolean achou = false;
            int size = novaF.getVertices().size();                                        
            for( int i = 0; !achou && i < size; i++ ) {
                Vertice3D v2 = novaF.getVertices().get( i );
                double[] v2_p = filtro.getPonto3D( v2 );

                if ( to.getMath3D().verificaSeIguais( v_p, v2_p ) ) {
                    vertsParaRemover.add( v );
                    achou = true;
                }
            }
        }
        intersecoes.removeAll( vertsParaRemover );
        novaEst.removeFace( novaF );
        
        return novaEst;
    }
            
    public void alteraEstruturaNovaFaceNaoInterna( Face3D f, 
            List<Vertice3D> intersecoes, 
            List<Face3D> facesObj2, List<NovaFace> novasFaces, 
            FiltroVert3D filtro, Objeto3DTO to ) {
        List<GrafoEst3DUtil.GrafoAresta> g_arestas = new ArrayList();
        List<GrafoEst3DUtil.GrafoVertice> g_vertices = new ArrayList();

        List<Vertice3D> verticesProcessados = new ArrayList();
        List<Vertice3D> vertices = f.getVertices();
        
        GrafoEst3DUtil.GrafoVertice gv = new GrafoEst3DUtil.GrafoVertice();
        gv.vIndice = grafoUtil.indiceVerticeNaoInterno( vertices, facesObj2, filtro, to );            
        gv.isIntersecao = false;
        
        if ( gv.vIndice == -1 )
            return; 

        grafoUtil.carregaGrafo( g_vertices, g_arestas, gv, f.getVertices(), intersecoes, filtro, to );                    
        if ( grafoUtil.contaIntersecoes( g_vertices, intersecoes ) > 2 )
            grafoUtil.excluiArestasBordaDoGrafo( g_arestas, f.getVertices(), intersecoes, filtro, to );
        
        Debug debug = (Debug)to.getObjeto();
        debug.g_arestas = g_arestas;
        debug.vertices = new ArrayList( vertices );
        debug.intersecoes = intersecoes;
                
        boolean temVerticeNaoInserido = true;            
        do {     
            Face3D novaF = grafoUtil.geraNovaFace( facesObj2, g_arestas, gv, vertices, intersecoes, verticesProcessados, filtro, to );
            if ( novaF.getVertices().size() >= 3 )
                novasFaces.add( new NovaFace( novaF, f ) );                                                                   
            
            debug.vertices = new ArrayList( novaF.getVertices() );

            
            temVerticeNaoInserido = false;
            int size = vertices.size();
            for ( int i = 0; !temVerticeNaoInserido && i < size; i++ ) {
                Vertice3D v = vertices.get( i );
                if ( !verticesProcessados.contains( v ) ) {
                    double[] ponto = filtro.getPonto3D( v );
                    if ( to.getMath3D().verificaSePontoInternoAObjeto( facesObj2, ponto, filtro ) ||
                            ( to.getMath3D().verificaSePontoEmSuperficie( facesObj2, ponto, filtro ) ) )
                        continue;
                    
                    gv = new GrafoEst3DUtil.GrafoVertice();
                    gv.vIndice = i;
                    gv.isIntersecao = false;

                    temVerticeNaoInserido = true;
                }
            }                      
        } while( temVerticeNaoInserido );
    }
    
    public void alteraEstruturaComEvoltorias( Estrutura3D est, List<Vertice3D> novosVertices, FiltroVert3D filtro, Objeto3DTO to ) {
        List<Face3D> paraAdicionar = new ArrayList();
        List<Face3D> facesVNCopiaParaAdicao = new ArrayList();
        List<Face3D> visitadas = new ArrayList();
        for( Face3D f : est.getFaces() ) {
            if ( visitadas.contains( f ) )
                continue;
                                    
            double[][] plano = to.getMath3D().planoPontos( f, filtro );
            double[] p1 = plano[0];
            double[] p2 = plano[1];
            double[] p3 = plano[2];
                                    
            List<Face3D> faces = new ArrayList();
            estUtil.facesComplanaresDeMesmaFace( faces, f, p1, p2, p3, filtro, to );

            if ( faces.size() <= 1 )
                continue;

            List<Vertice3D> evoltoria = this.evoltoria( faces, p1, p2, p3, filtro, to );
            if ( evoltoria.size() >= 3 ) {
                Face3D novaFace = new Face3D();
                for( Vertice3D v : evoltoria )
                    novaFace.addVertice( v );

                paraAdicionar.add( novaFace );                 
                facesVNCopiaParaAdicao.add( f );                               
                
                visitadas.addAll( faces );
            }
        }
                
        visitadas.forEach( f -> est.removeFace( f ) );
                
        int size = paraAdicionar.size();
        for( int i = 0; i < size; i++ ) {
            Face3D novaFace = paraAdicionar.get( i );            
            Face3D faceVNCopia = facesVNCopiaParaAdicao.get( i );
            est.addFaceECopiarVNDirecao( novaFace, faceVNCopia, filtro, to );
        }        
    }
            
    public List<Vertice3D> evoltoria( List<Face3D> faces, double[] p1, double[] p2, double[] p3, FiltroVert3D filtro, Objeto3DTO to ) {
        List<Face3D> coplanares = to.getMath3D().facesCoplanares( faces, p1, p2, p3, filtro );
        
        Estrutura3D est = new Estrutura3D( null );
        est.reinicia();
        
        for( Face3D f : coplanares ) {
            f.getVertices().forEach( v -> {
                if ( !est.getVertices().contains( v ) )
                    est.addVertice( v ); 
            } );
            est.addFace( f, to ); 
        }
        est.recalculaArestas();
                
        for( Vertice3D v : est.getVertices() ) { 
            List<Aresta3D> pr = new ArrayList();
            for( Aresta3D a : v.getArestas() ) {
                if ( a.getFaces().size() > 1 ) {
                    pr.add( a );
                }   
            }  
            pr.forEach( a -> v.getArestas().remove( a ) );
        } 
        
        List<Vertice3D> paraRemover = new ArrayList();
        est.getVertices().forEach( v -> {
            if ( v.getArestas().isEmpty() )
                paraRemover.add( v );
        } );
                        
        paraRemover.forEach( v -> est.removeVertice( v ) ); 
        
        List<Vertice3D> vertices = new ArrayList();
        for( Vertice3D v : est.getVertices() )
            estUtil.insereVerticeEmOrdem( vertices, v, false, filtro, to );
        
        return vertices;
    }
    
    public void calculaInsersecoes( Estrutura3D est1, Estrutura3D est2, List<Vertice3D> inters, FiltroVert3D filtro, Objeto3DTO to ) {                                        
        for( Face3D f1 : est1.getFaces() ) {                                                                                                                                                                                                          
            int size = f1.getVertices().size();
            for( int i = 0; i < size; i++ ) {
                Vertice3D f1_v1 = f1.getVertices().get( i );
                Vertice3D f1_v2 = f1.getVertices().get( i == size-1 ? 0 : i+1 );
                double[] l1_p0 = filtro.getPonto3D( f1_v1 );
                double[] l1_p = filtro.getPonto3D( f1_v2 );

                for( Face3D f2 : est2.getFaces() ) {                    
                    double[] p_int = to.getMath3D().intersecaoLinhaFace( f2, l1_p0, l1_p, filtro );                    
                    if ( p_int != null ) {
                        if ( !estUtil.verificaSeListaContemVertice( inters, p_int, filtro, to ) ) {
                            Vertice3D p_int_v2 = new Vertice3D( p_int );
                            inters.add( p_int_v2 );                             
                        }
                    }
                }
            }                                                       
        }                              
    }   

    public void eliminaIntersecoesVertice( Estrutura3D est1, Estrutura3D est2, List<Vertice3D> intersecoes, FiltroVert3D filtro, Objeto3DTO to ) {
        List<Vertice3D> paraRemover = new ArrayList();
        for( Vertice3D v : intersecoes ) {
            double[] p = filtro.getPonto3D( v );
            if ( estUtil.verificaSeListaContemVertice( est1.getVertices(), p, filtro, to) ) {
                
                paraRemover.add( v );
            }
            else if ( estUtil.verificaSeListaContemVertice( est2.getVertices(), p, filtro, to ) )
                paraRemover.add( v );
        }            
        intersecoes.removeAll( paraRemover );        
    }
                    
    public static class Debug {
        public List<GrafoEst3DUtil.GrafoAresta> g_arestas;
        public List<Vertice3D> vertices;
        public List<Vertice3D> intersecoes;
        
        public Vertice3D inicial;
    }
    
    public class NovaFace {
        public Face3D nova;
        public Face3D pai;
        
        public NovaFace( Face3D nova, Face3D pai ) {
            this.nova = nova;
            this.pai = pai;
        }
    }
    
}
