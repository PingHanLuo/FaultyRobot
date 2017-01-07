package tdrpg.luo.faultyrobot.GameObject.DynamicObject;

import tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject.AliveObject;
import tdrpg.luo.faultyrobot.GameObject.DynamicObject.AliveObject.Hoodlum;
import tdrpg.luo.faultyrobot.GameObject.DynamicObject.Hazard.Hazard;
import tdrpg.luo.faultyrobot.GameObject.GameObject;
import tdrpg.luo.faultyrobot.GamePanel;

/**
 * Created by Luo on 2016-05-25.
 */
public abstract class DynamicObject extends GameObject {
    protected double xMoveSpeed, yMoveSpeed, walkTimer;
    protected boolean coordinated;
    protected int xDisplacement, yDisplacement, xRange, yRange, direction;
    public int getxDisplacement(){return xDisplacement;}
    public int getyDisplacement(){return yDisplacement;}
    public DynamicObject(){
        super();
        xMoveSpeed=0;
        yMoveSpeed=0;
        xDisplacement = 0;
        yDisplacement = 0;
        xRange = 0;
        yRange = 0;
        walkTimer = 0;
        direction = 0;
        coordinated = false;
    }

    @Override
    public boolean collided(GamePanel gamePanel){
        if(this instanceof AliveObject){
            return this.collided(gamePanel);
        }
        return false;
    }

    public void destroy(){
        if(this instanceof  AliveObject){
            ((AliveObject)this).destroy();
        }
        GameObject.goPurgeList.add(this);
    }

    public void update(){
        if(this instanceof AliveObject){
            this.update();
        }else if(this instanceof Hazard){
            this.update();
        }else{

        }
    }

    public static void reset(){
        AliveObject.reset();
        Hoodlum.eCount = 0;
        Hoodlum.nCount = 0;
        Hoodlum.hCount = 0;
        Hoodlum.totalCount = 0;
    }

}
