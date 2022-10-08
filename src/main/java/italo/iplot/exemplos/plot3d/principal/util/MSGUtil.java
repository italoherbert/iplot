package italo.iplot.exemplos.plot3d.principal.util;

import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MSGUtil {
    
    private JDialog dialog = new JDialog();
    private MSGTitulo msgTitulo = () -> {
        return "";
    };
    
    public MSGUtil() {
        dialog.setModal( true );
        dialog.setModalityType( Dialog.ModalityType.APPLICATION_MODAL );
        dialog.setAlwaysOnTop( true );   
    }
    
    public void mostraInfo( String msg ) {
        this.mostraInfo(msg, msgTitulo.getTitulo() ); 
    }
    
    public void mostraAlerta( String msg ) {
        this.mostraAlerta(msg, msgTitulo.getTitulo() );
    }
    
    public void mostraErro( String msg ) {
        this.mostraErro(msg, msgTitulo.getTitulo() );
    }    
            
    public int mostraPergunta( String msg ) {
        return this.mostraPergunta(msg, msgTitulo.getTitulo() );
    } 
    
    public void mostraInfo( String msg, String titulo ) {        
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.INFORMATION_MESSAGE );
    }
    
    public void mostraAlerta( String msg, String titulo ) {
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.WARNING_MESSAGE );
    }
    
    public void mostraErro( String msg, String titulo ) {
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.ERROR_MESSAGE );
    }    
    
    public int mostraPergunta( String msg, String titulo ) {
        return JOptionPane.showConfirmDialog( dialog, msg, titulo, JOptionPane.QUESTION_MESSAGE ); 
    }

    public MSGTitulo getTitulo() {
        return msgTitulo;
    }

    public void setTitulo(MSGTitulo msgTitulo) {
        this.msgTitulo = msgTitulo;
    }
    
}
