import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.*;

public class NewMethod {

	public static void main(String[] args) {

		//method 2: java.net.http.HttpClient
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(NewMethod::Parse)
		.join();
		

	}
	
	public static String Parse(String responseBody) {
		JSONArray albums = new JSONArray(responseBody);
		for (int i = 0; i < albums.length(); i++) {
			JSONObject album = albums.getJSONObject(i);
			int id = album.getInt("id");
			int userId = album.getInt("userId");
			String title = album.getString("title");
			System.out.println("id: " + id + " -=- userId: " + userId + " -=- title: " + title);
		}
		return null;
	}

}
