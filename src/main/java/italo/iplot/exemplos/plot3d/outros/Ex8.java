package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.gui.plot.Plot3DGUI;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametricaObjeto3D;
import javax.swing.JFrame;

public class Ex8 {
    
    public static void main(String[] args) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (pc) -> {  
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
                                                                                    
            pc.setAltura2D( 1.5 ); 
            pc.addComponenteObj3D( funcObj3d ); 
            pc.addComponenteObj3D( funcObj3d_x90 ); 
            pc.setDZ( 0.75 );
        };
        
        plot3D.setGrafico( plot3D.novoAlocaImagemGrafico() ); 
                               
        Plot3DGUI plotGUI = plot3D.novoPlotGUI();              
        
        JFrame janela = new JFrame();
        janela.setTitle( "Esferoide Exemplo" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( plotGUI ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                
        janela.setVisible( true );
                
        int w = plot3D.getDesenhoComponent().getWidth();
        int h = plot3D.getDesenhoComponent().getHeight();  
        plot3D.constroi( drv, w, h );
    }
    
}
