import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Card{

	public int Suit;
	public int Number;
	private int Value; //used for sorting 
	private String[] SuitName = {"C","D","H","S"};
	private String[] NumberName = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	public Card(int Suit, int Number){
		this.Suit = Suit;
		this.Number = Number;
	}
	public String GetCardName(){
		return SuitName[this.Suit] + NumberName[this.Number];
	}
}

class Computer{

	private ArrayList<Card> Cards = new ArrayList<Card>(); //you can't see or change the remaining cards.
	private ArrayList<Card> Hands = new ArrayList<Card>(); //set to private because you can only watch it, not change it.
	private int NowBet = 0; //set to private because anyone can't easily change the bet.
	private String NowPlayer = "Anonymous"; //in fact, not used. but keep for a possible future use.

	public void SetPlayerName(String playername){
		this.NowPlayer = playername;
	}

	public void CardInitialize(){
		Cards.clear();
		Hands.clear();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 13; j++){
				Cards.add(new Card(i, j));
			}
		} 	
		Collections.shuffle(Cards);
	}

	public void ShowCards(){
		System.out.println("Show all Cards (for test)");
		for(int i = 0; i < Cards.size(); i++){
			System.out.printf("(%d) %s\n", i, Cards.get(i).GetCardName());
		}
	}

	public void SetBet(int bet){
		this.NowBet = bet;
	}

	public void DealCards(){
		while(Hands.size() < 5){
			Hands.add(this.Cards.remove(0));
			//you can not deal specific number, or you can get more than 5 cards.
			//we don't care about the Cards number, because you only need no more than 10 cards.
		}
		//we don't sort here, because this is a challange for players to find the hand type
	}

	//for test
	public void GodDealCards(){
		Scanner scanner = new Scanner(System.in);
		while(Hands.size() < 5){
			System.out.printf("choose a card for hands: (suit[0-3], number[0-12]) ");
			Hands.add(new Card(scanner.nextInt(), scanner.nextInt()));
		}
	}


	public void ShowHands(){
		System.out.printf("Your cards are ");
		for(int i = 0; i < 5; i++){
			System.out.printf("(%d) %s ", i, Hands.get(i).GetCardName());
		}
		System.out.printf("\n");
	}

	public void DropAndGetHands(ArrayList<Integer> choose){
		if(choose.isEmpty()){
			System.out.println("Keep all cards.");
		}
		else{
			Collections.sort(choose); //we sort the choose cards because you may not enter the card id in order.
			System.out.printf("Okay. I will discard ");
			for(int i = 0; i < choose.size(); i++){
				System.out.printf("(%d) %s ", choose.get(i), Hands.get(choose.get(i)).GetCardName());
			}
			System.out.printf("\n");
			for(int i = 0; i < choose.size(); i++){
				Hands.set(choose.get(i), this.Cards.remove(0));
			}
		}
	}

	//for test
	public void GodDropAndGetHands(ArrayList<Integer> choose){
		if(choose.isEmpty()){
			System.out.println("Keep all cards.");
		}
		else{
			Collections.sort(choose);
			System.out.printf("Okay. I will discard ");
			for(int i = 0; i < choose.size(); i++){
				System.out.printf("(%d) %s ", choose.get(i), Hands.get(choose.get(i)).GetCardName());
			}
			System.out.printf("\n");
			Scanner scanner = new Scanner(System.in);
			for(int i = 0; i < choose.size(); i++){
				System.out.printf("choose a card for hands: (suit[0-3], number[0-12]) ");
				Hands.set(choose.get(i), new Card(scanner.nextInt(), scanner.nextInt()));
			}
		}
	}

	public int FindBestHandAndPayoff(){ 
		String HandType = FindBestHand();
		int payoff =  GetPayoff(HandType, NowBet);
		System.out.printf("You get a [%s] hand. The payoff is %d.\n", HandType, payoff);
		return payoff;
		//we combine FindBestHand and GetPayoff because in real world, we would not find best hand but not get payoff. 
	}

	private String FindBestHand(){
		if(IsFlush() && IsStraight() && HaveCardNumber(9) == 1 && HaveCardNumber(0) == 1)
			return "royal flush";
		else if(IsFlush() && IsStraight())
			return "straight flush";
		else if(HaveCardNumber() == 4)
			return "four of a kind";
		else if(IsFullHouse())
			return "full house";
		else if(IsFlush())
			return "flush";
		else if(IsStraight())
			return "straight";
		else if(HaveCardNumber() == 3)
			return "three of a kind";
		else if(IsTwoPair())
			return "two pair";
		else if(HaveCardNumber(10) == 2 || HaveCardNumber(11) == 2 || HaveCardNumber(12) == 2 || HaveCardNumber(0) == 2)
			return "Jacks or better";
		else
			return "others";
	}

	private boolean IsFlush(){
		int nowsuit = Hands.get(0).Suit;
		for(int i = 1; i < 5; i++){
			if(Hands.get(i).Suit != nowsuit)
				return false;
		}
		return true;
	}

	private boolean IsStraight(){
		//sort by Number
		ArrayList<Integer> NumberList = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++){
			int number = Hands.get(i).Number;
			if(number == 0)
				number += 13;
			NumberList.add(number);
		}
		Collections.sort(NumberList);
		int FirstNumber = NumberList.get(0).intValue();
		if(FirstNumber > 9) //this fit the start number can not be J, Q, K
			return false;
		for(int i = 0; i < 5; i++){
			if((FirstNumber+i)%13 != NumberList.get(i).intValue()%13)
				return false;
		}
		return true;
	}

	private int HaveCardNumber(int number){ //count for a specific card number
		int count = 0;
		for(int i = 0; i < 5; i++){
			if(Hands.get(i).Number == number)
				count += 1;
		}
		return count;
	}

	private int HaveCardNumber(){ //return max number count, not for specific card number
		int MaxCount = 0;
		for(int i = 0; i < 13; i++){
			int count = HaveCardNumber(i);
			if(count > MaxCount)
				MaxCount = count;
		}
		return MaxCount;
	}

	private boolean IsFullHouse(){
		//sort by Number
		ArrayList<Integer> NumberList = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++){
			NumberList.add(Hands.get(i).Number);
		}
		Collections.sort(NumberList); 
		//if we sort by card number, head card and end card will cover all card number type in a full house.
		int HeadNumberCount = HaveCardNumber(NumberList.get(0).intValue());
		int EndNumberCount = HaveCardNumber(NumberList.get(4).intValue());
		if((HeadNumberCount == 2 && EndNumberCount == 3)||(HeadNumberCount == 3 && EndNumberCount == 2))
			return true;
		else
			return false;
	}

	private boolean IsTwoPair(){
		ArrayList<Integer> NumberList = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++){
			NumberList.add(Hands.get(i).Number);
		}
		Collections.sort(NumberList);
		int HeadNumberCount = HaveCardNumber(NumberList.get(0).intValue());
		int MidNumberCount = HaveCardNumber(NumberList.get(2).intValue());
		int EndNumberCount = HaveCardNumber(NumberList.get(4).intValue());
		//two pair pattern = AABBC or AABCC or ABBCC, so we choose 0, 2, 4 to get all number type in two pair
		if( (HeadNumberCount == 2 && MidNumberCount == 2 && EndNumberCount == 1) ||
			(HeadNumberCount == 2 && MidNumberCount == 1 && EndNumberCount == 2) ||
			(HeadNumberCount == 1 && MidNumberCount == 2 && EndNumberCount == 2)){
			return true;
		}
		else
			return false;
	}

	private int GetPayoff(String HandType, int bet){
		int payoff = 0;
		switch(HandType){
			case "royal flush":
				if(bet < 5)
					payoff = 250 * bet;
				else if(bet == 5)
					payoff = 4000;
				break;
			case "straight flush":
				payoff = 50 * bet;
				break;
			case "four of a kind":
				payoff = 25 * bet;
				break;
			case "full house":
				payoff = 9 * bet;
				break;
			case "flush":
				payoff = 6 * bet;
				break;
			case "straight":
				payoff = 4 * bet;
				break;
			case "three of a kind":
				payoff = 3 * bet;
				break;
			case "two pair":
				payoff = 2 * bet;
				break;
			case "Jacks or better":
				payoff = 1 * bet;
				break;
			case "others":
				payoff = 0;
				break;
		}
		return payoff;
	}
}

