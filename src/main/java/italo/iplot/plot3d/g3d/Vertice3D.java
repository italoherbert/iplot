package italo.iplot.plot3d.g3d;

import java.util.ArrayList;
import java.util.List;

public class Vertice3D {
    
    private double[] p0 = new double[3];    
    private double[] p = new double[3];    
    private double[] visaoP  = new double[3];
    
    private List<Face3D> faces = null;
    private List<Aresta3D> arestas = null;        
    
    private VerticeRaio3D raio3D;
    
    public Vertice3D( double[] v ) {
        this( v[0], v[1], v[2] );
    }

    public Vertice3D(double x0, double y0, double z0) {
        this.visaoP[0] = this.p[0] = this.p0[0] = x0;
        this.visaoP[1] = this.p[1] = this.p0[1] = y0;
        this.visaoP[2] = this.p[2] = this.p0[2] = z0;
    }    
        
    public void addAresta(Aresta3D aresta) {
        if ( arestas == null )
            arestas = new ArrayList();
        arestas.add( aresta );        
    }
    
    public void addFace(Face3D face) {
        if ( faces == null )
            faces = new ArrayList<>();
        faces.add( face );
    }
    
    public void novaListaArestas() {
        arestas = null;
    }
        
    public void novaListaFaces() {
        faces = null;
    }
                
    public void copiaParaVisao() {
        this.visaoP[0] = this.p[0];
        this.visaoP[1] = this.p[1];
        this.visaoP[2] = this.p[2];
    }
    
    public void copiaP0ParaP() {
        this.p[0] = this.p0[0];
        this.p[1] = this.p0[1];
        this.p[2] = this.p0[2];
    }
    
    public void setVisaoP( double x, double y, double z ) {
        this.visaoP[0] = x;
        this.visaoP[1] = y;
        this.visaoP[2] = z;
    }    
        
    public void setP( double x, double y, double z ) {
        this.p[0] = x;
        this.p[1] = y;
        this.p[2] = z;
    }
    
    public void setP0( double x0, double y0, double z0 ) {
        this.p0[0] = x0;
        this.p0[1] = y0;
        this.p0[2] = z0;
    }
    
    public double[] getP0() {
        return p0;
    }
    
    public void setP0(double[] p0) {
        this.p0 = p0;
    }
    
    public double[] getP() {
        return p;
    }
    
    public void setP(double[] p) {
        this.p = p;
    }
    
    public double[] getVisaoP() {
        return visaoP;
    }
    
    public void setVisaoP(double[] p) {
        this.visaoP = p;
    }

    public double getX() {
        return p[0];
    }

    public void setX(double x) {
        this.p[0] = x;
    }

    public double getY() {
        return p[1];
    }

    public void setY(double y) {
        this.p[1] = y;
    }

    public double getZ() {
        return p[2];
    }

    public void setZ(double z) {
        this.p[2]= z;
    }
    
    public double getX0() {
        return p0[0];
    }

    public void setX0(double x0) {
        this.p0[0] = x0;
    }

    public double getY0() {
        return p0[1];
    }

    public void setY0(double y0) {
        this.p0[1] = y0;
    }

    public double getZ0() {
        return p0[2];
    }

    public void setZ0(double z0) {
        this.p0[2]= z0;
    }

    public double getVisaoX() {
        return visaoP[0];
    }

    public void setVisaoX(double x) {
        this.visaoP[0] = x;
    }

    public double getVisaoY() {
        return visaoP[1];
    }

    public void setVisaoY(double y) {
        this.visaoP[1] = y;
    }

    public double getVisaoZ() {
        return visaoP[2];
    }

    public void setVisaoZ(double z) {
        this.visaoP[2] = z;
    }

    public List<Face3D> getFaces() {
        return faces;
    }

    public List<Aresta3D> getArestas() {
        return arestas;
    }

    public VerticeRaio3D getVerticeRaio3D() {
        return raio3D;
    }

    public void setVerticeRaio3D(VerticeRaio3D raio3D) {
        this.raio3D = raio3D;
    }

    
}
