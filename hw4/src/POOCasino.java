package foop;

import java.lang.*;
import java.lang.reflect.*;
import foop.*;
import java.util.*;

public class POOCasino{
	private static ArrayList<Player> loadPlayers(String[] playerClassName, int nChip) throws ClassNotFoundException, 
 			InstantiationException, 
 			IllegalAccessException,
 			NoSuchMethodException,
 			InvocationTargetException{

		ClassLoader classLoader = POOCasino.class.getClassLoader();

		ArrayList<Player> players = new ArrayList<Player>();

		for(int i = 0; i < playerClassName.length; i++){
			try{
				Class<?> Playeri = Class.forName(playerClassName[i]);//classLoader.loadClass(playerClassName[i]);
				System.out.println("Player"+Integer.toString(i)+": " + Playeri.getName());
				players.add((Player) Playeri.getDeclaredConstructor(int.class).newInstance(nChip));
				System.out.println(players.get(i).toString());	
			}catch(ClassNotFoundException e){
				e.printStackTrace();		
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}catch(InvocationTargetException e){
				e.printStackTrace();
			}
		}
		return players;
	}

	public static void main(String[] args)
	throws ClassNotFoundException, 
			NoSuchMethodException,
			IllegalAccessException, 
			InstantiationException,
			InvocationTargetException{
		int nRound = Integer.valueOf(args[0]); // the number of rounds of the game
		int nChip = Integer.valueOf(args[1]); // the amount of chips that each player has in the beginning

		System.out.printf("nRound: %d\n", nRound);
		System.out.printf("nChip: %d\n", nChip);

		ArrayList<Player> players = loadPlayers(Arrays.copyOfRange(args, 2, 2+4), nChip);
		Blackjack game = new Blackjack(nRound, players);
		game.play();


	}
}
