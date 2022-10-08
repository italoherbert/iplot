package italo.iplot.thread.plot3d;

import italo.iplot.thread.PCOperThread;
import static italo.iplot.thread.PCOperThread.AJUSTE;
import static italo.iplot.thread.PCOperThread.GRADE_VISIVEL;
import static italo.iplot.thread.PCOperThread.REGUA_VISIVEL;
import static italo.iplot.thread.PCOperThread.SEM_CFG_CONSTRUIR;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DAplic;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;

public class PCPlot3DOperManagerThread extends Plot3DOperManagerThread implements PCOperThread {          
            
    private int oper = NENHUM;
    private int operAnt = NENHUM;
    
    private final PlanoCartesianoPlot3DAplic pcAplic;    
    
    public PCPlot3DOperManagerThread( PlanoCartesianoPlot3DAplic aplic ) {
        super( aplic );
        this.pcAplic = aplic;
    }
    
    @Override
    protected void inicializaObjeto3D() {}

    @Override
    protected void transforma() {
        pcAplic.getPlanoCartesiano().transforma( aplic ); 
    }
    
    @Override
    protected void constroi() {       
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
            
    public void semConfigConstroi( boolean incRotAngs ) {        
        this.executa( SEM_CFG_CONSTRUIR, incRotAngs );
    }
    
    public void move() {
        this.executa( MOVER, false ); 
    }
    
    public void zoomMais() {
        this.executa( ZOOM_MAIS, false );
    }
    
    public void zoomMenos() {
        this.executa( ZOOM_MENOS, false ); 
    }
    
    public void gradeVisivel() {
        this.executa( GRADE_VISIVEL, false );
    }
    
    public void reguaVisivel() {
        this.executa( REGUA_VISIVEL, false );
    }
    
    public void ajuste() {
        this.executa( AJUSTE, false );
    }
    
    public void executa( int oper, boolean incRotAngs ) {        
        this.oper = oper;
        super.constroiERepinta( incRotAngs );                        
    }   
        
    private void operConstroi() {        
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        pc.setConfigurarPlotObjsManager( false );
        pc.constroi( pcAplic ); 
        pc.setConfigurarPlotObjsManager( true ); 
    }
    
    private void operMove() {
        pcAplic.getMoveManager().execute( pcAplic ); 
    }

    private void operZoomMais() {
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        pc.zoom( pcAplic.getZoom(), pcAplic );
    }
    
    private void operZoomMenos() {
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        pc.zoom( 1.0d / pcAplic.getZoom(), pcAplic );
    }

    private void operGradeVisivel() {
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        pc.gradeVisivel( !pc.isPintarGrade(), pcAplic );
    }

    private void operReguaVisivel() {
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();        
        pc.reguaVisivel( !pc.isPintarRegua(), pcAplic );
    }

    private void operAjusta() {
        PlanoCartesianoObjeto3D pc = pcAplic.getPlanoCartesiano();
        pc.constroi( pcAplic );
    }
        
    public int getOper() {
        return oper;
    }
    
}
