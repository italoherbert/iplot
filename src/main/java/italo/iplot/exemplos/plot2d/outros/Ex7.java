package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex7 {
    
    public static void main(String[] args) {
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {                      
            PCFuncObjeto2D funcObjeto2d = new PCFuncObjeto2D(); 
            funcObjeto2d.setFunc2D( (double x) -> Math.sin( x ) ); 
            funcObjeto2d.setXIntervalo( -Math.PI, Math.PI ); 
            funcObjeto2d.setYIntervaloAtivado( false ); 
            funcObjeto2d.setLegenda( "dados" ); 
            funcObjeto2d.setArestasCor( Color.BLUE );             
            
            //pc.getPlotObj2DManager().setXYNumRotulos( 9 ); 
            pc.setPintarMouseLinhas( true ); 
            pc.addComponenteObj2D( funcObjeto2d );
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
