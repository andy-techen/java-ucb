import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    Stage window;
    Scene scene;
    ArrayList<Object> preferences_ls;

    @Override
    public void start(Stage primary_stage) {
        window = primary_stage;

        // setting up layout-------------------------------------------------------------
        BorderPane layout = new BorderPane();

        // menu bar
        MenuBar menu_bar = new MenuBar();
        Menu setting_menu = new Menu("_Settings");
        MenuItem setting_preferences = new MenuItem("_Preferences");
        setting_preferences.setOnAction(e -> preferences_ls = Preferences.display()); // open up preferences scene
        setting_menu.getItems().addAll(setting_preferences);
        Menu help_menu = new Menu("_Help");
        MenuItem help_about = new MenuItem("_About");
        MenuItem help_docs = new MenuItem("_Docs");
        help_menu.getItems().addAll(help_about, help_docs);
        menu_bar.getMenus().addAll(setting_menu, help_menu);
        layout.setTop(menu_bar);

        // search pane
        VBox search_pane = new VBox();
        search_pane.setPadding(new Insets(50, 50, 50, 50));
        search_pane.setSpacing(20);

        Label search_label = new Label("FoodMood");
        HBox search_box = new HBox();
        search_box.setSpacing(10);
        TextField search_bar = new TextField();
        search_bar.setPromptText("What to Eat?");
        search_bar.setPrefWidth(630);
        search_bar.setPrefHeight(40);

        Button search_button = new Button("Search!");
        search_button.setPrefHeight(40);
        search_button.setPrefWidth(60);
        search_box.getChildren().addAll(search_bar, search_button);

        TextField location_bar = new TextField();
        location_bar.setPromptText("Where to Eat?");
        location_bar.setMaxWidth(150);
        location_bar.setPrefHeight(30);

        ChoiceBox mood_box = new ChoiceBox();
        mood_box.getItems().addAll("I'm Feeling Happy :)", "I'm Feeling Sad :(", "I'm Feeling Stressed :/", "I'm Feeling Lucky :D");
        mood_box.setValue("I'm Feeling Lucky :D");
        
        search_pane.getChildren().addAll(search_label, search_box, location_bar, mood_box);
        search_pane.setAlignment(Pos.CENTER);
        layout.setCenter(search_pane);

        // setting up results pane-------------------------------------------------------
        FlowPane results_pane = new FlowPane();
        results_pane.setPadding(new Insets(10, 10, 10, 10));

//        search_button.setOnAction(e -> {
//            Results.search(term, location, limit);
//            layout.setCenter(results_pane);
//        });

        scene = new Scene(layout, 800, 600);
        window.setTitle("FoodMood");
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
