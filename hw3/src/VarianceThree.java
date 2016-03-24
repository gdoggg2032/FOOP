import java.lang.*;
import java.util.*;

class VarianceThree extends OldMaid{

	// this variance is just more than 4 players
	
	public VarianceThree(String gameType){
		super(gameType);
	}
	
	protected void loadRules(){

		this.CannotDrop.add(new Card(0, 0));
		this.CannotDrop.add(new Card(1, 0));

		//add two jokers in CannotDrop
		this.WinRule = 0;
		//win with no cards

		this.PlayerNumber = 5;
		//this game has 5 players!!
	}
}