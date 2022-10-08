package italo.iplot.gui.plot.icone;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

public abstract class Icone implements Icon {
    
    protected int dim;
    protected int esp = 0;
    protected int arredondarCantoCom = 10;
    
    protected boolean desativarAntiAliasing = false;
    
    protected Color cor = Color.BLACK;
    protected Color linhaCor = Color.BLACK;
    protected BasicStroke stroke = new BasicStroke( 3 ); 
    
    protected double espDIMFator = 0;
    protected double arredondarCantoComDIMFator = 0.54d;
    
    protected boolean pintarBorda = false;
    protected Color bordaCor = Color.BLACK;
    protected BasicStroke bordaStroke = new BasicStroke( 2 );

    protected Color bgCor = null;
    
    public Icone( int dim ) {
        this.dim = dim;
    }

    protected abstract void pintaIcone( Graphics2D g2D, int x, int y );

    @Override
    public void paintIcon( Component c, Graphics g, int x, int y ) {
        Graphics2D g2D = (Graphics2D)g.create();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, ( desativarAntiAliasing ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON ) ); 
                
        esp = (int)Math.round( dim * espDIMFator );
        arredondarCantoCom = (int)Math.round( dim * arredondarCantoComDIMFator );
        
        int rx = 0;
        int ry = 0;
        int rw = dim + 2*x;
        int rh = dim + 2*y;
        
        if ( bgCor != null ) {
            g2D.setColor( bgCor );             
            g2D.fill( new RoundRectangle2D.Float( rx, ry, rw, rh, arredondarCantoCom, arredondarCantoCom ) );
        }
        
        if ( pintarBorda ) {
            float lw = bordaStroke.getLineWidth();
            g2D.setColor( bordaCor );
            g2D.setStroke( bordaStroke );
            g2D.draw( new RoundRectangle2D.Float( rx+lw*.5f, ry+lw*.5f, rw-lw, rh-lw, arredondarCantoCom, arredondarCantoCom ) ); 
        }
    
        
        g2D.setStroke( stroke );        
        g2D.setColor( cor );
        this.pintaIcone( g2D, x, y );
        
        g2D.dispose();
    }
    
    @Override
    public int getIconWidth() {
        return dim;
    }

    @Override
    public int getIconHeight() {
        return dim;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public BasicStroke getStroke() {
        return stroke;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }

    public double getEspDIMFator() {
        return espDIMFator;
    }

    public void setEspDIMFator(double espDIMFator) {
        this.espDIMFator = espDIMFator;
    }

    public double getArredondarCantoComDIMFator() {
        return arredondarCantoComDIMFator;
    }

    public void setArredondarCantoComDIMFator(int arredondarCantoComDIMFator) {
        this.arredondarCantoComDIMFator = arredondarCantoComDIMFator;
    }

    public boolean isPintarBorda() {
        return pintarBorda;
    }

    public void setPintarBorda(boolean pintarBorda) {
        this.pintarBorda = pintarBorda;
    }

    public Color getBordaCor() {
        return bordaCor;
    }

    public void setBordaCor(Color bordaCor) {
        this.bordaCor = bordaCor;
    }

    public BasicStroke getBordaStroke() {
        return bordaStroke;
    }

    public void setBordaStroke(BasicStroke bordaStroke) {
        this.bordaStroke = bordaStroke;
    }

    public Color getBGCor() {
        return bgCor;
    }

    public void setBGCor(Color bgCor) {
        this.bgCor = bgCor;
    }

    public Color getLinhaCor() {
        return linhaCor;
    }

    public void setLinhaCor(Color linhaCor) {
        this.linhaCor = linhaCor;
    }

    public boolean isDesativarAntiAliasing() {
        return desativarAntiAliasing;
    }

    public void setDesativarAntiAliasing(boolean desativarAntiAliasing) {
        this.desativarAntiAliasing = desativarAntiAliasing;
    }
    
}
