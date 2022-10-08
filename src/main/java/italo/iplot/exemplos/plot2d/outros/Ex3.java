package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.g2d.GrafoObjeto2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2D;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DDriver;
import italo.iplot.plot2d.planocartesiano.g2d.PCDadosObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex3 {
    
    public static void main(String[] args) {        
        PlanoCartesianoPlot2D plot2D = new PlanoCartesianoPlot2D();
                
        PlanoCartesianoPlot2DDriver drv = ( plot2d, pc ) -> {
            double x1 = -Math.PI;
            double x2 = Math.PI;
            double inc = 0.05;
            int nverts = (int)( Math.abs( x2-x1 ) / inc );
            double[] vx = new double[ nverts ];
            double[] vy = new double[ nverts ];
            double x = x1;
            for( int i = 0; i < nverts; i++ ) {
                vx[ i ] = x;
                vy[ i ] = Math.sin( x );
                x += inc;
            }
           
            nverts = 50;
            double[][] pontos = new double[ nverts ][2];
            for( int i = 0; i < pontos.length; i++ ) {
                pontos[i][0] = -2*Math.PI + ( 4 * Math.PI * Math.random() );
                pontos[i][1] = -4 + ( 8 * Math.random() );                
            }
            
            PCDadosObjeto2D dadosFuncObjeto2d = new PCDadosObjeto2D( vx, vy );
            dadosFuncObjeto2d.setLegenda( "dados" ); 
            dadosFuncObjeto2d.setArestasCor( Color.BLUE ); 
            
            GrafoObjeto2D pontosObj2d = new GrafoObjeto2D( pontos );
            pontosObj2d.setPintarVertices( true );
            pontosObj2d.setPintarArestas( false ); 
            pontosObj2d.setLegenda( "pontos" ); 
            pontosObj2d.setVerticesCor( Color.ORANGE ); 
            
            PCFuncObjeto2D cosFuncObj2D = new PCFuncObjeto2D();
            cosFuncObj2D.setXIntervaloCompleto( true );
            cosFuncObj2D.setArestasCor( Color.RED );
            cosFuncObj2D.setLegenda( "cos(x)" );
            cosFuncObj2D.setFunc2D( (px) -> {
                return Math.cos( px );                
            } );
            
            pc.getPlotObj2DManager().setXYNumRotulos( 9 ); 
            pc.addComponenteObj2D( cosFuncObj2D ); 
            pc.addComponenteObj2D( dadosFuncObjeto2d );
            pc.addComponenteObj2D( pontosObj2d );             
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
