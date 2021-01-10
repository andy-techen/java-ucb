import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Main extends Application {
    Stage window;
    Scene scene;
    ArrayList<Object> preferences_ls;
    ArrayList<Store> store_arr;

    @Override
    public void start(Stage primary_stage) {
        window = primary_stage;
        window.setTitle("FoodMood");
        window.getIcons().add(new Image("file:resources/logo.png"));

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
        search_label.getStyleClass().add("label-title");
        HBox search_box = new HBox();
        search_box.setSpacing(10);
        TextField search_bar = new TextField();
        search_bar.setPromptText("What to Eat?");
        search_bar.setPrefWidth(630);
        search_bar.setPrefHeight(40);
        search_box.setAlignment(Pos.CENTER);

        Button search_button = new Button("Search!");
        search_button.setPrefHeight(40);
        search_button.setMinWidth(60);
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

        // setting up results box--------------------------------------------------------
        ScrollPane results_pane = new ScrollPane();
        VBox results_box = new VBox();
        results_box.setPadding(new Insets(50, 75, 50, 75));
        results_box.setSpacing(20);
        results_box.prefWidthProperty().bind(window.widthProperty().subtract(25));
        results_pane.setContent(results_box);

        HBox results_box_top = new HBox();
        Button return_button = new Button("Return to Search");
        return_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        return_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        return_button.setOnAction(e -> {
            layout.setCenter(search_pane);
            results_box.getChildren().clear();
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        results_box_top.getChildren().addAll(region, return_button);
        results_box.getChildren().add(results_box_top);

        search_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        search_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        search_button.setOnAction(e -> {
            scene.setCursor(Cursor.WAIT);
            layout.setCenter(results_pane);

            // add mood food to search term
            String mood = mood_box.getSelectionModel().getSelectedItem().toString();
            String term = String.join("+", search_bar.getText().split(" ")) + "+" + getMoodFood(mood);
            System.out.println("Search term: " + term);

            // setting price according to budget
            int budget = (int) preferences_ls.get(4);
            String price = getPrice(budget);

            System.out.println("Budget: " + price);

            String location = location_bar.getText();
            int limit = (int) preferences_ls.get(5);
            System.out.println("Location: " + location);
            System.out.println("Results: " + limit);

            try {
                store_arr = Results.search(term, location, price, limit);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            Label term_label = new Label("Showing " + store_arr.size() + " results for: " + term);
            results_box_top.getChildren().add(0, term_label);

            store_arr.forEach(s -> results_box.getChildren().add(storeToBox(s)));
        });

        scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("file:resources/style.css");

        window.setScene(scene);
        window.show();
    }

    private String getMoodFood(String mood) {
        switch (mood) {
            case "I'm Feeling Happy :)":
                return String.join("+", (ArrayList) preferences_ls.get(0));
            case "I'm Feeling Sad :(":
                return String.join("+", (ArrayList) preferences_ls.get(1));
            case "I'm Feeling Stressed :/":
                return String.join("+", (ArrayList) preferences_ls.get(2));
            default:
                return String.join("+", (ArrayList) preferences_ls.get(3));
        }
    }
    private String getPrice(int budget) {
        if (budget <= 10) {
            return "1";
        } else if (budget <= 30) {
            return "1,2";
        } else if (budget <= 60) {
            return "1,2,3";
        }
        return "1,2,3,4";
    }
    private HBox storeToBox(Store store) {
        HBox store_box = new HBox();
        store_box.setSpacing(30);

        ImageView profile_image = new ImageView(new Image(store.getProfile_url()));
        profile_image.setStyle("-fx-border-radius: 50%");
        profile_image.setFitHeight(100);
        profile_image.setFitWidth(100);
        profile_image.setPreserveRatio(false);

        VBox detail_box = new VBox();
        Label name_label = new Label(store.getName());
        name_label.getStyleClass().add("label-section");
        Label address_label = new Label(store.getAddress());
        Label price_label = new Label(store.getPrice());
        Label rating_label = new Label(Integer.toString(store.getReviews()));
        System.out.println(store.getName());

        detail_box.getChildren().addAll(name_label, address_label, price_label, rating_label);
        store_box.getChildren().addAll(profile_image, detail_box);

        return store_box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
