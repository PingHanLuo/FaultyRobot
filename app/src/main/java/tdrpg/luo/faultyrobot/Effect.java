package tdrpg.luo.faultyrobot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Luo on 2016-06-13.
 */
public class Effect{
    public static ArrayList<Effect> effects = new ArrayList<Effect>();
    public static boolean stunned = false;
    public static boolean random = false;
    public static Timer timer = new Timer();
    private double duration, amount;
    private String type;
    public Effect(){
        this.duration = 0;
        this.type = "";
        this.amount = 0;
    }
    public Effect(int duration, String type, double amount){
        this.duration = duration;
        this.type = type;
        this.amount = amount;
        effects.add(this);
    }

    public void applyEffect(){
        switch (type){
            case "slow":
                slow();
                break;
            case "slowX":
                slowX();
                break;
            case "slowY":
                slowY();
                break;
            case "stun":
                stun();
                break;
            case "randomize":
                randomize();
                break;
        }
    }
    private void slow(){
        Background.currentBackground.setVector((Background.currentBackground.getXVector() - this.amount),(Background.currentBackground.getYVector() - this.amount));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Background.currentBackground.setVector((Background.currentBackground.getXVector() + amount),(Background.currentBackground.getYVector() + amount));
                effects.remove(Effect.this);
                this.cancel();
            }
        },((long)duration*1000));
    }
    private void slowX(){
        Background.currentBackground.setXVector(Background.currentBackground.getXVector() - this.amount);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Background.currentBackground.setXVector(Background.currentBackground.getXVector() + amount);
                effects.remove(Effect.this);
                this.cancel();
            }
        },((long)duration*1000));
    }
    private void slowY(){
        Background.currentBackground.setYVector(Background.currentBackground.getYVector() - this.amount);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Background.currentBackground.setYVector(Background.currentBackground.getYVector() + amount);
                effects.remove(Effect.this);
                this.cancel();
            }
        },((long)duration*1000));
    }
    private void stun(){
        stunned = true;
        this.amount = Player.getInstance().getSpeed();
        Player.getInstance().setSpeed(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stunned = false;
                Player.getInstance().setSpeed(amount);
                for(Effect effect : effects){
                    if((!(effect == Effect.this))&&(effect.type.equals("stun"))){
                        stunned = true;
                        effect.amount = Effect.this.amount;
                        Player.getInstance().setSpeed(0);
                    }
                }
                effects.remove(Effect.this);
                this.cancel();
            }
        },((long)duration*1000));
    }
    private void randomize(){
        random = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                random = false;
                for(Effect effect : effects){
                    if((!(effect == Effect.this))&&(effect.type.equals("randomize"))){
                        random = true;
                    }
                }
                effects.remove(Effect.this);
                this.cancel();
            }
        },((long)duration*1000));
    }
    public static void reset(){
        timer.cancel();
        effects.clear();
        stunned = false;
        random = false;
    }
}
