package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.ConstroiObj3DAdapter;
import italo.iplot.plot3d.g3d.CuboObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Ex7 {
    
    public static void main(String[] args) {
        Plot3DSimples cuboPlot3D = new Plot3DSimples();
        Plot3DDriver cuboPlot3DDrv = (plot3D, uv) -> {
            CuboObjeto3D cubo = new CuboObjeto3D();
            cubo.setPintarArestas( true );
            cubo.setPintarFaces( false ); 
            cubo.setLado( 1.0d );
            cubo.setCor( Color.RED );
            uv.addObjeto( cubo );

            uv.setConstroiObj3DListener( new ConstroiObj3DAdapter() {
                @Override
                public void construiuParcialmente(Objeto3D obj, Objeto3DTO to) {
                    plot3D.getTransformador3D().rotY( cubo, Math.PI / 6, plot3D.getXYZFiltroV3D() );
                    plot3D.getTransformador3D().rotX( cubo, Math.PI / 6, plot3D.getXYZFiltroV3D() );
                    uv.aplicaTransformacoes();
                }

            } );                                                                                
        }; 

        JComponent c = cuboPlot3D.getDesenhoComponent();

        JFrame janela = new JFrame();
        janela.setTitle( "Desenho de cubo 3D" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );   

        int w = c.getWidth();
        int h = c.getHeight();
        cuboPlot3D.setGrafico( cuboPlot3D.novoAlocaImagemGrafico() ); 
        cuboPlot3D.constroi( cuboPlot3DDrv, w, h );
        cuboPlot3D.addRotacaoDesenhoGUIListener();
    }
    
}
