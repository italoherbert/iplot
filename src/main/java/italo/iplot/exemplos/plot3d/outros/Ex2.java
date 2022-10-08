package italo.iplot.exemplos.plot3d.outros;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class Ex2 extends JFrame implements PlanoCartesianoPlot3DDriver {
        
    private PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
    private int painelLargura = 640;
    private int painelAltura = 480;
    
    public Ex2() {
        JComponent desenhoComp = plot3D.getDesenhoComponent();
        desenhoComp.setPreferredSize( new Dimension( painelLargura, painelAltura ) );        
                        
        super.setTitle( "Desenho da função x³+3z² em 3D" ); 
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        super.setContentPane( desenhoComp ); 
        super.pack();
        super.setLocationRelativeTo( this );        
         
        plot3D.constroi( this, painelLargura, painelAltura ); 
        plot3D.addPCRotacaoDesenhoGUIListener();
    }

    @Override
    public void configura(PlanoCartesianoObjeto3D pc) {
        pc.setAltura2D( 1.7 );
        
        PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();                    
        funcObj3d.setIntervalos( -Math.PI, Math.PI, -Math.PI, Math.PI );        
        funcObj3d.setFunc3D( (x,z) -> {
            return Math.pow( x, 3 ) + 3*Math.pow( z, 2 );            
        } );     
        
        pc.addComponenteObj3D( funcObj3d ); 
    }
    
    public static void main( String[] args ) {
        Ex2 ex = new Ex2();
        ex.setVisible( true );         
    }
    
}
