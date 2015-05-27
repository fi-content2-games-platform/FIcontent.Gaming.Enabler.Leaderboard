// configuration file
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class ServerSettings {
	final String url; // of mysql database, e.g. jdbc:mysql://localhost:3306/mygame
	final String user;
	final String password;
	final String logfile; // name of logfile

	public ServerSettings(String configFileName) {
		Properties prop = new Properties();
		//load a properties file
		try {
			prop.load(new FileInputStream(configFileName));
		} catch (IOException ex) {
			System.out.println("error reading config file: " + configFileName);
			System.exit(1);
		}

		//get the property values and print them
		url = prop.getProperty("url"); // jdbc:mysql://localhost:3306/mygame";
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		logfile = prop.getProperty("logfile");

		if (LeaderboardREST.DEBUG) {
			System.out.println("read these config values:");
			System.out.println("url: " + url);
			System.out.println("user: " + user);
			System.out.println("password: " + password);
			System.out.println("logfile: " + logfile);
                        System.out.flush();
		}
	}
}
