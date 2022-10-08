package italo.iplot.plot3d.g3d;

public class CuboObjeto3D extends ParalelepipedoObjeto3D {
        
    public double getLado() {
        return super.getDX();
    }

    public void setLado(double lado) {
        super.setDX( lado );
        super.setDY( lado );
        super.setDZ( lado ); 
    }
            
}
