package italo.iplot.plot3d.g3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Objeto3D {               

    public final static int VCIRCULO = 1;
    public final static int VPOLIGONO_REGULAR_OBJ = 2;
    public final static int VGLOBO_OBJ = 3;
    
    public enum Preenchimento { NORMAL, GRADIENTE }
    
    protected Objeto3D grupo = null;
    protected Objeto3DGrafico grafico = null;
    
    protected boolean visivel = true;
    
    protected boolean desenharFaces = false;
    protected boolean desenharFacesDeTraz = false;
    protected boolean pintarFaces = true;
    protected boolean pintarArestas = false;
    protected boolean pintarVertices = false; 
    protected boolean aplicarIluminacaoAFace = true;
    protected boolean aplicarIluminacaoAAresta = false;
    
    protected boolean inverterVetoresNormais = false;
    protected boolean cortarFilhos = false;
    protected boolean cortavel = true;
    protected boolean addNovaFaceAposCorte = true;
    protected boolean removerNovasArestasAposCorte = false;
    protected boolean construidoNoObjetoGrupo = false;
    protected boolean verticesObjetosConstruidos = false;
    
    protected boolean construidoParcialmente = false;
    protected boolean construindo = false;                
    
    protected Preenchimento preenchimento = Preenchimento.NORMAL;
    protected Preenchimento arestaPreenchimento = Preenchimento.NORMAL;

    protected Objeto3DVisivel obj3DVisivel = null;    
    protected int verticeObjTipo = VCIRCULO;
    protected VerticeRaio3D verticeRaio3D = null;
        
    protected Color cor = new Color( 50, 250, 150 );    
    protected Color verticesCor = Color.BLUE;    
    protected Color arestasCor = Color.WHITE;
    protected Color faceArestasCor = Color.BLACK;

    protected Color[] gradienteCores = { Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED };              
              
    protected final Estrutura3D estrutura = new Estrutura3D( this );
    protected final List<Objeto3D> objetosFilhos = new ArrayList();
    protected List<Objeto3D> verticesObjetos = null;

    private ConstroiObj3DListener constroiObj3DListener = null;
    private final List<ConstroiObj3DListener> constroiObj3DListeners = new ArrayList(); 
    private final List<ConstroiObj3DListener> somenteUmaConstroiObj3DListeners = new ArrayList();
    private ConstruiuObj3DListener construiuObj3DListener = null;
    
    public abstract void constroiObjeto3D(Objeto3DTO to);
    
    public void antesDeDesenhar() {}
    
    public void constroi( Objeto3DTO to ) {
        construidoParcialmente = false;
        construindo = true;
        
        if ( constroiObj3DListener != null )
            constroiObj3DListener.construindo( this, to ); 
        
        constroiObj3DListeners.forEach( l -> l.construindo( this, to ) ); 
        somenteUmaConstroiObj3DListeners.forEach( l -> l.construindo( this, to ) );
        
        estrutura.reinicia();
        
        this.constroiObjeto3D( to ); 
        
        estrutura.recalculaArestas();
        
        verticesObjetosConstruidos = false;
        if ( this.isObjVertices() ) {
            if ( verticesObjetos == null ) {
                verticesObjetos = new ArrayList();
            } else {
                for( Objeto3D vobj : verticesObjetos )
                    this.removeObjeto( vobj );
                verticesObjetos.clear();                
            }
            
            for( Vertice3D v : estrutura.getVertices() ) {
                double x = v.getX();
                double y = v.getY();
                double z = v.getZ();

                Objeto3D vobj = to.getVObj3DFactory().novo( this );
                if ( vobj != null ) {                        
                    vobj.constroi( to ); 
                    vobj.setObj3DVisivel( new VerticeObjeto3DVisivel( this ) );
                    vobj.setConstruidoNoObjetoGrupo( true );

                    to.getTransformador3D().translada( vobj, x, y, z, to.getInicialFiltroV3D() );             
                    to.getTransformador3D().translada( vobj, x, y, z, to.getXYZFiltroV3D() );             
                    vobj.aplicaTransformacoes();

                    verticesObjetos.add( vobj );
                    this.addObjeto( vobj ); 
                }
            }
            verticesObjetosConstruidos = true;
        }        
            
        objetosFilhos.forEach(obj -> {
            if ( !obj.isConstruidoNoObjetoGrupo() )
                obj.constroi( to ); 
        } ); 
        
        estrutura.calculaVerticesParaVN( to, to.getXYZFiltroV3D() );
                
        construidoParcialmente = true;
                        
        if ( constroiObj3DListener != null )
            constroiObj3DListener.construiuParcialmente( this, to ); 
            
        constroiObj3DListeners.forEach( l -> l.construiuParcialmente( this, to ) );                
        
        somenteUmaConstroiObj3DListeners.forEach( l -> l.construiuParcialmente( this, to ) );        
        somenteUmaConstroiObj3DListeners.clear();
        
        construindo = false;        

        if ( construiuObj3DListener != null )
            construiuObj3DListener.construiu( this, to );        
    }
            
    public void reiniciaVertices() {
        objetosFilhos.forEach( o -> o.reiniciaVertices() );
        estrutura.getVertices().forEach( v -> v.copiaP0ParaP() ); 
    }
        
    public void aplicaTransformacoes() {       
        objetosFilhos.forEach( o -> o.aplicaTransformacoes() );         
        estrutura.getVertices().forEach( v -> v.copiaParaVisao() );         
    }
    
    public void setGradienteCores( Color cor1, Color cor2 ) {
        preenchimento = Preenchimento.GRADIENTE;
        this.gradienteCores = new Color[] { cor1, cor2 };
    }
    
    public void setGradienteCores( Color... cores ) {
        preenchimento = Preenchimento.GRADIENTE;
        this.gradienteCores = cores;
    }
                       
    public boolean isVisivel() {
        return visivel && ( obj3DVisivel == null ? true : obj3DVisivel.isVisivel() );            
    }      
    
    public boolean isObjVertices() {
        return verticeObjTipo != VCIRCULO;
    }
        
    public void addObjeto(Objeto3D obj) {
        obj.setGrupo( this );
        objetosFilhos.add( obj );      
    }       
    
    public void removeObjeto( Objeto3D obj ) {
        obj.setGrupo( null );
        objetosFilhos.remove( obj );
    }
    
    public void removeTodosOsObjetos() {
        objetosFilhos.forEach( obj -> obj.setGrupo( null ) );
        objetosFilhos.clear();
    }
    
    public ConstroiObj3DListener getConstroiObj3DListener() {
        return constroiObj3DListener;
    }
    
    public ConstruiuObj3DListener getConstruiuObj3DListener() {
        return construiuObj3DListener;
    }

    public void setConstroiObj3DListener(ConstroiObj3DListener listener) {
        this.constroiObj3DListener = listener;
    }
    
    public void setConstruiuObj3DListener( ConstruiuObj3DListener listener ) {
        this.construiuObj3DListener = listener;
    }
        
    public boolean addConstroiObj3DListener(ConstroiObj3DListener listener) {
        return this.constroiObj3DListeners.add( listener );        
    }
    
    public boolean removeConstroiObj3DListener( ConstroiObj3DListener listener ) {
        return constroiObj3DListeners.remove( listener );
    }
    
    public boolean addSomenteUmaConstroiObj3DListener( ConstroiObj3DListener listener ) {
        return somenteUmaConstroiObj3DListeners.add( listener );
    }
    
    public boolean isConstruidoParcialmente() {
        return construidoParcialmente;
    }
    
    public boolean isConstruindo() {
        return construindo;
    }

    public List<Objeto3D> getObjetos() {
        return objetosFilhos;
    }
        
    public Objeto3D getGrupo() {
        return grupo;
    }

    public void setGrupo(Objeto3D grupo) {
        this.grupo = grupo;
    }
    
    public boolean isPintarVertices() {
        return pintarVertices;
    }

    public void setPintarVertices(boolean pintarVertices) {
        this.pintarVertices = pintarVertices;
    }

    public Color getVerticesCor() {
        return verticesCor;
    }

    public void setVerticesCor(Color verticesCor) {
        this.verticesCor = verticesCor;
    }

    public boolean isDesenharFaces() {
        return desenharFaces;
    }

    public void setDesenharFaces(boolean desenharFaces) {
        this.desenharFaces = desenharFaces;
    }

    public boolean isDesenharFacesDeTraz() {
        return desenharFacesDeTraz;
    }

    public void setDesenharFacesDeTraz(boolean desenharFacesDeTraz) {
        this.desenharFacesDeTraz = desenharFacesDeTraz;
    }
    
    public boolean isPintarFaces() {
        return pintarFaces;
    }

    public void setPintarFaces(boolean pintarFaces) {
        this.pintarFaces = pintarFaces;
    }

    public boolean isPintarArestas() {
        return pintarArestas;
    }

    public void setPintarArestas(boolean pintarArestas) {
        this.pintarArestas = pintarArestas;
    }
    
    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Color getArestasCor() {
        return arestasCor;
    }

    public void setArestasCor(Color arestasCor) {
        this.arestasCor = arestasCor;
    }

    public Color getFaceArestasCor() {
        return faceArestasCor;
    }

    public void setFaceArestasCor(Color faceArestasCor) {
        this.faceArestasCor = faceArestasCor;
    }

    public boolean isInverterVetoresNormais() {
        return inverterVetoresNormais;
    }

    public void setInverterVetoresNormais(boolean inverterVetoresNormais) {
        this.inverterVetoresNormais = inverterVetoresNormais;
    }

    public Objeto3DVisivel getObj3DVisivel() {
        return obj3DVisivel;
    }

    public void setObj3DVisivel(Objeto3DVisivel obj3DVisivel) {
        this.obj3DVisivel = obj3DVisivel;
    }
            
    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public Objeto3DGrafico getGrafico() {
        return grafico;
    }

    public void setGrafico(Objeto3DGrafico grafico) {
        this.grafico = grafico;
    }

    public Preenchimento getPreenchimento() {
        return preenchimento;
    }

    public void setPreenchimento(Preenchimento preenchimento) {
        this.preenchimento = preenchimento;
    }

    public Preenchimento getArestaPreenchimento() {
        return arestaPreenchimento;
    }

    public void setArestaPreenchimento(Preenchimento arestaPreenchimento) {
        this.arestaPreenchimento = arestaPreenchimento;
    }

    public boolean isCortarFilhos() {
        return cortarFilhos;
    }

    public void setCortarFilhos(boolean cortarFilhos) {
        this.cortarFilhos = cortarFilhos;
    }

    public boolean isAddNovaFaceAposCorte() {
        return addNovaFaceAposCorte;
    }

    public void setAddNovaFaceAposCorte(boolean addNovaFaceAposCorte) {
        this.addNovaFaceAposCorte = addNovaFaceAposCorte;
    }
    
    public Color[] getGradienteCores() {
        return gradienteCores;
    }

    public Estrutura3D getEstrutura() {
        return estrutura;
    }

    public boolean isConstruidoNoObjetoGrupo() {
        return construidoNoObjetoGrupo;
    }

    public void setConstruidoNoObjetoGrupo(boolean construidoNoObjetoGrupo) {
        this.construidoNoObjetoGrupo = construidoNoObjetoGrupo;
    }

    public boolean isRemoverNovasArestasAposCorte() {
        return removerNovasArestasAposCorte;
    }

    public void setRemoverNovasArestasAposCorte(boolean removerNovasArestasAposCorte) {
        this.removerNovasArestasAposCorte = removerNovasArestasAposCorte;
    }

    public int getVerticeObjTipo() {
        return verticeObjTipo;
    }

    public void setVerticeObjTipo(int verticeObjTipo) {
        this.verticeObjTipo = verticeObjTipo;
    }

    public VerticeRaio3D getVerticeRaio3D() {
        return verticeRaio3D;
    }

    public void setVerticeRaio3D(VerticeRaio3D verticeRaio3D) {
        this.verticeRaio3D = verticeRaio3D;
    }

    public boolean isCortavel() {
        return cortavel;
    }

    public void setCortavel(boolean cortavel) {
        this.cortavel = cortavel;
    }

    public boolean isAplicarIluminacaoAFace() {
        return aplicarIluminacaoAFace;
    }

    public void setAplicarIluminacaoAFace(boolean aplicarIluminacaoAFace) {
        this.aplicarIluminacaoAFace = aplicarIluminacaoAFace;
    }

    public boolean isAplicarIluminacaoAAresta() {
        return aplicarIluminacaoAAresta;
    }

    public void setAplicarIluminacaoAAresta(boolean aplicarIluminacaoAAresta) {
        this.aplicarIluminacaoAAresta = aplicarIluminacaoAAresta;
    }
    
}
