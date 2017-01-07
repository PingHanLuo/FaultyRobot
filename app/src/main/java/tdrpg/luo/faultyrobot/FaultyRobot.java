package tdrpg.luo.faultyrobot;

/**
 *
 * obligations
 * if use this robot sprite
 * http://veni-mortem.deviantart.com/art/FREE-Top-Down-Sprite-Pack-150122353
 *
 *
 * hoodlums (hblue - hred)
 * http://pxp-graphics.blogspot.ca/p/topdown-sprites.html
 *
 *
 *  Robot
 *  Creative Commons 3.0
 *  Platforge Project
 *  Artist: Hannah Cohan
 * http://opengameart.org/content/robot-0
 *
 *
 * SithJester
 * Japanese Village Exterior (grass backgound)
 * http://untamed.wild-refuge.net/rmxpresources.php?characters
 */





import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.Timer;

import tdrpg.luo.faultyrobot.GameObject.GameObject;

public class FaultyRobot extends Activity {
    public static LayoutInflater inflater;
    private RelativeLayout menuLayout;
    public static FrameLayout frameLayout;
    public static GamePanel Game;
    public static FaultyRobot faultyRobot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faultyRobot = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_faulty_robot);
        inflater = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        Game = new GamePanel(this);
        frameLayout = (FrameLayout)findViewById(R.id.faultyRobot);
        menuLayout = ((RelativeLayout)inflater.inflate(R.layout.menu,null,false));
        frameLayout.addView(new SurfaceView(this));
        frameLayout.addView(Game);
        frameLayout.addView(menuLayout);
        end();
        Button btnPlay = (Button) findViewById(R.id.play);
        Button btnExit = (Button) findViewById(R.id.exit);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void play(){
        Game.gameThread = new GameThread(Game,Game.getHolder());
        GameThread.setRunning(true);
        Game.gameThread.start();
        Effect.timer = new Timer();
        frameLayout.bringChildToFront(Game);

    }
    public void end(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {;
                frameLayout.bringChildToFront(menuLayout);
                GameThread.setRunning(false);
                try {
                    Game.gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        (new AlertDialog.Builder(this))
                .setTitle("Confirm action")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
