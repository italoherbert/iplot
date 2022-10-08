package italo.iplot.plot2d.g2d.util.corte;

import italo.iplot.plot2d.g2d.Aresta2D;
import italo.iplot.plot2d.g2d.Estrutura2D;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.util.corte.aresta.BaixoArestaCortador2D;
import italo.iplot.plot2d.g2d.util.corte.aresta.CimaArestaCortador2D;
import italo.iplot.plot2d.g2d.util.corte.aresta.FrenteArestaCortador2D;
import italo.iplot.plot2d.g2d.util.corte.aresta.TrazArestaCortador2D;
import italo.iplot.plot2d.g2d.util.corte.face.BaixoFaceCortador2D;
import italo.iplot.plot2d.g2d.util.corte.face.CimaFaceCortador2D;
import italo.iplot.plot2d.g2d.util.corte.face.FrenteFaceCortador2D;
import italo.iplot.plot2d.g2d.util.corte.face.TrazFaceCortador2D;
import italo.iplot.plot2d.g2d.util.corte.vertice.EstruturaVerticesRemovedor;
import italo.iplot.plot2d.g2d.util.corte.vertice.LateraisVRCondicao;
import italo.iplot.plot2d.g2d.util.corte.vertice.VerticesCortador2D;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot2d.g2d.util.corte.vertice.VerticesRemovedor;

public class Cortador2D {
        
    private final CimaFaceCortador2D cimaFaceCortador2D = new CimaFaceCortador2D();
    private final BaixoFaceCortador2D baixoFaceCortador2D = new BaixoFaceCortador2D();
    private final TrazFaceCortador2D trazFaceCortador2D = new TrazFaceCortador2D();
    private final FrenteFaceCortador2D frenteFaceCortador2D = new FrenteFaceCortador2D();
    
    private final CimaArestaCortador2D cimaArestaCortador2D = new CimaArestaCortador2D();
    private final BaixoArestaCortador2D baixoArestaCortador2D = new BaixoArestaCortador2D();
    private final TrazArestaCortador2D trazArestaCortador2D = new TrazArestaCortador2D();
    private final FrenteArestaCortador2D frenteArestaCortador2D = new FrenteArestaCortador2D();
    
    private final VerticesCortador2D verticeCortador2D = new VerticesCortador2D();
    
    public void corta( CorteContainerObjeto2D container, Estrutura2D est, FiltroVert2D filtro ) {
        List<Face2D> faces = new ArrayList( est.getFaces() );
        for( Face2D face : faces )
            this.cortaFace( container, est, face, filtro );
                        
        List<Aresta2D> arestas = new ArrayList( est.getArestas() );
        for( Aresta2D aresta : arestas )
            this.cortaAresta( container, est, aresta, filtro );         
        
        this.cortaVertices( container, est, filtro );         
    }
            
    public void cortaFace( CorteContainerObjeto2D container, Estrutura2D est, Face2D face, FiltroVert2D filtro ) {
        List<Aresta2D> arestas = est.faceArestas( face );
                
        cimaFaceCortador2D.corte( container, est, face, filtro );
        baixoFaceCortador2D.corte( container, est, face, filtro );
        trazFaceCortador2D.corte( container, est, face, filtro );        
        frenteFaceCortador2D.corte( container, est, face, filtro );
            
        int size = face.getVertices().size();
        for( int i = 0; i < size; i++ ) {
            Vertice2D v1 = face.getVertices().get( i );
            Vertice2D v2 = face.getVertices().get( i == size-1 ? 0 : i+1 );
            Aresta2D a = new Aresta2D( v1, v2 );
            if ( !arestas.isEmpty() )
                est.copiaConfig( a, arestas.get( 0 ) );
            est.addAresta( a ); 
        }
        est.removeArestas( arestas );        
    }

    public void cortaAresta( CorteContainerObjeto2D container, Estrutura2D est, Aresta2D aresta, FiltroVert2D filtro ) {                   
        cimaArestaCortador2D.corte( container, est, aresta, filtro );
        baixoArestaCortador2D.corte( container, est, aresta, filtro );
        trazArestaCortador2D.corte( container, est, aresta, filtro );
        frenteArestaCortador2D.corte( container, est, aresta, filtro );        
    }            
    
    public void cortaVertices( CorteContainerObjeto2D container, Estrutura2D est, FiltroVert2D filtro ) {
        VerticesRemovedor rverts = new EstruturaVerticesRemovedor( est );
        CorteCondicao cond = new LateraisVRCondicao();
        verticeCortador2D.corte( container, rverts, cond, filtro );
    }            
        
}
