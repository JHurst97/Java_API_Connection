
import java.net.URI;
import java.net.http.*;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver {

	public static void main(String[] args) {
		System.out.println("Enter name of a Pokemon:");
		
		selectPokemon();
	}
	
	public static void selectPokemon() {
		Scanner scan = new Scanner(System.in); String name = scan.next();
		 
		System.out.println(name);
		//String name = "ditto";
		try {
			getInfo(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("pokemon not found! try another:");
			selectPokemon();
		}
	}

	public static void getInfo(String name_) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + name_))
				.build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
				.thenApply(Driver::Parse).join();
	}

	public static String Parse(String responseBody) {
		JSONObject pokemon = new JSONObject(responseBody);
		int id = pokemon.getInt("id");
		int weight = pokemon.getInt("weight");
		int height = pokemon.getInt("height");
		System.out.println("id: " + id + " -=- Weight: " + weight + " -=- height: " + height);

		// abilities
		System.out.println("Abilities:");
		JSONArray abilities = pokemon.getJSONArray("abilities");
		// loop through each ability
		for (int i = 0; i < abilities.length(); i++) {
			JSONObject ability = abilities.getJSONObject(i);
			JSONObject abilityInfo = ability.getJSONObject("ability");
			String name = abilityInfo.getString("name");
			String abilityURL = abilityInfo.getString("url");
			System.out.println(name);
			// get specific info about ability from a diff endpoint.
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(abilityURL)).build();
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenApply(Driver::GetAbilityInfo).join();
		}

		return null;
	}

	// gets info on specific ability from the API ability endpoint.
	public static String GetAbilityInfo(String responseBody) {
		JSONObject ability = new JSONObject(responseBody);
		JSONArray effect_entries = ability.getJSONArray("effect_entries");
		JSONObject effectEntryEng = effect_entries.getJSONObject(1);
		String effect = effectEntryEng.getString("effect");
		System.out.println(effect);
		return null;
	}

}
