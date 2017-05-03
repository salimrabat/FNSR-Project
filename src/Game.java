/**
 * Created by srabb on 02/05/2017.
 */

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Game");
        Group group = new Group();

        Rectangle rect1 = new Rectangle(0, -50, 100, 50);
        rect1.setFill(Color.BROWN);

        Rectangle rect2 = new Rectangle(150, -50, 100, 50);
        rect2.setFill(Color.YELLOW);

        Rectangle rect3 = new Rectangle(300, -50, 100, 50);
        rect3.setFill(Color.ORANGE);

        Rectangle rect4 = new Rectangle(450, -50, 100, 50);
        rect4.setFill(Color.GREEN);

        Rectangle rectButton = new Rectangle(0, 400, 550, 100);
        rectButton.setFill(Color.GRAY);

        group.getChildren().addAll(rectButton, rect1, rect2, rect3 ,rect4);



        Scene scene = new Scene(group, 550, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline1 = new Timeline();
        timeline1.setCycleCount(Timeline.INDEFINITE);

        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);

        Timeline timeline3 = new Timeline();
        timeline3.setCycleCount(Timeline.INDEFINITE);

        Timeline timeline4 = new Timeline();
        timeline4.setCycleCount(Timeline.INDEFINITE);

        KeyValue keyValue1 = new KeyValue(rect1.yProperty(), 600);

        KeyValue keyValue2 = new KeyValue(rect2.yProperty(), 600);

        KeyValue keyValue3 = new KeyValue(rect3.yProperty(), 600);

        KeyValue keyValue4 = new KeyValue(rect4.yProperty(), 600);

        Random rand = new Random();



        KeyFrame kf1 = new KeyFrame(Duration.millis(1500+rand.nextInt(1000)), keyValue1);
        KeyFrame kf2 = new KeyFrame(Duration.millis(1500+rand.nextInt(1000)), keyValue2);
        KeyFrame kf3 = new KeyFrame(Duration.millis(1500+rand.nextInt(1000)), keyValue3);
        KeyFrame kf4 = new KeyFrame(Duration.millis(1500+rand.nextInt(1000)), keyValue4);
        timeline1.getKeyFrames().add(kf1);

        timeline2.getKeyFrames().add(kf2);

        timeline3.getKeyFrames().add(kf3);

        timeline4.getKeyFrames().add(kf4);

        ParallelTransition parallelTransition = new ParallelTransition();

        parallelTransition.getChildren().addAll(timeline1, timeline2, timeline3, timeline4);

        parallelTransition.play();


    }
}
