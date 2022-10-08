package italo.iplot.plot3d.g3d.est_opers.util;

import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.est_opers.util.OperadorEst3DUtil.Debug;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class ListaEncadeadaEst3DUtil {
    
    private Estrutura3DUtil estUtil;
    
    public ListaEncadeadaEst3DUtil( Estrutura3DUtil estUtil ) {
        this.estUtil = estUtil;
    }
    
    public List<Vertice3D> intersecoes( ListaEncadeada listaENC ) {
        List<Vertice3D> intersecoes = new ArrayList();                        
                        
        Vertice3D prim = listaENC.primeiro.vertice;

        Vertice3D2 perc = listaENC.primeiro;
        boolean continuar = true;
        do {                         
            intersecoes.add( perc.vertice );
            perc = perc.prox;

            continuar = false;
            if ( perc != null ) 
                if ( perc.vertice != prim )
                    continuar = true;
        } while( continuar );
                
        return intersecoes;
    }
    
    public ListaEncadeada geraListaEncadeada( List<Face3D> todasAsFaces, Face3D face, List<Vertice3D> novosVertices, FiltroVert3D filtro, Objeto3DTO to ) {        
        Vertice3D2 v3D2 = new Vertice3D2();
        v3D2.vertice = null;
        
        boolean encontrouV3D2 = false;
        List<Vertice3D> deMesmaFace = new ArrayList();
        for( Vertice3D novoV : novosVertices ) {                       
            double[] ponto = filtro.getPonto3D( novoV );
            
            if ( to.getMath3D().verificaSePontoEhVerticeDaFace( face.getVertices(), ponto, filtro ) )
                continue;            
            
            if ( to.getMath3D().verificaSePontoPertenceAFace( face.getVertices(), ponto, filtro ) ) {
                deMesmaFace.add( novoV );
                if ( !encontrouV3D2 ) {
                    v3D2.vertice = novoV;
                    encontrouV3D2 = true;
                }
            }
        } 
                
        ((Debug)to.getObjeto()).vertices = new ArrayList( deMesmaFace );
                        
        if ( v3D2.vertice != null ) {                                      
            List<Vertice3D> processados = new ArrayList();
            this.geraListaEncadeada( todasAsFaces, face, deMesmaFace, processados, v3D2, filtro, to );
            
            Vertice3D2 anterior = v3D2;
            while( anterior.ant != null )
                anterior = anterior.ant;               
            
            Vertice3D2 proximo = v3D2;
            while( proximo.prox != null )
                proximo = proximo.prox;                                         
            
            ListaEncadeada l = new ListaEncadeada();
            l.primeiro = anterior;
            l.ultimo = proximo;
            l.escolhido = v3D2;
               
            return l;
        }
        return null;
    }
    
    private void geraListaEncadeada( 
            List<Face3D> faces, Face3D f, 
            List<Vertice3D> intersecoes, List<Vertice3D> processados, 
            Vertice3D2 v3D2, 
            FiltroVert3D filtro, Objeto3DTO to ) {            
        
        if ( v3D2.ant != null && v3D2.prox != null )
            return;
                                                           
        Vertice3D v = v3D2.vertice;
        double[] ponto = filtro.getPonto3D( v );
        
        if ( processados.contains( v ) )
            return;
                                                                        
        boolean vizinhosEncontrados = false;
        int size = faces.size();
        int intCopSize = intersecoes.size();
        
        for( int i = 0; !vizinhosEncontrados && i < size; i++ ) {
            Face3D vF = faces.get( i ); 
                             
            if ( to.getMath3D().verificaSeVerticesPertencemAFace( f, vF.getVertices(), filtro ) )
                continue;            
                                    
            if ( !to.getMath3D().verificaSePontoPertenceAFace( vF.getVertices(), ponto, filtro ) )
                continue;                
                                    
            for( int j = 0; !vizinhosEncontrados && j < intCopSize; j++ ) {                             
                Vertice3D vert = intersecoes.get( j );
                if ( vert == v )
                    continue;
                                                                              
                double[] vertP = filtro.getPonto3D( vert );                
                                
                if ( to.getMath3D().verificaSePontoPertenceAFace( vF.getVertices(), vertP, filtro ) ) {                                        
                    Vertice3D2 vizinho = new Vertice3D2();
                    vizinho.vertice = vert;                                  
                    vizinho.face = vF;
                    
                    if ( v3D2.ant == null ) {
                        v3D2.ant = vizinho;
                        vizinho.prox = v3D2;                        
                        vizinho.ant = null;                                        
                    } else {
                        if ( v3D2.prox == null ) {
                            v3D2.prox = vizinho;
                            vizinho.ant = v3D2;
                            vizinho.prox = null;
                        }                       
                    }                                                                                               

                    vizinhosEncontrados = ( v3D2.ant != null && v3D2.prox != null );
                }
                
            }                   
        }        

        processados.add( v3D2.vertice );
        intersecoes.remove( v3D2.vertice );        
        
        if ( v3D2.ant != null )
            intersecoes.remove( v3D2.ant.vertice );
        if ( v3D2.prox != null )
            intersecoes.remove( v3D2.prox.vertice );
                                  
        if ( v3D2.ant != null )                   
            this.geraListaEncadeada( faces, f, intersecoes, processados, v3D2.ant, filtro, to );                
        if ( v3D2.prox != null )
            this.geraListaEncadeada( faces, f, intersecoes, processados, v3D2.prox, filtro, to );                                                        
    }        
    
    public class ListaEncadeada {
        public Vertice3D2 escolhido;
        public Vertice3D2 primeiro;
        public Vertice3D2 ultimo;                
    }
    
    public class Vertice3D2 {
        public Vertice3D vertice = null;
        public Vertice3D2 ant = null;
        public Vertice3D2 prox = null;  
        public Face3D face;
    }
    
}
