package italo.iplot.exemplos.plot3d.principal;

import italo.iplot.exemplos.plot3d.principal.controller.GaussianaController;
import italo.iplot.exemplos.plot3d.principal.gui.JanelaPrincipalGUI;
import italo.iplot.exemplos.plot3d.principal.util.MSGUtil;
import italo.iplot.funcs.g3d.Func3D;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import italo.iplot.plot3d.Plot3DDriver;
import italo.iplot.plot3d.Plot3DSimples;
import italo.iplot.plot3d.g3d.ConstroiObj3DAdapter;
import italo.iplot.plot3d.g3d.CuboObjeto3D;
import italo.iplot.plot3d.g3d.FuncObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.funcs.g3d.GaussianaFunc3D;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametricaObjeto3D;
import javax.swing.JComponent;

public class PlanoCartesiano3DPrincipal {
    
    private final static double FUNCOES_PLANOS_CARTESIANOS_ALTURA = 1.9d;          
    
    private final static int LARGURA_PAINEL = 800;
    private final static int ALTURA_PAINEL = 600;
    
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(() -> {            
            
            JanelaPrincipalGUI jp = new JanelaPrincipalGUI( LARGURA_PAINEL, ALTURA_PAINEL );
            
            // *************************************************************
            // 4 FUNÇÕES
            // *************************************************************
            
            PlanoCartesianoPlot3D[] funcoesAplics = {
                new PlanoCartesianoPlot3D(),
                new PlanoCartesianoPlot3D(),
                new PlanoCartesianoPlot3D(),
                new PlanoCartesianoPlot3D()
            };

            PlanoCartesianoPlot3DDriver[] funcoesDRVs = {
                ( pc ) -> {
                    pc.setAltura2D( FUNCOES_PLANOS_CARTESIANOS_ALTURA );            
                    
                    int npu = 20;
                    int npv = 20;

                    int a = 1;
                    int r = 4;

                    Func3D fx = ( u, v ) -> ( r + ( a * Math.cos( u ) ) ) * Math.cos( v );
                    Func3D fy = ( u, v ) -> ( r + ( a * Math.cos( u ) ) ) * Math.sin( v );
                    Func3D fz = ( u, v ) -> a * Math.sin( u );

                    PCFuncParametricaObjeto3D funcObj3d = new PCFuncParametricaObjeto3D();
                    funcObj3d.setFuncX( fx );
                    funcObj3d.setFuncZ( fy );
                    funcObj3d.setFuncY( fz );            
                    funcObj3d.setVetoresUeV( 0, 2*Math.PI, npu, 0, 2*Math.PI, npv );            
                    funcObj3d.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );                                     
                    funcObj3d.setPintarFaces( true ); 

                    PCFuncParametricaObjeto3D funcObj3d_x90 = new PCFuncParametricaObjeto3D();
                    funcObj3d_x90.setFuncX( fx );
                    funcObj3d_x90.setFuncZ( fy );
                    funcObj3d_x90.setFuncY( fz );
                    funcObj3d_x90.setVetoresUeV( 0, 2*Math.PI, npu, 0, 2*Math.PI, npv );
                    funcObj3d_x90.setFuncParametricaOpers( ( double[][] mat, int k ) -> {
                        double ang = Math.PI/2;
                        double[][] rotX90 = {
                            { 1, 0, 0 },
                            { 0, Math.cos( ang ), -Math.sin( ang ) },
                            { 0, Math.sin( ang ),  Math.cos( ang ) }
                        };

                        int nl = mat.length;
                        int nc = mat[0].length;

                        double[][] result = new double[ nl ][ nc ];
                        for( int i = 0; i < nl; i++ )
                            for( int j = 0; j < nc; j++ )
                                result[i][j] = 0;

                        for( int i = 0; i < nc; i++ )
                            for( int j = 0; j < nl; j++ )
                                for( int l = 0; l < rotX90.length; l++ )
                                    result[ j ][ i ] += mat[ l ][ i ] * rotX90[ j ][ l ];

                        for( int j = 0; j < result[0].length; j++ )
                            result[ 0 ][ j ] += 5;

                        return result;
                    } ); 

                    funcObj3d_x90.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );                                     
                    funcObj3d_x90.setPintarFaces( true ); 

                    pc.addComponenteObj3D( funcObj3d ); 
                    pc.addComponenteObj3D( funcObj3d_x90 ); 
                    pc.setDZ( 0.75 ); 
                },
                ( pc ) -> {
                    pc.setAltura2D( FUNCOES_PLANOS_CARTESIANOS_ALTURA );            
                    
                    int npu = 50;
                    int npv = 50;

                    PCFuncParametricaObjeto3D funcObj3d = new PCFuncParametricaObjeto3D();
                    Func3D raioFunc = ( u, v ) -> 2 + Math.sin( 7*u + 5*v ); 
                    funcObj3d.setFuncX( ( u, v ) -> raioFunc.getY( u, v ) * Math.cos( u ) * Math.sin( v ) );
                    funcObj3d.setFuncZ( ( u, v ) -> raioFunc.getY( u, v ) * Math.sin( u ) * Math.sin( v ) );
                    funcObj3d.setFuncY( ( u, v ) -> raioFunc.getY( u, v ) * Math.cos( v ) );
                    funcObj3d.setVetoresUeV( 0, 2*Math.PI, npu, 0, Math.PI, npv );

                    funcObj3d.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );                                     
                    funcObj3d.setPintarFaces( true ); 

                    pc.addComponenteObj3D( funcObj3d ); 
                },
                ( pc ) -> {
                    pc.setAltura2D( FUNCOES_PLANOS_CARTESIANOS_ALTURA );            
                    pc.setPontilharGrade( false ); 
                    pc.setGradeCor( new Color( 100, 230, 250 ) );
                    
                    PCFuncObjeto3D funcObj3D = new PCFuncObjeto3D();
                    funcObj3D.setIntervalos( -5 , 5, -5 , 5  );                        
                    funcObj3D.setFunc3D( (x, z) -> {
                        return 12 * Math.cos( ( x*x+z*z ) / 4 ) / ( 3 + x*x + z*z );                         
                    } ); 
                    
                    pc.addComponenteObj3D( funcObj3D );
                },
                ( pc ) -> {
                    pc.setAltura2D( FUNCOES_PLANOS_CARTESIANOS_ALTURA );            
                                        
                    double a = 1.25d;
                    double b = 2 * Math.PI;
                    
                    // SEASHELL FUNC
                    PCFuncParametricaObjeto3D funcObj3D = new PCFuncParametricaObjeto3D();
                    funcObj3D.setVetoresUeV( -Math.PI, Math.PI, -Math.PI, Math.PI );
                    funcObj3D.setFuncX( ( u, v ) -> a * ( 1 - v/b ) * Math.cos( 2*v ) * ( 1 + Math.cos( u ) ) + Math.cos( 2*v ) );
                    funcObj3D.setFuncZ( ( u, v ) -> a * ( 1 - v/b ) * Math.sin( 2*v ) * ( 1 + Math.cos( u ) ) + Math.sin( 2*v ) );
                    funcObj3D.setFuncY( ( u, v ) -> 10*v/b + a * ( 1 - v/b ) * Math.sin( u ) + 15 ); 
                    
                    pc.setDY( 1.0d );
                    pc.addComponenteObj3D( funcObj3D ); 
                }
            };     
            
            DesenhoUI[] funcoesUIs = new DesenhoUI[ funcoesAplics.length ];
            for( int i = 0; i < funcoesAplics.length; i++ )
                funcoesUIs[ i ] = funcoesAplics[ i ].getDesenhoUI();
            jp.addFuncoesUIs( funcoesUIs );                             
            
            // *******************************************************************
            // GAUSSIANA
            // *******************************************************************
            
            MSGUtil msgUtil = new MSGUtil();
            msgUtil.setTitulo( () -> {
                return jp.getTitle();
            } );
            
            GaussianaFunc3D gaussianaF3D = new GaussianaFunc3D( 40, 80 );
            PCFuncObjeto3D gaussianaFuncObj3D = new PCFuncObjeto3D();            
            
            PlanoCartesianoPlot3D gaussianaF3DAplic = new PlanoCartesianoPlot3D();
            PlanoCartesianoPlot3DDriver gaussianaF3DDRV = ( pc ) -> {
                pc.setAltura2D( 2.0d );            
                pc.setTitulo( "Função gaussiana" ); 
                pc.getPlotObj3DManager().setXYZNumRotulos( 9 );
                
                gaussianaFuncObj3D.setXIntervalo( -50 , 50  );
                gaussianaFuncObj3D.setZIntervalo( -50 , 50  );                        
                gaussianaFuncObj3D.setFunc3D( gaussianaF3D );
                
                pc.addComponenteObj3D( gaussianaFuncObj3D );
            };                        
                        
            jp.setGaussianaListener( new GaussianaController( gaussianaF3DAplic, gaussianaFuncObj3D, gaussianaF3D, msgUtil ) );

            Dimension gaussianaDIM = jp.addGaussianaDesenhoUI( gaussianaF3DAplic.getDesenhoUI() );
            gaussianaF3DAplic.constroi( gaussianaF3DDRV, gaussianaDIM.width, gaussianaDIM.height ); 
            gaussianaF3DAplic.addPCRotacaoDesenhoGUIListener();
            
            jp.getGaussianaGUI().setFuncRaio( gaussianaF3D.getRaio() );
            jp.getGaussianaGUI().setFuncAltura( gaussianaF3D.getAltura() );
            
            jp.getGaussianaGUI().setNDivX( gaussianaFuncObj3D.getXNDivs() ); 
            jp.getGaussianaGUI().setNDivZ( gaussianaFuncObj3D.getZNDivs() ); 
            
            jp.getGaussianaGUI().setX1( gaussianaFuncObj3D.getX1() );
            jp.getGaussianaGUI().setX2( gaussianaFuncObj3D.getX2() );
            jp.getGaussianaGUI().setZ1( gaussianaFuncObj3D.getZ1() );
            jp.getGaussianaGUI().setZ2( gaussianaFuncObj3D.getZ2() );
            
            jp.getGaussianaGUI().setPintarVertices( gaussianaFuncObj3D.isPintarVertices() );
            jp.getGaussianaGUI().setDesenharFaces( gaussianaFuncObj3D.isDesenharFaces() );
            jp.getGaussianaGUI().setPintarFaces( gaussianaFuncObj3D.isPintarFaces() ); 
            

            //*********************************************************************
            // GAUSSIANA 2
            //*********************************************************************
           
            Plot3DSimples gaussianaPlot3D = new Plot3DSimples();
            Plot3DDriver gaussianaPlot3DDriver = (plot3D, uv) -> {
                FuncObjeto3D funcObj3D = new FuncObjeto3D();
                funcObj3D.setDivX( 30 );
                funcObj3D.setDivZ( 30 );
                funcObj3D.setDX( 1.0d );
                funcObj3D.setDZ( 1.0d );
                funcObj3D.setCor( new Color( 50, 150, 250 ) );
                
                funcObj3D.setFunc3D( new GaussianaFunc3D( 0.4d, 0.8d ) ); 
                                                
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
            
            Dimension gaussiana2DIM = jp.addGaussiana2DesenhoUI( gaussianaPlot3D.getDesenhoUI() ); 
            gaussianaPlot3D.constroi( gaussianaPlot3DDriver, gaussiana2DIM.width, gaussiana2DIM.height );            
            gaussianaPlot3D.addRotacaoDesenhoGUIListener();
           
            //*********************************************************************
            // CUBO
            //*********************************************************************
            
            Plot3DSimples cuboPlot3D = new Plot3DSimples();
            Plot3DDriver cuboPlot3DDrv = (plot3D, uv) -> {
                CuboObjeto3D cubo = new CuboObjeto3D();
                cubo.setDesenharFaces( false );
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
            
            Dimension cuboDIM = jp.addCuboDesenhoUI( cuboPlot3D.getDesenhoUI() ); 
            cuboPlot3D.constroi( cuboPlot3DDrv, cuboDIM.width, cuboDIM.height );
            cuboPlot3D.addRotacaoDesenhoGUIListener();
            
            //*******************************************************************
                                                
            jp.empacotaECentraliza();
                                    
            for( int i = 0; i < funcoesAplics.length; i++ ) { 
                JComponent c = funcoesUIs[ i ].getJComponent();
                funcoesAplics[ i ].constroi( funcoesDRVs[ i ], c.getWidth(), c.getHeight() );                         
                funcoesAplics[ i ].addPCRotacaoDesenhoGUIListener();
            }
            
            jp.setVisible( true );                                     
        } );
    }    
    
}
