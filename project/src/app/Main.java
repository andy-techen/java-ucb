package app;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    Stage window;
    Scene start_scene, result_scene;

    @Override
    public void start(Stage primary_stage) {
        window = primary_stage;

        // setting up start scene--------------------------------------------------------
        BorderPane start_layout = new BorderPane();

        // menu bar
        MenuBar menu_bar = new MenuBar();
        Menu setting_menu = new Menu("_Settings");
        MenuItem setting_preferences = new MenuItem("_Preferences");
        setting_preferences.setOnAction(e -> Preferences.display()); // open up preferences scene
        setting_menu.getItems().addAll(setting_preferences);
        Menu help_menu = new Menu("_Help");
        Menu about_menu = new Menu("_About");
        menu_bar.getMenus().addAll(setting_menu, help_menu, about_menu);
        start_layout.setTop(menu_bar);

        Button search_button = new Button("Search!");
        search_button.setOnAction(e -> window.setScene(result_scene));
        start_layout.setBottom(search_button);

        start_scene = new Scene(start_layout, 800, 600);

        window.setTitle("What to Eat?");
        window.setScene(start_scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
