package tdrpg.luo.faultyrobot;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Luo on 2016-05-13.
 */
public class GameThread extends Thread {
    public static final int FRAMERATE = 40, MILLISECONDS_TO_SECOND = 1000;
    public Timer updateTimer;
    private static boolean running;
    private GamePanel gamePanel;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;

    public static void setRunning(boolean sRunning){
        running = sRunning;
    }

    public GameThread(GamePanel gamePanel, SurfaceHolder surfaceHolder){
        super();
        //connects to the rest of the classes
        this.gamePanel = gamePanel;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run(){
        //Creates a timer with FPS = FRAMERATE set to update the Gamepanel
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gamePanel.update();
            }
        },MILLISECONDS_TO_SECOND,MILLISECONDS_TO_SECOND/FRAMERATE);
        gamePanel.draw(canvas);

        while(running){
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //draw/render the game
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception ex){}
            finally {
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        }
    }
}
