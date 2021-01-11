import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
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

		scene = new Scene(layout, 300, 300);
		scene.getStylesheets().add(Docs.class.getResource("style.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
}
