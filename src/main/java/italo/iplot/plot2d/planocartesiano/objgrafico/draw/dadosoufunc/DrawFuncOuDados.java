package italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc;

import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.inifim.IniciaFinalizaDraw;
import italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha.DrawLinha;

public interface DrawFuncOuDados {
    
    public void draw( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv, DrawLinha dwlinha, IniciaFinalizaDraw ifdraw );
    
}
