package tdrpg.luo.faultyrobot.GameObject.StaticObject.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.R;

/**
 * Created by Luo on 2016-06-01.
 */
public class Collectible extends Item {
    public static ArrayList<Collectible> collected = new ArrayList<Collectible>();
    private String setName;
    public Collectible(){
    }
    public Collectible(int width, int height, boolean isSquare, Bitmap sprite,String setName){
        super(width,height,isSquare,sprite,false,false);
        this.setName = setName;
    }


    public static void spawn(Context context){
        Collectible collectible;
        Bitmap bmp;
        switch ((int)(Math.random()*10)){
            case 0:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.essential_items),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Essential Items");
                collectible.spawnLocation();
                break;
            case 1:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mechanical_parts),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Mechanical Parts");
                break;
            case 2:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.original_designs),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Original Designs");
                collectible.spawnLocation();
                break;
            case 3:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.energy_source),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Energy Sources");
                collectible.spawnLocation();
                break;
            case 4:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.emotional),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Emotional Objects");
                collectible.spawnLocation();
                break;
            case 5:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.construction_materials),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Construction Materials");
                collectible.spawnLocation();
                break;
            case 6:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.construction_materials),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Futuristic Shielding");
                collectible.spawnLocation();
                break;
            case 7:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.construction_materials),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"Futuristic Weapons");
                collectible.spawnLocation();
                break;
            case 8:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.construction_materials),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"High Quality Equipment");
                collectible.spawnLocation();
                break;
            case 9:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.construction_materials),32,32,false);
                collectible = new Collectible(32,32,true,bmp,"AI fragment");
                collectible.spawnLocation();
                break;
        }

    }

}
