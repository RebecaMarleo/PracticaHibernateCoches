package org.example.practica_hibernate_coches;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.practica_hibernate_coches.Controller.CochesController;
import org.example.practica_hibernate_coches.util.R;

public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        CochesController controller = new CochesController();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(R.getUI("coches.fxml"));
        fxmlLoader.setController(controller);
        VBox vbox = fxmlLoader.load();
        controller.cargarDatos();
        Scene scene = new Scene(vbox, 600, 581);
        stage.setTitle("CRUD Gestión de coches");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}