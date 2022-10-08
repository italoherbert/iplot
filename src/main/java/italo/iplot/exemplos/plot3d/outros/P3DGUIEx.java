package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.gui.plot.PlotGUI;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class P3DGUIEx {
    
    public static void main( String[] args ) {
        PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();            
                
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (plano) -> {            
            plano.setAltura2D( 2.0d );
            plano.setTitulo( "Função seno(d(x,z))" ); 
                        
            funcObj3d.setIntervalos( -Math.PI, Math.PI, -Math.PI, Math.PI );
            funcObj3d.setFunc3D( (x,z) -> {
                double d = Math.sqrt( x*x + z*z );
                return Math.sin( d );                
            } ); 
            
            plano.addComponenteObj3D( funcObj3d );
        };
        
        PlotGUI pgui = plot3D.novoPlotGUI();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho da função seno(d(x,z))" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( pgui ); 
        janela.setSize( 500, 500 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );  
                    
        JComponent c = plot3D.getDesenhoComponent();
        plot3D.constroi( drv, c.getWidth(), c.getHeight() );        
    }
    
}
