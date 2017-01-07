package tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import tdrpg.luo.faultyrobot.CollisionDetector;
import tdrpg.luo.faultyrobot.GameManager;
import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GamePanel;
import tdrpg.luo.faultyrobot.Player;
import tdrpg.luo.faultyrobot.R;

/**
 * Created by Luo on 2016-05-31.
 */
public class Hoodlum extends AliveObject {
    public static int totalCount = 0, eCount =0,nCount = 0,hCount=0;
    private static final int XMARGIN = 3, YMARGIN = 7;
    public static final int WIDTH = 26, HEIGHT = 22;
    private int difficulty;
    public Hoodlum(){
        super();
    }
    public Hoodlum(double moveSpeed, int width, int height, double speed, boolean isSquare, Bitmap sprite, boolean coordinated, int xRange, int yRange, int detectionRange, int difficulty){
        super();
        this.xMoveSpeed = moveSpeed;
        this.yMoveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isSquare = isSquare;
        this.sprite = sprite;
        this.coordinated = coordinated;
        this.xDisplacement = 0;
        this.yDisplacement = 0;
        this.xRange = xRange;
        this.yRange = yRange;
        this.detectionRange = detectionRange;
        this.difficulty = difficulty;
    }

    public void destroy(){
        switch (this.difficulty){
            case 0:
                eCount--;
                break;
            case 1:
                nCount--;
                break;
            case 2:
                hCount--;
                break;
        }
        totalCount--;
        AliveObject.purgeList.add(this);
        GameObject.goPurgeList.add(this);
    }
    public void update(){
        if(coordinated){
            double distance = Math.sqrt(Math.pow((this.x - Player.X),2) + Math.pow((this.y - Player.Y),2));
            if(distance > detectionRange){
                if(xRange >0) {
                    if (!(Math.abs(xDisplacement) < xRange)) {
                        xMoveSpeed = -xMoveSpeed;
                    }
                    if (!(this.checkCollsions())) {
                        xDisplacement += xMoveSpeed;
                        x += xMoveSpeed;
                        if (xMoveSpeed > 0) {
                            direction = 2;
                        } else {
                            direction = 1;
                        }
                        walkTimer += speed;
                    }
                }
                if(yRange>0) {
                    if (!(Math.abs(yDisplacement) < yRange)) {
                        yMoveSpeed = -yMoveSpeed;
                    }
                    if (!(this.checkCollsions())) {
                        yDisplacement += yMoveSpeed;
                        y += yMoveSpeed;
                        if (yMoveSpeed > 0) {
                            direction = 0;
                        } else {
                            direction = 3;
                        }
                        walkTimer += speed;
                    }
                }
                if(walkTimer > 13){
                    walkTimer %= 14;
                }
            }else{
                hunt();
                xDisplacement = 0;
                yDisplacement = 0;
            }
        }else{
            hunt();
            xDisplacement = 0;
            yDisplacement = 0;
        }
    }

    public boolean huntX(){
        if(this.x > (Player.X + Player.WIDTH)){
            x -= Math.abs(xMoveSpeed);
            direction = 2;
            if(this.checkCollsions()){
                x += Math.abs(xMoveSpeed);
            }else{
                return true;
            }
        }else if((this.x + this.width) < Player.X){
            x += Math.abs(xMoveSpeed);
            direction = 0;
            if(this.checkCollsions()){
                x -= Math.abs(xMoveSpeed);
            }else{
                return true;
            }
        }
        return false;
    }
    public boolean huntY(){
        if(this.y > (Player.Y + Player.HEIGHT)){
            y -= Math.abs(yMoveSpeed);
            direction = 3;
            if(this.checkCollsions()){
                y += Math.abs(yMoveSpeed);
            }else {
                return true;
            }
        }else if((this.y + this.height) < Player.Y){
            direction = 1;
            y += Math.abs(yMoveSpeed);
            if(this.checkCollsions()){
                y -= Math.abs(yMoveSpeed);
            }else {
                return true;
            }
        }
        return false;
    }
    public void hunt(){
        if(!huntX()){
           huntY();
        }
        walkTimer += speed;
        if(walkTimer > 13){
            walkTimer %= 14;
        }
    }
    public void draw(Canvas canvas, Context context){
        Bitmap bmp = Bitmap.createBitmap(sprite,(XMARGIN+((int)walkTimer*(WIDTH+(XMARGIN*2)))),YMARGIN,WIDTH,HEIGHT);
        Bitmap refinedBitmap = GameManager.refineBitmap(bmp,direction,width,height);
        canvas.drawBitmap(refinedBitmap,(int)x,(int)y,null);
        //canvas.drawBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.earthwizard),((int)walkTimer)*WIDTH,direction * HEIGHT,WIDTH,HEIGHT),(int)x, (int)y,null);

    }
}
