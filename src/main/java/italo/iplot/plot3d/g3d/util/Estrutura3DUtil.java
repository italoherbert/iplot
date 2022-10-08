
package italo.iplot.plot3d.g3d.util;

import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;


public class Estrutura3DUtil {
                
    public boolean verificaSeCoplanarAAlgumPlano( Face3D f, List<double[][]> planos, FiltroVert3D filtro, Objeto3DTO to ) {
        for( double[][] plano : planos )
            if ( to.getMath3D().verificaSeVerticesPertencemAPlano( f.getVertices(), plano[0], plano[1], plano[2], filtro ) )
                return true;
        return false;
    }
            
    /*
    private void insereVerticesEmFacesEmOrdem( List<Face3D> faces, List<Vertice3D> lista, Objeto3DTO to, FiltroVert3D filtro, boolean apenasSeComplanar ) {        
        for( Face3D f : faces ) {
            this.insereVerticesEmOrdem( f.getVertices(), lista, apenasSeComplanar, filtro, to );        
            for( Vertice3D v : lista )
                v.addFace( f ); 
        }
    }    

    private void insereVerticesEmOrdem( Face3D f, List<Vertice3D> lista, boolean apenasSeCoplanar, FiltroVert3D filtro, Objeto3DTO to ) {        
        this.insereVerticesEmOrdem( f.getVertices(), lista, apenasSeCoplanar, filtro, to );
        for( Vertice3D v : lista )
            v.addFace( f );
    }
    */
    
    public boolean insereVerticeEmOrdem( Face3D f, Vertice3D v, boolean apenasSeCoplanar, FiltroVert3D filtro, Objeto3DTO to ) {
        boolean inseriu = this.insereVerticeEmOrdem( f.getVertices(), v, apenasSeCoplanar, filtro, to );
        v.addFace( f );   
        
        return inseriu;
    }
    
    public boolean insereVerticesEmOrdem( List<Vertice3D> vertices, List<Vertice3D> lista, boolean apenasSeCoplanar, FiltroVert3D filtro, Objeto3DTO to ) {        
        int i = 0;
        int fsize = vertices.size();        
        if ( 3 - fsize > 0 ) {
            int q = 3 - fsize;
            for( ; i < q; i++ )
                vertices.add( lista.get( i ) );
        }
        
        boolean inseriu = true;
        
        int size = lista.size();          
        for( ;inseriu && i < size; i++ ) {
            Vertice3D v = lista.get( i );
            inseriu = this.insereVerticeEmOrdem( vertices, v, apenasSeCoplanar, filtro, to );
        }
        
        return inseriu;
    }
    
    public boolean insereVerticeEmOrdem( List<Vertice3D> vertices, Vertice3D v, boolean apenasSeCoplanar, FiltroVert3D filtro, Objeto3DTO to ) {
        int fsize = vertices.size();        
        double[] ponto = filtro.getPonto3D( v ); 

        boolean inserir = true;
        if ( apenasSeCoplanar && fsize >= 3 ) {           
            double[][] plano_pontos = to.getMath3D().planoPontos( vertices, filtro );
            double[] p1 = plano_pontos[0];
            double[] p2 = plano_pontos[1];
            double[] p3 = plano_pontos[2];

            inserir = to.getMath3D().verificaSePontoPertenceAPlano( ponto, p1, p2, p3 );
        }
        
        if ( fsize < 2 ) {
            vertices.add( v );
            return true;
        }

        if ( inserir ) {
            Vertice3D minV1 = null;
            Vertice3D minV2 = null;
            double minD = Double.MAX_VALUE;
            for( int j = 1; j < fsize+1; j++ ) {       
                Vertice3D v1 = vertices.get( j-1 );
                Vertice3D v2 = vertices.get( j == fsize ? 0 : j );
                double[] v_lp0 = filtro.getPonto3D( v1 );
                double[] v_lp = filtro.getPonto3D( v2 ); 

                double[] u_vet = to.getMath3D().sub( v_lp, v_lp0 );
                double[] v_vet = to.getMath3D().sub( ponto, v_lp0 );

                double[] w_vet = to.getMath3D().projecaoDeVSobreU( u_vet, v_vet );
                double[] pw = to.getMath3D().soma( v_lp0, w_vet );

                if ( to.getMath3D().verificaSeParalelepipedoContemPonto( v_lp0, v_lp, pw ) ) {                        
                    double d = to.getMath3D().distanciaPontoReta( ponto, v_lp0, v_lp );
                    if ( d < minD ) {
                        minD = d;
                        minV1 = v1;
                        minV2 = v2;
                    }
                }
            }

            if ( minV1 != null )
                return this.insereVerticeEntre( vertices, minV1, minV2, v );                                            
        }
        return false;
    }
    
