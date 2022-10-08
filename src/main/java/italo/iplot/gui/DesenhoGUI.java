package italo.iplot.gui;

import italo.iplot.gui.grafico.Grafico;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DesenhoGUI extends JPanel implements DesenhoUI, MouseListener, MouseMotionListener {
        
    private final List<DesenhoGUIListener> listeners = new ArrayList();
        
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x;
    private int y;
    private boolean primeiroMovimento = true;
    
    private int frequenciaPinturaMod = 3;    
        
    private int mouseX = 0;
    private int mouseY = 0;
        
    private final Pintura pintura;

    public DesenhoGUI() {        
        this.pintura = new Pintura();
        
        super.setBackground( Color.WHITE );        
        super.addMouseListener( this ); 
        super.addMouseMotionListener( this );
    }
     
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent( g );        
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        pintura.pinta( g, this );
    }
     
    @Override
    public void atualizaXYs() {
        if ( primeiroMovimento ) {
            x2 = x;
            y2 = y;            
            primeiroMovimento = false;
        }
        x1 = x2;
        y1 = y2;
        x2 = x;
        y2 = y;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {                                        
        x = e.getX();
        y = e.getY();
        mouseX = e.getX();
        mouseY = e.getY();
                        
        for( DesenhoGUIListener listener : listeners ) 
            listener.arrastou( this ); 
    }
            
    @Override
    public void mouseReleased(MouseEvent e) {
        primeiroMovimento = true;         
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();        
        
        for( DesenhoGUIListener listener : listeners )
            listener.moveu( this ); 
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void addDesenhoListener( DesenhoGUIListener listener ) {
        this.listeners.add( listener );
    }
    
    @Override
    public boolean removeDesenhoListener( DesenhoGUIListener listener ) {
        return this.listeners.remove( listener );
    }

    @Override
    public JComponent getJComponent() {
        return this;
    }
    
    public Pintura getPintura() {
        return pintura;
    }
    
    @Override
    public Grafico getGrafico() {
        return pintura.getGrafico();
    }
    
    @Override
    public Desenho getDesenho() {
        return pintura.getDesenho();
    }
    
    @Override
    public void setDesenho(Desenho desenho) {
        pintura.setDesenho( desenho ); 
    }
    
    @Override
    public void setGrafico( Grafico g2D ) {
        pintura.setGrafico( g2D ); 
    }
            
    @Override
    public int getX1() {
        return x1;
    }

    @Override
    public void setX1(int x1) {
        this.x1 = x1;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public void setY1(int y1) {
        this.y1 = y1;
    }

    @Override
    public int getX2() {
        return x2;
    }

    @Override
    public void setX2(int x2) {
        this.x2 = x2;
    }

    @Override
    public int getY2() {
        return y2;
    }

    @Override
    public void setY2(int y2) {
        this.y2 = y2;
    }

    @Override
    public int getMouseX() {
        return mouseX;
    }

    @Override
    public int getMouseY() {
        return mouseY;
    }

    @Override
    public boolean isPrimeiroMovimento() {
        return primeiroMovimento;
    }

    @Override
    public void setPrimeiroMovimento(boolean primeiroMovimento) {
        this.primeiroMovimento = primeiroMovimento;
    }       

}
