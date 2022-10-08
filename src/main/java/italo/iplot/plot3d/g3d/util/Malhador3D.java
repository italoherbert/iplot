/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package italo.iplot.plot3d.g3d.util;

import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class Malhador3D {
    
    public void malha( Objeto3D obj, FiltroVert3D filtro, Objeto3DTO to ) {        
        List<Face3D> faces = new ArrayList( obj.getEstrutura().getFaces().size() );
        faces.addAll( obj.getEstrutura().getFaces() );
        for( Face3D f : faces ) {
            List<Vertice3D[]> arestas = new ArrayList();
            this.malha( obj.getEstrutura(), f, arestas, filtro, to );  
        }
        
        obj.getEstrutura().recalculaArestas();
    }
    
    public void malha( Estrutura3D est, Face3D f, List<Vertice3D[]> arestas, FiltroVert3D filtro, Objeto3DTO to ) {
        int size = f.getVertices().size();
        if ( size <= 3 )
            return;         
                                          
        List<int[]> lista = new ArrayList();
        
        for( int i = 0; i < size; i++ ) {
            int v1_i = i;
            
            int ant_i = ( i == 0 ? size-1 : i-1 );
            int pos_i = ( i == size-1 ? 0 : i+1 );
                    
            Vertice3D v1 = f.getVertices().get( v1_i );
            double[] v1_p = filtro.getPonto3D( v1 );
            for( int j = 0; j < size; j++ ) {
                if( j == i || j == ant_i || j == pos_i )
                    continue;
                
                int v2_i = j;                                                
                Vertice3D v2 = f.getVertices().get( v2_i );                
                
                if ( this.arestaJaEscolhida( arestas, v1, v2 ) )
                    continue;
                
                double[] v2_p = filtro.getPonto3D( v2 );
                
                boolean cruzam = false;
                for( int k = 0; !cruzam && k < size; k++ ) {
                    Vertice3D v3 = f.getVertices().get( k );
                    Vertice3D v4 = f.getVertices().get( k == size-1 ? 0 : k+1 );
                    
                    if ( ( v3 == v1 && v4 == v2 ) || ( v4 == v1 && v3 == v2 ) )
                        continue;
                                       
                    double[] v3_p = filtro.getPonto3D( v3 );
                    double[] v4_p = filtro.getPonto3D( v4 );
                                               
                    if ( to.getMath3D().verificaSeColineares( v1_p, v2_p, v3_p ) &&
                            to.getMath3D().verificaSeColineares( v1_p, v2_p, v4_p ) ) {
                        cruzam = true; 
                    } else {
                        double[] p_int = to.getMath3D().intersecaoRetas( v1_p, v2_p, v3_p, v4_p );
                        if ( p_int != null ) {
                            if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v1_p, v2_p, p_int ) && 
                                    to.getMath3D().verificaSeParalelepipedoContemPonto( v3_p, v4_p, p_int ) ) { 
                                if ( !to.getMath3D().verificaSeIguais( p_int, v1_p ) && 
                                        !to.getMath3D().verificaSeIguais( p_int, v2_p ) &&
                                        !to.getMath3D().verificaSeIguais( p_int, v3_p ) && 
                                        !to.getMath3D().verificaSeIguais( p_int, v4_p )  ) {
                                    cruzam = true;                                      
                                }
                            }
                        }
                    }
                }
                
                if ( !cruzam ) {
                    double[] medio_p = to.getMath3D().div( to.getMath3D().soma( v1_p, v2_p ), 2 );
                                        
                    if ( to.getMath3D().verificaSePontoPertenceAFace( f.getVertices(), medio_p, filtro ) )
                        lista.add( new int[]{ v1_i, v2_i } );                    
                }
            }
        }             
        
        if ( !lista.isEmpty() ) {
            int[] proximosIs = null;
            double minD = Double.MAX_VALUE;
            for( int[] is : lista ) {
                Vertice3D v1 = f.getVertices().get( is[0] );
                Vertice3D v2 = f.getVertices().get( is[1] );
                double[] v1_p = filtro.getPonto3D( v1 );
                double[] v2_p = filtro.getPonto3D( v2 );
                double d = to.getMath3D().distancia( v1_p, v2_p );
                if ( d < minD ) {
                    proximosIs = is;
                    minD = d;
                }
            }
            
            int v1_i = proximosIs[0];
            int v2_i = proximosIs[1];
                        
            Face3D novaFace1 = new Face3D();
            boolean continuar = true;
            int i = v1_i;
            while( continuar ) {
                novaFace1.addVertice( f.getVertices().get( i ) );
                if ( i == v2_i ) {
                    continuar = false;
                } else {
                    i = ( i == size-1 ? 0 : i+1 );
                }
            }

            Face3D novaFace2 = new Face3D();
            continuar = true;
            i = v2_i;
            while( continuar ) {
                novaFace2.addVertice( f.getVertices().get( i ) );
                if ( i == v1_i ) {
                    continuar = false;
                } else {
                    i = ( i == size-1 ? 0 : i+1 );
                }
            }

            est.removeFace( f );

            est.addFaceECopiarVNDirecao( novaFace1, f, filtro, to );
            est.addFaceECopiarVNDirecao( novaFace2, f, filtro, to );
                        
            Vertice3D v1 = f.getVertices().get( v1_i );
            Vertice3D v2 = f.getVertices().get( v2_i );
            arestas.add( new Vertice3D[]{ v1, v2 } );
                        
            this.malha( est, novaFace1, arestas, filtro, to ); 
            this.malha( est, novaFace2, arestas, filtro, to );                     
        }
    }    
    
    private boolean arestaJaEscolhida( List<Vertice3D[]> arestas, Vertice3D v1, Vertice3D v2 ) {
        for( Vertice3D[] a : arestas )
            if ( ( a[0] == v1 && a[1] == v2 ) || ( a[0] == v2 && a[1] == v1 ) )
                return true;
        return false;
    }
    
}
