package tdrpg.luo.faultyrobot.GameObject.StaticObject;

import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Item.Item;
import tdrpg.luo.faultyrobot.GameObject.StaticObject.Obstruction.Obstruction;

/**
 * Created by Luo on 2016-05-25.
 */
public abstract class StaticObject extends GameObject {
    public boolean pickable, animated;
    public StaticObject(){
        super();
        pickable = false;
        animated = false;
    }
    @Override
    public void destroy(){
        if(this instanceof Item){
            Item.purgeList.add((Item)this);
        }else if(this instanceof Obstruction){
            Obstruction.purgeList.add((Obstruction)this);
        }
        GameObject.goPurgeList.add(this);
    }

    @Override
    public void update(){

    }

    public static void reset(){
        Item.items.clear();
        Item.purgeList.clear();
        Obstruction.obstructions.clear();
        Obstruction.purgeList.clear();
    }
}
