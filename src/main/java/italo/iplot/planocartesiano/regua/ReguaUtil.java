package italo.iplot.planocartesiano.regua;

import java.text.DecimalFormat;

public class ReguaUtil {                                   
        
    public final static int NUM_ROTULOS_PADRAO = 5;

    private int maxPrecisao = 6;
    private int maxCasasDecimaisParaDiv = 2;
    
    public double[] calculaIntervalo( double min, double max, int numRotulos ) {
        double dif = Math.abs( max - min );
                                        
        double div = (numRotulos-1);
        
        double inc;
        double dist;
        double resto;        
                
        int e = (int)Math.log10( dif );        
        e--;            
        if ( dif < 1 )
            e--;
                
        double min2 = min;
        double max2 = max;
                
        double ePot10 = Math.pow( 10, e );
        double absEPot10 = Math.pow( 10, Math.abs( e ) );                       
                        
        double divMin = min / ePot10;
        if ( divMin != (int)divMin ) {            
            if ( min < 0 ) {
                double redondo = ((int)( ( min - ePot10 ) * absEPot10 )) / absEPot10;
                if ( redondo == (int)redondo ) {
                    min2 = redondo;
                } else {
                    min2 = Math.ceil( ( min - Math.pow( 10, e ) ) * absEPot10 ) / absEPot10 ;
                }
            } else {
                double redondo = ((int)( min * absEPot10)) / absEPot10;
                if ( redondo == (int)redondo ) {
                    min2 = redondo;
                } else {                    
                    min2 = Math.floor( ( min - Math.pow( 10, e ) ) * absEPot10 ) / absEPot10 ;
                }
            }            
        }
        
        double divMax = max / Math.pow( 10, e );
        if ( divMax != (int)divMax ) {            
            if ( max < 0 ) {
                double redondo = ((int)( max * absEPot10 )) / absEPot10;
                if ( redondo == (int)redondo ) {
                    max2 = redondo;
                } else {
                    max2 = Math.ceil( ( max + Math.pow( 10, e ) ) * absEPot10 ) / absEPot10 ;
                }
            } else {
                double redondo = (int)( (max + ePot10) * absEPot10) / absEPot10;
                if ( redondo == (int)redondo ) {
                    max2 = redondo;
                } else {
                    max2 = Math.floor( ( max + Math.pow( 10, e ) ) * absEPot10 ) / absEPot10 ;
                }
            }            
        }
        dif = Math.abs( max2 - min2 );        
        double d = dif / div; 
        int maxCasasDecimais = maxCasasDecimaisParaDiv;
        if ( e < 0 )
            maxCasasDecimais -= e;
        int difNumCasasDecimais = this.contaCasasDecimais( d ); 
        if ( difNumCasasDecimais <= maxCasasDecimais ) {                    
            dist = dif;
            resto = 0;
            inc = dif / div;
        } else {                                                                                                 
            double pot10 = Math.pow( 10, e );                                      
            double aux = dif + (div * pot10);                        
            dist = aux - ( aux % (div * pot10) );
                        
            resto = dist - dif;
            if ( resto == 0 ) {
                inc = dif / div;
            } else {
                inc = dist / div;
            }
        }          
        
        
        if ( resto != 0 ) { 
            int e2 = (int)Math.log10( max2 );
            int e3 = (int)Math.log10( resto );            
        
            int e4 = Math.abs( e3 );
            if ( e2 > 0 && e4 > 0 )
                e4--;
                                    
            double e3Pot10 = Math.pow( 10, e3 );
            double e4Pot10 = Math.pow( 10, e4 );
            
            if ( e4Pot10 <= resto )            
                max2 = ((int)((max2 + e3Pot10) * e4Pot10)) / e4Pot10;                                        
            min2 = max2-dist;        
        }
                            
        return new double[] { min2, max2, inc };
    }
           
    public int contaCasasDecimais( double n ) {
        double aux = n;
        int e = 0;
        if ( aux != (int)aux ) {            
            do {
                aux *= 10;
                e++;
            } while( aux != (int)aux && e <= maxPrecisao );
        }
        return e;
    }
    
    public double calculaBorda( int numRotulos, double inc, double n1, double n2 ) {                       
        return Math.abs( n2-n1 ) - ( (numRotulos-1) * inc );        
    }    
    
    public double calculaPlanoCartesianoH( double inc, double borda, int i ) {                                                      
        return borda*.5d + ( i * inc );                              
    }
     
    public double calculaH( double dn, double n1, double n2, double planoCartesianoH ) {
        return - ( dn * .5d ) + ( dn * planoCartesianoH / Math.abs( n2-n1 ) );
    }
    
    public int getMaxPrecisao() {
        return maxPrecisao;
    }

    public void setMaxPrecisao(int maxPrecisao) {
        this.maxPrecisao = maxPrecisao;
    }

    public int getMaxCasasDecimaisParaDiv() {
        return maxCasasDecimaisParaDiv;
    }

    public void setMaxCasasDecimaisParaDiv(int maxCasasDecimaisParaDiv) {
        this.maxCasasDecimaisParaDiv = maxCasasDecimaisParaDiv;
    }
        
    public static void main( String[] args ) {                
        double[][] intervalos = {                        
            {-1.003416, 0.996584},            
            {-1.991099, 0.00122},
            {-2.003416, -0.996584},
            {-1.991099, -0.00122},
            {0.003416, 1.996584},
            {0.991099, 2.00122},             
            { -0.05, 0.05 },
            { -0.2, 0.5 },             
            {0.04, 1.04},                       
            {-0.5, -0.18 },
            {-0.112, -0.1},
            {0.04, 0.523},                        
            {0.05, 1},            
            {0.04, 50.05},           
            {1000, 15080.4},
            {990, 1500},
            {-Math.PI, Math.PI }                          
        };
        
        ReguaUtil rutil = new ReguaUtil();
        
        for( double[] inter : intervalos ) {
            double min = inter[0];
            double max = inter[1];
            double[] nvs = rutil.calculaIntervalo( min, max, NUM_ROTULOS_PADRAO );
                
            System.out.println( "("+min+", "+max+"):  "+nvs[0]+"  "+nvs[1]+"  "+nvs[2] );
            for( int i = 0; i < NUM_ROTULOS_PADRAO; i++ ) {
                String valor = new DecimalFormat( "#.####" ).format( nvs[0] + (nvs[2]*i)  );
                System.out.println( "\t"+valor );
            }
        }
    }       
        
}
