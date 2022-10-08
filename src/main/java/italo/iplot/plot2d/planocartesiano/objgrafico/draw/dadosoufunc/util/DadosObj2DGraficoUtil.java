package italo.iplot.plot2d.planocartesiano.objgrafico.draw.dadosoufunc.util;

import italo.iplot.gui.Tela;
import italo.iplot.plot2d.g2d.util.Math2D;

public class DadosObj2DGraficoUtil {
    
    public DadosObj2DGraficoLinha calculaP0P1( double antPCX, double antPCY, double pcX, double pcY, double pcX1, double pcY1, double pcX2, double pcY2, Math2D math, Tela tela ) {
        double[] linhaP0 = null;
        double[] linhaP1 = null;
        boolean pcForaDaFaixa = false;
        
        boolean x0Setado = false;
        boolean y0Setado = false;
        boolean x1Setado = false;
        
        if ( ( antPCX < pcX1 && pcX >= pcX1 ) || ( pcX < pcX1 && antPCX > pcX1 ) ) {
            double pcIY = math.calcRetaY( antPCX, antPCY, pcX, pcY, pcX1 );
            if ( pcIY >= pcY1 && pcIY <= pcY2 ) {
                if ( antPCX < pcX1 && pcX >= pcX1 ) {                
                    linhaP0 = math.doublePontoPX( pcX1, pcIY, tela );                    
                    linhaP1 = math.doublePontoPX( pcX, pcY, tela );                
                } else {
                    linhaP0 = math.doublePontoPX( pcX1, pcIY, tela );                    
                    linhaP1 = math.doublePontoPX( antPCX, antPCY, tela );
                    pcForaDaFaixa = true;
                }
                x0Setado = true;
            }
        }

        if ( ( antPCX <= pcX2 && pcX > pcX2 ) || ( pcX <= pcX2 && antPCX > pcX2 ) ) {
            double pcIY = math.calcRetaY( antPCX, antPCY, pcX, pcY, pcX2 );
            if ( pcIY >= pcY1 && pcIY <= pcY2 ) {
                if ( antPCX <= pcX2 && pcX > pcX2 ) {
                    if ( !x0Setado )
                        linhaP0 = math.doublePontoPX( antPCX, antPCY, tela );                    
                    linhaP1 = math.doublePontoPX( pcX2, pcIY, tela );
                    pcForaDaFaixa = true;
                } else {
                    if ( !x0Setado )
                        linhaP0 = math.doublePontoPX( pcX, pcY, tela );                    
                    linhaP1 = math.doublePontoPX( pcX2, pcIY, tela );
                }
                x1Setado = true;
            }
        }

        if ( ( antPCY < pcY1 && pcY >= pcY1 ) || ( pcY < pcY1 && antPCY >= pcY1 ) ) {
            double pcIX = math.calcRetaX( antPCX, antPCY, pcX, pcY, pcY1 );
            if ( pcIX >= pcX1 && pcIX <= pcX2 ) {
                if ( x0Setado ) {
                    linhaP1 = math.doublePontoPX( pcIX, pcY1, tela );
                    y0Setado = true;                
                    pcForaDaFaixa = true;
                } else if ( x1Setado ) {
                    linhaP0 = math.doublePontoPX( pcIX, pcY1, tela ); 
                    y0Setado = true;                                       
                    pcForaDaFaixa = true;
                } else {
                    if ( antPCY < pcY1 && pcY >= pcY1 ) {                    
                        linhaP0 = math.doublePontoPX( pcX, pcY, tela );
                        linhaP1 = math.doublePontoPX( pcIX, pcY1, tela );
                    } else {
                        linhaP0 = math.doublePontoPX( antPCX, antPCY, tela );
                        linhaP1 = math.doublePontoPX( pcIX, pcY1, tela );
                        pcForaDaFaixa = true;
                    }
                    y0Setado = true;                
                }                                    
            }
        } 

        if ( ( antPCY <= pcY2 && pcY > pcY2 ) || ( pcY <= pcY2 && antPCY > pcY2 ) ) {
            double pcIX = math.calcRetaX( antPCX, antPCY, pcX, pcY, pcY2 );
            if ( pcIX >= pcX1 && pcIX <= pcX2 ) {
                if ( x0Setado ) {
                    linhaP1 = math.doublePontoPX( pcIX, pcY2, tela );                    
                    pcForaDaFaixa = true;                    
                } else if ( x1Setado ) {
                    linhaP0 = math.doublePontoPX( pcIX, pcY2, tela );                                            
                    pcForaDaFaixa = true;                    
                } else if ( y0Setado ) {
                    linhaP1 = math.doublePontoPX( pcIX, pcY2, tela );                                            
                    pcForaDaFaixa = true;                    
                } else {
                    if ( antPCY <= pcY2 && pcY > pcY2 ) {
                        linhaP0 = math.doublePontoPX( pcIX, pcY2, tela );                    
                        linhaP1 = math.doublePontoPX( antPCX, antPCY, tela );                    
                        pcForaDaFaixa = true;
                    } else {
                        linhaP0 = math.doublePontoPX( pcIX, pcY2, tela );
                        linhaP1 = math.doublePontoPX( pcX, pcY, tela );                    
                    }
                }
            }
        }
        
        return new DadosObj2DGraficoLinha( linhaP0, linhaP1, pcForaDaFaixa );
    }
    
    public double calcRetaX( double x1, double y1, double x2, double y2, double y ) {
        if ( x2 == x1 || y2 == y1 )
            return x1;
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return ( y - y1  + ( cang * x1 ) ) / cang;
    }
    
    public double calcRetaY( double x1, double y1, double x2, double y2, double x ) {
        if ( x2 == x1 )
            return y1;        
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return cang * ( x - x1 ) + y1;
    } 
    
}
