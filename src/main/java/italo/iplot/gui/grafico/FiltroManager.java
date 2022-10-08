package italo.iplot.gui.grafico;

import italo.iplot.grafico.filtro.FiltroConf;
import italo.iplot.grafico.filtro.FiltroConfig;

public interface FiltroManager {
    
    public DoubleFiltroPixel criaFiltroPixelAntialiasing( GraficoPixel buffer );    
    
    public FiltroPixel criaFiltroPixelEscurece( GraficoPixel buffer, int reduzirEm );
    
    public FiltroPixel criaFiltroPixelSigma( GraficoPixel buffer, int d, int tolerancia );
    
    public FiltroPixel criaFiltroPixelMax( GraficoPixel buffer, int d );
    
    public FiltroPixel criaFiltroPixelGaussiana3x3( GraficoPixel buffer, FiltroConfig cfg );
    
    public FiltroPixel criaFiltroPixelMediaPassaBaixa3x3( GraficoPixel buffer, FiltroConfig cfg );
        
    public FiltroPixel criaFiltroPixelMediaPassaAlta3x3( GraficoPixel buffer, FiltroConfig cfg );
    
    public FiltroPixel criaFiltroPixelLinear( GraficoPixel buffer, double[][] mascara, FiltroConfig cfg );
            
    public void aplicaFiltroMediaPassaAlta3x3( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 );
    
    public void aplicaFiltroMediaPassaBaixa( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, int d );
        
    public void aplicaFiltroGaussiana3x3( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 );

    public void aplicaFiltroGaussiana5x5( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2 );
    
    public void aplicaFiltroGaussiana( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, int d, double desvio );
    
    public void aplicaFiltro( GraficoPixel gpx, GraficoPixel buffer, int x1, int y1, int x2, int y2, double[][] mascara );
    
    public void aplicaFiltro( GraficoPixel gpx, GraficoPixel buffer, double[][] mascara );
            
    public int corAplicaMascara( GraficoPixel gpx, GraficoPixel buffer, int x, int y, double[][] mascara );
    
    public int corAplicaMascara( GraficoPixel buffer, int x, int y, double[][] mascara, FiltroConf cfg );
    
    public int corAplicaMascara( GraficoPixel buffer, int x, int y, int x1, int y1, int x2, int y2, double[][] mascara );    
            
    public CoresUtil getCoresUtil();
    
}
