package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FrontApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FrontApp.class.getResource("view/main_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("view/icon.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Waveπ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void criarAudio(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FrontApp.class.getResource("view/criar_menu.fxml"));
        stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage.setScene(scene);
        stage.setTitle("Criando wave");
    }

    public static void ouvirAudio(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FrontApp.class.getResource("view/ouvir_menu.fxml"));
        stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage.setScene(scene);
        stage.setTitle("Ouvindo wave");
    }

    public static void dadosAudio(Stage stage, String nome) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FrontApp.class.getResource("view/dados_menu.fxml"));
        stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage.setScene(scene);
        stage.setTitle("Wave criado");
    }

    public static void menuPrincipal(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FrontApp.class.getResource("view/main_menu.fxml"));
        stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 800, 480);
        stage.setScene(scene);
        stage.setTitle("Waveπ");
    }
}