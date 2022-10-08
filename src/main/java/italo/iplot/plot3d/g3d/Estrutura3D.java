package italo.iplot.plot3d.g3d;

import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;
import java.util.HashMap;
import java.util.Map;

public class Estrutura3D {

    protected final List<Vertice3D> vertices = new ArrayList();
    protected final List<Aresta3D> arestas = new ArrayList();
    protected final List<Aresta3D> outrasArestas = new ArrayList( 0 );
    protected final List<Face3D> faces = new ArrayList(); 
        
    private boolean estruturaIniciada = false;
    
    protected Objeto3D objeto;
    
    public Estrutura3D( Objeto3D obj ) {
        this.objeto = obj;
    }
        
    public Estrutura3D copia( FiltroVert3D filtro, Objeto3DTO to ) {
        Estrutura3D est = new Estrutura3D( objeto );
        est.reinicia();
        
        if ( estruturaIniciada ) {
            Map<Vertice3D, Vertice3D> vmap = new HashMap();
            vertices.forEach( v -> {
                double[] p = filtro.getPonto3D( v );
                Vertice3D v2 = new Vertice3D( p.clone() );
                v2.setVerticeRaio3D( v.getVerticeRaio3D() );
                est.addVertice( v2 );
                
                vmap.put( v, v2 );
            });
            faces.forEach( f -> {
                Face3D f2 = new Face3D();
                f2.setInverterVN( f.isInverterVN() );
                f2.setCor( f.getCor() );
                f2.setFace3DVisivel( f.getFace3DVisivel() );
                f2.setVisivel( f.isVisivel() ); 
                f2.setObjeto( objeto );
                f.getVertices().forEach( v -> {
                    Vertice3D v2 = vmap.get( v );
                    f2.addVertice( v2 );                     
                } );
                est.addFace( f2, to ); 
            } );
            outrasArestas.forEach( a -> {    
                int i1 = this.verticeIndice( a.getV1() );
                int i2 = this.verticeIndice( a.getV2() );
                Vertice3D a2_v1 = est.getVertices().get( i1 );
                Vertice3D a2_v2 = est.getVertices().get( i2 );
                
                Aresta3D a2 = new Aresta3D( objeto, a2_v1, a2_v2 );
                a2.setArestaPontilhada( a.isArestaPontilhada() );
                a2.setCor( a.getCor() );
                a2.setEspacoArestaPX( a.getEspacoArestaPX() );
                est.addOutrasAresta( a2 );
            } );
        }
            
        return est;
    }        
    
    public void calculaVerticesParaVN( Objeto3DTO to, FiltroVert3D filtro ) {
        for( Face3D f : faces ) {
            Vertice3D[] verts = to.getMath3D().verticesNaoColineares( f.getVertices(), filtro );
            f.setVerticesParaVNCalc( verts );
        }        
    }
    
    public int verticeIndice( Vertice3D v ) {
        int size = vertices.size();
        for( int i = 0; i < size; i++ ) {
            Vertice3D v2 = vertices.get( i );
            if ( v == v2 )
                return i;
        }
        return -1;
    }
    
    public void reinicia() {
        vertices.clear();
        arestas.clear();
        outrasArestas.clear();
        faces.clear();
        
        estruturaIniciada = true;
    }
    
    public void recalculaArestas() {                     
        arestas.clear();
        
        for( Face3D f : faces ) {                        
            int tam = f.getVertices().size();            
            for( int i = 0; i < tam; i++ ) {
                Vertice3D v1 = f.getVertices().get( i );
                Vertice3D v2 = f.getVertices().get( i == tam-1 ? 0 : i+1 );                
                Aresta3D a = this.getV1V2Aresta( v1, v2 );                
                if ( a == null ) {
                    Aresta3D aresta = new Aresta3D( objeto, v1, v2 );
                    aresta.setNovaAresta( false ); 
                    aresta.addFace( f );                      
                    arestas.add( aresta ); 
                } else {                      
                    if ( a.getFaces() != null )
                        if ( !a.getFaces().contains( f ) )
                            a.addFace( f );                                                             
                    
                    arestas.add( a );                    
                }                
            }            
        }        
    }
            
    public List<Aresta3D> calculaArestas( Face3D f ) {
        List<Aresta3D> lista = new ArrayList();
        
        int size = f.getVertices().size();                        
        for( int i = 0; i < size; i++ ) {
            Vertice3D v1 = f.getVertices().get( i );
            Vertice3D v2 = f.getVertices().get( i == size-1 ? 0 : i+1 );
            Aresta3D a = this.getV1V2Aresta( v1, v2 ); 
            
            lista.add( a );
        }     
        
        return lista;
    }
    
