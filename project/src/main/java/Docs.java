import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Docs {
	static Scene scene;

	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Docs");
		window.getIcons().add(new Image(Docs.class.getResource("logo.png").toExternalForm()));

		VBox layout = new VBox();
		layout.setAlignment(Pos.TOP_LEFT);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setSpacing(5);

		Label title = new Label("How to Use FoodMood");
		title.getStyleClass().add("label-section");
		TextFlow text_flow = new TextFlow();
		text_flow.getStyleClass().add("text-flow");
		text_flow.setLineSpacing(4);
		Text paragraph = new Text("1.  Settings > Preferences\n" +
				"2.  Tell FoodMood your favorite food for each mood!\n" +
				"3.  Tell FoodMood your budget and how many stores to display.\n" +
				"4.  Go on and enter anything in the search bar!\n" +
				"5.  Enter your location so FoodMood can search for stores nearby.\n" +
				"6.  Finally, select your mood right now and search for results!"
		);
		text_flow.getChildren().add(paragraph);

		HBox href_box = new HBox();
		href_box.setSpacing(4);
		href_box.setAlignment(Pos.CENTER);
		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);

		ColorAdjust gray = new ColorAdjust();
		gray.setBrightness(0.4);   // change from black (#000000) to #666666

		Label github_label = new Label("Source:");
		Button github_button = new Button();
		ImageView github_icon = new ImageView(new Image(Docs.class.getResource("github.png").toExternalForm()));
		github_icon.setSmooth(true);
		github_icon.setFitHeight(25);
		github_icon.setPreserveRatio(true);
		github_button.setGraphic(github_icon);
		github_button.getStyleClass().add("button-trans");
		github_button.setOnMouseEntered(e -> {
			github_button.setEffect(gray);
			scene.setCursor(Cursor.HAND);
		});
		github_button.setOnMouseExited(e -> {
			github_button.setEffect(null);
			scene.setCursor(Cursor.DEFAULT);
		});
		github_button.setOnAction(e -> Main.openBrowser("https://github.com/andy-techen/java-ucb"));

		Label java_label = new Label("JavaFX:");
		Button java_button = new Button();
		ImageView java_icon = new ImageView(new Image(Docs.class.getResource("java.png").toExternalForm()));
		java_icon.setSmooth(true);
		java_icon.setFitHeight(25);
		java_icon.setPreserveRatio(true);
		java_button.setGraphic(java_icon);
		java_button.getStyleClass().add("button-trans");
		java_button.setOnMouseEntered(e -> {
			java_button.setEffect(gray);
			scene.setCursor(Cursor.HAND);
		});
		java_button.setOnMouseExited(e -> {
			java_button.setEffect(null);
			scene.setCursor(Cursor.DEFAULT);
		});
		java_button.setOnAction(e -> Main.openBrowser("https://openjfx.io/"));

		href_box.getChildren().addAll(region, github_label, github_button, java_label, java_button);

		layout.getChildren().addAll(title, text_flow, href_box);

		scene = new Scene(layout, 410, 250);
		scene.getStylesheets().add(Docs.class.getResource("style.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
}
