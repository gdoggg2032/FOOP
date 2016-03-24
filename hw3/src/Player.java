import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Player{

	public int Id;
	public String Name;
	//Id is the number of player (e.g. player 0, player 1)
	//Name is the name Player want to be called

	public ArrayList<Card> Hands = new ArrayList<Card>();

	private Random ran = new Random();
	//random for choose a card to draw

	public int Quit;
	//Quit = 0 for gaming, 1 for not gaming


	public Player(int id){
		this.Id = id;
		this.Name = "Anonymous";
		this.Quit = 0;
	}

	public Player(int id, String name){
		this.Id = id;
		this.Name = name;
		this.Quit = 0;
	}

	//get a card from others (including deck)
	public void getHands(Card card){
		this.Hands.add(card);
	}

	public void Handsort(){
		Collections.sort(this.Hands, new Card());
	}

	public void ShowHands(){
		System.out.printf("%s (player %d):", this.Name, this.Id);
		for(int i = 0; i < this.Hands.size(); i++){
			System.out.printf(" %s", this.Hands.get(i).toString());
		}
		System.out.printf("\n");
	}

	public void DropPairCard(ArrayList<Card> CannotDrop){
		this.Handsort();	
		for(int i = 0; i < this.Hands.size()-1; i++){
			Card nowcard = this.Hands.get(i);
			Card nextcard = this.Hands.get(i+1);
			//can only drop cards which are not in CannotDrop
			if(nowcard.notIn(CannotDrop) && nextcard.notIn(CannotDrop)
				&& nowcard.ispairOf(nextcard)){
				this.Hands.remove(i+1);
				this.Hands.remove(i);
				i--;
			}
		}
	}

	public void DrawCardFrom(Player other){
		int choice = this.ran.nextInt(other.Hands.size());
		Card tmpCard = other.Hands.remove(choice);
		System.out.printf("%s (player %d) draws a card from %s (player %d) %s\n", this.Name, this.Id, other.Name, other.Id, tmpCard.toString());
		this.getHands(tmpCard);
		this.Handsort();		
	}

	public boolean NoHands(){
		return this.Hands.size() == 0;
	}

}