    public boolean arestasOk() {
        for( Face3D f : faces ) {
            int size = f.getVertices().size();
            for( int i = 1; i < size; i++ ) {
                Vertice3D v1 = f.getVertices().get( i-1 );
                Vertice3D v2 = f.getVertices().get( i );
                Aresta3D a = this.getV1V2Aresta( v1, v2 ); 
                if ( a == null )
                    return false;
            }
        }
        return true;
    }
    
    public void addFaceECopiarVNDirecao( Face3D novaFace, Face3D faceParaCopia, FiltroVert3D filtro, Objeto3DTO to ) {
        novaFace.setInverterVN( faceParaCopia.isInverterVN() ); 
        novaFace.calculaVerticesParaVNCalc( to );
        
        double[] vn1 = to.getMath3D().vetorNormal( novaFace, filtro );
        double[] vn2 = to.getMath3D().vetorNormal( faceParaCopia, filtro );
        double ang = to.getMath3D().angulo( vn1, vn2 );
        if ( ang > Math.PI*.5d )          
            novaFace.setInverterVN( !novaFace.isInverterVN() );        
        
        this.addFace( novaFace, false, to ); 
    }
    
    public void addFaceECopiarVNDirecao( Face3D novaFace, double[][] planoPontos, boolean inverterVN, FiltroVert3D filtro, Objeto3DTO to ) {
        novaFace.setInverterVN( inverterVN ); 
        novaFace.calculaVerticesParaVNCalc( to );
        
        double[] p1 = planoPontos[0];
        double[] p2 = planoPontos[1];
        double[] p3 = planoPontos[2];
        
        double[] vn1 = to.getMath3D().vetorNormal( novaFace, filtro );
        double[] vn2 = to.getMath3D().vetorNormal( p1, p2, p3, inverterVN );
        double ang = to.getMath3D().angulo( vn1, vn2 );
        if ( ang > Math.PI*.5d )          
            novaFace.setInverterVN( !novaFace.isInverterVN() );        
        
        this.addFace( novaFace, false, to );         
    }
    
    public void removeEstruturaVertice( Vertice3D v ) {        
        if ( v.getFaces() != null ) {            
            v.getFaces().forEach( f -> {                
                f.removeVertice( v );
                if ( f.getVertices().size() < 3 )
                    this.removeFace( f );
            } );
        }
        if ( v.getArestas() != null ) {
            v.getArestas().forEach( a -> { 
                outrasArestas.remove( a );
            } );
        }
        this.removeVertice( v );
    }
        
    public boolean removeEstruturaAresta( Aresta3D aresta ) {
        boolean removida = false;
        if ( arestas != null ) {
            removida = this.removeAresta( aresta );
            if ( removida ) {
                if ( aresta.getV1().getArestas() != null )
                    aresta.getV1().getArestas().remove( aresta );
                if ( aresta.getV2().getArestas() != null )
                    aresta.getV2().getArestas().remove( aresta );
            }
        }
        return removida;
    }
    
    public boolean removeEstruturaFace( Face3D face ) {
        boolean removida = false;
        if ( faces != null ) {
            removida = this.removeFace( face );
            if ( removida ) {
                for( Vertice3D v : face.getVertices() )
                    if ( v.getFaces() != null ) {
                        v.getFaces().remove( face );                                
                        if ( v.getFaces().isEmpty() )
                            vertices.remove( v );
                    }
            }
        }
        return removida;
    }
    
    public void removeVerticeDaFace( Vertice3D v, Face3D f ) {
        if ( v.getFaces() == null )
            return;
        
        v.getFaces().remove( f );        
        if ( v.getFaces().isEmpty() )
            vertices.remove( v );                
    }
        
    public boolean removeFace( Face3D face ) {
        if ( faces != null )
            return faces.remove( face );        
        return false;
    }
    
    public boolean removeVertice( Vertice3D v ) {
        if ( vertices != null )
            return vertices.remove( v );            
        return false;
    }
        
    public boolean removeAresta( Aresta3D aresta ) {
        if ( arestas != null )
            return arestas.remove( aresta );           
        return false;
    }
    
    public boolean removeOutrasAresta( Aresta3D aresta ) {
        if ( outrasArestas != null )
            return outrasArestas.remove( aresta );            
        return false;
    }

    public void addFace( Face3D face, Objeto3DTO to ) {
        this.addFace( face, true, to );
    }
    
