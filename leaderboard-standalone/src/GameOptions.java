// mysql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Blob;

class GameOptions {
	static public GameOptions getGameOptions(String gameID, Connection con) throws SQLException {
		// TODO: look in cache
		// TODO: if not in cache, make new one and put in cache

// just quick hack for now: always generate new object
		return new GameOptions(gameID, con);
	}


	//protected String gameID;
	public GameOptions(String gameID, Connection con) throws SQLException {
		//this.gameID = gameID;

		//// read options from $options table in database
		//Connection con = DriverManager.getConnection(LeaderboardREST.settings.url, LeaderboardREST.settings.user, LeaderboardREST.settings.password);
		Statement st = con.createStatement();
		onlyKeepBestEntry = false;
		maxEntries = 0;
		String qustr = "SELECT * FROM mygame.$options WHERE game = \'" + gameID + "\';";
		if (LeaderboardREST.DEBUG) System.out.println(qustr + "\n");
		ResultSet rs = st.executeQuery(qustr);
		ResultSetMetaData rsmd = rs.getMetaData();
		//int columnCount = rsmd.getColumnCount();
		if (rs.next()) { // only first position
			maxEntries = rs.getInt("maxEntries");
			onlyKeepBestEntry = rs.getBoolean("onlyKeepBestEntry");
			addUpScore = rs.getBoolean("addUpScore");
			socialnetwork = rs.getString("socialnetwork");
		}
		if (LeaderboardREST.DEBUG) {
			System.out.println("socialnetwork: " + socialnetwork);
			//System.out.println(result.toString() + "\n");
		}
		//// end read options from database
	}

	protected int maxEntries;
	public int getMaxEntries() { return maxEntries; }

	protected String socialnetwork;
	public String getSocialNetwork() { return socialnetwork; }

	protected boolean onlyKeepBestEntry;
	public boolean getOnlyKeepBestEntry() { return onlyKeepBestEntry; }
	
	protected boolean addUpScore;
	public boolean getAddUpScore() { return addUpScore; }
}
