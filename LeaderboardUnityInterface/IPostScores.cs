using System.Collections.Generic;

public interface IPostScores
{
    string PostScores(Score score);
    Scores GetScores();
}

public class Scores : List<Score>
{ }

public struct Score
{
    public readonly string name;
    public readonly long score;
    public readonly int rank;

    public Score(string name, long score)
        : this(name, score, -1)
    { }
    public Score(string name, long score, int rank)
    {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }
	
	public override string ToString ()
	{
		return "Score: " + this.score + ", Name: " + this.name + ", Rank: " + this.rank;
	}
}