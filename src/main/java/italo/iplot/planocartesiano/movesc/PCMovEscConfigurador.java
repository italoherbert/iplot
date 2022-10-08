package italo.iplot.planocartesiano.movesc;

public class PCMovEscConfigurador {
    
    public void move( PCContainerMovEsc movesc, double des ) {
        double min = movesc.getMin();
        double max = movesc.getMax();
        double in1 = movesc.getIN1();
        double in2 = movesc.getIN2();
        double idesloc = movesc.getIDesloc();
        double iinc = movesc.getIInc();
        
        double idn = Math.abs( in2 - in1 );
        double dn = Math.abs( max - min );       
        
        min -= des;
        max = min + dn;
                
        in1 -= des;
        in2 = in1 + idn;

        idesloc += des;                                
        
        if ( idesloc < 0 || idesloc > iinc  )
            idesloc %= iinc;  
        
        movesc.setMin( min );
        movesc.setMax( max );
        movesc.setIN1( in1 );
        movesc.setIN2( in2 );
        movesc.setIInc( iinc );
        movesc.setIDesloc( idesloc ); 
    }
    
    public void escalar( PCContainerMovEsc movesc, double escala ) {
        double min = movesc.getMin();
        double max = movesc.getMax();
        double in1 = movesc.getIN1();
        double in2 = movesc.getIN2();
        double idesloc = movesc.getIDesloc();
        double iinc = movesc.getIInc();
        double ifator = movesc.getIFator();                                
                                
        if ( escala <= 0 ) {
            idesloc = iinc = in1 = in2 = min = max = ifator = 0;
        } else {            
            double idn = Math.abs( in2 - in1 );        
            double dn = Math.abs( max - min );
            
            double im = in1 + idesloc + ( idn*.5d );
            double m = min + ( dn*.5d );  
                        
            idesloc *= escala;
            iinc *= escala;  

            in1 = im - ( idn * escala )*.5d - idesloc;
            in2 = im + ( idn * escala )*.5d - idesloc;                        
            
            min = m - ( dn * escala )*.5d;
            max = m + ( dn * escala )*.5d;
            
            ifator = Math.abs( max - min ) / Math.abs( in2 - in1 );
        }
        movesc.setMin( min );
        movesc.setMax( max );
        movesc.setIN1( in1 );
        movesc.setIN2( in2 );
        movesc.setIInc( iinc );
        movesc.setIDesloc( idesloc );
        movesc.setIFator( ifator );
    }
    
}
