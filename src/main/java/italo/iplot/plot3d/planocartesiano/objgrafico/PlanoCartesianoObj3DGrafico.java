package italo.iplot.plot3d.planocartesiano.objgrafico;

import italo.iplot.gui.grafico.Grafico;
import italo.iplot.planocartesiano.Legenda;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DGrafico;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import italo.iplot.plot3d.g3d.Objeto3DGraficoDriver;

public class PlanoCartesianoObj3DGrafico implements Objeto3DGrafico {

    @Override
    public void desenho( Grafico grafico, Objeto3D obj, Objeto3DGraficoDriver drv ) {
        PlanoCartesianoObjeto3D planoObj = (PlanoCartesianoObjeto3D)obj;
        
        if ( planoObj.getTitulo() != null ) {
            String titulo = planoObj.getTitulo();
            double[] v = {
                planoObj.getTituloX(),
                planoObj.getTituloY(),
                0
            };
            int[] txy = drv.getMath3D().pontoPX( v, drv.getTela() );

            grafico.getGraphics().setFont( planoObj.getTituloFont() ); 
            grafico.setCor( planoObj.getTituloCor() );
            grafico.desenhaTexto( titulo, txy[0], txy[1] );
        }  
        
        double pcx = planoObj.getPCX();
        double pcy = planoObj.getPCY();
        double pdx = planoObj.getPCLargura();
        double pdy = planoObj.getPCAltura();
        
        double lbordaVertical = drv.getMath3D().verticeUnidade( planoObj.getLegendaBordaVerticalPX(), drv.getTela() );
        double lbordaLateral = drv.getMath3D().verticeUnidade( planoObj.getLegendaBordaLateralPX(), drv.getTela() );
        double lesp = drv.getMath3D().verticeUnidade( planoObj.getLegendaEspPX(), drv.getTela() );
        double ltracoComprimento = drv.getMath3D().verticeUnidade( planoObj.getLegendaTracoComprimentoPX(), drv.getTela() );
        double lpontoRaio = drv.getMath3D().verticeUnidade( planoObj.getLegendaPontoRaioPX(), drv.getTela() );
        double glborda = drv.getMath3D().verticeUnidade( planoObj.getGrupoLegendaBordaPX(), drv.getTela() );
        double glespy = drv.getMath3D().verticeUnidade( planoObj.getGrupoLegendaYEspPX(), drv.getTela() );
        
        int lpontoRaioPX = planoObj.getLegendaPontoRaioPX();
        
        List<Legenda> legendas = planoObj.getLegendas();
        double maxLegendaLarg = 0;
        for( Legenda legenda : legendas ) {
            Rectangle2D ret = grafico.stringLimites( legenda.getLegenda(), planoObj.getLegendaFont() );
            double w = drv.getMath3D().verticeUnidade( ret.getWidth(), drv.getTela() );
            double c = ( legenda.getTipo() == Legenda.LINHA ? ltracoComprimento : 2*lpontoRaio );
            double larg = 2*lbordaLateral + c + lesp + w;
            if ( larg > maxLegendaLarg )
                maxLegendaLarg = larg;
        }
        
        double x = pcx + pdx*.5d - glborda - maxLegendaLarg;
        double y = pcy + pdy*.5d - glborda;
                
        grafico.getGraphics().setFont( planoObj.getLegendaFont() );
        for( Legenda legenda : legendas ) {            
            Rectangle2D ret = grafico.stringLimites( legenda.getLegenda(), planoObj.getLegendaFont() );
            double h = drv.getMath3D().verticeUnidade( ret.getHeight(), drv.getTela() );
            double alt = 2*lbordaVertical + h;
            
            int[] xy = drv.getMath3D().pontoPX( new double[] { x, y, 0 }, drv.getTela() );
            int largPX = (int)drv.getMath3D().telaUnidade( maxLegendaLarg, drv.getTela() );
            int altPX = (int)drv.getMath3D().telaUnidade( alt, drv.getTela() );
            
            grafico.setCor( planoObj.getCor() );
            grafico.preencheRetangulo( xy[0], xy[1], largPX, altPX );
            
            grafico.setCor( legenda.getCor() );            
                        
            double x1 = x + lbordaLateral;
            double y1 = y - lbordaVertical - h*.5d;
            int[] xy1 = drv.getMath3D().pontoPX( new double[] { x1, y1, 0 }, drv.getTela() );
            if ( legenda.getTipo() == Legenda.LINHA ) {
                double x2 = x1 + ltracoComprimento;
                double y2 = y1;
                int[] xy2 = drv.getMath3D().pontoPX( new double[] { x2, y2, 0 }, drv.getTela() );
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
            xy1 = drv.getMath3D().pontoPX( new double[] { x1, y1, 0 }, drv.getTela() );

            grafico.setCor( planoObj.getLegendaCor() ); 
            grafico.getGraphics().setFont( planoObj.getLegendaFont() );
            grafico.desenhaTexto( legenda.getLegenda(), xy1[0], xy1[1] );
                        
            grafico.setCor( planoObj.getLegendaCor() );
            grafico.desenhaRetangulo( xy[0], xy[1], largPX, altPX );
            
            y -= 2*lbordaVertical + h + glespy;                         
        }
        
        /*
        int x = drv.getTela().getTelaLargura()/2 + (int)drv.getMath3D().telaUnidade( planoObj.getPCX(), drv.getTela() );
        int y = drv.getTela().getTelaAltura()/2 - (int)drv.getMath3D().telaUnidade( planoObj.getPCY(), drv.getTela() );
        int w = (int)drv.getMath3D().telaUnidade( planoObj.getPCAltura(), drv.getTela() );
        int h = (int)drv.getMath3D().telaUnidade( planoObj.getPCAltura(), drv.getTela() );

        grafico.desenhaRetangulo( x - w / 2, y - h / 2, w, h );        
        */
    }
    
}
