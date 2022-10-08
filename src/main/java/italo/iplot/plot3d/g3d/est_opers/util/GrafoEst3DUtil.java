package italo.iplot.plot3d.g3d.est_opers.util;

import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class GrafoEst3DUtil {
    
    private Estrutura3DUtil util;
    
    public GrafoEst3DUtil( Estrutura3DUtil util ) {
        this.util = util;
    }
        
    public Face3D geraNovaFace( List<Face3D> facesObj2, List<GrafoAresta> g_arestas, GrafoVertice gv, 
            List<Vertice3D> vertices, List<Vertice3D> intersecoes,
            List<Vertice3D> verticesProcessados, 
            FiltroVert3D filtro, Objeto3DTO to ) {
        
        Face3D f = new Face3D();
        
        Vertice3D inicialV;
        if ( gv.isIntersecao ) {
            inicialV = intersecoes.get( gv.vIndice );
        } else {
            inicialV = vertices.get( gv.vIndice );                            
            if ( !verticesProcessados.contains( inicialV ) )
                verticesProcessados.add( inicialV );
        }
        f.getVertices().add( inicialV );
        
        GrafoVertice inicial = gv;
        GrafoVertice atual = inicial;
        do {                        
            List<GrafoVertice> vizinhos = this.grafoVizinhos( g_arestas, atual.vIndice, atual.isIntersecao );
            GrafoVertice escolhido = null;
            int size = vizinhos.size();
            for( int i = 0; escolhido == null && i < size; i++ ) {
                GrafoVertice vizinho = vizinhos.get( i );                
                if ( atual.isIntersecao && vizinho.isIntersecao )
                    if ( this.verificaSeVIntersContidosEmAresta( vertices, intersecoes, atual, vizinho, filtro, to ) )
                        continue;                
                
                Vertice3D vizinhoV = this.getGVertice( vertices, intersecoes, vizinho );
                if ( f.getVertices().contains( vizinhoV ) )
                    continue;
                
                double[] p = filtro.getPonto3D( vizinhoV );
                if ( to.getMath3D().verificaSePontoInternoAObjeto( facesObj2, p, filtro ) || 
                        ( !vizinho.isIntersecao && to.getMath3D().verificaSePontoEmSuperficie( facesObj2, p, filtro ) ) ) {
                    verticesProcessados.add( vizinhoV );
                    continue;
                }                                
                                                                                
                escolhido = vizinho;                
            } 
            
            if ( escolhido != null && !this.verificaSeGVIguais( escolhido, inicial ) ) {                    
                Vertice3D v;
                if ( escolhido.isIntersecao ) {
                    v = intersecoes.get( escolhido.vIndice );
                } else {
                    v = vertices.get( escolhido.vIndice );                                        
                    if( !verticesProcessados.contains( v ) )
                        verticesProcessados.add( v );
                }                                
                
                f.getVertices().add( v );                                
            }                
                        
            atual = escolhido;
        } while( atual != null && !this.verificaSeGVIguais( atual, inicial ) );
        
        return f;
    }
            
    public int indiceVerticeNaoInterno( List<Vertice3D> vertices, List<Face3D> facesObj2, FiltroVert3D filtro, Objeto3DTO to ) {
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            Vertice3D v = vertices.get( i );
            double[] ponto = filtro.getPonto3D( v );
            if ( !to.getMath3D().verificaSePontoInternoAObjeto( facesObj2, ponto, filtro ) && 
                    ( !to.getMath3D().verificaSePontoEmSuperficie( facesObj2, ponto, filtro ) ) )
                return i;            
        }
        return -1;
    }
    
    public int contaIntersecoes( List<GrafoVertice> g_vertices, List<Vertice3D> intersecoes ) {
        int cont = 0;        
        for( GrafoVertice gv : g_vertices )
            if ( gv.isIntersecao )
                cont++;                           
        return cont;
    }
    
    public void excluiArestasBordaDoGrafo( List<GrafoAresta> g_arestas, List<Vertice3D> vertices, List<Vertice3D> intersecoes, FiltroVert3D filtro, Objeto3DTO to ) {
        List<GrafoAresta> paraRemover = new ArrayList();
        for( GrafoAresta ga : g_arestas ) {                        
            if ( !ga.gv1.isIntersecao || !ga.gv2.isIntersecao )
                continue;
            
            Vertice3D v1 = this.getGVertice( vertices, intersecoes, ga.gv1 );
            Vertice3D v2 = this.getGVertice( vertices, intersecoes, ga.gv2 );
                     
            int[] a_col_v1 = this.arestaColinearComVInter( vertices, v1, filtro, to );
            int[] a_col_v2 = this.arestaColinearComVInter( vertices, v2, filtro, to );
            
            if ( a_col_v1 != null && a_col_v2 != null ) {
                if ( ( a_col_v1[0] != a_col_v2[0] || a_col_v1[1] == a_col_v2[1] ) &&
                        ( a_col_v1[0] != a_col_v2[1] || a_col_v1[1] != a_col_v2[0] ) ) {
                    paraRemover.add( ga );
                }
            }            
        }
        
        paraRemover.forEach( ga -> g_arestas.remove( ga ) );
    }
            
    public void carregaGrafo( List<GrafoVertice> g_vertices, List<GrafoAresta> g_arestas, GrafoVertice gv, 
            List<Vertice3D> vertices, List<Vertice3D> intersecoes, 
            FiltroVert3D filtro, Objeto3DTO to ) {
                
        if ( !gv.visitado ) {                                    
            gv.visitado = true;
            
            int i = gv.vIndice;
            
            if ( gv.isIntersecao ) {
                Vertice3D v = intersecoes.get( i );
                
                int antI  = ( i == 0 ? intersecoes.size()-1 : i-1 );
                int proxI = ( i == intersecoes.size()-1 ? 0 : i+1 );
                                         
                GrafoVertice novoGV;
                novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, antI, true );
                if ( novoGV != null )
                    this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to);    

                novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, proxI, true );
                if ( novoGV != null )
                    this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to);                    
                
                int[] arestaIDs = this.arestaColinearComVInter( vertices, v, filtro, to );
                if ( arestaIDs != null ) {                  
                    Vertice3D antV = intersecoes.get( antI );
                    Vertice3D proxV = intersecoes.get( proxI );
                                                                       
                    int v1_i = arestaIDs[0];
                    int v2_i = arestaIDs[1];
                    
                    Vertice3D v1 = vertices.get( v1_i );
                    Vertice3D v2 = vertices.get( v2_i );
                    
                    double[] v_p = filtro.getPonto3D( v );
                    
                    double[] ant_p = filtro.getPonto3D( antV );
                    double[] prox_p = filtro.getPonto3D( proxV );
                    
                    double[] v1_p = filtro.getPonto3D( v1 );
                    double[] v2_p = filtro.getPonto3D( v2 );
                                                            
                    boolean ant_colinear = to.getMath3D().verificaSeColineares( v1_p, v2_p, ant_p );
                    boolean prox_colinear = to.getMath3D().verificaSeColineares( v1_p, v2_p, prox_p );
                    boolean add_v1 = true;
                    boolean add_v2 = true;
                    
                    if ( ant_colinear || prox_colinear ) {
                        if ( ant_colinear ) {
                            if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v_p, v1_p, ant_p ) )
                                add_v1 = false;
                            if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v_p, v2_p, ant_p ) )
                                add_v2 = false;
                        }
                        
                        if ( prox_colinear ) {
                            if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v_p, v1_p, prox_p ) )
                                add_v1 = false;     
                            if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v_p, v2_p, prox_p ) )
                                add_v2 = false;     
                        }
                    }
                                        
                    if ( add_v1 ) {
                        novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, v1_i, false );
                        if ( novoGV != null )
                            this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );                                                                                                                                
                    }
                    
                    if ( add_v2 ) {
                        novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, v2_i, false );
                        if ( novoGV != null )
                            this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );
                    }                     
                }
            } else {
                int antI  = ( i == 0 ? vertices.size()-1 : i-1 );
                int proxI = ( i == vertices.size()-1 ? 0 : i+1 ); 
                
                Vertice3D v = vertices.get( i );
                Vertice3D antV = vertices.get( antI );
                Vertice3D proxV = vertices.get( proxI );
    
                int proxInterI = this.proximaIntersecao( v, antV, intersecoes, filtro, to );
                
                if ( proxInterI == -1 ) {
                    GrafoVertice novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, antI, false );
                    if ( novoGV != null )
                        this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );                                                                                                        
                } else {
                    GrafoVertice novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, proxInterI, true );
                    if ( novoGV != null )
                        this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );                    
                }   
                
                proxInterI = this.proximaIntersecao( v, proxV, intersecoes, filtro, to );
                
                if ( proxInterI == -1 ) {
                    GrafoVertice novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, proxI, false );
                    if ( novoGV != null )
                        this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );                                                                                                        
                } else {
                    GrafoVertice novoGV = this.addGrafoAresta( g_vertices, g_arestas, gv, proxInterI, true );
                    if ( novoGV != null )
                        this.carregaGrafo( g_vertices, g_arestas, novoGV, vertices, intersecoes, filtro, to );                    
                }                    
            }            
        }
    }
            
    private int[] arestaColinearComVInter( List<Vertice3D> vertices, Vertice3D v, FiltroVert3D filtro, Objeto3DTO to ) {                
        double[] vp1 = filtro.getPonto3D( v );
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            int j = ( i == size-1 ? 0 : i+1 );
            Vertice3D v2 = vertices.get( i );
            Vertice3D v3 = vertices.get( j );
            double[] vp2 = filtro.getPonto3D( v2 );
            double[] vp3 = filtro.getPonto3D( v3 );
            
            if ( to.getMath3D().verificaSeColineares( vp2, vp3, vp1 ) )
                return new int[] { i, j };            
        }
        return null;
    }
            
    private int proximaIntersecao( Vertice3D v, Vertice3D outroV, List<Vertice3D> intersecoes, FiltroVert3D filtro, Objeto3DTO to ) {
        double[] vp1 = filtro.getPonto3D( v );
        double[] vp2 = filtro.getPonto3D( outroV );
        double minD = Double.MAX_VALUE;
        int minDI = -1;
        int size = intersecoes.size();
        for( int i = 0; i < size; i++ ) {
            Vertice3D interV = intersecoes.get( i );
            double[] vp3 = filtro.getPonto3D( interV );
            if ( to.getMath3D().verificaSeColineares( vp1, vp2, vp3 ) ) {
                double d = to.getMath3D().distancia( vp1, vp3 );
                if ( d < minD ) {
                    minDI = i;
                    minD = d;
                }
            }
        }
        return minDI;
    }
    
    public GrafoVertice addGrafoAresta( List<GrafoVertice> g_vertices, List<GrafoAresta> g_arestas, GrafoVertice gv, int vIndice, boolean isIntersecao ) {                                   
        if ( !this.verificaSeArestaExiste( g_arestas, gv.vIndice, gv.isIntersecao, vIndice, isIntersecao ) ) {            
            boolean novo = false;
            
            GrafoVertice grafoV = this.grafoVertice( g_vertices, vIndice, isIntersecao );
            if ( grafoV == null ) {
                grafoV = new GrafoVertice();                
                novo = true;
            }
            grafoV.vIndice = vIndice;
            grafoV.isIntersecao = isIntersecao;    
            
            g_arestas.add( new GrafoAresta( gv, grafoV ) );
            
            if ( novo ) {
                g_vertices.add( grafoV );
                return grafoV;
            }
        }
        return null;
    }
    
    public boolean verificaSeVIntersContidosEmAresta( List<Vertice3D> vertices, List<Vertice3D> intersecoes, 
            GrafoVertice gv, GrafoVertice gvizinho, 
            FiltroVert3D filtro, Objeto3DTO to ) {
        
        if ( gv.isIntersecao && gvizinho.isIntersecao ) {
            Vertice3D gv_v = this.getGVertice( vertices, intersecoes, gv );                
            Vertice3D gvizinho_v = this.getGVertice( vertices, intersecoes, gvizinho );

            double[] gv_p = filtro.getPonto3D( gv_v );
            double[] gvizinho_p = filtro.getPonto3D( gvizinho_v );

            int size = vertices.size();
            for( int i = 0; i < size; i++ ) {
                Vertice3D v1 = vertices.get( i );
                Vertice3D v2 = vertices.get( i==size-1 ? 0 : i+1 );
                double[] v1_p = filtro.getPonto3D( v1 );
                double[] v2_p = filtro.getPonto3D( v2 );

                if ( to.getMath3D().verificaSeColineares( v1_p, v2_p, gv_p ) && to.getMath3D().verificaSeColineares( v1_p, v2_p, gvizinho_p ) )
                    return true;            
            }
        }
        return false;
    }
    
    public Vertice3D getGVertice( List<Vertice3D> vertices, List<Vertice3D> intersecoes, GrafoVertice gv ) {
        if ( gv.isIntersecao )
            return intersecoes.get( gv.vIndice );
        return vertices.get( gv.vIndice );
    }
    
    public GrafoVertice buscaGrafoVertice( List<GrafoVertice> g_vertices, List<Vertice3D> vertices, List<Vertice3D> intersecoes, Vertice3D v ) {
        for( GrafoVertice g_vert : g_vertices ) {
            Vertice3D v2 = this.getGVertice( vertices, intersecoes, g_vert );
            if ( v == v2 )
                return g_vert;            
        }
        return null;
    }
            
    public List<GrafoVertice> grafoVizinhos( List<GrafoAresta> arestas, int vIndice, boolean isIntersecao ) { 
        List<GrafoVertice> gverts = new ArrayList();
        for( GrafoAresta a : arestas ) {
            if ( ( a.gv1.isIntersecao == isIntersecao && a.gv1.vIndice == vIndice ) || ( a.gv2.isIntersecao == isIntersecao && a.gv2.vIndice == vIndice ) ) {
                if ( a.gv1.isIntersecao == isIntersecao && a.gv1.vIndice == vIndice ) {
                    gverts.add( a.gv2 );
                } else {
                    gverts.add( a.gv1 );
                }
            }
        }
        return gverts;
    }
        
    public GrafoVertice grafoVertice( List<GrafoVertice> g_vertices, int vIndice, boolean isIntersecao ) {
        for( GrafoVertice gv : g_vertices )
            if ( gv.isIntersecao == isIntersecao && gv.vIndice == vIndice )
                return gv;
        return null;
    }
    
    public boolean verificaSeArestaExiste(List<GrafoAresta> arestas, int vIndice, boolean isIntersecao, int vIndice2, boolean isIntersecao2 ) {
        for( GrafoAresta ga : arestas ) {
            if ( ( ga.gv1.vIndice == vIndice && ga.gv1.isIntersecao == isIntersecao && ga.gv2.vIndice == vIndice2 && ga.gv2.isIntersecao == isIntersecao2 ) || 
                    ( ga.gv2.vIndice == vIndice && ga.gv2.isIntersecao == isIntersecao && ga.gv1.vIndice == vIndice2 && ga.gv1.isIntersecao == isIntersecao2 ) ) {
                return true;
            }
        }
        return false;
    }
    
    public boolean verificaSeGVIguais( GrafoVertice gv1, GrafoVertice gv2 ) {
        return gv1.vIndice == gv2.vIndice && gv1.isIntersecao == gv2.isIntersecao;
    }
        
    public static class GrafoVertice {
        public boolean isIntersecao = false;
        public boolean visitado = false;
        public int vIndice = -1;                
    }        
       
    public static class GrafoAresta {
        public GrafoVertice gv1;
        public GrafoVertice gv2;
        
        public GrafoAresta(GrafoVertice v1, GrafoVertice v2) {
            this.gv1 = v1;
            this.gv2 = v2;
        }
    }
    
}
