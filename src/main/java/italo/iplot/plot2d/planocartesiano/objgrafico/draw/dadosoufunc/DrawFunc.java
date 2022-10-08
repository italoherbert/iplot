package italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc;

import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCContainerObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDraw;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha.DrawLinha;
import italo.iplot.funcs.g2d.Func2D;

public class DrawFunc implements DrawFuncOuDados {
    
    @Override
    public void draw( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv, DrawLinha dwlinha, IniciaFinalizaDraw ifdraw ) {
        PCFuncObjeto2D funcObj2D = (PCFuncObjeto2D)obj;
        PCContainerObjeto2D container = funcObj2D.getPCContainerObjeto2D();
        
        Func2D func = funcObj2D.getFunc2D();
        if ( func == null )
            return;
        
        double minY = funcObj2D.getMinY();
        double maxY = funcObj2D.getMaxY();
                
        double x1, x2;
        if ( funcObj2D.isXIntervaloCompleto() ) {
            x1 = container.getIX1();
            x2 = container.getIX2();
        } else {
            x1 = funcObj2D.getX1();
            x2 = funcObj2D.getX2();    
        }
        
        double dx = container.getDX();
        double dy = container.getDY();
        double cx = container.getX();
        double cy = container.getY();
        double pcX1 = cx - dx*.5d;
        double pcY1 = cy - dy*.5d;
        double pcX2 = cx + dx*.5d;
        double pcY2 = cy + dy*.5d;                
        
        double meioH = drv.getMath2D().getMeioH( drv.getTela() );        
        int npontos = (int)Math.ceil( Math.abs( container.calculaX( x2 ) - container.calculaX( x1 ) ) * meioH );
        double inc = Math.abs( x2 - x1 ) / npontos; 
                         
        grafico.setCor( funcObj2D.getArestasCor() ); 
                                
        int f = ifdraw.getMultFator();        
        Tela tela = new TelaImpl( drv.getTela().getTelaLargura() * f, drv.getTela().getTelaAltura() * f );        
                
        ifdraw.inicia();
        
        double[] p0 = { 0, 0 };
        double[] p1;
        boolean nan = true;
        for( double x = x1; x <= x2; x+= inc ) {
            double y = func.getY( x );
            if ( y != Double.NaN && y != Double.NEGATIVE_INFINITY && y != Double.POSITIVE_INFINITY ) {                
                if ( y >= minY && y <= maxY ) {
                    double pcX = container.calculaX( x );
                    double pcY = container.calculaY( y );
                    double[] pxy = drv.getMath2D().doublePontoPX( pcX, pcY, tela );
                    
                    if ( pcX >= pcX1 && pcX <= pcX2 && pcY >= pcY1 && pcY <= pcY2 ) {
                        p1 = pxy;
                        if ( !nan )
                            dwlinha.draw( grafico, p0, p1 );                                                    
                    }
                    
                    p0 = pxy;                      
                    
                    nan = pcX < pcX1 || pcX > pcX2 || pcY < pcY1 || pcY > pcY2;                    
                } else {
                    nan = true;
                }
            } else {
                nan = true;
            }            
        }        

        ifdraw.finaliza();
    }
    
}
