package italo.iplot.plot2d.planocartesiano.objgrafico;

import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DGrafico;
import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import italo.iplot.plot2d.planocartesiano.g2d.Traco2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import italo.iplot.plot2d.g2d.Objeto2DGraficoDriver;

public class PlanoCartesianoObj2DGrafico implements Objeto2DGrafico {
    
    @Override
    public void desenho( Grafico grafico, Objeto2D obj, Objeto2DGraficoDriver drv ) {
        PlanoCartesianoObjeto2D planoObj = (PlanoCartesianoObjeto2D)obj;
                
        if ( planoObj.getTitulo() != null ) {
            String titulo = planoObj.getTitulo();
            double x = planoObj.getTituloX();
            double y = planoObj.getTituloY();
            int[] txy = drv.getMath2D().pontoPX( x, y, drv.getTela() );

            grafico.getGraphics().setFont( planoObj.getTituloFont() ); 
            grafico.setCor( planoObj.getTituloCor() );
            grafico.desenhaTexto( titulo, txy[0], txy[1] );
        }
                
        if ( planoObj.isPintarEixoRotulos() ) {
            grafico.setCor( planoObj.getEixoRotulosCor() );
            grafico.getGraphics().setFont( planoObj.getEixoRotuloFont() );
            
            String yRotulo = planoObj.getYEixoRotulo();
            if ( yRotulo != null ) {
                double x = planoObj.getXEixoRotuloY();
                double y = planoObj.getYEixoRotuloY();
                int[] txy = drv.getMath2D().pontoPX( x, y, drv.getTela() );                
                grafico.desenhaTexto( yRotulo, txy[0], txy[1], -Math.PI*.5d );
            }

            String xRotulo = planoObj.getXEixoRotulo();
            if ( xRotulo != null ) {
                double x = planoObj.getXEixoRotuloX();
                double y = planoObj.getYEixoRotuloX();
                int[] txy = drv.getMath2D().pontoPX( x, y, drv.getTela() );
                grafico.desenhaTexto( xRotulo, txy[0], txy[1] );
            }
        }
        
        if ( planoObj.isPintarRegua() ) {
            List<Traco2D> tracos = planoObj.getTracos();

            grafico.getGraphics().setFont( planoObj.getReguaValoresFont() );        
            grafico.setCor( planoObj.getRotulosCor() ); 
            for( Traco2D traco : tracos ) {
                if ( traco.getTipo() == Traco2D.CIMA || traco.getTipo() == Traco2D.FRENTE )
                    continue;

                double vx = traco.getX();
                double vy = traco.getY();

                int[] xy = drv.getMath2D().pontoPX( vx, vy, drv.getTela() );
                int x = xy[0];
                int y = xy[1];                                  

                String rotulo = drv.getRotuloDecimalFormat().format( traco.getValor() );
                Rectangle2D rotuloRT = grafico.stringLimites( rotulo, planoObj.getReguaValoresFont() );

                switch( traco.getTipo() )  {
                    case Traco2D.TRAZ:
                        x -= ( rotuloRT.getMaxX() - rotuloRT.getMinX() );
                        y += ( rotuloRT.getMaxY() - rotuloRT.getMinY() ) / 4;
                        break;                
                    case Traco2D.BAIXO:
                        y += ( rotuloRT.getMaxY() - rotuloRT.getMinY() ) / 2;
                        x -= ( rotuloRT.getMaxX() - rotuloRT.getMinX() ) / 2;
                        break;
                }            

                grafico.desenhaTexto( rotulo, x, y );
            }
        }                       
        
        double cx = planoObj.getX();
        double cy = planoObj.getY();
        double dx = planoObj.getDX();
        double dy = planoObj.getDY();
        double lbordaVertical = drv.getMath2D().verticeUnidade( planoObj.getLegendaBordaVerticalPX(), drv.getTela() );
        double lbordaLateral = drv.getMath2D().verticeUnidade( planoObj.getLegendaBordaLateralPX(), drv.getTela() );
        double lesp = drv.getMath2D().verticeUnidade( planoObj.getLegendaEspPX(), drv.getTela() );
        double ltracoComprimento = drv.getMath2D().verticeUnidade( planoObj.getLegendaTracoComprimentoPX(), drv.getTela() );
        double lpontoRaio = drv.getMath2D().verticeUnidade( planoObj.getLegendaPontoRaioPX(), drv.getTela() );
        double glborda = drv.getMath2D().verticeUnidade( planoObj.getGrupoLegendaBordaPX(), drv.getTela() );
        double glespy = drv.getMath2D().verticeUnidade( planoObj.getGrupoLegendaYEspPX(), drv.getTela() );
        
        int lpontoRaioPX = planoObj.getLegendaPontoRaioPX();

        List<Legenda> legendas = planoObj.getLegendas();
        double maxLegendaLarg = 0;
        for( Legenda legenda : legendas ) {
            Rectangle2D ret = grafico.stringLimites( legenda.getLegenda(), planoObj.getLegendaFont() );
            double w = drv.getMath2D().verticeUnidade( ret.getWidth(), drv.getTela() );
            double c = ( legenda.getTipo() == Legenda.LINHA ? ltracoComprimento : 2*lpontoRaio );
            double larg = 2*lbordaLateral + c + lesp + w;
            if ( larg > maxLegendaLarg )
                maxLegendaLarg = larg;
        }
        
        double x = cx + dx*.5d - glborda - maxLegendaLarg;
        double y = cy + dy*.5d - glborda;
                
        grafico.getGraphics().setFont( planoObj.getLegendaFont() );
        for( Legenda legenda : legendas ) {            
            Rectangle2D ret = grafico.stringLimites( legenda.getLegenda(), planoObj.getLegendaFont() );
            double h = drv.getMath2D().verticeUnidade( ret.getHeight(), drv.getTela() );
            double alt = 2*lbordaVertical + h;
            
            int[] xy = drv.getMath2D().pontoPX( x, y, drv.getTela() );
            int largPX = (int)drv.getMath2D().telaUnidade( maxLegendaLarg, drv.getTela() );
            int altPX = (int)drv.getMath2D().telaUnidade( alt, drv.getTela() );
            
            grafico.setCor( planoObj.getCor() );
            grafico.preencheRetangulo( xy[0], xy[1], largPX, altPX );
            
            grafico.setCor( legenda.getCor() );
                        
            double x1 = x + lbordaLateral;
            double y1 = y - lbordaVertical - h*.5d;
            int[] xy1 = drv.getMath2D().pontoPX( x1, y1, drv.getTela() );
            if ( legenda.getTipo() == Legenda.LINHA ) {
                double x2 = x1 + ltracoComprimento;
                double y2 = y1;
                int[] xy2 = drv.getMath2D().pontoPX( x2, y2, drv.getTela() );
                grafico.desenhaLinha( xy1[0], xy1[1], xy2[0], xy2[1] );
                
                x1 = x2;
            } else {
                grafico.preencheCirculo( xy1[0]+lpontoRaioPX, xy1[1], lpontoRaioPX );
                
                x1 += 2*lpontoRaio;
                if ( 2*lpontoRaio > h )
                    h = 2*lpontoRaio;
            }
            
            x1 += lesp;
            y1 -= h*.25d;
            xy1 = drv.getMath2D().pontoPX( x1, y1, drv.getTela() );

            grafico.setCor( planoObj.getLegendaCor() ); 
            grafico.getGraphics().setFont( planoObj.getLegendaFont() );
            grafico.desenhaTexto( legenda.getLegenda(), xy1[0], xy1[1] );
                        
            grafico.setCor( planoObj.getLegendaCor() );
            grafico.desenhaRetangulo( xy[0], xy[1], largPX, altPX );
            
            y -= 2*lbordaVertical + h + glespy;                         
        }
        
        if ( planoObj.isPintarMouseLinhas() ) {
            double left = planoObj.getX() - planoObj.getDX()*.5d;        
            double top = planoObj.getY() - planoObj.getDY()*.5d;
            double right = planoObj.getX() + planoObj.getDX()*.5d;        
            double bottom = planoObj.getY() + planoObj.getDY()*.5d;

            int[] p1 = drv.getMath2D().pontoPX( left, top, drv.getTela() );
            int[] p2 = drv.getMath2D().pontoPX( right, bottom, drv.getTela() );

            int mouseX = drv.getMouseX();
            int mouseY = drv.getMouseY();
            
            if ( mouseX >= p1[0] && mouseX <= p2[0] && mouseY >= p2[1] && mouseY <= p1[1] ) {
                grafico.setCor( planoObj.getMouseLinhasCor() ); 
                grafico.desenhaLinha( p1[0], mouseY, p2[0], mouseY );
                grafico.desenhaLinha( mouseX, p1[1], mouseX, p2[1] );                        
            }                                    
        }
    }
    
}
