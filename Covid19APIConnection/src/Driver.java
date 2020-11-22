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
			JSONArray countryInfo = new JSONArray(response.body());
			JSONObject country = countryInfo.getJSONObject(0);
			
			String cName = country.getString("country");
			String code = country.getString("code");
			int confirmed = country.getInt("confirmed");
			int recovered = country.getInt("recovered");
			int critical = country.getInt("critical");
			int deaths = country.getInt("deaths");
			int latitude = country.getInt("latitude");
			int longitude = country.getInt("longitude");
			String lastChange = country.getString("lastChange");
			String lastUpdate = country.getString("lastUpdate");

			System.out.println("Here are the Covid-19 stats for: \"" + name + "\"");
			System.out.println("Name: " + cName);
			System.out.println("Code: " + code);
			System.out.println("Confirmed: " + confirmed);
			System.out.println("Recovered: " + recovered);
			System.out.println("Critical: " + critical);
			System.out.println("Deaths: " + deaths);
			System.out.println("Latitude: " + latitude);
			System.out.println("Longitude: " + longitude);
			System.out.println("Last change: " + lastChange);
			System.out.println("Last update: " + lastUpdate);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
