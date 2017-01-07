package tdrpg.luo.faultyrobot.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import tdrpg.luo.faultyrobot.Background;
import tdrpg.luo.faultyrobot.CollisionDetector;
import tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject.AliveObject;
import tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject.Hoodlum;
import tdrpg.luo.faultyrobot.GameObject.DynamicObject.DynamicObject;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Item.Item;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Obstruction.Obstruction;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.StaticObject;
import tdrpg.luo.faultyrobot.GamePanel;
import tdrpg.luo.faultyrobot.Player;

/**
 * Created by Luo on 2016-05-25.
 */
public abstract class GameObject {
    public static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    public static ArrayList<GameObject> goPurgeList = new ArrayList<GameObject>();
    protected int width,height;
    protected double x,y,speed;
    protected boolean isSquare;
    protected Bitmap sprite;

    public double getX(){return x;}
    public double getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public double getSpeed(){return speed;}
    public boolean getIsSquare(){return isSquare;}

    public GameObject(){
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height=0;
        this.speed = 0;
        this.isSquare=true;
        this.sprite=null;
        gameObjects.add(this);
    }

    public GameObject(double x, double y, double speed, int width, int height, boolean isSquare){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height=height;
        this.speed = speed;
        this.isSquare = isSquare;
        this.sprite=null;
        gameObjects.add(this);
    }

    public GameObject(double x, double y, double speed, int width, int height, boolean isSquare, Bitmap sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height=height;
        this.speed = speed;
        this.isSquare = isSquare;
        this.sprite=sprite;
        gameObjects.add(this);
    }

    public void destroy(){
        if(this instanceof DynamicObject){
            ((DynamicObject)this).destroy();
        }else if(this instanceof StaticObject){
            ((StaticObject)this).destroy();
        }
        GameObject.goPurgeList.add(this);
    }

    public static void adjustAll(){
        for(GameObject gameObject:gameObjects){
            gameObject.adjust();
        }
    }
    public void update(){
        if(this instanceof DynamicObject){
            ((DynamicObject)this).update();
        }else{
            ((StaticObject)this).update();
        }
    }
    public void adjust(){
        switch (GamePanel.input){
            case 0:
                y += Background.currentBackground.getYVector();
                break;
            case 1:
                x -= Background.currentBackground.getXVector();
                break;
            case 2:
                y -= Background.currentBackground.getYVector();
                break;
            case 3:
                x += Background.currentBackground.getXVector();
                break;
        }
    }

    public void draw(Canvas canvas, Context context) {
        if (this instanceof AliveObject) {
            ((AliveObject) this).draw(canvas, context);
        }else{
            canvas.drawBitmap(sprite,((int)x)+width/2,((int)y)+height/2,null);
        }
    }

    public static void despawn(){
        for (Iterator<GameObject> iterator = GameObject.gameObjects.iterator(); iterator.hasNext();) {
            GameObject gameObject = iterator.next();
            if (gameObject != null) {
                if((Math.abs(gameObject.x - Player.X)>GamePanel.WIDTH*GamePanel.Zoom)||(Math.abs(gameObject.y - Player.Y)>GamePanel.HEIGHT*GamePanel.Zoom)){
                    gameObject.destroy();
                }
            }
        }
    }
    private int randomize(int length){
        int randomNumber = (int) (Math.random()*2);
        switch (randomNumber){
            case 0: return (int) ((Math.random()*length/4) - length);
            case 1: return (int) ((Math.random()*length/4) + length);
            default: return 0;
        }
    }
    public boolean spawnLocation(){
        int rX=0, rY=0, tries = 0;
        boolean found = false;
        while(!found || tries > 10){
            tries++;
            found = true;
            rX = randomize(GamePanel.WIDTH);
            rY = randomize(GamePanel.HEIGHT);
            synchronized (GameObject.gameObjects) {
                for (GameObject gameObject : gameObjects) {
                    if (CollisionDetector.collided(this.isSquare, rX, rY, width, height, gameObject.x, gameObject.y, gameObject.width, gameObject.height, gameObject.isSquare)) {
                        found = false;
                        break;
                    }
                }
            }
        }
        if(tries>10){
            Log.i("Failed","Spawn Location failed");
            return false;
        }else{
            x = rX;
            y = rY;
            return true;
        }
    }
    public boolean collided(GamePanel gamePanel) {
        if(this instanceof StaticObject){
            if(((StaticObject) this).pickable){
                this.destroy();
                Player.addInventory(this);
                return false;
            }else if(this instanceof Obstruction){
                return this.collided(gamePanel);
            }
        }else if(this instanceof DynamicObject){
            if(this instanceof AliveObject){
                return this.collided(gamePanel);
            }
        }
        return false;
    }
    public boolean checkCollsions(){
        synchronized (GameObject.gameObjects) {
            for (GameObject gameObject : gameObjects) {
                if (CollisionDetector.collided(gameObject.isSquare, gameObject.getX(), gameObject.getY(), gameObject.width, gameObject.height, this.x, this.y, this.width, this.height, this.isSquare) && (this != gameObject)) {
                    Log.i("Collided","");
                    if (gameObject instanceof Item) {
                        gameObject.destroy();
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void reset(){
        gameObjects.clear();
        goPurgeList.clear();
        DynamicObject.reset();
        StaticObject.reset();
    }
}
