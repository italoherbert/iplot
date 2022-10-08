package italo.iplot.grafico.teste;

import italo.iplot.grafico.linha.RadialLinhaDrawer;
import italo.iplot.grafico.linha.BresenhanLinhaDrawer;
import italo.iplot.grafico.pixel.doublegpx.DoubleGraficoPixelRound;
import italo.iplot.grafico.util.CoresUtilitario;
import italo.iplot.gui.grafico.CoresUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import italo.iplot.gui.grafico.GraficoPixel;
import italo.iplot.gui.grafico.DoubleGraficoPixel;

public class TestePixelsUtil {
    
    public static void main( String[] args ) {
        Painel p = new FuncPainel();
        
        JFrame f = new JFrame();
        f.setContentPane( p ); 
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setSize( 600, 600 );
        f.setLocationRelativeTo( f );
        f.setVisible( true );
    }
            
    static abstract class Painel extends JPanel implements GraficoPixel {
        
        protected final int dim = 100;
        protected final Color[][] pixels = new Color[ dim ][ dim ];
        
        protected final CoresUtil coresUtil = new CoresUtilitario();
        protected final RadialLinhaDrawer gantialiasing = new RadialLinhaDrawer();
        protected final BresenhanLinhaDrawer glinha = new BresenhanLinhaDrawer();
        
        protected final Color bgCor = Color.WHITE;
        protected final boolean desenharGrade = false;
                   
        public abstract void desenha( Graphics g );
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent( g ); 
            
            for( int i = 0; i < dim; i++ )
                for( int j = 0; j < dim; j++ )
                    pixels[i][j] = bgCor;
                                    
            this.desenha( g ); 
            
            int w = super.getWidth();
            int h = super.getHeight();
            int borda = 10;

            int wqdim = ( w - (2*borda) ) / dim;
            int hqdim = ( h - (2*borda) ) / dim;
            int wdim = dim * wqdim;
            int hdim = dim * hqdim;

            for( int i = 0; i < dim; i++ ) {
                for( int j = 0; j < dim; j++ ) {
                    int x = borda + ( i * wqdim );
                    int y = borda + ( j * hqdim );
                    g.setColor( pixels[i][j] ); 
                    g.fillRect( x, y, wqdim, hqdim );
                }
            }

            if ( desenharGrade ) {
                g.setColor( Color.GREEN );
                int x = borda;
                int y = borda;              

                for( int i = 0; i <= dim; i++ ) {
                    g.drawLine( x, y, x+wdim, y );
                    y += hqdim;
                }
                x = borda;
                y = borda;
                for( int i = 0; i <= dim; i++ ) {
                    g.drawLine( x, y, x, y+hdim );
                    x += wqdim;
                }                     
            }
        }

        @Override
        public void pintaPixel(int x, int y, int rgb) {
            if ( x >= 0 && x < dim && y >= 0 && y < dim )
                pixels[x][y] = new Color( rgb );
        }

        @Override
        public int getRGB(int x, int y) {
            if ( x < 0 || x >= dim || y < 0 || y >= dim )
                return Color.BLACK.getRGB();
            return pixels[x][y].getRGB();
        }                

        @Override
        public int getLarg() {
            return dim;
        }

