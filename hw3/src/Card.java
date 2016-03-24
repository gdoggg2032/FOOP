import java.lang.*;
import java.util.*;


class Card implements Comparator<Card>{

	public int Suit;
	public int Number;
	private String[] SuitRank = {"R","B","C","D","H","S"};
	private String[] NumberRank = {"0","2","3","4","5","6","7","8","9","10","J","Q","K","A"};

	public Card(){};

	public Card(int suit, int number){
		this.Suit = suit;
		this.Number = number;
	}

	public String toString(){
		return SuitRank[this.Suit] + NumberRank[this.Number];
	}

	public int compare(Card a, Card b){
		return (a.Number - b.Number) * 6 + a.Suit - b.Suit;
	}

	public boolean ispairOf(Card another){
		return this.Number == another.Number;
	}


	public boolean notIn(ArrayList<Card> Cards){
		for(int i = 0; i < Cards.size(); i++){
			if(compare(this, Cards.get(i)) == 0)
				return false;
		}
		return true;
	}
}