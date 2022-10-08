package italo.iplot.plot2d.g2d.util;

import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;
import java.util.List;

public class Transformador2D {
      
    public void rot( Objeto2D obj, double ang, FiltroVert2D filtro ) {        
        obj.getObjetos().forEach( o -> this.rot( o, ang, filtro ) );         
        
        List<Vertice2D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.rot( v, ang, filtro ) );       
    }
    
    public void rot( List<Vertice2D> vertices, double ang, FiltroVert2D filtro ) {
        vertices.forEach( v -> this.rot( v, ang, filtro ) ); 
    }
        
    public void rot( Vertice2D v, double ang, FiltroVert2D filtro ) {
        double x1 = filtro.getX( v );
        double y1 = filtro.getY( v );
        
        double senAng = Math.sin( ang );
        double cosAng = Math.cos( ang );
                                        
        double x2 = ( x1 * cosAng ) + ( y1 * senAng );
        double y2 = ( y1 * cosAng ) - ( x1 * senAng );
                
        filtro.setX( v, x2 );
        filtro.setY( v, y2 );        
    }
    
    public void transladaX( Objeto2D obj, double x, FiltroVert2D filtro ) {
        this.translada( obj, x, 0, filtro );
    }
    
    public void transladaY( Objeto2D obj, double y, FiltroVert2D filtro ) {
        this.translada( obj, 0, y, filtro );
    }
        
    public void translada( Objeto2D obj, double x, double y, FiltroVert2D filtro ) {
        obj.getObjetos().forEach( o -> this.translada( o, x, y, filtro ) );         
        
        List<Vertice2D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.translada( v, x, y, filtro ) );         
    }
    
    public void translada( Vertice2D v, double x, double y, FiltroVert2D filtro ) {
        filtro.setX( v, filtro.getX( v ) + x );
        filtro.setY( v, filtro.getY( v ) + y );
    }
        
    public void escala( Objeto2D obj, double escalar, FiltroVert2D filtro ) {
        this.escala( obj, escalar, escalar, filtro );
    }
    
    public void escala( Objeto2D obj, double sx, double sy, FiltroVert2D filtro ) {
        obj.getObjetos().forEach( o -> {                 
            this.escala( o, sx, sy, filtro );                  
        } );                 
        
        List<Vertice2D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.escala( v, sx, sy, filtro ) );        
    }
    
    public void escala( Vertice2D v, double sx, double sy, FiltroVert2D filtro ) {
        filtro.setX( v, filtro.getX( v ) * sx );
        filtro.setY( v, filtro.getY( v ) * sy ); 
    }
    
    public void sub( List<Vertice2D> vertices, double x, double y, FiltroVert2D filtro ) {
        for( Vertice2D v : vertices ) {
            filtro.setX( v, filtro.getX( v ) - x );
            filtro.setY( v, filtro.getY( v ) - y );
        }        
    }
    
    public void soma( List<Vertice2D> vertices, double x, double y, FiltroVert2D filtro ) {
        for( Vertice2D v : vertices ) {
            filtro.setX( v, filtro.getX( v ) + x );
            filtro.setY( v, filtro.getY( v ) + y );
        }        
    }
    
}