    public void addFace( Face3D face, boolean calcularVertsVNCalc, Objeto3DTO to ) {
        if ( calcularVertsVNCalc )
            face.calculaVerticesParaVNCalc( to );
        
        face.setObjeto( objeto );
        faces.add( face );     
    }
            
    public void addVertice( Vertice3D v ) {
        vertices.add( v );
    }
        
    public void addAresta( Aresta3D aresta ) {
        arestas.add( aresta );
    }
    
    public void addOutrasAresta( Aresta3D aresta ) {
        outrasArestas.add( aresta );
    }
    
    public List<Aresta3D> todasAsArestas() {
        int quant = 0;        
        if ( outrasArestas != null )
            quant += outrasArestas.size();
        if ( arestas != null )
            quant += arestas.size();
        
        List<Aresta3D> lista = new ArrayList( quant );
        if ( outrasArestas != null )
            lista.addAll( outrasArestas );
        if ( arestas != null )
            lista.addAll( arestas );
        
        return lista;
    }
    
    public Aresta3D arestaVizinhaIgual( Aresta3D a ) {
        List<Aresta3D> lista1 = a.getV1().getArestas();
        List<Aresta3D> lista2 = a.getV2().getArestas();
        for( Aresta3D a2 : lista1 )
            if ( a != a2 )
                if ( this.isVerticesDeAresta( a, a2.getV1(), a2.getV2() ) )
                    return a2;
        
        for( Aresta3D a2 : lista2 )
            if ( a != a2 )
                if ( this.isVerticesDeAresta( a, a2.getV1(), a2.getV2() ) )
                    return a2;
        
        return null;
    }
    
    public Aresta3D getAresta( Vertice3D v1, Vertice3D v2 ) {
        for( Aresta3D a : arestas )
            if ( this.isVerticesDeAresta( a, v1, v2 ) )
                return a;
        return null;
    }
        
    public Aresta3D getOutraAresta( Vertice3D v1, Vertice3D v2 ) {
        for( Aresta3D a : outrasArestas )
            if ( this.isVerticesDeAresta( a, v1, v2 ) )
                return a;        
        return null;
    }
    
    public Aresta3D getV1V2Aresta( Vertice3D v1, Vertice3D v2 ) {        
        if ( v1.getArestas() != null ) {
            for( Aresta3D a : v1.getArestas() )
                if ( this.isVerticesDeAresta( a, v1, v2 ) )
                    return a;
        }
        return null;
    }
    
    public boolean isVerticesDeAresta( Aresta3D a, Vertice3D v1, Vertice3D v2 ) {
        return ( a.getV1() == v1 && a.getV2() == v2 ) || ( a.getV1() == v2 && a.getV2() == v1 );
    }
    
    public boolean isArestaFaceFrontal( Aresta3D a, boolean inverteVNs, FiltroVert3D filtro, Objeto3DTO to ) {
        if ( a.getV1().getFaces() != null && a.getV2().getFaces() != null ) {
            boolean arestaDeFaceFrontal = false;
            int size = a.getV1().getFaces().size();
            for( int i = 0; !arestaDeFaceFrontal && i < size; i++ ) {
                Face3D f = a.getV1().getFaces().get( i );
                arestaDeFaceFrontal = !this.isFaceTrazeira( f, inverteVNs, filtro, to );
            }
            
            if ( arestaDeFaceFrontal ) {
                arestaDeFaceFrontal = false;
                size = a.getV2().getFaces().size();
                for( int i = 0; !arestaDeFaceFrontal && i < size; i++ ) {
                    Face3D f = a.getV2().getFaces().get( i );
                    arestaDeFaceFrontal = !this.isFaceTrazeira( f, inverteVNs, filtro, to ); 
                }
            }
            return arestaDeFaceFrontal;
        }
        return true;
    }
        
    public boolean isFaceTrazeira( Face3D f, boolean inverteVNs, FiltroVert3D filtro, Objeto3DTO to ) {
        double[] vn = to.getMath3D().vetorNormal( f, filtro );                                                                                    
        if ( inverteVNs )
            vn = to.getMath3D().mult( vn, -1.0d );

        return ( vn[2] < 0 );
    }
            
    public List<Vertice3D> getVertices() {
        return vertices;
    }
    
    public List<Aresta3D> getArestas() {
        return arestas;
    }
    
    public List<Aresta3D> getOutrasArestas() {
        return outrasArestas;
    }
    
    public List<Face3D> getFaces() {
        return faces;
    }

    public boolean isEstruturaIniciada() {
        return estruturaIniciada;
    }

    public Objeto3D getObjeto() {
        return objeto;
    }
            
}
