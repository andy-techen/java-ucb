import java.util.ArrayList;

public class Store {
	private String name, url, address, price, profile_url;
	private ArrayList<String> categories;
	private double rating;
	private int reviews;

	public Store(String name, String url, String address, String price, String profile_url, ArrayList<String> categories, double rating, int reviews) {
		this.name = name;
		this.url = url;
		this.address = address;
		this.price = price;
		this.profile_url = profile_url;
		this.categories = categories;
		this.rating = rating;
		this.reviews = reviews;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getAddress() {
		return address;
	}

	public String getPrice() {
		return price;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public double getRating() {
		return rating;
	}

	public int getReviews() {
		return reviews;
	}
}
