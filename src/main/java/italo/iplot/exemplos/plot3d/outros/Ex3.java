package italo.iplot.exemplos.plot3d.outros;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DDriver;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;

public class Ex3 {
        
    public static void main(String[] args) {
        PlanoCartesianoPlot3DDriver drv = (pc) -> {
            pc.setAltura2D( 1.7 ); 
            
            PCFuncObjeto3D funcObj3d = new PCFuncObjeto3D();            
            funcObj3d.setIntervalos( -Math.PI, Math.PI, -Math.PI, Math.PI );
            funcObj3d.setFunc3D( (x,z) -> {
                double d = Math.sqrt( x*x + z*z );
                return Math.sin( d );                
            } );
            
            pc.addComponenteObj3D( funcObj3d );
        };
        
        PlanoCartesianoPlot3D aplic = new PlanoCartesianoPlot3D();                    
        
        BufferedImage imagem = new BufferedImage( 640, 480, BufferedImage.TYPE_INT_RGB );
        aplic.constroi( imagem.getGraphics(), drv, imagem.getWidth(), imagem.getHeight() );
                
        String arquivo = JOptionPane.showInputDialog( "Informe o nome do arquivo a ser salvo: " );
        if ( arquivo != null ) {
            File f = new File( arquivo+".jpg" );
            try {
                ImageIO.write( imagem, "jpg", f );
                JOptionPane.showMessageDialog( null, "Arquivo salvo para: "+f.getPath() );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog( null, "Não foi possível salvar a imagem para: "+arquivo );
            }
        }
        
    }
    
}
