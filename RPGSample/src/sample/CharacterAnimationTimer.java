package sample;

import javafx.animation.AnimationTimer;

import java.util.Calendar;

/**
 * Created by s-hanamura on 2016/11/06.
 */
public class CharacterAnimationTimer extends AnimationTimer {

    // アニメーション対象のキャラクター
    private MyCharacterModel model = null;

    // アニメーションのコマ送り間隔（ミリ秒）
    private long duration = 10;

    // アニメーションの時間（ミリ秒）
    private long animathionLength = 100;

    private long startTime = 0;

    /**
     * アニメーションを初期化する
     */
    public CharacterAnimationTimer(MyCharacterModel model, long milliSec) {
        // 内部変数の初期化
        this.model = model;
        this.duration = milliSec * 1000000L;
    }

    @Override
    public void handle(long t) {
        // System.out.println("start time t = " + t);
        System.out.println("current time = " + Calendar.getInstance().getTimeInMillis());
        // アニメーション開始時間を取得
        if (startTime == 0) {
            startTime = Calendar.getInstance().getTimeInMillis();
        }
        long endTime = startTime + animathionLength;

        // コマ送り
        // コマ数を計算し、移動元〜移動先をコマ数に分割、コマごとにモデルを再描画する
        long now = Calendar.getInstance().getTimeInMillis();
        while (now < endTime) {
            now = Calendar.getInstance().getTimeInMillis();
            model.setTranslate(now);
            model.repaint();
        }

//        while (now < endTime) {
//            double rate = ((double)(now - startTime) / (double)animathionLength);
//            System.out.println("rate = " + rate);
//            model.moveToNext(rate);
//            model.repaint();
//            now = Calendar.getInstance().getTimeInMillis();
//        }

        //model.moveToNext(1);
        //System.out.println("move to next cood (x, y, z) = " + model.getX() + ", " + model.getY() + ", " + model.getZ() + ")");
        //model.repaint();

        // アニメーションを停止
        startTime = 0;
        stop();
    }


}

