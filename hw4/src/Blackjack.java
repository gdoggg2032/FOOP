package foop;

import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import foop.*;

class Blackjack{

	private int nRound;
	private int nPlayer;
	private ArrayList<Player> players;
	private ArrayList<Hand> current_table;

	private Dealer dealer;

	private ArrayList<PlayerInfo> playerInfoTable;

	private String[] Suits = {"S", "H", "D", "C"};
	private String[] Values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};






	Blackjack(int nRound, ArrayList<Player> players){
		this.nRound = nRound;
		this.nPlayer = players.size();
		this.players = players;
		this.current_table = new ArrayList<Hand>();
		this.dealer = new Dealer("Dealer");
		this.playerInfoTable = new ArrayList<PlayerInfo>();
		for(int i = 0; i < this.nPlayer; i++){
			playerInfoTable.add(new PlayerInfo(players.get(i).toString(), i));
		}
		System.out.println("Number of players: " + this.nPlayer);
	}

	private void resetPlayerInfoTable(){
		for(int i = 0; i < this.nPlayer; i++){
			this.playerInfoTable.get(i).refresh();
			if(i>0 && this.playerInfoTable.get(i).id == this.playerInfoTable.get(i-1).id){

				double allChips = players.get(i).get_chips();
				try{
					players.get(i-1).increase_chips(allChips);
				}catch(Player.NegativeException e){
					System.out.printf("[resetPlayerInfoTable negative]There is something wrong, it cannot happen!\n");
					System.exit(0);
				}
				
				playerInfoTable.remove(i);
				players.remove(i);
				this.nPlayer--;
			}
		}
	}

	private void doBet(){
		for(int i = this.nPlayer - 1; i >= 0; i--){
			int bet = players.get(i).make_bet(this.current_table, this.nPlayer, i);
			if(bet == 0){
				System.out.printf("[doBet]Can not bet 0 dollar and just play for fun.\n");
				System.out.printf("[doBet]Player %s must quit.\n", players.get(i).toString());
				players.remove(i);
				playerInfoTable.remove(i);
				this.nPlayer--;
				continue;
			}
			try{
				players.get(i).decrease_chips((double) bet);
				this.playerInfoTable.get(i).bet = bet;
				System.out.printf("[doBet]Player %s bets %d!\n", players.get(i).toString(), bet);
			}catch( Player.BrokeException e){
				System.out.printf("[doBet]Player %s cannot afford this bet and must quit.\n", players.get(i).toString());
				players.remove(i);
				playerInfoTable.remove(i);
				this.nPlayer--;
			}catch(Player.NegativeException e){
				System.out.printf("[doBet negative]There is something wrong, it cannot happen!\n");
				System.exit(0);
			}
		}
	}

	private void askInsurance(){
		if(dealer.info.faceUp.get(0).getValue() == Card.VALUE_LOWER){
			for(int i = this.nPlayer - 1; i >= 0; i--){
				boolean answer = players.get(i).buy_insurance(playerInfoTable.get(i).faceUp.get(0), dealer.info.faceUp.get(0), this.current_table);
				if(answer == true){
					try{
						players.get(i).decrease_chips(0.5 * (double) playerInfoTable.get(i).bet);
						playerInfoTable.get(i).insurance = true;
						System.out.printf("[askInsurance]Player %s buy an insurance.\n", players.get(i).toString());
					}catch(Player.BrokeException e){
						System.out.printf("[askInsurance]Player %s is a madman and must quit.\n", players.get(i).toString());
						players.remove(i);
						playerInfoTable.remove(i);
						this.nPlayer--;
					}catch(Player.NegativeException e){
						System.out.printf("[askInsurance negative]There is something wrong, it cannot happen!\n");
						System.exit(0);
					}
				}
			}
		}
	}

	private void refreshCurrentTable(){
		this.current_table.clear();
		//System.out.printf("[refreshCurrentTable] nPlayer: %d\n", this.nPlayer);
		for(int i = 0; i < this.nPlayer; i++){
			this.current_table.add(new Hand(playerInfoTable.get(i).faceUp));
		}
	}

	private boolean checkBlackjack(ArrayList<Card> faceUp, ArrayList<Card> faceDown){
		ArrayList<Card> total = new ArrayList<Card>();
		total.addAll(faceUp);
		total.addAll(faceDown);
		if(total.size() == 2 && 
			((total.get(0).getValue() == (byte) 1 && total.get(1).getValue() > (byte) 9) ||
			(total.get(1).getValue() == (byte) 1 && total.get(0).getValue() > (byte) 9))){
			return true;
		}
		else
			return false;
	}
	private boolean checkBlackjack(ArrayList<Card> faceUp){
		ArrayList<Card> total = new ArrayList<Card>();
		total.addAll(faceUp);
		if(total.size() == 2 && 
			((total.get(0).getValue() == (byte) 1 && total.get(1).getValue() > (byte) 9) ||
			(total.get(1).getValue() == (byte) 1 && total.get(0).getValue() > (byte) 9))){
			return true;
		}
		else
			return false;
	}

	private void askSurrender(){
		for(int i = 0; i < nPlayer; i++){
			boolean answer = players.get(i).do_surrender(playerInfoTable.get(i).faceUp.get(0), dealer.info.faceUp.get(0), this.current_table);
			if(answer == true){
				playerInfoTable.get(i).status = "Surrender";
				System.out.printf("[askSurrender]Player %s[%d] Surrender!\n", players.get(i).toString(), playerInfoTable.get(i).id);
			}
		}
	}

	private void flipout(PlayerInfo info){
		info.faceUp.addAll(info.faceDown);
		info.faceDown.clear();
	}

	private boolean ckeckSameValue(ArrayList<Card> faceUp){
		if(faceUp.get(0).getValue() == faceUp.get(1).getValue() || 
			(faceUp.get(0).getValue() > 9 && faceUp.get(1).getValue() > 9)){
			return true;
		}
		else
			return false;
	}
	private void askSplit(int position) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{

		boolean answer = players.get(position).do_split(playerInfoTable.get(position).faceUp, dealer.info.faceUp.get(0), this.current_table);
		if(answer == true){
			
			int bet = playerInfoTable.get(position).bet;
			try{
				players.get(position).decrease_chips(2*bet);
				// you need give split_player double bet to double down

				Class<?> Playeri = players.get(position).getClass();
				players.add(position+1, (Player) Playeri.getDeclaredConstructor(int.class).newInstance(bet));
				
				PlayerInfo splited_info = new PlayerInfo(playerInfoTable.get(position).playerName, playerInfoTable.get(position).id);
				splited_info.bet = bet;
				splited_info.faceUp.add(playerInfoTable.get(position).faceUp.remove(0));

				playerInfoTable.add(position+1, splited_info);
				this.nPlayer++;
				System.out.printf("[askSplit]Player %s[%d] Split!, card1 value: %d, card2 value: %d\n", 
					players.get(position).toString(), playerInfoTable.get(position).id,
					playerInfoTable.get(position).faceUp.get(0).getValue(),
					playerInfoTable.get(position+1).faceUp.get(0).getValue());
			}catch(Player.BrokeException e){
				System.out.printf("[askSplit]You don't have enough money to split.\n");
				players.remove(position);
				playerInfoTable.remove(position);
				this.nPlayer--;
			}catch(Player.NegativeException e){
				System.out.printf("[askSplit negative] It can not happen!!\n");
				System.exit(0);
			}
			
		}
	}
	private boolean askDoubleDown(int position){
		if(players.get(position).do_double(new Hand(playerInfoTable.get(position).faceUp), dealer.info.faceUp.get(0), this.current_table)){
			int bet = playerInfoTable.get(position).bet;

			try{
				players.get(position).decrease_chips(bet);
				playerInfoTable.get(position).faceUp.add(dealer.dealCard());
				playerInfoTable.get(position).bet *= 2;
				playerInfoTable.get(position).refreshStatus();
				System.out.printf("[askDoubleDown]Player %s[%d] Double Down! total bet: %d\n", players.get(position).toString(), playerInfoTable.get(position).id, playerInfoTable.get(position).bet);
				return true;

			}catch(Player.BrokeException e){
				System.out.printf("[askDoubleDown]You don't have enough money to doubledown.\n");
				players.remove(position);
				playerInfoTable.remove(position);
				this.nPlayer--;
				return false;
			}catch(Player.NegativeException e){
				System.out.printf("[askDoubleDown negative] It can not happen!!\n");
				System.exit(0);
			}
		}
		return false;
	}

	private void doResulting(){
		System.out.printf("*********************************\n");
		System.out.printf("[doResulting]Resulting:\n");
		System.out.printf("Dealer: ");
		printPlayerInfo(dealer.info);
		System.out.printf("---------------------------------\n");
		for(int i = 0; i < nPlayer; i++){
			printPlayerInfo(playerInfoTable.get(i));
			double reward;
			double bet = (double) playerInfoTable.get(i).bet;
			String status = playerInfoTable.get(i).status;
			if(status == "Surrender"){
				reward = 0.5 * bet;
			}else if(status == "Busted"){
				reward = 0.0;
			}else{
				boolean dealerBlackjack = checkBlackjack(dealer.info.faceUp);
				boolean playerBlackjack = checkBlackjack(playerInfoTable.get(i).faceUp);
				if(playerBlackjack && dealerBlackjack) // push
					reward = bet;
				else if(playerBlackjack && !dealerBlackjack)
					reward = bet + 1.5 * bet;
				else if(!playerBlackjack && dealerBlackjack){
					reward = 0.0;
					if(playerInfoTable.get(i).insurance == true)
						reward = bet;
				}
				else{
					if(status == "OK" && dealer.info.status == "Busted")
						reward = bet + bet;
					else if(status == "Busted")
						reward = 0.0;
					else{
						int playerValue = playerInfoTable.get(i).getValue();
						int dealerValue = dealer.info.getValue();
						//System.out.printf("%d, %d\n",playerValue, dealerValue);
						if(playerValue > dealerValue)
							reward = bet + bet;
						else if(playerValue < dealerValue)
							reward = 0.0;
						else
							reward = bet; // push
					}
				}
			}
			try{
				players.get(i).increase_chips(reward);
			}catch(Player.NegativeException e){
				System.out.printf("[doResulting] It can not happen!!\n");
				System.exit(0);
			}
			System.out.printf("Player %s[%d] bets %f, reward %f, now: %f\n", players.get(i).toString(), playerInfoTable.get(i).id, bet, reward, players.get(i).get_chips());
			System.out.printf("---------------------------------\n");
			

		}
	}



	void play() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		for(int r = 0; r < this.nRound; r++){
			System.out.printf("[play]=============Round %d===============\n", r+1);
			dealer.refresh();
			resetPlayerInfoTable();
			dealer.getNewDeck();
			doBet();
			dealer.dealCards(playerInfoTable);
			refreshCurrentTable();
			System.out.printf("Dealer: ");
			printPlayerInfo(dealer.info);
			askInsurance();
			if(checkBlackjack(dealer.info.faceUp, dealer.info.faceDown) == false)
				askSurrender();
			System.out.printf("[play]-----player time-------\n");

			//players time
			for(int i = 0; i < nPlayer; i++){
				System.out.printf("[play]Player %s[%d] turn!\n", players.get(i).toString(), playerInfoTable.get(i).id);
				if(playerInfoTable.get(i).status == "Surrender")
					continue;
				flipout(playerInfoTable.get(i));
				System.out.printf("[play]Player %s[%d] flip out!\n", players.get(i).toString(), playerInfoTable.get(i).id);
				printPlayerInfo(playerInfoTable.get(i));
				refreshCurrentTable();
				if(playerInfoTable.get(i).faceUp.size()>=2 && ckeckSameValue(playerInfoTable.get(i).faceUp) == true)
					askSplit(i);
				refreshCurrentTable();
				if(askDoubleDown(i) == false){

					while(playerInfoTable.get(i).status == "OK" 
						&& players.get(i).hit_me(new Hand(playerInfoTable.get(i).faceUp), dealer.info.faceUp.get(0), this.current_table)){
						Card card = dealer.dealCard();
						playerInfoTable.get(i).faceUp.add(card);
						System.out.printf("[play]Player %s[%d] hit: %s\n", players.get(i).toString(), playerInfoTable.get(i).id, cardToString(card));
						playerInfoTable.get(i).refreshStatus();
						refreshCurrentTable();
						printPlayerInfo(playerInfoTable.get(i));
					}
				}else{
					printPlayerInfo(playerInfoTable.get(i));
				}
				System.out.printf("------------------------------\n");
			}
			System.out.printf("[play]-----dealer time-------\n");
			//dealer's time
			flipout(dealer.info);
			System.out.printf("[play]Dealer flip out!\n");
			printPlayerInfo(dealer.info);
			refreshCurrentTable();
			while(dealer.info.softSafe() && dealer.info.status == "OK"){
				Card card = dealer.dealCard();
				dealer.info.faceUp.add(card);
				System.out.printf("[play]Dealer hit: %s\n", cardToString(card));
				dealer.info.refreshStatus();
				refreshCurrentTable();
			}

			doResulting();
			System.out.printf("==========================================\n");

		}

	}

	private String cardToString(Card card){
		int suit = (int) card.getSuit();
		int value = (int) card.getValue();
		return Suits[suit-1] + Values[value-1];
	}

	private void printPlayerInfo(PlayerInfo info){
		String output = "[PlayerInfo]";
		output += "Status: " + info.status + ", ";
		output += "Bet: " + new String(""+info.bet)+", ";
		output += "Hand: ";
		for(int i = 0; i < info.faceUp.size(); i++){
			output += cardToString(info.faceUp.get(i)) + " ";
		}
		output += ", Insurance: ";
		if(info.insurance == true)
			output += "Yes\n";
		else
			output += "No\n";

		System.out.printf(output);
	}

}