class Player{

	private String PlayerName = "Anonymous";
	private int Pdollars = 0; //this is private, just as you must get tokens from a cashier
	//we choose to get the 1000p by a method because there may be another way to increase P-dollars in future work.

	public Player(String playername){
		this.PlayerName = playername;
	}
	public Player(){} //this constructor is kept for "Anonymous" if user don't want to record the name.

	public void AddPdollars(int pdollars){
		this.Pdollars += pdollars;
	}

	public int GetPdollars(){
		return this.Pdollars;
	}

	public String GetPlayerName(){
		return this.PlayerName;
	}

	public int Paybet(int bet){
		//Paybet will decrease your Pdollars!
		if(Pdollars < bet){
			System.out.println("You don't have enough money.");
			return 0;
		}
		else{
			this.Pdollars -= bet;
			return bet;
		}
	}
	public ArrayList<Integer> ChooseDrop(String Keeping){ 
		//initially Drop contain all cards, and we remove card which is also in Keeping
		ArrayList<Integer> Drop = new ArrayList<Integer>();
		Drop.add(0);
		Drop.add(1);
		Drop.add(2);
		Drop.add(3);
		Drop.add(4);
		if(Keeping.compareTo("none") == 0)
			return Drop;
		else{
			String[] Keeps = Keeping.split("");
			int KeepNum = Keeping.length();
			for(int i = 0; i < KeepNum; i++){
				Drop.remove(Integer.valueOf(Keeps[i]));
			}
			return Drop;
		}
		
	}
}

