package italo.iplot.plot2d.grafico;

import italo.iplot.grafico.texto.Texto2D;
import italo.iplot.grafico.Java2DGrafico;
import italo.iplot.gui.grafico.ArestaGeom;
import italo.iplot.gui.grafico.FaceGeom;
import java.awt.image.BufferedImage;

public class Java2DGrafico2D extends Java2DGrafico {

    @Override
    public void graficoBufferGerado( BufferedImage imagem ) {}

    @Override
    public void antesDesenharGrafico() {}
       
    @Override
    public void preencheFace( FaceGeom geom ) {
        super.java2DPreencheFace( geom );
    }

    @Override
    public void desenhaLinhaPontilhada( ArestaGeom geom, int esp ) {
        super.java2DDesenhaLinhaPontilhada( geom, esp );
    }

    @Override
    public void desenhaLinha( ArestaGeom geom ) {
        super.java2DDesenhaLinha( geom );
    }

    @Override
    public void desenhaTexto(String texto, int x, int y, double rotAng) {
        super.addTexto2D( new Texto2D( x, y, texto, this.getGraphics().getFont(), rotAng ) );
    }
                  
}
