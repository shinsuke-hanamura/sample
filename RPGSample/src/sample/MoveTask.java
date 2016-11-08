package sample;

import javafx.concurrent.Task;

import java.util.Calendar;

/**
 * Created by s-hanamura on 2016/11/06.
 */
public class MoveTask extends Task<Void>{

    MyCharacterModel model = null;

    public MoveTask(MyCharacterModel myCharacterModel) {
        this.model = myCharacterModel;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("called");

        long now = Calendar.getInstance().getTimeInMillis();
        System.out.println("current time = " + now);
        while (true) {
            now = Calendar.getInstance().getTimeInMillis();
            model.setTranslate(now);
            model.repaint();
            Thread.sleep(20);
        }


    }

//    @Override
//    protected Void call() throws Exception {
//        System.out.println("called");
//
//        System.out.println("current time = " + Calendar.getInstance().getTimeInMillis());
//        // アニメーション開始時間を取得
//        long startTime = Calendar.getInstance().getTimeInMillis();
//        // アニメーションの時間（ミリ秒）
//        long animathionLength = 100;
//
//        long endTime = startTime + animathionLength;
//
//        // コマ送り
//        // コマ数を計算し、移動元〜移動先をコマ数に分割、コマごとにモデルを再描画する
//        long now = Calendar.getInstance().getTimeInMillis();
//        while (now < endTime) {
//            now = Calendar.getInstance().getTimeInMillis();
//            model.setTranslate(now);
//            model.repaint();
//            Thread.sleep(100);
//        }
//        updateMessage("OK");
//
//        return null;
//    }

}