public class POOCasino{
	public static String AuthorInfo = "b01902032 Chiang Tung-Chun (Gdog)";
	public static void main(String [] argv){
		Integer TestFlag = Integer.valueOf(argv[0]); // normal mode = 0, test mode = 1

		Scanner scanner = new Scanner(System.in);
		System.out.printf("POOCasino Jacks or better, written by %s.\n", AuthorInfo);
		System.out.printf("Please enter your name: ");
		Player player = new Player(scanner.next());
		System.out.printf("Welcome, %s.\n", player.GetPlayerName());
		player.AddPdollars(1000);
		System.out.printf("You have %d P-dollars now.\n", player.GetPdollars());

		Computer computer = new Computer();
		computer.SetPlayerName(player.GetPlayerName());
		int round = 1;
		int bet = -1;
		while(true){
			System.out.printf("Please enter your P-dollar bet for round %d (1-5 or 0 for quitting the game): ", round);
			bet = scanner.nextInt();
			if(bet < 0 || bet > 5){
				System.out.println("You have entered an illegal bet");
				continue;
			}
			bet = player.Paybet(bet);
			if(bet == 0)break;
			else{
				
				computer.SetBet(bet);
				computer.CardInitialize();

				if(TestFlag != 0)
					computer.ShowCards(); //for test

				if(TestFlag == 0)
					computer.DealCards();
				else
					computer.GodDealCards(); //for test

				computer.ShowHands();
				System.out.printf("Which cards do you want to keep? (none or index) ");

				if(TestFlag == 0)
					computer.DropAndGetHands(player.ChooseDrop(scanner.next()));
				else
					computer.GodDropAndGetHands(player.ChooseDrop(scanner.next())); //for test

				computer.ShowHands();
				player.AddPdollars(computer.FindBestHandAndPayoff());
				System.out.printf("You have %d P-dollars now.\n", player.GetPdollars());

			}
			round += 1;
		}
		if(round < 2)
			System.out.printf("Good bye, %s. You played for %d round and have %d P-dollars now.\n", player.GetPlayerName(), round-1, player.GetPdollars());
		else
			System.out.printf("Good bye, %s. You played for %d rounds and have %d P-dollars now.\n", player.GetPlayerName(), round-1, player.GetPdollars());
		
	}
}
















