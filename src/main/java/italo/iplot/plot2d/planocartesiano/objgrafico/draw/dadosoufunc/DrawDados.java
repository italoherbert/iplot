package italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc;

import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCContainerObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCDadosObjeto2D;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDraw;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha.DrawLinha;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.util.DadosObj2DGraficoLinha;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.util.DadosObj2DGraficoUtil;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDrawVazio;
import java.awt.Color;

public class DrawDados implements DrawFuncOuDados {

    private final DadosObj2DGraficoUtil util = new DadosObj2DGraficoUtil();
    
    @Override
    public void draw( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv, DrawLinha dwlinha, IniciaFinalizaDraw ifdraw ) {
        PCDadosObjeto2D dadosFuncObj2D = (PCDadosObjeto2D)obj;
        PCContainerObjeto2D container = dadosFuncObj2D.getPCContainerObjeto2D();
                        
        if ( !obj.isPintarArestas() )
            return;
        
        double[] dadosX = dadosFuncObj2D.getDadosX();
        double[] dadosY = dadosFuncObj2D.getDadosY();
        
        double dx = container.getDX();
        double dy = container.getDY();
        double cx = container.getX();
        double cy = container.getY();
        double pcX1 = cx - dx*.5d;
        double pcY1 = cy - dy*.5d;
        double pcX2 = cx + dx*.5d;
        double pcY2 = cy + dy*.5d;                                                       
                     
        grafico.setCor( dadosFuncObj2D.getArestasCor() ); 
        
        int f = ifdraw.getMultFator();
        Tela tela = new TelaImpl( drv.getTela().getTelaLargura() * f, drv.getTela().getTelaAltura() * f );
        
        ifdraw.inicia(); 
        
        double antPX = 0;
        double antPY = 0;
        double antPCX = 0;
        double antPCY = 0;
        boolean nan = true;
        boolean antPCSetado = false;        
        for( int i = 0; i < dadosX.length; i++ ) {
            double x = dadosX[i];
            double y = dadosY[i];
            if ( y != Double.NaN && y != Double.NEGATIVE_INFINITY && y != Double.POSITIVE_INFINITY ) {                
                double pcX = container.calculaX( x );
                double pcY = container.calculaY( y );
                double[] pxy = drv.getMath2D().doublePontoPX( pcX, pcY, tela );                
                                
                boolean carregou = false;
                if ( antPCSetado ) {                    
                    DadosObj2DGraficoLinha linha = util.calculaP0P1( antPCX, antPCY, pcX, pcY, pcX1, pcY1, pcX2, pcY2, drv.getMath2D(), tela );                                                         
                    double[] p0 = linha.getP0();
                    double[] p1 = linha.getP1();
                    nan = linha.isNaN();
                                        
                    if ( p1 != null ) {
                        if ( p0 == null )
                            p0 = new double[] { antPX, antPY };
                        
                        dwlinha.draw( grafico, p0, p1 );                                                
                        carregou = true;
                    }
                } 
                
                if ( !carregou && pcX >= pcX1 && pcX <= pcX2 && pcY >= pcY1 && pcY <= pcY2 ) {
                    double[] p0 = new double[] { antPX, antPY };
                    double[] p1 = pxy;                    
                    if ( !nan )
                        dwlinha.draw( grafico, p0, p1 );                     
                }
                
                antPX = pxy[0];
                antPY = pxy[1];                                                                                                
                antPCX = pcX;
                antPCY = pcY;  
                antPCSetado = true;
                
                nan = pcX < pcX1 || pcX > pcX2 || pcY < pcY1 || pcY > pcY2;                
            } else {
                nan = true;
            }            
            
        } 
                
        ifdraw.finaliza();                 
    }
    
}
