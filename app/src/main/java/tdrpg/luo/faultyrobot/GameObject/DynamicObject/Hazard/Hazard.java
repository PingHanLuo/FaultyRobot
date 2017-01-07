package tdrpg.luo.faultyrobot.GameObject.DynamicObject.Hazard;

import android.graphics.Bitmap;

import tdrpg.luo.faultyrobot.GameObject.DynamicObject.DynamicObject;
import tdrpg.luo.faultyrobot.Player;

/**
 * Created by Luo on 2016-05-31.
 */
public class Hazard extends DynamicObject {
    public Hazard(){
        super();
    }
    public Hazard(int moveSpeed, int width, int height, boolean isSquare, Bitmap sprite, boolean coordinated, int xRange, int yRange){
        super();
        this.xMoveSpeed = moveSpeed;
        this.yMoveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.isSquare = isSquare;
        this.sprite = sprite;
        this.coordinated = coordinated;
        this.xDisplacement = 0;
        this.yDisplacement = 0;
        this.xRange = xRange;
        this.yRange = yRange;
    }
    public void update(){
        int sign = 0;
        if(coordinated){
            if(!(Math.abs(xDisplacement) < xRange)){
                xMoveSpeed = -xMoveSpeed;
            }
            if(!(this.checkCollsions())) {
                xDisplacement += xMoveSpeed;
                x += xMoveSpeed;
            }
            if(!(Math.abs(yDisplacement) < yRange)){
                yMoveSpeed = -yMoveSpeed;
            }
            if(!(this.checkCollsions())) {
                yDisplacement += yMoveSpeed;
                y += yMoveSpeed;
            }
        }else{
            //randomly moves it
            switch ((int)(Math.random()*2)){
                case 0:
                    x += xMoveSpeed;
                    sign = 1;
                    break;
                case 1:
                    x -= xMoveSpeed;
                    sign = -1;
            }
            //checks if collision occurred. if it did undo the recent move
            if(this.checkCollsions()){
                x += sign * xMoveSpeed;
            }else{
                //fix the displacement info too
                xDisplacement += xMoveSpeed;
            }
            sign = 0;
            switch ((int)(Math.random()*2)){
                case 0:
                    y += yMoveSpeed;
                    sign = 1;
                    break;
                case 1:
                    y -= yMoveSpeed;
                    sign = -1;
            }
            if(this.checkCollsions()){
                y += sign * yMoveSpeed;
            }else{
                yDisplacement += yMoveSpeed;
            }
        }
    }
}
