import javafx.application.Application;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class Main extends Application {
	Stage window;
	Scene scene;
	ArrayList<Object> preferences_ls;
	ArrayList<Store> store_arr;
	String final_term, final_location, final_price;
	int final_limit;

	@Override
	public void start(Stage primary_stage) {
		window = primary_stage;
		window.setTitle("FoodMood");
		window.getIcons().add(new Image(getClass().getResource("logo.png").toExternalForm()));

		// setting up search page layout----------------------------------------------------
		BorderPane layout = new BorderPane();

		// menu bar
		MenuBar menu_bar = new MenuBar();
		Menu setting_menu = new Menu("_Settings");
		MenuItem setting_preferences = new MenuItem("_Preferences");
		setting_preferences.setOnAction(e -> preferences_ls = Preferences.display());   // open up preferences scene
		setting_menu.getItems().addAll(setting_preferences);
		Menu help_menu = new Menu("_Help");
		MenuItem help_about = new MenuItem("_About");
		MenuItem help_docs = new MenuItem("_Docs");
		help_about.setOnAction(e -> About.display());   // open up about scene
		help_docs.setOnAction(e -> Docs.display());    // open up docs scene
		help_menu.getItems().addAll(help_about, help_docs);
		menu_bar.getMenus().addAll(setting_menu, help_menu);
		layout.setTop(menu_bar);

		// search pane
		VBox search_pane = new VBox();
		search_pane.setPadding(new Insets(50, 50, 50, 50));
		search_pane.setSpacing(5);

		Label search_label = new Label("FoodMood");
		search_label.getStyleClass().add("label-title");
		HBox search_box = new HBox();
		search_box.setSpacing(10);
		TextField search_bar = new TextField();
		search_bar.setPromptText("What to Eat?");
		search_bar.setPrefWidth(600);
		search_bar.setPrefHeight(40);
		search_box.setAlignment(Pos.CENTER);
		search_box.getStyleClass().add("box-search");

		Button search_button = new Button("Search!");
		search_button.setPrefHeight(40);
		search_button.setMinWidth(70);
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

		// setting up results box-----------------------------------------------------------
		ScrollPane results_pane = new ScrollPane();
		VBox results_box = new VBox();
		results_box.setPadding(new Insets(50, 75, 50, 75));
		results_box.setSpacing(20);
		results_box.prefWidthProperty().bind(window.widthProperty().subtract(25));
		results_pane.setContent(results_box);

		HBox results_box_top = new HBox();
		Button return_button = new Button("Return to Search");
		return_button.setPrefHeight(30);
		return_button.setMinWidth(120);
		return_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
		return_button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
		return_button.setOnAction(e -> {
			layout.setCenter(search_pane);
			results_box.getChildren().clear();
			results_box_top.getChildren().clear();
		});
		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);

		// Set up service and background thread for search tasks
		final Service<Void> search_service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						try {
							store_arr = Results.search(final_term, final_location, final_price, final_limit);
						} catch (IOException ioe) {
							System.out.println(ioe);
						}

						Label term_label = new Label("Showing " + store_arr.size() + " results for: " + final_term);
						results_box_top.getChildren().addAll(term_label, region, return_button);
						results_box.getChildren().add(results_box_top);
						System.out.println("added results_box_top");

						store_arr.forEach(s -> {
							System.out.println(s.getName());
							results_box.getChildren().addAll(storeToBox(s), new Separator());
						});

						return null;
					}
				};
			}
		};
		search_service.setOnSucceeded(t -> {
			scene.setCursor(Cursor.DEFAULT);
			layout.setCenter(results_pane);
		});
		search_service.setOnFailed(t -> {
			System.out.println("Service Failed");
			scene.setCursor(Cursor.DEFAULT);
		});

		search_button.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
		search_button.setOnMouseExited(e -> {
			if (!search_service.isRunning()) {
				scene.setCursor(Cursor.DEFAULT);
			}
		});
		search_button.setOnAction(e -> {
			String term = "";
			String price = "";
			String location = "";
			int limit = 10;
			try {
				// add mood food to search term
				String mood = mood_box.getSelectionModel().getSelectedItem().toString();
				term = String.join("+", search_bar.getText().split(" ")) + "+" + getMoodFood(mood);
				System.out.println("Search term: " + term);

				// setting price according to budget
				int budget = (int) preferences_ls.get(4);
				price = getPrice(budget);
				System.out.println("Budget: " + price);

				location = location_bar.getText();
				limit = (int) preferences_ls.get(5);
				System.out.println("Location: " + location);
				System.out.println("Max Results: " + limit);
			} catch (Exception ex) {    // IndexOutOfBoundsException, IOException
				System.out.println(ex);
				Alert.display();    // preferences haven't been set
				e.consume();    // break event handler
			}

			final_term = term;
			final_location = location;
			final_price = price;
			final_limit = limit;

			scene.setCursor(Cursor.WAIT);
			search_service.restart();
		});

		scene = new Scene(layout, 800, 600);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

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
			default:    // I'm Feeling Lucky :D
				return String.join("+", (ArrayList) preferences_ls.get(3));
		}
	}

	private String getPrice(int budget) {
		// price range based on yelp settings
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

		Image profile_image_orig = new Image(store.getProfile_url());
		ImageView profile_image = cropImageCenter(profile_image_orig, 100, 100);
		profile_image.setSmooth(true);

		VBox detail_box = new VBox();
		detail_box.setSpacing(5);
		Hyperlink name_label = new Hyperlink(store.getName());
		name_label.getStyleClass().addAll("label-section", "button-trans");
		name_label.setOnAction(e -> openBrowser(store.getUrl()));
		Label address_label = new Label(store.getAddress());
		address_label.getStyleClass().add("label-address");

		HBox cat_price_box = new HBox();
		cat_price_box.setSpacing(6);
		ArrayList<String> categories_ls = store.getCategories();
		HBox cat_box = addCategory(categories_ls);
		Label price_label = new Label(store.getPrice());
		cat_price_box.getChildren().addAll(cat_box, price_label);

		HBox rating_review_box = new HBox();
		rating_review_box.setSpacing(6);
		HBox rating_box = addRating(store.getRating());
		Label review_label = new Label(store.getReviews() + " reviews");
		rating_review_box.getChildren().addAll(rating_box, review_label);
		rating_review_box.setAlignment(Pos.BASELINE_LEFT);

		detail_box.getChildren().addAll(name_label, cat_price_box, rating_review_box, address_label);
		store_box.getChildren().addAll(profile_image, detail_box);

		return store_box;
	}

	private HBox addCategory(ArrayList<String> categories_ls) {
		HBox cat_box = new HBox();
		cat_box.setSpacing(3);

		for (int i = 0; i < categories_ls.size(); i++) {
			Label cat_label = new Label(categories_ls.get(i));
			cat_label.getStyleClass().add("label-category");
			cat_box.getChildren().add(cat_label);
		}

		return cat_box;
	}

	private HBox addRating(double rating) {
		HBox rating_box = new HBox();
		rating_box.setSpacing(1);

		Image star_orig = new Image(getClass().getResource("star.png").toExternalForm());
		for (int i = 0; i < (int) rating; i++) {
			ImageView star = new ImageView(star_orig);
			star.setSmooth(true);
			star.setFitHeight(12);
			star.setPreserveRatio(true);
			rating_box.getChildren().add(star);
		}
		// cropping star based on decimal
		double rating_decimal = rating - (int) rating;
		if (rating_decimal > 0) {
			ImageView star_par = cropImageLeft(star_orig, rating_decimal, 12 * rating_decimal, 12);
			star_par.setSmooth(true);
			rating_box.getChildren().add(star_par);
		}
		return rating_box;
	}

	private ImageView cropImageCenter(Image image, double width, double height) {
		Rectangle2D mask;
		double w = image.getWidth();
		double h = image.getHeight();
		double min = (w < h) ? w : h;
		double x, y;

		// setting up coordinates according to image orientation
		if (w < h) {
			x = 0;
			y = (h - w) / 2;
		} else {
			x = (w - h) / 2;
			y = 0;
		}
		mask = new Rectangle2D(x, y, min, min);

		ImageView image_cropped = new ImageView(image);
		image_cropped.setViewport(mask);
		image_cropped.setFitWidth(width);
		image_cropped.setFitHeight(height);
		image_cropped.setPreserveRatio(true);

		return image_cropped;
	}

	private ImageView cropImageLeft(Image image, double pct, double width, double height) {
		Rectangle2D mask;
		double w = image.getWidth();
		double h = image.getHeight();

		mask = new Rectangle2D(0, 0, w * pct, h);

		ImageView image_cropped = new ImageView(image);
		image_cropped.setViewport(mask);
		image_cropped.setFitWidth(width);
		image_cropped.setFitHeight(height);
		image_cropped.setPreserveRatio(true);

		return image_cropped;
	}

	public static void openBrowser(String url) {
		try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (URISyntaxException uriSyntaxException) {
			uriSyntaxException.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
