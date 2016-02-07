import java.lang.String;

//represents a ScoreEntry object

public class ScoreEntry {
	
	private String name;
	private int value;
	
	//constructor
	public ScoreEntry(String a, int b) {
		setScoreName(a);
	    setScoreValue(b);
	}

	public int getScoreValue() {
		return value;
	}

	public void setScoreValue(int value) {
		this.value = value;
	}

	public String getScoreName() {
		return name;
	}

	public void setScoreName(String name) {
		this.name = name;
	}
	
}
	
