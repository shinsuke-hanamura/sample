package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Box;

import java.util.Calendar;

/**
 * Created by s-hanamura on 2016/11/06.
 */
public class MyCharacterModel extends CharacterModel {

    private boolean selected = false;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double detX = 0;
    private double detY = 0;
    private double detZ = 0;

    final double speed = 0.1; // 1msecごとの移動座標距離

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

    public void sendKey(KeyCode code, boolean pressed) {

        this.lastTime = Calendar.getInstance().getTimeInMillis();
        this.lastTimeX = this.x;
        this.lastTimeY = this.y;
        this.lastTimeZ = this.z;

        this.detX = 0;
        this.detY = 0;

        if (code == null) return;

        if (code.equals(KeyCode.LEFT))  this.detX = pressed ? -speed : 0;
        if (code.equals(KeyCode.RIGHT)) this.detX = pressed ? speed : 0;
        if (code.equals(KeyCode.UP))   this.detY = pressed ? -speed : 0;
        if (code.equals(KeyCode.DOWN)) this.detY = pressed ? speed : 0;
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
