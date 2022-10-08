package italo.iplot.gui;

import italo.iplot.gui.grafico.Grafico;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class Pintura implements GPintura {
    
    private Desenho desenho;
    private Grafico grafico;
    
    private Tela tela;
        
    private BufferedImage bufferImagem;
    private Graphics graphics;
        
    public Pintura() {}
    
    public Pintura( Desenho desenho, Grafico grafico, Tela tela ) {
        this.desenho = desenho;
        this.inicializa( tela, grafico ); 
    }
    
    public void inicializa(Tela tela, Grafico grafico) {
        this.tela = tela;    
        this.grafico = grafico;
       
        this.grafico.setPintura( this ); 
                
        bufferImagem = new BufferedImage( tela.getTelaLargura(), tela.getTelaAltura(), BufferedImage.TYPE_INT_RGB );
        graphics = bufferImagem.createGraphics();
        ((Graphics2D)graphics).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );                               
        
        for( int i = 0; i < bufferImagem.getWidth(); i++ )
            for( int j = 0; j < bufferImagem.getHeight(); j++ )
                bufferImagem.setRGB( i, j, grafico.getBGCor().getRGB() );
        
        this.grafico.graficoBufferGerado( bufferImagem ); 
    }
            
    public void pinta( Graphics g ) {
        this.pinta( g, null ); 
    }
    
    public void pinta( Graphics g, ImageObserver imgObs ) { 
        if ( desenho != null ) {                        
            grafico.antesDesenharGrafico();
            
            desenho.desenha( grafico );
            grafico.completaPintura();
                  
            g.drawImage( bufferImagem, 0, 0, imgObs );
        }
    }
    
    public void setDesenho(Desenho desenho) {
        this.desenho = desenho;
    }
    
    public void setGrafico( Grafico grafico ) {
        this.grafico = grafico;
    }
    
    public Desenho getDesenho() {
        return desenho;
    }

    public Grafico getGrafico() {
        return grafico;
    }
    
    public Tela getTela() {
        return tela;
    }

    @Override
    public Graphics getAWTGraphics() {
        return graphics;
    }

    @Override
    public BufferedImage getImagem() {
        return bufferImagem;
    }

}
