

/**
 * Created by srabb on 02/05/2017.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameInterface extends Application {


    @FXML
    private Text actiontarget;
    private static boolean buttonPressed = false;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        actiontarget.setText("Let's Start");
        buttonPressed = true;
        Game2 game = new Game2();
        game.run();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameInterface.fxml"));

        Scene scene = new Scene(root, 550, 600);
        stage.setTitle("Welcome to the Game");
        stage.setScene(scene);
        stage.show();

    }

}

