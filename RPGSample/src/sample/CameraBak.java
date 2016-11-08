package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.util.Calendar;

public class CameraBak extends Application {

    MyCharacterModel myChara = new MyCharacterModel();


    @Override
    public void start(Stage primaryStage) {

        final Group root = new Group();

        Box box = new Box(100.0d, 100.0d, 100.0d);

        // box を左へ (-20) 移動 (X 軸)
        myChara.setX(-20.0d);
        myChara.setShape(box);

        PhongMaterial boxMaterial = new PhongMaterial();
        boxMaterial.setDiffuseColor(Color.GREEN);
        // boxMaterial.setSpecularColor(Color.WHITESMOKE);
        box.setMaterial(boxMaterial);


        // 透視投影カメラ
        final PerspectiveCamera cam = new PerspectiveCamera(true);
        // Field of View
        cam.setFieldOfView(45.5d);
        // Clipping Planes
        cam.setNearClip(1.0d);
        cam.setFarClip(1_000_000.0d);

        // カメラを 500 後退させる。(Z 軸を -500)
        cam.setTranslateZ(-500.0d);

        root.getChildren().addAll(box);

        Scene scene = new Scene(root, 640.0d, 360.0d, Color.WHITE);

        scene.setCamera(cam);

        // 青色の円にアニメーション・タイマーによる自由アニメーションを追加
        CharacterAnimationTimer characterAnimationTimer = new CharacterAnimationTimer(myChara, 1000L);
        // 実装3 タスクとして実行
        new Thread(new MoveTask(myChara)).start();

        // シーンにキー入力処理を追加する
        EventHandler<KeyEvent> sceneKeyFilter = (event) ->
        {
            System.out.println("key input(" + event.getCode().name() + ")");
            //myChara.sendKey(event.getCode());
            // 実装1 アニメーションを開始する関数を登録
            // characterAnimationTimer.start();
            // 実装2
            // animate();
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, sceneKeyFilter);



        primaryStage.setTitle("JavaFX 3D 1");
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }

    private void animate() {

        System.out.println("current time = " + Calendar.getInstance().getTimeInMillis());
        // アニメーション開始時間を取得
        long startTime = Calendar.getInstance().getTimeInMillis();
        // アニメーションの時間（ミリ秒）
        long animathionLength = 100;

        long endTime = startTime + animathionLength;

        // コマ送り
        // コマ数を計算し、移動元〜移動先をコマ数に分割、コマごとにモデルを再描画する
        long now = Calendar.getInstance().getTimeInMillis();
        while (now < endTime) {
            now = Calendar.getInstance().getTimeInMillis();
            myChara.setTranslate(now);
            myChara.repaint();
        }
    }
}
