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

import java.util.Calendar;
import java.util.List;

// public class CameraWork extends Application implements KeyListener {
public class CameraWorkWithMap extends Application {

    MyCharacterModel myChara = new MyCharacterModel();

    private static final int MAP_MIN_X = -10;
    private static final int MAP_MIN_Y = -10;
    private static final int WALL_SIZE = 20;


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

        // walls
        Walls walls = new Walls(root);
        walls.readWalls("data/map.csv");

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

    class Walls {
        private List<WallModel> wallsTranslate;
        public String WALL_STRING = "1";
        private Group groupToAddWalls;

        public Walls(Group groupToAddWalls) {
            this.groupToAddWalls = groupToAddWalls;

        }

        private void readWalls(String mapCsvFilePath) {
            List<List<String>> data = ReadCSV.read(mapCsvFilePath);
            double y = MAP_MIN_Y;
            for (List<String> eachLine : data) {
                double x = MAP_MIN_X;
                for (String eachString : eachLine) {
                    if (eachString.equals(WALL_STRING)) {
                        Box wallBox = createWallBox();
                        this.groupToAddWalls.getChildren().addAll(wallBox);

                        WallModel wall = new WallModel();
                        wall.setX(x);
                        wall.setY(y);
                        wall.setShape(wallBox);
                        wall.repaint();
                    }
                    x += WALL_SIZE;

                }
                y += WALL_SIZE;
            }
        }

        private Box createWallBox() {
            Box box = new Box(WALL_SIZE, WALL_SIZE, WALL_SIZE);
            PhongMaterial boxMaterial = new PhongMaterial();
            boxMaterial.setDiffuseColor(Color.BROWN);
            box.setMaterial(boxMaterial);
            return box;
        }
    }


}
