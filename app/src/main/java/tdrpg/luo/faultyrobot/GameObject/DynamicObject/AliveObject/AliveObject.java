package tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.GameObject.DynamicObject.DynamicObject;
import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GamePanel;
import tdrpg.luo.faultyrobot.R;

/**
 * Created by Luo on 2016-05-25.
 */
public class AliveObject extends DynamicObject{
    public static ArrayList<AliveObject> aliveObjects = new ArrayList<AliveObject>();
    public static ArrayList<AliveObject> purgeList = new ArrayList<AliveObject>();
    protected boolean aggressive;
    protected int detectionRange;
    public boolean getAggressive(){return aggressive;}
    public int getDetectionRange(){return detectionRange;}
    public AliveObject(){
        super();
        aggressive = true;
        detectionRange = 0;
        aliveObjects.add(this);
    }
    @Override
    public boolean collided(GamePanel gamePanel){
        if(this instanceof Hoodlum){
            gamePanel.end();
            return true;
        }else{
            return false;
        }
    }

    public void destroy(){
        if (this instanceof Hoodlum){
            ((Hoodlum)this).destroy();
        }
        purgeList.add(this);
        GameObject.goPurgeList.add(this);
    }

    @Override
    public void update() {
        if(this instanceof Hoodlum){
            this.update();
        }
    }
    @Override
    public void draw(Canvas canvas, Context context){
        if(this instanceof Hoodlum) {
            this.draw(canvas, context);
        }else {
            canvas.drawBitmap(sprite, (int) x + width / 2, (int) y + height / 2, null);
        }
    }

    public static void spawn(Context context){
        Hoodlum hoodlum;
        switch ((int)(Math.random()*3)){
            case 0:
                Bitmap easyHunter = BitmapFactory.decodeResource(context.getResources(), R.drawable.hblue);
                hoodlum = new Hoodlum(0.1,52,44, 0.10,true,easyHunter,true,200,0,200,0);
                hoodlum.spawnLocation();
                Hoodlum.eCount++;
                break;
            case 1:
                Bitmap normalHunter = BitmapFactory.decodeResource(context.getResources(), R.drawable.hyellow);
                hoodlum = new Hoodlum(0.5,52,44, 0.10,true,normalHunter,true,200,0,500,1);
                hoodlum.spawnLocation();
                Hoodlum.nCount++;
                break;
            case 2:
                Bitmap hardHunter = BitmapFactory.decodeResource(context.getResources(), R.drawable.hred);
                hoodlum = new Hoodlum(1,52,44, 0.10,true,hardHunter,true,200,0,1000,2);
                hoodlum.spawnLocation();
                Hoodlum.hCount++;
                break;
        }
        Hoodlum.totalCount++;
    }
    public static void reset(){
        aliveObjects.clear();
    }
}
