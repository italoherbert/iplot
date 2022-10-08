package italo.iplot.exemplos.plot3d.outros;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.ConstroiObj3DAdapter;
import italo.iplot.plot3d.g3d.CuboObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.RetanguloObjeto3D;

public class Ex6 { 
    
    public static void main(String[] args) {
        Plot3DDriver plot3DDrv = (plot3D, uv) -> {
            CuboObjeto3D cubo = new CuboObjeto3D();
            cubo.setPintarArestas( true ); 
            cubo.setDesenharFaces( false ); 
            cubo.setPintarVertices( true );
            cubo.setPintarFaces( true ); 
            cubo.setArestasCor( Color.WHITE ); 
            cubo.setVerticesCor( Color.BLUE ); 
            cubo.setDesenharFacesDeTraz( true );
                        
            cubo.setDX( 0.4d );
            cubo.setDY( 1.5d );
            cubo.setDZ( 0.4d );            
            
            cubo.setCor( Color.GREEN );
            cubo.setVisivel( true ); 
            uv.addObjeto( cubo );           

            RetanguloObjeto3D ret = new RetanguloObjeto3D();
            ret.setPintarArestas( true ); 
            ret.setPintarVertices( true );
            ret.setPintarFaces( true ); 
            ret.setArestasCor( Color.GREEN ); 
            ret.setVerticesCor( Color.RED ); 
            ret.setDXDZ( 1.2, 1.2 ); 
            ret.setDuasFaces( false ); 
            ret.setInverterVN( true ); 
            ret.setCor( Color.GREEN );
            ret.setVisivel( true ); 
            uv.addObjeto( ret );
            
            //ret.constroi( plot3D ); 
                  
            uv.addConstroiObj3DListener( new ConstroiObj3DAdapter() { 
                @Override
                public void construiuParcialmente(Objeto3D obj, Objeto3DTO to) {
                    /*
                    to.getTransformador3D().translada( ret, 0.1, 0.1, 0.0, to.getXYZFiltroV3D() ); 
                        
            
                    double[][] pontos = to.getMath3D().planoPontos( ret.getFace(), to.getXYZFiltroV3D() );

                    to.getCortador3D().corte( cubo.getEstrutura(), pontos, ret.isInverterVN(), to.getXYZFiltroV3D(), to );                                   
                    cubo.aplicaTransformacoes();
                    */
                }
            } );             
            //plot3D.getTransformador().rotY( ret, Math.PI / 6, plot3D.getXYZFiltroV3D() );
            //plot3D.getTransformador().rotX( ret, Math.PI / 4, plot3D.getXYZFiltroV3D() );
                        
            //plot3D.getTransformador().rotY( uv, Math.PI / 6, plot3D.getXYZFiltroV3D() );
            //plot3D.getTransformador().rotX( uv, Math.PI / 6, plot3D.getXYZFiltroV3D() );                                               
        };
        
        Plot3DSimples plot3D = new Plot3DSimples();
        JComponent c = plot3D.getDesenhoComponent();
                   
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho do cubo em 3D" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( c ); 
        janela.setSize( 640, 480 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );         

        plot3D.constroi( plot3DDrv, c.getWidth(), c.getHeight() ); 
        plot3D.addRotacaoDesenhoGUIListener();
    }        
    
}
