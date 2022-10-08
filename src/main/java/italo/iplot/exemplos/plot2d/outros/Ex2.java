package italo.iplot.exemplos.plot2d.outros;

import italo.iplot.plot2d.Plot2DDriver;
import italo.iplot.plot2d.Plot2DSimples;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import java.awt.Color;
import javax.swing.JFrame;

public class Ex2 {
        
    public static void main(String[] args) {        
        Plot2DSimples plot2D = new Plot2DSimples();
                
        Plot2DDriver drv = ( plot2d, uv ) -> {
            PCFuncObjeto2D senoFuncObj2D = new PCFuncObjeto2D();
            senoFuncObj2D.setLegenda( "seno(x)" );
            senoFuncObj2D.setXIntervalo( -Math.PI, Math.PI ); 
            senoFuncObj2D.setFunc2D( (x) -> {
                return Math.sin( x );                
            } );
            
            PCFuncObjeto2D cosFuncObj2D = new PCFuncObjeto2D();
            cosFuncObj2D.setCor( Color.RED ); 
            cosFuncObj2D.setLegenda( "cos(x)" ); 
            cosFuncObj2D.setXIntervalo( -Math.PI, Math.PI );
            cosFuncObj2D.setYIntervalo( -2, 2 );
            cosFuncObj2D.setFunc2D( (x) -> {
                return Math.cos( x );
            } );
            
            PCFuncObjeto2D tanFuncObj2D = new PCFuncObjeto2D();
            tanFuncObj2D.setCor( Color.GREEN ); 
            tanFuncObj2D.setLegenda( "tan(x)" ); 
            tanFuncObj2D.setXIntervalo( -2*Math.PI, 2*Math.PI );
            tanFuncObj2D.setYIntervalo( -2, 2 ); 
            tanFuncObj2D.limitarY( -2, 2 );
            tanFuncObj2D.setFunc2D( (x) -> {
                return Math.tan( x );
            } );
            
            PlanoCartesianoObjeto2D pc = new PlanoCartesianoObjeto2D();
            pc.setPintarEixoRotulos( true );
            pc.setPintarRegua( true );
            pc.addComponenteObj2D( senoFuncObj2D );
            pc.addComponenteObj2D( cosFuncObj2D ); 
            pc.addComponenteObj2D( tanFuncObj2D );
            
            uv.setCorFundo( new Color( 240, 240, 240 ) );
            uv.addObjeto( pc );
        };
        
        JFrame janela = new JFrame();
        janela.setTitle( "Desenho de retangulo" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( plot2D.getDesenhoComponent() ); 
        janela.setSize( 640, 640 );
        janela.setLocationRelativeTo( janela );                

        janela.setVisible( true );
        
        int w = plot2D.getDesenhoComponent().getWidth();
        int h = plot2D.getDesenhoComponent().getHeight();        
        plot2D.constroi( drv, w, h );                
    }
    
}
