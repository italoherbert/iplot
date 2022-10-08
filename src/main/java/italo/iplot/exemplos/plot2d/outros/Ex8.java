package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.funcs.g2d.Func2D;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCDadosObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex8 {
        
    public static void main(String[] args) {
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {                      
            PCDadosObjeto2D funcObjeto2d = new PCDadosObjeto2D(); 
            
            double r = 5;
            Func2D fx = ( t ) -> r * Math.cos( t );
            Func2D fy = ( t ) -> r * Math.sin( t );
            
            funcObjeto2d.setFuncsParametricas( -Math.PI, Math.PI,(int)Math.round( 2*Math.PI*r ), fx, fy );
            funcObjeto2d.setLegenda( "dados" ); 
            funcObjeto2d.setArestasCor( Color.BLUE );             
            funcObjeto2d.setXIntervaloAtivado( true );
            funcObjeto2d.setYIntervaloAtivado( true );
            funcObjeto2d.setMinX( -10 );
            funcObjeto2d.setMaxX( 10 );
            funcObjeto2d.setMinY( -10 );
            funcObjeto2d.setMaxY( 10 );
            
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
