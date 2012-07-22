public final class Vec3D {
    public double x,y,z;
    public double w;
    public Vec3D(double x,double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.w=1;//Unused for now
    }


    /**
    *Get vector magnitude
    */
    public double mag() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public void mult(double f) {
        x*=f;
        y*=f;
        z*=f;
    }
    /**
    *Normalize vector coordinates
    */
    public void norm() {
        double mag=mag();
        x/=mag;
        y/=mag;
        z/=mag;
    }
    public void neg() {
        x=-x;
        y=-y;
        z=-z;
    }
    public void zero() {
        x=0;
        y=0;
        z=0;
    }
    public void add(Vec3D b) {
        x+=b.x;
        y+=b.y;
        z+=b.z;
    }
    public void sub(Vec3D b) {
        x-=b.x;
        y-=b.y;
        z-=b.z;
    }
    public void div(double n) {
        x/=n;
        y/=n;
        z/=n;
    }
    public double dot(Vec3D b) {
        return x*b.x+y*b.y+z*b.z;
    }

    public Vec3D cross(Vec3D b) {
        return new Vec3D(this.y * b.z - b.y * this.z,
                         this.z * b.x - b.z * this.x,
                         this.x * b.y - b.x * this.y);
    }

    public void limit(double max) {
        double m=mag();
        if(m>max) {
            norm();
            mul(max);
        }
    }
    /**
    *Returns heading angle (to be used in 2D)
    */
    public double headingAngle2D() {
        double angle=Math.atan2(-y,x);
        return angle*-1;
    }

    /**
    *Returns angle between x-axis and a line from origin to the point
    */
    public double azimuth() {
        return Math.atan2(y, x);
    }
    /**
    *Returns angle between the x-y plane and the line from the origin to the point
    */
    public double elevation() {
        return Math.atan2(z, Math.sqrt(x*x+y*y));
    }


    /*Static Methods*/


    public static double distance(Vec3D a, Vec3D b) {
        double temp = Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2) + Math.pow(b.z - a.z, 2);
        return (Math.sqrt(temp));

    }
    public static Vec3D add(Vec3D a,Vec3D b) {
        Vec3D ret=new Vec3D(0,0,0);
        ret.x=b.x+a.x;
        ret.y=b.y+a.y;
        ret.z=b.z+a.z;
        return ret;
    }
    public static Vec3D sub(Vec3D a,Vec3D b) {
        Vec3D ret=new Vec3D(0,0,0);
        ret.x=b.x-a.x;
        ret.y=b.y-a.y;
        ret.z=b.z-a.z;
        return ret;
    }

    public static Vec3D cross(Vec3D a, Vec3D b) {
        Vec3D temp = new Vec3D(0,0,0);
        temp.x = a.y * b.z - b.y * a.z;
        temp.y = a.z * b.x - b.z * a.x;
        temp.z = a.x * b.y - b.x * a.y;
        return temp;
    }
    public static Vec3D neg(Vec3D a) {
        return new Vec3D(-a.x,-a.y,-a.z);
    }

    public float[][] makeMat3(Vec3D v, Vec3D u, Vec3D z)
    {
        float temp[][] = new float[3][3];
        temp[0][0] = (float) v.x;
        temp[0][1] = (float) v.y;
        temp[0][2] = (float) v.z;

        temp[1][0] = (float) u.x;
        temp[1][1] = (float) u.y;
        temp[1][2] = (float) u.z;

        temp[2][0] = (float) z.x;
        temp[2][1] = (float) z.y;
        temp[2][2] = (float) z.z;

        return temp;
    }

    /*Getters and Setters*/
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public void set(double nx,double ny,double nz) {
        x=nx;
        y=ny;
        z=nz;
    }
}