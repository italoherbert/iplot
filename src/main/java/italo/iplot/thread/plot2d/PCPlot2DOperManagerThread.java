package italo.iplot.thread.plot2d;

import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DAplic;
import italo.iplot.thread.PCOperThread;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;

public class PCPlot2DOperManagerThread extends Plot2DOperManagerThread implements PCOperThread {        
            
    private final PlanoCartesianoPlot2DAplic pcAplic;    
    
    private int oper = NENHUM;
    
    public PCPlot2DOperManagerThread( PlanoCartesianoPlot2DAplic aplic ) {
        super( aplic );
        this.pcAplic = aplic;
    }
    
    @Override
    protected void inicializaObjeto2D() {}
    
    @Override
    protected void constroi() {
        int operAnt = oper;        
        switch( oper ) {
            case SEM_CFG_CONSTRUIR:
                this.operConstroi(); 
                break;
            case MOVER:
                this.operMove();
                break;
            case ZOOM_MAIS:
                this.operZoomMais();
                break;
            case ZOOM_MENOS:
                this.operZoomMenos();
                break;
            case GRADE_VISIVEL:
                this.operGradeVisivel();
                break;
            case REGUA_VISIVEL:
                this.operReguaVisivel();
                break;
            case MOUSE_LINHAS_VISIVEL:
                this.operMouseLinhasVisivel();
                break;
            case AJUSTE:
                this.operAjusta();
                break;
            default:
                super.transformaERepinta();
                break;
        }       
        
        if ( operAnt != SEM_CFG_CONSTRUIR ) {
            oper = NENHUM;
        } else {
             if ( operAnt == oper ) {
                 oper = NENHUM;
             } else {
                 this.constroi();
             }
        }
    }
            
    public void semConfigConstroi() {        
        this.executa( SEM_CFG_CONSTRUIR );
    }
    
    public void move() {
        this.executa( MOVER ); 
    }
    
    public void zoomMais() {        
        this.executa( ZOOM_MAIS );
    }
    
    public void zoomMenos() {
        this.executa( ZOOM_MENOS ); 
    }
    
    public void gradeVisivel() {
        this.executa( GRADE_VISIVEL );
    }
    
    public void reguaVisivel() {        
        this.executa( REGUA_VISIVEL );
    }
    
    public void ajuste() {
        this.executa( AJUSTE );
    }
    
    public void mouseLinhasVisivel() {
        this.executa( MOUSE_LINHAS_VISIVEL );
    }
    
    public void executa( int oper ) {
        this.oper = oper;                                
        super.constroiERepinta();        
    }
    
    private void operConstroi() {        
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.setConfigurarPlotObjsManager( false );
        pc.constroi( aplic ); 
        pc.setConfigurarPlotObjsManager( true );         
    }
    
    private void operMove() {
        pcAplic.getMoveManager().execute( pcAplic ); 
    }

    private void operZoomMais() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.zoom( pcAplic.getZoom(), pcAplic );
    }
    
    private void operZoomMenos() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.zoom( 1.0d / pcAplic.getZoom(), pcAplic );
    }

    private void operGradeVisivel() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.gradeVisivel( !pc.isPintarGrade(), pcAplic );
    }

    private void operReguaVisivel() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();        
        pc.reguaVisivel( !pc.isPintarRegua(), pcAplic );
    }

    private void operAjusta() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.constroi( pcAplic );
    }
    
    private void operMouseLinhasVisivel() {
        PlanoCartesianoObjeto2D pc = pcAplic.getPlanoCartesiano();
        pc.setPintarMouseLinhas( !pc.isPintarMouseLinhas() ); 
    }

    public int getOper() {
        return oper;
    }
    
}
