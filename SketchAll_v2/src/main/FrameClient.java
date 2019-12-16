package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import communication.RecepteurUnicast;
import editeur.EditeurClient;
import menu.MenuPane;
import server.RemoteEditeurServeur;
import server.RemoteUserServeur;

public class FrameClient extends JFrame {

	private static final long serialVersionUID = 1L;

	// le Thread pour pouvoir recevoir des mises à jour en provenance du serveur
	private Thread threadRecepteur;

	// le r�cepteur de messages diffus�s aux abonn�s
	private RecepteurUnicast RecepteurUnicast;

	// le serveur distant qui centralise toutes les informations
	private RemoteEditeurServeur server;

	// le nom de la machine qui h�berge l'�diteur local
	protected String clientMachineName;


	// le nom de la machine sur laquelle se trouve le serveur distant :
	// - le syst�me saura calculer l'adresse IP de la machine à partir de son nom
	// - sinon on met directement l'adresse IP du serveur dans cette chaine de
	// caract�re
	private EditeurClient editeur;
	private String username;
	private HashMap<String, Player> players;
	private ChatPane chatPane;
	private PlayersPane playersPane;
	private ArrayList<String> words;
	private JPanel mainViewPane;
	private JPanel containerPane;
	private MenuPane menuPane;
	private int round;
	private CardLayout cl;

	private WaitingPanel waitingPane;

	// Constructeur à qui on transmet les informations suivantes :
	// - nom de l'�diteur
	// - nom du serveur distant
	// - nom de la machine sur laquelle se trouve le serveur
	// - num�ro de port sur lequel est d�clar� le serveur sur la machine distante
	FrameClient(final String clientMachineName, final String username, RemoteEditeurServeur server) {
		this.clientMachineName = clientMachineName;
		this.username = username;
		players = new HashMap<String, Player>();
		playersPane = new PlayersPane();
		selectWords();
		this.server=server;
		
		waitingPane= new WaitingPanel(this);
		try {
			// cr�ation d'un r�cepteur unicast en demandant l'information de num�ro port au
			// serveur
			// en même temps on transmet au serveur l'adresse IP de la machine du client au
			// serveur
			// de façon à ce que ce dernier puisse par la suite envoyer des messages de mise
			// à jour à ce r�cepteur
			RecepteurUnicast = new RecepteurUnicast(InetAddress.getByName(clientMachineName),
					server.getPortEmission(InetAddress.getByName(clientMachineName)));
			// on aimerait bien demander automatiquement quel est l'adresse IP de la machine
			// du client,
			// mais le probl�me est que celle-ci peut avoir plusieurs adresses IP (filaire,
			// wifi, ...)
			// et qu'on ne sait pas laquelle sera retourn�e par InetAddress.getLocalHost
			// ()...
			// recepteurUnicast = new RecepteurUnicast (serveur.getPortEmission
			// (InetAddress.getLocalHost ())) ;
			RecepteurUnicast.setLocalClient(this);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// cr�ation d'un Thread pour pouvoir recevoir les messages du serveur en
		// parall�le des interactions avec les dessins
		threadRecepteur = new Thread(RecepteurUnicast);
		// d�marrage effectif du Thread
		threadRecepteur.start();

		try {
			// récupération de tous les dessins déjà présents sur le serveur
			ArrayList<RemoteUserServeur> onlinePlayers = new ArrayList<RemoteUserServeur>(
					server.getPlayerList().values());
			// ajout de tous les dessins dans la zone de dessin
			for (RemoteUserServeur player : onlinePlayers) {
				addPlayer(player.getUsername(), player,player.getInGame());
			}
		} catch (Exception e) {
			System.out.println("probleme liaison CentralManager");
			e.printStackTrace();
			System.exit(1);
		}

		// ajout du joueur dans la liste serveur
		try {
			if (!server.getPlayerList().containsKey(this.username)) {
				server.addPlayer(this.username);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// demande d'affichage de l'�diteur
		// - faite "seulement"/"tardivement" ici pour que tous les objets r�cup�r�s du
		// serveur apparaissent bien du premier coup

		this.setSize(1000, 600);

		this.setLayout(new BorderLayout());

	

		cl= new CardLayout();
		this.setTitle("SketchAll");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatPane = new ChatPane(this);
	

		menuPane= new MenuPane(this);
	

		this.editeur = new EditeurClient(this);
	
		containerPane= new JPanel();
		containerPane.setLayout(cl);
		containerPane.add(menuPane,"1");
		containerPane.add(waitingPane,"2");
		
		
		
		this.add(containerPane,BorderLayout.CENTER);



		cl.show(containerPane, "1");

		setVisible(true);

	}

	public synchronized void addPlayer(String username) {
		if (!players.containsKey(username)) {
			try {
				System.out.println("nbr of players : " + players.size());
				this.addPlayer(username, server.getPlayer(username),false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void addPlayer(String username, RemoteUserServeur proxy, boolean inGame) {
		Player player = new Player(proxy, username,inGame);
		players.put(username, player);
		System.out.println(players);
		playersPane.addPlayer(player);

	} 


	public synchronized void setPlayerSketcher(String username, boolean sketcher) {
		players.get(username).setSketcher(sketcher);
	}

	public synchronized void setPlayerPoints(String username, int points) {
		players.get(username).setPoints(points);
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public RemoteEditeurServeur getServer() {
		return server;
	}

	public EditeurClient getEditeur() {
		return editeur;
	}

	public ChatPane getChatPane() {
		return chatPane;
	}

	public String getUsername() {
		return username;
	}

	public Player getPlayer(String username) {
		return players.get(username);
	}

	public void setChatPane(ChatPane chatPane) {
		this.chatPane = chatPane;
	}

	public void setGuesserPoints(int points) {
		int score = getPlayer(username).getPoints();
		score += points;
		getPlayer(username).setPoints(score);
		playersPane.setPoints(getPlayer(username).getUsername(), score);
	}

	public void setSketcherPoints(int points) {
		for(Map.Entry<String, Player> entry : players.entrySet()) {
			//String key = entry.getKey();
			Player player = entry.getValue();
			if (player.isSketcher()) {
				int score = player.getPoints();
				score += points;
				player.setPoints(score);
				playersPane.setPoints(player.getUsername(), score);
			}
		}
	}

	public CardLayout getCardLayout() {
		return cl;
	}

	public JPanel getContainerPane() {
		return containerPane;
	}
	public void selectWords() {
		RandomWords randomWords = new RandomWords();
		words = randomWords.selectWords();
	}

	public String nextWord() {    
		
		
		
		String nextWord;
		if (round < words.size()) {
			nextWord = words.get(round);
		} else {
			nextWord = null;
		}
		return nextWord;
	}

	public void setPlayerInGame(String name, boolean b) {

		getPlayers().get(name).setInGame(b);
		int count=0;
		for (Map.Entry<String,Player> entry : players.entrySet())  {
		System.out.println(entry.getValue().isInGame()+"isIngame");
			if(entry.getValue().isInGame()) {
				count++;
			}
		}
		
		waitingPane.getProgressBar().setValue(count);
		waitingPane.revalidate();
		waitingPane.repaint();
		revalidate();
		repaint();
		if(count>3) {
			
			mainViewPane = new JPanel();
			mainViewPane.setLayout(new BorderLayout());
			mainViewPane.add(editeur, BorderLayout.CENTER);
			mainViewPane.add(chatPane, BorderLayout.EAST);
			mainViewPane.add(playersPane, BorderLayout.WEST);
			editeur.creerChronometre();
			containerPane.add(mainViewPane,"3");
			
			cl.show(containerPane, "3");
		}

		
	}
}
