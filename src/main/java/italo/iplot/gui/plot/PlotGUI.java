package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoGUIListener;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.plot.icone.ApontadorIcone;
import italo.iplot.gui.plot.icone.BGIcone;
import italo.iplot.gui.plot.icone.GirarIcone;
import italo.iplot.gui.plot.icone.Icone;
import italo.iplot.gui.plot.icone.MoverIcone;
import italo.iplot.gui.plot.icone.ZoomIcone;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class PlotGUI extends JPanel implements ActionListener, MouseListener, DesenhoGUIListener {
    
    public final static int NENHUM = 0;
    public final static int APONTADOR = 1;
    public final static int GIRAR = 2;
    public final static int MOVER = 3;
    public final static int ZOOM_MAIS = 4;
    public final static int ZOOM_MENOS = 5; 
    
    private final int ICONE_DIM = 24;    
    private final int CURSOR_ICONE_DIM = 32;    
    private final boolean MOVER_ICONE_DES_TRI = true;
    
    private final String MAO_IMG_CHAVE = "mao";
    private final String MAO_FECHADA_IMG_CHAVE = "maofechada";
    private final String ZOOM_MAIS_IMG_CHAVE = "zoommais";
    private final String ZOOM_MENOS_IMG_CHAVE = "zoommenos";
                    
    private final JToolBar ferBar = new JToolBar();
    private final JPanel textoInfoPNL = new JPanel();
    
    protected final JToggleButton apontadorBT;
    protected final JToggleButton girarBT;
    protected final JToggleButton moverBT;
    protected final JToggleButton zoomMaisBT;
    protected final JToggleButton zoomMenosBT;
    protected JButton gradeBT;
    protected JButton reguaBT;
    protected JButton ajusteBT;
    protected JButton mouseLinhasBT;
    protected JLabel infoLB;
                            
    private final Cursor defaultCursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    private final Cursor moverCursor = Cursor.getPredefinedCursor( Cursor.MOVE_CURSOR );
    private Cursor maoCursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    private Cursor maoFechadaCursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    private Cursor zoomMaisCursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    private Cursor zoomMenosCursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    
    private int btSelecionado = NENHUM;
    
    private DesenhoUI desenhoUI = null;
    
    private PlotGUIListener listener = null;
    private ExecPlotGUIListener mouseListener = null;
                
    public PlotGUI() {  
        apontadorBT = new JToggleButton();
        girarBT = new JToggleButton();
        moverBT = new JToggleButton();
        zoomMaisBT = new JToggleButton();
        zoomMenosBT = new JToggleButton();
        gradeBT = new JButton( "grade" );
        reguaBT = new JButton( "regua" );
        mouseLinhasBT = new JButton( "linhas" );
        ajusteBT = new JButton( "ajuste" );
                
        ferBar.setLayout( new BoxLayout( ferBar, BoxLayout.X_AXIS ) ); 
        
        AbstractButton[] botoes = {
            apontadorBT, girarBT, moverBT, zoomMaisBT, zoomMenosBT, gradeBT, reguaBT, mouseLinhasBT, ajusteBT
        };
                        
        Icone apontadorIcone = new ApontadorIcone( ICONE_DIM );
        Icone sobreBTApontadorIcone = new ApontadorIcone( ICONE_DIM );
        Icone pressBTApontadorIcone = new ApontadorIcone( ICONE_DIM );
        Icone selecBTApontadorIcone = new ApontadorIcone( ICONE_DIM );
        
        Icone girarIcone = new GirarIcone( ICONE_DIM );
        Icone sobreBTGirarIcone = new GirarIcone( ICONE_DIM );
        Icone pressBTGirarIcone = new GirarIcone( ICONE_DIM );
        Icone selecBTGirarIcone = new GirarIcone( ICONE_DIM );
        
        Icone moverIcone = new MoverIcone( ICONE_DIM, MOVER_ICONE_DES_TRI );
        Icone sobreBTMoverIcone = new MoverIcone( ICONE_DIM, MOVER_ICONE_DES_TRI );
        Icone pressBTMoverIcone = new MoverIcone( ICONE_DIM, MOVER_ICONE_DES_TRI );
        Icone selecBTMoverIcone = new MoverIcone( ICONE_DIM, MOVER_ICONE_DES_TRI );
        
        Icone zoomMaisIcone = new ZoomIcone( ICONE_DIM, true );
        Icone sobreBTZoomMaisIcone = new ZoomIcone( ICONE_DIM, true );
        Icone pressBTZoomMaisIcone = new ZoomIcone( ICONE_DIM, true );
        Icone selecBTZoomMaisIcone = new ZoomIcone( ICONE_DIM, true );
        
        Icone zoomMenosIcone = new ZoomIcone( ICONE_DIM, false );
        Icone sobreBTZoomMenosIcone = new ZoomIcone( ICONE_DIM, false );
        Icone pressBTZoomMenosIcone = new ZoomIcone( ICONE_DIM, false );
        Icone selecBTZoomMenosIcone = new ZoomIcone( ICONE_DIM, false );
                
        Icone btIcone = new BGIcone( ICONE_DIM );
        Icone sobreBTIcone = new BGIcone( ICONE_DIM );
        Icone pressBTIcone = new BGIcone( ICONE_DIM );
        Icone selecBTIcone = new BGIcone( ICONE_DIM );
                             
        Icone[][] icones = { 
            { apontadorIcone, sobreBTApontadorIcone, pressBTApontadorIcone, selecBTApontadorIcone },
            { girarIcone, sobreBTGirarIcone, pressBTGirarIcone, selecBTGirarIcone },
            { moverIcone, sobreBTMoverIcone, pressBTMoverIcone, selecBTMoverIcone },
            { zoomMaisIcone, sobreBTZoomMaisIcone, pressBTZoomMaisIcone, selecBTZoomMaisIcone },
            { zoomMenosIcone, sobreBTZoomMenosIcone, pressBTZoomMenosIcone, selecBTZoomMenosIcone },
            { btIcone, sobreBTIcone, pressBTIcone, selecBTIcone },
            { btIcone, sobreBTIcone, pressBTIcone, selecBTIcone },
            { btIcone, sobreBTIcone, pressBTIcone, selecBTIcone },
            { btIcone, sobreBTIcone, pressBTIcone, selecBTIcone }
        };
                                
        int[] strokes = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
        Color[][] linhaCores = {
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },            
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },            
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },            
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE }            
        };
        Color[][] cores = {
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK },
            { Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK }
        };
        Color[] bg_cores = {
            null,
            new Color( 240, 240, 255 ),
            Color.LIGHT_GRAY, 
            new Color( 150, 180, 200 )
        };
        double espDIMFator = 0.125d;
        
        for( int i = 0; i < icones.length; i++ ) {
            for( int j = 0; j < icones[i].length; j++ ) {                
                icones[i][j].setStroke( new BasicStroke( strokes[ i ], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER ) ); 
                icones[i][j].setCor( cores[ i ][ j ] ); 
                icones[i][j].setLinhaCor( linhaCores[ i ][ j ] ); 
                icones[i][j].setBGCor( bg_cores[ j ] ); 
                icones[i][j].setPintarBorda( j > 0 );                 
                icones[i][j].setEspDIMFator( espDIMFator );
            }

            botoes[i].setHorizontalTextPosition( AbstractButton.CENTER ); 
            botoes[i].setIcon( icones[i][0] ); 
            botoes[i].setRolloverIcon( icones[i][1] ); 
            botoes[i].setPressedIcon( icones[i][2] ); 
            botoes[i].setSelectedIcon( icones[i][3] ); 
            botoes[i].setContentAreaFilled( false );
            botoes[i].setOpaque( false );        
            botoes[i].setBorderPainted( false );
            botoes[i].setFocusPainted( false );
            botoes[i].addActionListener( this );
            ferBar.add( botoes[i] );
        }   
                        
        Image cursorMaoImage = this.carregaImagem( "mao.png" );  
        Image cursorMaoFechadaImage = this.carregaImagem( "mao-fechada.png" );
        
        ZoomIcone cursorZoomIcone = new ZoomIcone( CURSOR_ICONE_DIM, true );
        cursorZoomIcone.setEspDIMFator( 0.2d ); 
        cursorZoomIcone.setPintarCirculoCentral( true ); 
        cursorZoomIcone.setCor( Color.WHITE );
        cursorZoomIcone.setLinhaCor( Color.BLACK );
        cursorZoomIcone.setStroke( new BasicStroke() );                
        cursorZoomIcone.setDesativarAntiAliasing( true ); 
        
        BufferedImage cursorZoomMaisImage = new BufferedImage( CURSOR_ICONE_DIM, CURSOR_ICONE_DIM, BufferedImage.TYPE_INT_ARGB_PRE );
        cursorZoomIcone.paintIcon( null, cursorZoomMaisImage.getGraphics(), 0, 0 );
        
        cursorZoomIcone.setPintarTracoVertical( false ); 
        
        BufferedImage cursorZoomMenosImage = new BufferedImage( CURSOR_ICONE_DIM, CURSOR_ICONE_DIM, BufferedImage.TYPE_INT_ARGB_PRE );
        cursorZoomIcone.paintIcon( null, cursorZoomMenosImage.getGraphics(), 0, 0 );
                
        maoCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorMaoImage, new Point( 0, 0 ), MAO_IMG_CHAVE );
        maoFechadaCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorMaoFechadaImage, new Point( 0, 0 ), MAO_FECHADA_IMG_CHAVE );
                                          
        zoomMaisCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorZoomMaisImage, new Point( 0, 0 ), ZOOM_MAIS_IMG_CHAVE );
        zoomMenosCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorZoomMenosImage, new Point( 0, 0 ), ZOOM_MENOS_IMG_CHAVE );
        
        infoLB = new JLabel( " " );

        textoInfoPNL.setLayout( new FlowLayout( FlowLayout.LEFT ) ); 
        textoInfoPNL.add( infoLB );
                
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.NORTH, ferBar );        
        super.add( BorderLayout.SOUTH, textoInfoPNL );
    }
    
    protected void actionPerformed2( ActionEvent e ) {}

    @Override
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
                
        if ( mouseListener != null )
            mouseListener.plotEventoExecutando( this, desenhoUI );
        
        if ( e.getSource() == apontadorBT ) {
            apontadorBT.setSelected( !apontadorBT.isSelected() );
            this.acionarApontadorBT();
        } else if ( e.getSource() == girarBT ) {
            girarBT.setSelected( !girarBT.isSelected() );
            this.acionarGirarBT();
        } else if ( e.getSource() == moverBT ) {
            moverBT.setSelected( !moverBT.isSelected() );
            this.acionarMoverBT();
        } else if ( e.getSource() == zoomMaisBT ) {
            zoomMaisBT.setSelected( !zoomMaisBT.isSelected() );
            this.acionarZoomMaisBT();
        } else if ( e.getSource() == zoomMenosBT ) {
            zoomMenosBT.setSelected( !zoomMenosBT.isSelected() );
            this.acionarZoomMenosBT();
        } else if ( e.getSource() == gradeBT ) {
            listener.gradeBTAcionado( this );
        } else if ( e.getSource() == reguaBT ) {
            listener.reguaBTAcionado( this );
        } else if ( e.getSource() == ajusteBT ) {
            listener.ajusteBTAcionado( this ); 
        } else if ( e.getSource() == mouseLinhasBT ) {
            listener.mouseLinhasBTAcionado( this ); 
        }
                        
        if ( mouseListener != null )
            mouseListener.plotEventoExecutado( this, desenhoUI );        
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        if ( listener != null )
            listener.clicou( this, desenhoUI, e );        
    }

    @Override
    public void mousePressed( MouseEvent e ) {
        if ( btSelecionado == GIRAR )
            desenhoUI.setCursor( maoFechadaCursor );
                
        if ( mouseListener != null )
            mouseListener.plotEventoExecutando( this, desenhoUI );
    }

    @Override
    public void mouseReleased( MouseEvent e ) {
        if ( btSelecionado == GIRAR )
            desenhoUI.setCursor( maoCursor );       
        
        if ( mouseListener != null )
            mouseListener.plotEventoExecutado( this, desenhoUI );
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void arrastou(DesenhoUI ui) {
        if ( listener != null )
            listener.arrastou( this, desenhoUI );        
    }

    @Override
    public void moveu(DesenhoUI ui) {
        if ( listener != null )
            listener.moveu( this, desenhoUI );
    }
    
    public void acionarApontadorBT() {
        apontadorBT.setSelected( !apontadorBT.isSelected() );
        
        btSelecionado = APONTADOR;
        if ( apontadorBT.isSelected() ) {                                                                
            girarBT.setSelected( false );
            moverBT.setSelected( false );
            zoomMaisBT.setSelected( false );
            zoomMenosBT.setSelected( false ); 
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        } else {
            btSelecionado = NENHUM;
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        }       
                
        if ( listener != null )
            listener.apontadorBTAcionado( this );
    }
    
    public void acionarGirarBT() {
        girarBT.setSelected( !girarBT.isSelected() );
        
        btSelecionado = GIRAR;
        if ( girarBT.isSelected() ) {                                                                
            apontadorBT.setSelected( false );
            moverBT.setSelected( false );
            zoomMaisBT.setSelected( false );
            zoomMenosBT.setSelected( false ); 
            if ( desenhoUI != null )
                desenhoUI.setCursor( maoCursor );
        } else {
            btSelecionado = NENHUM;
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        }        
                
        if ( listener != null )
            listener.girarBTAcionado( this );
    }
    
    public void acionarMoverBT() {
        moverBT.setSelected( !moverBT.isSelected() );
        
        btSelecionado = MOVER;
        if ( moverBT.isSelected() ) {                
            apontadorBT.setSelected( false );
            girarBT.setSelected( false );
            zoomMaisBT.setSelected( false );
            zoomMenosBT.setSelected( false ); 
            if ( desenhoUI != null )
                desenhoUI.setCursor( moverCursor );
        } else {
            btSelecionado = NENHUM;
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        }

        if ( listener != null )
            listener.moverBTAcionado( this );
    }
    
    public void acionarZoomMaisBT() {
        zoomMaisBT.setSelected( !zoomMaisBT.isSelected() );
        
        btSelecionado = ZOOM_MAIS;
        if ( zoomMaisBT.isSelected() ) {
            apontadorBT.setSelected( false );
            moverBT.setSelected( false );
            girarBT.setSelected( false );
            zoomMenosBT.setSelected( false ); 
            if ( desenhoUI != null )
                desenhoUI.setCursor( zoomMaisCursor );                
        } else {
            btSelecionado = NENHUM;
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        }
        
        if ( listener != null )
            listener.zoomMaisBTAcionado( this );
    }
    
    public void acionarZoomMenosBT() {        
        zoomMenosBT.setSelected( !zoomMenosBT.isSelected() );

        btSelecionado = ZOOM_MENOS;
        if ( zoomMenosBT.isSelected() ) {
            apontadorBT.setSelected( false );
            moverBT.setSelected( false );
            girarBT.setSelected( false ); 
            zoomMaisBT.setSelected( false );
            if ( desenhoUI != null )
                desenhoUI.setCursor( zoomMenosCursor );                
        } else {
            btSelecionado = NENHUM;
            if ( desenhoUI != null )
                desenhoUI.setCursor( defaultCursor );
        } 
        
        if ( listener != null )
            listener.zoomMenosBTAcionado( this );
    }

    public void setMouseLinhasVisiveis( boolean visivel ) {
        infoLB.setVisible( visivel );
        textoInfoPNL.setVisible( visivel ); 
    }
    
    public void setDesenhoUI( DesenhoUI ui ) {
        if ( desenhoUI != null )
            super.remove( (JComponent)desenhoUI );
        
        super.add( BorderLayout.CENTER, (JComponent)ui );
        ui.removeDesenhoListener( this );
        ui.addDesenhoListener( this ); 
        ui.removeMouseListener( this );
        ui.addMouseListener( this );         
        desenhoUI = ui;
    }
    
    private Image carregaImagem( String nome ) {
        try {
            InputStream in = Icone.class.getResourceAsStream( nome );
            if ( in != null )
                return ImageIO.read( in );            
        } catch (IOException ex) {
            
        }
        return null;
    }
                   
    public void setPlotListener( PlotGUIListener listener ) {
        this.listener = listener;
    }
            
    public void setExecPlotListener( ExecPlotGUIListener listener ) {
        this.mouseListener = listener;
    }
        
    public void setGirarRotulo( String rotulo ) {
        girarBT.setText( rotulo ); 
    }
    
    public void setMoverRotulo( String rotulo ) {
        moverBT.setText( rotulo ); 
    }
    
    public void setZoomMaisRotulo( String rotulo ) {
        zoomMaisBT.setText( rotulo ); 
    }
    
    public void setZoomMenosRotulo( String rotulo ) {
        zoomMenosBT.setText( rotulo );
    }
    
    public void setMostrarEsconderGradeRotulo( String rotulo ) {
        gradeBT.setText( rotulo );
    }
    
    public void setMostrarEsconderReguaRotulo( String rotulo ) {
        reguaBT.setText( rotulo ); 
    }
    
    public void setMostrarEsconderMouseLinhasRotulo( String rotulo ) {
        mouseLinhasBT.setText( rotulo ); 
    }
        
    public void setTextoInfo( String info ) {
        this.setTextoInfo( info, Color.BLACK );
    }
    
    public void setTextoInfo( String info, Color cor ) {
        infoLB.setForeground( cor );
        infoLB.setText( info );
    }
    
    public int getBTSelecionado() {
        return btSelecionado;
    }

    public DesenhoUI getDesenhoUI() {
        return desenhoUI;
    }
        
}
