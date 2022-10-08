package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.funcs.g3d.GaussianaFunc3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GaussianaEx {
    
    public static void main( String[] args ) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (plano) -> {            
            plano.setAltura2D( 1.9 );
            plano.setTitulo( "Função gaussiana" ); 
            plano.setDY( 0.8d ); 
            
            PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();
            funcObj3d.setGradienteCores( new Color( 255, 150, 10 ), new Color( 255, 100, 50 ) );
            funcObj3d.setDesenharFaces( false ); 
            funcObj3d.setIntervalos( -10, 10, -10, 10 );        
            funcObj3d.setFunc3D( new GaussianaFunc3D( 9, 10 ) ); 
            
            plano.addComponenteObj3D( funcObj3d ); 
            
            plano.gradeVisivel( false, plot3D );
            plano.reguaVisivel( false, plot3D );
        };
        
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho da função gaussiana" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );  
        
        plot3D.constroi( drv, c.getWidth(), c.getHeight() );  
        plot3D.addPCRotacaoDesenhoGUIListener();
    }
    
}
