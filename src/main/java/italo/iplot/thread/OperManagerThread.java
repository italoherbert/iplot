package italo.iplot.thread;

import italo.iplot.desenho.DesenhoListener;
import italo.iplot.gui.Desenho;

public abstract class OperManagerThread extends Thread implements DesenhoListener {
        
    private OperManagerAlgoritmoDriver algoritmoDrv = null;
    private boolean parar = false;

    protected boolean esperando = false;
    protected boolean esperar = true;
    protected boolean executando = false;
    
    protected boolean ocupado = false;
    protected boolean pintado = true;
    
    protected boolean esperandoAntesDeConstruir = false;
    protected boolean esperandoAntesDePintar = false;
    protected boolean construirERepintarAposPintura = false;
    
    private boolean primeiraEspera = true;
    protected boolean construir = false;
            
    public OperManagerThread() {
        super( "Constroi e repinta - (Thread)" );
    }
    
    protected abstract void inicializa();
    protected abstract void constroi(); 
    
    protected abstract void transformaERepinta();
    
    public DesenhoListener startThreadDesenhoListener() {
        return new DesenhoListener() {
            @Override
            public void desenhando(Desenho desenho) {}

            @Override
            public void desenhou(Desenho desenho) {
                startThread();
            }
        };
    }
    
    public void startThread() {
        super.start();
        while( !esperando ) {
            synchronized( this ) {
                try {
                    wait();
                } catch ( InterruptedException e ) {

                }
            }
        }
    }
        
    @Override
    public void run() {    
        this.inicializa();                
        
        executando = true;
                
        esperar = true;
        while( !this.isParar() ) { 
            while ( esperar ) {
                try {                    
                    if ( primeiraEspera ) {
                        primeiraEspera = false;
                        synchronized( this ) {
                            if ( esperar ) {
                                notifyAll();
                                esperando = true;
                                esperandoAntesDeConstruir = true;
                                wait();
                            }
                        }
                    } else {
                        synchronized( this ) {
                            if ( esperar ) {
                                esperando = true;
                                esperandoAntesDeConstruir = true;
                                wait();
                            }
                        }
                    }
                } catch (InterruptedException ex) {

                }
            }
            esperando = false;
            esperandoAntesDeConstruir = false;
            
            pintado = false;
            esperar = true;
            
            ocupado = true;
            
            if ( construir ) {
                this.constroi();  
                construir = false;
                construirERepintarAposPintura = false;
            }                           
            
            this.transformaERepinta();                        
                
            while( !pintado ) {
                try {
                    synchronized( this ) {
                        if ( !pintado ) {
                            esperando = true;
                            esperandoAntesDePintar = true;
                            wait();
                        }
                    }
                } catch (InterruptedException ex) {

                }
            }
            
            esperando = false;            
            esperandoAntesDePintar = false;
            ocupado = false; 
            
            esperar = !construirERepintarAposPintura;               
        }
        
        executando = false;
    }
    
    public void apenasTransformaERepinta() {
        this.constroiERepinta( false );        
    }
    
    public void constroiERepinta() {
        this.constroiERepinta( true ); 
    }
    
    private void constroiERepinta( boolean construir ) {                                   
        if ( !executando )
            return;

        if ( !this.construir )
            this.construir = construir;
                
        if ( esperandoAntesDeConstruir ) {            
            esperar = false;
            construirERepintarAposPintura = false;
            synchronized( this ) {
                notifyAll();
            }
        } else {      
            construirERepintarAposPintura = construir;
        }        
    }
        
    public boolean isParar() {
        return parar || ( algoritmoDrv == null ? false : algoritmoDrv.condicaoParada() );
    }  
    
    public boolean isEsperando() {
        return esperando;
    }
        
    @Override
    public void desenhando( Desenho desenho ) {
        pintado = false;
    }

    @Override
    public void desenhou( Desenho desenho ) {
        pintado = true;
        if ( esperandoAntesDePintar && executando ) {
            synchronized( this ) {
                notifyAll();
            }
        }
    }

    public boolean isExecutando() {
        return executando;
    }

    public OperManagerAlgoritmoDriver getAlgoritmoDriver() {
        return algoritmoDrv;
    }

    public void setAlgoritmoDriver(OperManagerAlgoritmoDriver drv) {
        this.algoritmoDrv = drv;
    }

    public void setParar(boolean parar) {
        this.parar = parar;
    }

}
