package tdrpg.luo.faultyrobot.GameObject.DynamicObject.Hazard;

import android.graphics.Bitmap;
import android.graphics.MaskFilter;

import tdrpg.luo.faultyrobot.Player;

/**
 * Created by Luo on 2016-06-15.
 */
public class Missle extends Hazard {
    private int moveSpeed;
    private boolean calibrated;
    public Missle(){
        super();
    }
    public Missle(int moveSpeed, int width, int height, boolean isSquare, Bitmap sprite){
        super();
        this.calibrated = false;
        this.coordinated = true;
        this.sprite = sprite;
        this.moveSpeed = moveSpeed;
    }
    public void update(){
        if(!calibrated){
            calibrate();
        }else{
            move();
        }
    }
    private void move(){
        x += xMoveSpeed;
        y += yMoveSpeed;
        if(this.checkCollsions()){
            this.destroy();
        }
    }
    private void calibrate(){
        int xDifference, yDifference;
        double angle;
        xDifference = (int)(Player.X + (Player.WIDTH/2) - (this.x + (this.width/2)));
        yDifference = (int)(Player.Y + (Player.HEIGHT/2) - (this.y + (this.height/2)));
        if((this.x + (this.width/2))> (Player.X + (Player.WIDTH/2))){
            xDifference *= -1;
        }
        if((this.y + (this.height/2))> (Player.Y + (Player.HEIGHT/2))){
            yDifference *= -1;
        }
        angle = Math.atan(yDifference/xDifference);
        xMoveSpeed = Math.abs(moveSpeed*(Math.cos(angle)));
        yMoveSpeed = Math.abs(moveSpeed*(Math.sin(angle)));
        if(xDifference < 0){
            xMoveSpeed *= -1;
        }if(yDifference < 0){
            yMoveSpeed *= -1;
        }
    }
}
