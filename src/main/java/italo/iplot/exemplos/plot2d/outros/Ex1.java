package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.plot2d.Plot2DDriver;
import italo.iplot.plot2d.Plot2DSimples;
import italo.iplot.plot2d.g2d.RetanguloObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex1 {
    
    public static void main(String[] args) {        
        Plot2DSimples plot2D = new Plot2DSimples();
        
        RetanguloObjeto2D retObj2d = new RetanguloObjeto2D( -0.5, -0.5, 0.5, 0.5 );
        
        Plot2DDriver drv = ( plot2d, uv ) -> {
            retObj2d.setCor( Color.ORANGE );
            retObj2d.setArestasCor( Color.WHITE );
            retObj2d.setVerticesCor( Color.BLUE ); 
            
            uv.addObjeto( retObj2d );
        };
        
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho de retangulo" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( plot2D.getDesenhoComponent() ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );
        
        int w = plot2D.getDesenhoComponent().getWidth();
        int h = plot2D.getDesenhoComponent().getHeight();        
        plot2D.constroi( drv, w, h );
        
        plot2D.getTransformador2D().rot( retObj2d , Math.PI/6, plot2D.getXYFiltroV2D() );
        retObj2d.aplicaTransformacoes();
        plot2D.getDesenhoUI().repaint();
        
    }
    
}
