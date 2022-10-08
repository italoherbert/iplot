package italo.iplot.exemplos.plot3d.outros;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.CuboObjeto3D;
import italo.iplot.plot3d.g3d.est_opers.util.OperadorEst3DUtil.Debug;

public class Ex4 { 
    
    public static void main(String[] args) {
        Plot3DDriver plot3DDrv = (plot3D, uv) -> {
            CuboObjeto3D cubo = new CuboObjeto3D();
            cubo.setPintarArestas( true ); 
            cubo.setDesenharFaces( false );
            cubo.setPintarFaces( false );
            cubo.setPintarVertices( true ); 
            cubo.setFaceArestasCor( Color.WHITE );
            cubo.setArestasCor( Color.WHITE );
            cubo.setDesenharFacesDeTraz( true ); 
            cubo.setLado( 1d );
            cubo.setCor( Color.RED );            
            cubo.setVisivel( true );
            uv.addObjeto( cubo );            
            
            CuboObjeto3D cubo2 = new CuboObjeto3D();
            cubo2.setPintarArestas( true ); 
            cubo2.setDesenharFaces( false ); 
            cubo2.setPintarVertices( true );
            cubo2.setPintarFaces( false ); 
            cubo2.setArestasCor( Color.GREEN ); 
            cubo2.setVerticesCor( Color.RED ); 
            cubo2.setDesenharFacesDeTraz( true );

            cubo2.setLado( 1.0d ); 
            /*
            cubo2.setDX( 0.4d );
            cubo2.setDY( 1.5d );
            cubo2.setDZ( 0.4d );                        
            */
            
            cubo2.setCor( Color.GREEN );
            cubo2.setVisivel( false ); 
            uv.addObjeto( cubo2 );
            
            uv.constroi( plot3D ); 
                                                                 
            //plot3D.getTransformador().rotY( cubo2, Math.PI / 6, plot3D.getXYZFiltroV3D() );
            //plot3D.getTransformador().rotX( cubo2, Math.PI / 7, plot3D.getXYZFiltroV3D() );            

            plot3D.getTransformador3D().translada( cubo2, 0.5, 0.0, 0, plot3D.getXYZFiltroV3D() ); 
          
            //plot3D.getEstOper().malha( cubo, plot3D.getXYZFiltroV3D(), plot3D );                                    
            
            plot3D.getEstOper().uniao( cubo, cubo2, plot3D ); 
                           
            //plot3D.getTransformador().rotY( uv, Math.PI / 6, plot3D.getXYZFiltroV3D() );
            //plot3D.getTransformador().rotX( uv, Math.PI / 6, plot3D   .getXYZFiltroV3D() );           
                
            
            Debug debug = (Debug)plot3D.getObjeto();              
            /*
            int size = debug.vertices.size();
            int r = 10;
            for( int i = 0; i < size; i++ ) {
                Vertice3D v = new Vertice3D( debug.vertices.get( i ).getP().clone() );
                v.setRaio( r += 3 ); 
                cubo.getEstrutura().addVertice( v );
            }
            /*
            if ( debug != null ) {
                int size = debug.intersecoes.size();
                int r = 20;                                
                for( int i = 0; i < size; i++ ) {
                    Vertice3D v1 = new Vertice3D( debug.intersecoes.get( i ).getP().clone() );
                    Vertice3D v2 = new Vertice3D( debug.intersecoes.get( i==size-1 ? 0 : i+1 ).getP().clone() );                     
                    
                    cubo.getEstrutura().addVertice( v1 );
                    cubo.getEstrutura().addVertice( v2 ); 
                    cubo.getEstrutura().addAresta( new Aresta3D( v1, v2, Color.RED ) ); 
                }
            }            
            */    
            
            /*
            for( GrafoEst3DUtil.GrafoAresta ga : debug.g_arestas ) {
                Vertice3D v1 = plot3D.getEstOper().getGrafoEstUtil().getGVertice( debug.vertices, debug.intersecoes, ga.gv1 );
                Vertice3D v2 = plot3D.getEstOper().getGrafoEstUtil().getGVertice( debug.vertices, debug.intersecoes, ga.gv2 );                                
                
                Vertice3D v11 = new Vertice3D( v1.getP().clone() );
                Vertice3D v12 = new Vertice3D( v2.getP().clone() );
                
                cubo.getEstrutura().addVertice( v11 );
                cubo.getEstrutura().addVertice( v12 ); 
                
                cubo.getEstrutura().addAresta( new Aresta3D( v11, v12, Color.ORANGE ) ); 
            }
            */  
            
            uv.aplicaTransformacoes();
            
            /*
            Aresta3D a = cubo2.getEstrutura().getArestas().get( 1 );
            
            Face3D f = cubo.getEstrutura().getFaces().get( 5 );             
            Vertice3D v1 = f.getVertices().get( 0 );
            Vertice3D v2 = f.getVertices().get( 1 );
            Vertice3D v3 = f.getVertices().get( 2 );
            
            double[] p1 = v1.getP();
            double[] p2 = v2.getP();
            double[] p3 = v3.getP();
                                                    
            double[] lp0 = a.getV1().getP();
            double[] lp  = a.getV2().getP();                                 
            
            Vertice3D lp0_v = new Vertice3D( lp0.clone() );
            Vertice3D lp_v = new Vertice3D( lp.clone() );
            lp0_v.setRaio( 8 );
            lp_v.setRaio( 8 );    
            
            cubo2.getEstrutura().addVertice( lp0_v );
            cubo2.getEstrutura().addVertice( lp_v );

            cubo2.getEstrutura().addAresta( new Aresta3D( lp0_v, lp_v, Color.ORANGE ) );                        
            
            double[] pontoInt = plot3D.getMath3D().intersecaoLinhaFace( f, lp0, lp, plot3D.getXYZFiltroV3D() );                 
            if ( pontoInt != null ) {                
                //cubo2.getEstrutura().addVertice( new Vertice3D( pontoInt ) ); 
                                                    
                //boolean contemPonto = plot3D.getMath3D().verificaSePontoPertenceAFace( f.getVertices(), pontoInt, plot3D.getXYZFiltroV3D() );            
                
                //System.out.println( contemPonto );                                        

                Vertice3D vert1 = f.getVertices().get( 0 );
                Vertice3D vert2 = f.getVertices().get( 1 );
                double[] vert_p1 = vert1.getP();
                double[] vert_p2 = vert2.getP();
                double[] p_medio = plot3D.getMath3D().div( plot3D.getMath3D().soma( vert_p1, vert_p2 ), 2 );                                                    
                
                double[] arestaP0 = f.getVertices().get( 2 ).getP();
                double[] arestaP = f.getVertices().get( 3 ).getP();                                                           

                double[] p_int = plot3D.getMath3D().intersecaoRetas( arestaP0, arestaP, p_medio, pontoInt );
                
                System.out.println( p_medio[0]+" "+p_medio[1]+" "+p_medio[2]+"  "+pontoInt[0]+"  "+pontoInt[1]+" "+pontoInt[2] );
                Vertice3D pm = new Vertice3D( p_medio );                                
                Vertice3D pi = new Vertice3D( pontoInt );                
                Vertice3D retaInt = new Vertice3D( p_int );
                pi.setRaio( 20 );
                pm.setRaio( 25 );    
                retaInt.setRaio( 30 ); 
                                
                cubo.getEstrutura().addVertice( pi ); 
                cubo.getEstrutura().addVertice( pm ); 
                cubo.getEstrutura().addVertice( retaInt ); 
                
                Vertice3D a_v1 = new Vertice3D( arestaP0.clone() );
                Vertice3D a_v2 = new Vertice3D( arestaP.clone() );
                a_v1.setRaio( 8 );
                a_v2.setRaio( 8 );    
            
                cubo.getEstrutura().addVertice( a_v1 );
                cubo.getEstrutura().addVertice( a_v2 );

                cubo.getEstrutura().addAresta( new Aresta3D( a_v1, a_v2, Color.RED ) );
                cubo.getEstrutura().addAresta( new Aresta3D( pm, pi, Color.BLUE ) );
            }
            */
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
    }        
    
}
