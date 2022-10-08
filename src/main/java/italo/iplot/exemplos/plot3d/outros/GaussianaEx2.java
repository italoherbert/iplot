package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.funcs.g3d.GaussianaFunc3D;
import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.ConstroiObj3DAdapter;
import italo.iplot.plot3d.g3d.FuncObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GaussianaEx2 {
    
    public static void main( String[] args ) {
        Plot3DSimples gaussianaPlot3D = new Plot3DSimples();
        Plot3DDriver gaussianaPlot3DDriver = (plot3D, uv) -> {            
            FuncObjeto3D funcObj3D = new FuncObjeto3D();
            funcObj3D.setDivX( 30 );
            funcObj3D.setDivZ( 30 );
            funcObj3D.setDX( 1.0d );
            funcObj3D.setDZ( 1.0d );
            funcObj3D.setGradienteCores( new Color( 255, 150, 10 ), new Color( 255, 100, 50 ) );

            funcObj3D.setFunc3D( new GaussianaFunc3D( 0.45d, 0.8d ) ); 

            uv.setCorFundo( Color.WHITE ); 
            uv.addObjeto( funcObj3D );

            uv.setConstroiObj3DListener( new ConstroiObj3DAdapter() {
                @Override
                public void construiuParcialmente(Objeto3D obj, Objeto3DTO to) {
                    plot3D.getTransformador3D().rotX( funcObj3D, -Math.PI / 4, plot3D.getXYZFiltroV3D() );
                    plot3D.getTransformador3D().rotY( funcObj3D, Math.PI / 4, plot3D.getXYZFiltroV3D() );
                    uv.aplicaTransformacoes();
                }
            } );             
        };

        JComponent c = gaussianaPlot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho da função gaussiana" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );
        
        gaussianaPlot3D.constroi( gaussianaPlot3DDriver, c.getWidth(), c.getHeight() );            
        gaussianaPlot3D.addRotacaoDesenhoGUIListener();
    }
    
}
