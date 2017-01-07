package tdrpg.luo.faultyrobot.GameObject.StaticObject.Item;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.GameObject.StaticObject.StaticObject;

/**
 * Created by Luo on 2016-06-01.
 */
public class Item extends StaticObject {
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static ArrayList<Item> purgeList = new ArrayList<Item>();
    private boolean usable;

    public boolean getUsable(){return usable;}
    public Item(){
        super();
        usable = false;
        items.add(this);
    }
    public Item(int width, int height, boolean isSquare, Bitmap sprite,boolean animated, boolean usable){
        super();
        pickable = true;
        this.width = width;
        this.height=height;
        this.isSquare = isSquare;
        this.sprite=sprite;
        this.animated = animated;
        this.usable = usable;
        items.add(this);
    }



    public static void spawn(Context context){
        switch ((int)(Math.random()*1)){
            case 0:
                //spawn a collectible
                Collectible.spawn(context);
                break;
            default: break;
        }
    }

}
