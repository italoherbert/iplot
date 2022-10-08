package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.SuperficieObjeto3D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;

public class GrafoEx {
    
    public static void main(String[] args) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (pc) -> {            
            int len = 20;
            double[] vx = new double[ len ];
            double[] vz = new double[ len ];
            double[][] my = new double[len][len];
            double q = ( 2 * Math.PI ) / (len-1);
            
            for( int i = 0; i < len; i++ )
                vx[i] = i * q;
            
            for( int i = 0; i < len; i++ )
                vz[i] = i * q;            
                
            for( int i = 0; i < len; i++ ) {
                for( int j = 0; j < len; j++ ) {
                    my[i][j] = Math.sin( vx[i] ); 
                }
            }
            
            SuperficieObjeto3D superficie = new SuperficieObjeto3D( vx, vz, my );
            superficie.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );
            
            pc.setTitulo( "Grafo Exemplo!" ); 
            pc.addComponenteObj3D( superficie ); 
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
