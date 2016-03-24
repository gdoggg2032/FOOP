import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Card{
	String Suit;
	String Number;
	int Value;
	private String[] SuitRank = {"R","B","C","D","H","S"};
	private String[] NumberRank = {"0","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	public Card(int value){
		this.Value = value;
		if(value<2){
			this.Suit = SuitRank[value];
			this.Number = NumberRank[0];
		}
		else{
			this.Suit = SuitRank[(value-2)%4+2];
			this.Number = NumberRank[(value-2)/4%13+1];
		}
	}
}
class Player{
	int Id;
	ArrayList<Card> Hands = new ArrayList<Card>();
	String Name;
	int CardNum;
	public Player(int id){
		this.Id = id;
		this.Name = "Player"+id;
		this.CardNum = 13;
		if(id==0||id==1) this.CardNum++;
	}
	public void Handsort(){
		for(int i = 0; i < this.Hands.size(); i++)
			for(int j = 0; j < this.Hands.size(); j++){
				if(this.Hands.get(i).Value < this.Hands.get(j).Value){
					Card tmpCard = this.Hands.get(i);
					Hands.set(i, this.Hands.get(j));
					Hands.set(j, tmpCard);
				}
			}
	}
	public void ShowHands(){
		System.out.printf("%s:",this.Name);
		for(int j = 0; j < this.CardNum; j++)
			System.out.printf(" %s%s",this.Hands.get(j).Suit, this.Hands.get(j).Number);
		System.out.printf("\n");
	}
	public void DropCard(){
		for(int i = 0; i < this.CardNum-1; i++){
			if(this.Hands.get(i).Number != "0" && this.Hands.get(i).Number == this.Hands.get(i+1).Number){
				this.Hands.remove(i+1);
				this.Hands.remove(i);
				this.CardNum -= 2;
				i--;
			}
		}
	}
	public void DrawCardFrom(Player other){
		Random ran = new Random();
		int choice = ran.nextInt(other.CardNum);
		Card tmpCard = other.Hands.remove(choice);
		System.out.printf("%s draws a card from %s %s%s\n", this.Name, other.Name, tmpCard.Suit, tmpCard.Number);
		this.Hands.add(tmpCard);
		this.Handsort();
		this.CardNum = this.Hands.size();
		other.CardNum = other.Hands.size();
	}
}
public class PlayGame{
	static ArrayList<Card> CardInitialize(){
		ArrayList<Card> Cards = new ArrayList<Card>();
		for(int i = 0; i < 54; i++) Cards.add(new Card(i));	
		Collections.shuffle(Cards);
		return Cards;
	}
	static ArrayList<Player> PlayerInitialize(ArrayList<Card> Cards){
		ArrayList<Player> Players = new ArrayList<Player>();
		for(int i = 0; i < 4; i++){
			Players.add(new Player(i));
			for(int j = 0; j < Players.get(i).CardNum; j++){
				Players.get(i).Hands.add(Cards.remove(0));
			}
			Players.get(i).Handsort();
			Players.get(i).ShowHands();
		}
		System.out.println("Drop cards");
		for(int i = 0; i < Players.size(); i++){
			Players.get(i).DropCard();
			Players.get(i).ShowHands();
		}
		return Players;
	}
	static int SomeoneWin(ArrayList<Player> Players, int turn){
		int Winner = 0;
		int next = turn;
		for(int i = 0; i < Players.size(); i++)
			if(Players.get(i).CardNum == 0){
				Winner++;
				if(next > i)next--;
				//if(next == i)next = (next + Players.size() + 1)%Players.size();
				if(Winner == 1) System.out.printf("%s ", Players.get(i).Name);
				else System.out.printf("and %s ", Players.get(i).Name);
			}
		if(Winner == 1) System.out.printf("wins\n");
		else if(Winner > 1) System.out.printf("win\n");
		
		for(int i = Players.size()-1; i >= 0; i--){
			
			if(Players.get(i).CardNum == 0){
				Players.remove(i);
			}
		}
	
		if(Winner == 0)
			return next;
		else
			return -next-1;
			
		
		
	}
	static int BasicGame(ArrayList<Player> Players, int turn){
		int next;
		while((next = SomeoneWin(Players,turn)) >= 0){
			
			Players.get(turn%Players.size()).DrawCardFrom(Players.get((turn+1)%Players.size()));
			Players.get(turn%Players.size()).DropCard();
			Players.get(turn%Players.size()).ShowHands();
			Players.get((turn+1)%Players.size()).ShowHands();
			turn = (turn+1)%Players.size();	
		}
		return -(next+1);
	}
	static void BonusGame(ArrayList<Player> Players, int turn){
		while(Players.size() > 1){
			turn = BasicGame(Players, turn);
		}
	}
	public static void main(String [] argv){ 
		/* initialize */
		ArrayList<Card> Cards = CardInitialize();
		System.out.println("Deal cards");
		ArrayList<Player> Players = PlayerInitialize(Cards);
		/* Game Start */
		System.out.println("Game start");
		int turn = BasicGame(Players, 0);
		/* Basic Game Over */
		System.out.println("Basic game over");
		
		/* Bonus Game Start */
		System.out.println("Continue");
		BonusGame(Players, turn);
		/* Bonus Game Over */
		System.out.println("Bonus game over");
	}
}