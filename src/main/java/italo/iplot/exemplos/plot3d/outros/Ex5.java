package italo.iplot.exemplos.plot3d.outros;

import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import javax.swing.SwingUtilities;

public class Ex5 {
    
    public static void main( String[] args ) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (plano) -> {            
            plano.setAltura2D( 1.7 );
            
            PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();            
            funcObj3d.setIntervalos( -Math.PI, Math.PI, -Math.PI, Math.PI );        
            funcObj3d.setFunc3D( (x,z) -> {
                return 1 / x;            
            } ); 
            
            plano.addComponenteObj3D( funcObj3d );
        };
        
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho da função y=1/x em 3D" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 480 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );  

        plot3D.constroi( drv, c.getWidth(), c.getHeight() );                 
    }
    
}
