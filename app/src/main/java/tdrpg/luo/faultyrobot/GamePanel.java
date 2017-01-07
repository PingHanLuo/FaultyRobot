package tdrpg.luo.faultyrobot;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;

import tdrpg.luo.faultyrobot.GameObject.GameObject;

/**
 * Created by Luo on 2016-05-13.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int Zoom = 4;
    public static final int WIDTH = 2048/Zoom;
    public static final int HEIGHT = 2048/Zoom;

    public static float scaleX, scaleY;
    public static int input = -1;
    //links to other classes
    public GameThread gameThread;
    public Background background;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(this, getHolder());
        //allows touch
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.grass));
        scaleX = (float)getWidth()/WIDTH;
        scaleY = (float)getHeight()/HEIGHT;
        GameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        GameThread.setRunning(false);
        boolean repeat = true;
        while(repeat) {
            try {
                gameThread.join();
                repeat = false;
            } catch (InterruptedException ex) {
            }
        }
        ((Activity)getContext()).finish();
    }

    public void end(){
        Player.getInstance().reset();
        GameObject.reset();
        Background.currentBackground.reset();
        FaultyRobot.faultyRobot.end();
        gameThread.updateTimer.cancel();
        Effect.reset();
    }

    @Override
    public void draw(Canvas canvas){
        if(canvas != null) {
            final int saved = canvas.save();
            canvas.scale(scaleX, scaleY);
            background.draw(canvas);
            Player.getInstance().draw(canvas,getContext());
            synchronized (GameObject.gameObjects) {
                for (GameObject gameObject : GameObject.gameObjects) {
                    gameObject.draw(canvas, getContext());
                }
            }
            canvas.restoreToCount(saved);

        }
    }

    public void update(){
        background.update();
        Player.getInstance().update();
        synchronized (GameObject.gameObjects) {
            for (Iterator<GameObject> iterator = GameObject.gameObjects.iterator(); iterator.hasNext(); ) {
                GameObject gameObject = iterator.next();
                if (gameObject != null) {
                    gameObject.update();
                    if (CollisionDetector.collided(false, Player.X, Player.Y, Player.curWidth, Player.curHeight, gameObject.getX(), gameObject.getY(), gameObject.getWidth(), gameObject.getHeight(), gameObject.getIsSquare())) {
                        if (gameObject.collided(this)) {
                            break;
                        }
                    }
                }
            }
        }
        GameObject.despawn();
        GameManager.purge();
        GameManager.getInstance().update(getContext());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
       if(Effect.random){
           input = (int)(Math.random()*4);
       }else {
           if (event.getAction() == MotionEvent.ACTION_DOWN) {
               input = (input + 1) % 4;
           }
       }
        return true;
    }
}
