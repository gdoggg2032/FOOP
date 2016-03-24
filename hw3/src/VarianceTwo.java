import java.lang.*;
import java.util.*;

class VarianceTwo extends OldMaid{

	// this variance is the one win with remaining cards
	//players with no hands lose
	
	public VarianceTwo(String gameType){
		super(gameType);
	}
	
	protected void loadRules(){

		this.CannotDrop.add(new Card(0, 0));
		this.CannotDrop.add(new Card(1, 0));

		//add two jokers in CannotDrop
		this.WinRule = 1;
		//win with remaining cards
	}
}