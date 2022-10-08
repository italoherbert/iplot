package italo.iplot.exemplos.plot3d.outros;

import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.gui.plot.Plot3DGUI;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametricaObjeto3D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EsferoideEx {
    
    public static void main(String[] args) {
        PlanoCartesianoPlot3D plot3D = new PlanoCartesianoPlot3D();
        PlanoCartesianoPlot3DDriver drv = (pc) -> {  
            int npu = 20;
            int npv = 20;
            
            PCFuncParametricaObjeto3D funcObj3d = new PCFuncParametricaObjeto3D();
            Func3D raioFunc = ( u, v ) -> 1;//2 + Math.sin( 7*u + 5*v ); 
            funcObj3d.setFuncX( ( u, v ) -> raioFunc.getY( u, v ) * Math.cos( u ) * Math.sin( v ) );
            funcObj3d.setFuncZ( ( u, v ) -> raioFunc.getY( u, v ) * Math.sin( u ) * Math.sin( v ) );
            funcObj3d.setFuncY( ( u, v ) -> raioFunc.getY( u, v ) * Math.cos( v ) );
            funcObj3d.setVetoresUeV( 0, 2*Math.PI, npu, 0, Math.PI, npv );
            
            funcObj3d.setPreenchimento( Objeto3D.Preenchimento.GRADIENTE );                                     
            funcObj3d.setPintarFaces( true ); 
                        
            pc.setDY( 1.0d );
            pc.addComponenteObj3D( funcObj3d ); 
            pc.setTitulo( "Esferoide" );
        };
                                       
        Plot3DGUI plotGUI = plot3D.novoPlotGUI();             
        
        JFrame janela = new JFrame();
        janela.setTitle( "Esferoide Exemplo" ); 
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        janela.setContentPane( plotGUI ); 
        janela.setSize( 500, 500 );
        janela.setLocationRelativeTo( janela );   
        
        SwingUtilities.invokeLater( () -> {
            janela.setVisible( true );
                
            int w = plot3D.getDesenhoComponent().getWidth();
            int h = plot3D.getDesenhoComponent().getHeight();  
            plot3D.constroi( drv, w, h );            
        } );
                        
    }
    
}
