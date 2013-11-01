package leaderboardext;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
//import java.sql.PreparedStatement;
import java.sql.Statement;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

public class LeaderboardHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User player, ISFSObject params) {
		String func = params.getUtfString("func");
		if (func.equals("submitScore")) handleSubmitScore(player, params);
		if (func.equals("getRankingPosition")) handleGetRankingPosition(player, params);
		if (func.equals("getRankedList")) handleGetRankedList(player, params);
/*
		ISFSObject rtn = new SFSObject();
		rtn.putUtfString("func", "mytest");
		LeaderboardExtension parentEx = (LeaderboardExtension) getParentExtension();
		parentEx.send("leaderboard",  rtn,  player);
*/
	}

	// client params: gameID, playerID, scoreEntries, userData
	void handleSubmitScore(User player, ISFSObject params) {
		//int gameID
		String gameID = toSafeString(params.getUtfString("gameID"), 30);
		String playerID = toSafeString(params.getUtfString("playerID"), 30);
		ISFSObject userData = params.getSFSObject("userData");

		ISFSArray scoreEntries = params.getSFSArray("scoreEntries");
		String names = "";
		String values = "";
		for (int i=0; i<scoreEntries.size(); i++) {
			if (i>0) {
				names += ',';
				values += ',';
			}
			names += toSafeString(scoreEntries.getSFSObject(i).getUtfString("name"), 30);
			values += scoreEntries.getSFSObject(i).getInt("value"); // only returning int, so is safe
		}

		// enter new data in database 
		execMySQLUpdate("INSERT INTO mygame." + gameID + " (playerID," + names + ") VALUES (\'" + playerID + "\'," + values + ");");
		
		ISFSObject rtn = new SFSObject();
		rtn.putUtfString("func",  "submitScore");
		rtn.putInt("position",  44);
		
		LeaderboardExtension parentEx = (LeaderboardExtension) getParentExtension();
		parentEx.send("leaderboard",  rtn,  player);
	}

	// client params: gameID, playerID, scoreName='highscore', bool higherIsBetter=true
	void handleGetRankingPosition(User player, ISFSObject params) {
		String gameID = toSafeString(params.getUtfString("gameID"), 30);
		String playerID = toSafeString(params.getUtfString("playerID"), 30);
		String orderBy = toSafeString(params.getUtfString("orderBy"), 30);
		boolean higherIsBetter = params.getBool("higherIsBetter");

		// query database 
		String qustr = "SELECT x.position FROM (SELECT mygame." + gameID + ".highscore,mygame." + gameID + ".playerID,@rownum:=@rownum+1 AS POSITION from mygame." + gameID + " JOIN (select @rownum:=0) r ORDER by mygame." + gameID + "." + orderBy + (higherIsBetter?" DESC":"") +") x WHERE x.playerID=\'" + playerID + "\' order by position limit 1;";
		SFSArray result = execMySQLQuery(qustr);

		// extract answer  TODO: the result is double? should be int
		ISFSObject rtn = new SFSObject();
		rtn.putUtfString("func",  "getRankingPosition");
		rtn.putInt("position",  (int) (result.getSFSObject(0).getDouble("POSITION")+0.4999));
		
		LeaderboardExtension parentEx = (LeaderboardExtension) getParentExtension();
		parentEx.send("leaderboard",  rtn,  player);
	}

	// client params: gameID, rankStart, rankEnd, scoreName='highscore'
	void handleGetRankedList(User player, ISFSObject params) {
		String gameID = toSafeString(params.getUtfString("gameID"), 30);
		int rankStart = 0; if (params.getInt("rankStart") != null) rankStart = params.getInt("rankStart");
		int rankEnd = 0; if (params.getInt("rankEnd") != null) rankEnd = params.getInt("rankEnd");
		String orderBy = "highscore"; if (params.getUtfString("orderBy") != null) orderBy = toSafeString(params.getUtfString("orderBy"), 30);

        try {
            //get a connection to the database
            Connection conn = getParentExtension().getParentZone().getDBManager().getConnection();

            //This will strip potential SQL injections
            //PreparedStatement sql = conn.prepareStatement("SELECT * FROM mygame.game1 ORDER BY highscore DESC LIMIT 1;");
            String qustr = "SELECT * FROM mygame." + gameID + " ORDER BY " + orderBy + " DESC" + ((rankStart+rankEnd > 0) ? (" LIMIT " + rankStart + "," + rankEnd) : "") + ";";
            //PreparedStatement sql = conn.prepareStatement(qustr);
            //ResultSet result = sql.executeQuery();
            Statement sql = conn.createStatement();
            ResultSet result = sql.executeQuery(qustr);

            //Put the result into an SFSobject array
            SFSArray rows = SFSArray.newFromResultSet(result);
            
            conn.close();

            ////////////////////
            
            ISFSObject rtn = new SFSObject();
    		rtn.putUtfString("func",  "getRankedList");
    		rtn.putSFSArray("list", rows);
    		
    		LeaderboardExtension parentEx = (LeaderboardExtension) getParentExtension();
    		parentEx.send("leaderboard",  rtn,  player);
        } catch (SQLException e) {
        	trace(ExtensionLogLevel.WARN, " SQL Failed: " + e.toString());
        }
	}
	
	SFSArray execMySQLQuery(String queryString) {
	    try {
	        //get a connection to the database
	        Connection conn = getParentExtension().getParentZone().getDBManager().getConnection();
	
	        //PreparedStatement sql = conn.prepareStatement(queryString);
	        //ResultSet result = sql.executeQuery();
	        Statement sql = conn.createStatement();
	        ResultSet result = sql.executeQuery(queryString);

            //Put the result into an SFSobject array
            SFSArray res = SFSArray.newFromResultSet(result);

            conn.close();
            return res;
	    } catch (SQLException e) {
	    	trace(ExtensionLogLevel.WARN, " SQL Failed: " + e.toString());
	    }
	    return null;
	}

	void execMySQLUpdate(String updateString) {
	    try {
	        //get a connection to the database
	        Connection conn = getParentExtension().getParentZone().getDBManager().getConnection();
	
	        //PreparedStatement sql = conn.prepareStatement(updateString);
	        //sql.executeUpdate();
	        Statement sql = conn.createStatement();
	        sql.executeUpdate(updateString);

            conn.close();
	    } catch (SQLException e) {
	    	trace(ExtensionLogLevel.WARN, " SQL Failed: " + e.toString());
	    }
	}

	public static String toSafeString(String s, int maxLength) { // this should return a string that is safe to use in sql inputs, preventing sql injection
		if (s == null) return null;

		int len = s.length();
		if (len > maxLength) len = maxLength;
		StringBuilder res = new StringBuilder(len);
		for (int i=0; i<len; i++) {
			char ch = s.charAt(i);
			if (   ((ch >= 'a') && (ch <= 'z')) // be very careful when changing this! besides ',\,; there are also comment charaters etc.
				|| ((ch >= 'A') && (ch <= 'Z'))
				|| ((ch >= '0') && (ch <= '9'))
				|| (ch == '.')  )
			res.append(ch);
		}

		return res.toString();
	}

}
