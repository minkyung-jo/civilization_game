package runner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

import controller.GameController;
import view.StartScreen;
import view.CivEnum;
import view.GameScreen;
import model.Bandit;
import model.Civilization;
import view.GridFX;
import model.Egypt;
import model.QinDynasty;
import model.RomanEmpire;
import model.CandyLand;
import model.ChosunDynasty;
import model.Maya;

/**
 * Created by Tian-Yo Yang on 11/11/2016.
 */
public class CivilizationGame extends Application {

    /**
     * this method is called upon running/launching the application
     * this method should display a scene on the stage
     */
    private Stage window;
    private static VBox vbox;
    private static String settlementName;

    public void start(Stage primaryStage) {
        //TODO
        window = primaryStage;
        StartScreen startScreen = new StartScreen();
        Button stBttn = startScreen.getStartButton();
        ListView<CivEnum> civList = startScreen.getCivList();
        Text t = new Text(10, 50, "Select a Civilization to Begin");
        t.setFont(new Font(20));
        t.setFill(Color.RED);
        vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.getChildren().addAll(t, civList, stBttn);
        vbox.setMaxSize(300, 200);
        StartScreen.getStackPane().getChildren().add(vbox);

        StackPane root = StartScreen.getStackPane();
        Scene scene = new Scene(root);
        URL resource = getClass().getResource("startsound.mp3");
        // String music = "startsound.mp3";
        Media sound = new Media(resource.toString());
        MediaPlayer mp = new MediaPlayer(sound);
        mp.play();
        window.setScene(scene);
        window.show();
        // what needs to go inside ()?

        stBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mp.stop();
                String myCiv = civList.getSelectionModel()
                    .getSelectedItem().toString();
                Civilization civilization = new Civilization(myCiv);
                if (myCiv.equals("Ancient Egypt")) {
                    civilization = new Egypt();
                } else if (myCiv.equals("Roman Empire")) {
                    civilization = new RomanEmpire();
                } else if (myCiv.equals("Qin Dynasty")) {
                    civilization = new QinDynasty();
                } else if (myCiv.equals("Chosun Dynasty")) {
                    civilization = new ChosunDynasty();
                } else if (myCiv.equals("Maya")) {
                    civilization = new Maya();
                } else if (myCiv.equals("Candy Land")) {
                    civilization = new CandyLand();
                }
                GameController.setCivilization(civilization);
                TextInputDialog dialog = new TextInputDialog("Town Name");
                dialog.setTitle("A New Settlement");
                dialog.setHeaderText("You have build a Settlement!");
                dialog.setContentText("Enter the Name of your first town:");
                Optional<String> settlement = dialog.showAndWait();
                GridFX.getMap().putSettlement(settlement.get(),
                    civilization, 0, 9);
                GridFX.getMap().addEnemies(new Bandit(), 2);
                GridFX.update();
                if (settlement.isPresent()) {
                    startGame();
                }
            }
        });

        // if (result.isPresent()) {
        //     entered = result.get();
        // }

    }
    /**
     * This is the main method that launches the javafx application
     */
    public static void main(String[] args) {
        //TODO
        launch(args);
    }
    /**
    * This method is responsible for setting the scene to the corresponding
    * layout.
    * and returning the scene.
    * @return Scene
    */

    public Scene startGame() {
        GameScreen gameScene = new GameScreen();
        BorderPane bp = gameScene.getBorderPane();
        Scene scene = new Scene(bp, 1920, 1080);
        window.setScene(scene);
        window.show();
        return scene;
    }

    public static String getSettlement() {
        return settlementName;
    }

    public static Civilization getCivilization() {
        return GameController.getCivilization();
    }


}
