import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
	static Scene scene;

	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Preferences Not Set!");
		window.getIcons().add(new Image("file:resources/logo.png"));

		VBox layout = new VBox();
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setSpacing(20);

		Text alert_text = new Text("Your preferences have not been set yet!");
		Button alert_button = new Button("Set Preferences");
		alert_button.setPrefHeight(30);
		alert_button.setMinWidth(120);
		alert_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
		alert_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
		alert_button.setOnAction(e -> {
			Preferences.display();
			window.close();
		});

		layout.getChildren().addAll(alert_text, alert_button);

		scene = new Scene(layout, 280, 150);
		scene.getStylesheets().add("file:resources/style.css");
		window.setScene(scene);
		window.showAndWait();
	}
}