    public void removeVertice( Estrutura3D est, Vertice3D v, boolean removerFacesQueTemVertices ) {
        if ( v.getFaces() != null ) {            
            v.getFaces().forEach( f -> {                
                f.removeVertice( v );
                if ( removerFacesQueTemVertices )
                    est.removeFace( f );
            } );
        }
        if ( v.getArestas() != null ) {
            v.getArestas().forEach( a -> {
                est.removeOutrasAresta( a );
            } );
        }
        est.removeVertice( v );
    }
    
    public List<Vertice3D> listaVerticesInternos( List<Vertice3D> vertsObj1, List<Face3D> facesObj2, FiltroVert3D filtro, Objeto3DTO to ) {        
        List<Vertice3D> lista = new ArrayList();
        for( Vertice3D v : vertsObj1 ) {
            double[] p = filtro.getPonto3D( v ); 
            if ( to.getMath3D().verificaSePontoInternoAObjeto( facesObj2, p, filtro ) )
                lista.add( v );                                    
        }
        return lista;
    }        
    
    public Vertice3D verticeJaExistente( List<Vertice3D> vertices, double[] p, FiltroVert3D filtro, Objeto3DTO to ) {        
        for( Vertice3D v : vertices ) {
            double[] p2 = filtro.getPonto3D( v );
            if ( to.getMath3D().verificaSeIguais( p, p2 ) )
                return v;
        }
        return null;
    }
    
    public boolean verificaSeListaContemVertice( List<Vertice3D> lista, double[] vert, FiltroVert3D filtro, Objeto3DTO to ) {
        for( Vertice3D v : lista ) {
            double[] p = filtro.getPonto3D( v );
            if ( to.getMath3D().verificaSeIguais( vert, p ) )
                return true;
        }
        return false;
    }
    
    public boolean insereVerticesEntre( Face3D f, Vertice3D v1, Vertice3D v2, List<Vertice3D> lista ) {
        boolean inseriu = this.insereVerticesEntre( f.getVertices(), v1, v2, lista );
        if ( inseriu )
            for( Vertice3D v : lista )
                v.addFace( f );         
        return inseriu;
    }        
    
