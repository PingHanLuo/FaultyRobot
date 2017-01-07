package tdrpg.luo.faultyrobot;

/**
 * Created by Luo on 2016-05-25.
 */
public class CollisionDetector {
    private boolean collided = false;
    private static CollisionDetector ourInstance = new CollisionDetector();
    public static boolean getCollided(){
        return ourInstance.collided;
    }
    private CollisionDetector() {
    }

    private static boolean rectCheck(double X, double length, double oX, double olength){
        if((((X+length) < (oX + olength)) && ((X+length) > (oX))) || (((X) < (oX + olength)) && ((X) > (oX)))){
            return true;
        }
        return false;
    }

    private static boolean circRectCheck(double X,double Y,double radius,double oX,double oY,double oWidth,double oHeight){
        if (Math.abs((oX + oWidth/2)-(X + radius))<= (oWidth/2 + radius)){
            if(Math.abs((oY + oHeight/2)-(Y + radius))<= (oHeight/2 + radius)){
                if ((Math.pow(((Y+radius)-(oY + oHeight/2)),2)+Math.pow(((X+radius)-(oX + oWidth/2)),2))>=radius){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean collided(boolean isSquare,double X,double Y,double width,double height,double oX,double oY,double oWidth,double oHeight, boolean oIsSquare){
        if(isSquare){
            if(oIsSquare){
                if(rectCheck(X,width,oX, oWidth)){
                    if(rectCheck(Y, height, oY, oHeight)){
                        return true;
                    }
                }
            }else{
                if(circRectCheck(oX, oY, oWidth/2, X, Y, width, height)){
                    return true;
                }
            }
        }else {
            if (oIsSquare) {
                if (circRectCheck(X, Y, width/2, oX, oY, oWidth, oHeight)) {
                    return true;
                }
            } else {
                if((Math.pow(Math.abs((oX+oWidth)-(X+width)),2)+Math.pow(Math.abs((oY+oWidth)-(Y+width)),2))<(oWidth+width)) {
                    return true;
                }
            }
        }
        return false;
    }
}
