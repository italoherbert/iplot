package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCDadosObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex5 {
    
    public static void main(String[] args) {        
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {
            double x1 = -Math.PI;
            double x2 = Math.PI;
            double inc = 0.5;
            int nverts = (int)( Math.abs( x2-x1 ) / inc );
            double[] vx = new double[ nverts ];
            double[] vy = new double[ nverts ];
            double x = x1;
            for( int i = 0; i < nverts; i++ ) {
                vx[ i ] = x;
                vy[ i ] = Math.sin( x );
                x += inc;
            }
                       
            PCDadosObjeto2D dadosFuncObjeto2d = new PCDadosObjeto2D( vx, vy );
            dadosFuncObjeto2d.setLegenda( "dados" ); 
            dadosFuncObjeto2d.setArestasCor( Color.BLUE ); 
            
            //pc.getPlotObj2DManager().setXYNumRotulos( 9 ); 
            pc.addComponenteObj2D( dadosFuncObjeto2d );
        };
        
        Plot2DGUI plotGUI = plot2D.novaPlot2DGUI(); 
        
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho de Funções" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( plotGUI ); 
        janela.setSize( 500, 500 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );
        
        int w = plot2D.getDesenhoComponent().getWidth();
        int h = plot2D.getDesenhoComponent().getHeight();        
        plot2D.setGrafico( plot2D.novoAlocaImagemGrafico() ); 
        plot2D.constroi( drv, w, h );
    }
    
}
