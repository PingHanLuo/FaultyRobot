package tdrpg.luo.faultyrobot;

import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.GameObject.GameObject;

/**
 * Created by Luo on 2016-05-20.
 */
public class Player {
    public static int curWidth = 77, curHeight = 70;
    public static final int YMARGIN = 17, XMARGIN = 7,WIDTH = 77, HEIGHT = 70, X = GamePanel.WIDTH/2-WIDTH/2, Y = GamePanel.HEIGHT/2-HEIGHT/2;
    private int direction=-1,health;
    private ArrayList<GameObject> inventory = new ArrayList<GameObject>();
    private double speed=0.25,walkTimer;
    private static Player player = new Player();

    public static Player getInstance() {
        return player;
    }

    public static void addInventory(GameObject gameObject){
        player.inventory.add(gameObject);
    }
    public double getSpeed(){return this.speed;}
    public void setSpeed(double speed){this.speed=speed;}

    private Player() {
    }

    public void update(){
        switch (GamePanel.input){
            case 0:
                walkTimer+=speed;
                if(!(direction == GamePanel.input)){
                    direction = GamePanel.input;
                    walkTimer=0;
                }else if(walkTimer>5){
                    walkTimer = 0;
                }
                break;
            case 1:
                walkTimer+=speed;
                if(!(direction == GamePanel.input)){
                    direction = GamePanel.input;
                    walkTimer=0;
                }else if(walkTimer>5){
                    walkTimer = 0;
                }
                break;
            case 2:
                walkTimer+=speed;
                if(!(direction == GamePanel.input)){
                    direction=GamePanel.input;
                }else if(walkTimer>5){
                    walkTimer = 0;
                }
                break;
            case 3:
                walkTimer+=speed;
                if(!(direction == GamePanel.input)){
                    direction = GamePanel.input;
                    walkTimer=0;
                }else if(walkTimer>5){
                    walkTimer=0;
                }
                break;
            default:
                return;
        }
    }


    public void draw(Canvas canvas, Context context){
        Bitmap bmp = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.scaledrobot),(XMARGIN+((int)walkTimer*(WIDTH+XMARGIN))),YMARGIN,curWidth,curHeight);
        Bitmap refinedBitmap = GameManager.refineBitmap(bmp,direction,WIDTH,HEIGHT);
        canvas.drawBitmap(refinedBitmap,X,Y,null);
    }
    public void reset(){
        this.walkTimer = 0;
        this.direction = -1;
    }
}
