package italo.iplot.plot3d.g3d.util;

import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Vertice3D;
import java.util.List;
import italo.iplot.plot3d.g3d.vert.FiltroVert3D;

public class Transformador3D {
    
    private double D = 3d;
                       
    public void rotX( Objeto3D obj, double angX, FiltroVert3D filtro ) {
        this.rot( obj, angX, 0, 0, filtro ); 
    }
    
    public void rotY( Objeto3D obj, double angY, FiltroVert3D filtro ) {
        this.rot( obj, 0, angY, 0, filtro );
    }
    
    public void rotZ( Objeto3D obj, double angZ, FiltroVert3D filtro ) {
        this.rot( obj, 0, 0, angZ, filtro );
    }
    
    public void rotX( Vertice3D v, double angX, FiltroVert3D filtro ) {
        this.rot( v, angX, 0, 0, filtro ); 
    }
    
    public void rotY( Vertice3D v, double angY, FiltroVert3D filtro ) {
        this.rot( v, 0, angY, 0, filtro );
    }
    
    public void rotZ( Vertice3D v, double angZ, FiltroVert3D filtro ) {
        this.rot( v, 0, 0, angZ, filtro );
    }
        
    public void rot( Objeto3D obj, double angX, double angY, double angZ, FiltroVert3D filtro ) {        
        obj.getObjetos().forEach( o -> this.rot( o, angX, angY, angZ, filtro ) );         
        
        List<Vertice3D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.rot( v, angX, angY, angZ, filtro ) );       
    }
    
    public void rot( List<Vertice3D> vertices, double angX, double angY, double angZ, FiltroVert3D filtro ) {
        vertices.forEach( v -> this.rot( v, angX, angY, angZ, filtro ) ); 
    }
    
    public void rot( Vertice3D v, double angX, double angY, double angZ, FiltroVert3D filtro ) {
        this.rot( filtro.getPonto3D( v ), angX, angY, angZ );
    }

    public void rotX( double[] vertice, double angX ) {
        this.rot( vertice, angX, 0, 0 );
    }
    
    public void rotY( double[] vertice, double angY ) {
        this.rot( vertice, 0, angY, 0 );
    }
    
    public void rotZ( double[] vertice, double angZ ) {
        this.rot( vertice, 0, 0, angZ );
    }
    
    public void rot( double[] vertice, double angX, double angY, double angZ ) {
        double x1 = vertice[0];
        double y1 = vertice[1];
        double z1 = vertice[2];
        
        double senAngX = Math.sin( angX );
        double cosAngX = Math.cos( angX );
        double senAngY = Math.sin( angY );
        double cosAngY = Math.cos( angY );
        double senAngZ = Math.sin( angZ );
        double cosAngZ = Math.cos( angZ );
        
        double x2, y2, z2;
                
        y2 = ( y1 * cosAngX ) + ( z1 * senAngX );
        z2 = ( z1 * cosAngX ) - ( y1 * senAngX );
        
        z1 = z2;
        y1 = y2;
        
        x2 = ( x1 * cosAngY ) + ( z1 * senAngY );
        z2 = ( z1 * cosAngY ) - ( x1 * senAngY );
        
        z1 = z2;
        x1 = x2;
        
        x2 = ( x1 * cosAngZ ) + ( y1 * senAngZ );
        y2 = ( y1 * cosAngZ ) - ( x1 * senAngZ );
        
        vertice[0] = x2;
        vertice[1] = y2;
        vertice[2] = z2;
    }
    
    public void transladaX( Objeto3D obj, double x, FiltroVert3D filtro ) {
        this.translada( obj, x, 0, 0, filtro );
    }
    
    public void transladaY( Objeto3D obj, double y, FiltroVert3D filtro ) {
        this.translada( obj, 0, y, 0, filtro );
    }
    
    public void transladaZ( Objeto3D obj, double z, FiltroVert3D filtro ) {
        this.translada( obj, 0, 0, z, filtro );
    }
    
    public void translada( Objeto3D obj, double x, double y, double z, FiltroVert3D filtro ) {
        obj.getObjetos().forEach( o -> this.translada( o, x, y, z, filtro ) );         
        
        List<Vertice3D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.translada( v, x, y, z, filtro ) ); 
    }
    
    public void translada( Vertice3D v, double x, double y, double z, FiltroVert3D filtro ) {
        filtro.setX( v, filtro.getX( v ) + x );
        filtro.setY( v, filtro.getY( v ) + y );
        filtro.setZ( v, filtro.getZ( v ) + z );
    }
    
    public void escala( Objeto3D obj, double escalar, FiltroVert3D filtro ) {
        this.escala( obj, escalar, escalar, escalar, filtro );
    }
    
    public void escala( Objeto3D obj, double sx, double sy, double sz, FiltroVert3D filtro ) {
        obj.getObjetos().forEach( o -> {                 
            this.escala( o, sx, sy, sz, filtro );                  
        } );                 
        
        List<Vertice3D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.escala( v, sx, sy, sz, filtro ) );       
    }
    
    public void escala( Vertice3D v, double sx, double sy, double sz, FiltroVert3D filtro ) {
        filtro.setX( v, filtro.getX( v ) * sx );
        filtro.setY( v, filtro.getY( v ) * sy ); 
        filtro.setZ( v, filtro.getZ( v ) * sz ); 
    }
    
    public double[] perspectivaParaOrtogonal( double[] v ) {
        double fproj = ( D + v[2] ) / D;
        if ( fproj != 0 ) {
            return new double[] {
                v[0] / fproj,
                v[1] / fproj,
                v[2] / fproj
            };
        }        
        return v.clone(); 
    }
    
    public void perspectiva( Objeto3D obj, FiltroVert3D filtro ) {                
        List<Vertice3D> vertices = obj.getEstrutura().getVertices();
        vertices.forEach( v -> this.perspectiva( v, filtro ) );
        
        obj.getObjetos().forEach( o -> {                 
            this.perspectiva( o, filtro );                   
        } );
    }
    
    public void perspectiva( Vertice3D v, FiltroVert3D filtro ) {
        double fproj = ( D + filtro.getZ( v ) ) / D;
        filtro.setX( v, filtro.getX( v ) * fproj );
        filtro.setY( v, filtro.getY( v ) * fproj ); 
        filtro.setZ( v, filtro.getZ( v ) * fproj );
    }
    
    public void perspectiva( double[] v ) {
        double fproj = ( D + v[2] ) / D;
        v[0] *= fproj;
        v[1] *= fproj;
        v[2] *= fproj;
    }
    
    public void soma( List<Vertice3D> vertices, double[] p0, FiltroVert3D filtro ) {
        vertices.forEach( v -> this.soma( v, p0, filtro ) ); 
    }
    
    public void soma( Vertice3D v, double[] p0, FiltroVert3D filtro ) {
        filtro.setX( v, filtro.getX( v ) + p0[0] );
        filtro.setY( v, filtro.getY( v ) + p0[1] );
        filtro.setZ( v, filtro.getZ( v ) + p0[2] );
    }         
    
    public void sub( List<Vertice3D> vertices, double[] p0, FiltroVert3D filtro ) {
        vertices.forEach( v -> this.sub( v, p0, filtro ) ); 
    }
    
    public void sub( Vertice3D v, double[] p0, FiltroVert3D filtro ) {
        filtro.setX( v, filtro.getX( v ) - p0[0] );
        filtro.setY( v, filtro.getY( v ) - p0[1] );
        filtro.setZ( v, filtro.getZ( v ) - p0[2] );
    }
    
    public double[] soma( double[] p, double[] p0, FiltroVert3D filtro ) {
        return new double[] {
            p[0] + p0[0],
            p[1] + p0[1],
            p[2] + p0[2]
        };
    }
    
    public double[] sub( double[] p, double[] p0, FiltroVert3D filtro ) {
        return new double[] {
            p[0] - p0[0],
            p[1] - p0[1],
            p[2] - p0[2]
        };
    }
    
}
