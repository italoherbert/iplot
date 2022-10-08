package italo.iplot.planocartesiano.telaajuste;

import java.awt.Font;
import java.awt.geom.Rectangle2D;

public class PCTelaAjustador {
    
    public void ajusta( PCContainerTelaAjuste container, PCTelaAjusteTO to, boolean eh3d ) {            
        double bordaPX = container.getBordaPX();
        
        String titulo = container.getTitulo();            
        String yEixoRotulo = container.getYEixoRotulo();    
        String xzEixoRotulo = container.getXZEixoRotulo();    
                
        int tituloGDistPX = container.getTituloGraficoDistanciaPX();        
        int reguaValorDistPX = container.getReguaValorDistanciaPX();
        int eixoRotulosDistPX = container.getEixoRotulosDistanciaPX();
        
        Font tituloFont = container.getTituloFont();    
        Font eixoRotuloFont = container.getEixoRotuloFont();
        Font reguaValoresFont = container.getReguaValoresFont();
        
        boolean pintarRegua = container.isPintarRegua();
        boolean pintarEixoRotulos = container.isPintarEixoRotulos();
                
        double borda = to.verticeUnidade( bordaPX );
        
        double pclarg = container.getMaxLargura();    
        double pcalt = container.getMaxAltura();
        double pcx = container.getCentroX();
        double pcy = container.getCentroY();
        
        double reguaValorLarguraMax = 0;
        double reguaValorAlturaMax = 0;
            
        if ( eh3d ) {
            pcy += borda;
        } else {
            pcalt -= 2*borda;  
            pclarg -= 2*borda;
        }
                                
        double tituloGDist = to.verticeUnidade( tituloGDistPX );
        double reguaValorDist = to.verticeUnidade( reguaValorDistPX );
        double eixoRotuloDist = to.verticeUnidade( eixoRotulosDistPX );
                        
        if ( titulo != null ) {
            Rectangle2D ret = to.getStringLimites( titulo, tituloFont );             
            
            double tituloH = to.verticeUnidade( ret.getHeight() );
            
            pcy -= (tituloGDist + tituloH)*.5d;
            pcalt -= (tituloGDist + tituloH);            
        } else {
            pcy -= tituloGDist*.5d;
            pcalt -= tituloGDist;  
        }
        
        if ( pintarRegua ) {
            double maxRotuloLargPX = 0;
            double iMinY = container.getPCPlotObjManager().getIMinY();
            double yinc = container.getPCPlotObjManager().getIIncY();
            double ydesloc = container.getPCPlotObjManager().getIDeslocY();
            int yNumRotulos = container.getPCPlotObjManager().getYNumRotulos();        
            for( int i = 0; i < yNumRotulos; i++ ) {
                double valor = iMinY + ( i * yinc ) + ydesloc;            
                String rotulo = to.getRotuloDecimalFormat().format( valor );
                double larg = to.getStringLimites( rotulo, reguaValoresFont ).getWidth();
                if ( larg > maxRotuloLargPX )
                    maxRotuloLargPX = larg;
            }
            
            double valor = iMinY + ydesloc;            
            String rotulo = to.getRotuloDecimalFormat().format( valor );
            double maxRotuloAltPX = to.getStringLimites( rotulo, reguaValoresFont ).getHeight();

            reguaValorLarguraMax = to.verticeUnidade( maxRotuloLargPX );
            reguaValorAlturaMax = to.verticeUnidade( maxRotuloAltPX );
            
            if ( !eh3d ) {
                pcx += ( reguaValorLarguraMax + reguaValorDist ) *.5d;
                pcy += ( reguaValorAlturaMax + reguaValorDist );                        
                pclarg -= ( reguaValorAlturaMax + reguaValorDist );            
            }            
        }
        
        if ( pintarEixoRotulos ) {
            if ( yEixoRotulo != null ) {
                Rectangle2D retRY = to.getStringLimites( yEixoRotulo, eixoRotuloFont );
                double yRotuloAlt = to.verticeUnidade( retRY.getHeight() );            

                if ( !eh3d ) {
                    pcx += (eixoRotuloDist + yRotuloAlt )*.5d;
                    pclarg -= yRotuloAlt;
                }
            }

            if ( xzEixoRotulo != null ) {
                Rectangle2D retRX = to.getStringLimites( xzEixoRotulo, eixoRotuloFont );
                double xzRotuloAlt = to.verticeUnidade( retRX.getHeight() );   

                if ( !eh3d ) {
                    pcy += (eixoRotuloDist + xzRotuloAlt)*.25d;
                    pcalt -= (eixoRotuloDist + xzRotuloAlt)*.5d;
                }
            }        
        }
                                
        container.setPCX( pcx );
        container.setPCY( pcy );
        container.setPCLargura( pclarg );
        container.setPCAltura( pcalt ); 
        
        container.setReguaYValorLarguraMax( reguaValorLarguraMax );
        container.setReguaYValorAlturaMax( reguaValorAlturaMax ); 
    }
    
