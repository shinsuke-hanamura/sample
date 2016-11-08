package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;

import java.util.Calendar;

/**
 * Created by s-hanamura on 2016/11/06.
 */
public class MyCharacterModelBak extends CharacterModel {

    private boolean selected = false;

    private double x = 0;
    private double y = 0;
    private double z = 0;

//    private double lastX = 0;
//    private double lastY = 0;
//    private double lastZ = 0;

//    private double nextX = 0;
//    private double nextY = 0;
//    private double nextZ = 0;

    // final double DET_MOVE = 100;

    private double detX = 0;
    private double detY = 0;
    private double detZ = 0;

    final double speed = 1; // 1msecごとの移動座標距離

    private double lastTimeX = 0;
    private double lastTimeY = 0;
    private double lastTimeZ = 0;

    private double lastTime = 0;

    /**
     *
     */
    private boolean controllable = true;
    private Box shape;

    @Override
    public void clicked() {
        this.selected = (!this.selected);
    }

    public void sendKey(KeyCode code) {

        this.lastTime = Calendar.getInstance().getTimeInMillis();
        this.lastTimeX = this.x;
        this.lastTimeY = this.y;
        this.lastTimeZ = this.z;

        this.detX = 0;
        this.detY = 0;

        if (code == null) return;

        if (code.equals(KeyCode.LEFT))  this.detX = -speed;
        if (code.equals(KeyCode.RIGHT)) this.detX = speed;
        if (code.equals(KeyCode.UP))   this.detY = -speed;
        if (code.equals(KeyCode.DOWN)) this.detY = speed;


//        this.lastX = this.x;
//        this.lastY = this.y;
//        this.lastZ = this.z;
//
//        if (code.equals(KeyCode.LEFT))  this.nextX = this.x - DET_MOVE;
//        if (code.equals(KeyCode.RIGHT)) this.nextX = this.x + DET_MOVE;
//        if (code.equals(KeyCode.UP))   this.nextY = this.y - DET_MOVE;
//        if (code.equals(KeyCode.DOWN)) this.nextY = this.y + DET_MOVE;
    }

    public boolean isSelected() {
        return selected;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * 時刻tにおける座標に移動する
     */
    public void setTranslate(long t) {
        this.x = this.lastTimeX + this.detX * (t - this.lastTime);
        this.y = this.lastTimeY + this.detY * (t - this.lastTime);
        this.z = this.lastTimeZ + this.detZ * (t - this.lastTime);
    }


    /**
     * 次の座標に移動させる
     * @param rate 移動率。1の時、完全に次の座標に移動。0の時は移動元にいる
     */
    @Deprecated
    public void moveToNext(double rate) {
//        this.x = this.lastX + rate * (this.nextX - this.lastX);
//        this.y = this.lastY + rate * (this.nextY - this.lastY);
//        this.z = this.lastZ + rate * (this.nextZ - this.lastZ);
    }

    public void repaint() {
        this.shape.setTranslateX(this.getX());
        this.shape.setTranslateY(this.getY());
        System.out.println("repainted with cood (x, y, z) = " + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")");
    }

    public void setShape(Box shape) {
        this.shape = shape;
        this.repaint();
    }


}
