package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.GloboObjeto3D;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GloboEx {
    
    public static void main(String[] args) {
        Plot3DDriver plot3DDrv = (plot3D, uv) -> {
            GloboObjeto3D globoObj3d = new GloboObjeto3D();
            globoObj3d.setRaio( 0.5d ); 
            globoObj3d.setNumDivs( 16 ); 
            globoObj3d.setPintarVertices( false );
            globoObj3d.setPintarArestas( true ); 
            globoObj3d.setPintarFaces( true );
            globoObj3d.setVerticesCor( Color.YELLOW ); 
            
            uv.addObjeto( globoObj3d ); 
        };
        
        Plot3DSimples plot3D = new Plot3DSimples();
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho de globo em 3D" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 480 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );         

        plot3D.constroi( plot3DDrv, c.getWidth(), c.getHeight() ); 
        plot3D.addRotacaoDesenhoGUIListener();
    }
    
}
