package tdrpg.luo.faultyrobot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.system.Os;
import android.util.Log;

import tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject.AliveObject;
import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Item.Item;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Obstruction.Obstruction;

/**
 * Created by Luo on 2016-06-05.
 */
public class GameManager {
    public static final int ALIVE_LIMIT = 10, HAZARD_LIMIT = 0, OBSTRUCTION_LIMIT = 10, ITEM_LIMIT = 10;
    public static final long SPAWN_INTERVAL = 5000000;
    public static long prevNanoTime;
    private GamePanel gamePanel;
    private static GameManager manager = new GameManager();

    public static GameManager getInstance() {
        return manager;
    }

    private GameManager() {
        prevNanoTime = System.nanoTime();
    }

    public void update(Context context){
        if(((System.nanoTime() - prevNanoTime)/1000)>SPAWN_INTERVAL){
            if(AliveObject.aliveObjects.size()<ALIVE_LIMIT){
                AliveObject.spawn(context);
            }
            if(Obstruction.obstructions.size()<OBSTRUCTION_LIMIT){
                Obstruction.spawn(context);
            }
            if(Item.items.size()<ITEM_LIMIT){
                Item.spawn(context);
            }
            prevNanoTime = System.nanoTime();
        }
    }

    public static Bitmap refineBitmap(Bitmap bmp, int direction,int width,int height){
        Matrix matrix = new Matrix();
        matrix.postRotate((direction-1) * 90);
        Bitmap refinedBitmap = Bitmap.createBitmap(bmp, 0, 0,
                bmp.getWidth(),bmp.getHeight(), matrix, true);
        return Bitmap.createScaledBitmap(refinedBitmap,width,height,true);
    }
    public static void purge(){
        synchronized (GameObject.gameObjects){
            for(GameObject gameObject : GameObject.goPurgeList){
                GameObject.gameObjects.remove(gameObject);
            }
            GameObject.goPurgeList.clear();
        }
        synchronized (AliveObject.aliveObjects){
            for (AliveObject aliveObject : AliveObject.purgeList){
                AliveObject.aliveObjects.remove(aliveObject);
            }
            AliveObject.purgeList.clear();
        }
        synchronized (Obstruction.obstructions){
            for (Obstruction obstruction :Obstruction.purgeList){
                Obstruction.obstructions.remove(obstruction);
            }
            Obstruction.purgeList.clear();
        }
        synchronized (Item.items){
            for (Item item : Item.purgeList){
                Item.items.remove(item);
            }
            Item.purgeList.clear();
        }
    }
}