        @Override
        public int getAlt() {
            return dim;
        }

    }
        
    static class FuncPainel extends Painel {

        @Override
        public void desenha(Graphics g) {
            double ainc = ( 4*Math.PI ) / dim;
            int desloc = 1;
            
            DoubleGraficoPixel dpx = new DoubleGraficoPixelRound( this );
                        
            double xant = 0;
            double yant = 0;
            for( double a = 0, x = 0; x < dim; a+=ainc*desloc, x+= desloc ) {
                double y = ( (dim/2-1) - ( Math.sin( a ) * (dim/4-1) ) );                                                
                if ( y < 0 )
                    y = 0;
                if ( y > dim )
                    y = dim;
                
                double[] p0 = { xant, yant };
                double[] p1 = { x, y };
                                
                if ( x == 0 ) {
                    xant = x;
                    yant = y;
                } else {   
                    if ( x == 1 ) {
                        gantialiasing.desenhaLinha( dpx, p0, p1, Color.BLACK.getRGB() );                                        
                    } else {
                        gantialiasing.desenhaLinha( dpx, p0, p1, Color.BLACK.getRGB() );                                                                
                    }
                    xant = x;
                    yant = y;
                }                                
            }                                    
        }   
                
    }
    
    static class LinhaPainel extends Painel {
        private final Color curvaCor = Color.BLACK;
        private final BufferedImage imagem = new BufferedImage( dim, dim, BufferedImage.TYPE_INT_RGB );
        
        @Override
        public void desenha(Graphics g) {
            int quant = 12;
            double ainc = (2*Math.PI) / quant;
            int r = 20;
            
            DoubleGraficoPixel dpx = new DoubleGraficoPixelRound( this );
            
            for( int k = 0; k < quant; k++ ) {
                double a = k * ainc;
                double[] p0 = { dim/4, dim/4 };
                double[] p = { r * Math.cos( a ), r * Math.sin( a ) };                        

                double[] p1 = { p0[0] + p[0], p0[1] + p[1] };
                
                Graphics2D g2D = (Graphics2D)imagem.getGraphics();
                g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
                g2D.setColor( bgCor );
                g2D.fillRect( 0, 0, dim, dim ); 
                g2D.setColor( curvaCor );
                g2D.drawLine( (int)Math.round( p0[0] ), (int)Math.round( p0[1] ), (int)Math.round( p1[0] ), (int)Math.round( p1[1] ) ); 

                for( int i = 0; i < dim; i++ )
                    for( int j = 0; j < dim; j++ )
                        if ( imagem.getRGB( i, j ) != bgCor.getRGB() )
                            pixels[i][j] = new Color( imagem.getRGB( i, j ) );                            

                p0[0] += dim/2;
                p1 = new double[] { p0[0] + p[0], p0[1] + p[1] };
                
                int[] lp0 = { (int)Math.round( p0[0] ), (int)Math.round( p0[1] ) };
                int[] lp1 = { (int)Math.round( p1[0] ), (int)Math.round( p1[1] ) };
                glinha.desenhaLinha( this, lp0, lp1, curvaCor.getRGB() ); 

                p0[1] += dim/2;                        
                p1 = new double[] { p0[0] + p[0], p0[1] + p[1] };
                gantialiasing.desenhaLinha( dpx, p0, p1, curvaCor.getRGB() );
            }
        }
        
    }
        
    static class CurvaPainel extends Painel {

        private final Color curvaCor = Color.BLACK;
        private final BufferedImage imagem = new BufferedImage( dim, dim, BufferedImage.TYPE_INT_RGB );
        
        @Override
        public void desenha(Graphics g) {                                                
            int[] p0 = { 4, 10 };
            int[] p1 = { 50, 15 };                        
            
            Graphics2D g2D = (Graphics2D)imagem.getGraphics();            
            g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2D.setColor( bgCor );
            g2D.fillRect( 0, 0, dim, dim ); 
            g2D.setColor( curvaCor );
            QuadCurve2D.Float curva = new QuadCurve2D.Float( p0[0], p0[1], 60, 150, p1[0], p1[1] );
            g2D.draw( curva );                                                                           
            
            p0[0] += 50;
            p1[0] += 50;
            
            g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
            QuadCurve2D curva2 = new QuadCurve2D.Float( p0[0], p0[1], 110, 150, p1[0], p1[1] );
            g2D.draw( curva2 );
            
            for( int i = 0; i < dim; i++ )
                for( int j = 0; j < dim; j++ )
                    pixels[i][j] = new Color( imagem.getRGB( i, j ) );            
        }
        
        
    }    
    
}