    public double[] calculaPosTitulo( String titulo, PCContainerTelaAjuste container, PCTelaAjusteTO to, boolean eh3d ) {
        Rectangle2D ret = to.getStringLimites( titulo, container.getTituloFont() ); 

        int tituloGDistPX = container.getTituloGraficoDistanciaPX();        
        double tituloGDist = to.verticeUnidade( tituloGDistPX );
        double pcx = container.getPCX();
        double pcy = container.getPCY();
        double pcalt = container.getPCAltura();

        double x = ( eh3d ? 0 : pcx );
        double y = pcy;
        double tituloW = to.verticeUnidade( ret.getWidth() );
        return new double[] {
            x - tituloW*.5d,
            y + pcalt*.5d + tituloGDist
        };        
    }
    
    public double[] calculaPosEixoY2D( String yEixoRotulo, PCContainerTelaAjuste container, PCTelaAjusteTO to ) {
        int reguaRotuloDistPX = container.getReguaValorDistanciaPX();
        
        double reguaRotuloDistancia = to.verticeUnidade( reguaRotuloDistPX );
        
        double pcx = container.getPCX();
        double pcy = container.getPCY();
        double pclarg = container.getPCLargura();  
        
        double reguaValorLarguraMax = container.getReguaYValorLarguraMax();
        
        Rectangle2D ret = to.getStringLimites( yEixoRotulo, container.getEixoRotuloFont() ); 
        double yEixoRotuloLarg = to.verticeUnidade( ret.getWidth() );
        double yEixoRotuloAlt = to.verticeUnidade( ret.getHeight() );
                
        return new double[] {
            pcx - pclarg*.5d - reguaRotuloDistancia - reguaValorLarguraMax - yEixoRotuloAlt*.5d,
            pcy - yEixoRotuloLarg*.5d
        };
    }
    
    public double[] calculaPosEixoX2D( String xEixoRotulo, PCContainerTelaAjuste container, PCTelaAjusteTO to ) {
        int reguaRotuloDistPX = container.getReguaValorDistanciaPX();
        
        double reguaRotuloDistancia = to.verticeUnidade( reguaRotuloDistPX );
        
        double pcx = container.getPCX();
        double pcy = container.getPCY();
        double pcalt = container.getPCAltura();

        double reguaValorAlturaMax = container.getReguaYValorAlturaMax();

        Rectangle2D ret = to.getStringLimites( xEixoRotulo, container.getEixoRotuloFont() ); 
        double xEixoRotuloLarg = to.verticeUnidade( ret.getWidth() );
        double xEixoRotuloAlt = to.verticeUnidade( ret.getHeight() );
                
        return new double[] {
            pcx - xEixoRotuloLarg *.5d,
            pcy - pcalt*.5d - reguaRotuloDistancia - reguaValorAlturaMax - xEixoRotuloAlt *.5d
        };
    }
    
}
