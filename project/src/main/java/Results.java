import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.*;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Results {
	public static String build_url(String term, String location, String price, int limit) {
		String yelp_api = "https://api.yelp.com/v3/businesses/search?term={0}&location={1}&price={2}&limit={3}&open_now=true";
		String search_url = MessageFormat.format(yelp_api, term, location, price, limit);
		System.out.println(search_url);

		return search_url;
	}
	private static JsonArray extract_json(String results) {
		JsonObject full_json_obj = new Gson().fromJson(results, JsonObject.class);
		JsonArray full_json_arr = full_json_obj.getAsJsonArray("businesses");
		JsonArray results_json = new JsonArray();

		for (int i = 0; i < full_json_arr.size(); i++) {
			JsonObject store_full = full_json_arr.get(i).getAsJsonObject();
			JsonObject store = new JsonObject();

			store.addProperty("name", store_full.get("name").getAsString());
			store.addProperty("rating", store_full.get("rating").getAsDouble());
			store.addProperty("reviews", store_full.get("review_count").getAsInt());

			// "price" tags may be null
			if (store_full.has("price")) {
				store.addProperty("price", store_full.get("price").getAsString());
			} else {
				store.addProperty("price", "");
			}
			store.addProperty("profile_url", store_full.get("image_url").getAsString());

			// transform address JsonArray to String
			JsonArray address = store_full.getAsJsonObject("location").getAsJsonArray("display_address");
			StringBuilder address_str = new StringBuilder();
			address.forEach(a -> {
				address_str.append(a.getAsString());
				address_str.append(" ");
			});
			store.addProperty("address", address_str.toString());

			// extract categories JsonObject into ArrayList
			JsonArray categories = store_full.get("categories").getAsJsonArray();
			JsonArray categories_ls = new JsonArray();
			for (int j = 0; j < categories.size(); j++) {
				categories_ls.add(categories.get(j).getAsJsonObject().get("title").getAsString());
			}
			store.add("categories", categories_ls);

			results_json.add(store);
		}

		return results_json;
	}

	public static ArrayList<Store> search(String term, String location, String price, int limit) throws IOException {
		String search_url = build_url(term, location, price, limit);

		// setting up connection
		URL url = new URL(search_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		Dotenv env = Dotenv.load();
		conn.setRequestProperty("Authorization", "Bearer " + env.get("YELP_API_KEY"));
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("GET");

		// reading from conn
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuffer results = new StringBuffer();
		while ((line = br.readLine()) != null) {
			results.append(line);
		}
		br.close();

		// extracting target JsonArray
		JsonArray results_json = extract_json(results.toString());

		// creating ArrayList of Store objects
		ArrayList<Store> store_arr = new ArrayList<>();
		for (int i = 0; i < results_json.size(); i++) {
			JsonObject store = results_json.get(i).getAsJsonObject();
			store_arr.add(
					new Store(
							store.get("name").getAsString(), store.get("address").getAsString(),
							store.get("price").getAsString(), store.get("profile_url").getAsString(),
							new Gson().fromJson(store.get("categories"), ArrayList.class), store.get("rating").getAsDouble(),
							store.get("reviews").getAsInt()
					)
			);
		}

		conn.disconnect();

		return store_arr;
	}
}
