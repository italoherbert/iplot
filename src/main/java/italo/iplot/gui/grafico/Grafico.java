package italo.iplot.gui.grafico;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import italo.iplot.gui.GPintura;
import java.awt.image.BufferedImage;

public interface Grafico extends GraficoPixel {   
                            
    public void graficoBufferGerado( BufferedImage imagem );
    
    public void antesDesenharGrafico();
    
    public void preencheFace( FaceGeom geom );
    
    public void desenhaLinhaPontilhada( ArestaGeom geom, int esp );
    
    public void desenhaLinha( ArestaGeom geom );
        
    public void completaPintura();
        
    public void pintaPixel( int x, int y );
            
    public void desenhaLinhaNormal( DoubleGraficoPixel dgpx, double[] p0, double[] p1 );
        
    public void desenhaLinha( int x1, int y1, int x2, int y2 );
    
    public void desenhaLinhaPontilhada( int x1, int y1, int x2, int y2, int esp);
        
    public void desenhaRetangulo( int x, int y, int largura, int altura );
        
    public void preencheRetangulo( int x, int y, int largura, int altura );
        
    public void desenhaCirculo( int x, int y, int r );
    
    public void preencheCirculo( int x, int y, int r );
    
    public void desenhaTexto( String texto, int x, int y );
    
    public void desenhaTexto(String texto, int x, int y, double rang );

    public Rectangle2D stringLimites( String texto );
    
    public Rectangle2D stringLimites( String texto, Font font );
        
    public GPintura getPintura();
    
    public void setPintura(GPintura pintura);
    
    public Color getBGCor();
    
    public void setBGCor(Color cor);
    
    public Color getCor();
    
    public void setCor( Color cor );
    
    public Graphics getGraphics();
    
    public FiltroManager getFiltroManager();
    
    public GraficoBufferPainterFactory getGBufferPainterFactory();

    public CoresUtil getCoresUtil();
    
}
