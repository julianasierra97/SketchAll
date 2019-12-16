package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class PartieServeur {
	

	private ArrayList<String> gameList = new ArrayList<String>();
	private EditeurServeur server;
	private ArrayList<String> words;
	private ArrayList<Integer> couplesSketcher;
	
	public PartieServeur(EditeurServeur server) {
		this.server=server;
		initCouples();
	}
	
	public void addPlayer(String username) {
		gameList.add(username);
		if (isComplete()) {
			startGame();
		}
	}
	

	public boolean isComplete() {
		return (gameList.size()>3);
	}
	
	public void startGame() {
		for (String word : words) {
			
		}
	}
	
	public void newRound(String word) {
		int couple = couplesSketcher.remove(couplesSketcher.size()-1);
		try {
			//on recupere le couple
			for (String id : gameList) {
				if (id==gameList.get(couple%10) || id==gameList.get(couple/10)) {
					server.getPlayer(id).setSketcher(true,word);
				}
				else {
					server.getPlayer(id).setSketcher(false, word);
				}
			
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// On initialise un array représentant les combinaisons possibles de sketchers stockées sous formes d'entiers
	public void initCouples(){
		couplesSketcher = new ArrayList<Integer>();
		couplesSketcher.add(10);
		couplesSketcher.add(12);
		couplesSketcher.add(13);
		couplesSketcher.add(20);
		couplesSketcher.add(23);
		couplesSketcher.add(30);
		Collections.shuffle(couplesSketcher);
		
	}
	
	public EditeurServeur getServer() {
		return server;
	}

}
