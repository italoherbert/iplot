package italo.iplot.plot2d.planocartesiano.objgrafico;

import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGrafico;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.DrawDados;

public class DadosObj2DGrafico implements Objeto2DGrafico {
        
    private final DrawDados drawFunc = new DrawDados();
    private final FuncOuDadosObj2DGrafico funcOuDadosGrafico = new FuncOuDadosObj2DGrafico( drawFunc );
    
    @Override
    public void desenho( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv ) {
        funcOuDadosGrafico.desenho( grafico, obj, drv );
    }
    
}