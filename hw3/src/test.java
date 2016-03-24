import java.lang.*;
import java.util.*;



public class test{

	public static boolean testCard(){
		Card A = new Card(0, 0);
		boolean compare1 = A.compare(new Card(0, 1), new Card(0, 2)) < 0;
		boolean compare2 = A.compare(new Card(0, 0), new Card(0, 0)) == 0;
		boolean compare3 = A.compare(new Card(0, 2), new Card(0, 1)) > 0;
		boolean compare4 = A.compare(new Card(0, 2), new Card(1, 1)) > 0;
		boolean Compare = compare1 & compare2 & compare3 & compare4;

		boolean pair1 = new Card(0, 1).ispairOf(new Card(1, 1)) == true;
		boolean pair2 = new Card(1, 0).ispairOf(new Card(0, 1)) == false;
		boolean Pair = pair1 & pair2;

		ArrayList<Card> Cards = new ArrayList<Card>();
		Cards.add(new Card(0, 1));
		Cards.add(new Card(0, 2));
		boolean notin1 = new Card(0, 0).notIn(Cards) == true;
		boolean notin2 = new Card(0, 1).notIn(Cards) == false;
		boolean Notin = notin1 & notin2;

		return Compare & Pair & Notin;

	}

	public static boolean testPlayer(){
		Player player = new Player(0);

		player.getHands(new Card(0, 0));
		Card A = new Card(0, 0);
		boolean Gethand = A.compare(player.Hands.get(0), new Card(0, 0)) == 0;

		player.getHands(new Card(0, 2));
		player.getHands(new Card(0, 1));
		player.Handsort();
		boolean sort1 = A.compare(player.Hands.get(0), new Card(0, 0)) == 0;
		boolean sort2 = A.compare(player.Hands.get(1), new Card(0, 1)) == 0;
		boolean sort3 = A.compare(player.Hands.get(2), new Card(0, 2)) == 0;
		boolean Sort = sort1 & sort2 & sort3;

		ArrayList<Card> CannotDrop = new ArrayList<Card>();
		player.getHands(new Card(1, 1));
		player.DropPairCard(CannotDrop);
		boolean drop1 = A.compare(player.Hands.get(0), new Card(0, 0)) == 0;
		boolean drop2 = A.compare(player.Hands.get(1), new Card(0, 2)) == 0;
		

		player.getHands(new Card(0, 1));
		player.getHands(new Card(1, 1));
		CannotDrop.add(new Card(1, 1));
		player.DropPairCard(CannotDrop);
		boolean drop3 = A.compare(player.Hands.get(0), new Card(0, 0)) == 0;
		boolean drop4 = A.compare(player.Hands.get(1), new Card(0, 1)) == 0;
		boolean drop5 = A.compare(player.Hands.get(2), new Card(1, 1)) == 0;
		boolean drop6 = A.compare(player.Hands.get(3), new Card(0, 2)) == 0;
		boolean Drop = drop1 & drop2 & drop3 & drop4 & drop5 & drop6;

		Player other = new Player(1);
		boolean draw1 = other.Hands.size() == 0;
		boolean draw2 = player.Hands.size() == 4;
		other.DrawCardFrom(player);
		boolean draw3 = other.Hands.size() == 1;
		boolean draw4 = player.Hands.size() == 3;
		player.DrawCardFrom(other);
		boolean draw5 = other.Hands.size() == 0;
		boolean draw6 = player.Hands.size() == 4;
		player.Handsort();
		boolean draw7 = A.compare(player.Hands.get(0), new Card(0, 0)) == 0;
		boolean draw8 = A.compare(player.Hands.get(1), new Card(0, 1)) == 0;
		boolean draw9 = A.compare(player.Hands.get(2), new Card(1, 1)) == 0;
		boolean draw10 = A.compare(player.Hands.get(3), new Card(0, 2)) == 0;
		boolean Draw = draw1 & draw2 & draw3 & draw4 & draw5 & draw6 & draw7 & draw8 & draw9 & draw10;

		boolean nohand1 = other.NoHands() == true;
		boolean nohand2 = player.NoHands() == false;
		boolean Nohand = nohand1 & nohand2;

		return Gethand & Sort & Drop & Draw & Nohand;

	}

	public static boolean testOldMaid(){
		OldMaid game = new OldMaid("Classical");
		Card A = new Card(0, 0);

		game.loadRules();
		boolean load1 = A.compare(game.CannotDrop.get(0), new Card(0, 0)) == 0;
		boolean load2 = A.compare(game.CannotDrop.get(1), new Card(1, 0)) == 0;
		boolean load3 = game.WinRule == 0;
		boolean Load = load1 & load2 & load3;

		ArrayList<Card> Cards = game.CardInitialize();
		boolean cardinitial1 = Cards.size() == 54;
		boolean cardinitial2 = true;
		for(int i = 0; i < game.DropCards.size(); i++){
			cardinitial2 = cardinitial2 & game.DropCards.get(i).notIn(Cards);
		}
		boolean Cardinitial = cardinitial1 & cardinitial2;

		game.PlayerInitialize(Cards);
		boolean quit1 = game.SomeoneQuit() == 0;
		game.Players.get(1).Hands.clear();
		boolean quit2 = game.SomeoneQuit() == 1;
		boolean Quit = quit1 & quit2;

		return Load & Cardinitial & Quit;
	}

	public static void main(String[] argv){
		boolean TestCard = testCard();
		System.out.println("TestCard: "+Boolean.toString(TestCard));
		boolean TestPlayer = testPlayer();
		System.out.println("TestPlayer: "+Boolean.toString(TestPlayer));
		boolean TestOldMaid = testOldMaid();
		System.out.println("TestOldMaid: "+Boolean.toString(TestOldMaid));

	}

}