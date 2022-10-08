package italo.iplot.plot3d.g3d.util;

import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Aresta3D;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class Cortador3D {
    
    private final Estrutura3DUtil util;
    
    public Cortador3D( Estrutura3DUtil util ) {
        this.util = util;
    }
           
    public void corte( Objeto3D container, Estrutura3D est, boolean addNovaFace, boolean cortarNovasArestas, FiltroVert3D filtro, Objeto3DTO to ) {        
        List<Face3D> faces = container.getEstrutura().getFaces();
        boolean cortarVertices = est.getFaces().isEmpty() && est.getOutrasArestas().isEmpty();
        
        est.recalculaArestas();
        this.cortaFaces( faces, est, addNovaFace, cortarNovasArestas, filtro, to );                
        this.cortaOutrasArestas( faces, est, filtro, to );                
                         
        if ( cortarVertices )
            this.cortaVertices( faces, est, filtro, to );                                                           
    }
    
    public void cortaVertices( List<Face3D> faces, Estrutura3D est, FiltroVert3D filtro, Objeto3DTO to ) {
        for( Face3D f : faces ) {
            double[][] pontos = to.getMath3D().planoPontos( f, filtro );            
            this.cortaVertices( est, pontos, f.isInverterVN(), filtro, to );
        }        
    }
    
    public void cortaOutrasArestas( List<Face3D> faces, Estrutura3D est, FiltroVert3D filtro, Objeto3DTO to ) {
        for( Face3D f : faces ) {
            double[][] pontos = to.getMath3D().planoPontos( f, filtro );            
            this.cortaOutrasArestas( est, pontos, f.isInverterVN(), filtro, to );
        }        
    }
    
    public void cortaFaces( List<Face3D> faces, Estrutura3D est, boolean addNovaFace, boolean cortarNovasArestas, FiltroVert3D filtro, Objeto3DTO to ) {        
        List<Vertice3D> novosVertices = null;
        if ( cortarNovasArestas )
            novosVertices = new ArrayList();
                
        for( Face3D f : faces ) {
            double[][] pontos = to.getMath3D().planoPontos( f, filtro );            
            this.cortaFace( est, pontos, f.isInverterVN(), addNovaFace, novosVertices, filtro, to );
        }                               
        
        if ( cortarNovasArestas ) {
            for( Aresta3D a : est.getArestas() ) {
                int vi = util.buscaIndiceDeVertice( novosVertices, a.getV1() );
                if ( vi != -1 )
                    vi = util.buscaIndiceDeVertice( novosVertices, a.getV2() );
                
                if ( vi != -1 ) {
                    a.setNovaAresta( true ); 
                }
            }
        }        
    }
        
    public Face3D cortaFace( Estrutura3D est, double[][] planoPontos, boolean inverterVN, boolean addNovaFace, List<Vertice3D> novosVertices, FiltroVert3D filtro, Objeto3DTO to ) {                
        double[] p1 = planoPontos[0];
        double[] p2 = planoPontos[1];
        double[] p3 = planoPontos[2];                
        
        Face3D novaFace = new Face3D();
                
        for( Aresta3D a : est.getArestas() ) {
            double[] lp0 = filtro.getPonto3D( a.getV1() );
            double[] lp = filtro.getPonto3D( a.getV2() );
            if ( to.getMath3D().verificaSeRetaEParalelaAPlano( lp0, lp, p1, p2, p3 ) )                
                continue;
                                                 
            double[] p_int = to.getMath3D().intersecaoRetaPlano( lp0, lp, p1, p2, p3 );            
            if ( p_int != null ) {
                if ( to.getMath3D().verificaSeParalelepipedoContemPonto( lp0, lp, p_int ) ) {
                    if ( !to.getMath3D().verificaSeIguais( lp0, p_int ) && !to.getMath3D().verificaSeIguais( lp, p_int ) ) {
                        Vertice3D v = new Vertice3D( p_int );
                        boolean inserirVertice = true;
                        
                        for( Face3D f : a.getFaces() ) {                      
                            if ( !util.verificaSeListaContemVertice( f.getVertices(), p_int, filtro, to ) ) {
                                util.insereVerticeEntre( f, a.getV1(), a.getV2(), v );
                            } else inserirVertice = false;
                        }

                        if ( !util.verificaSeListaContemVertice( novaFace.getVertices(), p_int, filtro, to ) )
                            util.insereVerticeEmOrdem( novaFace, v, true, filtro, to );

                        if ( inserirVertice ) {
                            est.addVertice( v ); 
                            if ( novosVertices != null )
                                novosVertices.add( v );
                        }
                    }
                    
                }                   
            }                   
        }               
                
        if ( addNovaFace )
            if ( !novaFace.getVertices().isEmpty() )
                est.addFaceECopiarVNDirecao( novaFace, planoPontos, inverterVN, filtro, to );        
                        
        this.cortaVertices( est, planoPontos, inverterVN, filtro, to );
        
        List<Face3D> facesParaRemover = new ArrayList();
        for( Face3D f : est.getFaces() )
            if ( f.getVertices().size() < 3 )
                facesParaRemover.add( f );
                    
        facesParaRemover.forEach( f -> est.removeEstruturaFace( f ) );                    
        
        est.recalculaArestas();                
                                
        return novaFace;
    }
    
    public void cortaOutrasArestas( Estrutura3D est, double[][] planoPontos, boolean inverterVN, FiltroVert3D filtro, Objeto3DTO to ) {                
        double[] p1 = planoPontos[0];
        double[] p2 = planoPontos[1];
        double[] p3 = planoPontos[2];                
                
        int size = est.getOutrasArestas().size();
        for( int i = 0; i < size; i++ ) {
            Aresta3D a = est.getOutrasArestas().get( i );
            double[] lp0 = filtro.getPonto3D( a.getV1() );
            double[] lp = filtro.getPonto3D( a.getV2() );
            if ( to.getMath3D().verificaSeRetaEParalelaAPlano( lp0, lp, p1, p2, p3 ) )                
                continue;
                                                
            double[] p_int = to.getMath3D().intersecaoRetaPlano( lp0, lp, p1, p2, p3 );
            if ( p_int != null ) {
                if ( to.getMath3D().verificaSeParalelepipedoContemPonto( lp0, lp, p_int ) ) {
                    if ( !to.getMath3D().verificaSeIguais( lp0, p_int ) && !to.getMath3D().verificaSeIguais( lp, p_int ) ) {                                           
                        Vertice3D v = util.verticeJaExistente( est.getVertices(), p_int , filtro, to );
                        if ( v == null) {
                            v = new Vertice3D( p_int );
                            est.addVertice( v );         
                        }

                        est.addOutrasAresta( new Aresta3D( est.getObjeto(), a.getV1(), v ) );
                        est.addOutrasAresta( new Aresta3D( est.getObjeto(), v, a.getV2() ) );
                        est.removeOutrasAresta( a );                                            
                        i--;
                        size--;
                    }
                }
            }
        }       
                    
        this.cortaVertices( est, planoPontos, inverterVN, filtro, to );        
    }
    
    public void cortaVertices( Estrutura3D est, double[][] planoPontos, boolean inverterVN, FiltroVert3D filtro, Objeto3DTO to ) { 
        double[] p1 = planoPontos[0];
        double[] p2 = planoPontos[1];
        double[] p3 = planoPontos[2];    
        double[] fvn = to.getMath3D().vetorNormal( p1, p2, p3, inverterVN );
                
        List<Vertice3D> verticesParaRemover = new ArrayList();
        for( Vertice3D v : est.getVertices() ) {
            double[] p_v = filtro.getPonto3D( v );
            p_v = to.getTransformador3D().sub( p_v, p2, filtro );
            double a = to.getMath3D().angulo( fvn, p_v );
            if ( a < Math.PI*.5d )
                verticesParaRemover.add( v );                                    
        }                
        verticesParaRemover.forEach( v -> est.removeEstruturaVertice( v ) );
    }
    
}
