package italo.iplot.plot3d.g3d.est_opers;

import italo.iplot.plot3d.g3d.util.Malhador3D;
import italo.iplot.plot3d.g3d.util.Cortador3D;
import italo.iplot.plot3d.g3d.est_opers.util.GrafoEst3DUtil;
import italo.iplot.plot3d.g3d.est_opers.util.ListaEncadeadaEst3DUtil;
import italo.iplot.plot3d.g3d.est_opers.util.OperadorEst3DUtil;
import italo.iplot.plot3d.g3d.Estrutura3D;
import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class EstruturaOperador {
    
    private final Estrutura3DUtil estUtil = new Estrutura3DUtil();
    private final ListaEncadeadaEst3DUtil listaUtil = new ListaEncadeadaEst3DUtil( estUtil );
    private final GrafoEst3DUtil grafoEstUtil = new GrafoEst3DUtil( estUtil );
            
    private final Malhador3D malha = new Malhador3D();
    private final Cortador3D corte = new Cortador3D( estUtil ); 
    
    private final OperadorEst3DUtil OperEstUtil = new OperadorEst3DUtil( estUtil, grafoEstUtil, listaUtil, corte );
    
    private final Uniao3D uniao = new Uniao3D( estUtil, OperEstUtil );
    
    public void malha( Objeto3D obj, FiltroVert3D filtro, Objeto3DTO to) {
        malha.malha( obj, filtro, to );
    }
    
    public Face3D corte( Estrutura3D est, double[][] planoPontos, boolean inverterVN, FiltroVert3D filtro, Objeto3DTO to ) {                
        return corte.cortaFace( est, planoPontos, inverterVN, true, null, filtro, to );
    }
    
    public void uniao( Objeto3D obj1, Objeto3D obj2, Objeto3DTO to ) {                
        uniao.uniao( obj1, obj2, to ); 
    }

    public Estrutura3DUtil getEstUtil() {
        return estUtil;
    }

    public ListaEncadeadaEst3DUtil getListaUtil() {
        return listaUtil;
    }

    public GrafoEst3DUtil getGrafoEstUtil() {
        return grafoEstUtil;
    }
    
}
