import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Preferences {
	static ArrayList<String> happy_food, sad_food, stressed_food, fav_food;
	static ArrayList<ArrayList<String>> preference_ls;

	public static ArrayList<ArrayList<String>> display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Preferences");

		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10, 10, 10, 10));

		happy_food = new ArrayList<>();
		sad_food = new ArrayList<>();
		stressed_food = new ArrayList<>();
		fav_food = new ArrayList<>();

		preference_ls = new ArrayList<>();
		Collections.addAll(preference_ls, happy_food, sad_food, stressed_food, fav_food);

		window.setScene(new Scene(layout, 500, 500));
		window.showAndWait();

		return preference_ls;
	}
}
