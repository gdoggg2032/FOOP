package foop;

import java.lang.*;
import java.util.*;
import foop.*;


class PlayerB01902032 extends Player {

	private boolean dealerBalckjack;
	private int initial_chips;
	public PlayerB01902032(int chips){
		super(chips);
		this.dealerBalckjack = false;
		this.initial_chips = chips;
	}

	// strategy from https://www.blackjackinfo.com/blackjack-basic-strategy-engine/?numdecks=1&soft17=h17&dbl=all&das=yes&surr=ns&peek=no
	public boolean buy_insurance(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
		this.dealerBalckjack = true;
		// never buy insurance
		return false;
	}
	public boolean do_double(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
		ArrayList<Card> handCards = my_open.getCards();
		if(checkTable(handCards, dealer_open) == "D")
			return true;

		return false;
	}
	public boolean do_split(java.util.ArrayList<Card> my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
		
		if(checkTable(my_open, dealer_open) == "P")
			return true;

		return false;
	}
	public boolean do_surrender(Card my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
		this.dealerBalckjack = false;
		// because we can not see the other card, so never surrender.
		return false;
	}
	public boolean hit_me(Hand my_open, Card dealer_open, java.util.ArrayList<Hand> current_table){
		ArrayList<Card> handCards = my_open.getCards();

		if(checkTable(handCards, dealer_open) == "H")
			return true;
		return false;
	}
	public int make_bet(java.util.ArrayList<Hand> last_table, int total_player, int my_position){
		//if(get_chips() > 1.0 * (double) this.initial_chips)
		//	return (int)(0.1 * get_chips());
		// always 1 bet
		return 1;
	}
	public String toString(){
		return "B01902032";
	}
	public void haha(){
		System.out.printf("haha\n");
		// haha for test~
	}



	private String checkTable( ArrayList<Card> my_open, Card dealer_open){
		boolean soft = false;
		boolean pairs = checkPairs(my_open);
		if(pairs == true){
			int value = (int)my_open.get(0).getValue();
			if(value >= 10)
				value = 10;
			int dvalue = (int)dealer_open.getValue();
			if(dvalue >= 10)
				dvalue = 10;
			switch(value){
				case 1:
					if(dvalue == 1)
						return "H";
					else
						return "P";
				case 2:
					if(dvalue >= 2 && dvalue <= 7)
						return "P";
					else
						return "H";
				case 3:
					if(dvalue >= 2 && dvalue <= 8)
						return "P";
					else
						return "H";
				case 4:
					if(dvalue >= 4 && dvalue <= 6)
						return "P";
					else
						return "H";
				case 5:
					if(dvalue >= 2 && dvalue <= 9)
						return "D";
					else
						return "H";
				case 6:
					if(dvalue >= 2 && dvalue <= 7)
						return "P";
					else
						return "H";
				case 7:
					if(dvalue >= 2 && dvalue <= 8)
						return "P";
					else if(dvalue == 10)
						return "S";
					else
						return "H";
				case 8:
					if(dvalue >= 2 && dvalue <= 9)
						return "P";
					else
						return "H";
				case 9:
					if(dvalue ==7 || dvalue == 10)
						return "S";
					else
						return "P";
				case 10:
					return "S";
			}


		}else{
			if(minValue(my_open) != maxValue(my_open))
				soft = true;
			if(soft == true){
				int value = 0;
				for(int i = 0; i < my_open.size(); i++){
					int v = (int)my_open.get(i).getValue();
					if(v != 1){
						if(v>=10)
							value += 10;
						else 
							value += v;
					}
				}
				int dvalue = (int)dealer_open.getValue();
				if(value > 10)
					value = 10;
				if(dvalue > 10)
					dvalue = 10;

				switch(value){
					
					case 6:
						if(dvalue >= 2 && dvalue <= 6)
							return "D";
						else
							return "H";
					case 7:
						if(dvalue >= 3 && dvalue <= 6)
							return "D";
						else if(dvalue >= 9 || dvalue == 1 )
							return "H";
						else
							return "S";
					case 8:
						if(dvalue == 6)
							return "D";
						else
							return "S";
					case 9:
						return "S";
					case 10:
						return "S";
					default: // 2,3,4,5
						if(dvalue<=4 && dvalue <= 6)
							return "D";
						else
							return "H";


				}


			}else{
				int value = maxValue(my_open);
				int dvalue = (int)dealer_open.getValue();
				if(dvalue >10)
					dvalue = 10;
				if(value>=1 && value <= 7){
					return "H";
				}else if(value == 8){
					if(dvalue == 5 || dvalue == 6)
						return "D";
					else
						return "H";
				}else if(value == 9){
					if(dvalue >= 2 && dvalue <= 6)
						return "D";
					else
						return "H";
				}else if(value == 10 || value == 11){
					if(dvalue >= 2 && dvalue <= 9)
						return "D";
					else
						return "H";
				}else if(value == 12){
					if(dvalue>=4 && dvalue <=6)
						return "S";
					else
						return "H";
				}else if(value >= 13 && value <= 16){
					if(dvalue >= 2 && dvalue <= 6)
						return "S";
					else
						return "H";
				}else if(value >= 17){
					return "S";
				}

			}
		}
		return "";
	}

	private boolean checkPairs(ArrayList<Card> handCards){
		if(handCards.size()!=2)
			return false;
		Card card1 = handCards.get(0);
		Card card2 = handCards.get(1);
		if(card1.getValue() >= 10 && card2.getValue() >= 10)
			return true;
		if(card1.getValue() == card2.getValue())
			return true;

		return false;
	}


	private int maxValue(ArrayList<Card> handCards){
		int value = 0;
		for(int i = 0; i < handCards.size(); i++){
			int number = handCards.get(i).getValue();
			if(number > 10)
				number = 10;
			if(number == 1)
				number = 11;
			value += number;
		}
		return value;
	}
	private int minValue(ArrayList<Card> handCards){
		int value = 0;
		for(int i = 0; i < handCards.size(); i++){
			int number = handCards.get(i).getValue();
			if(number > 10)
				number = 10;
			value += number;
		}
		return value;
	}


	// public final void decrease_chips(double diff) throws Player.NegativeException, Player.BrokeException
	// protected final double get_chips()
	// public final void decrease_chips(double diff) throws Player.NegativeException, Player.BrokeException
	// public final void increase_chips(double diff) throws Player.NegativeException
}