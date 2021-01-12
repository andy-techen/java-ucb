package com.foodmood;

import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class Preferences {
	static ArrayList<String> happy_food, sad_food, stressed_food, fav_food;
	static int max_price, results;
	static ArrayList<Object> preference_ls;
	static Scene scene;

	public static ArrayList<Object> display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Preferences");
		window.getIcons().add(new Image(Preferences.class.getResourceAsStream("/logo.png")));

		VBox layout = new VBox();
		layout.setAlignment(Pos.TOP_LEFT);
		layout.setPadding(new Insets(30, 30, 30, 30));
		layout.setSpacing(5);

		// food preferences--------------------------------------------------
		GridPane food_pane = new GridPane();
		food_pane.setHgap(20);
		food_pane.setVgap(10);
		food_pane.setPadding(new Insets(0, 0, 30, 0));

		Label food_label = new Label("Food Preferences");
		food_label.getStyleClass().add("label-section");

		Label happy_label = new Label("Food for Happy Mood :)");
		GridPane.setConstraints(happy_label, 0, 1);
		TextField happy_input = new TextField();
		happy_input.setText("cookies");
		happy_input.setPrefWidth(250);
		GridPane.setConstraints(happy_input, 1, 1);

		Label sad_label = new Label("Food for Sad Mood :(");
		GridPane.setConstraints(sad_label, 0, 2);
		TextField sad_input = new TextField();
		sad_input.setText("chocolate");
		sad_input.setPrefWidth(250);
		GridPane.setConstraints(sad_input, 1, 2);

		Label stressed_label = new Label("Food for Stressed Mood :/");
		GridPane.setConstraints(stressed_label, 0, 3);
		TextField stressed_input = new TextField();
		stressed_input.setText("cake");
		stressed_input.setPrefWidth(250);
		GridPane.setConstraints(stressed_input, 1, 3);

		Label fav_label = new Label("Food for Any Kind of Mood :D");
		GridPane.setConstraints(fav_label, 0, 4);
		TextField fav_input = new TextField();
		fav_input.setText("mango");
		fav_input.setPrefWidth(250);
		GridPane.setConstraints(fav_input, 1, 4);

		Text notes_text = new Text("* Separate food with comma or space!");
		GridPane.setConstraints(notes_text, 1, 0);
		notes_text.getStyleClass().add("text-notes");

		// other preferences-------------------------------------------------
		GridPane other_pane = new GridPane();
		other_pane.setHgap(20);
		other_pane.setVgap(10);
		other_pane.setPadding(new Insets(10, 0, 30, 0));

		Label other_label = new Label("Other com.foodmood.Preferences");
		other_label.getStyleClass().add("label-section");

		Label price_label = new Label("My Budget $$$ (USD)");
		price_label.setPrefWidth(160);
		GridPane.setConstraints(price_label, 0, 0);
		Slider price_slider = new Slider(0, 80, 5);
		price_slider.setMajorTickUnit(10);
		price_slider.setMinorTickCount(1);
		price_slider.setPrefWidth(250);
		price_slider.setSnapToTicks(true);
		price_slider.setShowTickLabels(true);
		price_slider.setShowTickMarks(true);
		GridPane.setConstraints(price_slider, 1, 0);

		Label results_label = new Label("Number of Stores Displayed");
		GridPane.setConstraints(results_label, 0, 1);
		ChoiceBox results_box = new ChoiceBox();
		results_box.getItems().addAll(5, 10, 15, 20, 25, 30);
		results_box.setValue(10);
		GridPane.setConstraints(results_box, 1, 1);

		// submitting preferences
		HBox button_hBox = new HBox();
		button_hBox.setAlignment(Pos.BOTTOM_RIGHT);
		button_hBox.setSpacing(10);
		Button submit_button = new Button("All Done!");
		Button reset_button = new Button("Reset!");
		submit_button.setPrefHeight(30);
		submit_button.setMinWidth(80);
		reset_button.setPrefHeight(30);
		reset_button.setMinWidth(60);

		preference_ls = new ArrayList<>();
		submit_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
		submit_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
		reset_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
		reset_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));

		submit_button.setOnAction(e -> {
			happy_food = new ArrayList<>(Arrays.asList(happy_input.getText().split("[ ,]+")));
			sad_food = new ArrayList<>(Arrays.asList(sad_input.getText().split("[ ,]+")));
			stressed_food = new ArrayList<>(Arrays.asList(stressed_input.getText().split("[ ,]+")));
			fav_food = new ArrayList<>(Arrays.asList(fav_input.getText().split("[ ,]+")));
			max_price = (int) price_slider.getValue();  //  $ = under $10. $$ = 11-30. $$$ = 31-60. $$$$ = over $61
			results = (int) results_box.getValue();
			Collections.addAll(preference_ls, happy_food, sad_food, stressed_food, fav_food, max_price, results);
			window.close();
		});
		reset_button.setOnAction(e -> {
			preference_ls = null;
			happy_input.clear();
			sad_input.clear();
			stressed_input.clear();
			fav_input.clear();
			price_slider.setValue(5);
			results_box.setValue(10);
		});

		food_pane.getChildren().addAll(
				notes_text, happy_label, happy_input, sad_label, sad_input,
				stressed_label, stressed_input, fav_label, fav_input);
		other_pane.getChildren().addAll(
				price_label, price_slider,
				results_label, results_box);
		button_hBox.getChildren().addAll(submit_button, reset_button);
		layout.getChildren().addAll(food_label, new Separator(), food_pane, other_label, new Separator(), other_pane, button_hBox);

		scene = new Scene(layout, 500, 500);
		scene.getStylesheets().add("style.css");
		window.setScene(scene);
		window.showAndWait();

		return preference_ls;
	}
}
