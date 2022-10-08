package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.planocartesiano.regua.ReguaUtil;
import italo.iplot.plot3d.g3d.LinhaObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Regua3D;
import italo.iplot.plot3d.planocartesiano.objgrafico.ReguaObj3DGrafico;
import java.awt.geom.Rectangle2D;

public class ReguaPlanoCartesianoObjeto3D extends LinhaObjeto3D {
    
    private final PlanoCartesianoObjeto3D planoObj;
    private final Regua3D regua;
    private final Regua3DTipo reguaTipo;
    
    private Vertice3D eixoRotuloV;
    
    public ReguaPlanoCartesianoObjeto3D( PlanoCartesianoObjeto3D plano, Regua3D regua, double[] p1, double[] p2, Regua3DTipo reguaTipo ) {
        super( p1, p2 );
        this.planoObj = plano;
        this.regua = regua;     
        this.reguaTipo = reguaTipo;
        
        super.cortavel = false;
        super.grafico = new ReguaObj3DGrafico();
    }
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {
        super.constroiObjeto3D( to );
        
        super.removeTodosOsObjetos();
        
        ReguaUtil reguaUtil = planoObj.getReguaUtil();
        
        double n1 = regua.getN1();
        double n2 = regua.getN2();
        double inc = regua.getInc();
        double desloc = regua.getDesloc();
        int numRotulos = regua.getNumRotulos();
        double dn = regua.getDN();
        double c = to.getMath3D().verticeUnidade( planoObj.getReguaTracoComprimentoPX(), to.getTela() );
        double rotuloDist = to.getMath3D().verticeUnidade( planoObj.getReguaValorDistanciaPX(), to.getTela() );
        
        double borda = reguaUtil.calculaBorda( numRotulos, inc, n1, n2 );
        borda += 2 * desloc;
                
        double h_n1 = -dn*.5d;
        double h_n2 =  dn*.5d;
            
        boolean parar = false;
        for( int i = 0; !parar; i++ ) {            
            double h1 = reguaUtil.calculaPlanoCartesianoH( inc, borda, i );
            double h2 = reguaUtil.calculaH( dn, n1, n2, h1 );
                        
            if ( h2 >= h_n1 && h2 <= h_n2 ) {            
                double rotulo = n1 + ( i * inc ) + desloc;

                double[][] extremidades = regua.calculaExtremidades( p1, h2, c );
                double[] v1 = extremidades[0];            
                double[] v2 = extremidades[1];

                double[][] rotuloPontoExtremidades = regua.calculaExtremidades( p1, h2, rotuloDist );
                double[] rotuloVertice = rotuloPontoExtremidades[1];

                TracoRequaPlanoCartesianoObjeto3D traco = new TracoRequaPlanoCartesianoObjeto3D( this, rotulo, v1, v2, rotuloVertice );
                traco.setArestasCor( planoObj.getReguaCor() ); 
                super.addObjeto( traco );
            } else {
                if ( h2 >= h_n2 )
                    parar = true;
            }
        }   
                
        double[] eixoRotuloP = null;
        
        if ( planoObj.isPintarEixoRotulos() ) {
            String rotulo = this.getRotulo();
            if ( rotulo != null ) {
                Rectangle2D ret = to.getStringLimites( rotulo, planoObj.getEixoRotuloFont() );
                double larg = to.getMath3D().verticeUnidade( ret.getWidth(), to.getTela() );
                
                double reguaValorDist = to.getMath3D().verticeUnidade( planoObj.getReguaValorDistanciaPX(), to.getTela() );
                double eixoRotuloDist = to.getMath3D().verticeUnidade( planoObj.getEixoRotulosDistanciaPX(), to.getTela() );
                double reguaTracoComprimento = to.getMath3D().verticeUnidade( planoObj.getReguaTracoComprimentoPX(), to.getTela() );
                
                double reguaYValorMax;
                if ( reguaTipo == Regua3DTipo.Y ) {
                    reguaYValorMax = planoObj.getReguaYValorLarguraMax();
                } else {
                    reguaYValorMax = planoObj.getReguaYValorAlturaMax();
                }
                
                double h = larg * .5d * regua.getDirecao();
                double eixoRotuloC = reguaTracoComprimento + reguaValorDist + reguaYValorMax + eixoRotuloDist;
                double[][] ext = regua.calculaExtremidades( p1, h, eixoRotuloC );
                eixoRotuloP = ext[1];                
            }
        }
        
        if ( eixoRotuloP == null ) {
            double[][] ext = regua.calculaExtremidades( p1, 0, 0 );
            eixoRotuloP = ext[0];
        }        
        
        eixoRotuloV = new Vertice3D( eixoRotuloP );
        super.estrutura.addVertice( eixoRotuloV ); 
    }
    
    public String getRotulo() {
        String rotulo = null;
        switch ( reguaTipo ) {
            case X:
                rotulo = planoObj.getXEixoRotulo();
                break;
            case Y:
                rotulo = planoObj.getYEixoRotulo();
                break;
            case Z:
                rotulo = planoObj.getZEixoRotulo();
                break;
        }
        return rotulo;
    }
        
    public Vertice3D getEixoRotuloVertice() {
        return eixoRotuloV;
    }

    public Regua3D getRegua() {
        return regua;
    }

    public Regua3DTipo getReguaTipo() {
        return reguaTipo;
    }

    public PlanoCartesianoObjeto3D getPlanoObj() {
        return planoObj;
    }
    
}
