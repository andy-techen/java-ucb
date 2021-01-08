import com.sun.javafx.fxml.builder.URLBuilder;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import io.github.cdimascio.dotenv.Dotenv;

public class Results {
	static Dotenv env = Dotenv.load();

	public static String build_url(String term, String location, int limit) {
		String yelp_api = "https://api.yelp.com/v3/businesses/search?term={0}&location={1}&limit={2}&open_now=true";
		String search_url = MessageFormat.format(yelp_api, term, location, limit);
		System.out.println(search_url);

		System.out.println(System.getProperty("user.dir"));
		System.out.println(env.get("YELP_API_KEY"));

		return search_url;
	}
	public static void search(String term, String location, int limit) throws IOException {
		String search_url = build_url(term, location, limit);

		URL url = new URL(search_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Authorization", "Bearer " + env.get("YELP_API_KEY"));
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuffer results = new StringBuffer();
		while ((line = br.readLine()) != null) {
			results.append(line);
		}
		br.close();

		System.out.println(results.toString());

		conn.disconnect();
	}

	public static void main(String[] args) throws IOException {
		search("Ramen", "Taipei", 10);
	}
}
