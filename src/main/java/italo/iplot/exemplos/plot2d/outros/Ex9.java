package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex9 {
    
    public static void main(String[] args) {        
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {            
            PCFuncObjeto2D senFuncObj2D = new PCFuncObjeto2D();
            senFuncObj2D.setXIntervalo( -Math.PI, Math.PI );
            senFuncObj2D.setYIntervalo( -2, 2 ); 
            senFuncObj2D.setXIntervaloCompleto( true );
            senFuncObj2D.setArestasCor( Color.BLUE );
            senFuncObj2D.setLegenda( "sen(x)" );
            senFuncObj2D.setFunc2D( (px) -> {
                return Math.sin( px );                
            } );
                                    
            PCFuncObjeto2D cosFuncObj2D = new PCFuncObjeto2D();
            cosFuncObj2D.setXIntervalo( -Math.PI, Math.PI );
            cosFuncObj2D.setYIntervalo( -2, 2 ); 
            cosFuncObj2D.setXIntervaloCompleto( true );
            cosFuncObj2D.setArestasCor( Color.RED );
            cosFuncObj2D.setLegenda( "cos(x)" );
            cosFuncObj2D.setFunc2D( (px) -> {
                return Math.cos( px );                
            } );
            
            pc.addComponenteObj2D( senFuncObj2D );
            pc.addComponenteObj2D( cosFuncObj2D ); 
            pc.setTitulo( "Seno e Cosseno" ); 
        };
        
        Plot2DGUI plotGUI = plot2D.novaPlot2DGUI(); 
        
        JFrame janela = new JFrame();
        janela.setTitle( "Funções seno e cosseno" ); 
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
