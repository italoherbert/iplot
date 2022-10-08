package italo.iplot.gui.grafico;

public interface GraficoBufferPainterFactory {
        
    public GraficoBufferPainter criaGraficoBufferPainterSSAA( GraficoPixel gpx );

    public GraficoBufferPainter criaGraficoBufferPainterNormal( GraficoPixel gpx );
    
}
