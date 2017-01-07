package tdrpg.luo.faultyrobot.GameObject.StaticObject.Obstruction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.Background;
import tdrpg.luo.faultyrobot.Effect;
import tdrpg.luo.faultyrobot.GameManager;
import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.StaticObject;
import tdrpg.luo.faultyrobot.GamePanel;
import tdrpg.luo.faultyrobot.Player;
import tdrpg.luo.faultyrobot.R;

/**
 * Created by Luo on 2016-05-31.
 */
public class Obstruction extends StaticObject {
    public static final int WIDTH = 32, HEIGHT = 32;
    public static ArrayList<Obstruction> obstructions = new ArrayList<Obstruction>();
    public static ArrayList<Obstruction> purgeList = new ArrayList<Obstruction>();
    private boolean fatal;
    public Obstruction(){
        super();
        pickable = false;
        obstructions.add(this);
    }
    public Obstruction(int width, int height, boolean isSquare, Bitmap sprite, boolean fatal){
        super();
        pickable = false;
        this.width = width;
        this.height=height;
        this.isSquare = isSquare;
        this.sprite=sprite;
        this.animated = false;
        this.fatal = fatal;
        obstructions.add(this);
    }
    @Override
    public boolean collided(GamePanel gamePanel){
        if(fatal){
            gamePanel.end();
            return true;
        }else{
            obstructions.remove(this);
            this.destroy();
            //Apply slow effect
            Effect effect1 = new Effect(1,"stun",0);
            Effect effect2 = new Effect(5,"slow",1);
            effect1.applyEffect();
            effect2.applyEffect();
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas, Context context){
        canvas.drawBitmap(sprite, (int)x, (int)y,null);
    }
    public static void spawn(Context context){
        Bitmap bmp = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.japanesevillage),32,32,WIDTH,HEIGHT);
        Obstruction obstruction = new Obstruction(64,64,true,GameManager.refineBitmap(bmp,1,64,64), false);
        obstruction.spawnLocation();
    }
}
