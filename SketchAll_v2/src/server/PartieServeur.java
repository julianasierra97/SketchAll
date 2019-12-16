package server;

import java.util.ArrayList;

public class PartieServeur {
	

	private ArrayList<String> gameList = new ArrayList<String>();
	private EditeurServeur server;
	
	public PartieServeur(EditeurServeur server) {
		this.server=server;
	}
	
	public void addPlayer(String username) {
		gameList.add(username);
	}
	
	public boolean isComplete() {
		return true;
	}

}
