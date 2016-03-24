import java.lang.*;
import java.util.*;

class VarianceOne extends OldMaid{

	// this variance is no jokers and randomly pick two number up
	//players would not know the jokers
	public VarianceOne(String gameType){
		super(gameType);
	}

	protected void loadRules(){

		ArrayList<Card> TmpDeck = CardInitialize();
		Card card1 = TmpDeck.remove(0);
		Card card2 = TmpDeck.remove(0);
		this.DropCards.add(card1);
		this.DropCards.add(card2);
		//System.out.printf("[System] Pick up %s, %s\n", card1, card2);
		//add two jokers in CannotDrop
		this.WinRule = 0;
	}
}