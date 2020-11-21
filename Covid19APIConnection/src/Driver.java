import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver {

	public static void main(String[] args) throws Exception {
		selectCountry();
		
	}
	
	public static void selectCountry() {
		System.out.println("Enter name of a country:");
		Scanner scan = new Scanner(System.in); 
		String name = scan.next();
		System.out.println(name);
			getInfo(name);
		scan.close();
	}

	private static void getInfo(String name) {
		// TODO Auto-generated method stub
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://covid-19-data.p.rapidapi.com/country?name=" + name))
				.header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
				.header("x-rapidapi-key", "53938677fcmsh0c078c613b2637bp12a574jsne447af7f82aa")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			JSONArray countryInfo = new JSONArray(response.body());
			JSONObject country = countryInfo.getJSONObject(0);
			int deaths = country.getInt("deaths");
				System.out.println(deaths);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
