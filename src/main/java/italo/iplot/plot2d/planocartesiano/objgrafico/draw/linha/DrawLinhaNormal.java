package italo.iplot.plot2d.planocartesiano.objgrafico.draw.linha;

import italo.iplot.gui.grafico.DoubleGraficoPixel;
import italo.iplot.gui.grafico.Grafico;

public class DrawLinhaNormal implements DrawLinha {

    private final DoubleGraficoPixel dgpx;
    
    public DrawLinhaNormal( DoubleGraficoPixel dgpx ) {
        this.dgpx = dgpx;
    }
        
    @Override
    public void draw( Grafico grafico, double[] p0, double[] p1 ) {
        grafico.desenhaLinhaNormal( dgpx, p0, p1 );
    }
    
}
