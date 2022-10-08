package italo.iplot.gui.plot.icone;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class LupaIcone extends Icone {

    protected boolean pintarCirculoCentral = false;
    protected BasicStroke caboStroke = new BasicStroke( 2.0f );
    
    public LupaIcone( int dim ) {
        super( dim );
    }

    protected abstract void pintaInterLupaIcone( Graphics2D g2D, int cx, int cy, int r );
    
    @Override
    protected void pintaIcone( Graphics2D g2D, int x, int y ) {
        int lw = (int)super.stroke.getLineWidth();
        
        int lupaDim = ( ( dim - 2*esp ) * 3 / 4 );
        int lupaX = x + esp;
        int lupaY = y + esp; 
        int lupaR = lupaDim / 2;
        int lupaXC = lupaX + lupaR;
        int lupaYC = lupaY + lupaR;
        
        double ang = Math.PI / 4.0d;
        
        int r2 = (int)Math.sqrt( Math.pow( x+dim-esp-lupaXC, 2 ) + Math.pow( y+dim-esp-lupaYC, 2 ) );
        
        int linhaX1 = lupaXC + (int)Math.ceil( lupaR * Math.cos( ang ) );
        int linhaY1 = lupaYC + (int)Math.ceil( lupaR * Math.sin( ang ) );
        int linhaX2 = lupaXC + (int)( r2 * Math.cos( ang ) );
        int linhaY2 = lupaYC + (int)( r2 * Math.sin( ang ) );                
        
        if ( pintarCirculoCentral ) {
            g2D.setColor( cor );        
            g2D.fillArc( lupaX, lupaY, 2*lupaR, 2*lupaR, 0, 360 );
        }
        
        g2D.setColor( linhaCor ); 
        g2D.drawArc( lupaX, lupaY, 2*lupaR, 2*lupaR, 0, 360 );
        
        g2D.setStroke( caboStroke ); 
        g2D.drawLine( linhaX1, linhaY1, linhaX2, linhaY2 );
        
        this.pintaInterLupaIcone( g2D, lupaXC, lupaYC, lupaR );
    }

    public boolean isPintarCirculoCentral() {
        return pintarCirculoCentral;
    }

    public void setPintarCirculoCentral(boolean pintarCirculoCentral) {
        this.pintarCirculoCentral = pintarCirculoCentral;
    }
    
}
