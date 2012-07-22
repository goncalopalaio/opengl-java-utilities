public final class MiniMath {

    public MiniMath() {

    }

    public static double degrees(double radians) {
        return radians*180/Math.PI;
    }
    public static double radians(double degrees) {
        return degrees * Math.PI /180;
    }
    public static double genPi() {
        return 4*Math.atan(1);
    }
    public static double randRange(double max,double min) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }


}