package italo.iplot.plot3d.g3d.est_opers;

import italo.iplot.plot3d.g3d.est_opers.util.OperadorEst3DUtil;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class Uniao3D {
    
    private Estrutura3DUtil estUtil;
    private OperadorEst3DUtil operEst3DUtil;

    public Uniao3D(Estrutura3DUtil estUtil, OperadorEst3DUtil boolEstOpersUtil) {
        this.estUtil = estUtil;
        this.operEst3DUtil = boolEstOpersUtil;
    }
    
    public void uniao( Objeto3D obj1, Objeto3D obj2, Objeto3DTO to ) {                
        FiltroVert3D filtro = to.getXYZFiltroV3D();
        Estrutura3D est1 = obj1.getEstrutura();
        Estrutura3D est2 = obj2.getEstrutura().copia( filtro, to );
        est2.recalculaArestas();
        
        List<Vertice3D> novosVertices = new ArrayList();
        operEst3DUtil.calculaInsersecoes(est1, est2, novosVertices, filtro, to );        
        operEst3DUtil.calculaInsersecoes(est2, est1, novosVertices, filtro, to );                      
               
        operEst3DUtil.alteraEstruturaComEvoltorias(est1, novosVertices, filtro, to ); 
        operEst3DUtil.alteraEstruturaComEvoltorias(est2, novosVertices, filtro, to );
        
        novosVertices.clear();
        operEst3DUtil.calculaInsersecoes( est1, est2, novosVertices, filtro, to );        
        operEst3DUtil.calculaInsersecoes( est2, est1, novosVertices, filtro, to );                      
        
        List<Estrutura3D> novasEstruturas = new ArrayList();

        operEst3DUtil.eliminaIntersecoesVertice(est1, est2, novosVertices, filtro, to ); 
                
        List<Vertice3D> listaVerticesInternosAoObj2 = estUtil.listaVerticesInternos( est1.getVertices(), est2.getFaces(), filtro, to );
        List<Vertice3D> listaVerticesInternosAoObj1 = estUtil.listaVerticesInternos( est2.getVertices(), est1.getFaces(), filtro, to ); 
        
        if ( novosVertices.isEmpty() ) {
            
        } else {                     
            List<Face3D> todasAsFaces = new ArrayList( est1.getFaces().size() + est2.getFaces().size() );
            todasAsFaces.addAll( est1.getFaces() );
            todasAsFaces.addAll( est2.getFaces() );

            operEst3DUtil.alteraEstrutura(est1, est2, todasAsFaces, novosVertices, novasEstruturas, filtro, to );        
            operEst3DUtil.alteraEstrutura( est2, est1, todasAsFaces, novosVertices, novasEstruturas, filtro, to );                                                                                        
        }
        
        for( Vertice3D v : listaVerticesInternosAoObj2 )
            estUtil.removeVertice( est1, v, false );
        for( Vertice3D v : listaVerticesInternosAoObj1 )
            estUtil.removeVertice( est2, v, false );         
                                
        estUtil.removeFacesComMenosDe3Vertices( est1 );
        estUtil.removeFacesComMenosDe3Vertices( est2 );         
                        
        novosVertices.forEach( v -> est1.addVertice( v ) );         
                
        if ( novasEstruturas.isEmpty() ) {
            this.addNovaEstrutura( est1, est2, filtro, to );        
        } else {
            for( Estrutura3D novaEst : novasEstruturas )
                this.addNovaEstrutura( est1, novaEst, filtro, to ); 
        } 
                
        est1.recalculaArestas();                
    }
    
    public void addNovaEstrutura( Estrutura3D est, Estrutura3D novaEst, FiltroVert3D filtro, Objeto3DTO to ) {
        novaEst.getVertices().forEach( v -> { 
            double[] p = filtro.getPonto3D( v );
            if ( !estUtil.verificaSeListaContemVertice( est.getVertices(), p, filtro, to ) )
                est.addVertice( v ); 
        } );             
        
        novaEst.getFaces().forEach( f -> est.addFace( f, to ) );
    }
    
}
