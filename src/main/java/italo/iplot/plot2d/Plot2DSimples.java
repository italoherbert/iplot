package italo.iplot.plot2d;

import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.plot2d.g2d.UniversoVirtual2D;
import java.awt.Color;
import java.awt.Graphics;

public class Plot2DSimples extends Plot2D {
                 
    public Plot2DSimples() {
        super.grafico = super.novoJava2DGrafico();
    }
    
    public void constroi( Graphics g, Plot2DDriver drv, int largura, int altura ) {
        this.constroi( g, drv, new TelaImpl( largura, altura ) ); 
    }
            
    public void constroi( Graphics g, Plot2DDriver drv, Tela tela ) {
        super.inicializaPintura( tela ); 
        
        universoVirtual = new UniversoVirtual2D();
        drv.configura( this, universoVirtual );
        universoVirtual.constroi( this );
        
        this.getPintura().pinta( g ); 
    }            
    
    public void constroi( Plot2DDriver drv, int largura, int altura ) {
        this.constroi( drv, new TelaImpl( largura, altura ) );
    }
            
    public void constroi( Plot2DDriver drv, Tela tela ) {
        super.inicializaPintura( tela ); 
                                                
        universoVirtual = new UniversoVirtual2D();
        drv.configura( this, universoVirtual );
        universoVirtual.constroi( this ); 
                
        super.getDesenhoUI().repaint();
    }

    @Override
    public void setMouseIXYValor( String valor, Color cor ) {
        
    }
        
}
