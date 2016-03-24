package foop;

import java.lang.*;
import java.util.*;
import foop.*;
import java.util.Collections;

class Dealer{
	public PlayerInfo info;
	private ArrayList<Card> deck;

	Dealer(String name){
		this.info = new PlayerInfo(name, -1);
		this.deck = new ArrayList<Card>();
	}

	public void refresh(){
		this.info.refresh();
	}

	public void getNewDeck(){
		deck.clear();
		for(byte suit = Card.SPADE; suit <= Card.CLUB; suit++){
			for(byte value = Card.VALUE_LOWER; value <= Card.VALUE_UPPER; value++){
				deck.add(new Card(suit, value));
			}
		}
		Collections.shuffle(deck);
	}

	public Card dealCard(){
		Card card = new Card((byte)1, (byte)1);
		try{
			card = this.deck.remove(0);
		}catch(Exception e){
			System.out.printf("[dealCard] error!!\n");
			System.exit(0);
		}
		return card;
	}

	public void dealCards(ArrayList<PlayerInfo> playerInfoTable){
		for(int i = 0; i < playerInfoTable.size(); i++){
			playerInfoTable.get(i).faceUp.add(dealCard());
			playerInfoTable.get(i).faceDown.add(dealCard());
		}
		this.info.faceUp.add(dealCard());
		this.info.faceDown.add(dealCard());
	}
}