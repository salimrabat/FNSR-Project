import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by srabb on 03/05/2017.
 */
public class Game2 {


    public Game2() {
        int[] easy = new int[] {500, 500};
        int[] medium = new int[] {1500, 1000};
        int[] hard = new int[] {2000, 1500};
    }

    public void run() {
        Stage stage = new Stage();
        stage.setTitle("Now Playing");
        Group group = new Group();
        Scene gameScene = new Scene(group, 550, 600);
        stage.setScene(gameScene);
        stage.show();
        Random rand = new Random();

        //AnimationTimer timer = new AnimationTimer() {
            //@Override
            //public void handle(long now) {


                Rectangle rectButton = new Rectangle(0, 400, 550, 100);
                rectButton.setFill(Color.GRAY);
                Rectangle[] rectangles = generate4rectangels();

                group.getChildren().addAll(rectButton, rectangles[0], rectangles[1], rectangles[2], rectangles[3]);

                Timeline timeline1 = new Timeline();
                timeline1.setCycleCount(Timeline.INDEFINITE);

                Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);

                Timeline timeline3 = new Timeline();
        timeline3.setCycleCount(Timeline.INDEFINITE);

                Timeline timeline4 = new Timeline();
        timeline4.setCycleCount(Timeline.INDEFINITE);

                KeyValue keyValue1 = new KeyValue(rectangles[0].yProperty(), 600);

                KeyValue keyValue2 = new KeyValue(rectangles[1].yProperty(), 600);

                KeyValue keyValue3 = new KeyValue(rectangles[2].yProperty(), 600);

                KeyValue keyValue4 = new KeyValue(rectangles[3].yProperty(), 600);

                KeyFrame kf1 = new KeyFrame(Duration.millis(1500 + rand.nextInt(1000)), keyValue1);
                KeyFrame kf2 = new KeyFrame(Duration.millis(1500 + rand.nextInt(1000)), keyValue2);
                KeyFrame kf3 = new KeyFrame(Duration.millis(1500 + rand.nextInt(1000)), keyValue3);
                KeyFrame kf4 = new KeyFrame(Duration.millis(1500 + rand.nextInt(1000)), keyValue4);
                timeline1.getKeyFrames().add(kf1);

                timeline2.getKeyFrames().add(kf2);

                timeline3.getKeyFrames().add(kf3);

                timeline4.getKeyFrames().add(kf4);

                ParallelTransition parallelTransition = new ParallelTransition();

                parallelTransition.getChildren().addAll(timeline1, timeline2, timeline3, timeline4);
                parallelTransition.play();

//                long expectedtime = System.currentTimeMillis();
//
//                while (true) {//Or any Loops
//                    while (System.currentTimeMillis() < expectedtime) {
//                        //Empty Loop
//                    }
//                    expectedtime += 1000;
//
//                }
            //}

        //};
        //timer.start();
    }

    public void reset() {

    }

    private Rectangle[] generate4rectangels() {

        Rectangle rect1 = new Rectangle(0, -50, 100, 50);
        rect1.setFill(Color.BROWN);

        Rectangle rect2 = new Rectangle(150, -50, 100, 50);
        rect2.setFill(Color.YELLOW);

        Rectangle rect3 = new Rectangle(300, -50, 100, 50);
        rect3.setFill(Color.ORANGE);

        Rectangle rect4 = new Rectangle(450, -50, 100, 50);
        rect4.setFill(Color.GREEN);

        Rectangle[] rectangles = new Rectangle[]{rect1, rect2, rect3, rect4};

        return rectangles;
    }

}
