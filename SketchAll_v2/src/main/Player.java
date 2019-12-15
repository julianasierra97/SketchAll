package main;

import java.util.Random;

import server.RemoteUserServeur;

public class Player {

	private String username;
	private int points;
	private Random random = new Random();
	private boolean sketcher;
	private RemoteUserServeur proxy;
	private boolean inGame;


	public Player(RemoteUserServeur proxy, String username) {
		super();
		this.username = username;
		this.points = 0;
		
		this.sketcher = false;
		this.proxy = proxy;
		
		
		//this.sketcher = true;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isSketcher() {
		return sketcher;
	}

	public void setSketcher(boolean sketcher) {
		this.sketcher = sketcher;
	}

	public String getUsername() {
		return username;
	}

	public RemoteUserServeur getProxy() {
		return proxy;
	}
	
	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

}
