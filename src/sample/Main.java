package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

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

        ConnectionController connectionController = new ConnectionController();

        Model m = new Model();
        m.setId("Mario@mail");
        System.out.println("dasd");
        listController.start(m);
        messageController.start(m);
        menuController.start(m);

        primaryStage.setTitle("EmailTest");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
