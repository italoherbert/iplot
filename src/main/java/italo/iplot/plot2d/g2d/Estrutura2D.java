package italo.iplot.plot2d.g2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estrutura2D {
    
    private final List<Vertice2D> vertices = new ArrayList();
    private final List<Aresta2D> arestas = new ArrayList();
    private final List<Face2D> faces = new ArrayList( 1 );
        
    private boolean estruturaIniciada = false;
    
    protected Objeto2D objeto;
    
    public Estrutura2D( Objeto2D obj ) {
        this.objeto = obj;
    }
    
    public Estrutura2D copia() {
        Estrutura2D est = new Estrutura2D( objeto );
        
        Map<Vertice2D, Vertice2D> vmap = new HashMap();
        for( Vertice2D v : vertices ) {
            Vertice2D v2 = new Vertice2D( v.getX(), v.getY() );
            v2.setX0( v.getX0() );
            v2.setY0( v.getY0());
            v2.setVisaoX( v.getVisaoX() );
            v2.setVisaoY( v.getVisaoY() );
            v2.setVerticeRaio2D( v.getVerticeRaio2D() ); 
            est.addVertice( v2 ); 
            vmap.put( v, v2 );
        }

        for( Aresta2D a : arestas ) {
            Vertice2D v1 = vmap.get( a.getV1() );
            Vertice2D v2 = vmap.get( a.getV2() );

            Aresta2D a2 = new Aresta2D( v1, v2 );
            this.copiaConfig( a2, a ); 
            est.addAresta( a2 ); 
        }

        for( Face2D f : faces ) {
            Face2D f2 = new Face2D( f.getFace3DVisivel() );
            f2.setCor( f.getCor() );
            f2.setObjeto( f.getObjeto() );
            f2.setVisivel( f.isVisivel() ); 
            for( Vertice2D v : f.getVertices() ) 
                f2.addVertice( vmap.get( v ) );
            est.addFace( f2 );
        }        
        
        return est;
    }
    
    public void reinicia() {
        vertices.clear();
        arestas.clear();
        faces.clear();
        
        estruturaIniciada = true;
    }
    
    public void copiaConfig( Aresta2D a, Aresta2D a2 ) {
        a.setCor( a2.getCor() );
        a.setArestaPontilhada( a2.isArestaPontilhada() );
        a.setEspacoArestaPontosPX( a2.getEspacoArestaPontosPX() );
    }
    
    public boolean insereVertice( Face2D f, Vertice2D v1, Vertice2D v2, Vertice2D v ) {
        boolean inseriu = false;
        List<Vertice2D> verts = f.getVertices();
        int size = verts.size();
        for( int i = 0; !inseriu && i < size; i++ ) {
            Vertice2D vv1 = verts.get( i );
            Vertice2D vv2 = verts.get( i == size-1 ? 0 : i+1 );
            if ( ( v1 == vv1 && v2 == vv2 ) || ( v1 == vv2 && v2 == vv1 ) ) {
                verts.add( i+1, v );
                this.addVertice( v );
                inseriu = true;
            }
        }
        return inseriu;
    }
    
    public void quebraAresta( Aresta2D aresta, Vertice2D v ) {
        Vertice2D v1 = aresta.getV1();
        Vertice2D v2 = aresta.getV2();
        
        this.addVertice( v );
        this.addAresta( new Aresta2D( v1, v ) );
        this.addAresta( new Aresta2D( v, v2 ) );
        this.removeAresta( aresta );
    }
    
    public List<Aresta2D> faceArestas( Face2D f ) {
        List<Vertice2D> verts = f.getVertices();
        int size = verts.size();

        List<Aresta2D> lista = new ArrayList( size );
        for( int i = 0; i < size; i++ ) {
            Vertice2D v1 = verts.get( i );
            Vertice2D v2 = verts.get( i == size-1 ? 0 : i+1 );
            List<Aresta2D> v1Arestas = v1.getArestas();
            int asize = v1Arestas.size();
            Aresta2D aresta = null;
            for( int j = 0; aresta == null && j < asize; j++ ) {
                Aresta2D a = v1Arestas.get( j );
                if ( this.saoExtremidades( a, v1, v2 ) )
                    aresta = a;
            }
            
            if ( aresta == null )
                throw new RuntimeException( "Inconsistência na lista de arestas de algum vertice de uma face" );
            
            lista.add( aresta );
        }
        
        return lista;
    }
    
    public void removeArestas( List<Aresta2D> lista ) {
        arestas.removeAll( lista );        
    }
            
    public boolean removeVertice( Vertice2D v ) {
        if ( vertices != null )
            return vertices.remove( v );                    
        return false;
    }
    
    public boolean removeAresta( Aresta2D aresta ) {
        if ( arestas != null )
            return arestas.remove( aresta );                    
        return false;
    }
    
    public void removeFace( Face2D face ) {
        if ( faces != null )
            faces.remove( face );            
    }
    
    public void removeFaceVertice( Face2D face, Vertice2D v ) {
        if ( v.getFaces() != null ) {
            v.getFaces().remove( face );            
        }
        
        face.getVertices().remove( v );
        if ( face.getVertices().size() < 3 )
            faces.remove( face );                
    }        
    
    public void removeEstruturaVertice( Vertice2D v ) {        
        if ( v.getFaces() != null ) {                            
            v.getFaces().forEach( f -> {                
                f.removeVertice( v );
                if ( f.getVertices().size() < 3 )
                    this.removeFace( f );
            } );
        }
        if ( v.getArestas() != null ) {
            v.getArestas().forEach( a -> { 
                arestas.remove( a );
            } );
        }
        this.removeVertice( v );
    }
    
    public void removeEstruturaFace( Face2D face ) {
        if ( faces != null ) {
            this.removeArestas( this.faceArestas( face ) );
            
            for( Vertice2D v : face.getVertices() )
                if ( v.getFaces() != null )
                    v.getFaces().remove( face );
            
            faces.remove( face );                        
        }
    }
    
    public void removeEstruturaAresta( Aresta2D aresta ) {
        if ( arestas != null ) {            
            aresta.getV1().getArestas().remove( aresta );
            aresta.getV2().getArestas().remove( aresta );
            arestas.remove( aresta );            
        }
    }
        
    public void addFace( Face2D face) {        
        face.setObjeto( objeto );
        this.faces.add( face );        
    }
            
    public void addVertice( Vertice2D v ) {
        vertices.add( v );        
    }
        
    public void addAresta( Aresta2D aresta ) {
        arestas.add( aresta );        
    }
        
    public Aresta2D getAresta( Vertice2D v1, Vertice2D v2 ) {        
        for( Aresta2D a : arestas )
            if ( this.saoExtremidades( a, v1, v2 ) )
                return a;               
        return null;
    }
        
    public boolean saoExtremidades( Aresta2D a, Vertice2D v1, Vertice2D v2 ) {
        return ( a.getV1() == v1 && a.getV2() == v2 ) || ( a.getV1() == v2 && a.getV2() == v1 );
    }
            
    public List<Vertice2D> getVertices() {
        return vertices;
    }
    
    public List<Aresta2D> getArestas() {
        return arestas;
    }
        
    public List<Face2D> getFaces() {
        return faces;
    }

    public boolean isEstruturaIniciada() {
        return estruturaIniciada;
    }

    public Objeto2D getObjeto() {
        return objeto;
    }
    
}
