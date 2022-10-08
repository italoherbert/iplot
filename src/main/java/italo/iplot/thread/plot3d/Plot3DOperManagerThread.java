package italo.iplot.thread.plot3d;

import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.thread.OperManagerThread;

public abstract class Plot3DOperManagerThread extends OperManagerThread {
  
    protected final Plot3DAplic aplic;
    protected boolean incrementarRotAngs = false;
        
    public Plot3DOperManagerThread( Plot3DAplic aplic ) {
        this.aplic = aplic;        
    }

    protected abstract void inicializaObjeto3D();
    
    protected abstract void transforma();
    
    @Override
    protected void inicializa() {
        this.inicializaObjeto3D();
        aplic.getDesenho3D().setDesenho3DListener( this );
    }

    @Override
    protected void transformaERepinta() {
        if ( aplic.getRotManager() != null && incrementarRotAngs ) {
            aplic.getRotManager().execute( aplic );         
            incrementarRotAngs = false;
        }               
        this.transforma();
                                             
        synchronized( this ) {
            aplic.getDesenhoUI().repaint(); 
        }              
    }       
    
    public void apenasTransformaERepinta( boolean incRotAngs ) {
        if ( !incrementarRotAngs )
            incrementarRotAngs = incRotAngs;
                
        super.apenasTransformaERepinta();        
    }
    
    public void constroiERepinta( boolean incRotAngs ) {        
        if ( !incrementarRotAngs )
            incrementarRotAngs = incRotAngs;
        
        super.constroiERepinta();        
    }
                
}
