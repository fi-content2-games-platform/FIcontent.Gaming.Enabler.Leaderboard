// posting to social network
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Date;
import java.util.Calendar;

class SocialNetwork {
	public static void post(String socialnetwork, String gameID, String playerID, int newHighScore) {
		if (LeaderboardREST.DEBUG) System.out.println("--> to Social Network: " + gameID + " " + playerID + " " + newHighScore);

		try {
			URL obj = new URL(socialnetwork);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			//con.setRequestProperty("User-Agent", USER_AGENT);
			//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");

			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			long timestamp = calendar.getTimeInMillis();

			String requestBody = "{\n"
				+ " \"_id\":\"doc_id_" + gameID + "_" + newHighScore + "\",\n"
				+ " \"created\":" + timestamp + ",\n"
				+ " \"msg\":\"new highscore: " + newHighScore + " points in game " + gameID + " by player " + playerID + "!\",\n"
				+ " \"user\":{\"id\":\"0815\",\"name\":\"Leaderboard Server\"},\"type\":\"POST\"\n"
				+ "}";
			String urlParameters = "";

			// Send post request
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(urlParameters);
			con.setRequestProperty("Content-Length", "" + Integer.toString(requestBody.getBytes().length));
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(requestBody.getBytes());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (LeaderboardREST.DEBUG) {
				System.out.println("\nSending 'POST' request to URL : " + socialnetwork);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Post body : " + requestBody);
				System.out.println("Response Code : " + responseCode);
			}

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			if (LeaderboardREST.DEBUG) System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println("posting to Social Network failed! " + e.getMessage());
		}
	}
}