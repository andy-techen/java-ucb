import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.*;


public class Results {
	static Dotenv env = Dotenv.load();

	public static String build_url(String term, String location, int limit) {
		String yelp_api = "https://api.yelp.com/v3/businesses/search?term={0}&location={1}&limit={2}&open_now=true";
		String search_url = MessageFormat.format(yelp_api, term, location, limit);

		return search_url;
	}

	private static JSONArray extract_json(String results) {
		JSONObject full_json_obj = new JSONObject(results);
		JSONArray full_json_arr = full_json_obj.getJSONArray("businesses");
		JSONArray results_json = new JSONArray();

		for (int i = 0; i < full_json_arr.length(); i++) {
			JSONObject store_full = full_json_arr.getJSONObject(i);
			JSONObject store = new JSONObject();

			store.put("name", store_full.get("name"));
			store.put("rating", store_full.get("rating"));
			store.put("reviews", store_full.get("review_count"));

			// "price" tags may be null
			if (store_full.has("price")) {
				store.put("price", store_full.get("price"));
			} else {
				store.put("price", "");
			}
			store.put("profile_url", store_full.get("image_url"));

			// transform address JSONArray to String
			JSONArray address = store_full.getJSONObject("location").getJSONArray("display_address");
			StringBuilder address_str = new StringBuilder();
			address.forEach(a -> {
				address_str.append(a);
				address_str.append(", ");
			});
			store.put("address", address_str);

			// extract categories JSONObject into ArrayList
			JSONArray categories = (JSONArray) store_full.get("categories");
			ArrayList<String> categories_ls = new ArrayList<>();
			for (int j = 0; j < categories.length(); j++) {
				categories_ls.add(categories.getJSONObject(j).getString("title"));
			}
			store.put("categories", categories_ls);

			results_json.put(store);
		}

		return results_json;
	}

	public static ArrayList<Store> search(String term, String location, int limit) throws IOException {
		String search_url = build_url(term, location, limit);

		// setting up connection
		URL url = new URL(search_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

		// extracting target JSONArray
		JSONArray results_json = extract_json(results.toString());

		// creating ArrayList of Store objects
		ArrayList<Store> store_arr = new ArrayList<>();
		for (int i = 0; i < results_json.length(); i++) {
			JSONObject store = results_json.getJSONObject(i);
			store_arr.add(
					new Store(
							store.getString("name"), store.getString("address"),
							store.getString("price"), store.getString("profile_url"),
							(ArrayList<String>) store.get("categories"), store.getDouble("rating"),
							store.getInt("reviews")
					)
			);
		}

		conn.disconnect();

		return store_arr;
	}
}
