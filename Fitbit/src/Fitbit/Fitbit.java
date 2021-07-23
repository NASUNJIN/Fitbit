package Fitbit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.*;
import java.io.IOException;

public class Fitbit {
	public static void main (String[] args) { 
		String access_token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMzlaUDYiLCJzdWIiOiI5REs4UTkiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJzZXQgcmFjdCBybG9jIHJ3ZWkgcmhyIHJudXQgcnBybyByc2xlIiwiZXhwIjoxNjI0NTc0MzU2LCJpYXQiOjE2MjQ1NDU1NTZ9.8jvI6j_oxA2zxPALo2IYVkBm-ZZKEVHQce9UUbBxoJI";
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();
//		Request request = new Request.Builder()
//				  .url("https://api.fitbit.com/1/user/-/profile.json")
//				  .method("GET", null)
//				  .addHeader("Authorization", "Bearer " + access_token)
//				  .build();
		
		Request request = new Request.Builder()
				  .url("https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1min.json")
				  .method("GET", null)
				  .addHeader("Authorization", "Bearer " + access_token)
				  .build();
				
		try (Response response = client.newCall(request).execute()) {
		
			JSONObject obj = new JSONObject(response.body().string());
			JSONArray arr = obj.getJSONArray("activities-heart");
			
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				String s = o.getString("dateTime");
				System.out.println(s);

				JSONObject o1 = o.getJSONObject("value");
				System.out.println(o1);

				JSONArray a1 = o1.getJSONArray("heartRateZones");		
				System.out.println(a1);

				JSONObject o2 = a1.getJSONObject(0);
				System.out.println(o2);
				
				JSONArray keys = o2.names();

				for (int j = 0; j < keys.length(); j++) {
					String key = keys.getString(j); 
					Object oj = o2.get(key);
					if (oj instanceof Integer) {
						System.out.println(key + ": " + o2.getInt(key));
					} else if (oj instanceof Float) {
						System.out.println(key + ": " + o2.getFloat(key));
					} else if (oj instanceof String) {
						System.out.println(key + ": " + o2.getString(key));
					}
				}
			}

		} catch (IOException io) {
			io.printStackTrace(System.out);
		}
		
	}
}
