package italo.iplot.exemplos.plot3d.outros;

import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;

public class Ex1 {
    
    public static void main( String[] args ) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (plano) -> {            
            plano.setAltura2D( 1.2 );
            plano.setTitulo( "Função y=x³+3z²" ); 
            
            PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();
            funcObj3d.setIntervalos( -Math.PI, Math.PI, -Math.PI, Math.PI );        
            funcObj3d.setFunc3D( (x,z) -> {
                return Math.pow( x, 3 ) + 3*Math.pow( z, 2 );            
            } ); 
            
            plano.addComponenteObj3D( funcObj3d ); 
        };
        
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho da função y=x³+3z² em 3D" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );  
        
        plot3D.constroi( drv, c.getWidth(), c.getHeight() );  
        plot3D.addPCRotacaoDesenhoGUIListener();
    }
    
}
