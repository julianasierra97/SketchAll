package server;

import java.util.ArrayList;

public class PartieServeur {
	

	private ArrayList<String> gameList = new ArrayList<String>();
	private EditeurServeur server;
	private ArrayList<String> words;
	
	public PartieServeur(EditeurServeur server) {
		this.server=server;
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
		
	}
	public void newRound() {
		
	}

}
