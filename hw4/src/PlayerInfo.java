package foop;

import java.lang.*;
import java.util.*;
import foop.*;

class PlayerInfo{
	public String playerName;
	public String status;
	public ArrayList<Card> faceUp;
	protected ArrayList<Card> faceDown;
	public int bet;
	public boolean insurance;
	public int id;

	PlayerInfo(String playerName, int id){
		this.playerName = playerName;
		this.id = id;
		this.bet = 0;
		this.insurance = false;
		this.status = "OK"; // OK | Surrender | Busted
		this.faceUp = new ArrayList<Card>();
		this.faceDown = new ArrayList<Card>();
	}
	public void refresh(){
		this.status = "OK";
		this.bet = 0;
		this.insurance = false;
		this.faceUp.clear();
		this.faceDown.clear();
	}
	public int minValue(){
		int value = 0;
		for(int i = 0; i < this.faceUp.size(); i++){
			int number = this.faceUp.get(i).getValue();
			if(number > 10)
				number = 10;
			value += number;
		}
		return value;
	}

	public int maxValue(){
		int value = 0;
		for(int i = 0; i < this.faceUp.size(); i++){
			int number = this.faceUp.get(i).getValue();
			if(number > 10)
				number = 10;
			if(number == 1)
				number = 11;
			value += number;
		}
		return value;
	}

	public int getValue(){
		if(maxValue() <= 21)
			return maxValue();
		else
			return minValue();
	}

	public void refreshStatus(){
		if(minValue() > 21)
			this.status = "Busted";
	}

	public boolean softSafe(){
		int min = minValue();
		int max = maxValue();
		if(max <= 16 || (min != max && min == 17))
			return true;
		else
			return false;
	}



	
}