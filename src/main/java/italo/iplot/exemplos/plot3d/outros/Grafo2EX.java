package italo.iplot.exemplos.plot3d.outros;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.g3d.GrafoObjeto3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;

public class Grafo2EX {
    
    public static void main(String[] args) {                
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (pc) -> {                                    
            int tam = 101;
            double[][] nos = new double[ tam ][];
            int[][] arestas = new int[ tam-1][];
            
            double j = 0;
            for( int i = 0; i < tam; i++, j+=0.05 ) {
                double y = j;
                double x = Math.cos( 2*Math.PI*j );
                double z = Math.sin( 2*Math.PI*j );
                nos[ i ] = new double[]{ x, y, z };
                if ( i > 0 ) {
                    arestas[ i-1 ] = new int[] { i-1, i };
                }
            }
                                                               
            GrafoObjeto3D grafoObj3D = new GrafoObjeto3D( nos, arestas );
            grafoObj3D.setArestasCor( Color.BLUE ); 
            grafoObj3D.setPintarVertices( false ); 
            //grafoObj3D.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );                         
            pc.setAltura2D( 1.5 ); 
            pc.addComponenteObj3D( grafoObj3D ); 
        };
        
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Grafo Exemplo" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );  
        
        plot3D.constroi( drv, c.getWidth(), c.getHeight() ); 
        plot3D.addPCRotacaoDesenhoGUIListener();
    }
    
    
}
