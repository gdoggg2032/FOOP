import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;

class OldMaid{

	//private String[] SuitRank = {"R","B","C","D","H","S"};
	//private String[] NumberRank = {"0","2","3","4","5","6","7","8","9","10","J","Q","K","A"};

	protected ArrayList<Card> CannotDrop;
	protected ArrayList<Card> DropCards;
	protected ArrayList<Player> Players;
	//CannotDrop = special card not drop in game
	//DropCards = cards not add in game
	protected int WinRule;
	//WinRule = 0 for win with no hands, 1 for win with remaining hands

	public int PlayerNumber;
	//number of players 

	protected String GameType;


	public OldMaid(String gameType){
		this.GameType = gameType;
		this.CannotDrop = new ArrayList<Card>();
		this.DropCards = new ArrayList<Card>();
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 14; j++){
				if((i == 0 || i == 1) && (j != 0))
					DropCards.add(new Card(i, j));
				if((i > 1) && (j == 0))
					DropCards.add(new Card(i, j));
			}
		}
		//add non-exist cards in DropCards
		this.WinRule = 0;
		//default
		this.Players = new ArrayList<Player>();
		this.PlayerNumber = 4;
	}

	protected void loadRules(){
		this.CannotDrop.add(new Card(0, 0));
		this.CannotDrop.add(new Card(1, 0));
		//add two jokers in CannotDrop
		this.WinRule = 0;
	}

	protected ArrayList<Card> CardInitialize(){
		ArrayList<Card> Deck = new ArrayList<Card>();
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 14; j++){
				Card card = new Card(i, j);
				if(card.notIn(DropCards))
					Deck.add(card);
			}
		}
		Collections.shuffle(Deck);
		return Deck;
	}

	protected void PlayerInitialize(ArrayList<Card> Deck){
		
		for(int i = 0; i < PlayerNumber; i++){
			Players.add(new Player(i));
		}
		int turn = 0;
		while(Deck.size() > 0){
			Players.get(turn%PlayerNumber).getHands(Deck.remove(0));
			turn++;
		}
		//deliver cards
		for(int i = 0; i < PlayerNumber; i++){
			Players.get(i).Handsort();
			Players.get(i).ShowHands();
		}
		System.out.println("Drop cards");
		for(int i = 0; i < PlayerNumber; i++){
			Players.get(i).DropPairCard(this.CannotDrop);
			Players.get(i).ShowHands();
		}
	}

	protected void PlayerInitialize(ArrayList<Card> Deck, int playernumber){
		
		this.PlayerNumber = playernumber;
		for(int i = 0; i < PlayerNumber; i++){
			Players.add(new Player(i));
		}
		int turn = 0;
		while(Deck.size() > 0){
			Players.get(turn%PlayerNumber).getHands(Deck.remove(0));
			turn++;
		}
		//deliver cards
		for(int i = 0; i < PlayerNumber; i++){
			Players.get(i).Handsort();
			Players.get(i).ShowHands();
		}
		System.out.println("Drop cards");
		for(int i = 0; i < PlayerNumber; i++){
			Players.get(i).DropPairCard(this.CannotDrop);
			Players.get(i).ShowHands();
		}
	}

	protected int SomeoneQuit(){
		int Winners = 0;
		for(int i = 0; i < PlayerNumber; i++){
			Player player = Players.get(i);
			if(player.NoHands() && player.Quit == 0){
				Winners++;
				player.Quit = 1;
				if(Winners == 1) System.out.printf("%s (player %d) ", player.Name, player.Id);
				else System.out.printf("and %s (player %d) ", player.Name, player.Id);
			}
		}
		if(this.WinRule == 0){
			if(Winners == 1) System.out.printf("wins\n");
			else if(Winners > 1) System.out.printf("win\n");
		}
		else{
			if(Winners == 1) System.out.printf("loses\n");
			else if(Winners > 1) System.out.printf("lose\n");
		}
		
		return Winners;
	}

	public void Play(){
		System.out.println("GameType: "+this.GameType);
		/* initialize */
		this.loadRules();
		System.out.println("load rules");
		ArrayList<Card> Deck = CardInitialize();
		System.out.println("Deal cards");
		PlayerInitialize(Deck, this.PlayerNumber);

		/* Game Start */
		System.out.println("Game start");
		int turn = 0;
		if(WinRule == 0){
			turn = PlayBasic(turn);
			//only WinRule = 0 type end game with only one player quit
			/* Basic Game Over */
			System.out.println("Basic game over");
			/* Bonus Game Start */
			System.out.println("Continue");
		}
		/* Bonus Game Start */
		turn = PlayBonus(turn);
		if(this.WinRule == 1)
			System.out.printf("%s (player %d) win\n", Players.get(turn%PlayerNumber).Name, Players.get(turn%PlayerNumber).Id);
		System.out.println("Bonus game over");
	}

	private int FindNextTurn(int turn){
		int turnNext = turn;
		for(int i = turn + 1; i < turn + this.PlayerNumber; i++){
			if(Players.get(i%PlayerNumber).Quit == 0){
				turnNext = i%PlayerNumber;
				break;
			}		
		}
		
		return turnNext;
	}

	private int RemainPlayers(){
		int remain = 0;
		for(int i = 0; i < PlayerNumber; i++){
			if(Players.get(i).Quit == 0)
				remain++;
		}
		return remain;
	}

	protected int PlayBasic(int turn){
		while(true){
			int turnNext = FindNextTurn(turn);
			Players.get(turn%PlayerNumber).DrawCardFrom(Players.get((turnNext)%PlayerNumber));
			Players.get(turn%PlayerNumber).DropPairCard(this.CannotDrop);
			Players.get(turn%PlayerNumber).ShowHands();
			Players.get((turnNext)%PlayerNumber).ShowHands();
			if(SomeoneQuit() == 0)
				turn = turnNext;
			else
				break;	
		}
		return FindNextTurn(turn);
	}

	protected int PlayBonus(int turn){
		while(RemainPlayers() > 1){
			turn = PlayBasic(turn);
		}
		return turn;
	}


}