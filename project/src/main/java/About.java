import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class About {
	static Scene scene;

	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("About");
		window.getIcons().add(new Image(About.class.getResource("logo.png").toExternalForm()));

		VBox layout = new VBox();
		layout.setAlignment(Pos.TOP_LEFT);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setSpacing(5);

		Label about_title = new Label("About This Project");
		about_title.getStyleClass().add("label-section");
		TextFlow about_flow = new TextFlow();
		about_flow.getStyleClass().add("text-flow");
		about_flow.setTextAlignment(TextAlignment.JUSTIFY);
		about_flow.setMaxWidth(460);
		Text about_description = new Text("Have you ever had trouble deciding what to eat or where to eat?" +
				" Well, FoodMood is here to save the day! Tell FoodMood what you like and how you FEEL." +
				" It will take care of the rest. Stores tailored JUST FOR YOU are just one click away!" +
				" Discovering good food had never been this easy!"
		);
		about_flow.getChildren().add(about_description);

		Label ucb_title = new Label("About Java: Discovering Its Power");
		ucb_title.getStyleClass().add("label-section");
		TextFlow ucb_flow = new TextFlow();
		ucb_flow.getStyleClass().add("text-flow");
		ucb_flow.setTextAlignment(TextAlignment.JUSTIFY);
		ucb_flow.setMaxWidth(460);
		Text ucb_description = new Text("This JavaFX application is used for the final project of the" +
				" Java: Discovering Its Power course at UC Berkeley Extension. By applying many concepts taught" +
				" in this course, including OOP principles, exception handling, multithreading, accessing web data," +
				" and many more, it took me less than a week to learn JavaFX and code this application from scratch." +
				" Excited about discovering more about the power of Java in the future!"
		);
		ucb_flow.getChildren().add(ucb_description);

		layout.getChildren().addAll(about_title, about_flow, new Separator(), ucb_title, ucb_flow);

		scene = new Scene(layout, 410, 350);
		scene.getStylesheets().add(About.class.getResource("style.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
}
