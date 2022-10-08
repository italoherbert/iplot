package italo.iplot.plot3d.grafico;

import italo.iplot.grafico.Java2DGrafico;
import italo.iplot.grafico.linha.BresenhanLinhaDrawer;
import italo.iplot.grafico.texto.Texto2D;
import italo.iplot.gui.grafico.ArestaGeom;
import italo.iplot.gui.grafico.FaceGeom;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.grafico.desenho.DesenhoFace3D;
import italo.iplot.plot3d.grafico.desenho.DesenhoLinha3D;
import italo.iplot.plot3d.grafico.desenho.Pixel3D;
import italo.iplot.plot3d.grafico.geom.FaceGeom3D;
import italo.iplot.plot3d.grafico.geom.ArestaGeom3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class AlocaImagemGrafico3D extends Java2DGrafico implements Pixel3D {
    
    private final DesenhoFace3D faceDesenho = new DesenhoFace3D();
    private final DesenhoLinha3D linhaDesenho = new DesenhoLinha3D();
    
    private final BresenhanLinhaDrawer drawer = new BresenhanLinhaDrawer();
        
    private double[][] matz;
    private Objeto3D[][] objs;

    @Override
    public void graficoBufferGerado(BufferedImage imagem) {
        matz = new double[ imagem.getHeight() ][ imagem.getWidth() ];        
        objs = new Objeto3D[ imagem.getHeight() ][ imagem.getWidth() ];
    }

    @Override
    public void antesDesenharGrafico() {
        for( int i = 0; i < matz.length; i++ ) {
            for( int j = 0; j < matz[i].length; j++ ) {
                matz[ i ][ j ] = Double.NEGATIVE_INFINITY;
                objs[ i ][ j ] = null;
            }
        }
    }
    
    @Override
    public void preencheFace( FaceGeom geom ) {
        faceDesenho.preenche( (FaceGeom3D)geom, this, super.getCor() );        
    }

    @Override
    public void desenhaLinha( ArestaGeom geom ) {        
        linhaDesenho.desenha( (ArestaGeom3D)geom, this, super.getCor(), 1 );
    }

    @Override
    public void desenhaLinhaPontilhada( ArestaGeom geom, int esp ) {
        linhaDesenho.desenha( (ArestaGeom3D)geom, this, super.getCor(), esp );
    }
                    
    @Override
    public void desenhaTexto(String texto, int x, int y, double rang) {
        Font font = this.getGraphics().getFont();
        super.addTexto2D( new Texto2D( x, y, texto, font, rang ) );
    }

    @Override
    public void pintaFacePixel( FaceGeom3D geom, int x, int y, double nz, int rgb) {        
        if ( x < 0 || y < 0 || x >= pintura.getImagem().getWidth() || y >= pintura.getImagem().getHeight() )
            return;
        
        if ( nz >= matz[ y ][ x ] ) {            
            pintura.getImagem().setRGB( x, y, rgb );
            
            matz[ y ][ x ] = nz;
            objs[ y ][ x ] = geom.getFace().getObjeto();
        }
    }    
    
    @Override
    public void pintaArestaPixel( ArestaGeom3D geom, int x, int y, double nz, int rgb) {        
        if ( x < 0 || y < 0 || x >= pintura.getImagem().getWidth() || y >= pintura.getImagem().getHeight() )
            return;
        
        if ( geom.getAresta().getObjeto() == objs[ y ][ x ] || nz >= matz[ y ][ x ] ) {
            pintura.getImagem().setRGB( x, y, rgb );
            
            matz[ y ][ x ] = nz;
            objs[ y ][ x ] = geom.getAresta().getObjeto();
        }
    }
    
}
