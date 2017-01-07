package tdrpg.luo.faultyrobot;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

import tdrpg.luo.faultyrobot.GameObject.GameObject;

/**
 * Created by Luo on 2016-05-19.
 */
public class Background {
    private static final int jvSquareSize = 32;
    //Current scaling of this background. When it goes out of focus a new background is determined
    public static final double standardSpeed = 2;
    public static int direction;
    public static Background currentBackground;
    public static Background[] connectingBackground = new Background[8];
    private Bitmap bitmap;
    private double x,y;
    private double dx,dy;
    public Background(Bitmap bImage){
        this.bitmap = bImage;
        dx = standardSpeed;
        dy = standardSpeed;
        currentBackground = this;
    }
    public double getX(){return x;}
    public double getY(){return y;}
    //currently useless getters and setters
    public void setXVector(double dx){this.dx = dx;}
    public void setYVector(double dy){this.dy = dy;}
    public double getXVector(){return dx;}
    public double getYVector(){return dy;}
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    public void update() {
        if (!Effect.stunned) {
            boolean moved = false;
            direction = GamePanel.input;
            switch (direction) {
                case 0:
                    //comparing to dx and dy to account for the possibility that the image might go out of the bound
                    if (dy > 0) {
                        if (!(y < -dy)) {
                            y -= GamePanel.HEIGHT * GamePanel.Zoom - 10;
                        }
                        y += dy;
                        moved = true;
                    }
                    break;
                case 1:
                    if (dx > 0) {
                        if (!(x >= (((GamePanel.Zoom - 1) * -GamePanel.WIDTH) + dx))) {
                            x += GamePanel.WIDTH * GamePanel.Zoom - 10;
                        }
                        x -= dx;
                        moved = true;
                    }
                    break;
                case 2:
                    if (dy > 0) {
                        if (!(y >= (((GamePanel.Zoom - 1) * -GamePanel.HEIGHT) + dy))) {
                            y += GamePanel.HEIGHT * GamePanel.Zoom - 10;
                        }
                        y -= dy;
                        moved = true;
                    }
                    break;
                case 3:
                    if (dx > 0) {
                        if (!(x < -dx)) {
                            x -= GamePanel.WIDTH * GamePanel.Zoom - 10;
                        }
                        x += dx;
                        moved = true;
                    }
                    break;
            }
            if (moved) {
                synchronized (GameObject.gameObjects) {
                    for (GameObject gameObject : GameObject.gameObjects) {
                        gameObject.adjust();
                    }
                }
            }
        }
    }
    private void drawConnected(Canvas canvas){
        canvas.drawBitmap(bitmap,(int)x-GamePanel.WIDTH*GamePanel.Zoom+10,(int)y-GamePanel.HEIGHT*GamePanel.Zoom+10,null);
        canvas.drawBitmap(bitmap,(int)x-GamePanel.WIDTH*GamePanel.Zoom+10,(int)y,null);
        canvas.drawBitmap(bitmap,(int)x-GamePanel.WIDTH*GamePanel.Zoom+10,(int)y+GamePanel.HEIGHT*GamePanel.Zoom-10,null);
        canvas.drawBitmap(bitmap,(int)x,(int)y-GamePanel.HEIGHT*GamePanel.Zoom+10,null);
        canvas.drawBitmap(bitmap,(int)x,(int)y+GamePanel.HEIGHT*GamePanel.Zoom-10,null);
        canvas.drawBitmap(bitmap,(int)x+GamePanel.WIDTH*GamePanel.Zoom-10,(int)y-GamePanel.HEIGHT*GamePanel.Zoom+10,null);
        canvas.drawBitmap(bitmap,(int)x+GamePanel.WIDTH*GamePanel.Zoom-10,(int)y,null);
        canvas.drawBitmap(bitmap,(int)x+GamePanel.WIDTH*GamePanel.Zoom-10,(int)y+GamePanel.HEIGHT*GamePanel.Zoom-10,null);

    }

    //draw the image on the canvas
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,(int)x,(int)y,null);
        drawConnected(canvas);
    }
    public void reset(){
        this.dx = standardSpeed;
        this.dy = standardSpeed;
        this.x = 0;
        this.y = 0;
    }

}
