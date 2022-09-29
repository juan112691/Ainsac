package modelo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
         try {
             
           /*  TableView<ObservableList<String>> table = new TableView<ObservableList<String>>();
        // We bind the prefHeight- and prefWidthProperty to the height and width of the stage.
        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());
        stage.setScene(new Scene(table, 400, 400));*/
       
           Parent root = FXMLLoader.load(getClass().getResource("/fxml/Buscar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    public static void main(String[] args) {
        launch();
    }

}