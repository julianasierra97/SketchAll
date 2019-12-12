package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import drawing.DessinClient;
import main.FrameClient;
import server.RemoteDessinServeur;

// Classe d'édidion locale :
// - elle propage toutes ses actions au serveur via des "remote invocations"
// - elle reçoit également des mises à jour en provenance du serveur en mode "unicast"

public class EditeurClient extends JPanel {

	private static final long serialVersionUID = 1L;

	static Object shapeToCreate;
	Point debut, fin;

	Class<?> shapeClass;
	Constructor<?> shapeConstructor;

	private FrameClient frame;

	// l'élément visuel dans lequel on va manipuler des dessins
	private ZoneDeDessin ZoneDeDessin;
	private ToolPane toolPane;
	private JPanel chronometre;

	// une table pour stocker tous les dessins produits :
	// - elle est redondante avec le contenu de la ZoneDe Dessin
	// - mais elle va permettre d'accéder plus rapidement à un Dessin à partir de
	// son nom
	private HashMap<String, DessinClient> drawings = new HashMap<String, DessinClient>();

	// Constructeur à qui on transmet les informations suivantes :
	// - nom de l'éditeur
	// - nom du serveur distant
	// - nom de la machine sur laquelle se trouve le serveur
	// - numéro de port sur lequel est déclaré le serveur sur la machine distante
	public EditeurClient(FrameClient frame) {

		this.frame = frame;

		// création d'une ZoneDeDessin
		this.setLayout(new BorderLayout());
		ZoneDeDessin = new ZoneDeDessin(this);
		toolPane = new ToolPane(this);

		chronometre = new Chronometre(this);
		add(ZoneDeDessin, BorderLayout.CENTER);
		add(toolPane, BorderLayout.SOUTH);

		add(chronometre, BorderLayout.NORTH);

		this.setPreferredSize(new Dimension(700, 700));

		try {
			// récupération de tous les dessins déjà présents sur le serveur
			ArrayList<RemoteDessinServeur> dessinsDistants = frame.getServer().getDessinsPartages();
			// ajout de tous les dessins dans la zone de dessin
			for (RemoteDessinServeur rd : dessinsDistants) {
				addDrawing(rd, rd.getName(), rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight(), rd.getColor(),
						rd.getShapeType());
			}
		} catch (Exception e) {
			System.out.println("probleme liaison CentralManager");
			e.printStackTrace();
			System.exit(1);
		}

	}

	// méthode permettant de mettre à jour les limites d'un dessin
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur
	public synchronized void objectUpdateBounds(String objectName, int x, int y, int w, int h) {
		// récupération du dessin à partir de son nom
		DessinClient dessinToUpdate = drawings.get(objectName);
		if (dessinToUpdate != null) {
			dessinToUpdate.setBounds(x, y, w, h);
		}
	}

	// méthode permettant de mettre à jour la position d'un dessin
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur
	public synchronized void objectUpdateLocation(String objectName, int x, int y) {
		// récupération du dessin à partir de son nom
		DessinClient dessinToUpdate = drawings.get(objectName);
		if (dessinToUpdate != null) {
			dessinToUpdate.setLocation(x, y);
		}
	}

	public synchronized void objectUpdateColor(String objectName, Color color) {
		// récupération du dessin à partir de son nom
		DessinClient dessinToUpdate = drawings.get(objectName);
		if (dessinToUpdate != null) {
			dessinToUpdate.setForeground(color);
		}
	}

	// méthode permettant de créer un nouveau dessin suite à une interaction de
	// l'utilisateur
	// - elle va devoir transmettre cette création au serveur et lui demander un
	// proxy pour ce dessin
	public synchronized DessinClient creerDessin(int x, int y, int w, int h, Color color, String shapeType) {
		RemoteDessinServeur proxy = null;
		String proxyName = null;
		try {
			// ajout distant d'un dessin et récupération de son proxy
			proxy = frame.getServer().addDessin(x, y, w, h, color, shapeType);
			// récupération du nom attribué par le serveur :
			// - on le fait ici pour ne pas multiplier les blocs "try catch" nécessaires à
			// chaque invocation distante de méthode
			proxyName = proxy.getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// création d'un nouveau dessin
		String selectedShape = ZoneDeDessin.getShape();
		shapeClass(selectedShape);
		shapeConstructor();
		Object dessin;
		dessin = shapeInstance(ZoneDeDessin, proxy);
		// ajout du dessin dans la liste des dessins s'il n'y est pas déjà :
		// - on a pu recevoir une information de création en parallèle vie le Thread de
		// réception de messages diffusés par le serveur
		if (!drawings.containsKey(proxyName)) {
			drawings.put(proxyName, ((DessinClient) dessin));
		} else {
		}
		return ((DessinClient) dessin);
	}

	// méthode permettant d'ajouter localement un dessin déjà présent sur le serveur
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur
	public synchronized void addDrawing(String proxyName, int x, int y, int w, int h, Color color, String shapeType) {
		// System.out.println("ajout du dessin " + proxyName);
		// on ne l'ajoute que s'il n'est pas déjà présent
		// - il pourrait déjà être présent si il avait été créé localement par une
		// interaction dans cet éditeur local
		if (!drawings.containsKey(proxyName)) {
			RemoteDessinServeur proxy = null;
			try {
				// récupération du proxy via une demande au serveur
				proxy = frame.getServer().getDessin(proxyName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// ajout du dessin
			addDrawing(proxy, proxyName, x, y, w, h, color, shapeType);
		} else {
			// System.out.println ("dessin " + proxyName + " était déjà présent") ;
		}
	}

	// méthode d'ajout d'un dessin : factorisation de code
	public void addDrawing(RemoteDessinServeur proxy, String proxyName, int x, int y, int w, int h, Color color,
			String shapeType) {
		// création effective d'un dessin
		shapeClass(shapeType);
		shapeConstructor();
		DessinClient dessin;
		dessin = shapeInstance(ZoneDeDessin, proxy);

		// ajout du dessin dans la liste des dessins
		drawings.put(proxyName, dessin);
		// initialisation des limites du dessin
		dessin.setForeground(color);
		dessin.setBounds(x, y, w, h);
		// ajout du dessin dans la zone de dessin, au premier plan (grâce au "0" dans le
		// add)
		ZoneDeDessin.add(dessin, 0);
	}

	public void shapeClass(String selectedShape) {
		try {
			shapeClass = Class.forName("drawing." + selectedShape);
		} catch (ClassNotFoundException e1) {
		}
	}

	public void shapeConstructor() {
		try {
			shapeConstructor = shapeClass.getConstructor(ZoneDeDessin.class, RemoteDessinServeur.class);
		} catch (NoSuchMethodException | SecurityException e1) {
		}
	}

	public DessinClient shapeInstance(ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		try {
			shapeToCreate = shapeConstructor.newInstance(sheet, proxy);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
		}
		return ((DessinClient) shapeToCreate);
	}

	public ZoneDeDessin getZoneDeDessin() {
		return ZoneDeDessin;
	}

	public ShapePane getShapePane() {
		return toolPane.getShapePane();
	}

	public void setShape(DessinClient shape) {
		shapeToCreate = shape;
	}

	public void newGame() {
		drawings.clear();
		getZoneDeDessin().removeAll();
		repaint();
		revalidate();

	}

	public FrameClient getFrame() {
		return frame;
	}

	public boolean isSketcher() {
		boolean isSketcher = getFrame().getPlayer().isSketcher();
		return isSketcher;
	}

}
