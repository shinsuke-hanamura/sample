package sample;

import javafx.animation.AnimationTimer;
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

import java.awt.event.KeyListener;
import java.util.Calendar;

// public class CameraWork extends Application implements KeyListener {
public class CameraWork extends Application {

    MyCharacterModel myChara = new MyCharacterModel();


    @Override
    public void start(Stage primaryStage) {

        final Group root = new Group();

        Box box = new Box(10.0d, 10.0d, 10.0d);

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

        // シーンにキー入力処理を追加する
        EventHandler<KeyEvent> sceneKeyPressedFilter = (event) ->
        {
            System.out.println("key input(" + event.getCode().name() + ")");
            myChara.sendKey(event.getCode(), true);
        };
        //
        // scene.addEventFilter(KeyEvent.KEY_PRESSED, sceneKeyFilter);
        scene.setOnKeyPressed(sceneKeyPressedFilter);

        EventHandler<KeyEvent> sceneKeyReleasedFilter = (event) ->
        {
            System.out.println("key input(" + event.getCode().name() + ")");
            myChara.sendKey(event.getCode(), false);
        };
        scene.setOnKeyReleased(sceneKeyReleasedFilter);

        primaryStage.setTitle("JavaFX 3D");
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                now = Calendar.getInstance().getTimeInMillis();
                myChara.setTranslate(now);
                myChara.repaint();

            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
