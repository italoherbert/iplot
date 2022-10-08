package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.plot2d.Plot2DDriver;
import italo.iplot.plot2d.Plot2DSimples;
import italo.iplot.plot2d.g2d.RetanguloObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex4 {
    
    public static void main(String[] args) {
        Plot2DSimples plot2D = new Plot2DSimples();
        
        RetanguloObjeto2D retObj2d = new RetanguloObjeto2D( -0.9, -0.9, 0.9, 0.6 );
        
        Plot2DDriver drv = ( plot2d, uv ) -> {
            retObj2d.setCor( Color.YELLOW );
            retObj2d.setArestasCor( Color.WHITE );
            retObj2d.setVerticesCor( Color.RED ); 
            retObj2d.setPintarVertices( true );
            
            uv.setCorFundo( new Color( 240, 240, 240 ) );
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
    }
    
}
