package italo.iplot.thread.plot2d;

import italo.iplot.plot2d.Plot2DAplic;
import italo.iplot.thread.OperManagerThread;

public abstract class Plot2DOperManagerThread extends OperManagerThread {
  
    protected final Plot2DAplic aplic;
    
    public Plot2DOperManagerThread( Plot2DAplic aplic ) {
        this.aplic = aplic;        
    }

    protected abstract void inicializaObjeto2D();

    
    @Override
    protected void inicializa() {
        this.inicializaObjeto2D();
        aplic.getDesenho2D().setDesenho2DListener( this );
    }

    @Override
    protected void transformaERepinta() {                    
        synchronized( this ) {
            aplic.getDesenhoUI().repaint(); 
        }      
    }            
          
}

