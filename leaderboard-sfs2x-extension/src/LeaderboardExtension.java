package leaderboardext;

import com.smartfoxserver.v2.extensions.SFSExtension;

public class LeaderboardExtension extends SFSExtension {

	@Override
	public void init() {
		this.addRequestHandler("leaderboard", LeaderboardHandler.class);

	}

}
