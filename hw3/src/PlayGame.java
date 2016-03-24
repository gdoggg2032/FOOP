import java.lang.*;

public class PlayGame{
	public static void main(String[] argv){
		String GameType = argv[0];
		System.out.println(GameType);
		OldMaid Game;
		if(GameType.equals("Classical"))
			Game = new OldMaid("Classical");
		else if(GameType.equals("VarianceOne"))
			Game = new VarianceOne("VarianceOne");
		else if(GameType.equals("VarianceTwo"))
			Game = new VarianceTwo("VarianceTwo");
		else if(GameType.equals("VarianceThree"))
			Game = new VarianceThree("VarianceThree");
		else
			Game = new OldMaid("Classical");
			//default
		Game.Play();
	}
}