    public boolean insereVerticesEntre( List<Vertice3D> vertices, Vertice3D v1, Vertice3D v2, List<Vertice3D> lista ) {
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            int indice = i == size-1 ? 0 : i+1;
            Vertice3D fv1 = vertices.get( i );
            Vertice3D fv2 = vertices.get( indice );
            if ( ( fv1 == v1 || fv1 == v2 ) && ( fv2 == v1 || fv2 == v2 ) ) {                
                int listaSize = lista.size();
                for( int j = listaSize-1; j >= 0; j-- )
                    vertices.add( indice, lista.get( j ) );                 
                return true;
            }
        }
        return false;
    }
    
    public boolean insereVerticeEntre( Face3D f, Vertice3D v1, Vertice3D v2, Vertice3D v ) {
        boolean inseriu = this.insereVerticeEntre( f.getVertices(), v1, v2, v );        
        if ( inseriu )
            v.addFace( f ); 
        return inseriu;
    }
    
    public boolean insereVerticeEntre( List<Vertice3D> vertices, Vertice3D v1, Vertice3D v2, Vertice3D v ) {
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            int indice = ( i == size-1 ? 0 : i+1 );
            Vertice3D fv1 = vertices.get( i );
            Vertice3D fv2 = vertices.get( indice );           
            if ( ( fv1 == v1 && fv2 == v2 ) || ( fv2 == v1 && fv1 == v2 ) ) {                
                vertices.add( indice, v );
                return true;
            }
        }
        return false;
    }
    
    public int buscaIndiceDeVertice( List<Vertice3D> vertices, Vertice3D vertice ) {
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            Vertice3D v = vertices.get( i );
            if ( v == vertice )
                return i;
        }
        return -1;
    }
    
    public Vertice3D vertice( List<Vertice3D> vertices, double[] p, FiltroVert3D filtro, Objeto3DTO to ) {
        for( Vertice3D v : vertices ) {
            double[] v_p = filtro.getPonto3D( v );
            if ( to.getMath3D().verificaSeIguais( p, v_p ) )
                return v;
        }
        return null;
    }
    
    public Vertice3D[] maisDistantes( List<Vertice3D> vertices, FiltroVert3D filtro, Objeto3DTO to ) {
        double maiorDistancia = 0;
        Vertice3D[] distantes = { null, null };
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            Vertice3D v1 = vertices.get( i );
            Vertice3D v2 = vertices.get( i == size-1 ? 0 : i+1 );
            
            double[] vet = to.getMath3D().sub( filtro.getPonto3D( v1 ), filtro.getPonto3D( v2 ) );
            double dist = to.getMath3D().comprimento( vet );
            if ( dist > maiorDistancia ) {
                distantes[0] = v1;
                distantes[1] = v2;
                maiorDistancia = dist;
            }
        }
        
        if ( maiorDistancia > 0 )
            return distantes;
        return null;
    }            
    
    public void removeFacesComMenosDe3Vertices( Estrutura3D est ) {
        List<Face3D> lista = new ArrayList();
        for( Face3D f : est.getFaces() )
            if( f.getVertices().size() < 3 ) 
                lista.add( f );
        est.getFaces().removeAll( lista );        
    }        
                         
    public void facesComplanaresDeMesmaFace( List<Face3D> visitadas, Face3D face, 
            double[] p1, double[] p2, double[] p3,
            FiltroVert3D filtro, Objeto3DTO to ) {
        if ( visitadas.contains( face ) )
            return;
        
        visitadas.add( face );
        
        for( Vertice3D v : face.getVertices() )
            for( Face3D f : v.getFaces() )
                if ( to.getMath3D().verificaSeVerticesPertencemAPlano( f.getVertices(), p1, p2, p3, filtro ) )
                    this.facesComplanaresDeMesmaFace( visitadas, f, p1, p2, p3, filtro, to );                     
    }
    
    public int contaVertsColinearesAAlgumaAresta( Face3D face, List<Vertice3D> intersecoes, FiltroVert3D filtro, Objeto3DTO to ) {
        int cont = 0;
        for( Vertice3D v : intersecoes ) {
            double[] p = filtro.getPonto3D( v );
            
            int size = face.getVertices().size();
            for( int i = 0; i < size; i++ ) {
                Vertice3D v1 = face.getVertices().get( i );
                Vertice3D v2 = face.getVertices().get( i == size-1 ? 0 : i+1 );
                double[] p1 = filtro.getPonto3D( v1 );
                double[] p2 = filtro.getPonto3D( v2 );
                
                if ( to.getMath3D().verificaSeColineares( p1, p2, p ) )
                    if ( to.getMath3D().verificaSeParalelepipedoContemPonto( p1, p2, p ) )
                        cont++;
            }
        }
        return cont;
    }
    
    /*    
    public void verticesCoplanaresDeMesmaFace( List<Vertice3D> visitados, Vertice3D vertice, 
            double[] p1, double[] p2, double[] p3, 
            FiltroVert3D filtro, Objeto3DTO to ) {
        
        if ( visitados.contains( vertice ) )
            return;
        
        visitados.add( vertice );
        
        for( Aresta3D a : vertice.getArestas() ) {
            if ( a.getV1() == vertice ) {
                Vertice3D v = a.getV2();
                double[] ponto = filtro.getPonto3D( v );
                if ( to.getMath3D().verificaSePontoPertenceAPlano( ponto , p1, p2, p3 ) )
                    this.verticesCoplanaresDeMesmaFace( visitados, v, p1, p2, p3, filtro, to );
            } else {
                Vertice3D v = a.getV1();
                double[] ponto = filtro.getPonto3D( v );
                if ( to.getMath3D().verificaSePontoPertenceAPlano( ponto , p1, p2, p3 ) )
                    this.verticesCoplanaresDeMesmaFace( visitados, v, p1, p2, p3, filtro, to );
            }
        }
    }
    
    public boolean verificaSeVerticePertenceAGrupo( List<Vertice3D> visitados, Vertice3D v, Vertice3D procurado )  {
        Vertice3D atual = v;
        while( atual != null ) {
            visitados.add( atual );
            if ( atual == procurado )
                return true;
            
            Vertice3D vert = null;
            int size = atual.getFaces().size();
            for( int i = 0; vert == null && i < size; i++ ) {
                Face3D f = atual.getFaces().get( i );
                vert = this.verticeNaoVisitado( f.getVertices(), visitados );
            }
            System.out.println( vert );
            atual = vert;
        }
        return false;
    }
    
    public Vertice3D verticeNaoVisitado( List<Vertice3D> vertices, List<Vertice3D> visitados ) {
        for( Vertice3D v : vertices )
            if ( !visitados.contains( v ) )
                return v;        
        return null;
    }
    
*/
    
}
