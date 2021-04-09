package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("List.fxml"));
        root.setCenter(listLoader.load());
        ListController listController = listLoader.getController();

        FXMLLoader mailLoader = new FXMLLoader(getClass().getResource("Message.fxml"));
        root.setRight(mailLoader.load());
        MessageController messageController = mailLoader.getController();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root.setTop(menuLoader.load());
        MenuController menuController = menuLoader.getController();

        Model m = new Model();
        m.setId("Luigi@mail");
        listController.start(m);
        messageController.start(m);
        menuController.start(m);

        primaryStage.setTitle("EmailTest");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() {
        ListController.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
